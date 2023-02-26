package org.eamonn.belegost.doers

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.{Belegost, GameState}
import org.eamonn.belegost.model.{Player, Wall}

object gameRenderer {

  def drawState(batch: PolygonSpriteBatch,state: GameState): Unit = {
   drawWalls(batch, state)
    drawPlayer(batch, state)
  }
 def drawPlayer(batch: PolygonSpriteBatch,state: GameState): Unit = {
   batch.draw(Player.playerImg, state.player.loc.x*Belegost.screenUnit, state.player.loc.y*Belegost.screenUnit, 1*Belegost.screenUnit, 1*Belegost.screenUnit)

 }

  def drawWalls(batch: PolygonSpriteBatch,state: GameState): Unit = {
    state.walls.foreach(location => {
      var wall = Wall(location)
      batch.draw(Wall.wallImg, wall.loc.x * Belegost.screenUnit, wall.loc.y * Belegost.screenUnit, 1 * Belegost.screenUnit, 1 * Belegost.screenUnit)
    }
    )
  }

}
