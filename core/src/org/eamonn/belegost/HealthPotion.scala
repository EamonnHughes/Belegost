package org.eamonn.belegost

import org.eamonn.belegost.scenes.Game

case class HealthPotion(game: Game) extends Item {
  def use: Unit = {
    game.player.health = game.player.health + 4 min game.player.maxHealth
  }
  var name = "Health Potion"

}
