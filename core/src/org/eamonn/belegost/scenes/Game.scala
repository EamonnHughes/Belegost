package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.util.Location
import org.eamonn.belegost.{
  Belegost,
  Click,
  Enemy,
  Entity,
  Geometry,
  Player,
  Room,
  Scene
}
import sun.security.ec.point.ProjectivePoint.Mutable

import scala.collection.mutable

class Game extends Scene {
  var keysPressed = List.empty[Int]
  var quit = false
  var roomList = List[Room](
    Room(Location(2, 2), 15, 15, this),
    Room(Location(17, 4), 3, 1, this),
    Room(Location(20, 2), 15, 15, this)
  )
  var player = Player(Location(3, 3), Location(3, 3), this)
  var enemies = List.empty[Enemy]
  var everything = List.empty[Entity]
  override def init(): InputAdapter = new GameControl(this)
  var tick = 0.2f
  override def update(delta: Float): Option[Scene] = {
    if (enemies.length < 1) {
      enemies = Enemy(Location(10, 10), Location(10, 10), this) :: enemies
    }
    if (player.moved) {
      tick -= delta
    } else { player.update(delta) }
    if (tick <= 0) {
      tick = 0.2f
      enemies.foreach(enemy => enemy.update(delta))

      player.moved = false
    }
    everything = player :: enemies
    if (quit) {
      Some(new Home)
    } else {
      None
    }
  }

  override def render(batch: PolygonSpriteBatch): Unit = {
    roomList.foreach(room => room.draw(batch))

    everything.foreach(thing => thing.draw(batch))
  }
  def findEntity(location: Location): Entity = { // nulls
    everything.find(thing => thing.location == location).orNull
  }
}
