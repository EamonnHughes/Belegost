package org.eamonn.belegost.equipment

import org.eamonn.belegost.enchantments.{Enchantment, StatEnchantment}
import org.eamonn.belegost.scenes.Game

case class Boots(game: Game, aType: ArmorType) extends Equipment {
  var mod = aType.mod

  var name = s"${aType.name} Boots (+ ${mod})"
  def use: Unit = {
    game.player.boots = Some(this)
  }
  var enchantments: List[Enchantment] = List.empty

}
