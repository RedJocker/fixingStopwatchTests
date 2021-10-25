package org.hyperskill.stopwatch

import android.app.Activity
import android.os.Looper.getMainLooper
import android.widget.Button
import android.widget.TextView
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf

import org.hyperskill.stopwatch.TestUtils.findViewByString

//Version 1.3
@RunWith(RobolectricTestRunner::class)
class TimerStateUnitTest {

    private val activityController = Robolectric.buildActivity(MainActivity::class.java)

    private val activity: Activity by lazy {
        activityController.setup().get()
    }
    private val startButton: Button by lazy {
        findViewByString("startButton", activity)
    }
    private val resetButton: Button by lazy {
        findViewByString("resetButton", activity)
    }
    private val textView: TextView by lazy {
        findViewByString("textView", activity)
    }

    private val messageTextViewAssertionError = "in TextView property \"text\""

    @Test
    fun testShouldCheckTimerInitialValue() {
        val expected = "00:00"
        assertEquals(messageTextViewAssertionError, expected, textView.text)
    }

    @Test
    fun testShouldTakeOneSecondToCountOneSecondOnStartButtonClick() {
        val expected = "00:00"

        startButton.performClick()
        Thread.sleep(300)
        shadowOf(getMainLooper()).idle()

        val actual = textView.text
        assertEquals(messageTextViewAssertionError, expected, actual)
    }

    @Test
    fun testShouldCountOneSecondAfterOneSecondOnStartButtonClick() {
        val expected = "00:01"

        startButton.performClick()
        Thread.sleep(1100)
        shadowOf(getMainLooper()).runToEndOfTasks()

        val actual = textView.text
        assertEquals(messageTextViewAssertionError, expected, actual)
    }


    @Test
    fun testShouldStopTimerAndResetCountOnResetButtonClick() {
        val expected = "00:00"
        startButton.performClick()

        Thread.sleep(1100)
        shadowOf(getMainLooper()).runToEndOfTasks()

        resetButton.performClick()

        val actual = textView.text
        assertEquals(messageTextViewAssertionError, expected, actual)
    }

    @Test
    fun testShouldContinueCountOnPressingStartButtonAgain() {
        val expected = "00:02"

        startButton.performClick()
        Thread.sleep(1100)
        shadowOf(getMainLooper()).runToEndOfTasks()

        startButton.performClick()
        startButton.performClick()
        startButton.performClick()
        Thread.sleep(1100)
        shadowOf(getMainLooper()).runToEndOfTasks()


        val actual = textView.text
        assertEquals(messageTextViewAssertionError, expected, actual)
    }

    @Test
    fun testShouldIgnorePressingResetButtonAgain() {
        val expected = "00:00"

        startButton.performClick()

        Thread.sleep(1100)
        shadowOf(getMainLooper()).runToEndOfTasks()

        resetButton.performClick()

        Thread.sleep(1100)
        shadowOf(getMainLooper()).runToEndOfTasks()

        resetButton.performClick()

        Thread.sleep(1100)
        shadowOf(getMainLooper()).runToEndOfTasks()

        val actual = textView.text
        assertEquals(messageTextViewAssertionError, expected, actual)
    }

}