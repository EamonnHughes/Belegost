package org.eamonn.belegost.equipment

import org.eamonn.belegost.enchantments.{Enchantment, ExpMod}
import org.eamonn.belegost.scenes.Game

case class Helmet(game: Game, aType: ArmorType) extends Equipment {
  var mod = aType.mod * 1

  var name = s"${aType.name} Cap (+ ${mod})"
  def use: Unit = {
    game.player.helmet = Some(this)
  }

  var enchantments: List[Enchantment] = List(ExpMod(1.2f))
}
