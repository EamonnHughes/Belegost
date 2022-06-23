package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.{Belegost, Geometry, Scene, Text}

class CharCreation extends Scene {
  var race = false
  var pClass = false
  var done = false
  var quit = false

  override def init(): InputAdapter = new CharControl(this)

  override def update(delta: Float): Option[Scene] = {
    if (done) Some(new Game) else if (quit) Some(new Home) else None
  }

  override def render(batch: PolygonSpriteBatch): Unit = {
    if (!race) {
      Text.mediumFont.setColor(Color.WHITE)
      Text.mediumFont.draw(
        batch,
        "a: Human",
        Belegost.screenUnit,
        Geometry.ScreenHeight - Belegost.screenUnit
      )
    } else if (!pClass) {

      Text.mediumFont.setColor(Color.WHITE)
      Text.mediumFont.draw(
        batch,
        "a: Fighter",
        Belegost.screenUnit,
        Geometry.ScreenHeight - Belegost.screenUnit
      )
    } else {

      Text.mediumFont.setColor(Color.WHITE)
      Text.mediumFont.draw(
        batch,
        "y: Begin \n n: Quit",
        Belegost.screenUnit,
        Geometry.ScreenHeight - Belegost.screenUnit
      )
    }
  }
}
