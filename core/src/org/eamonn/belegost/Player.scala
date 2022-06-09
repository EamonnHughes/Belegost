package org.eamonn.belegost

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.scenes.Game
import org.eamonn.belegost.util.{Delta, Location}
import org.graalvm.compiler.word.Word

case class Player(
    var location: Location,
    var destination: Location,
    var game: Game,
    var health: Int
) extends Entity {
  var moved = false
  var maxHealth = health

  var pathToDest = Option.empty[Path]
  var clickedDest: Location = location
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.setColor(Color.WHITE)
    batch.draw(
      Belegost.Player,
      location.x * Belegost.screenUnit,
      location.y * Belegost.screenUnit,
      Belegost.screenUnit,
      Belegost.screenUnit
    )
    batch.setColor(1, 0, 0, 0.5f)
    batch.draw(
      Belegost.Square,
      location.x * Belegost.screenUnit,
      (location.y + 1) * Belegost.screenUnit,
      (health.toFloat / maxHealth.toFloat) * Belegost.screenUnit,
      Belegost.screenUnit / 10
    )
  }
  def update(delta: Float): Unit = {
    val prevdest = location

    destination = computeDestination
    navTo

    for {
      path <- pathToDest
    } {
      val nextLoc = path.getHead
      game.enemies.foreach(enemy => {
        if (enemy.location == nextLoc) {
          enemy.health -= 1
          moved = true
          destination = location
        } else {

          location = nextLoc

          pathToDest = path.tail
        }
      })
      if (location != prevdest) moved = true
    }
  }
  def navTo: Unit = {
    if (game.roomList.exists(room => room.isInRoom(destination))) {
      pathToDest = Navigation
        .findPath(destination, location, game)
        .flatMap(path => path.tail)
    } else { destination = location }
  }
  def computeDestination: Location = {

    if (game.keysPressed.contains(19)) {
      if (game.keysPressed.contains(22)) {
        location + Delta(1, 1)
      } else if (game.keysPressed.contains(21)) {
        location + Delta(-1, 1)
      } else {
        location + Delta(0, 1)
      }

    } else if (game.keysPressed.contains(20)) {
      if (game.keysPressed.contains(22)) {
        location + Delta(1, -1)
      } else if (game.keysPressed.contains(21)) {
        location + Delta(-1, -1)
      } else {
        location + Delta(0, -1)
      }

    } else if (game.keysPressed.contains(21)) {
      location + (Delta(-1, 0))

    } else if (game.keysPressed.contains(22)) {
      location + (Delta(1, 0))

    } else {
      destination
    }
  }

}
