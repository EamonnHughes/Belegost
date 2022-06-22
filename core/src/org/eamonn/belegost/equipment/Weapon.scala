package org.eamonn.belegost.equipment

import org.eamonn.belegost.d
import org.eamonn.belegost.scenes.Game

case class Weapon(game: Game) extends Equipment {

  val weaponType: WeaponType = WeaponType.ShortSword
  var mod = (d(4) - 1)
  var dNum = weaponType.diceNum
  var dAmt = weaponType.diceAmount

  var name = s"${weaponType.name} (${dNum}d$dAmt) (+ ${mod})"
  def use: Unit = {
    game.player.weapon = Some(this)
  }
}
