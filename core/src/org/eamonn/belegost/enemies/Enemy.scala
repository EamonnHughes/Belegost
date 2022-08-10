package org.eamonn.belegost.enemies

import org.eamonn.belegost.Entity

trait Enemy extends Entity {
  def attack(): Unit
  def dropLoot(): Unit
  var name: String
}
