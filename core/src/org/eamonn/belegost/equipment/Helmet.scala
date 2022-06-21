package org.eamonn.belegost.equipment

import org.eamonn.belegost.scenes.Game

case class Helmet(game: Game) extends Equipment {

  val aType: ArmorType = ArmorType.LeatherSkullcap
  var name = s"${aType.name} (+ ${aType.mod})"
  def use: Unit = {
    game.player.helmet = Some(this)
  }
}
