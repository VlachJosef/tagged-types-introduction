package com.vlachjosef.scalaz

import org.scalatest.{FlatSpec, Matchers}

class TaggedTypesSuite extends FlatSpec with Matchers {

  "Simple types" should "provide *less* type safety and they do :(" in {

    import com.vlachjosef.scalaz.notypes._

    val firePit    = Arena("firePit", "Fire Pit")
    val iceDungeon = Arena("iceDungeon", "Ice Dungeon")
    val bracket1 = Bracket("bracket1", "firePit")
    val bracket2 = Bracket("bracket2", "firePit")
    val bracket3 = Bracket("bracket3", "iceDungeon")
    val player1 = PlayerProfile("player1", Map(firePit.id -> bracket1.id, iceDungeon.id -> bracket3.id))
    val player2 = PlayerProfile("player2", Map(firePit.id -> bracket1.id))
    val player3 = PlayerProfile("player3", Map(firePit.id -> bracket2.id, iceDungeon.id -> bracket3.id))

    val player4 = PlayerProfile("player4", Map(bracket1.id -> firePit.id)) // This compiles, but it's bug

    player4.bracketMapping.get("firePit") should be(Some("bracket1")) // this will fail
  }

  "Tagged types" should "provide *more* type safety and they do :)" in {
    import com.vlachjosef.scalaz.tags._
    import scalaz.Tag

    val thePitId     = Tag.of[ArenaIdTag]("firePit")
    val iceDungeonId = Tag.of[ArenaIdTag]("iceDungeon")
    val bracket1Id = Tag.of[BracketIdTag]("bracket1")
    val bracket2Id = Tag.of[BracketIdTag]("bracket2")
    val bracket3Id = Tag.of[BracketIdTag]("bracket3")
    val player1Id = Tag.of[ProfileIdTag]("player1")
    val player2Id = Tag.of[ProfileIdTag]("player2")
    val player3Id = Tag.of[ProfileIdTag]("player3")
    val player4Id = Tag.of[ProfileIdTag]("player4")

    val firePit    = Arena(thePitId, "Fire Pit")
    val iceDungeon = Arena(iceDungeonId, "Ice Dungeon")
    val bracket1 = Bracket(bracket1Id, thePitId)
    val bracket2 = Bracket(bracket2Id, thePitId)
    val bracket3 = Bracket(bracket3Id, iceDungeonId)
    val player1 = PlayerProfile(player1Id, Map(firePit.id -> bracket1.id, iceDungeon.id -> bracket3.id))
    val player2 = PlayerProfile(player2Id, Map(firePit.id -> bracket1.id))
    val player3 = PlayerProfile(player3Id, Map(firePit.id -> bracket2.id, iceDungeon.id -> bracket3.id))

    "val player4 = PlayerProfile(player4Id, Map(bracket1.id -> firePit.id))" shouldNot typeCheck

  }
}
