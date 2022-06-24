package org.eamonn.belegost

import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.scenes.Game
import org.eamonn.belegost.util.{Location, TextureWrapper}

case class Room(location: Location, width: Int, height: Int, var game: Game) {
  var wallTexs: List[TextureWrapper] = {
    List(Belegost.Wall, Belegost.Wall2, Belegost.Wall3)
  }
  var floorTexes: List[TextureWrapper] = {
    List(Belegost.Floor, Belegost.Floor2, Belegost.Floor3)

  }

  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.setColor(Color.GRAY)

    for (dWidth <- 0 until width) {
      for (dHeight <- 0 until height) {
        var floorT: Int = {
          if (dWidth % 7 != 0 || dHeight % 5 != 0) { 0 }
          else { 2 }
        }
        batch.draw(
          floorTexes(floorT),
          (dWidth + location.x) * Belegost.screenUnit,
          (dHeight + location.y) * Belegost.screenUnit,
          Belegost.screenUnit,
          Belegost.screenUnit
        )
        Location(dWidth + location.x, dHeight + location.y).findAdjacents
          .foreach(wLoc => {
            if (!game.roomList.exists(room => room.isInRoom(wLoc))) {
              val wallT: Int = {
                if (wLoc.x % 3 != 0) {
                  0
                } else if (wLoc.y % 2 == 0) { 1 }
                else { 2 }
              }
              batch.draw(
                wallTexs(wallT),
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
