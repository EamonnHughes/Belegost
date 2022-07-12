package org.eamonn.belegost.scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.util.Location
import org.eamonn.belegost.{
  Belegost,
  Geometry,
  NavMenu,
  Scene,
  Text,
  d,
  menuItem
}

import java.awt.MenuItem

class LevelUp(gameE: Game) extends Scene {
  var statBonuses = NavMenu(
    List(
      menuItem(
        "Strength",
        () => {
          game.player.baseStr += 1

          statUpgrading = false
          upgradingHealth = true

        }
      ),
      menuItem(
        "Dexterity",
        () => {
          game.player.baseDex += 1

          statUpgrading = false
          upgradingHealth = true

        }
      ),
      menuItem(
        "Constitution",
        () => {
          game.player.baseCon += 1

          statUpgrading = false
          upgradingHealth = true

        }
      ),
      menuItem(
        "Intelligence",
        () => {
          game.player.baseInt += 1

          statUpgrading = false
          upgradingHealth = true

        }
      ),
      menuItem(
        "Wisdom",
        () => {
          game.player.baseWis += 1

          statUpgrading = false
          upgradingHealth = true

        }
      ),
      menuItem(
        "Charisma",
        () => {
          game.player.baseCha += 1

          statUpgrading = false
          upgradingHealth = true

        }
      )
    ),
    Location(
      ((Geometry.ScreenWidth / 2) / Belegost.screenUnit).toInt - 5,
      (Geometry.ScreenHeight / Belegost.screenUnit).toInt - 2
    ),
    6,
    10
  )
  var game: Game = gameE
  var hRolls = 3
  var healthUpgrade = (game.player.playerClass.hitDie / 2) + 1
  var statUpgrading = true
  var upgradingHealth = false
  var gameBegin = false
  Belegost.translationX = 0
  Belegost.translationY = 0
  override def init(): InputAdapter = new LevelUpControl(this)

  override def update(delta: Float): Option[Scene] = {
    statBonuses.update
    game.keysPressed = List.empty
    if (gameBegin) {

      game.player.level += 1
      game.player.maxHealth += healthUpgrade + ((game.player.constitution - 10) / 2)
      game.player.health += healthUpgrade + ((game.player.constitution - 10) / 2)
      game.player.nextXP += (game.player.nextXP * (1.33)).toInt
      game.changingTranslationX = true
      game.changingTranslationY = true
      Some(game)
    } else { None }
  }

  override def render(batch: PolygonSpriteBatch): Unit = {

    statBonuses.draw(
      batch
    )

    if (!statUpgrading) {
      Text.mediumFont.draw(
        batch,
        s" Health Bonus: ${healthUpgrade + ((game.player.constitution - 10) / 2)}  \n ${if (hRolls > 0) "r: reroll" else "no more rerolls"}  \n Enter to confirm",
        Geometry.ScreenWidth / 2 - Belegost.screenUnit * 6,
        Geometry.ScreenHeight - Belegost.screenUnit * 10
      )
    }
    if (!upgradingHealth && !statUpgrading) {
      Text.mediumFont.draw(
        batch,
        " Press Y: Confirm Level Up",
        Geometry.ScreenWidth / 2 - Belegost.screenUnit * 6,
        Geometry.ScreenHeight / 2 - Belegost.screenUnit * 10
      )
    }
  }
}
