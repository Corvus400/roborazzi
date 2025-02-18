package com.github.takahirom.integration_test_project

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.DEFAULT_ROBORAZZI_OUTPUT_DIR_PATH
import android.app.Application
import androidx.test.core.app.ApplicationProvider
import org.robolectric.Shadows
import android.content.ComponentName
import org.junit.Test
import org.robolectric.annotation.GraphicsMode

import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class RoborazziTest {
  init {
    com.github.takahirom.roborazzi.ROBORAZZI_DEBUG = true
  }
  @Test
  fun testCapture() {
    val appContext: Application = ApplicationProvider.getApplicationContext()
    Shadows.shadowOf(appContext.packageManager).addActivityIfNotPresent(
      ComponentName(
        appContext.packageName,
        MainActivity::class.java.name,
      )
    )
    ActivityScenario.launch(MainActivity::class.java)
    onView(ViewMatchers.isRoot()).captureRoboImage()
  }

  @Test
  fun testCaptureWithCustomPath() {
    ActivityScenario.launch(MainActivity::class.java)
    onView(ViewMatchers.isRoot()).captureRoboImage(
      filePath = "$DEFAULT_ROBORAZZI_OUTPUT_DIR_PATH/customdir/custom_file.png",
      roborazziOptions = RoborazziOptions(
        compareOptions = RoborazziOptions.CompareOptions(
          outputDirectoryPath = "$DEFAULT_ROBORAZZI_OUTPUT_DIR_PATH/custom_compare_outputDirectoryPath",
        )
      )
    )
  }
}