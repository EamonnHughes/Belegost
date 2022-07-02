package org.eamonn.belegost.equipment

import org.eamonn.belegost.d
import org.eamonn.belegost.enchantments.{Enchantment, ExpMod}
import org.eamonn.belegost.scenes.Game

case class LightSource(game: Game, Type: lightType) extends Equipment {
  var mod = Type.mod

  var name = s"${Type.name} (${mod})"
  def use: Unit = {
    game.player.lightS = Some(this)
  }
  def decrease: Unit = {
    mod -= 1
  }
  def output: Int = {
    if (mod >= (Type.mod / 8) * 7) { 8 }
    else if (mod >= (Type.mod / 8) * 6) { 7 }
    else if (mod >= (Type.mod / 8) * 5) { 6 }
    else if (mod >= (Type.mod / 8) * 4) { 5 }
    else if (mod >= (Type.mod / 8) * 3) { 4 }
    else if (mod >= (Type.mod / 8) * 2) { 3 }
    else if (mod >= (Type.mod / 8)) { 2 }
    else {
      1
    }
  }

  var enchantments: List[Enchantment] = List(ExpMod(1.2f))
}
