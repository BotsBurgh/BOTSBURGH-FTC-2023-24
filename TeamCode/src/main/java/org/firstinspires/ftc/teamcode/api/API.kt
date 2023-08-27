package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode

/**
 * An API is a shared class used between multiple components.
 *
 * An API needs to be initialized with the [init] function before it can be used.
 */
abstract class API {
    private var uninitializedOpMode: OpMode? = null
    protected val opMode: OpMode
        get() = uninitializedOpMode ?: throw NullPointerException("<API> has not been initialized with the OpMode before being used.")

    /**
     * Initializes the API to use the given [opMode].
     *
     * This must be called before any other function provided by the API.
     *
     * @throws IllegalStateException If called more than once.
     */
    fun init(opMode: OpMode) {
        // You can only initialize an API once. If it is initialized more than once, throw an error.
        if (uninitializedOpMode != null) {
            throw IllegalStateException("Tried to initialize an <API> more than once.")
        }

        uninitializedOpMode = opMode
    }
}
