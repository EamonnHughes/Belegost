package org.eamonn.belegost.skills

import org.eamonn.belegost.Player

trait Skill {
  var cur: Int
  val max: Int
  def affect: Unit
  def add(skillTree: SkillTree): Boolean
  val name: String

}

case class Accurate(player: Player) extends Skill {
  var cur: Int = 0
  val max: Int = 3
  val name = "Increased Accuracy"
  def affect: Unit = {
    player.accurateBonus = cur
  }
  def add(skillTree: SkillTree): Boolean = {
    val before = skillTree.skills.takeWhile(skill => skill ne this)
    if (before.forall(skill => skill.cur > 0 && cur < max)) {
      cur += 1
      true
    } else {
      false
    }
  }
}

case class Evasive(player: Player) extends Skill {
  var cur: Int = 0
  val max: Int = 3
  val name = "Faster Reflexes"
  def affect: Unit = {
    player.evasiveBonus = cur
  }
  def add(skillTree: SkillTree): Boolean = {
    val before = skillTree.skills.takeWhile(skill => skill ne this)
    if (before.forall(skill => skill.cur > 0 && cur < max)) {
      cur += 1
      true
    } else {
      false
    }
  }
}
case class Critical(player: Player) extends Skill {
  var cur: Int = 0
  val max: Int = 1
  val name = "Precise Strikes"
  def affect: Unit = {
    player.evasiveBonus = cur
  }
  def add(skillTree: SkillTree): Boolean = {
    val before = skillTree.skills.takeWhile(skill => skill ne this)
    if (
      before.forall(skill => skill.cur > 0 && cur < max && skillTree.total >= 4)
    ) {
      cur += 1
      true
    } else {
      false
    }
  }
}
