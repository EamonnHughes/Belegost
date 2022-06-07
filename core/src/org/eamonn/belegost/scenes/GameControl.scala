package org.eamonn.belegost.scenes

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import org.eamonn.belegost.{Click, Geometry}
import org.eamonn.belegost.util.Location

class GameControl(game: Game) extends InputAdapter {
  override def touchDown(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean = {
    game.mouseButtonsPressed.put(
      pointer,
      Click(
        button,
        Location(screenX, Geometry.ScreenHeight - screenY),
        Location(screenX, Geometry.ScreenHeight - screenY)
      )
    )

    true
  }
  override def touchUp(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean = {
    game.mouseButtonsPressed.remove(pointer)
    true
  }

  override def keyDown(keycode: Int): Boolean = {
    game.keysPressed = keycode :: game.keysPressed

    true
  }

  override def keyUp(keycode: Int): Boolean = {
    game.keysPressed = game.keysPressed.filterNot(i => i == keycode)
    true
  }

}
