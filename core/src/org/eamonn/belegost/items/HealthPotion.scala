package org.eamonn.belegost.items

import org.eamonn.belegost.scenes.Game

case class HealthPotion(game: Game) extends Item {
  def use: Unit = {
    game.player.health = game.player.health + 4 min game.player.maxHealth
    game.player.inventory.addOne((1, EmptyBottle(game)))
  }
  var name = "Health Potion"

}
