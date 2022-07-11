package org.eamonn.belegost

trait StatusEffect {
  def tick(): Unit
  val target: Entity
  var duration: Int
  def initial(): Unit
  def end(): Unit
  var name: String
}

case class Poisoned(
    val target: Entity,
    var duration: Int,
    player: Player,
    damage: Int,
    save: Int
) extends StatusEffect {
  def tick(): Unit = {
    target.health -= damage
    duration -= 1

  }
  def initial(): Unit = {
    player.baseCon -= 1
  }
  def end(): Unit = {
    player.baseCon += 1
  }
  var name = "Poison " + damage
}

case class Burning(
    val target: Entity,
    var duration: Int,
    player: Player,
    damage: Int
) extends StatusEffect {
  def tick(): Unit = {
    target.health -= damage
    duration -= 1
  }
  def initial(): Unit = {}
  def end(): Unit = {}

  var name = "Burning " + damage
}

case class Starving(
    var duration: Int,
    player: Player
) extends StatusEffect {
  val target: Entity = player
  var tier = 0
  def tick(): Unit = {

    player.damDealtMod += tier
    player.damRecievedMod -= tier
    duration += 1
    if (duration % 5 == 0) {
      tier += 1
    }
    player.damDealtMod -= tier
    player.damRecievedMod += tier

    if (player.hunger > 15) {
      end
      duration = 0
    }
    name = "Starving " + tier
  }
  def initial(): Unit = {
    player.damDealtMod -= tier
    player.damRecievedMod += tier
    player.baseStr -= 1
    player.baseDex -= 1
    player.baseCon -= 1
    player.baseInt -= 1
    player.baseWis -= 1
    player.baseCha -= 1
  }
  def end(): Unit = {
    player.damDealtMod += tier
    player.damRecievedMod -= tier
    player.baseStr += 1
    player.baseDex += 1
    player.baseCon += 1
    player.baseInt += 1
    player.baseWis += 1
    player.baseCha += 1
  }

  var name = "Starving " + tier
}
