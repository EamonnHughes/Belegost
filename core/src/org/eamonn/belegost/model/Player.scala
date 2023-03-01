package org.eamonn.belegost.model

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.navigation.Path
import org.eamonn.belegost.{AssetLoader, Belegost}
import org.eamonn.belegost.util.Location

case class Player() {
  var loc = Location(0, 0)
  var destination = loc.copy()
  var moved = false
  var path: Option[Path] = None

  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.draw(Player.playerImg, loc.x*Belegost.screenUnit, loc.y*Belegost.screenUnit, 1*Belegost.screenUnit, 1*Belegost.screenUnit)
  }

}

object Player {

  def playerImg = AssetLoader.image("Player.png")

}