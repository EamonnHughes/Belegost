package org.eamonn.belegost.scenes

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import org.eamonn.belegost.{Belegost, Geometry}
import org.eamonn.belegost.util.Location
class GameControl(game: Game) extends InputAdapter {
  override def touchDown(
                          screenX: Int,
                          screenY: Int,
                          pointer: Int,
                          button: Int
                        ): Boolean = {

    game.state.player.destination = Location(
      (screenX / Belegost.screenUnit).toInt - Belegost.translationX,
      ((Geometry.ScreenHeight - screenY) / Belegost.screenUnit).toInt - Belegost.translationY
    )
    println(game.state.player.destination)
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
