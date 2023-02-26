package org.eamonn.belegost

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.doers.gameRenderer
import org.eamonn.belegost.model.{Player, Wall}
import org.eamonn.belegost.util.Location

case class GameState() {
  var keysDown: List[Int] = List.empty

  var walls: List[Location] = List(Location(1, 1), Location(0, 1), Location(-1, 1), Location(-1, 0), Location(-1, -1), Location(-1, -2), Location(1, 0), Location(1, -1))
  walls.foreach(wall => {
    Wall.walls.put(wall, Wall(wall))
  })

  var player = new Player
}
