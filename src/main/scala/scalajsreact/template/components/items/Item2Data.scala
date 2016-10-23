package scalajsreact.template.components.items

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._


object Item2Data {

   val component = ScalaComponent.build[Unit]("Item2").render_P( p =>
     <.div("This is Item2 Page ")
   ).build

   def apply() = component
 }
