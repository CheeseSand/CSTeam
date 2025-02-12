package org.cheesesand.csTeam

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor

class TeamException(val component: Component) : Throwable()

val executedCommandIsNotCorrect = TeamException(Component.text("실행할 명령어가 올바르지 않습니다")
    .color(TextColor.fromHexString("#FF5555")))