package ch.becompany.rest

import akka.actor.{ActorContext, Actor}
import ch.becompany.Boot
import ch.becompany.dao.Tables.UserRow
import ch.becompany.db.Users
import ch.becompany.model.{UserValidator, User}
import ch.becompany.json.UserJsonProtocol
import ch.becompany.validation.Validator
import ch.becompany.validation.Validation.ValidationResult
import shapeless._
import spray.http.StatusCodes
import spray.httpx.unmarshalling.FromRequestUnmarshaller
import spray.httpx.marshalling._
import spray.routing._
import spray.json._

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

case class ValidateRejection(result: ValidationResult) extends Rejection

// this trait defines our service behavior independently from the service actor
trait UserService extends HttpService {
  val context: ActorContext

  import UserJsonProtocol._
  import spray.httpx.SprayJsonSupport._
  import context.dispatcher
  import Dependencies._

  def validateEntity1[T:Validator, U <: HList](t: T): Directive0 =
    new Directive0 {
      def happly(inner: HNil ⇒ Route): Route = {
        val validator = implicitly[Validator[T]]
        val result = validator.validate(t)
        if (result.isEmpty) inner(HNil)
          else reject(ValidateRejection(result))
      }
    }
/*
  def validateEntity[T:Validator](t: T): Directive1[T] =
    entity(um) flatMap {
      case t =>
        val validator = implicitly[Validator[T]]
        val result = validator.validate(t)
        if (result.isEmpty) t
        else reject(ValidateRejection(result))
    }
*/

  def validateEntity[T:Validator](d: Directive1[T]): Directive1[T] =
    d flatMap {
      case t =>
        val validator = implicitly[Validator[T]]
        val result = validator.validate(t)
        if (result.isEmpty) provide(t)
        else reject(ValidateRejection(result))
    }

  implicit val userValidator = UserValidator
  val validateUser = validateEntity(entity(as[User]))

  implicit val validateRejectionHandler = RejectionHandler {
    case List(ValidateRejection(result)) => {
      complete((StatusCodes.BadRequest, result.toJson))
    }
  }

  lazy val usersRoute =
    pathPrefix("api") {
      path("users") {
        get {
          complete(Users.list())
        } ~
        post {
          entity(as[User]) { user =>
            complete(Users.add(user) map (id => user.copy(id = Some(id))))
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