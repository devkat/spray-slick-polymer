package ch.becompany.validation

object CoreRules {

  def notBlank(s: String): Boolean = s.trim.length > 0

}
