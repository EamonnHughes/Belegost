package org.eamonn.belegost.navigation
import org.eamonn.belegost.model.Wall
import org.eamonn.belegost.scenes.Game
import org.eamonn.belegost.util.Location

import scala.collection.mutable

case class Path(points: List[Location]) {
  def getHead: Location = {
    points.head
  }
  def tail: Option[Path] = {
    if (points.length > 1) {
      Some(Path(points.tail))
    } else {
      None
    }

  }
  def add(location: Location): Path = {
    Path(location :: points)
  }
  def extendPaths(visCells: mutable.Set[Location]): List[Path] = {
    for {
      loc <- getHead.findAdjacents
      if visCells.add(loc)
      if !Wall.walls.contains(loc)
    } yield add(loc)
  }
}
