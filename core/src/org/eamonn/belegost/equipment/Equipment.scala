package org.eamonn.belegost.equipment

import org.eamonn.belegost.items.Item

trait Equipment extends Item {
  def use: Unit
  var name: String
  var mod: Int
}
sealed trait ArmorType {
  val name: String
  var mod: Int
}
object ArmorType {
  case object Leather extends ArmorType {
    val name = "Leather"
    var mod = 1
  }
}

sealed trait WeaponType {
  val name: String
  var diceNum: Int
  var diceAmount: Int
}
object WeaponType {
  case object ShortSword extends WeaponType {
    val name = "Shortsword"
    var diceNum = 1
    var diceAmount = 6
  }
}
