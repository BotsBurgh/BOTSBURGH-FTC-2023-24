package org.firstinspires.ftc.vision

import org.openftc.easyopencv.OpenCvCamera

/**
 * Returns the (possibly null) current camera of the given [VisionPortal].
 *
 * This is in the `org.firstinspires.ftc.vision` package so it can access the `protected`
 * [VisionPortalImpl.camera] property.
 */
fun getVisionPortalCamera(visionPortal: VisionPortal): OpenCvCamera? =
    (visionPortal as? VisionPortalImpl)?.camera

fun getVisionPortalCamera(visionPortal: VisionPortalImpl): OpenCvCamera = visionPortal.camera
