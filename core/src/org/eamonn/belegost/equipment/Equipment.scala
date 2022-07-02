package org.eamonn.belegost.equipment

import org.eamonn.belegost.enchantments.Enchantment
import org.eamonn.belegost.items.Item

trait Equipment extends Item {
  def use: Unit
  var name: String
  var mod: Float
  var enchantments: List[Enchantment]
}
sealed trait ArmorType {
  val name: String
  var mod: Float
}
object ArmorType {
  case object Leather extends ArmorType {
    val name = "Leather"
    var mod = 1
  }
  case object Cloth extends ArmorType {
    val name = "Cloth"
    var mod = 0.5f
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
  case object Staff extends WeaponType {
    val name = "Staff"
    var diceNum = 2
    var diceAmount = 2
  }
}

sealed trait lightType {
  val name: String
  var mod: Float
}
object lightType {
  case object Lamp extends lightType {
    val name = "Lamp"
    var mod = 2000f
  }
}
