package org.cheesesand.csTeam.commands.actions.op

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.*
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import org.teamcrez.daydream.event.TabCompleteEvent
import java.io.File

class FKickAction(private val plugin: JavaPlugin): TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        if(!sender.isOp){
            throw noHavePermission
        }

        if(args.size <= 0){
            //throw invalidArgument
            return
        }

        val targetPlayerUUID = Bukkit.getOfflinePlayer(args[0]).uniqueId

        val playerDataFolder = File(plugin.dataFolder, "playerData")
        val file = File(playerDataFolder, "${targetPlayerUUID}.dat")

        if(!file.exists()){
            throw noHaveTeam
        }

        file.delete()

        //json 파일에서 해당 플레이어의 팀을 제거
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