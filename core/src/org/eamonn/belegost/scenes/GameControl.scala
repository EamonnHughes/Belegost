package org.eamonn.belegost.scenes

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import org.eamonn.belegost.{Belegost, Click, Geometry}
import org.eamonn.belegost.util.Location

class GameControl(game: Game) extends InputAdapter {
  override def touchDown(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean = {
    if (button == 1) {
      game.player.destination = Location(
        (screenX / Belegost.screenUnit).toInt,
        ((Geometry.ScreenHeight - screenY) / Belegost.screenUnit).toInt
      )
    }

    true
  }
  override def touchUp(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean = {
    true
  }

  override def keyDown(keycode: Int): Boolean = {
    game.keysPressed = keycode :: game.keysPressed
    if (keycode == Keys.I) {
      game.player.inInventory = !game.player.inInventory
    }

    true
  }

  override def keyUp(keycode: Int): Boolean = {
    game.keysPressed = game.keysPressed.filterNot(i => i == keycode)
    true
  }

}
