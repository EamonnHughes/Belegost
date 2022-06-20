package org.eamonn.belegost

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch

object Menu {
  def draw(batch: PolygonSpriteBatch, player: Player): Unit = {
    batch.setColor(Color.WHITE)
    batch.draw(
      Belegost.Square,
      Belegost.screenUnit,
      Geometry.ScreenHeight - Belegost.screenUnit * ((player.inventory.length + 1) max 2),
      Geometry.ScreenWidth - Belegost.screenUnit * 2,
      Belegost.screenUnit * (player.inventory.length max 1)
    )
    if (player.inventory.nonEmpty) {
      batch.setColor(Color.RED)
      batch.draw(
        Belegost.Square,
        Belegost.screenUnit,
        Geometry.ScreenHeight - Belegost.screenUnit * (player.currentInventoryItem + 2),
        Geometry.ScreenWidth - Belegost.screenUnit * 2,
        Belegost.screenUnit
      )
    }
    player.inventory.zipWithIndex.foreach({ case (item, index) =>
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "x" + item._1 + "   " + item._2.name,
        Belegost.screenUnit * 2,
        Geometry.ScreenHeight - Belegost.screenUnit * (index + 1) + Text.smallFont.getDescent
      )
    })
  }
}
