package scalajsreact.template.routes

object AppRouter {

  sealed trait AppPage

  case object Home extends AppPage
  case class Items(p : Item) extends AppPage

}
