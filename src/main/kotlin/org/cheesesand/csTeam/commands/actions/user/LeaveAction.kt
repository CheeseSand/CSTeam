package org.cheesesand.csTeam.commands.actions.user

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import org.cheesesand.csTeam.noHaveTeam
import java.io.File

class LeaveAction(private val plugin: JavaPlugin): TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        sender.sendMessage("LeaveAction")

//        val targetPlayerUUID = sender.
//
//        val playerDataFolder = File(plugin.dataFolder, "playerData")
//        val file = File(playerDataFolder, "${targetPlayerUUID}.dat")
//
//        if(!file.exists()){
//            throw noHaveTeam
//        }
//
//        file.delete()

        //json 파일에서 해당 플레이어의 팀을 제거
    }
}