package org.cheesesand.csTeam.events

import kotlinx.serialization.json.Json
import net.luckperms.api.LuckPerms
import net.luckperms.api.cacheddata.CachedMetaData
import net.luckperms.api.model.user.User
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.PlayerDataStruct
import org.cheesesand.csTeam.TeamDataStruct
import org.cheesesand.csTeam.teamChatStatus
import java.io.File

class PlayerChatEvent(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        var format = "${event.player.name}: ${event.message}"

        val provider = Bukkit.getServicesManager().getRegistration(LuckPerms::class.java)

        var prefix = ""
        var suffix = ""

        if (provider != null) {
            val luckPerms = provider.provider
            val user: User? = luckPerms.userManager.getUser(event.player.uniqueId)

            if (user != null) {
                val metaData: CachedMetaData = user.cachedData.metaData

                prefix = ChatColor.translateAlternateColorCodes('&', metaData.prefix ?: "")
                suffix = ChatColor.translateAlternateColorCodes('&', metaData.suffix ?: "")

                format = "$prefix${event.player.name}$suffix: ${event.message}"
            }
        }

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

            event.recipients.clear()

            for (member in team.members) {
                val player = Bukkit.getPlayer(member) ?: continue
                event.recipients.add(player)
            }

            format = "$prefix${event.player.name}$suffix: !!" + ChatColor.GRAY + event.message
        }

        event.player.sendMessage(format)
        event.format = format
    }
}