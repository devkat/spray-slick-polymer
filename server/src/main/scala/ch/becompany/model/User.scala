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

  val rules: Map[String, Seq[ValidationRule[User]]] =
    Map(
      "email" -> Seq(
        ((user: User) => notBlank(user.email), "Please enter an e-mail address."),
        ((user: User) => email(user.email), "Please enter a valid e-mail address.")),
      "givenName" -> Seq(
        ((user: User) => notBlank(user.givenName), "Please enter a given name.")
      ),
      "familyName" -> Seq(
        ((user: User) => notBlank(user.familyName), "Please enter a family name.")
      )
    )

}