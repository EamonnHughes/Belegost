package org.eamonn.belegost.util

case class Box(loc: Vec2, size: Vec2) {
  def isColliding(checkedBox: Box): Boolean = {
    if (
      loc.x + size.x > checkedBox.loc.x && loc.x < checkedBox.loc.x + checkedBox.size.x &&
      loc.y + size.y > checkedBox.loc.y && loc.y < checkedBox.loc.y + checkedBox.size.y
    ) {
      true
    } else {
      false
    }
  }
}
