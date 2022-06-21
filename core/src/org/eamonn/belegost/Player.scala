package org.eamonn.belegost

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.equipment.Helmet
import org.eamonn.belegost.items.{EmptyBottle, HealthPotion, Item}
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
    (40, HealthPotion(game))
  )
  var currentInventoryItem = 0
  var helmet: Option[Helmet] = None
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
      Menu.draw(batch, this)
    }
  }
  var inInventory = false
  def update(delta: Float): Unit = {
    if (
      game.keysPressed
        .contains(Keys.SPACE) || game.keysPressed.contains(Keys.ENTER)
    ) { moved = true }
    game.pickups.foreach(pUp => {
      if (location == pUp.location) {
        val index = game.player.inventory.indexWhere({ case (count, item) =>
          if (item == pUp.corresponding && count < 40) true else false
        })
        if (index < 0) {

          game.player.inventory.addOne((1, pUp.corresponding))
        } else {
          game.player.inventory(index) = (
            game.player.inventory(index)._1 + 1,
            game.player.inventory(index)._2
          )
        }
        game.pickups = game.pickups.filterNot(f => f eq pUp)

      }
    })
    val prevdest = location
    if (
      !moved && game.keysPressed.contains(
        Keys.ENTER
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
            enemy.health -= d(4)
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
