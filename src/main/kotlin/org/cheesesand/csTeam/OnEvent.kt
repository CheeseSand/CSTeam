package org.cheesesand.csTeam

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class OnEvent(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onPlayerAttack(event: EntityDamageByEntityEvent) {
        val damager = event.damager
        val entity = event.entity

        if (damager is Player && entity is Player) {
            // 세부 조건 나중에 추가
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        val player = event.player
        
        // 데이터 관리 코드 따로 만들기
//        val dataFolder = File(plugin.dataFolder, "playerData")
//        val file = File(dataFolder, "${player.uniqueId}.dat")
//
//        if (!file.exists()) {
////            event.sender.sendMessage(Component.text("저장된 내용이 없습니다."))
//            //return true
//        }
//
//        try {
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            plugin.logger.info("class onPlayerChat에서 ${player.uniqueId}.dat를 불러오는데 실패했습니다.")
//        }
    }
}