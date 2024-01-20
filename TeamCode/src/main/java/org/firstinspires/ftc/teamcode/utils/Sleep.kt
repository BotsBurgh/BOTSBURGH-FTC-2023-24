package org.firstinspires.ftc.teamcode.utils

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode

fun opModeSleep(opMode: OpMode): (Long) -> Unit =
    if (opMode is LinearOpMode) {
        opMode::sleep
    } else {
        ::nonLinearSleep
    }

/**
 * A version of [LinearOpMode.sleep] that works for [OpMode]s.
 */
private fun nonLinearSleep(ms: Long) {
    try {
        Thread.sleep(ms)
    } catch (e: InterruptedException) {
        Thread.currentThread().interrupt()
    }
}
