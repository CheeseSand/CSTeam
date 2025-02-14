package org.cheesesand.csTeam.commands

import kotlinx.serialization.json.Json
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.TeamDataStruct
import org.cheesesand.csTeam.TeamException
import org.cheesesand.csTeam.commands.actions.*
import org.cheesesand.csTeam.commands.actions.op.*
import org.cheesesand.csTeam.commands.actions.user.*
import org.cheesesand.csTeam.executedCommandIsNotCorrect
import org.teamcrez.daydream.event.CommandExecuteEvent
import org.teamcrez.daydream.event.TabCompleteEvent
import org.teamcrez.daydream.wrapper.CommandObject
import java.io.File

class TeamCommand(plugin: JavaPlugin): CommandObject() {

    private val actions = HashMap<String, TeamActionCommand>()
    init {
        actions["accept"] = AcceptAction(plugin)
        actions["chat"] = ChatAction()
        actions["create"] = CreateAction(plugin)
        actions["deny"] = DenyAction()
        actions["help"] = HelpAction()
        actions["info"] = InfoAction(plugin)
        actions["invite"] = InviteAction(plugin)
        actions["kick"] = KickAction(plugin)
        actions["leave"] = LeaveAction(plugin)
        actions["list"] = ListAction(plugin)
        actions["remove"] = RemoveAction(plugin)
        actions["setting"] = SettingAction(plugin)

        actions["delete"] = DeleteAction(plugin)
        actions["fkick"] = FKickAction(plugin)
        actions["fset"] = FSetAction()
        actions["reset"] = ResetAction(plugin)
    }

    override fun execute(event: CommandExecuteEvent): Boolean {
        try{
            if(event.args.isEmpty()){
                actions["help"]?.execute(event.sender, ArrayList())
                return true
            }

            if(!actions.keys.contains(event.args[0])){
                throw executedCommandIsNotCorrect
            }

            val command = actions[event.args[0]] ?: throw executedCommandIsNotCorrect

            command.execute(event.sender, ArrayList(event.args.drop(1)))
            return true
        } catch (exception: TeamException) {
            event.sender.sendMessage(exception.component)
            return false
        }
    }

    override fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String> {
        if (tabCompleteEvent.args == null) {
            return actions.keys.toMutableList()
        }

        val currentInput = tabCompleteEvent.args.lastOrNull() ?: ""

        if (tabCompleteEvent.args.size == 1) {
            return actions.keys.filter {
                it.startsWith(currentInput, ignoreCase = true)
            }.toMutableList()
        }

        if(tabCompleteEvent.args.size == 2){
            if(tabCompleteEvent.args[0] == "create"){
                return mutableListOf("[팀 이름]")
            }

            if(tabCompleteEvent.args[0] == "help"){
                return mutableListOf("[명령어]")
            }

            if(tabCompleteEvent.args[0] == "info" || tabCompleteEvent.args[0] == "delete"){
//                val file = File(plugin.dataFolder, "teamData.json")
//                val teams: List<TeamDataStruct> = Json.decodeFromString(file.readText())
//
//                return teams.map {
//                    it.name
//                }.filter {
//                    it.startsWith(currentInput, ignoreCase = true)
//                }.toMutableList()
                return mutableListOf("[팀 이름]")
            }

            if(tabCompleteEvent.args[0] == "invite" || tabCompleteEvent.args[0] == "kick" || tabCompleteEvent.args[0] == "fkick" || tabCompleteEvent.args[0] == "fset"){
                return Bukkit.getOnlinePlayers().map {
                    it.name
                }.filter {
                    it.startsWith(currentInput, ignoreCase = true)
                }.toMutableList()
            }

            if (tabCompleteEvent.args[0] == "list"){
                return mutableListOf("[페이지]")
            }
        }

        return mutableListOf()
    }
}