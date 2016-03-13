package ch.becompany.rest

case class Version(version: String) {
  def ::(part: String) = Dependency(part :: Nil, this)
}

case class Dependency(parts: List[String], version: Version) {
  def ::(part: String) = Dependency(part :: parts, version)
}

object Dependencies {

  implicit def stringToVersion(s: String): Version = Version(s)

  val angularDependencies = Seq(
    "angular-example" :: "1.0.0-SNAPSHOT",
    "angular2" :: "2.0.0-beta.8",
    "es6-promise" :: "3.1.2",
    "es6-shim" :: "0.35.0",
    "reflect-metadata" :: "0.1.3",
    "rxjs" :: "5.0.0-beta.2",
    "systemjs" :: "0.19.20"
  )

  val polymerDependencies: Seq[Dependency] = Seq(
    "github-com-Polymer" :: "polymer" :: "1.2.4",
    "github-com-PolymerElements" :: "font-roboto" :: "1.0.1",
    "github-com-PolymerElements" :: "iron-a11y-announcer" :: "1.0.4",
    "github-com-PolymerElements" :: "iron-a11y-keys-behavior" :: "1.1.0",
    "github-com-PolymerElements" :: "iron-ajax" :: "1.1.1",
    "github-com-PolymerElements" :: "iron-autogrow-textarea" :: "1.0.10",
    "github-com-PolymerElements" :: "iron-behaviors" :: "1.0.11",
    "github-com-PolymerElements" :: "iron-checked-element-behavior" :: "1.0.3",
    "github-com-PolymerElements" :: "iron-collapse" :: "1.0.4",
    "github-com-PolymerElements" :: "iron-dropdown" :: "1.0.6",
    "github-com-PolymerElements" :: "iron-fit-behavior" :: "1.0.5",
    "github-com-PolymerElements" :: "iron-flex-layout" :: "1.2.3",
    "github-com-PolymerElements" :: "iron-form-element-behavior" :: "1.0.6",
    "github-com-PolymerElements" :: "iron-icon" :: "1.0.7",
    "github-com-PolymerElements" :: "iron-icons" :: "1.1.3",
    "github-com-polymerelements" :: "iron-iconset-svg" :: "1.0.9",
    "github-com-PolymerElements" :: "iron-image" :: "1.0.4",
    "github-com-PolymerElements" :: "iron-input" :: "1.0.7",
    "github-com-PolymerElements" :: "iron-media-query" :: "1.0.7",
    "github-com-PolymerElements" :: "iron-menu-behavior" :: "1.0.6",
    "github-com-PolymerElements" :: "iron-meta" :: "1.1.1",
    "github-com-PolymerElements" :: "iron-overlay-behavior" :: "1.1.1",
    "github-com-PolymerElements" :: "iron-pages" :: "1.0.4",
    "github-com-PolymerElements" :: "iron-range-behavior" :: "1.0.3",
    "github-com-PolymerElements" :: "iron-resizable-behavior" :: "1.0.2",
    "github-com-PolymerElements" :: "iron-selector" :: "1.2.2",
    "github-com-PolymerElements" :: "iron-validatable-behavior" :: "1.0.5",
    "github-com-PolymerElements" :: "neon-animation" :: "1.0.8",
    "github-com-PolymerElements" :: "paper-badge" :: "1.0.4",
    "github-com-PolymerElements" :: "paper-behaviors" :: "1.0.10",
    "github-com-PolymerElements" :: "paper-button" :: "1.0.11",
    "github-com-PolymerElements" :: "paper-card" :: "1.0.8",
    "github-com-PolymerElements" :: "paper-checkbox" :: "1.0.10",
    "github-com-PolymerElements" :: "paper-dialog" :: "1.0.4",
    "github-com-PolymerElements" :: "paper-dialog-behavior" :: "1.1.0",
    "github-com-PolymerElements" :: "paper-dialog-scrollable" :: "1.1.0",
    "github-com-PolymerElements" :: "paper-drawer-panel" :: "1.0.6",
    "github-com-PolymerElements" :: "paper-dropdown-menu" :: "1.1.0",
    "github-com-PolymerElements" :: "paper-elements" :: "1.0.6",
    "github-com-PolymerElements" :: "paper-fab" :: "1.1.0",
    "github-com-PolymerElements" :: "paper-header-panel" :: "1.1.1",
    "github-com-PolymerElements" :: "paper-icon-button" :: "1.0.6",
    "github-com-PolymerElements" :: "paper-input" :: "1.0.18",
    "github-com-PolymerElements" :: "paper-input" :: "1.1.1",
    "github-com-PolymerElements" :: "paper-item" :: "1.1.2",
    "github-com-PolymerElements" :: "paper-material" :: "1.0.4",
    "github-com-PolymerElements" :: "paper-menu" :: "1.2.1",
    "github-com-PolymerElements" :: "paper-menu-button" :: "1.0.3",
    "github-com-PolymerElements" :: "paper-progress" :: "1.0.7",
    "github-com-PolymerElements" :: "paper-radio-button" :: "1.0.11",
    "github-com-PolymerElements" :: "paper-radio-group" :: "1.0.4",
    "github-com-polymerelements" :: "paper-ripple" :: "1.0.2",
    "github-com-PolymerElements" :: "paper-scroll-header-panel" :: "1.0.12",
    "github-com-PolymerElements" :: "paper-slider" :: "1.0.8",
    "github-com-PolymerElements" :: "paper-spinner" :: "1.1.0",
    "github-com-PolymerElements" :: "paper-styles" :: "1.1.3",
    "github-com-PolymerElements" :: "paper-tabs" :: "1.1.0",
    "github-com-PolymerElements" :: "paper-toast" :: "1.1.0",
    "github-com-PolymerElements" :: "paper-toggle-button" :: "1.0.11",
    "github-com-PolymerElements" :: "paper-toolbar" :: "1.1.4",
    "github-com-PolymerElements" :: "paper-tooltip" :: "1.1.1",
    "github-com-PolymerElements" :: "paper-icon-button" :: "1.0.5",
    "github-com-PolymerElements" :: "paper-listbox" :: "1.0.0",
    "github-com-PolymerElements" :: "paper-material" :: "1.0.2",
    "github-com-PolymerElements" :: "paper-ripple" :: "1.0.2",
    "github-com-PolymerElements" :: "paper-styles" :: "1.0.9",
    "github-com-web-animations-web-animations-js" :: "2.1.2",
    "page" :: "1.6.4",
    "polymer-ts" :: "0.1.20",
    "webcomponentsjs" :: "0.7.20"
  )

  val dependencies = angularDependencies ++ polymerDependencies

  def resolveDir(module: String): Option[String] =
    dependencies.find(d => d.parts.last == module) map { dep =>
      val dir = dep.parts.mkString("-")
      //System.out.println(s"Resolving $dir/${dep.version.version}")
      s"META-INF/resources/webjars/$dir/${dep.version.version}"
    }

}
