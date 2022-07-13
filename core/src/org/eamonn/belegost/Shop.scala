package org.eamonn.belegost

import org.eamonn.belegost.items.Item

import scala.collection.mutable

case class Shop(var inventory: mutable.ListBuffer[(Int, Item)]) {}
