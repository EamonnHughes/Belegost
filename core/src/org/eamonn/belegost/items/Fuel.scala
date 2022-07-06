package org.eamonn.belegost.items

import org.eamonn.belegost.scenes.Game

case class Fuel(game: Game) extends Item {
  def use: Unit = {
    game.player.lightS.foreach(l => l.mod = (l.mod + 300) min l.Type.mod)

  }
  var name = "Fuel"

}
