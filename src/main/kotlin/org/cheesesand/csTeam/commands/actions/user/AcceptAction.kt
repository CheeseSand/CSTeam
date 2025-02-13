package org.cheesesand.csTeam.commands.actions.user

import kotlinx.serialization.json.Json
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.PlayerDataStruct
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import org.cheesesand.csTeam.activeInvitations
import org.cheesesand.csTeam.isNotAPlayer
import java.io.File
import kotlin.collections.ArrayList

class AcceptAction(private val plugin: JavaPlugin): TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        if(sender !is Player){
            throw isNotAPlayer
        }

        val invitation = activeInvitations.find {
            it.receiver == sender
        }

        if(invitation == null){
            sender.sendMessage(Component.text("수락할 초대가 없습니다."))
            return
        }

        val playerDataFolder = File(plugin.dataFolder, "playerData")
        val playerfile = File(playerDataFolder, "${invitation.sender.uniqueId}.dat")

        val playerData: PlayerDataStruct = Json.decodeFromString(playerfile.readText())

        val player = PlayerDataStruct(
            playerUUID = sender.uniqueId,
            teamName = playerData.teamName
        )

        val jsonString = Json.encodeToString(player)
        File(playerDataFolder, "${sender.uniqueId}.dat").writeText(jsonString)

        activeInvitations.remove(invitation)
        invitation.sender.sendMessage(Component.text("${sender.name}님이 초대를 수락했습니다."))
        sender.sendMessage(Component.text("${invitation.sender.name}님의 초대를 수락했습니다."))
    }
}