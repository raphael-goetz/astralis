package de.raphaelgoetz.astralis.schedule.time

/** Units of that are available */
enum class TaskTimeTypes{
    SECONDS,
    MINUTES,
    HOURS,
    DAYS
}

/**
 * Turns the given time units into milliseconds.
 * @param time that gets turned into milliseconds.
 * @return the converted milliseconds.
 */
fun TaskTimeTypes.toMilliseconds(time: Long): Long {
    return when (this) {
        TaskTimeTypes.SECONDS -> time * 1000
        TaskTimeTypes.MINUTES -> time * 1000 * 60
        TaskTimeTypes.HOURS -> time * 1000 * 60 * 60
        TaskTimeTypes.DAYS -> time * 1000 * 60 * 60 * 24
    }
}