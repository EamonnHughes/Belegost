package org.eamonn.belegost.items

import org.eamonn.belegost._
import org.eamonn.belegost.scenes.Game

case class Rations(game: Game) extends Item {
  def use: Unit = {
    game.player.hunger += (250 - game.player.hunger) / 10

  }
  var name = "Rations"

}
