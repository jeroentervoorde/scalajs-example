package scalajsreact.template.components

import japgolly.scalajs.react.{ChildrenArg, JsComponent}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.Dynamic
import scala.scalajs.js.annotation.{JSExport, JSFullName, JSName, ScalaJSDefined}
/**
  * Created by jeroen on 9/21/16.
  */

@ScalaJSDefined
@JSName("MyMap")
@JSExport("MyMap")
class MyMap(props: js.Dynamic) extends scalajsreact.template.Bundle.ReactLeaflet.Map(props)


object Leaflet {



  object Map {
    @ScalaJSDefined
    class Props(
      var center: js.Array[Float],
      var zoom: Int,
      var style: js.Dynamic = js.Dynamic.literal("height" -> "100%")
      //var onMoveEnd : js.UndefOr[js.Function1[js.Dynamic, Unit]] = js.undefined
    ) extends js.Object {

      var ref : js.Function1[js.Dynamic, Unit] = (elem: js.Dynamic) => {
        dom.console.log("Ref: " + elem)
      }
    }

    val Component = JsComponent.byName[Props, ChildrenArg.Varargs, Props]("ReactLeaflet.Map")

    def apply(props: Props)(children: CtorType.ArgChild*) = {
      Component.ctor.apply(props)(children:_*)
    }
  }

  object TileLayer {
    @ScalaJSDefined
    class Props(var url: String, var attribution: String) extends js.Object

    val Component = JsComponent.byName[Props, ChildrenArg.None, Props]("ReactLeaflet.TileLayer")

    def apply(props: Props) = {
      Component.ctor.apply(props)
    }
  }

  object WMSTileLayer {
    val crs4326 = js.Dynamic.global.Leaflet.CRS.EPSG4326

    @ScalaJSDefined
    class Props(var url: String, var attribution: String, var version: String = "1.3.0", var transparent: Boolean = true) extends js.Object

    val Component = JsComponent.byName[Props, ChildrenArg.None, Props]("ReactLeaflet.WMSTileLayer")

    def apply(props: Props) = {
      Component.ctor.apply(props)
    }
  }

  object Marker {
    @ScalaJSDefined
    class Props(var position: js.Array[Float]) extends js.Object

    val Component = JsComponent.byName[Props, ChildrenArg.Varargs, Props]("ReactLeaflet.Marker")

    def apply(props: Props)(children: CtorType.ArgChild*) = {
      Component.ctor.apply(props)(children:_*)
    }
  }

  object Popup {
    val Component = JsComponent.byName[js.Object, ChildrenArg.Varargs, js.Object]("ReactLeaflet.Popup")

    def apply(children: CtorType.ArgChild*) = {
      Component.ctor.apply(new js.Object())(children:_*)
    }
  }

  //
  //    <TileLayer
  // url='http://{s}.tile.osm.org/{z}/{x}/{y}.png'
  // attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
  // />
}
