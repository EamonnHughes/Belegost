package org.eamonn.belegost.equipment

import org.eamonn.belegost.scenes.Game

case class BodyArmor(game: Game) extends Equipment {
  val aType: ArmorType = ArmorType.Leather
  var name = s"${aType.name} Armor (+ ${aType.mod})"
  def use: Unit = {
    game.player.bodyArmor = Some(this)
  }
}
