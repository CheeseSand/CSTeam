package org.cheesesand.csTeam

import net.luckperms.api.LuckPerms
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.teamcrez.daydream.DayDream
import org.cheesesand.csTeam.commands.TeamCommand
import org.teamcrez.daydream.wrapper.CommandObject
import java.io.File
import java.util.HashMap
import java.util.UUID



val activeInvitations = mutableListOf<Invitation>()
var teamChatStatus: HashMap<UUID, Boolean> = HashMap()

class CSTeam : JavaPlugin(), Listener {
    override fun onEnable() {
        fileExists()

        val provider = Bukkit.getServicesManager().getRegistration(LuckPerms::class.java)

        if (provider == null) {
            logger.severe("LuckPerms 플러그인을 찾을 수 없습니다!")
            server.pluginManager.disablePlugin(this)
            return
        }

        val api: LuckPerms = provider.provider
        logger.info("LuckPerms 발견! ${api.pluginMetadata.version}")

        registerEvents()
        syncPlotSquaredCommand()

        logger.info("CSTeam Enabled!")
    }

    override fun onDisable() {
        logger.info("CSTeam Disabled!")
    }

    private fun fileExists() {
        var file = File(dataFolder, "playerData")

        if (!file.exists()) {
            file.mkdirs()

            file = File(dataFolder, "playerData")
            if (!file.exists()){
                logger.severe("playerData 폴더 생성을 실패하였습니다!")
                server.pluginManager.disablePlugin(this)
                return
            }

            logger.info("playerData 폴더 생성 완료!")
        }

        file = File(dataFolder, "teamData.json")

        if (!file.exists()) {
            file.writeText("[]")

            file = File(dataFolder, "teamData.json")
            if (!file.exists()){
                logger.severe("teamData.json 생성을 실패하였습니다!")
                server.pluginManager.disablePlugin(this)
                return
            }

            logger.info("teamData.json 생성 완료!")
        }

        file = File(dataFolder, "config.yml")

        saveDefaultConfig()

        if (!file.exists()) {
            file = File(dataFolder, "config.yml")

            if(!file.exists()){
                logger.severe("기본 설정 파일(config.yml) 생성을 실패하였습니다!")
                server.pluginManager.disablePlugin(this)
                return
            }

            logger.info("기본 설정 파일(config.yml) 생성 완료!")
        }
    }

    private fun registerEvents() {
        server.pluginManager.apply {
            this.registerEvents(OnEvent(this@CSTeam), this@CSTeam)
        }
    }

    private fun syncPlotSquaredCommand() {
        val commandMap = HashMap<String, CommandObject>()
        commandMap["csteam"] = TeamCommand(this@CSTeam)
        val daydream = DayDream(server)
        daydream.applyCommand("csteam", commandMap)
    }
}
