package org.eamonn.belegost

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch

object Menu {
  def drawInventory(batch: PolygonSpriteBatch, player: Player): Unit = {
    batch.setColor(Color.WHITE)
    batch.draw(
      Belegost.Square,
      Belegost.screenUnit,
      Geometry.ScreenHeight - Belegost.screenUnit * ((player.inventory.length + 6)),
      Geometry.ScreenWidth - Belegost.screenUnit * 2,
      Belegost.screenUnit * (player.inventory.length + 5)
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
      batch.setColor(Color.BLACK)
      batch.draw(
        Belegost.Square,
        Belegost.screenUnit,
        Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 1),
        Geometry.ScreenWidth - Belegost.screenUnit * 2,
        Belegost.screenUnit / 20
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
    player.helmet.foreach(helm => {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        s"Helmet: ${helm.name}",
        Belegost.screenUnit * 2,
        Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 1) + Text.smallFont.getDescent
      )
    })
    if (player.helmet.isEmpty) {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "Helmet: None}",
        Belegost.screenUnit * 2,
        Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 1) + Text.smallFont.getDescent
      )
    }
  }
}
