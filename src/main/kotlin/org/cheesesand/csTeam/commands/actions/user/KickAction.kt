package org.cheesesand.csTeam.commands.actions.user

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import org.cheesesand.csTeam.isNotAPlayer
import org.cheesesand.csTeam.noHaveTeam
import org.teamcrez.daydream.event.TabCompleteEvent
import java.io.File

class KickAction(private val plugin: JavaPlugin): TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        sender.sendMessage("KickAction")
        if(args.size <= 0){
            //throw invalidArgument
            return
        }

        if (sender !is Player){
            throw isNotAPlayer
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