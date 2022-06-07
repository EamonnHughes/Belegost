package org.eamonn.belegost

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.scenes.Entity
import org.eamonn.belegost.util.Vec2

case class Player(var location: Vec2) extends Entity {
  def draw(batch: PolygonSpriteBatch): Unit = {}
}
