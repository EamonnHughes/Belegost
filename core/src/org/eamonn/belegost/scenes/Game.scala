package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost
import org.eamonn.belegost.enchantments.{ExpMod, StatEnchantment}
import org.eamonn.belegost.enemies.Orc
import org.eamonn.belegost.items.{HealthPickup, Money, PickUp}
import org.eamonn.belegost.util.Location
import cats.instances._
import cats.implicits._
import org.eamonn.belegost.{
  Belegost,
  Click,
  Entity,
  Geometry,
  Menu,
  Player,
  Room,
  Scene,
  Spell,
  Spells,
  d
}
import sun.jvm.hotspot.gc.z.ZGlobals
import sun.security.ec.point.ProjectivePoint.Mutable

import scala.collection.{GenMap, mutable}

class Game(
    val pRace: playerRace,
    val pClass: playerClass,
    val sStats: List[Int]
) extends Scene {
  var mx = 0
  var my = 0
  val visited = mutable.Map.empty[Location, Float]
  val visible = mutable.Map.empty[Location, Float]
  var changingTranslationX = false
  var MoneyInDungeon: List[Money] = List(Money(Location(20, 20)))
  var changingTranslationY = false
  var keysPressed = List.empty[Int]
  var quit = false
  var casting = List.empty[Spells]
  var pickups = List[PickUp](HealthPickup(Location(24, 5), this))
  var roomList = List[Room](
    Room(Location(0, 0), 25, 25, this),
    Room(Location(25, 15), 1, 1, this),
    Room(Location(26, 0), 20, 20, this)
  )
  var healthTimer = 5
  var spellSlotTimer = 5
  var player = Player(Location(3, 3), Location(3, 3), this)
  var enemies = List.empty[Entity]
  var everything = List.empty[Entity]

  override def init(): InputAdapter = new GameControl(this)

  var tick = 0.13f

  def changeX(delta: Float): Unit = {
    if (
      Belegost.translationX <
        ((Geometry.ScreenWidth / 2 / Belegost.screenUnit).toInt - player.location.x)
    ) {
      Belegost.translationX = (Belegost.translationX + 1).toInt
    } else if (
      Belegost.translationX >
        ((Geometry.ScreenWidth / 2 / Belegost.screenUnit).toInt - player.location.x)
    ) {
      Belegost.translationX = (Belegost.translationX - 1).toInt
    } else {
      changingTranslationX = false
    }
  }

  def changeY(delta: Float): Unit = {
    if (
      Belegost.translationY <
        ((Geometry.ScreenHeight / 2 / Belegost.screenUnit).toInt - player.location.y)
    ) {
      Belegost.translationY = (Belegost.translationY + 1).toInt
    } else if (
      Belegost.translationY >
        ((Geometry.ScreenHeight / 2 / Belegost.screenUnit).toInt - player.location.y)
    ) {
      Belegost.translationY = (Belegost.translationY - 1).toInt
    } else {
      changingTranslationY = false
    }

  }

  var eeEs = 1f

  override def update(delta: Float): Option[Scene] = {
    player.invMenu.update()
    player.equipMenu.update()
    player.equipMenuUpdate()

    val statEnchants = player.equipped
      .flatMap(_.enchantments)
      .collect({ case se: StatEnchantment =>
        se.mod
      })
    val expEnchants = player.equipped
      .flatMap(_.enchantments)
      .collect({ case se: ExpMod =>
        se.mod
      })
    eeEs = 1
    expEnchants.foreach(eeCh => {
      eeEs = eeEs * eeCh
    })
    player.enchMod = statEnchants.foldLeft(List(0, 0, 0, 0, 0, 0))((l1, l2) =>
      l1.alignCombine(l2)
    )
    visible.clear()
    visited.put(player.location, 1f)

    val lightDistSquared = player.lightDist * player.lightDist

    visible.put(player.location, 1f)
    for (
      xx <-
        0 until (Geometry.ScreenWidth / Belegost.screenUnit).toInt
    ) {
      for (
        yy <-
          0 until (Geometry.ScreenHeight / Belegost.screenUnit).toInt
      ) {
        val x = xx - Belegost.translationX
        val y = yy - Belegost.translationY
        val loc = Location(x, y)
        var nx: Int = player.location.x
        var ny: Int = player.location.y

        val dx = x - player.location.x
        val dy = y - player.location.y
        var primary = 0
        var dxp = false
        if (Math.abs(dx) > Math.abs(dy)) {
          primary = dx
          dxp = true
        } else {
          primary = dy
        }
        val dist = dx * dx + dy * dy
        if (dist <= lightDistSquared) {
          var ok = true
          for (changeP <- 1 until Math.abs(primary)) {
            if (dxp) {
              nx = player.location.x + (changeP * Math.signum(dx)).toInt
              ny = player.location.y + (Math
                .abs(changeP * dy / dx) * Math.signum(dy)).toInt
            } else {
              ny = player.location.y + (changeP * Math.signum(dy)).toInt
              nx = player.location.x + (Math
                .abs(changeP * dx / dy) * Math.signum(dx)).toInt
            }
            if (roomList.forall(room => !room.isInRoom(Location(nx, ny)))) {
              ok = false
            }
          }
          if (ok) {
            val light = dist.toFloat / lightDistSquared

            visited.updateWith(loc)((current) =>
              Some(light min current.getOrElse(1f))
            )

            visible.put(
              loc,
              light
            )
          }
        }
      }

    }

    if (
      (
        player.location.x < -Belegost.translationX + 7 || player.location.x > -Belegost.translationX + (Geometry.ScreenWidth / Belegost.screenUnit) - 7
      ) && !changingTranslationX
    ) {
      changingTranslationX = true
    }
    if (
      (
        player.location.y < -Belegost.translationY + 7 || player.location.y > -Belegost.translationY + (Geometry.ScreenHeight / Belegost.screenUnit) - 7
      ) && !changingTranslationY
    ) {
      changingTranslationY = true
    }
    if (changingTranslationX) {
      changeX(delta)
    }
    if (changingTranslationY) {
      changeY(delta)
    }
    if (!changingTranslationX && !changingTranslationY) {
      if (casting.nonEmpty) {
        casting.head.use(this)
      } else {
        if (enemies.length < 1) {
          enemies = Orc(
            Location(13, 13),
            Location(13, 13),
            this,
            10
          ) :: belegost.enemies.Orc(
            Location(10, 10),
            Location(10, 10),
            this,
            10
          ) :: enemies
        }
        if (player.moved) {
          tick -= delta
        } else {
          player.update(delta)
        }
        if (tick <= 0) {
          tick = 0.13f
          enemies.foreach(enemy => enemy.update(delta))
          player.lightS.foreach(lS => lS.decrease)
          if (player.health < player.maxHealth) {
            healthTimer -= 1
            if (healthTimer <= 0) {
              player.health = (player.health + 1) min player.maxHealth
              healthTimer = 5
            }
          }
          if (player.spellSlots < player.maxSS) {
            spellSlotTimer -= 1
            if (spellSlotTimer <= 0) {
              player.spellSlots = (player.spellSlots + 1) min player.maxSS
              spellSlotTimer = 5
            }
          }

          player.moved = false
        }
      }
    }
    everything = player :: enemies
    if (player.XPvalue >= player.nextXP) {
      Some(new LevelUp(this))
    } else if (quit || player.health <= 0) {
      Some(new Home)
    } else {
      None
    }
  }

  override def render(batch: PolygonSpriteBatch): Unit = {
    batch.getTransformMatrix.setToTranslation(
      Belegost.translationX * Belegost.screenUnit,
      Belegost.translationY * Belegost.screenUnit,
      0
    )
    roomList.foreach(room => room.draw(batch))
    MoneyInDungeon.foreach(Money => Money.draw(batch))
    pickups.foreach(pickup => pickup.draw(batch))

    everything.foreach(thing => {
      if (visible.contains(thing.location))
        thing.draw(batch)
    })

    for (
      xx <-
        0 until (Geometry.ScreenWidth / Belegost.screenUnit).toInt
    ) {
      for (
        yy <-
          0 until (Geometry.ScreenHeight / Belegost.screenUnit).toInt
      ) {
        val x = xx - Belegost.translationX
        val y = yy - Belegost.translationY

        val loc = Location(x, y)
        val alpha =
          visible.getOrElse(
            loc,
            1f
          ) min (visited.getOrElse(loc, 1f) max 0.7f)
        batch.setColor(0, 0, 0, alpha)

        batch.draw(
          Belegost.Square,
          x * Belegost.screenUnit,
          y * Belegost.screenUnit,
          Belegost.screenUnit,
          Belegost.screenUnit
        )
      }
    }

    if (player.inSpellList) {
      Menu.drawSpellBook(batch, player)
    } else if (player.inInventory) {
      Menu.drawInventory(batch, player)
    } else if (player.inEquip) {
      Menu.drawEquipment(batch, player)
    }

    Menu.drawStats(batch, player)

  }

  def findEntity(location: Location): Entity = { // nulls
    everything.find(thing => thing.location == location).orNull
  }
}
