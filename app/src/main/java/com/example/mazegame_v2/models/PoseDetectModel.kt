package com.example.mazegame_v2.models

import android.graphics.Bitmap

class PoseDetectModel() {
    var rightShoulder = floatArrayOf(0f, 1000f)
    var leftShoulder = floatArrayOf(0f, 0f)
    var rightWrist = floatArrayOf(0f, 1000f)
    var leftWrist = floatArrayOf(0f, 0f)

    // Detect pose and save in each FloatArray variable
    fun detectPose(outputFeature: FloatArray, bitmap: Bitmap) {
        var x = 0
        var h = bitmap.height
        var w = bitmap.width

        while (x <= 49) {
            if (outputFeature.get(x+2) > 0.45) {
                if (x == 15) {
                    rightShoulder[0] = outputFeature.get(x+1)*w
                    rightShoulder[1] = outputFeature.get(x)*h
                } else if (x == 18) {
                    leftShoulder[0] = outputFeature.get(x+1)*w
                    leftShoulder[1] = outputFeature.get(x)*h
                } else if (x == 27) {
                    rightWrist[0] = outputFeature.get(x+1)*w
                    rightWrist[1] = outputFeature.get(x)*h
                } else if (x == 30) {
                    leftWrist[0] = outputFeature.get(x+1)*w
                    leftWrist[1] = outputFeature.get(x)*h
                }
            }
            x += 3
        }
    }
}