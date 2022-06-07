package org.eamonn.belegost

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.scenes.Game
import org.eamonn.belegost.util.{Delta, Location}

case class Player(
    var location: Location,
    var destination: Location,
    var game: Game
) extends Entity {

  var pathToDest = Option.empty[Path]
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
    for {
      path <- pathToDest
    } {
      val nextLoc = path.getHead

      location = nextLoc
      pathToDest = path.tail
    }
  }

  def setDestination: Location = {

    if(){}else if(game.keysPressed.nonEmpty){ if (game.keysPressed.contains(19)) {
      location + Delta(0, 1)

    } else if (game.keysPressed.contains(20)) {
      location + (Delta(0, -1))

    } else if (game.keysPressed.contains(21)) {
      location + (Delta(-1, 0))

    } else if (game.keysPressed.contains(22)) {
      location + (Delta(1, 0))

    } else {
      location
    }}

  }
}
