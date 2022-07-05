package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.{Belegost, Geometry, Scene, Text, d}

class LevelUp(gameE: Game) extends Scene {
  var game = gameE
  var hRolls = 3
  var upStat = 0
  var healthUpgrade = (game.player.playerClass.hitDie / 2) + 1
  var statUpgrading = true
  var upgradingHealth = false
  var gameBegin = false
  Belegost.translationX = 0
  Belegost.translationY = 0
  override def init(): InputAdapter = new LevelUpControl(this)

  override def update(delta: Float): Option[Scene] = {
    game.keysPressed = List.empty
    if (gameBegin) {
      if (upStat == 0) {
        game.player.baseStr += 1
      } else if (upStat == 1) {
        game.player.baseDex += 1
      } else if (upStat == 2) {
        game.player.baseCon += 1
      } else if (upStat == 3) {
        game.player.baseInt += 1
      } else if (upStat == 4) {
        game.player.baseWis += 1
      } else if (upStat == 5) {
        game.player.baseCha += 1
      }

      game.player.level += 1
      game.player.maxHealth += healthUpgrade + ((game.player.constitution - 10) / 2)
      game.player.health += healthUpgrade + ((game.player.constitution - 10) / 2)
      game.player.nextXP += (game.player.nextXP * 1.25).toInt
      Some(game)
    } else { None }
  }

  override def render(batch: PolygonSpriteBatch): Unit = {
    batch.setColor(Color.WHITE)

    batch.draw(
      Belegost.Square,
      Geometry.ScreenWidth / 2 - Belegost.screenUnit * 7,
      Geometry.ScreenHeight - ((Text.mediumFont.getLineHeight * (upStat + 2))),
      Belegost.screenUnit / 2,
      Text.mediumFont.getAscent + Text.mediumFont.getCapHeight - Text.mediumFont.getDescent
    )

    Text.mediumFont.setColor(Color.WHITE)
    Text.mediumFont.draw(
      batch,
      s"Strength: ${game.player.strength} \n Dexterity: ${game.player.dexterity} \n Constitution: ${game.player.constitution} \n " +
        s"Intelligence: ${game.player.intelligence} \n Wisdom: ${game.player.wisdom} \n Charisma: ${game.player.charisma} \n ",
      Geometry.ScreenWidth / 2 - Belegost.screenUnit * 6,
      Geometry.ScreenHeight - Belegost.screenUnit
    )

    Text.mediumFont.draw(
      batch,
      s"Health Bonus: ${healthUpgrade + ((game.player.constitution - 10) / 2)}  \n ${if (hRolls > 0) "r: reroll" else "no more rerolls"}",
      Geometry.ScreenWidth / 2 - Belegost.screenUnit * 6,
      Geometry.ScreenHeight - Belegost.screenUnit * 8
    )
    if (!upgradingHealth && !statUpgrading) {
      Text.mediumFont.draw(
        batch,
        "Press Y: Confirm Level Up",
        Geometry.ScreenWidth / 2 - Belegost.screenUnit * 6,
        Geometry.ScreenHeight / 2 - Belegost.screenUnit * 10
      )
    }
  }
}
