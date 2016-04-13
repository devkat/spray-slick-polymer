package ch.becompany.rest

import ch.becompany.json.UserJsonProtocol
import ch.becompany.validation.Validation._
import ch.becompany.validation.Validator
import spray.http.StatusCodes
import spray.json.{DefaultJsonProtocol, JsValue, RootJsonFormat}
import spray.routing._
import spray.json._

import scala.concurrent.ExecutionContext
import scala.util.{Success, Failure}

object JsValueJsonProtocol extends DefaultJsonProtocol {
  implicit val jsValueFormat = new RootJsonFormat[JsValue] {
    def write(value: JsValue) = value
    def read(value: JsValue) = value
  }
}

case class ValidateRejection(result: ValidationResult) extends Rejection

trait ValidationDirectives extends Directives {

  import spray.httpx.SprayJsonSupport._
  import JsValueJsonProtocol._

  def validateEntity[T:Validator](d: Directive1[T])(implicit ec: ExecutionContext): Directive1[T] =
    d flatMap { t =>
      val validator = implicitly[Validator[T]]
      onComplete(validator.validate(t)) flatMap {
        case Success(result) =>
          if (result.isEmpty) provide(t)
          else reject(ValidateRejection(result))
        case Failure(ex) => complete(StatusCodes.InternalServerError,
          s"An error occurred: ${ex.getMessage}")
      }
    }

  implicit def validateRejectionHandler = RejectionHandler {
    case List(ValidateRejection(result)) => {
      complete((StatusCodes.BadRequest, result.toJson))
    }
  }

}
