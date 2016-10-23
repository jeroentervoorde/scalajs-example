package scalajsreact.template.models

import diode.ActionResult.{ModelUpdate, ModelUpdateSilent}
import diode.react.ReactConnector
import diode.{Action, Circuit}
import org.scalajs.dom

case class Position(lat: Float, lon: Float)

case class AppState(position: Position, zoom: Int, sel: Option[String] = None)

case class UpdateSelection(newSelection: Option[String]) extends Action

case class UpdatePosition(newPosition: Position, silent: Boolean) extends Action

case class UpdateZoom(newZoom: Int) extends Action

case class UpdatePositionAndZoom(newPosition: Position, newZoom: Int, silent: Boolean) extends Action


object AppCircuit extends Circuit[AppState] with ReactConnector[AppState] {
  def initialModel = AppState(Position(51.0f, 5.2f), 8, None)
  override val actionHandler: HandlerFunction =
    (model, action) => action match {
      case UpdateSelection(newSelection) =>
        val updated = (model.sel.toSeq ++ newSelection).mkString(", ")
        Some(ModelUpdate(model.copy(sel = newSelection)))
      case UpdatePosition(newPosition, silent) =>
        val newModel = model.copy(position = newPosition)
        val update = if (silent) {
          ModelUpdateSilent(newModel)
        } else {
          ModelUpdate(newModel)
        }
        Some(update)
      case UpdateZoom(newZoom) =>
        Some(ModelUpdate(model.copy(zoom = newZoom)))
      case UpdatePositionAndZoom(newPosition, newZoom, silent) =>
        if (silent) {
          Some(ModelUpdateSilent(model.copy(position = newPosition, zoom = newZoom)))
        } else {
          Some(ModelUpdate(model.copy(position = newPosition, zoom = newZoom)))
        }
        case _ => None
    }
}