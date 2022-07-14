package org.eamonn.belegost

import org.eamonn.belegost.items.Item
import org.eamonn.belegost.util.Location

import scala.collection.mutable

case class Shop(var inventory: mutable.ListBuffer[(Int, Item)]) {
  var invMenu = NavMenu(
    {
      inventory
        .map({ case (num, ite) =>
          menuItem(
            "$" + s"$num ${ite.name}",
            () => { ite.use }
          )
        })
        .toList
    },
    Location(1, (Geometry.ScreenHeight / Belegost.screenUnit).toInt - 1),
    10,
    (Geometry.ScreenWidth / Belegost.screenUnit).toInt - 2
  )
}
