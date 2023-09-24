package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor

// APIs are shared code used by components.
// As compared to components, which can be disabled, APIs usually aren't.
// As such, using them should have no side-effects. (An API shouldn't make the robot move unless
// told to by a component.)
// This API is used to control a four-wheeled robot.
object DemoQuadWheels : API() {
    // The wheel motors
    private lateinit var fl: DcMotor
    private lateinit var fr: DcMotor
    private lateinit var bl: DcMotor
    private lateinit var br: DcMotor

    // This is how you define FTC Dashboard configuration.
    // Uncomment the following line to enable it.
    // @Config
    private object DemoQuadWheelsConfig {
        @JvmField
        var MAGIC_NUMBER = 5

        @JvmField
        var SOME_NAME = "Bob"
    }

    override fun init(opMode: OpMode) {
        // You must call super.init(opMode), or the API will not initialize correctly.
        super.init(opMode)

        this.fl = this.hardwareMap.get(DcMotor::class.java, "fl")
        this.fr = this.hardwareMap.get(DcMotor::class.java, "fr")
        this.bl = this.hardwareMap.get(DcMotor::class.java, "bl")
        this.br = this.hardwareMap.get(DcMotor::class.java, "br")
    }

    // A public function that sets the power of all four wheels.
    // This is callable by any components.
    fun drive(fl: Double, fr: Double, bl: Double, br: Double) {
        this.fl.power = fl
        this.fr.power = fr
        this.bl.power = bl
        this.br.power = br
    }

    fun drive(power: Double) {
        this.drive(power, power, power, power)
    }
}
