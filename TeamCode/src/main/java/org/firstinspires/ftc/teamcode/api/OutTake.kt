package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo

object OutTake : API() {
    // servo
    lateinit var outTakeBelt: Servo
        private set

    // how much the conveyor belt will move to drop one pixel
    private const val outTakePosition: Double = 8.0


    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.outTakeBelt = hardwareMap.get(Servo::class.java, "ServoBelt")
    }

    /*
    drops a pixel
     */
    fun drop() {
        outTakeBelt.setPosition(outTakeBelt.position + outTakePosition)
    }
}