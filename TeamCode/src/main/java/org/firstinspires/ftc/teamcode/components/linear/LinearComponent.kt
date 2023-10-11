package org.firstinspires.ftc.teamcode.components.linear

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

/**
 * A linear component is a class that makes the robot do something during autonomous.
 *
 * Unlike normal components, linear components are meant to be run during the autonomous phase. They
 * are run in sequence, and thus use a [LinearOpMode] instead of a normal OpMode.
 */
interface LinearComponent {
    /**
     * Runs the linear component.
     *
     * This function will be run once. Unless programmed otherwise, this component will have
     * complete control of the robot when [run] is called.
     */
    fun run(linearOpMode: LinearOpMode)
}
