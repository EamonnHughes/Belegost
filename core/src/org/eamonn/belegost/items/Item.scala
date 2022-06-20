package org.eamonn.belegost.items

trait Item {
  def use: Unit
  var name: String
}
