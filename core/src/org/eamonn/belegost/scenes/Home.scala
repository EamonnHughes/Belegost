package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.Scene

class Home extends Scene {
  var gameBegin = false

  override def init(): InputAdapter = new HomeControl(this)

  override def update(delta: Float): Option[Scene] = {
    if (gameBegin) {
      Some(new Game)
    } else { None }
  }

  override def render(batch: PolygonSpriteBatch): Unit = {}
}
