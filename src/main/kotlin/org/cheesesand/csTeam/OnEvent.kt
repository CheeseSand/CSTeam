package org.cheesesand.csTeam

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

        if (/*팀이 같지 않다면*/ true) {
            return
        }

        if(/*팀 설정의 pvp가 활성화 되있다면*/ false) {
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
    }
}