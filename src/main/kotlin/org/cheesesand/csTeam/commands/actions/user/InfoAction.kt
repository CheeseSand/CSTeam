package org.cheesesand.csTeam.commands.actions.user

import kotlinx.serialization.json.Json
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.PlayerDataStruct
import org.cheesesand.csTeam.TeamDataStruct
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import org.cheesesand.csTeam.teamNotFound
import org.cheesesand.csTeam.unknownError
import org.cheesesand.csTeam.isNotAPlayer
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class InfoAction(private val plugin: JavaPlugin): TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        val teamName: String

        if(args.size <= 0){
            if (sender !is Player) {
                throw isNotAPlayer
            }

            val senderUUID: UUID = Bukkit.getPlayerUniqueId(sender.name) ?: throw unknownError
            val playerDataFolder = File(plugin.dataFolder, "playerData")
            val playerfile = File(playerDataFolder, "${senderUUID}.dat")

            if (!playerfile.exists()) {
                throw teamNotFound
            }

            val playerData: PlayerDataStruct = Json.decodeFromString(playerfile.readText())

            teamName = playerData.teamName ?: throw teamNotFound
        }else{
            teamName = args[0]
        }

        val file = File(plugin.dataFolder, "teamData.json")
        val teams: List<TeamDataStruct> = Json.decodeFromString(file.readText())
        val team = teams.find { it.name == teamName } ?: throw teamNotFound

        sender.sendMessage(Component.text("---- 팀 정보 ----"))
        sender.sendMessage(Component.text("팀 이름: ${team.name}"))
        sender.sendMessage(Component.text("팀장: ${Bukkit.getOfflinePlayer(team.owner).name}"))
        sender.sendMessage(Component.text("멤버 수: ${team.members.size}명"))
        sender.sendMessage(Component.text("--------------"))
    }
}