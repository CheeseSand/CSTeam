package org.cheesesand.csTeam.commands.actions.user

import kotlinx.serialization.json.Json
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.TeamDataStruct
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import java.io.File

class ListAction(private val plugin: JavaPlugin): TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        val file = File(plugin.dataFolder, "teamData.json")
        val teams: List<TeamDataStruct> = Json.decodeFromString(file.readText())

        sender.sendMessage(Component.text("팀 목록: "))

        for (team in teams) {
            sender.sendMessage(Component.text(team.name))
        }
    }
}