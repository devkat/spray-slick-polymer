package ch.becompany.model

import ch.becompany.validation.CoreRules._
import ch.becompany.validation.Validator
import ch.becompany.validation.Validation.ValidationRule

case class User(
  id: Option[Long],
  email: String,
  givenName: String,
  familyName: String
)

object UserValidator extends Validator[User] {
  import ch.becompany.validation.CoreRules._

  val rules: Map[Option[String], Seq[ValidationRule[User]]] =
    Map(Some("email") -> Seq(((user: User) => notBlank(user.email), "Please enter an email address.")))

}