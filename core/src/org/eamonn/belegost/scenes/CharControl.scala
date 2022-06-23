package org.eamonn.belegost.scenes

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
class CharControl(charCreation: CharCreation) extends InputAdapter {
  override def touchDown(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean = {

    true
  }

  override def keyDown(keycode: Int): Boolean = {
    if (!charCreation.race) {

      if (keycode == Keys.A) {
        charCreation.race = true
      }
    } else if (!charCreation.pClass) {

      if (keycode == Keys.A) {
        charCreation.pClass = true
      }
    } else {

      if (keycode == Keys.Y) {
        charCreation.done = true
      }
      if (keycode == Keys.N) {
        charCreation.quit = true
      }
    }
    true
  }
}
