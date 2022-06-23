package org.eamonn.belegost.equipment

import org.eamonn.belegost.scenes.Game

case class BodyArmor(game: Game) extends Equipment {
  val aType: ArmorType = ArmorType.Leather
  var mod = aType.mod * 3
  var name = s"${aType.name} Armor (+ ${mod})"
  def use: Unit = {
    game.player.bodyArmor = Some(this)
  }
}
