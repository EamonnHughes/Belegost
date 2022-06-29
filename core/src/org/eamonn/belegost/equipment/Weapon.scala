package org.eamonn.belegost.equipment

import org.eamonn.belegost.d
import org.eamonn.belegost.enchantments.Enchantment
import org.eamonn.belegost.scenes.Game

case class Weapon(game: Game, weapType: WeaponType) extends Equipment {

  var mod = (d(4) - 1 max 0)
  var dNum = weapType.diceNum
  var dAmt = weapType.diceAmount

  var name = s"${weapType.name} (${dNum}d$dAmt) (+ ${mod})"
  def use: Unit = {
    game.player.weapon = Some(this)
  }

  var enchantments: List[Enchantment] = List.empty
}
