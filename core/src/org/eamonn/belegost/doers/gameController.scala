package org.eamonn.belegost.doers

import com.badlogic.gdx.Input.Keys
import org.eamonn.belegost.GameState
import org.eamonn.belegost.model._
import org.eamonn.belegost.navigation.Navigation
import org.eamonn.belegost.util._

object gameController {
  def updateOnTick(state: GameState): Unit = {
    movePlayer(state)
    if(state.enemies.length <= 3){
      var locat = Location(state.player.loc.x + ((Math.random() - .5f) * 5 + 5).toInt, state.player.loc.y + ((Math.random() - .5f) * 5 + 5).toInt)
      createEnemy(state, locat)
    }
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
        state.player.destination =  newLoc.copy()

      } else if (state.keysDown.contains(Keys.DOWN)) {
        newLoc.y -= 1
        state.player.destination =  newLoc.copy()

      }
      if (state.keysDown.contains(Keys.LEFT)) {
        newLoc.x -= 1
        state.player.destination = newLoc.copy()

      } else if (state.keysDown.contains(Keys.RIGHT)) {
        newLoc.x += 1
        state.player.destination =  newLoc.copy()

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
      state.player.destination = state.player.loc.copy()
    }
  }

  def createEnemy(state: GameState, loc: Location): Unit = {
    if(!Wall.walls.contains(loc)){
      state.enemies = Enemy(loc) :: state.enemies
    }
  }
}
