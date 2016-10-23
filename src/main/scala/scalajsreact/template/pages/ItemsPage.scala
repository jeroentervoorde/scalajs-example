package scalajsreact.template.pages

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import scala.scalajs.js
import scalacss.Defaults._
import scalajsreact.template.routes.Item

object ItemsPage {

  object Style extends StyleSheet.Inline {
    import dsl._
    val container = style(
    display.flex,
    minHeight(600.px))

    val nav = style(width(190.px),
    borderRight :=! "1px solid rgb(223, 220, 220)")

    val content = style(padding(30.px))
  }

  val component = ScalaComponent.build[Props]("ItemsPage")
    .render_P { P =>
      <.div(
        //<.div(Style.nav, LeftNav(LeftNav.Props(Item.menu,P.selectedPage,P.ctrl))),
        <.div(P.selectedPage.title)
      )
    }
    .build

  case class Props(selectedPage : Item/*,ctrl : RouterCtl[Item]*/)

  def apply(props : Props,ref: js.UndefOr[String] = "", key: js.Any = {}) = component.ctor(props)

}
