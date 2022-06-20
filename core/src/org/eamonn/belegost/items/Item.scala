package org.eamonn.belegost.items

import org.eamonn.belegost.scenes.Game

trait Item {
  def use: Unit
  var name: String
  val game: Game
}
