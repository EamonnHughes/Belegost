package org.eamonn.belegost.equipment

import org.eamonn.belegost.scenes.Game

case class Boots(game: Game) extends Equipment {
  val aType: ArmorType = ArmorType.Leather
  var name = s"${aType.name} Boots (+ ${aType.mod})"
  def use: Unit = {
    game.player.boots = Some(this)
  }
}
