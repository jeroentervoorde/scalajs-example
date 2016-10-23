package scalajsreact.template

import diode.react.{ModelProxy, ReactConnectProxy}
import japgolly.scalajs.react._
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLElement
import japgolly.scalajs.react.vdom._
import japgolly.scalajs.react.vdom.html_<^._

import scala.scalajs.js
import scala.scalajs.js.{Dynamic, JSApp}
import scala.scalajs.js.annotation.{JSExport, ScalaJSDefined}
import scalajsreact.template.components.Select.Opt
import scalajsreact.template.components.{Footer, Leaflet, Smally, Smally2}
import scalajsreact.template.css.AppCSS
import scalajsreact.template.models._
import scala.scalajs.js.JSConverters._

@js.native
object Bundle extends js.Object {
  def Leaflet : js.Any = js.native
  def React : js.Any = js.native
  def ReactDOM : js.Any = js.native
  def ReactSelect : js.Any = js.native

  @js.native
  object ReactLeaflet extends js.Object {

    @js.native
    class Map(props: js.Dynamic) extends js.Object
    //def Map: js.Any = js.native
    //def TileLayer: js.Any = js.native
  }

}

@JSExport
object ReactApp extends JSApp {

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
     ) extends js.Object

    object JsProps {
      def apply(options: Seq[Opt], value: Option[String], onChange: Option[(String) => Unit]) = {
        new JsProps(options.toJSArray, value.orUndefined, onChange.map(l => ((o:Opt) => l(o.value)) : js.Function1[Opt, Unit]).orUndefined)
      }
    }
    val Component = JsComponent.byName[JsProps, ChildrenArg.None, JsProps]("ReactSelect")

    def apply(props: JsProps) = Component(props)
  }

  @js.native
  trait LMap extends js.Object {
    def leafletElement: js.Dynamic = js.native
  }

  object Page {
    val component = ScalaComponent
      .build[ModelProxy[AppState]]("Page")
      .stateless
      .noBackend
      .render[ChildrenArg.None] { $ =>
        {
          val state = $.props.value
          val center = js.Array(state.position.lat, state.position.lon)
          val zoom = state.zoom

          val base = Leaflet.TileLayer(new Leaflet.TileLayer.Props("http://{s}.tiles.mapbox.com/v3/simacan.h8d0jmim/{z}/{x}/{y}.png", ""))
          val hdflow = Leaflet.WMSTileLayer(new Leaflet.WMSTileLayer.Props("http://hdflow-map-dev.services.simacan.com/wms", ""))
          val cris = Leaflet.WMSTileLayer(new Leaflet.WMSTileLayer.Props("http://cris2-map.services.simacan.com/wms?layerName=cris2incidents", ""))

          val popup = Leaflet.Popup(<.i(s"Navigeren naar ${state.sel}"))
          val marker = Leaflet.Marker(new Leaflet.Marker.Props(center))(popup)

          val mapProps = new Leaflet.Map.Props(center, zoom) {
            def updatePos(event: js.Dynamic) : Unit = {

              val newCenter = event.target.getCenter()
              val pos = Position(newCenter.lat.asInstanceOf[Float], newCenter.lng.asInstanceOf[Float])
              dom.console.log(s"update pos $pos")

              $.props.dispatch(UpdatePosition(pos, silent = true)).runNow()
            }

            def updateZoom(event: js.Dynamic) : Unit = {

              val newZoom = event.target.getZoom().asInstanceOf[Int]
              dom.console.log(s"update zoom $newZoom")
              $.props.dispatch(UpdateZoom(newZoom)).runNow()
            }

            val onMoveEnd = Some((updatePos _) : js.Function1[js.Dynamic, Unit]).orUndefined
            val onZoomEnd = Some((updateZoom _) : js.Function1[js.Dynamic, Unit]).orUndefined
          }

          val children = Seq(base, hdflow, cris, marker)
          val map = Leaflet.Map(mapProps)(children.map(_.reactElement):_*)


          //val lmap = map.mapMounted[LMap](_.asInstanceOf[LMap])



          //val l = (map.asInstanceOf[js.Dynamic].leafletElement)
          //dom.console.log("leaflet element " + l)

          val destinations = Seq(Select.Opt("Kantoor"),Select.Opt("Huis"))

          val props = Select.JsProps(destinations, state.sel, Some(s => $.props.dispatchNow(UpdateSelection(Some(s)))))
          val select = Select.Component(props)
          <.div(
            <.b("Select destination"),
            select,
            map
          )

        }
      }
      .shouldComponentUpdate(_ => true)
      .build

    def apply(state: ModelProxy[AppState]) = {
      component.ctor.apply(state)
    }
  }

  @JSExport
  override def main(): Unit = {

    js.Dynamic.global.React = Bundle.React
    js.Dynamic.global.ReactDOM = Bundle.ReactDOM
    js.Dynamic.global.ReactSelect = Bundle.ReactSelect
    js.Dynamic.global.ReactLeaflet = Bundle.ReactLeaflet
    js.Dynamic.global.Leaflet = Bundle.Leaflet
    js.Dynamic.global.Leaflet.Icon.Default.imagePath = "http://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images"
    AppCSS.load


    dom.document.body.style.height = "100vh"
    val b = dom.document.body

    val e = dom.document.createElement("div").asInstanceOf[HTMLElement]
    b.appendChild(e)

    e.id = "app"
    e.style.width = "100%"
    e.style.height = "100vh"
    //e.style.backgroundColor = "green"

    Footer("JeroenSoft")

    def updatePosition(pos: js.Dynamic) : Unit = {
      AppCircuit.dispatch(
        UpdatePosition(
          Position(
            pos.coords.latitude.asInstanceOf[Float],
            pos.coords.longitude.asInstanceOf[Float]
          ),
          silent = false
        ))
    }

    def positionError(err: js.Dynamic) : Unit = {
      dom.console.log(s"position error: ${err.message}")
    }

    val positionOps = js.Dynamic.literal("enableHighAccuracy" -> true)

//    import scala.scalajs.js.timers._
//    setInterval(5000) { // note the absence of () =>
//      val lat : Float = 51f + math.random.toFloat / 100
//      val lon : Float = 5.1f + math.random.toFloat / 100
//      AppCircuit.dispatch(UpdatePosition(Position(lat, lon), silent = false))
//    }

    js.Dynamic.global.navigator.geolocation.watchPosition(updatePosition _, positionError _, positionOps)
//    val dc = AppCircuit.wrap(_.sel){ p =>
//      dom.console.log("Building smally")
//      Smally2(Smally2.Props(p.value, p.dispatch(UpdateSelection(Some("Hallo!")))))
//    }
//
//    dc.renderIntoDOM(e)

    AppCircuit.subscribe(AppCircuit.zoom(_.zoom))(d =>
      dom.console.log("l: " + d.value)
    )

    val sc = AppCircuit.connect(s => s)

    sc(s => Page(s)).renderIntoDOM(e)

  }

}

