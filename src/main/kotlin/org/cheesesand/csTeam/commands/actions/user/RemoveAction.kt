package org.cheesesand.csTeam.commands.actions.user

import org.bukkit.command.CommandSender
import org.cheesesand.csTeam.commands.actions.TeamActionCommand

class RemoveAction: TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        sender.sendMessage("RemoveAction")
        // 팀에 있던 플레어들 playerData 파일 제거
        // 팀 json 파일에서 제거
    }
}