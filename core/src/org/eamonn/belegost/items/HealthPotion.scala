package org.eamonn.belegost.items

import org.eamonn.belegost.scenes.Game

case class HealthPotion(game: Game) extends Item {
  def use: Unit = {
    game.player.health = game.player.health + 4 min game.player.maxHealth
    val index = game.player.inventory.indexWhere({ case (count, item) =>
      if (item == EmptyBottle(game) && count < 40) true else false
    })
    if (index < 0) {

      game.player.inventory.addOne((1, EmptyBottle(game)))
    } else {
      game.player.inventory(index) =
        (game.player.inventory(index)._1 + 1, game.player.inventory(index)._2)
    }

  }
  var name = "Health Potion"

}
