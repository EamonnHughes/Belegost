package org.eamonn.belegost

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.util.Location

import java.awt.MenuItem

case class NavMenu(
    var itList: List[menuItem],
    location: Location,
    var length: Int,
    lX: Int
) {
  var selected = 0

  def up() = {
    if (itList.nonEmpty) { selected = (selected - 1) max 0 }
  }
  def down() = {
    if (itList.nonEmpty) { selected = (selected + 1) min itList.length - 1 }
  }
  def used() = {
    if (itList.nonEmpty) {
      itList(selected).use()
    }
  }
  def update(): Unit = {
    if (selected >= length + startDrawingAt) {
      startDrawingAt = selected
    }
    if (startDrawingAt > selected) {
      startDrawingAt = (selected - length + 1) max 0
    }

    if (selected >= itList.length) {
      selected = itList.length - 1
    }
  }

  var startDrawingAt = 0
  def draw(
      batch: PolygonSpriteBatch
  ): Unit = {
    if (itList.nonEmpty) {

      var listDrawn: List[menuItem] =
        itList.slice(startDrawingAt, startDrawingAt + length)
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
          (location.x - Belegost.translationX) * Belegost.screenUnit,
          (location.y - index - Belegost.translationY) * Belegost.screenUnit
        )
      })
    }
  }
}
case class menuItem(var name: String, use: () => Unit) {}
