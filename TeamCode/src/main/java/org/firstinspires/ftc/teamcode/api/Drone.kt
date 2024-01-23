package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.utils.RobotConfig

/**
 * An API to control the drone launcher
 */
object Drone: API() {
    private lateinit var pin: Servo

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.pin = this.opMode.hardwareMap.get(Servo::class.java, "pin")
    }

    /** Moves the pin to the open position*/
    fun releasePin() {
        this.pin.position = RobotConfig.Drone.OPEN_PIN
    }

    fun resetPin() {
        this.pin.position = RobotConfig.Drone.CLOSE_PIN
    }


}