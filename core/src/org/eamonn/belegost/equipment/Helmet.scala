package org.eamonn.belegost.equipment

import org.eamonn.belegost.scenes.Game

case class Helmet(game: Game) extends Equipment {

  val modifier = aType.mod
  val aType = ArmorType.LeatherSkullcap
  var name = s"${aType.name} (+ $modifier)"
  def use: Unit = {
    game.player.helmet = Some(this)
  }
}
