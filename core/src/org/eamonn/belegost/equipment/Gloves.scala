package org.eamonn.belegost.equipment

import org.eamonn.belegost.scenes.Game

case class Gloves(game: Game, aType: ArmorType) extends Equipment {
  var mod = aType.mod

  var name = s"${aType.name} Gloves (+ ${mod})"
  def use: Unit = {
    game.player.gloves = Some(this)
  }
}
