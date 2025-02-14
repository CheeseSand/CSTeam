package org.cheesesand.csTeam.commands.actions.user

import kotlinx.serialization.json.Json
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.*
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import org.teamcrez.daydream.event.TabCompleteEvent
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class InviteAction(private val plugin: JavaPlugin): TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        if(args.size != 1){
//            throw invalidArguments
            return
        }

        if(sender !is Player){
            throw isNotAPlayer
        }

        val target = Bukkit.getPlayer(args[0]) ?: throw playerNotFound

        val existingInvitation = activeInvitations.find {
            it.receiver == target && it.expirationTime > System.currentTimeMillis()
        }

        if (existingInvitation != null) {
            sender.sendMessage(Component.text("${target.name}님은 이미 유효한 초대를 받았습니다."))
            return
        }

        var playerUUID: UUID = sender.uniqueId
        val playerDataFolder = File(plugin.dataFolder, "playerData")
        var file = File(playerDataFolder, "${playerUUID}.dat")

        if (!file.exists()) {
//            throw teamNotFound
            // 팀 소속 X throw
            return
        }

        val playerData: PlayerDataStruct = Json.decodeFromString(file.readText())
        val teamName = playerData.teamName ?: throw teamNotFound

        file = File(plugin.dataFolder, "teamData.json")
        val teams: List<TeamDataStruct> = Json.decodeFromString(file.readText())
        val team = teams.find { it.name == teamName } ?: throw unknownError

        if(team.owner != playerUUID){
//            throw notOwner
            return
        }

        playerUUID = target.uniqueId
        file = File(playerDataFolder, "${playerUUID}.dat")

        if (file.exists()) {
            // 이티 팀 있음 에러
            return
        }

        val expirationTime = System.currentTimeMillis() + 2 * 60 * 1000
        val invitation = Invitation(sender, target, expirationTime)
        activeInvitations.add(invitation)

        target.sendMessage(Component.text("${sender.name}님이 당신을 초대했습니다. 수락하려면 /csteam accept, 거절하려면 /csteam deny 을 입력하세요."))

        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            if (activeInvitations.contains(invitation)) {
                activeInvitations.remove(invitation)
                sender.sendMessage(Component.text("${target.name}님이 초대를 수락하지 않아 만료되었습니다."))
                target.sendMessage(Component.text("${sender.name}님의 초대가 만료되었습니다."))
            }
        }, 2 * 60 * 20L)
    }

    override fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String> {
        if (tabCompleteEvent.args?.size == 2) {
            return Bukkit.getOnlinePlayers().map {
                it.name
            }.filter {
                it.startsWith(tabCompleteEvent.args.lastOrNull() ?: "", ignoreCase = true)
            }.toMutableList()
        }

        return mutableListOf()
    }
}