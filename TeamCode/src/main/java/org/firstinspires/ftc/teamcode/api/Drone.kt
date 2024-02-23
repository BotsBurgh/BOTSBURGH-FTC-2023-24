package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.CRServo

/**
 * An API to control the drone launcher.
 */
object Drone : API() {
    private lateinit var droneLeft: CRServo
    private lateinit var droneRight: CRServo

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.droneLeft = this.opMode.hardwareMap.get(CRServo::class.java, "droneLeft")
        this.droneRight = this.opMode.hardwareMap.get(CRServo::class.java, "droneRight")
    }

    fun spin() {
        this.droneLeft.power = 1.0
        this.droneRight.power = -1.0
    }

    fun stop() {
        this.droneLeft.power = 0.0
        this.droneRight.power = 0.0
    }
}
