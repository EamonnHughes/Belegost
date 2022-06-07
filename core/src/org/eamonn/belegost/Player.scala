package org.eamonn.belegost

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.scenes.{Entity, Game}
import org.eamonn.belegost.util.Vec2

case class Player(var location: Vec2, var destination: Vec2, var game: Game)
    extends Entity {
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.setColor(Color.GREEN)
    batch.draw(
      Belegost.Square,
      location.x * Belegost.screenUnit,
      location.y * Belegost.screenUnit,
      Belegost.screenUnit,
      Belegost.screenUnit
    )
  }
  def update(delta: Float): Unit = {
    destination = setDestination
    location = destination
  }

  def setDestination: Vec2 = {
    if (game.keysPressed.contains(19)) {
      location.add(Vec2(0, 1))

    } else if (game.keysPressed.contains(20)) {
      location.add(Vec2(0, -1))

    } else if (game.keysPressed.contains(21)) {
      location.add(Vec2(-1, 0))

    } else if (game.keysPressed.contains(22)) {
      location.add(Vec2(1, 0))

    } else {
      location
    }
  }
}
