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
import org.eamonn.belegost.scenes.{Classes, Game, Races}
import org.eamonn.belegost.util.{Delta, Location}
import org.graalvm.compiler.word.Word

import scala.collection.mutable

case class Player(
    var location: Location,
    var destination: Location,
    var game: Game,
    var health: Int
) extends Entity {
  def playerRace = game.pRace
  def playerClass = game.pClass
  var XPvalue = 0
  var nextXP: Int = (10 * playerRace.xpMod).toInt
  var money = 0
  def speed: Int = 1 max ((dexterity - 10) / 2)
  var level = 1
  var stats = game.sStats
  var strength: Int = stats.head + playerRace.statBonus(0)
  stats = stats.tail
  var dexterity: Int = stats.head + playerRace.statBonus(1)
  stats = stats.tail
  var constitution: Int = stats.head + playerRace.statBonus(2)
  stats = stats.tail
  var intelligence: Int = stats.head + playerRace.statBonus(3)
  stats = stats.tail
  var wisdom: Int = stats.head + playerRace.statBonus(4)
  stats = stats.tail
  var charisma: Int = stats.head + playerRace.statBonus(5)
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
    if (playerRace == Races.Human && playerClass == Classes.Fighter) {
      batch.draw(
        Belegost.HumanFighterPC,
        location.x * Belegost.screenUnit,
        location.y * Belegost.screenUnit,
        Belegost.screenUnit,
        Belegost.screenUnit
      )
    }
    if (playerRace == Races.Dwarf && playerClass == Classes.Fighter) {
      batch.draw(
        Belegost.DwarfFighterPC,
        location.x * Belegost.screenUnit,
        location.y * Belegost.screenUnit,
        Belegost.screenUnit,
        Belegost.screenUnit
      )
    }

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
    game.MoneyInDungeon.foreach(pUp => {
      if (location == pUp.location) {
        money += pUp.amount
        game.MoneyInDungeon = game.MoneyInDungeon.filterNot(mId => mId eq pUp)
      }
    })
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
              weapon.foreach(weapon => {
                val roll = d(20)
                if (roll == 20) {
                  enemy.health -= (d(
                    weapon.dNum,
                    weapon.dAmt
                  ) + weapon.mod + ((strength - 10) / 2)) * 2
                } else if (roll + weapon.mod > enemy.armorClass) {

                  enemy.health -= d(
                    weapon.dNum,
                    weapon.dAmt
                  ) + weapon.mod + ((strength - 10) / 2)
                }
              })

              if (weapon.isEmpty) {
                val roll = d(20)

                if (roll == 20) {
                  enemy.health -= (d(2) + ((strength - 10) / 2)) * 2
                } else if (roll > enemy.armorClass)
                  enemy.health -= d(2) + ((strength - 10) / 2)
              }
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
