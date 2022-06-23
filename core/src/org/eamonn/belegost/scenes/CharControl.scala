package org.eamonn.belegost.scenes

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import org.eamonn.belegost.scenes.Classes.Fighter
import org.eamonn.belegost.scenes.Races.Human
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
    if (charCreation.race.isEmpty) {

      if (keycode == Keys.A) {
        charCreation.race = Some(Human)
      }
    } else if (charCreation.pClass.isEmpty) {

      if (keycode == Keys.A) {
        charCreation.pClass = Some(Fighter)
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
