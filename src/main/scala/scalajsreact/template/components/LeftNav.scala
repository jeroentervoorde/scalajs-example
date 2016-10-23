package scalajsreact.template.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import scala.scalajs.js.{Any, UndefOr}
import scalacss.Defaults._
import scalajsreact.template.routes.Item

object LeftNav {

  object Style extends StyleSheet.Inline {

    import dsl._

    val container = style(display.flex,
      flexDirection.column,
      listStyle := "none",
      padding.`0`
    )

    val menuItem = styleF.bool(selected => styleS(
      lineHeight(48.px),
      padding :=! "0 25px",
      cursor.pointer,
      textDecoration := "none",
      mixinIfElse(selected)(color.red,
        fontWeight._500)
        (color.black,
            &.hover(color(c"#555555"),
              backgroundColor(c"#ecf0f1")))
    ))
  }

  case class Props(menus: Vector[Item], selectedPage: Item/*, ctrl: RouterCtl[Item]*/)

  //implicit val currentPageReuse = Reusability.by_==[Item]
  //implicit val propsReuse = Reusability.by((_: Props).selectedPage)

  val component = ScalaComponent.build[Props]("LeftNav")
    .render_P { P =>
      <.ul(P.menus.map(m => <.li(m.title)):_*)
//      <.ul(Style.container)(
//        P.menus.map(item => <.li(^.key := item.title,
//          Style.menuItem(item == P.selectedPage),
//          item.title))
//          //P.ctrl setOnClick item))
//      )
    }
    //.configure(Reusability.shouldComponentUpdate)
    .build


  def apply(props: Props, ref: UndefOr[String] = "", key: Any = {}) = component.ctor(props)//.set(key, ref)(props)

}
