package org.cheesesand.csTeam.commands.actions.user

import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import org.teamcrez.daydream.event.TabCompleteEvent

class SettingAction(private val plugin: JavaPlugin): TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        sender.sendMessage("SettingAction")
    }

    override fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String> {
        return mutableListOf()
    }
}