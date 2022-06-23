package org.eamonn.belegost

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.equipment.{
  BodyArmor,
  Boots,
  Cloak,
  Equipment,
  Gloves,
  Helmet,
  Weapon
}
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
  var XPvalue = 0
  var nextXP = 10
  def speed = 2
  var level = 1
  var strength: Int = List(d(6), d(6), d(6), d(6)).sorted.tail.sum
  var dexterity: Int = List(d(6), d(6), d(6), d(6)).sorted.tail.sum
  var constitution: Int = List(d(6), d(6), d(6), d(6)).sorted.tail.sum
  var intelligence: Int = List(d(6), d(6), d(6), d(6)).sorted.tail.sum
  var wisdom: Int = List(d(6), d(6), d(6), d(6)).sorted.tail.sum
  var charisma: Int = List(d(6), d(6), d(6), d(6)).sorted.tail.sum
  var baseAC = 10
  def armorClass = baseAC + acMod + ((dexterity - 10) / 2)
  var weapon: Option[Weapon] = None
  def acMod: Int = {
    var eqBonus = 0
    equipped.foreach(equip => {
      eqBonus += equip.mod
    })
    eqBonus
  }
  var moved = false
  var maxHealth = health
  var inventory = mutable.ListBuffer[(Int, Item)](
    (40, HealthPotion(game)),
    (1, Helmet(game)),
    (1, BodyArmor(game)),
    (1, Gloves(game)),
    (1, Boots(game)),
    (1, Cloak(game)),
    (1, Weapon(game))
  )
  def equipped: List[Equipment] = {
    helmet.toList ::: bodyArmor.toList ::: gloves.toList ::: boots.toList ::: cloak.toList
  }
  var currentInventoryItem = 0
  var helmet: Option[Helmet] = None
  var bodyArmor: Option[BodyArmor] = None
  var gloves: Option[Gloves] = None
  var boots: Option[Boots] = None
  var cloak: Option[Cloak] = None
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
  var inInventory = false
  def update(delta: Float): Unit = {
    if (XPvalue >= nextXP) {
      level += 1
      nextXP += nextXP * 2
    }
    maxHealth = (level * 10) + ((constitution - 10) / 2)
    if (
      game.keysPressed
        .contains(Keys.SPACE)
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
      var attackedEnemy = false
      destination = computeDestination
      navTo
      for {
        path <- pathToDest
      } {
        val nextLoc = path.getHead
        game.enemies.foreach(enemy => {
          if (enemy.location == nextLoc) {
            for (i <- 0 until speed) {
              weapon.foreach(weapon =>
                if (d(20) + weapon.mod > enemy.armorClass) {
                  enemy.health -= d(
                    weapon.dNum,
                    weapon.dAmt
                  ) + weapon.mod + ((strength - 10) / 2)
                }
              )

              if (weapon.isEmpty)
                if (d(20) > enemy.armorClass)
                  enemy.health -= d(2) + ((strength - 10) / 2)
            }
            attackedEnemy = true
            destination = location
          }
        })
        if (!attackedEnemy) {
          location = nextLoc

          pathToDest = path.tail
        }
        if (location != prevdest || attackedEnemy) moved = true
        attackedEnemy = false
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
