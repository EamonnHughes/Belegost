package org.eamonn.belegost.equipment

import org.eamonn.belegost.scenes.Game

case class Gloves(game: Game) extends Equipment {
  val aType: ArmorType = ArmorType.Leather
  var name = s"${aType.name} Gloves (+ ${aType.mod})"
  def use: Unit = {
    game.player.gloves = Some(this)
  }
}
