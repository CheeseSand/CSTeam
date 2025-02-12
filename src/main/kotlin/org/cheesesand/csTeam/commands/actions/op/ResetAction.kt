package org.cheesesand.csTeam.commands.actions.op

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import org.cheesesand.csTeam.*
import org.cheesesand.csTeam.commands.actions.TeamActionCommand
import java.io.File

class ResetAction(private val plugin: JavaPlugin): TeamActionCommand(){
    override fun execute(sender: CommandSender, args: ArrayList<String>) {
        if(!sender.isOp){
            throw noHavePermission
        }

        sender.sendMessage(Component.text("모든 팀 데이터를 초기화 하는 중입니다.")
            .color(TextColor.fromHexString("#FF5555")))

        sender.sendMessage(Component.text("작업이 끝나기 전에 서버를 종료하지 마십시오!")
            .color(TextColor.fromHexString("#FF5555")))

        var file = File(plugin.dataFolder, "teamData.json")
        file.writeText("[]")

        file = File(plugin.dataFolder, "playerData")

        file.listFiles()?.forEach {
            it.delete()
        }

        sender.sendMessage(Component.text("모든 팀 데이터를 초기화했습니다.")
            .color(TextColor.fromHexString("#FF5555")))
    }
}