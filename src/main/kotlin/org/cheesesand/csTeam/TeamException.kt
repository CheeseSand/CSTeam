package org.cheesesand.csTeam

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor

class TeamException(val component: Component) : Throwable()

val isNotAPlayer = TeamException(Component.text("해당 명령어는 플레이어가 아니면 실행할 수 없습니다.")
    .color(TextColor.fromHexString("#FF5555")))

val executedCommandIsNotCorrect = TeamException(Component.text("실행할 명령어가 올바르지 않습니다.")
    .color(TextColor.fromHexString("#FF5555")))

val noHavePermission = TeamException(Component.text("해당 명령어를 사용할 수 있는 권한이 없습니다.")
    .color(TextColor.fromHexString("#FF5555")))

val unknownError = TeamException(Component.text("알 수 없는 오류입니다. 관리자한테 문의하십시오.")
    .color(TextColor.fromHexString("#FF5555")))

val alreadyExistsTeam = TeamException(Component.text("이미 존재하는 팀 이름입니다.")
    .color(TextColor.fromHexString("#FF5555")))

val alreadyJoinTeam = TeamException(Component.text("이미 다른 팀에 들어가있습니다.")
    .color(TextColor.fromHexString("#FF5555")))

val teamNotFound = TeamException(Component.text("해당 팀을 찾을 수 없습니다.")
    .color(TextColor.fromHexString("#FF5555")))