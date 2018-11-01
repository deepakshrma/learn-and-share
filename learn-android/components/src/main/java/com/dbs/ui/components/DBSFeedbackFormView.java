/*
 * - Licensed Materials - Property of DBS Bank SG
 * - "Restricted Materials of DBS Bank"
 *
 * APP Studio SDK, Copyright (c) 2018. DBS Bank SG
 *
 * Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 */

package com.dbs.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.dbs.components.R;
import com.dbs.ui.BaseView;
import com.dbs.ui.components.dbsrating.DBSRatingBar;
import com.dbs.ui.customViews.DBSGridView;
import com.dbs.ui.error.InvalidRatingCommentsException;
import com.dbs.ui.utils.TextWatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The DBSFeedbackFormView Component is to take feedback from user.
 * User can set rating, and give feedback on rating.
 * If user select rating less than 4, User need to tell Area of improvements.
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSFeedbackFormView extends BaseView implements DBSRatingBar.OnRatingChangeListener {

    public static final int LOW_RATING_THRESHOLD = 4;
    public static final int DEFAULT_TITLE_TEXT = R.string.feedback_overall_experience_question;
    private static final int DEFAULT_IMPROVEMENTS_TEXT = R.string.what_can_be_improved;
    private static final int DEFAULT_FEEDBACK_HINT = R.string.feedback_hint;
    private static final int DEFAULT_FEEDBACK_MAX_LEN = 150;
    private static final int DEFAULT_SUBMIT_BUTTON_TEXT = R.string.submit;
    private static final int DEFAULT_RATING_COMMENTS_RESOURCE = R.array.feedback_rating_comments;
    private static final int DEFAULT_AREAS_OF_IMPROVEMENT = R.array.feedback_areas_of_improvement;

    DBSRatingBar dbsRatingBar;
    ArrayAdapter<String> improvementsAdapter;
    private AppCompatTextView title;
    private AppCompatEditText feedbackEditText;
    private TextInputLayout feedbackTextInputLayout;
    private AppCompatButton submitButton;
    private AppCompatTextView ratingComment;
    private AppCompatTextView improvementsTitle;
    private DBSGridView improvementsGrid;
    private String[] ratingComments;
    private Map<String, Boolean> areasOfImprovements;
    private OnFeedbackFormSubmitListener onFeedbackFormSubmitListener;
    private Context context;

    public DBSFeedbackFormView(@NonNull Context context) {
        super(context);
    }

    public DBSFeedbackFormView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DBSFeedbackFormView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * This method will return DBSRatingBar from Feedback Form
     *
     * @return dbsRatingBar
     */
    public DBSRatingBar getDbsRatingBar() {
        return dbsRatingBar;
    }

    /**
     * This method is to set Feedback Hint Programmatically
     *
     * @param hint
     */
    public void setFeedbackHint(String hint) {
        feedbackEditText.setHint(hint);
    }

    /**
     * Returns current rating.
     */
    public int getRating() {
        return dbsRatingBar.getRating();
    }

    /**
     * This method is to set Rating Programmatically
     *
     * @param rating
     */
    public void setRating(int rating) {
        dbsRatingBar.setRating(rating);
        updateUIBasedOnRating(dbsRatingBar.getRating()); //rating bar handles cases where rating is greater than stars etc so you would want valid rating that is set on rating bar
    }

    /**
     * This method is to set Submit Button Text Programmatically
     *
     * @param submitButtonText
     */
    public void setSubmitButtonText(String submitButtonText) {
        submitButton.setText(submitButtonText);
    }

    /**
     * This method is to set OnFeedbackFormSubmitListener
     *
     * @param onFeedbackFormSubmitListener instance of OnFeedbackFormSubmitListener
     */
    public void setOnFeedbackFormSubmitListener(OnFeedbackFormSubmitListener onFeedbackFormSubmitListener) {
        this.onFeedbackFormSubmitListener = onFeedbackFormSubmitListener;
    }

    /**
     * This method is to set feed back form title Text Programmatically
     *
     * @param titleText
     */
    public void setTitleText(String titleText) {
        title.setText(titleText);
    }

    /**
     * This method is to set Improvements value in improvementsAdapter Programmatically.
     * This will Text of things can be improved grid view.
     *
     * @param improvements
     */
    public void setImprovements(String[] improvements) {
        improvementsAdapter.clear();
        improvementsAdapter.addAll(improvements);
        improvementsAdapter.notifyDataSetChanged();
    }

    /**
     * This method is to set ratingComments value Programmatically.
     *
     * @param ratingComments
     */
    public void setRatingComments(String[] ratingComments) {
        if (ratingComments == null || ratingComments.length != dbsRatingBar.getMaxRating()) {
            throw new InvalidRatingCommentsException("Rating Comments could not be null of less than DbsRatingBar getMaxRating");
        }
        this.ratingComments = Arrays.copyOf(ratingComments, ratingComments.length);
    }

    /**
     * This method to enable disable Submit button programmatically
     *
     * @param state
     */
    public void toggleSubmitButton(boolean state) {
        submitButton.setEnabled(state);
    }

    /**
     * This method returns title TextView from feedback form.
     *
     * @return AppCompatTextView
     */
    public AppCompatTextView getTitle() {
        return title;
    }

    /**
     * This method returns Feedback EditText from feedback form.
     *
     * @return AppCompatEditText
     */
    public AppCompatEditText getFeedbackEditText() {
        return feedbackEditText;
    }

    /**
     * This method returns Submit Button from feedback form.
     *
     * @return AppCompatButton
     */
    public AppCompatButton getSubmitButton() {
        return submitButton;
    }

    /**
     * This method returns Rating Comment TextView from feedback form.
     *
     * @return AppCompatTextView
     */
    public AppCompatTextView getRatingComment() {
        return ratingComment;
    }

    /**
     * This method returns Improvements Title TextView from feedback form.
     *
     * @return AppCompatTextView
     */
    public AppCompatTextView getImprovementsTitle() {
        return improvementsTitle;
    }

    /**
     * This method is to set Improvements Title Text Programmatically
     *
     * @param improvementsTitleText
     */
    public void setImprovementsTitle(String improvementsTitleText) {
        improvementsTitle.setText(improvementsTitleText);
    }

    /**
     * This method returns Improvements Grid from feedback form.
     *
     * @return AppCompatTextView
     */
    public GridView getImprovementsGrid() {
        return improvementsGrid;
    }

    /**
     * This method return weather feedback form is valid or not.
     * Condition 1: If user set rating less than LOW_RATING_THRESHOLD, user has to select atleast one thing can be improved
     * Condition 2: User can't exceed feedback text more than feedback comment text max length
     *
     * @return boolean { Form valid }
     */
    public boolean isValid() {
        return !((getRating() < LOW_RATING_THRESHOLD && getThingsToBeImproved().isEmpty())
                || (feedbackEditText.getText().toString().length() > feedbackTextInputLayout.getCounterMaxLength()));
    }

    private void toggleSubmitButton() {
        submitButton.setEnabled(isValid());
    }

    /**
     * @inheritDoc
     *
     * Update UI according to rating.
     */
    @Override
    public void onRatingUpdated(int rating) {
        updateUIBasedOnRating(rating);
    }

    @Override
    protected void viewInflated(View view, String type, AttributeSet attrs) {
        context = getContext();
        linkAllTheViewsToInstanceVariables(view);
        setDefaults();
        updateUIAsPerCustomXMLAttributes(attrs);
        setupImprovementsGrid();
        setupListeners();
        toggleSubmitButton();
    }

    @Override
    protected int provideLayoutId(String type) {
        return R.layout.dbs_feedback_form;
    }

    private void setupListeners() {
        dbsRatingBar.setRatingChangeListener(this);
        submitButton.setOnClickListener(v -> {
            if (onFeedbackFormSubmitListener == null) {
                return;
            }
            int rating = getRating();
            if (rating < LOW_RATING_THRESHOLD) {
                onFeedbackFormSubmitListener.onSubmitWithThingsToBeImproved(rating, feedbackEditText.getText().toString(), getThingsToBeImproved());
            } else {
                onFeedbackFormSubmitListener.onSubmit(rating, feedbackEditText.getText().toString());
            }
        });
        feedbackEditText.addTextChangedListener(new TextWatchers.AfterChangeTextWatcher() {
            @Override
            public void onTextChange(String currentText) {
                toggleSubmitButton();
            }
        });
    }

    private void updateUIAsPerCustomXMLAttributes(AttributeSet attrs) {
        TypedArray attributesFromXml = context.obtainStyledAttributes(attrs, R.styleable.DBSFeedbackFormView);

        title.setText(getStringAttributeOrDefault(attributesFromXml, R.styleable.DBSFeedbackFormView_dbs_title, DEFAULT_TITLE_TEXT));
        improvementsTitle.setText(getStringAttributeOrDefault(attributesFromXml, R.styleable.DBSFeedbackFormView_dbs_feedbackImprovementsTitle, DEFAULT_IMPROVEMENTS_TEXT));
        feedbackEditText.setHint(getStringAttributeOrDefault(attributesFromXml, R.styleable.DBSFeedbackFormView_dbs_feedbackHint, DEFAULT_FEEDBACK_HINT));
        submitButton.setText(getStringAttributeOrDefault(attributesFromXml, R.styleable.DBSFeedbackFormView_dbs_feedbackSubmitButtonText, DEFAULT_SUBMIT_BUTTON_TEXT));
        feedbackTextInputLayout.setCounterMaxLength(attributesFromXml.getInt(R.styleable.DBSFeedbackFormView_dbs_feedbackMaxLen, DEFAULT_FEEDBACK_MAX_LEN));
        attributesFromXml.recycle();
    }

    private String getStringAttributeOrDefault(TypedArray attributesFromXml, int stringResourceId, int defaultStringResourceId) {
        if (attributesFromXml.hasValue(stringResourceId))
            return attributesFromXml.getString(stringResourceId);
        return context.getString(defaultStringResourceId);
    }

    private void setupImprovementsGrid() {
        improvementsAdapter = new ArrayAdapter<>(context, R.layout.feedback_form_improvement_item, R.id.improvement);
        improvementsAdapter.addAll(areasOfImprovements.keySet());
        improvementsGrid.setAdapter(improvementsAdapter);
        improvementsGrid.setVerticalScrollBarEnabled(false);
        improvementsGrid.setOnItemClickListener((parent, view, position, id) -> {
            AppCompatTextView improvement = (AppCompatTextView) ((ViewGroup) view).getChildAt(0);
            boolean selected = !improvement.isSelected();
            improvement.setSelected(selected);
            areasOfImprovements.put(improvement.getText().toString(), selected);
            toggleSubmitButton();
        });

    }

    private List<String> getThingsToBeImproved() {
        ArrayList<String> improvements = new ArrayList<>();
        for (Map.Entry<String, Boolean> improvement : areasOfImprovements.entrySet()) {
            if (improvement.getValue()) {
                improvements.add(improvement.getKey());
            }
        }
        return improvements;
    }

    private void setDefaults() {
        ratingComments = getResources().getStringArray(DEFAULT_RATING_COMMENTS_RESOURCE);
        areasOfImprovements = new LinkedHashMap<>();
        String[] defaultAreasOfImprovements = getResources().getStringArray(DEFAULT_AREAS_OF_IMPROVEMENT);
        for (String improvement : defaultAreasOfImprovements)
            areasOfImprovements.put(improvement, false);
    }

    private void linkAllTheViewsToInstanceVariables(View view) {
        dbsRatingBar = view.findViewById(R.id.rating_bar);
        title = view.findViewById(R.id.title);
        feedbackEditText = view.findViewById(R.id.feedback);
        feedbackTextInputLayout = view.findViewById(R.id.feedbackTextInputLayout);
        submitButton = view.findViewById(R.id.submit);
        ratingComment = view.findViewById(R.id.rating_comment);
        improvementsTitle = view.findViewById(R.id.improvements_title);
        improvementsGrid = view.findViewById(R.id.improvements_grid);
    }

    private void updateUIBasedOnRating(int rating) {
        updateRatingCommentText(rating);
        updateImprovementsSectionVisibility(rating);
        toggleSubmitButton();
    }

    private void updateImprovementsSectionVisibility(int rating) {
        int visibility;
        if (rating == 0) {
            visibility = GONE;
        } else {
            visibility = (rating < LOW_RATING_THRESHOLD) ? VISIBLE : GONE;
        }
        improvementsGrid.setVisibility(visibility);
        improvementsTitle.setVisibility(visibility);

    }

    private void updateRatingCommentText(int rating) {
        if (rating > 0) {
            ratingComment.setText(ratingComments[rating - 1]);
        } else {
            ratingComment.setText("");
        }
    }

    public interface OnFeedbackFormSubmitListener {
        void onSubmit(int rating, String comment);

        void onSubmitWithThingsToBeImproved(int rating, String comment, List<String> canBeImproved);
    }
}