package org.eamonn.belegost.util

case class Vec2(x: Float, y: Float) {
  def add(newVec: Vec2): Vec2 = {
    Vec2(x + newVec.x, y + newVec.y)
  }
  def subtract(newVec: Vec2): Vec2 = {
    Vec2(x - newVec.x, y - newVec.y)
  }
  def div(denominator: Float): Vec2 = {
    Vec2(x / denominator, y / denominator)
  }
  def clamp(limit: Float): Vec2 = {
    val hyp = length
    if (hyp < limit) {
      this
    } else {
      Vec2((x / hyp) * limit, (y / hyp) * limit)
    }
  }
  def times(multiplier: Float): Vec2 = {
    Vec2(x * multiplier, y * multiplier)
  }
  def length: Float = {
    Math.sqrt(x * x + y * y).toFloat
  }
}
