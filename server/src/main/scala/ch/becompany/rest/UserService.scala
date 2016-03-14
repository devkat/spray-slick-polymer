package ch.becompany.rest

import akka.actor.{ActorContext, Actor}
import ch.becompany.db.Users
import ch.becompany.model.{UserValidator, User}
import ch.becompany.json.UserJsonProtocol
import spray.http.StatusCodes
import spray.routing._

import scala.concurrent.ExecutionContext

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class UserServiceActor extends Actor with UserService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(usersRoute)
}

// this trait defines our service behavior independently from the service actor
trait UserService extends HttpService with ValidationDirectives {
  val context: ActorContext

  import UserJsonProtocol._
  import spray.httpx.SprayJsonSupport._
  import context.dispatcher
  import Dependencies._

  implicit val userValidator = UserValidator
  val validateUser = validateEntity(entity(as[User]))

  lazy val usersRoute =
    pathPrefix("api") {
      path("users") {
        get {
          complete(Users.list())
        } ~
        post {
          validateUser { user =>
            complete(StatusCodes.Created, Users.add(user) map (id => user.copy(id = Some(id))))
          }
        }
      } ~
      path("users" / LongNumber) { id =>
        rejectEmptyResponse {
          get {
            complete(Users.find(id))
          }
        } ~
        put {
          validateUser { user =>
              complete {
                Users.update(id, user)
                user
              }
          }
        }
      }
    } ~
    pathPrefix("bower_components" / Segment) { moduleName =>
      get {
        resolveDir(moduleName) match {
          case Some(dir) => getFromResourceDirectory(dir)
          case None =>
            System.out.println("Could not resolve module " + moduleName)
            reject
        }
      }
    } ~
    path(PathEnd) {
      getFromResource("polymer/index.html")
    } ~
    pathPrefix("") {
      getFromResourceDirectory("polymer")
    }

}