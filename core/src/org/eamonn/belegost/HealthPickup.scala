package org.eamonn.belegost

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.scenes.Game
import org.eamonn.belegost.util.Location

case class HealthPickup(var location: Location, game: Game) extends PickUp {
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.draw(
      Belegost.HealthPotion,
      location.x * Belegost.screenUnit,
      location.y * Belegost.screenUnit,
      Belegost.screenUnit,
      Belegost.screenUnit
    )
  }
  var corresponding = HealthPotion(game)
}
