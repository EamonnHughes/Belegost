package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.util.Location
import org.eamonn.belegost.{
  Belegost,
  Click,
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
  var roomList = List[Room](Room(Location(2, 2), 5, 5))
  var player = Player(Location(3, 3), Location(3, 3), this)
  var everything = List.empty[Entity]
  override def init(): InputAdapter = new GameControl(this)
  var tick = 0.2f
  override def update(delta: Float): Option[Scene] = {
    tick -= delta
    if (tick <= 0) {
      player.update(delta)
      everything = List(player)
      tick = 0.2f
    }
    if (quit) {
      Some(new Home)
    } else {
      None
    }
  }

  override def render(batch: PolygonSpriteBatch): Unit = {
    roomList.foreach(room => room.draw(batch))

    player.draw(batch)
  }
  def findEntity(location: Location): Entity = { // nulls
    everything.find(thing => thing.location == location).orNull
  }
}
