package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.math.Matrix4
import org.eamonn.belegost.doers.{gameController, gameRenderer}
import org.eamonn.belegost.{Belegost, GameState, Geometry, Scene, Text}

class Game extends Scene {
  var state = new GameState
  Belegost.translationX = 0
  val matrix       = new Matrix4()
  Belegost.translationY = 0
  override def init(): InputAdapter = new GameControl(this)
  var tick = 0f
  override def update(delta: Float): Option[Scene] = {
    tick += delta
    if(tick >= .2f){
      gameController.updateOnTick(state)
      tick = 0f
    }
    Belegost.translationX = -(state.player.loc.x - (Geometry.ScreenWidth / Belegost.screenUnit) / 2).toInt
    Belegost.translationY = -(state.player.loc.y - (Geometry.ScreenHeight / Belegost.screenUnit) / 2).toInt
      None
  }

  override def render(batch: PolygonSpriteBatch): Unit = {
    batch.setTransformMatrix(
      matrix.setToTranslation(
        (Belegost.translationX * Belegost.screenUnit).floor,
        Belegost.translationY * Belegost.screenUnit,
        0
      )
    )
    gameRenderer.drawState(batch, state)
    batch.setTransformMatrix(matrix.idt())
  }
}
