package org.cheesesand.csTeam.commands.actions.op

import kotlinx.serialization.json.Json
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.*
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import org.teamcrez.daydream.event.TabCompleteEvent
import java.io.File

class DeleteAction(private val plugin: JavaPlugin): TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        if(!sender.isOp){
            throw noHavePermission
        }

        if(args.isEmpty()){
            // throw
            return
        }

        val playerDataFolder = File(plugin.dataFolder, "playerData")
        val file = File(plugin.dataFolder, "teamData.json")
        val teams: List<TeamDataStruct> = Json.decodeFromString(file.readText())
        val team = teams.find { it.name == args[0] } ?: throw unknownError

        for (member in team.members) {
            File(playerDataFolder, "${member}.dat").delete()
        }

        // 팀 json 파일에서 제거
    }

    override fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String> {
        if(tabCompleteEvent.args?.size == 2){
            val file = File(plugin.dataFolder, "teamData.json")
            val teams: List<TeamDataStruct> = Json.decodeFromString(file.readText())

            return teams.map {
                it.name
            }.filter {
                it.startsWith(tabCompleteEvent.args.lastOrNull() ?: "", ignoreCase = true)
            }.toMutableList()
        }

        return mutableListOf()
    }
}