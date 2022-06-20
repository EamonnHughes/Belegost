package org.eamonn.belegost.items

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.scenes.Game
import org.eamonn.belegost.util.Location
import org.eamonn.belegost.{Belegost, items}

case class HealthPickup(var location: Location, game: Game) extends PickUp {
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.setColor(Color.WHITE)
    batch.draw(
      Belegost.HealthPotion,
      location.x * Belegost.screenUnit,
      location.y * Belegost.screenUnit,
      Belegost.screenUnit,
      Belegost.screenUnit
    )
  }
  var corresponding: Item = items.HealthPotion(game)
}
