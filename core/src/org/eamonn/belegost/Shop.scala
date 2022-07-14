package org.eamonn.belegost

import com.badlogic.gdx.graphics.g2d.{Batch, PolygonSpriteBatch}
import org.eamonn.belegost.items.Item
import org.eamonn.belegost.util.Location

import scala.collection.mutable

case class Shop(
    var inventory: mutable.ListBuffer[(Int, Item)],
    location: Location
) {
  var invMenu = NavMenu(
    {
      inventory
        .map({ case (num, ite) =>
          menuItem(
            "$" + s"$num | ${ite.name}",
            () => { ite.use }
          )
        })
        .toList
    },
    Location(1, (Geometry.ScreenHeight / Belegost.screenUnit).toInt - 1),
    10,
    (Geometry.ScreenWidth / Belegost.screenUnit).toInt - 2
  )
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.draw(
      Belegost.Door,
      location.x * Belegost.screenUnit,
      location.y * Belegost.screenUnit,
      Belegost.screenUnit,
      Belegost.screenUnit
    )
  }
}
