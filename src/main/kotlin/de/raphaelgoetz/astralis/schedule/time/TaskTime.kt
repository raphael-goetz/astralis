package de.raphaelgoetz.astralis.schedule.time

enum class TaskTimeTypes{
    SECONDS,
    MINUTES,
    HOURS,
    DAYS
}

fun TaskTimeTypes.toMilliseconds(time: Long): Long {
    return when (this) {
        TaskTimeTypes.SECONDS -> time / 1000
        TaskTimeTypes.MINUTES -> time / 1000 / 60
        TaskTimeTypes.HOURS -> time / 1000 / 360
        TaskTimeTypes.DAYS -> time / 1000 / 360 / 24
    }
}