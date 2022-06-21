package org.eamonn.belegost.equipment

import org.eamonn.belegost.scenes.Game

case class Cloak(game: Game) extends Equipment {
  val aType: ArmorType = ArmorType.Leather
  var name = s"${aType.name} Cloak (+ ${aType.mod})"
  def use: Unit = {
    game.player.cloak = Some(this)
  }
}
