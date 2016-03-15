package ch.becompany.validation

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Try, Success, Failure}

package object Validation {
  type ValidationRule[T] = (T => Future[Boolean], String)
  type ValidationResult = Map[String, Seq[String]]
}

trait Validator[T] {
  import Validation._

  def rules(implicit ec: ExecutionContext): Map[String, Seq[ValidationRule[T]]]

  def flatRules(implicit ec: ExecutionContext): Seq[(String, ValidationRule[T])] =
    rules.toSeq.flatMap { case (attr, ruleSet) =>
      ruleSet map { r => (attr, r) }
    }

  def evaluateRule(t: T)(rule: ValidationRule[T])(implicit ec: ExecutionContext): Future[Option[String]] =
    rule match {
      case (check, msg) =>
        check(t) map { case result => if (!result) Some(msg) else None }
    }


  def validateAttrRules(t: T)(attrRules: (String, Seq[ValidationRule[T]]))(implicit ec: ExecutionContext):
    Future[(String, Seq[String])] =
      attrRules match {
        case (attr, rules) => {
          val checkedRules: Seq[Future[Option[String]]] = rules map evaluateRule(t)
          Future.sequence(checkedRules).map(results => (attr, results.flatten))
        }
      }

  def validate(t: T)(implicit ec: ExecutionContext): Future[ValidationResult] =
    Future.sequence(rules.map(validateAttrRules(t))).map(_.toMap)

}
