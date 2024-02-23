package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.utils.RobotConfig

/**
 * An API to control the drone launcher.
 */
object Drone : API() {
    private lateinit var hook: Servo

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.hook = opMode.hardwareMap.get(Servo::class.java, "droneHook")

        this.reset()
    }

    fun release() {
        this.hook.position = RobotConfig.Drone.OPEN_PIN
    }

    fun reset() {
        this.hook.position = RobotConfig.Drone.CLOSE_PIN
    }
}
