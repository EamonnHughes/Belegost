package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
class HomeControl(home: Home) extends InputAdapter {
  override def touchDown(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean = {
    home.gameBegin = true
    true
  }

}
