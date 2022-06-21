package org.eamonn.belegost.equipment

import org.eamonn.belegost.scenes.Game

case class Helmet(game: Game) extends Equipment {

  val aType: ArmorType = ArmorType.Leather
  var name = s"${aType.name} Cap (+ ${aType.mod})"
  def use: Unit = {
    game.player.helmet = Some(this)
  }
}
