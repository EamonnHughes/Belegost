package org.eamonn.belegost

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.scenes.Game
import org.eamonn.belegost.util.Location

case class Room(location: Location, width: Int, height: Int, var game: Game) {
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.setColor(Color.GRAY)
    batch.draw(
      Belegost.Square,
      location.x * Belegost.screenUnit,
      location.y * Belegost.screenUnit,
      width * Belegost.screenUnit,
      height * Belegost.screenUnit
    )
    for (dWidth <- 0 until width) {
      for (dHeight <- 0 until height) {
        Location(dWidth + location.x, dHeight + location.y).findAdjacents
          .foreach(wLoc => {
            if (!game.roomList.exists(room => room.isInRoom(wLoc))) {
              batch.draw(
                Belegost.Wall,
                wLoc.x * Belegost.screenUnit,
                wLoc.y * Belegost.screenUnit,
                Belegost.screenUnit,
                Belegost.screenUnit
              )
            }
          })
      }
    }

  }
  def isInRoom(loc: Location): Boolean = {
    if (
      loc.x < location.x + width && loc.x >= location.x && loc.y < location.y + height && loc.y >= location.y
    ) true
    else false
  }
}
