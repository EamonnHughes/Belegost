package org.eamonn.belegost

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch

object Menu {
  def drawStats(batch: PolygonSpriteBatch, player: Player): Unit = {
    batch.setColor(Color.WHITE)
    batch.draw(
      Belegost.Square,
      -(Belegost.translationX) * Belegost.screenUnit,
      -(Belegost.translationY) * Belegost.screenUnit + Belegost.screenUnit,
      Belegost.screenUnit * 8,
      Belegost.screenUnit * 20
    )
    Text.smallFont.setColor(Color.BLACK)
    Text.smallFont.draw(
      batch,
      s" ${player.playerRace} ${player.playerClass} \nlevel: ${player.level} \n next: ${player.nextXP - player.XPvalue}" +
        s" \n str: ${player.strength}\n dex: ${player.dexterity} " +
        s"\n con: ${player.constitution} \n int: ${player.intelligence} " +
        s"\n wis: ${player.wisdom} \n cha: ${player.charisma} " +
        s"\n health: ${player.health} / ${player.maxHealth} \n AC: ${player.armorClass} " +
        s" \n speed: ${player.speed} \n GP: ${player.money}",
      -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit,
      -(Belegost.translationY) * Belegost.screenUnit + Belegost.screenUnit * 20
    )
  }
  def drawInventory(batch: PolygonSpriteBatch, player: Player): Unit = {
    batch.setColor(Color.WHITE)
    batch.draw(
      Belegost.Square,
      -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit,
      -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * ((player.inventory.length + 7)),
      Geometry.ScreenWidth - Belegost.screenUnit * 2,
      Belegost.screenUnit * (player.inventory.length + 6)
    )
    if (player.inventory.nonEmpty) {
      batch.setColor(Color.RED)
      batch.draw(
        Belegost.Square,
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.currentInventoryItem + 2),
        Geometry.ScreenWidth - Belegost.screenUnit * 2,
        Belegost.screenUnit
      )
      batch.setColor(Color.BLACK)
      batch.draw(
        Belegost.Square,
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 1),
        Geometry.ScreenWidth - Belegost.screenUnit * 2,
        Belegost.screenUnit / 20
      )
    }
    player.inventory.zipWithIndex.foreach({ case (item, index) =>
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "x" + item._1 + "   " + item._2.name,
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (index + 1) + Text.smallFont.getDescent
      )
    })
    player.helmet.foreach(helm => {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        s"Helmet: ${helm.name}",
        (-Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        (-Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 1) + Text.smallFont.getDescent
      )
    })
    if (player.helmet.isEmpty) {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "Helmet: None",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 1) + Text.smallFont.getDescent
      )
    }
    player.bodyArmor.foreach(body => {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        s"Armor: ${body.name}",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 2) + Text.smallFont.getDescent
      )
    })
    if (player.bodyArmor.isEmpty) {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "Armor: None",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 2) + Text.smallFont.getDescent
      )
    }
    player.gloves.foreach(glove => {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        s"Gloves: ${glove.name}",
        (-Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        (-Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 3) + Text.smallFont.getDescent
      )
    })
    if (player.gloves.isEmpty) {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "Gloves: None",
        (-Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        (-Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 3) + Text.smallFont.getDescent
      )
    }
    player.boots.foreach(boot => {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        s"Boots: ${boot.name}",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 4) + Text.smallFont.getDescent
      )
    })
    if (player.boots.isEmpty) {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "Boots: None",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 4) + Text.smallFont.getDescent
      )
    }
    player.cloak.foreach(cloak => {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        s"Cloak: ${cloak.name}",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 5) + Text.smallFont.getDescent
      )
    })
    if (player.cloak.isEmpty) {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "Cloak: None",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 5) + Text.smallFont.getDescent
      )
    }
    player.weapon.foreach(weapon => {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        s"Weapon: ${weapon.name}",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 6) + Text.smallFont.getDescent
      )
    })
    if (player.weapon.isEmpty) {
      Text.smallFont.setColor(Color.BLACK)
      Text.smallFont.draw(
        batch,
        "Weapon: None",
        -(Belegost.translationX) * Belegost.screenUnit + Belegost.screenUnit * 2,
        -(Belegost.translationY) * Belegost.screenUnit + Geometry.ScreenHeight - Belegost.screenUnit * (player.inventory.length + 6) + Text.smallFont.getDescent
      )
    }
  }
}
