package org.cheesesand.csTeam.commands.actions.user

import kotlinx.serialization.json.Json
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.TeamDataStruct
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import org.teamcrez.daydream.event.TabCompleteEvent
import java.io.File

class ListAction(private val plugin: JavaPlugin): TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        val file = File(plugin.dataFolder, "teamData.json")
        val teams: List<TeamDataStruct> = Json.decodeFromString(file.readText())
        var page = 1

        if(args.size > 0 && args[0].toIntOrNull() != null && args[0].toInt() > 0){
            page = args[0].toInt()
        }

        sender.sendMessage(Component.text("--- 팀 목록 $page ---"))

        var startIdx = (page - 1) * 10

        if(startIdx > teams.size){
            startIdx = teams.size
        }

        var endIdx = page * 10

        if(endIdx > teams.size){
            endIdx = teams.size
        }

        for(i in startIdx until endIdx){
            sender.sendMessage(Component.text("${i + 1}: ${teams[i].name}"))
        }

        sender.sendMessage(Component.text("--- /csteam list [page]로 더 보기 ---"))
    }

    override fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String> {
        if (tabCompleteEvent.args?.size == 2) {
            return mutableListOf("[페이지]")
        }

        return mutableListOf()
    }
}