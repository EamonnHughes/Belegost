package org.eamonn.belegost

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.util.Location

trait PickUp {
  var location: Location
  def draw(batch: PolygonSpriteBatch): Unit
  var corresponding: Item
}
