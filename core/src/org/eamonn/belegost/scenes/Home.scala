package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.{Belegost, Geometry, Scene, Text}

class Home extends Scene {
  var gameBegin = false
  Belegost.translationX = 0
  Belegost.translationY = 0
  override def init(): InputAdapter = new HomeControl(this)

  override def update(delta: Float): Option[Scene] = {
    if (gameBegin) {
      Some(new CharCreation)
    } else { None }
  }

  override def render(batch: PolygonSpriteBatch): Unit = {

    Text.mediumFont.setColor(Color.WHITE)
    Text.mediumFont.draw(
      batch,
      "Press N: New Game",
      Geometry.ScreenWidth / 2 - Belegost.screenUnit * 6,
      Geometry.ScreenHeight / 2 - Belegost.screenUnit * 10
    )
  }
}
