package org.cheesesand.csTeam

import kotlinx.serialization.json.Json
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.plugin.java.JavaPlugin
import net.luckperms.api.LuckPerms
import net.luckperms.api.cacheddata.CachedMetaData
import net.luckperms.api.model.user.User
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.io.File

class OnEvent(private val plugin: JavaPlugin) : Listener {
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

    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        var format = "${event.player.name}: ${event.message}"

        val provider = Bukkit.getServicesManager().getRegistration(LuckPerms::class.java)

        if (provider != null) {
            val luckPerms = provider.provider
            val user: User? = luckPerms.userManager.getUser(event.player.uniqueId)

            if (user != null) {
                val metaData: CachedMetaData = user.cachedData.metaData
                var prefix = metaData.prefix ?: ""
                var suffix = metaData.suffix ?: ""

                prefix = ChatColor.translateAlternateColorCodes('&', prefix)
                suffix = ChatColor.translateAlternateColorCodes('&', suffix)

                format = "$prefix${event.player.name}$suffix: ${event.message}"
            }
        }

        event.format = format

        val chatStatus = teamChatStatus[event.player.uniqueId] ?: false
        if(chatStatus){
            val playerDataFolder = File(plugin.dataFolder, "playerData")
            var file = File(playerDataFolder, "${event.player.uniqueId}.dat")

            if (!file.exists()) {
                return
            }

            val teamName = Json.decodeFromString<PlayerDataStruct>(file.readText()).teamName ?: return

            file = File(plugin.dataFolder, "teamData.json")
            val teams: List<TeamDataStruct> = Json.decodeFromString(file.readText())
            val team = teams.find { it.name == teamName } ?: return

            // 특정 플레이어 한태만 메세지 전송(team.members)
        }
    }
}