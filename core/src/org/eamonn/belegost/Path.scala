package org.eamonn.belegost
import org.eamonn.belegost.scenes.Game
import org.eamonn.belegost.util.Location

import scala.collection.mutable

case class Path(points: List[Location], game: Game) {
  def getHead: Location = {
    points.head
  }
  def tail: Option[Path] = {
    if (points.length > 1) {
      Some(Path(points.tail, game))
    } else {
      None
    }

  }
  def add(location: Location): Path = {
    Path(location :: points, game)
  }
  def extendPaths(visCells: mutable.Set[Location], unit: Entity): List[Path] = {
    for {
      loc <- getHead.findAdjacents
      if visCells.add(loc)
      if game.roomList.exists(room => room.isInRoom(loc))
    } yield add(loc)
  }
}
