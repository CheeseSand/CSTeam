package org.cheesesand.csTeam.commands.actions

import org.bukkit.command.CommandSender
import org.teamcrez.daydream.event.TabCompleteEvent

abstract class TeamActionCommand {
    abstract fun execute(sender: CommandSender, args: ArrayList<String>)
    abstract fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String>
}