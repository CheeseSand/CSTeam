package org.cheesesand.csTeam.commands

import org.cheesesand.csTeam.commands.actions.*
import org.cheesesand.csTeam.commands.actions.op.*
import org.cheesesand.csTeam.commands.actions.user.*
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
//        return try {
//            if (event.args.isNotEmpty() && actions.keys.contains(event.args[0])) {
//                actions[event.args[0]]!!.execute(event.sender, ArrayList(event.args.drop(1)))
//                true
//            } else throw executedCommandIsNotCorrect
//        } catch(exception: PlotException) {
//            event.sender.sendMessage(exception.component)
//            false
//        }
        return true
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