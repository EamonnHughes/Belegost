package org.eamonn.belegost

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.util.Location

case class Room(location: Location, width: Int, height: Int) {
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.setColor(Color.WHITE)
    batch.draw(
      Belegost.Square,
      location.x * Belegost.screenUnit,
      location.y * Belegost.screenUnit,
      width * Belegost.screenUnit,
      height * Belegost.screenUnit
    )

  }
  def isInRoom(loc: Location): Boolean = {
    if (
      loc.x < location.x + width && loc.x >= location.x && loc.y < location.y + height && loc.y >= location.y
    ) true
    else false
  }
}
