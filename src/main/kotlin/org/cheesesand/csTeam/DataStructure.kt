package org.cheesesand.csTeam

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class TeamDataStruct(
    var name: String = "",
    var color: String = "",
    var owner: SUUID = UUID(0, 0),
    var members: MutableList<SUUID> = mutableListOf(),
    var setting: HashMap<String, Boolean> = hashMapOf(
        "team_pvp" to false
    )
)

@Serializable
data class PlayerDataStruct(
    var playerUUID: SUUID,
    var teamName: String? = null
)
