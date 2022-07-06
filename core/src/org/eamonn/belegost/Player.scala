package org.eamonn.belegost

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.belegost.Classes.FireBolt
import org.eamonn.belegost.equipment.{
  ArmorType,
  BodyArmor,
  Boots,
  Cloak,
  Equipment,
  Gloves,
  Helmet,
  LightSource,
  Weapon,
  WeaponType,
  lightType
}
import org.eamonn.belegost.items.{EmptyBottle, Fuel, HealthPotion, Item}
import org.eamonn.belegost.scenes.{Classes, Game, Races}
import org.eamonn.belegost.util.{Delta, Location}
import org.graalvm.compiler.word.Word

import scala.::
import scala.collection.mutable

case class Player(
    var location: Location,
    var destination: Location,
    var game: Game
) extends Entity {
  var accurateBonus = 0
  var evasiveBonus = 0
  def playerRace = game.pRace
  def playerClass = game.pClass
  var XPvalue = 0
  var nextXP: Int = (10 * playerRace.xpMod).toInt
  var money = 0
  def speed: Int = 1 max ((dexterity - 10) / 2)
  var level = 1
  var inEquip = false
  def lightDist: Int = {
    lightS match {
      case some: Some[LightSource] => some.head.output
      case None                    => 1
    }
  }
  var stats = game.sStats
  var strength: Int =
    stats.head + playerRace.statBonus(0) + playerClass.statBonus(0)
  stats = stats.tail
  var dexterity: Int =
    stats.head + playerRace.statBonus(1) + playerClass.statBonus(1)
  stats = stats.tail
  var constitution: Int =
    stats.head + playerRace.statBonus(2) + playerClass.statBonus(2)
  stats = stats.tail
  var intelligence: Int =
    stats.head + playerRace.statBonus(3) + playerClass.statBonus(3)
  stats = stats.tail
  var wisdom: Int =
    stats.head + playerRace.statBonus(4) + playerClass.statBonus(4)
  stats = stats.tail
  var charisma: Int =
    stats.head + playerRace.statBonus(5) + playerClass.statBonus(5)
  var baseStr = strength
  var baseDex = dexterity
  var baseCon = constitution
  var baseInt = intelligence
  var baseWis = wisdom
  var baseCha = charisma
  var enchMod = List[Int](0, 0, 0, 0, 0, 0)
  var health = playerClass.hitDie + ((constitution - 10) / 2)
  var baseAC = 10
  var maxSS = ((level / 2) + ((intelligence - 10) / 2)) max 2
  var spellSlots = maxSS
  var inSpellList = false
  var spellList: List[Spells] = List.empty
  if (playerClass.caster) {
    spellList = List(FireBolt)
  }
  def armorClass = baseAC + acMod + ((dexterity - 10) / 2)
  var weapon: Option[Weapon] = None
  def acMod: Int = {
    var eqBonus: Float = 0
    equipped.foreach(equip => {
      eqBonus += equip.mod
    })
    eqBonus.toInt
  }

  var moved = false
  var maxHealth = health
  var inventory = mutable.ListBuffer[(Int, Item)](
    (40, HealthPotion(game)),
    (10, Fuel(game))
  )
  if (playerClass == Classes.Fighter) {
    inventory = inventory.addAll(
      mutable.ListBuffer[(Int, Item)](
        (1, Helmet(game, ArmorType.Leather)),
        (1, BodyArmor(game, ArmorType.Leather)),
        (1, Gloves(game, ArmorType.Leather)),
        (1, Boots(game, ArmorType.Leather)),
        (1, Cloak(game, ArmorType.Leather)),
        (1, Weapon(game, WeaponType.ShortSword)),
        (1, LightSource(game, lightType.Lamp))
      )
    )
  } else if (playerClass == Classes.Wizard) {
    inventory = inventory.addAll(
      mutable.ListBuffer[(Int, Item)](
        (1, Helmet(game, ArmorType.Cloth)),
        (1, BodyArmor(game, ArmorType.Cloth)),
        (1, Gloves(game, ArmorType.Cloth)),
        (1, Boots(game, ArmorType.Cloth)),
        (1, Cloak(game, ArmorType.Cloth)),
        (1, Weapon(game, WeaponType.Staff)),
        (1, LightSource(game, lightType.Lamp))
      )
    )
  }
  def equipped: List[Equipment] = {
    helmet.toList ::: bodyArmor.toList ::: gloves.toList ::: boots.toList ::: cloak.toList
  }
  var invMenu = NavMenu(
    {
      inventory
        .map({ case (num, ite) =>
          menuItem(
            s"x $num ${ite.name}",
            () => { ite.use }
          )
        })
        .toList
    },
    Location(1, (Geometry.ScreenHeight / Belegost.screenUnit).toInt - 1),
    6,
    (Geometry.ScreenWidth / Belegost.screenUnit).toInt - 2
  )
  var equipMenu = NavMenu(
    List(menuItem(" ", () => {})),
    Location(1, (Geometry.ScreenHeight / Belegost.screenUnit).toInt - 1),
    1,
    (Geometry.ScreenWidth / Belegost.screenUnit).toInt - 2
  )
  var currentSpell = 0
  var helmet: Option[Helmet] = None
  var bodyArmor: Option[BodyArmor] = None
  var gloves: Option[Gloves] = None
  var boots: Option[Boots] = None
  var cloak: Option[Cloak] = None
  var lightS: Option[LightSource] = None
  var pathToDest = Option.empty[Path]
  var clickedDest: Location = location
  def equipMenuUpdate(): Unit = {
    var helm: menuItem = menuItem("Helm: None", () => {})
    if (helmet.nonEmpty) {
      helmet.foreach(nHelm => {

        helm = menuItem(
          s"Helm: ${nHelm.name}",
          () => {
            inventory.addOne((1, nHelm))
            helmet = None
            setInvMenu()
          }
        )
      })
    }
    var chest: menuItem = menuItem("Armor: None", () => {})
    if (bodyArmor.nonEmpty) {
      bodyArmor.foreach(bA => {

        chest = menuItem(
          s"Armor: ${bA.name}",
          () => {
            inventory.addOne((1, bA))
            bodyArmor = None
            setInvMenu()
          }
        )
      })
    }
    var gls: menuItem = menuItem("Gloves: None", () => {})
    if (gloves.nonEmpty) {
      gloves.foreach(glov => {

        gls = menuItem(
          s"Gloves: ${glov.name}",
          () => {
            inventory.addOne((1, glov))
            gloves = None
            setInvMenu()
          }
        )
      })
    }
    var foot: menuItem = menuItem("Boots: None", () => {})
    if (boots.nonEmpty) {
      boots.foreach(footwear => {

        foot = menuItem(
          s"Boots: ${footwear.name}",
          () => {
            inventory.addOne((1, footwear))
            boots = None
            setInvMenu()
          }
        )
      })
    }
    var back: menuItem = menuItem("Cloak: None", () => {})
    if (cloak.nonEmpty) {
      cloak.foreach(clk => {

        back = menuItem(
          s"Cloak: ${clk.name}",
          () => {
            inventory.addOne((1, clk))
            cloak = None
            setInvMenu()
          }
        )
      })
    }
    var weap: menuItem = menuItem("Weapon: None", () => {})
    if (weapon.nonEmpty) {
      weapon.foreach(wep => {

        weap = menuItem(
          s"Weapon: ${wep.name}",
          () => {
            inventory.addOne((1, wep))

            weapon = None
            setInvMenu()
          }
        )
      })
    }
    var liteS: menuItem = menuItem("Light Source: None", () => {})
    if (lightS.nonEmpty) {
      lightS.foreach(light => {

        liteS = menuItem(
          s"Light Source: ${light.name}",
          () => {
            inventory.addOne((1, light))

            lightS = None
            setInvMenu()
          }
        )
      })
    }
    equipMenu = NavMenu(
      List[menuItem](helm, chest, gls, foot, back, weap, liteS),
      equipMenu.location,
      7,
      equipMenu.lX
    )
  }
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.setColor(1, 1, 1, 1)
    if (playerRace == Races.Human) {
      if (playerClass == Classes.Fighter) {
        batch.draw(
          Belegost.HumanFighterPC,
          location.x * Belegost.screenUnit,
          location.y * Belegost.screenUnit,
          Belegost.screenUnit,
          Belegost.screenUnit
        )
      } else if (playerClass == Classes.Wizard) {
        batch.draw(
          Belegost.HumanWizardPC,
          location.x * Belegost.screenUnit,
          location.y * Belegost.screenUnit,
          Belegost.screenUnit,
          Belegost.screenUnit
        )
      }
    }
    if (playerRace == Races.Dwarf) {
      if (playerClass == Classes.Fighter) {
        batch.draw(
          Belegost.DwarfFighterPC,
          location.x * Belegost.screenUnit,
          location.y * Belegost.screenUnit,
          Belegost.screenUnit,
          Belegost.screenUnit
        )
      } else if (playerClass == Classes.Wizard) {
        batch.draw(
          Belegost.DwarfWizardPC,
          location.x * Belegost.screenUnit,
          location.y * Belegost.screenUnit,
          Belegost.screenUnit,
          Belegost.screenUnit
        )
      }
    }
    if (playerRace == Races.Elf) {
      if (playerClass == Classes.Fighter) {
        batch.draw(
          Belegost.ElfFighterPC,
          location.x * Belegost.screenUnit,
          location.y * Belegost.screenUnit,
          Belegost.screenUnit,
          Belegost.screenUnit
        )
      } else if (playerClass == Classes.Wizard) {
        batch.draw(
          Belegost.ElfWizardPC,
          location.x * Belegost.screenUnit,
          location.y * Belegost.screenUnit,
          Belegost.screenUnit,
          Belegost.screenUnit
        )
      }
    }

    batch.setColor(1, 0, 0, 0.5f)
    batch.draw(
      Belegost.Square,
      location.x * Belegost.screenUnit,
      (location.y + 1) * Belegost.screenUnit,
      (health.toFloat / maxHealth.toFloat) * Belegost.screenUnit,
      Belegost.screenUnit / 10
    )
  }
  var inInventory = false
  def setInvMenu(): Unit = {
    invMenu.itList = inventory
      .map({ case (num, ite) =>
        menuItem(
          s"x$num ${ite.name}",
          () => { ite.use() }
        )
      })
      .toList
  }
  def update(delta: Float): Unit = {

    invMenu.update()
    equipMenu.update()

    strength = baseStr + enchMod(0)
    dexterity = baseDex + enchMod(1)
    constitution = baseCon + enchMod(2)
    intelligence = baseInt + enchMod(3)
    wisdom = baseWis + enchMod(4)
    charisma = baseCha + enchMod(5)

    if (
      game.keysPressed
        .contains(Keys.SPACE)
    ) { moved = true }
    game.MoneyInDungeon.foreach(pUp => {
      if (location == pUp.location) {
        money += pUp.amount
        game.MoneyInDungeon = game.MoneyInDungeon.filterNot(mId => mId eq pUp)
      }
    })
    game.pickups.foreach(pUp => {
      if (location == pUp.location) {
        val index = game.player.inventory.indexWhere({ case (count, item) =>
          if (item == pUp.corresponding && count < 40) true else false
        })
        if (index < 0) {

          game.player.inventory.addOne((1, pUp.corresponding))
        } else {
          game.player.inventory(index) = (
            game.player.inventory(index)._1 + 1,
            game.player.inventory(index)._2
          )
        }
        game.pickups = game.pickups.filterNot(f => f eq pUp)

      }
    })
    val prevdest = location

    if (!moved) {
      var attackedEnemy = false
      destination = computeDestination
      navTo
      for {
        path <- pathToDest
      } {
        val nextLoc = path.getHead
        game.enemies.foreach(enemy => {
          if (enemy.location == nextLoc) {
            for (i <- 0 until speed) {
              weapon.foreach(weapon => {
                val roll = d(20)
                if (roll == 20) {
                  enemy.health -= (d(
                    weapon.dNum,
                    weapon.dAmt
                  ) + weapon.mod.toInt + ((strength - 10) / 2)) * 2
                } else if (
                  roll + weapon.mod + accurateBonus > enemy.armorClass
                ) {

                  enemy.health -= d(
                    weapon.dNum,
                    weapon.dAmt
                  ) + weapon.mod.toInt + ((strength - 10) / 2)
                }
              })

              if (weapon.isEmpty) {
                val roll = d(20)

                if (roll == 20) {
                  enemy.health -= (d(2) + ((strength - 10) / 2)) * 2
                } else if (roll > enemy.armorClass)
                  enemy.health -= d(2) + ((strength - 10) / 2)
              }
            }
            attackedEnemy = true
            destination = location
          }
        })
        if (!attackedEnemy) {
          location = nextLoc

          pathToDest = path.tail
        }
        if (location != prevdest || attackedEnemy) moved = true
        attackedEnemy = false
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
    if (!inInventory && !inEquip && !inSpellList) {
      if (game.keysPressed.contains(19)) {
        if (game.keysPressed.contains(22)) {
          location + Delta(1, 1)
        } else if (game.keysPressed.contains(21)) {
          location + Delta(-1, 1)
        } else {
          location + Delta(0, 1)
        }

      } else if (game.keysPressed.contains(20)) {
        if (game.keysPressed.contains(22)) {
          location + Delta(1, -1)
        } else if (game.keysPressed.contains(21)) {
          location + Delta(-1, -1)
        } else {
          location + Delta(0, -1)
        }

      } else if (game.keysPressed.contains(21)) {
        location + (Delta(-1, 0))

      } else if (game.keysPressed.contains(22)) {
        location + (Delta(1, 0))

      } else {
        destination
      }
    } else {
      destination
    }
  }

}
