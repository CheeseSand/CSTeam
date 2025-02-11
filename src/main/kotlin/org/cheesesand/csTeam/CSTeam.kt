package org.cheesesand.csTeam

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class CSTeam : JavaPlugin(), Listener {
    override fun onEnable() {
        server.pluginManager.apply {
            this.registerEvents(OnEvent(this@CSTeam), this@CSTeam)
        }

//        val commandMap = HashMap<String, CommandObject>()
//
//        val daydream = DayDream(this.server)

        var dataFolder = File(dataFolder, "playerData")
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()

            dataFolder = File(dataFolder, "teamData")
            if (!dataFolder.exists()){
                logger.info("playerData 폴더 생성을 실패하였습니다!")
                server.pluginManager.disablePlugin(this)
            }

            logger.info("playerData 폴더 생성 완료!")
        }

        dataFolder = File(dataFolder, "teamData")
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()

            dataFolder = File(dataFolder, "teamData")
            if (!dataFolder.exists()){
                logger.info("teamData 폴더 생성을 실패하였습니다!")
                server.pluginManager.disablePlugin(this)
            }
            
            logger.info("teamData 폴더 생성 완료!")
        }

        var configFile = File(dataFolder, "config.yml")

        saveDefaultConfig()

        if (!configFile.exists()) {
            configFile = File(dataFolder, "config.yml")

            if(!configFile.exists()){
                logger.info("기본 설정 파일(config.yml) 생성을 실패하였습니다!")
                server.pluginManager.disablePlugin(this)
            }

            logger.info("기본 설정 파일(config.yml) 생성 완료!")
        }

        logger.info("CSTeam Enabled!")
    }

    override fun onDisable() {
        logger.info("CSTeam Disabled!")
    }
}
