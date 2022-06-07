package org.eamonn.belegost

import org.eamonn.belegost.scenes.Game
import org.eamonn.belegost.util.Location

import scala.collection.mutable

object Navigation {
  def findPath(start: Location, finish: Location, game: Game): Option[Path] = {
    val visitedCells = mutable.Set.empty[Location]
    var paths = List(Path(List(start), game))
    while (!paths.exists(path => path.getHead == finish) && paths.nonEmpty) {
      paths = for {
        path <- paths
        newPath <- path.extendPaths(visitedCells, game.findEntity(start))
      } yield {
        newPath
      }
    }
    paths.find(path => path.getHead == finish)
  }
}
