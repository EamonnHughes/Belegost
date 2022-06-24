package org.eamonn.belegost

import com.badlogic.gdx.Input.Keys
import org.eamonn.belegost.scenes.Game

case class Spell() {}

sealed trait Spells {
  val name: String
  var cost: Int
  def use(game: Game): Unit
}
object Classes {
  case object FireBolt extends Spells {
    val name = "Fire Bolt"
    var cost = 1
    var damageDie = 1
    var damage = 8
    def use(game: Game): Unit = {
      var done = false
      if (game.keysPressed.contains(Keys.DOWN)) {
        done = true
        game.enemies.foreach(enemy => {
          if (
            enemy.location.x == game.player.location.x && enemy.location.y < game.player.location.y && enemy.location.y + 10 > game.player.location.y
          ) {
            enemy.health -= (d(
              damageDie,
              damage
            ) + ((game.player.intelligence - 10) / 2))
          }
        })
      } else if (game.keysPressed.contains(Keys.LEFT)) {
        done = true
        game.enemies.foreach(enemy => {
          if (
            enemy.location.y == game.player.location.y && enemy.location.x < game.player.location.x && enemy.location.x + 10 > game.player.location.x
          ) {
            enemy.health -= (d(
              damageDie,
              damage
            ) + ((game.player.intelligence - 10) / 2))
          }
        })

      } else if (game.keysPressed.contains(Keys.RIGHT)) {
        done = true
        game.enemies.foreach(enemy => {
          if (
            enemy.location.y == game.player.location.y && enemy.location.x > game.player.location.x && enemy.location.x - 10 < game.player.location.x
          ) {
            enemy.health -= (d(
              damageDie,
              damage
            ) + ((game.player.intelligence - 10) / 2))
          }
        })
      } else if (game.keysPressed.contains(Keys.UP)) {
        done = true
        game.enemies.foreach(enemy => {
          if (
            enemy.location.x == game.player.location.x && enemy.location.y > game.player.location.y && enemy.location.y - 10 < game.player.location.y
          ) {
            enemy.health -= (d(
              damageDie,
              damage
            ) + ((game.player.intelligence - 10) / 2))
          }
        })
      }
      if (done) {
        game.casting = game.casting.filterNot(spell => spell eq this)
      }
    }
  }
}
