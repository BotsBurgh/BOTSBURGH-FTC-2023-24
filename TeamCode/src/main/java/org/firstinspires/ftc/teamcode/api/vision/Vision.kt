package org.firstinspires.ftc.teamcode.api.vision

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection
import org.firstinspires.ftc.teamcode.api.API
import org.firstinspires.ftc.vision.VisionPortal

object Vision : API() {
    lateinit var portal: VisionPortal
        private set

    /**
     * Initializes the Vision Portal with a list of processors / APIs.
     *
     * You must create at least one vision API in order to use the portal.
     *
     * # Example
     *
     * ```
     * Vision.init(this, AprilVision, TFlowVision)
     * ```
     */
    fun init(opMode: OpMode, vararg visionAPIs: VisionAPI) {
        super.init(opMode)

        // Configure the builder with settings
        val builder = VisionPortal.Builder()
            .setCamera(BuiltinCameraDirection.BACK)

        // Add all of the processors
        for (visionAPI in visionAPIs) {
            builder.addProcessor(visionAPI.processor)
        }

        // Build the portal
        this.portal = builder.build()
    }

    // Vision must be initialized with at least one VisionAPI
    override fun init(opMode: OpMode) {
        throw RuntimeException("Please initialize Vision with at least one VisionAPI.")
    }
}
