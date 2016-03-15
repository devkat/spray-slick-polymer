package ch.becompany.validation

import scala.concurrent.Future

object CoreRules {

  private val emailRegex = """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""".r

  def notBlank(s: String): Boolean = s.trim.length > 0

  def email(s: String): Boolean =
    emailRegex.findFirstMatchIn(s).isDefined
}
