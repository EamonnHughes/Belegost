package org.eamonn.belegost.doers

import com.badlogic.gdx.Input.Keys
import org.eamonn.belegost.GameState
import org.eamonn.belegost.model._
import org.eamonn.belegost.navigation.Navigation
import org.eamonn.belegost.util._

object gameController {
  def updateOnTick(state: GameState): Unit = {
    movePlayer(state)
  }
  def movePlayer(state: GameState): Unit = {
    if(state.player.destination != state.player.loc){
      navPlayerTo(state)
    }
    var newLoc = state.player.loc.copy()
    if(state.player.path.nonEmpty){
      for {
        p <- state.player.path
      } {
        newLoc = p.getHead
      }
    } else {
      if (state.keysDown.contains(Keys.UP)) {
        newLoc.y += 1
      } else if (state.keysDown.contains(Keys.DOWN)) {
        newLoc.y -= 1
      }
      if (state.keysDown.contains(Keys.LEFT)) {
        newLoc.x -= 1
      } else if (state.keysDown.contains(Keys.RIGHT)) {
        newLoc.x += 1
      }
    }
    if(!Wall.walls.contains(newLoc)) {
      state.player.loc = newLoc
    }
  }

  def navPlayerTo(state: GameState): Unit = {
    if (!Wall.walls.contains(state.player.destination)) {
      state.player.path = Navigation
        .findPath(state.player.destination, state.player.loc)
        .flatMap(path => path.tail)
    } else {
      state.player.destination = state.player.loc
    }
  }
}
