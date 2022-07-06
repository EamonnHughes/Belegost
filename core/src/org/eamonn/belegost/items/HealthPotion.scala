package org.eamonn.belegost.items

import org.eamonn.belegost._
import org.eamonn.belegost.scenes.Game

case class HealthPotion(game: Game) extends Item {
  def use: Unit = {
    game.player.health = game.player.health + d(2, 3) min game.player.maxHealth

  }
  var name = "Health Potion"

}
