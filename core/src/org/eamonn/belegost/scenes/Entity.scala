package org.eamonn.belegost.scenes

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.util.Location

trait Entity {
  var location: Location
  var destination: Location
  def draw(batch: PolygonSpriteBatch): Unit
  def update(delta: Float): Unit
  def setDestination: Location
}
