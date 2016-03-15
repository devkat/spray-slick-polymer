package ch.becompany.rest

import ch.becompany.json.UserJsonProtocol
import ch.becompany.validation.Validation._
import ch.becompany.validation.Validator
import spray.http.StatusCodes
import spray.json.{DefaultJsonProtocol, JsValue, RootJsonFormat}
import spray.routing._
import spray.json._

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

  private def provideResult[T](t: T, result: ValidationResult): Directive1[T] =
    if (result.isEmpty) provide(t)
    else reject(ValidateRejection(result))

  def validateEntity[T:Validator](d: Directive1[T]): Directive1[T] =
    d flatMap {
      case t =>
        val validator = implicitly[Validator[T]]
        optionalHeaderValueByName("Validate-Attributes") flatMap {
          case Some(attr) => provideResult(t, validator.validate(t, attr))
          case None => provideResult(t, validator.validate(t))
        }
    }

  implicit def validateRejectionHandler = RejectionHandler {
    case List(ValidateRejection(result)) => {
      complete((StatusCodes.BadRequest, result.toJson))
    }
  }

}
