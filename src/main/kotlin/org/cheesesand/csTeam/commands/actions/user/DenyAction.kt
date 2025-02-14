package org.cheesesand.csTeam.commands.actions.user

import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import org.cheesesand.csTeam.activeInvitations
import org.cheesesand.csTeam.isNotAPlayer
import org.teamcrez.daydream.event.TabCompleteEvent

class DenyAction: TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        if(sender !is Player){
            throw isNotAPlayer
        }

        val invitation = activeInvitations.find {
            it.receiver == sender
        }

        if(invitation == null){
            sender.sendMessage(Component.text("거절할 초대가 없습니다."))
            return
        }

        activeInvitations.remove(invitation)
        invitation.sender.sendMessage(Component.text("${sender.name}님이 초대를 거절했습니다."))
        sender.sendMessage(Component.text("${invitation.sender.name}님의 초대를 거절했습니다."))
    }

    override fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String> {
        return mutableListOf()
    }
}