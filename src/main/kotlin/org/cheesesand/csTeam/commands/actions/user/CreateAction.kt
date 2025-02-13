package org.cheesesand.csTeam.commands.actions.user

import kotlinx.serialization.json.Json
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.*
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import java.io.File
import java.util.UUID


class CreateAction(private val plugin: JavaPlugin): TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        if(sender !is Player){
            throw isNotAPlayer
        }

        val teamName: String = args[0]
        var file = File(plugin.dataFolder, "teamData.json")
        val teams: List<TeamDataStruct> = Json.decodeFromString(file.readText())

        if(teams.any { it.name == teamName }){
            throw alreadyExistsTeam
        }

        val senderUUID: UUID = Bukkit.getPlayerUniqueId(sender.name) ?: throw unknownError
        var playerDataFolder = File(plugin.dataFolder, "playerData")
        file = File(playerDataFolder, "${senderUUID}.dat")

        if(file.exists()){
            val playerD = Json.decodeFromString<PlayerDataStruct>(file.readText())

            if(playerD.teamName != null){
                throw alreadyJoinTeam
            }
        }

        val team = TeamDataStruct(
            name = args[0],
            owner = senderUUID,
            members = mutableListOf(senderUUID)
        )

        val player = PlayerDataStruct(
            playerUUID = senderUUID,
            teamName = teamName
        )

        var jsonString = Json.encodeToString(team)
        file = File(plugin.dataFolder, "teamData.json")
        var context = file.readText().dropLast(1)

        if(context.last() != '[') {
            context += ","
        }

        context += "$jsonString]"

        file.writeText(context)

        jsonString = Json.encodeToString(player)
        playerDataFolder = File(plugin.dataFolder, "playerData")
        File(playerDataFolder, "${senderUUID}.dat").writeText(jsonString)

        sender.sendMessage(Component.text("팀이 생성되었습니다."))
    }
}