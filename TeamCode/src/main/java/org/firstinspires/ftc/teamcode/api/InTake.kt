package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor

object InTake : API() {
    lateinit var flyWheel1: CRServo
        private set
    lateinit var flyWheel2: CRServo
        private set
    lateinit var inTakeConvey: DcMotor
        private set

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.flyWheel1 = hardwareMap.get(CRServo::class.java, "flyWheel1")
        this.flyWheel2 = hardwareMap.get(CRServo::class.java, "flyWheel2")
        this.inTakeConvey = hardwareMap.get(DcMotor::class.java, "inTakeConvey")

        resetEncoders()
    }

    fun resetEncoders() {
        inTakeConvey.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        inTakeConvey.mode = DcMotor.RunMode.RUN_USING_ENCODER
    }

    /**
     * Starts moving the conveyor belt.
     */
    fun conveyMovement() {
        inTakeConvey.power = 1.0
    }

    /**
     * Stops the conveyor belt.
     */
    fun conveyorStop() {
        inTakeConvey.power = 0.0
    }

    /**
     * Starts moving the flywheels.
     */
    fun flyWheelMovement() {
        flyWheel1.power = 1.0
        flyWheel2.power = 1.0
    }

    /**
     * Starts moving the flywheels in reverse.
     */
    fun flyWheelReverseMovement() {
        flyWheel1.power = -1.0
        flyWheel2.power = -1.0
    }

    // functions to be used during auto to move flyWheel and Conveyor Belt
    fun flyWheelPosition(final: Int) {
        // commented out as no current way to check for position
        //while ( flyWheelEncoder.currentPosition < final) {
        //flyWheelMovement()
        // }
    }

    fun conveyPosition(final: Int) {
        while (inTakeConvey.currentPosition < final) {
            conveyMovement()
        }
    }
}
