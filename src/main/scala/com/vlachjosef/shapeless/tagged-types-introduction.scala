package com.vlachjosef.shapeless

package notypes {
  case class Arena(
    id: String, // unique identifier of the arena
    name: String // name of the arena
  )

  case class Bracket(
    id: String, // unique identifier of pvp bracket
    arenaId: String // to which arena this bracket belongs
  )

  case class PlayerProfile(
    id: String, // unique player profile identifier
    bracketMapping: Map[String, String] // arena to bracket mapping
  ) {
    /**
     * Change current bracket of player in arena
     */
    def changeBracket(arena: Arena, bracket: Bracket): PlayerProfile = {
      this.copy(bracketMapping = this.bracketMapping + (arena.id -> bracket.id))
    }
  }
}

package object tags {
  import shapeless.tag.@@
  import shapeless.tag

  type ArenaId   = String @@ ArenaIdTag
  type BracketId = String @@ BracketIdTag
  type ProfileId = String @@ ProfileIdTag

  val playerId: ProfileId  = tag[ProfileIdTag][String]("playerId")
  val arenaId: ArenaId     = tag[ArenaIdTag][String]("thePit")
  val bracketId: BracketId = tag[BracketIdTag][String]("bracket1")

  //PlayerProfile(playerId, Map(bracketId -> arenaId))
}

package tags {
  trait ArenaIdTag
  trait BracketIdTag
  trait ProfileIdTag

  case class Arena(
    id: ArenaId, // <- tagged type
    name: String
  )

  case class Bracket(
    id: BracketId, // <- tagged type
    arenaId: ArenaId // <- tagged type
  )

  case class PlayerProfile(
    id: ProfileId, // <- tagged type
    bracketMapping: Map[ArenaId, BracketId] // <- tagged types
  ) {
    def changeBracket(arena: Arena, bracket: Bracket): PlayerProfile = {
      this.copy(bracketMapping = this.bracketMapping + (arena.id -> bracket.id))
    }
  }
}
