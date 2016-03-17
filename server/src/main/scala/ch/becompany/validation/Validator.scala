package ch.becompany.validation

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Try, Success, Failure}

case class RuleCheck(check: Future[Boolean]) {
  def ~(msg: String) = RuleResult(check, msg)
}

case class RuleResult(check: Future[Boolean], msg: String) {
  def evaluate(implicit ec: ExecutionContext): Future[Option[String]] = check map { result =>
    if (!result) Some(msg) else None
  }
}

package object Validation {
  type ValidationResult = Map[String, Seq[String]]
}

trait Validator[T] {
  import Validation._

  implicit def toRuleCheck(b: Boolean)(implicit ec: ExecutionContext): RuleCheck =
    RuleCheck(Future(b))

  implicit def toRuleCheck(f: Future[Boolean])(implicit ec: ExecutionContext): RuleCheck =
    RuleCheck(f)

  /**
    * Validation rules.
    * @param t The object to validate.
    * @param ec The execution context.
    * @return A map from attributes to sets of validation results.
    */
  def rules(t: T)(implicit ec: ExecutionContext): Map[String, Seq[RuleResult]]

  /**
    * Validate an object.
    * @param t The object.
    * @param ec The execution context.
    * @return A future validation result.
    */
  def validate(t: T)(implicit ec: ExecutionContext): Future[ValidationResult] = {
    val results = rules(t).toSeq.map { case (attr, ruleResults) =>
      Future.sequence(ruleResults.map(_.evaluate)).map(results => (attr, results.flatten))
    }
    Future.sequence(results).map(_.filter(!_._2.isEmpty).toMap)
  }

}
