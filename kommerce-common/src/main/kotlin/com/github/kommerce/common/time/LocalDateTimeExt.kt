package com.github.kommerce.common.time

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*

fun LocalDateTime.toDate(zoneId: ZoneId = ZoneOffset.UTC): Date =
    Date.from(atZone(zoneId).toInstant())