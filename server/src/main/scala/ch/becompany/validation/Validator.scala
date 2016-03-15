package ch.becompany.validation

package object Validation {
  type ValidationRule[T] = (T => Boolean, String)
  type ValidationResult = Map[String, Seq[String]]
}

trait Validator[T] {
  import Validation._

  def rules: Map[String, Seq[ValidationRule[T]]]

  def validateRule(t: T)(ruleSet: (String, Seq[ValidationRule[T]])): Option[(String, Seq[String])] =
    ruleSet match {
      case (attr, rules) =>
        rules flatMap {
          case (rule, msg) => if (!rule(t)) Some(msg) else None
        } match {
          case Nil => None
          case list => Some((attr, list))
        }
    }

  def validate(t: T): ValidationResult =
    rules flatMap validateRule(t)

  def validate(t: T, attr: String): ValidationResult =
    rules filterKeys(_ == attr) flatMap validateRule(t)

}
