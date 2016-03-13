package ch.becompany.json

import ch.becompany.model.User
import spray.json.{RootJsonFormat, JsValue, DefaultJsonProtocol}

object UserJsonProtocol extends DefaultJsonProtocol {

  implicit val UserFormat = jsonFormat4(User)

  implicit val jsValueFormat = new RootJsonFormat[JsValue] {
    def write(value: JsValue) = value
    def read(value: JsValue) = value
  }

}
