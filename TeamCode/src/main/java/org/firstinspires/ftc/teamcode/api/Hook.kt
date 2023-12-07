package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo

object Hook: API() {
    private lateinit var rightServo: Servo
    private lateinit var leftServo: Servo

    override fun init(opMode: OpMode) {

        super.init(opMode)

        this.rightServo = this.hardwareMap.get(Servo::class.java, "rightServo")
        this.leftServo = this.hardwareMap.get(Servo::class.java, "leftServo")
    }
    fun openPosition() {
        this.rightServo.position = 0.5
        this.leftServo.position = 0.5
    }

    fun closePosition() {
        this.rightServo.position = 0.0
        this.leftServo.position = 0.0
    }

}