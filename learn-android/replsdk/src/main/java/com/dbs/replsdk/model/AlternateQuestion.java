package com.dbs.replsdk.model;

import com.dbs.replsdk.uimodel.DifferModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlternateQuestion implements DifferModel {

    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("question_id")
    @Expose
    private String question_id;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    @Override
    public boolean areItemsTheSame(DifferModel b) {
        if (!(b instanceof AlternateQuestion)) return false;
        return ((AlternateQuestion) b).question_id.equals(this.question_id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlternateQuestion question1 = (AlternateQuestion) o;

        if (question != null ? !question.equals(question1.question) : question1.question != null)
            return false;
        return question_id != null ? question_id.equals(question1.question_id) : question1.question_id == null;
    }


    @Override
    public boolean areContentsTheSame(DifferModel b) {

        return b.equals(this);
    }


}