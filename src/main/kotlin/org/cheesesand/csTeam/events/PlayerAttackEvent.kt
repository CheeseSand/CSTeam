package org.cheesesand.csTeam.events

import kotlinx.serialization.json.Json
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.PlayerDataStruct
import org.cheesesand.csTeam.TeamDataStruct
import java.io.File

class PlayerAttackEvent(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onPlayerAttack(event: EntityDamageByEntityEvent) {
        val damager = event.damager
        val entity = event.entity

        if (damager !is Player || entity !is Player) {
            return
        }

        val playerDataFolder = File(plugin.dataFolder, "playerData")
        var file = File(playerDataFolder, "${event.damager.uniqueId}.dat")

        if (!file.exists()) {
            return
        }

        val dTeamName = Json.decodeFromString<PlayerDataStruct>(file.readText()).teamName ?: return
        file = File(playerDataFolder, "${event.entity.uniqueId}.dat")

        if (!file.exists()) {
            return
        }

        val eTeamName = Json.decodeFromString<PlayerDataStruct>(file.readText()).teamName ?: return

        if (dTeamName != eTeamName) {
            return
        }

        file = File(plugin.dataFolder, "teamData.json")
        val teams: List<TeamDataStruct> = Json.decodeFromString(file.readText())
        val team = teams.find { it.name == dTeamName } ?: return

        if (team.setting["team_pvp"] ?: return) {
            return
        }

        event.isCancelled = true
    }
}