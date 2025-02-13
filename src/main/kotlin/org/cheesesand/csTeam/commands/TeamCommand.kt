package org.cheesesand.csTeam.commands

import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.TeamException
import org.cheesesand.csTeam.commands.actions.*
import org.cheesesand.csTeam.commands.actions.op.*
import org.cheesesand.csTeam.commands.actions.user.*
import org.cheesesand.csTeam.executedCommandIsNotCorrect
import org.teamcrez.daydream.event.CommandExecuteEvent
import org.teamcrez.daydream.event.TabCompleteEvent
import org.teamcrez.daydream.wrapper.CommandObject

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
        actions["remove"] = RemoveAction()

        actions["delete"] = DeleteAction()
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

        if (tabCompleteEvent.args.size == 1) {
            return actions.keys.toMutableList()
        }

        return ArrayList()
    }
}