package ch.becompany.model

import ch.becompany.db.Users
import ch.becompany.validation.CoreRules._
import ch.becompany.validation.Validator

import scala.concurrent.{ExecutionContext, Future}

case class User(
  id: Option[Long],
  email: String,
  givenName: String,
  familyName: String,
  role: Role
)

object UserValidator extends Validator[User] {
  import ch.becompany.validation.CoreRules._

  def newOrSameEmail(user: User)(implicit ec: ExecutionContext): Future[Boolean] =
    Users.findByEmail(user.email) map { u => (user.id, u) match {
      case (_, None) => true
      case (None, Some(u)) => false
      case (Some(id), Some(u)) => id == u.id.get
    }}

  def rules(user: User)(implicit ec: ExecutionContext) = Map(
    "email" -> Seq(
      notBlank(user.email) ~ "Please enter an e-mail address.",
      email(user.email) ~ "Please enter a valid e-mail address.",
      newOrSameEmail(user) ~ "This e-mail address is already registered."
    ),
    "givenName" -> Seq(
      notBlank(user.givenName) ~ "Please enter a given name."
    ),
    "familyName" -> Seq(
      notBlank(user.familyName) ~ "Please enter a family name."
    )
  )

}