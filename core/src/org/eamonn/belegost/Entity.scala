package org.eamonn.belegost

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.util.Location

trait Entity {
  def armorClass: Int
  def speed: Int
  var location: Location
  var destination: Location
  def draw(batch: PolygonSpriteBatch): Unit
  def update(delta: Float): Unit
  def computeDestination: Location
}
