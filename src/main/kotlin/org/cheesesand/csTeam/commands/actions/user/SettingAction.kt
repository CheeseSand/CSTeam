package org.cheesesand.csTeam.commands.actions.user

import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.commands.actions.TeamActionCommand

class SettingAction(private val plugin: JavaPlugin): TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        sender.sendMessage("SettingAction")
    }
}