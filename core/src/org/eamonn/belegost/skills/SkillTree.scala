package org.eamonn.belegost.skills

import cats.instances._
import cats.implicits._

import org.eamonn.belegost.Player

case class SkillTree(player: Player) {
  var skills: List[Skill] =
    List(Evasive(player), Accurate(player), Critical(player))
  var total = skills.foldMap(skill => skill.cur)

}
