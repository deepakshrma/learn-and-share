package com.dbs.replsdk.component;

import android.support.constraint.ConstraintLayout;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.dbs.replsdk.matcher.RecyclerViewMatcher;
import com.dbs.replsdk.R;
import com.dbs.replsdk.model.AlternateQuestion;
import com.dbs.replsdk.ui.components.AlternativeQuestionsView;
import com.dbs.replsdk.test.ViewTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.replsdk.matcher.RecyclerViewMatcher.nthChildOf;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class AlternateQuestionTest {

    private AlternateQuestion alternateQuestion;
    public static String QUESTION = "I have some unauthorised transactions ..";
    public static String QUESTION_ID = "10000";
    public static String ANSWER = "View Answers";
    private AlternativeQuestionsView alternativeQuestionsView;
    private List<AlternateQuestion> questionList;

    @Rule
    public ViewTestRule<ConstraintLayout> rule = new ViewTestRule<>(R.layout.alternate_question_test_layout);


    @Before
    public void setUp() {
        alternateQuestion = new AlternateQuestion();
        alternateQuestion.setQuestion(QUESTION);
        alternateQuestion.setQuestion_id(QUESTION_ID);
        alternativeQuestionsView =  rule.getView().findViewById(R.id.testAlternateQuestionView);
        questionList = new ArrayList<>();
        questionList.add(alternateQuestion);
        alternativeQuestionsView.updateData(questionList);
    }


    @Test
    public void shouldShowQuestionAndReply() {
        onView(withText(QUESTION)).check(matches(isDisplayed()));
        onView(withText(ANSWER)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldGiveCallbackForReplyWhenReplyIsClicked() {
        AlternativeQuestionsView.OnAlternativeQuestionReplyListener onClickListener = mock(AlternativeQuestionsView.OnAlternativeQuestionReplyListener.class);
        rule.runOnMainSynchronously(view -> alternativeQuestionsView.setOnAlternateQuestionsReplyListener(onClickListener));

        onView(withId(R.id.reply_button)).perform(click());

        verify(onClickListener, times(1)).onReply(any());
    }

    @Test
    public void shouldNotDisplayQuestionForNullList(){
        alternativeQuestionsView.updateData(null);
        hasChildCount(0);

    }

    @Test
    public void shouldAddAllQuestions(){
        AlternateQuestion question1 = new AlternateQuestion();
        AlternateQuestion question2 = new AlternateQuestion();
        AlternateQuestion question3 = new AlternateQuestion();
        question1.setQuestion("Question 1");
        question2.setQuestion("Question 2");
        question3.setQuestion("Question 3");
        questionList.clear();
        questionList.add(question1);
        questionList.add(question2);
        questionList.add(question3);
        alternativeQuestionsView.updateData(questionList);
        hasChildCount(3);
    }

    @Test
    public void shouldDisplayAllQuestionDataProperly() {
        AlternateQuestion question1 = new AlternateQuestion();
        AlternateQuestion question2 = new AlternateQuestion();
        AlternateQuestion question3 = new AlternateQuestion();
        question1.setQuestion("Question 1");
        question2.setQuestion("Question 2");
        question3.setQuestion("Question 3");
        questionList.clear();
        questionList.add(question1);
        questionList.add(question2);
        questionList.add(question3);
        alternativeQuestionsView.updateData(questionList);

        onView(withId(R.id.testAlternateQuestionView)).check(matches(hasDescendant(withText("Question 1"))));
        onView(withId(R.id.testAlternateQuestionView)).perform(scrollToPosition(1));
        onView(withId(R.id.testAlternateQuestionView)).check(matches(hasDescendant(withText("Question 2"))));
        onView(withId(R.id.testAlternateQuestionView)).perform(scrollToPosition(2));
        onView(withId(R.id.testAlternateQuestionView)).check(matches(hasDescendant(withText("Question 3"))));
    }

    @Test
    public void onCLickShouldWorkOnAllData() {
        AlternateQuestion question1 = new AlternateQuestion();
        AlternateQuestion question2 = new AlternateQuestion();
        question1.setQuestion("Question 1");
        question2.setQuestion("Question 2");
        questionList.clear();
        questionList.add(question1);
        questionList.add(question2);
        alternativeQuestionsView.updateData(questionList);

        AlternativeQuestionsView.OnAlternativeQuestionReplyListener onClickListener = mock(AlternativeQuestionsView.OnAlternativeQuestionReplyListener.class);
        rule.runOnMainSynchronously(view -> alternativeQuestionsView.setOnAlternateQuestionsReplyListener(onClickListener));

        onView((new RecyclerViewMatcher(R.id.testAlternateQuestionView)).atPositionOnView(0, R.id.reply_button))
                .perform(click());
        onView(withId(R.id.testAlternateQuestionView)).perform(scrollToPosition(1));
        onView((new RecyclerViewMatcher(R.id.testAlternateQuestionView)).atPositionOnView(1, R.id.reply_button))
                .perform(click());
        verify(onClickListener, times(2)).onReply(any());

    }
}
