package org.eamonn.belegost

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.util.Location

import java.awt.MenuItem

case class NavMenu(itList: List[menuItem]) {
  var selected = 0

  def up = { selected = (selected - 1) max 0 }
  def down = { selected = (selected + 1) min itList.length - 1 }
  def used = itList(selected).use

  def draw(
      batch: PolygonSpriteBatch,
      location: Location,
      length: Int,
      lX: Int
  ): Unit = {
    var startDrawingAt = 0
    if (selected > length) {
      startDrawingAt = selected
    }

    var listDrawn: List[menuItem] = List.empty
    if (startDrawingAt > 0) {
      listDrawn = itList.slice((startDrawingAt - 1), (length))
    } else {

      listDrawn = itList.take(length)
    }
    batch.setColor(Color.BLACK)
    batch.draw(
      Belegost.Square,
      (location.x - Belegost.translationX) * Belegost.screenUnit,
      (location.y - length - Belegost.translationY) * Belegost.screenUnit,
      lX * Belegost.screenUnit,
      Belegost.screenUnit * length
    )

    batch.setColor(Color.WHITE)
    batch.draw(
      Belegost.Square,
      (location.x - Belegost.translationX) * Belegost.screenUnit,
      (location.y - Belegost.translationY - (selected + 1 - startDrawingAt)) * Belegost.screenUnit,
      lX * Belegost.screenUnit,
      Belegost.screenUnit
    )

    listDrawn.zipWithIndex.foreach({ case (item, index) =>
      if (index + startDrawingAt != selected) {
        Text.mediumFont.setColor(Color.WHITE)
      } else {
        Text.mediumFont.setColor(Color.BLACK)
      }
      Text.mediumFont.draw(
        batch,
        " " + item.name,
        location.x * Belegost.screenUnit,
        (location.y - index) * Belegost.screenUnit
      )
    })
  }
}
case class menuItem(name: String, use: () => Unit) {}
