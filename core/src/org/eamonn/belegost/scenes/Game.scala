package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.util.Vec2
import org.eamonn.belegost.{Geometry, Scene, Belegost}

import scala.collection.mutable

class Game extends Scene {
  var quit = false
  override def init(): InputAdapter = new GameControl(this)

  override def update(delta: Float): Option[Scene] = {
    if (quit) {
      Some(new Home)
    } else {
      None
    }

  }

  override def render(batch: PolygonSpriteBatch): Unit = {}
}
