package org.eamonn.belegost.scenes

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
class GameControl(game: Game) extends InputAdapter {
  override def touchDown(
                          screenX: Int,
                          screenY: Int,
                          pointer: Int,
                          button: Int
                        ): Boolean = {
    true
  }

  override def keyDown(keycode: Int): Boolean = {
    if(!game.state.keysDown.contains(keycode)) {
      game.state.keysDown = keycode :: game.state.keysDown
    }
          true

  }

  override def keyUp(keycode: Int): Boolean = {
    if (game.state.keysDown.contains(keycode)) {
      game.state.keysDown = game.state.keysDown.filterNot(key => key == keycode)
    }
    true
  }
}
