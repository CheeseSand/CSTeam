package org.cheesesand.csTeam.commands.actions.user

import org.bukkit.command.CommandSender
import org.cheesesand.csTeam.commands.actions.TeamActionCommand

class HelpAction: TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        sender.sendMessage("HelpAction")
    }
}