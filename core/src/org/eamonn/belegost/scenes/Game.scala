package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.util.Location
import org.eamonn.belegost.{Belegost, Geometry, Player, Room, Scene}
import sun.security.ec.point.ProjectivePoint.Mutable

import scala.collection.mutable

class Game extends Scene {
  var keysPressed = List.empty[Int]
  var quit = false
  var roomList = List[Room](Room(Location(2, 2), 5, 5))
  var player = Player(Location(3, 3), Location(3, 3), this)
  var everything = List.empty[Entity]
  override def init(): InputAdapter = new GameControl(this)

  override def update(delta: Float): Option[Scene] = {
    player.update(delta)
    everything = List(player)
    if (quit) {
      Some(new Home)
    } else {
      None
    }
  }

  override def render(batch: PolygonSpriteBatch): Unit = {

    player.draw(batch)
  }
  def findEntity(location: Location): Entity = { // nulls
    everything.find(thing => thing.location == location).orNull
  }
}
