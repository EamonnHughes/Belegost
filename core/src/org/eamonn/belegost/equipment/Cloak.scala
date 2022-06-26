package org.eamonn.belegost.equipment

import org.eamonn.belegost.scenes.Game

case class Cloak(game: Game, aType: ArmorType) extends Equipment {
  var mod = aType.mod

  var name = s"${aType.name}-Reinforced Cloak (+ ${mod})"
  def use: Unit = {
    game.player.cloak = Some(this)
  }
}
