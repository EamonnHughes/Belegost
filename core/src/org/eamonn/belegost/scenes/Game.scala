package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.util.Vec2
import org.eamonn.belegost.{Belegost, Geometry, Player, Scene}
import sun.security.ec.point.ProjectivePoint.Mutable

import scala.collection.mutable

class Game extends Scene {
  var keysPressed = List.empty[Int]
  var quit = false
  var player = Player(Vec2(3, 3), Vec2(3, 3), this)
  override def init(): InputAdapter = new GameControl(this)

  override def update(delta: Float): Option[Scene] = {
    player.update(delta)
    if (quit) {
      Some(new Home)
    } else {
      None
    }
  }

  override def render(batch: PolygonSpriteBatch): Unit = {

    player.draw(batch)
  }
}
