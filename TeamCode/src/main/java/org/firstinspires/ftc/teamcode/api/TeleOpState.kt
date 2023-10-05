package org.firstinspires.ftc.teamcode.api

/**
 * A state machine for teleop used to configure behavior.
 *
 * Its current purpose it to display whether the robot is automatically target-locking an april tag
 * or if it should control normally.
 */
object TeleOpState : API() {
    enum class State {
        Default,
    }

    /**
     * The single source of truth for what state the robot is in.
     *
     * Be very careful about changing this value, as it affects many other things.
     */
    var state = State.Default
}
