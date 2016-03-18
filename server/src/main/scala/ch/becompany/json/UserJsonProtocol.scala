package ch.becompany.json

import ch.becompany.model.{Role,User}
import spray.json._

object UserJsonProtocol extends DefaultJsonProtocol {

  implicit object RoleFormat extends JsonFormat[Role] {
    def read(value: JsValue): Role = value match {
      case JsString(x) => Role.parse(x)
      case x => deserializationError("Expected String as JsString, but got " + x)
    }
    def write(role: Role) = JsString(role.id)
  }

  implicit val UserFormat = jsonFormat5(User)

}
