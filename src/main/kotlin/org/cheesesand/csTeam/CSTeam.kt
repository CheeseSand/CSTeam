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
        }



        dataFolder = File(dataFolder, "teamData")
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }

        logger.info("CSTeam Enabled!")
    }

    override fun onDisable() {
        logger.info("CSTeam Disabled!")
    }
}
