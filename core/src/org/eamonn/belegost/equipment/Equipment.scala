package org.eamonn.belegost.equipment

import org.eamonn.belegost.items.Item

trait Equipment extends Item {
  val modifier: Int
  def use: Unit
  var name: String
}
sealed trait ArmorType {
  val name: String
  var mod: Int
}
object ArmorType {
  case object LeatherSkullcap extends ArmorType {
    val name = "Leather Skullcap"
    var mod = 1
  }
}
