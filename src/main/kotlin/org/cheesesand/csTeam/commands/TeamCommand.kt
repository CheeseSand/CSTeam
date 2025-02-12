package org.cheesesand.csTeam.commands

import org.cheesesand.csTeam.TeamException
import org.cheesesand.csTeam.commands.actions.*
import org.cheesesand.csTeam.commands.actions.op.*
import org.cheesesand.csTeam.commands.actions.user.*
import org.cheesesand.csTeam.executedCommandIsNotCorrect
import org.teamcrez.daydream.event.CommandExecuteEvent
import org.teamcrez.daydream.event.TabCompleteEvent
import org.teamcrez.daydream.wrapper.CommandObject

class TeamCommand: CommandObject() {

    private val actions = HashMap<String, TeamActionCommand>()
    init {
        actions["accept"] = AcceptAction()
        actions["chat"] = ChatAction()
        actions["create"] = CreateAction()
        actions["deny"] = DenyAction()
        actions["info"] = InfoAction()
        actions["invite"] = InviteAction()
        actions["kick"] = KickAction()
        actions["leave"] = LeaveAction()
        actions["list"] = ListAction()
        actions["remove"] = RemoveAction()

        actions["delete"] = DeleteAction()
        actions["fkick"] = FKickAction()
        actions["fset"] = FSetAction()
        actions["reset"] = ResetAction()
    }

    override fun execute(event: CommandExecuteEvent): Boolean {
        try{
            if(event.args.isEmpty() || !actions.keys.contains(event.args[0])){
                throw executedCommandIsNotCorrect
            }

            val command = actions[event.args[0]] ?: throw executedCommandIsNotCorrect

            command.execute(event.sender, ArrayList(event.args.drop(1)))
            return true
        } catch (exception: TeamException) {
            event.sender.sendMessage(exception.message ?: "")
            return false
        }
    }

    override fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String> {
        if (tabCompleteEvent.args == null) {
            return actions.keys.toMutableList()
        } else if (tabCompleteEvent.args.size == 1) {
            return actions.keys.toMutableList()
        } else {
            return ArrayList()
        }
    }
}