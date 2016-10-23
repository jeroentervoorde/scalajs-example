package scalajsreact.template.components

import diode.react.ModelProxy
import japgolly.scalajs.react.Lifecycle.RenderScope
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.ScalaJSDefined
import scalajsreact.template.components.Select.JsProps
import scalajsreact.template.models.{AppState, UpdateSelection}

object Select {

  @ScalaJSDefined
  class Opt(var value: String, var label: String) extends js.Object

  object Opt {
    def apply(v: String) = new Opt(v, v)
  }

  @ScalaJSDefined
  class JsProps(
    var options: js.Array[Opt],
    var value: js.UndefOr[String],
    var onChange : js.UndefOr[js.Function1[Opt, Unit]] = js.undefined

  ) extends js.Object {
  }

  object JsProps {
    def apply(options: Seq[Opt] = js.Array(Opt("Jeroen"), Opt("is"), Opt("the"), Opt("Bomb")), value: Option[String], onChange: Option[(String) => Unit]) = {
      new JsProps(options.toJSArray, value.orUndefined, onChange.map(l => ((o:Opt) => l(o.value)) : js.Function1[Opt, Unit]).orUndefined)
    }
  }

  val Component = JsComponent.byName[JsProps, ChildrenArg.None, JsProps]("ReactSelect")

  def apply(props: JsProps) = {
    Component.ctor.apply(props)
  }
}

object Smally {
  val component = ScalaComponent
    .build[ModelProxy[Option[String]]]("Smally")
    .stateless
    .noBackend
    .render[ChildrenArg.None] { $ =>
      <.div(^.onClick --> $.props.dispatch(UpdateSelection(Some("Hoera"))),^.fontWeight := "bold", s"I am smally: ${$.props()}")
    }
    .shouldComponentUpdate(_ => true)
    .build

  def apply(str: ModelProxy[Option[String]]) = {
    dom.console.log(s"creating a new smally2 for props ${str}")
    component.ctor.apply(str)
  }
}

object Smally2 {
  case class Props(s: Option[String], clicked: Callback, changed: (String) => Unit)

  val component = ScalaComponent
    .build[Props]("Smally")
    .stateless
    .noBackend
    .render[ChildrenArg.None] { $ =>
    <.div(
      <.div(^.onClick --> $.props.clicked, ^.fontWeight := "bold", s"I am smally: ${$.props.s}"),
      Select(JsProps(value = $.props.s, onChange = Some($.props.changed)))
    )
  }
    .shouldComponentUpdate(_ => true)
    .build

  def apply(str: Props) = {
    dom.console.log(s"creating a new smally2 for props ${str}")
    component.ctor.apply(str)
  }
}


object Footer {
  case class State(counter: Int = 0)

  class Backend($: BackendScope[String, State]) {

    val inc: Callback =
      $.modState(s => s.copy(s.counter + 1))

    def render(p: String, s: State) = {
      <.footer(^.textAlign.center,
        <.div(^.borderBottom := "1px solid grey", ^.padding := "0px"),
        <.div(^.onClick --> inc, ^.paddingTop := "5px", s"Built using ${p}. Clicked ${s.counter} times!")
      )
    }
  }

  val component = ScalaComponent
    .build[String]("Footer")
    .initialState(State())
    //.noBackend
    .backend(new Backend(_))
//    .render[ChildrenArg.None] { $ =>
//      <.footer(^.textAlign.center,
//        Select.Component.ctor.apply(new JsProps()),
//
//        <.div(^.borderBottom := "1px solid grey", ^.padding := "0px"),
//        <.div(^.onClick --> $.modState(s => s.copy(counter = s.counter + 1)), ^.paddingTop := "5px", s"Built using ${$.props}. Clicked ${$.state.counter} times!")
//      )
//    }
    .renderBackend
    .build

  def apply(str: String) = component(str)
}
