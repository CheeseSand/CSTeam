package org.cheesesand.csTeam

import net.luckperms.api.LuckPerms
import org.bukkit.Bukkit
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
                logger.severe("playerData 폴더 생성을 실패하였습니다!")
                server.pluginManager.disablePlugin(this)
                return
            }

            logger.info("playerData 폴더 생성 완료!")
        }

        dataFolder = File(dataFolder, "teamData")
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()

            dataFolder = File(dataFolder, "teamData")
            if (!dataFolder.exists()){
                logger.severe("teamData 폴더 생성을 실패하였습니다!")
                server.pluginManager.disablePlugin(this)
                return
            }
            
            logger.info("teamData 폴더 생성 완료!")
        }

        var configFile = File(dataFolder, "config.yml")

        saveDefaultConfig()

        if (!configFile.exists()) {
            configFile = File(dataFolder, "config.yml")

            if(!configFile.exists()){
                logger.severe("기본 설정 파일(config.yml) 생성을 실패하였습니다!")
                server.pluginManager.disablePlugin(this)
                return
            }

            logger.info("기본 설정 파일(config.yml) 생성 완료!")
        }

        val provider = Bukkit.getServicesManager().getRegistration(LuckPerms::class.java)

        if (provider == null) {
            logger.severe("LuckPerms 플러그인을 찾을 수 없습니다!")
            server.pluginManager.disablePlugin(this)
            return
        }

        val api: LuckPerms = provider.provider
        logger.info("LuckPerms 발견! ${api.pluginMetadata.version}")

        logger.info("CSTeam Enabled!")
    }

    override fun onDisable() {
        logger.info("CSTeam Disabled!")
    }
}
