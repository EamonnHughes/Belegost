package org.eamonn.belegost.equipment

import org.eamonn.belegost.enchantments.{Enchantment, StatEnchantment}
import org.eamonn.belegost.scenes.Game

case class BodyArmor(game: Game, aType: ArmorType) extends Equipment {
  var mod = aType.mod * 2
  var name = s"${aType.name} Armor (+ ${mod})"
  def use: Unit = {
    game.player.bodyArmor = Some(this)
  }
  var enchantments: List[Enchantment] = List(
    StatEnchantment(List(1, 1, 1, 0, 0, 0))
  )
}
