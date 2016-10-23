package scalajsreact.template.routes

import japgolly.scalajs.react.vdom.ReactElement

import scalajsreact.template.components.items.{Item1Data, Item2Data, ItemsInfo}
import scalajsreact.template.pages.ItemsPage

sealed abstract class Item(val title: String,
val routerPath: String)


object Item {

  case object Info extends Item("Info","info")

  case object Item1 extends Item("Item1","item1")

  case object Item2 extends Item("Item2","item2")

  val menu = Vector(Info,Item1,Item2)

//  val routes = RouterConfigDsl[Item].buildRule { dsl =>
//    import dsl._
//
//    menu.map(i =>
//      staticRoute(i.routerPath, i) ~> renderR(r => ItemsPage(props = ItemsPage.Props(i, r)))
//    ).reduce(_ | _)
//
//  }

}
