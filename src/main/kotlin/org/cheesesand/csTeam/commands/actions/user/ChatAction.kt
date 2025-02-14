package org.cheesesand.csTeam.commands.actions.user

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import org.cheesesand.csTeam.isNotAPlayer
import org.cheesesand.csTeam.teamChatStatus

class ChatAction: TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        if(sender !is Player){
            throw isNotAPlayer
        }

        val chatStatus = teamChatStatus[sender.uniqueId] ?: false
        teamChatStatus[sender.uniqueId] = !chatStatus
        sender.sendMessage(teamChatStatus[sender.uniqueId].toString())
    }
}