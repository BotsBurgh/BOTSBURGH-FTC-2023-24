package org.firstinspires.ftc.teamcode.components

import com.qualcomm.robotcore.eventloop.opmode.OpMode

/**
 * A component is a class that makes the robot do something during teleop.
 *
 * Components provoke visible change from the robot, like moving the wheels or arms. To share code
 * between components, see the API folder.
 *
 * If a component depends on another API, it should say so in the object documentation.
 */
interface Component {
    /**
     * Initializes the component.
     *
     * This is called once when the Init button is pressed on the driver station.
     *
     * @throws NotImplementedError If not overridden by the inheritor.
     */
    fun init(opMode: OpMode) {
        throw NotImplementedError("<Component>.init must be overridden before it is called.")
    }

    /**
     * Runs the component.
     *
     * This is called repeatedly while the robot is running.
     *
     * @throws NotImplementedError If not overridden by the inheritor.
     */
    fun loop(opMode: OpMode) {
        throw NotImplementedError("<Component>.loop must be overridden before it is called.")
    }
}
