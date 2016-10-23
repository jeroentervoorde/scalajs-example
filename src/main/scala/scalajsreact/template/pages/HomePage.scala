package scalajsreact.template.pages

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import scalacss.Defaults._

object HomePage {

  object Style extends StyleSheet.Inline {
    import dsl._
    val content = style(textAlign.center,
      fontSize(30.px),
      minHeight(450.px),
      paddingTop(40.px))
  }

  val component = ScalaComponent.build[Unit]("HomePage").render_P(p =>
    <.div("ScalaJS-React Template ")
  ).build

  def apply() = component
}
