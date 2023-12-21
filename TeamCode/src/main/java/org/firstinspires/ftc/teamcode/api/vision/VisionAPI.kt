package org.firstinspires.ftc.teamcode.api.vision

import org.firstinspires.ftc.teamcode.api.API
import org.firstinspires.ftc.vision.VisionProcessor

/**
 * An API that exposes a [VisionProcessor], specifically for use in the [Vision] API.
 *
 * It is recommended that all objects that inherit [VisionAPI] also inherit [API].
 *
 * Look at [AprilVision] for an example.
 */
interface VisionAPI {
    val processor: VisionProcessor
}
