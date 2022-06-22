package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.items.{HealthPickup, PickUp}
import org.eamonn.belegost.util.Location
import org.eamonn.belegost.{
  Belegost,
  Click,
  Enemy,
  Entity,
  Geometry,
  Menu,
  Player,
  Room,
  Scene
}
import sun.security.ec.point.ProjectivePoint.Mutable

import scala.collection.mutable

class Game extends Scene {
  var changingTranslationX = false
  var changingTranslationY = false
  var keysPressed = List.empty[Int]
  var quit = false
  var pickups = List[PickUp](HealthPickup(Location(24, 5), this))
  var roomList = List[Room](
    Room(Location(-15, -15), 50, 50, this)
  )
  var player = Player(Location(3, 3), Location(3, 3), this, 10)
  var enemies = List.empty[Enemy]
  var everything = List.empty[Entity]
  override def init(): InputAdapter = new GameControl(this)
  var tick = 0.2f
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

  override def update(delta: Float): Option[Scene] = {
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
      if (enemies.length < 1) {
        enemies = Enemy(Location(13, 13), Location(13, 13), this, 10) :: Enemy(
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
        tick = 0.2f
        enemies.foreach(enemy => enemy.update(delta))

        player.moved = false
      }
    }
    everything = player :: enemies
    if (quit || player.health <= 0) {
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

    everything.foreach(thing => thing.draw(batch))
    pickups.foreach(pUp => pUp.draw(batch))
    if (player.inInventory) {
      Menu.drawInventory(batch, player)
    }
    Menu.drawStats(batch, player)
  }
  def findEntity(location: Location): Entity = { // nulls
    everything.find(thing => thing.location == location).orNull
  }
}
