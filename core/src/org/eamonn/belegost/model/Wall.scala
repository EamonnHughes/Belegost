package org.eamonn.belegost.model

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.{AssetLoader, Belegost}
import org.eamonn.belegost.util.Location

import scala.collection.mutable

case class Wall(loc: Location) {

  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.draw(Wall.wallImg, loc.x*Belegost.screenUnit, loc.y*Belegost.screenUnit, 1*Belegost.screenUnit, 1*Belegost.screenUnit)
  }

}

object Wall {

  def wallImg = AssetLoader.image("WallCenter.png")
  val walls = mutable.Map.empty[Location, Wall]

}