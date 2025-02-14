package org.cheesesand.csTeam.commands.actions.user

import kotlinx.serialization.json.Json
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.PlayerDataStruct
import org.cheesesand.csTeam.TeamDataStruct
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import org.cheesesand.csTeam.isNotAPlayer
import org.cheesesand.csTeam.noHaveTeam
import org.cheesesand.csTeam.unknownError
import org.teamcrez.daydream.event.TabCompleteEvent
import java.io.File

class LeaveAction(private val plugin: JavaPlugin): TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        if(sender !is Player){
            throw isNotAPlayer
        }

        val playerDataFolder = File(plugin.dataFolder, "playerData")
        var file = File(playerDataFolder, "${sender.uniqueId}.dat")

        if(!file.exists()){
            throw noHaveTeam
        }

        val teamName = Json.decodeFromString<PlayerDataStruct>(file.readText()).teamName

        file.delete()

        file = File(plugin.dataFolder, "teamData.json")
        val teams: List<TeamDataStruct> = Json.decodeFromString(file.readText())
        val team = teams.find { it.name == teamName } ?: throw unknownError

        if(team.owner == sender.uniqueId){
//            throw isOwner
            return
        }


        //json 파일에서 해당 플레이어의 팀을 제거
    }

    override fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String> {
        return mutableListOf()
    }
}