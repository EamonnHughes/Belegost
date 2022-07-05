package org.eamonn.belegost.enemies

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.items.{HealthPickup, Money}
import org.eamonn.belegost.scenes.Game
import org.eamonn.belegost.util.Location
import org.eamonn.belegost.{Belegost, Entity, Navigation, Path, d}

case class Orc(
    var location: Location,
    var destination: Location,
    var game: Game,
    var health: Int
) extends Entity {
  var pathToDest = Option.empty[Path]
  var XPvalue = 10
  val maxHealth = health
  def armorClass = 12
  def speed = 1

  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.setColor(Color.WHITE)
    batch.draw(
      Belegost.Orc,
      location.x * Belegost.screenUnit,
      location.y * Belegost.screenUnit,
      Belegost.screenUnit,
      Belegost.screenUnit
    )
    batch.setColor(1, 0, 0, 0.5f)
    batch.draw(
      Belegost.Square,
      location.x * Belegost.screenUnit,
      (location.y + 1) * Belegost.screenUnit,
      ((health.toFloat / maxHealth.toFloat) * Belegost.screenUnit) max 0,
      Belegost.screenUnit / 10
    )
  }
  def update(delta: Float): Unit = {

    if (health <= 0) {
      game.enemies = game.enemies.filterNot(enemy => enemy == this)
      game.player.XPvalue += (XPvalue * game.eeEs).toInt
      if (d(4) == 1) {
        game.pickups = HealthPickup(location, game) :: game.pickups
      } else if (d(3) == 1) {
        game.MoneyInDungeon = Money(location) :: game.MoneyInDungeon
      }

    }
    destination = computeDestination
    navTo

    for {
      path <- pathToDest
    } {
      val nextLoc = path.getHead
      if (game.player.location == nextLoc) {
        for (i <- 0 until speed) {
          var roll = d(20)

          if (roll == 20) game.player.health -= 2 * d(3)
          else if (roll > game.player.armorClass + game.player.evasiveBonus)
            game.player.health -= d(3)
        }
      } else if (!game.enemies.exists(enemy => enemy.location == nextLoc)) {

        location = nextLoc
        pathToDest = path.tail
      }
    }
  }
  def navTo: Unit = {
    if (game.roomList.exists(room => room.isInRoom(destination))) {
      pathToDest = Navigation
        .findPath(destination, location, game)
        .flatMap(path => path.tail)
    } else { destination = location }
  }
  def computeDestination: Location = {
    game.player.location
  }
}
