package ch.becompany.validation

package object Validation {
  type ValidationRule[T] = (T => Boolean, String)
  type ValidationResult = Map[Option[String], Seq[String]]
}

trait Validator[T] {
  import Validation._

  def rules: Map[Option[String], Seq[ValidationRule[T]]]

  def validate(t: T): ValidationResult = {
    rules map {
      case (attr, rules) => (attr, rules flatMap {
        case (rule, msg) => if (rule(t)) Some(msg) else None
      })
    }
  }

}
