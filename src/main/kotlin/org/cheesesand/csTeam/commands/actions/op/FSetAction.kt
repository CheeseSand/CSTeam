package org.cheesesand.csTeam.commands.actions.op

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.cheesesand.csTeam.*
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import org.teamcrez.daydream.event.TabCompleteEvent

class FSetAction: TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        if(!sender.isOp){
            throw noHavePermission
        }

        sender.sendMessage("FSetAction")
    }

    override fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String> {
        if (tabCompleteEvent.args?.size == 2) {
            return Bukkit.getOnlinePlayers().map {
                it.name
            }.filter {
                it.startsWith(tabCompleteEvent.args.lastOrNull() ?: "", ignoreCase = true)
            }.toMutableList()
        }

        return mutableListOf()
    }
}