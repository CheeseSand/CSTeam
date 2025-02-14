package org.cheesesand.csTeam.commands.actions.user

import org.bukkit.command.CommandSender
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import org.teamcrez.daydream.event.TabCompleteEvent

class HelpAction: TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        sender.sendMessage("HelpAction")
    }

    override fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String> {
        if(tabCompleteEvent.args?.size == 2){
            return mutableListOf("[명령어]")
        }

        return mutableListOf()
    }
}