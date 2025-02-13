package org.cheesesand.csTeam.commands.actions.user

import kotlinx.serialization.json.Json
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.*
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import java.io.File

class RemoveAction(private val plugin: JavaPlugin): TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        if(args.size != 1){
//            throw etc
            return
        }

        if(sender !is Player){
            throw isNotAPlayer
        }

        val playerDataFolder = File(plugin.dataFolder, "playerData")
        var file = File(playerDataFolder, "${sender.uniqueId}.dat")

        if(!file.exists()){
            throw noHaveTeam
        }

        val teamName = Json.decodeFromString<PlayerDataStruct>(file.readText()).teamName

        file = File(plugin.dataFolder, "teamData.json")
        val teams: List<TeamDataStruct> = Json.decodeFromString(file.readText())
        val team = teams.find { it.name == teamName } ?: throw unknownError

        if (team.owner != sender.uniqueId) {
//            throw notOwner
            return
        }

        for (member in team.members) {
            File(playerDataFolder, "${member}.dat").delete()
        }

        // 팀 json 파일에서 제거
    }
}