package org.eamonn.belegost

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.scenes.Game
import org.eamonn.belegost.util.Location

case class Enemy(
    var location: Location,
    var destination: Location,
    var game: Game
) extends Entity {
  var pathToDest = Option.empty[Path]

  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.draw(
      Belegost.Orc,
      location.x * Belegost.screenUnit,
      location.y * Belegost.screenUnit,
      Belegost.screenUnit,
      Belegost.screenUnit
    )
  }
  def update(delta: Float): Unit = {
    destination = computeDestination
    navTo

    for {
      path <- pathToDest
    } {
      val nextLoc = path.getHead

      location = nextLoc
      pathToDest = path.tail
    }
  }
  def navTo: Unit = {
    if (game.roomList.exists(room => room.isInRoom(destination))) {
      pathToDest = Navigation
        .findPath(destination, location, game)
        .flatMap(path => path.tail)
    } else { destination = location }
  }
  def computeDestination: Location = {
    game.player.location
  }
}
