package org.eamonn.belegost

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.scenes.Game
import org.eamonn.belegost.util.{Delta, Location}
import org.graalvm.compiler.word.Word

import scala.collection.mutable

case class Player(
    var location: Location,
    var destination: Location,
    var game: Game,
    var health: Int
) extends Entity {
  var moved = false
  var maxHealth = health
  var inventory = mutable.ListBuffer[(Int, Item)](
    (5, HealthPotion(game)),
    (4, HealthPotion(game)),
    (3, HealthPotion(game))
  )
  var currentInventoryItem = 0

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
    if (inInventory) {
      batch.setColor(Color.WHITE)
      batch.draw(
        Belegost.Square,
        Belegost.screenUnit,
        Geometry.ScreenHeight - Belegost.screenUnit * ((inventory.length + 1) max 2),
        Geometry.ScreenWidth - Belegost.screenUnit * 2,
        Belegost.screenUnit * (inventory.length max 1)
      )
      if (inventory.nonEmpty) {
        batch.setColor(Color.RED)
        batch.draw(
          Belegost.Square,
          Belegost.screenUnit,
          Geometry.ScreenHeight - Belegost.screenUnit * (currentInventoryItem + 2),
          Geometry.ScreenWidth - Belegost.screenUnit * 2,
          Belegost.screenUnit
        )
      }
      inventory.zipWithIndex.foreach({ case (item, index) =>
        Text.smallFont.setColor(Color.BLACK)
        Text.smallFont.draw(
          batch,
          "x" + item._1 + "   " + item._2.name,
          Belegost.screenUnit * 2,
          Geometry.ScreenHeight - Belegost.screenUnit * (index + 1) + Text.smallFont.getDescent
        )
      })
    }
  }
  var inInventory = false
  def update(delta: Float): Unit = {
    game.pickups.foreach(pUp => {
      if (location == pUp.location) {
        game.pickups = game.pickups.filterNot(f => f eq pUp)
        inventory.addOne((1, pUp.corresponding))
      }
    })
    val prevdest = location
    if (
      !moved && game.keysPressed.contains(
        Keys.U
      ) && inInventory && currentInventoryItem < inventory.length
    ) {
      val (count, item) = inventory(currentInventoryItem)
      item.use
      if (count > 1) {
        inventory.update(currentInventoryItem, (count - 1, item))
      } else {
        inventory.remove(currentInventoryItem)
      }
      moved = true
    }
    if (!moved) {
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

  }
  def navTo: Unit = {
    if (game.roomList.exists(room => room.isInRoom(destination))) {
      pathToDest = Navigation
        .findPath(destination, location, game)
        .flatMap(path => path.tail)
    } else { destination = location }
  }
  def computeDestination: Location = {
    if (!inInventory) {
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
    } else {
      destination
    }
  }

}
