package org.cheesesand.csTeam.commands.actions.op

import org.bukkit.command.CommandSender
import org.cheesesand.csTeam.*
import org.cheesesand.csTeam.commands.actions.TeamActionCommand

class FKickAction: TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        if(!sender.isOp){
            throw noHavePermission
        }

        sender.sendMessage("FKickAction")
    }
}