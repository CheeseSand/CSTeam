package org.cheesesand.csTeam.commands.actions

import org.bukkit.command.CommandSender

abstract class TeamActionCommand {
    abstract fun execute(sender: CommandSender, args: ArrayList<String>)
}