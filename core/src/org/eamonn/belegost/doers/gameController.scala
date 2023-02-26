package org.eamonn.belegost.doers

import com.badlogic.gdx.Input.Keys
import org.eamonn.belegost.GameState
import org.eamonn.belegost.model.Wall

object gameController {
  def updateOnTick(state: GameState): Unit = {
    movePlayer(state)
  }
  def movePlayer(state: GameState): Unit = {
    var newLoc = state.player.loc.copy()
    if(state.keysDown.contains(Keys.UP)){
      newLoc.y += 1
    } else if (state.keysDown.contains(Keys.DOWN)) {
      newLoc.y -= 1
    }
    if (state.keysDown.contains(Keys.LEFT)) {
      newLoc.x -= 1
    } else if (state.keysDown.contains(Keys.RIGHT)) {
      newLoc.x += 1
    }
    if(!Wall.walls.contains(newLoc)) {
      state.player.loc = newLoc
    }
  }
}
