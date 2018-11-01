package com.dbs.ui.components;

import android.support.test.runner.AndroidJUnit4;

import com.dbs.ui.Await;
import com.dbs.ui.components.testRule.ViewTestRule;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DBSTimerViewTest {

    @Rule
    public ViewTestRule<DBSTimerView> viewTestRule = new ViewTestRule<>(com.dbs.components.test.R.layout.timer_view);

    @Test
    public void timerViewShouldNotifyTimerListenerOnTheStartOfTimer() {
        DBSTimerView.TimerListener timerListener = mock(DBSTimerView.TimerListener.class);
        DBSTimerView countDownTimerView = viewTestRule.getActivity().findViewById(com.dbs.components.test.R.id.count_down_1);
        DBSTimerView countUpTimerView = viewTestRule.getActivity().findViewById(com.dbs.components.test.R.id.count_up_1);
        countDownTimerView.setTimerListener(timerListener);
        countUpTimerView.setTimerListener(timerListener);

        countDownTimerView.start();
        countDownTimerView.stop();
        countUpTimerView.start();
        countUpTimerView.stop();

        verify(timerListener, times(2)).onTimerStarted();
    }

    @Test
    public void timerViewShouldNotifyTimerListenerOnTheStopOfTimer() {
        DBSTimerView.TimerListener timerListener = mock(DBSTimerView.TimerListener.class);
        DBSTimerView countDownTimerView = viewTestRule.getActivity().findViewById(com.dbs.components.test.R.id.count_down_1);
        DBSTimerView countUpTimerView = viewTestRule.getActivity().findViewById(com.dbs.components.test.R.id.count_up_1);

        countDownTimerView.setTimerListener(timerListener);
        countUpTimerView.setTimerListener(timerListener);

        countDownTimerView.stop();
        countUpTimerView.stop();

        verify(timerListener, times(2)).onTimerStopped();
    }

    @Test
    public void timerViewShouldCallTimeElapsedOnTimerCompletion() throws InterruptedException {
        DBSTimerView.TimerListener timerListener = mock(DBSTimerView.TimerListener.class);
        DBSTimerView countUpTimerView = viewTestRule.getActivity().findViewById(com.dbs.components.test.R.id.count_down_1);
        DBSTimerView countDownTimerView = viewTestRule.getActivity().findViewById(com.dbs.components.test.R.id.count_down_1);
        countUpTimerView.setTimerListener(timerListener);
        countDownTimerView.setTimerListener(timerListener);

        countUpTimerView.start();
        countDownTimerView.start();

        Thread.sleep(2000);
        verify(timerListener, times(2)).onTimeElapsed();
        countUpTimerView.stop();
        countDownTimerView.stop();
    }

    @Test
    public void countDownTimerShouldHave00_00AsFinalValue() throws Exception {
        DBSTimerView.TimerListener timerListener = mock(DBSTimerView.TimerListener.class);
        DBSTimerView timerView = viewTestRule.getActivity().findViewById(com.dbs.components.test.R.id.count_down_1);
        timerView.setTimerListener(timerListener);
        timerView.start();
        Await.await(() -> timerView.getText().toString().equals("00:01"), 250);
        Assert.assertEquals("00:01", timerView.getText());
        Await.await(() -> timerView.getText().toString().equals("00:00"), 2000);
        Assert.assertEquals("00:00", timerView.getText());
        timerView.stop();
    }

    @Test
    public void countDownTimerShouldHaveTimerDurationAsInitialValue() throws Exception {
        DBSTimerView.TimerListener timerListener = mock(DBSTimerView.TimerListener.class);
        DBSTimerView timerView = viewTestRule.getActivity().findViewById(com.dbs.components.test.R.id.count_down_30);
        timerView.setTimerListener(timerListener);
        timerView.start();
        Await.await(() -> timerView.getText().toString().equals("00:30"), 250);
        Assert.assertEquals("00:30", timerView.getText());
        timerView.stop();
    }

    @Test
    public void countUpTimerShouldHave00_00AsInitialValue() throws Exception {
        DBSTimerView.TimerListener timerListener = mock(DBSTimerView.TimerListener.class);
        DBSTimerView timerView = viewTestRule.getActivity().findViewById(com.dbs.components.test.R.id.count_up_30);
        timerView.setTimerListener(timerListener);
        timerView.start();
        Await.await(() -> timerView.getText().toString().equals("00:00"), 250);
        Assert.assertEquals("00:00", timerView.getText());
        timerView.stop();
    }

    @Test
    public void countUpTimerShouldHaveCounterDurationAsFinalValue() throws Exception {
        DBSTimerView.TimerListener timerListener = mock(DBSTimerView.TimerListener.class);
        DBSTimerView timerView = viewTestRule.getActivity().findViewById(com.dbs.components.test.R.id.count_up_1);
        timerView.setTimerListener(timerListener);
        timerView.start();
        Await.await(() -> timerView.getText().toString().equals("00:01"), 2000);
        Assert.assertEquals("00:01", timerView.getText());
        timerView.stop();
    }

    @Test//added multiple cases into one coz timer is taking 10 secs to run
    public void countDownTimerShouldHaveIntermediateValuesAsPerIntervalAndCalloNTickWithThem() throws InterruptedException {
        DBSTimerView.TimerListener timerListener = mock(DBSTimerView.TimerListener.class);
        DBSTimerView timerView = viewTestRule.getActivity().findViewById(com.dbs.components.test.R.id.count_down_9);
        timerView.setTimerListener(timerListener);
        List<CharSequence> expectedTimerViewTextAtDifferentIntervals = Arrays.asList("00:09", "00:05", "00:01", "00:00");
        List<CharSequence> actualTimerViewTextAtDifferentIntervals = new ArrayList<CharSequence>();
        InOrder inOrder = Mockito.inOrder(timerListener);

        timerView.start();

        Thread.sleep(500);
        actualTimerViewTextAtDifferentIntervals.add(timerView.getText());
        int timeInHalfSeconds = 20;
        while (timeInHalfSeconds > 0) {
            if (!actualTimerViewTextAtDifferentIntervals.get(actualTimerViewTextAtDifferentIntervals.size() - 1).equals(timerView.getText()))
                actualTimerViewTextAtDifferentIntervals.add(timerView.getText());
            Thread.sleep(500);
            timeInHalfSeconds--;
        }
        Assert.assertEquals(expectedTimerViewTextAtDifferentIntervals, actualTimerViewTextAtDifferentIntervals);
        timerView.stop();
        inOrder.verify(timerListener, Mockito.atLeastOnce()).onTick(9);
        inOrder.verify(timerListener, Mockito.atLeastOnce()).onTick(5);
        inOrder.verify(timerListener, Mockito.atLeastOnce()).onTick(1);
    }

    @Test//added multiple cases into one coz timer is taking 10 secs to run
    public void countUpTimerShouldHaveIntermediateValuesAsPerIntervalAndCalloNTickWithThem() throws InterruptedException {
        DBSTimerView.TimerListener timerListener = mock(DBSTimerView.TimerListener.class);
        DBSTimerView timerView = viewTestRule.getActivity().findViewById(com.dbs.components.test.R.id.count_up_10);
        timerView.setTimerListener(timerListener);
        List<CharSequence> expectedTimerViewTextAtDifferentIntervals = Arrays.asList("00:00", "00:03", "00:06", "00:09", "00:10");
        List<CharSequence> actualTimerViewTextAtDifferentIntervals = new ArrayList<CharSequence>();
        InOrder inOrder = Mockito.inOrder(timerListener);

        timerView.start();

        Thread.sleep(500);
        actualTimerViewTextAtDifferentIntervals.add(timerView.getText());
        int timeInHalfSeconds = 22;
        while (timeInHalfSeconds > 0) {
            CharSequence timerText = timerView.getText();
            if (!actualTimerViewTextAtDifferentIntervals.get(actualTimerViewTextAtDifferentIntervals.size() - 1).equals(timerText))
                actualTimerViewTextAtDifferentIntervals.add(timerText);
            Thread.sleep(500);
            timeInHalfSeconds--;
        }
        Assert.assertEquals(expectedTimerViewTextAtDifferentIntervals, actualTimerViewTextAtDifferentIntervals);
        timerView.stop();
        inOrder.verify(timerListener, Mockito.atLeastOnce()).onTick(0);
        inOrder.verify(timerListener, Mockito.atLeastOnce()).onTick(3);
        inOrder.verify(timerListener, Mockito.atLeastOnce()).onTick(6);
        inOrder.verify(timerListener, Mockito.atLeastOnce()).onTick(9);
    }

}
