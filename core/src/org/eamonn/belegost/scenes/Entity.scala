package org.eamonn.belegost.scenes

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.util.Vec2

trait Entity {
  var location: Vec2
  def draw(batch: PolygonSpriteBatch): Unit
}
