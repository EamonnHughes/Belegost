package org.eamonn.belegost.items

import org.eamonn.belegost.scenes.Game

case class EmptyBottle(game: Game) extends Item {
  def use: Unit = {}
  var name = "Empty Potion Bottle"

}
