package org.eamonn.belegost.model

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.navigation.Path
import org.eamonn.belegost.{AssetLoader, Belegost}
import org.eamonn.belegost.util.Location

import scala.collection.mutable

case class Enemy(sLoc: Location) {
  var loc = sLoc.copy()
  var destination = sLoc.copy()
  var path: Option[Path] = None

  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.draw(Enemy.enemyImg, loc.x*Belegost.screenUnit, loc.y*Belegost.screenUnit, 1*Belegost.screenUnit, 1*Belegost.screenUnit)
  }

}

object Enemy {

  def enemyImg = AssetLoader.image("Imp.png")

}
