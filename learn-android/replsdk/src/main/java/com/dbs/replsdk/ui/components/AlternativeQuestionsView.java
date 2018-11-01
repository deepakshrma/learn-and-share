package com.dbs.replsdk.ui.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dbs.replsdk.R;
import com.dbs.replsdk.model.AlternateQuestion;
import com.dbs.ui.components.DBSTextView;
import com.dbs.ui.utils.ViewUtils;

import java.util.List;

import static com.dbs.ui.utils.ViewUtils.dpToPixels;

public class AlternativeQuestionsView extends DBSCarouselView<AlternateQuestion, AlternativeQuestionsView.AlternativeQuestionViewHolder> {

    AlternativeQuestionsView.OnAlternativeQuestionReplyListener onItemClickListener;

    public AlternativeQuestionsView(Context context) {
        super(context);
    }

    public AlternativeQuestionsView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AlternativeQuestionsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnAlternateQuestionsReplyListener(AlternativeQuestionsView.OnAlternativeQuestionReplyListener listener) {
        this.onItemClickListener = listener;
    }


    @Override
    public void bindData(AlternativeQuestionViewHolder holder, AlternateQuestion val, int i) {

        holder.questionTextView.setText(val.getQuestion());
        holder.replyButton.setText("View Answers");
        holder.replyButton.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onReply(val);
            }
        });

        if (adapter.getItemCount() == 1) {
            holder.itemView.setBackgroundResource(R.drawable.bg_bubble_rounded);
        } else {
            if (i == 0) {
                holder.itemView.setBackgroundResource(R.drawable.bg_card_left);
            } else if (i == adapter.getItemCount() - 1) {
                holder.itemView.setBackgroundResource(R.drawable.bg_card_right);
            } else {
                holder.itemView.setBackgroundResource(R.drawable.bg_card_middle);
            }
        }
        holder.itemView.forceLayout();
    }


    @Override
    public void updateData(List<AlternateQuestion> dtList) {
        super.updateData(dtList);
    }

    @Override
    public void onViewCreated(Context context, AttributeSet attrs) {
    }

    @Override
    public AlternativeQuestionViewHolder getViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.dbs_alternate_questions_view, viewGroup, false);

        int[] windowDimensions = ViewUtils.getScreenSize();
        int width = windowDimensions[0] - dpToPixels(viewGroup.getContext(), 60);
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.width = width;
        v.setLayoutParams(params);
        return new AlternativeQuestionViewHolder(v);
    }


    public interface OnAlternativeQuestionReplyListener {
        void onReply(AlternateQuestion alternateQuestion);
    }

    static class AlternativeQuestionViewHolder extends RecyclerView.ViewHolder {
        DBSTextView questionTextView;
        DBSTextView replyButton;

        AlternativeQuestionViewHolder(View view) {
            super(view);
            questionTextView = view.findViewById(R.id.alternate_question_tex);
            replyButton = view.findViewById(R.id.reply_button);
        }


    }
}