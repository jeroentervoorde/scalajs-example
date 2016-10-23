package scalajsreact.template.components.items

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object ItemsInfo {

  val component = ScalaComponent.build[Unit]("ItemsInfo").render_P( p =>
    <.div(" Items Root Page  ")
  ).build

  def apply() = component
}
