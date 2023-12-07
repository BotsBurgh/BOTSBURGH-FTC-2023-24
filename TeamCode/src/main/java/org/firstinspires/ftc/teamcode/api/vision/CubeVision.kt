package org.firstinspires.ftc.teamcode.api.vision

import android.graphics.Canvas
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration
import org.firstinspires.ftc.teamcode.api.API
import org.firstinspires.ftc.teamcode.utils.Team
import org.firstinspires.ftc.vision.VisionProcessor
import org.opencv.core.Mat

/**
 * A [VisionAPI] / [VisionProcessor] that scans for the game piece in its 3 possible positions.
 *
 * Note that this is only meant to run in the beginning of the autonomous phase. Afterwards, it can
 * be disabled with [Vision.disable].
 */
object CubeVision : API(), VisionAPI, VisionProcessor {
    override val processor: VisionProcessor
        get() = this

    /**
     * Initializes the API with a given [opMode] and [cubeColor].
     */
    fun init(opMode: OpMode, cubeColor: Team) {
        super.init(opMode)
    }

    override fun init(width: Int, height: Int, calibration: CameraCalibration?) {}

    override fun processFrame(frame: Mat, captureTimeNanos: Long): Any? {
        // This returned value is the userContext in [onDrawFrame]
        return null
    }

    override fun onDrawFrame(
        canvas: Canvas,
        onscreenWidth: Int,
        onscreenHeight: Int,
        scaleBmpPxToCanvasPx: Float,
        scaleCanvasDensity: Float,
        userContext: Any?,
    ) {
    }

    @Deprecated(
        message = "Please initialize CubeVision with cubeColor.",
        replaceWith = ReplaceWith("CubeVision.init(this, cubeColor)"),
        level = DeprecationLevel.ERROR,
    )
    override fun init(opMode: OpMode) {
        throw RuntimeException("Please initialize CubeVision with cubeColor.")
    }

    /**
     * Represents the position where the cube is placed, relative to the camera of the robot.
     */
    enum class CubePlacement {
        Left,
        Center,
        Right,
    }
}
