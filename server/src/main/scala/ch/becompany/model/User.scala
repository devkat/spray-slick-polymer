package ch.becompany.model

import ch.becompany.db.Users
import ch.becompany.validation.CoreRules._
import ch.becompany.validation.Validator
import ch.becompany.validation.Validation.ValidationRule

import scala.concurrent.{ExecutionContext, Future}

case class User(
  id: Option[Long],
  email: String,
  givenName: String,
  familyName: String
)

object UserValidator extends Validator[User] {
  import ch.becompany.validation.CoreRules._

  implicit def toFuture(b: Boolean)(implicit ec: ExecutionContext): Future[Boolean] = Future(b)

  def newOrSameEmail(user: User)(implicit ec: ExecutionContext): Future[Boolean] =
    Users.findByEmail(user.email) map { u => (user.id, u) match {
      case (_, None) => true
      case (None, Some(u)) => false
      case (Some(id), Some(u)) => id == u.id.get
    }}

  def rules(implicit ec: ExecutionContext): Map[String, Seq[ValidationRule[User]]] =
    Map(
      "email" -> Seq[ValidationRule[User]](
        ((user: User) => notBlank(user.email), "Please enter an e-mail address."),
        ((user: User) => email(user.email), "Please enter a valid e-mail address."),
        ((user: User) => newOrSameEmail(user), "This e-mail address is already registered.")
      ),
      "givenName" -> Seq[ValidationRule[User]](
        ((user: User) => notBlank(user.givenName), "Please enter a given name.")
      ),
      "familyName" -> Seq[ValidationRule[User]](
        ((user: User) => notBlank(user.familyName), "Please enter a family name.")
      )
    )

}