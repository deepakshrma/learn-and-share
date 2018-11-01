package com.dbs.replsdk.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dbs.replsdk.R;
import com.dbs.ui.components.DBSEditTextView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.subscriptions.CompositeSubscription;

import static com.dbs.replsdk.ui.utils.ViewUtils.switchImageWithAnimation;

/**
 * Created by deepak on 5/10/18.
 */

public class DBSChatToolbarView extends RelativeLayout {
    ImageView leftDrawableIcon;
    ImageView rightDrawableIcon;
    DBSEditTextView messageEditText;
    OnActionClickListener onActionClickListener;
    CompositeSubscription compositeSubscription = new CompositeSubscription();

    public DBSChatToolbarView(Context context) {
        super(context);
    }

    public DBSChatToolbarView(Context context, AttributeSet attrs) {
        this(context, attrs, NO_ID);
    }

    public DBSChatToolbarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    public void setOnActionClickListener(OnActionClickListener l) {
        onActionClickListener = l;
    }

    private void initView(AttributeSet attrs) {
        Context context = getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dbs_chat_toolbar, this, true);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DBSChatToolbarView);
        leftDrawableIcon = findViewById(R.id.leftNavigationItem);
        messageEditText = findViewById(R.id.messageEditText);
        rightDrawableIcon = findViewById(R.id.rightNavigationItem);
        if (attributes.hasValue(R.styleable.DBSChatToolbarView_android_drawableStart)) {
            Drawable lDrawable = attributes.getDrawable(R.styleable.DBSChatToolbarView_android_drawableStart);
            leftDrawableIcon.setImageDrawable(lDrawable);
        }
        if (attributes.hasValue(R.styleable.DBSChatToolbarView_android_drawableEnd)) {
            Drawable rDrawable = attributes.getDrawable(R.styleable.DBSChatToolbarView_android_drawableEnd);
            rightDrawableIcon.setImageDrawable(rDrawable);
        }
        rightDrawableIcon.setTag(R.drawable.ic_mic_btn);
        setListeners();
        attributes.recycle();
    }

    private void setListeners() {
        messageEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                messageEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                messageEditText.setSelection(messageEditText.getText().length());
            } else {
                messageEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
        rightDrawableIcon.setOnClickListener(v -> {
            if (onActionClickListener != null) {
                onActionClickListener.onClick((ChatToolbarAction) rightDrawableIcon.getTag(), messageEditText.getText().toString());
                messageEditText.getText().clear();
            }
        });
        compositeSubscription.add(RxTextView.textChanges(messageEditText)
                .debounce(300, TimeUnit.MILLISECONDS).subscribe(currentText -> {
                    ChatToolbarAction nextActionBtn = currentText.length() > 0 ? ChatToolbarAction.MESSAGE : ChatToolbarAction.MIC;
                    Drawable nextDrawable = getResources().getDrawable(nextActionBtn.actionId);
                    if (nextActionBtn != rightDrawableIcon.getTag()) {
                        switchImageWithAnimation(rightDrawableIcon, nextDrawable);
                    }
                    rightDrawableIcon.setTag(nextActionBtn);
                }));
    }

    @Override
    protected void onDetachedFromWindow() {
        compositeSubscription.unsubscribe();
        super.onDetachedFromWindow();
    }

    public enum ChatToolbarAction {
        MIC(R.drawable.ic_mic_btn),
        MESSAGE(R.drawable.ic_send_btn);
        private final int actionId;

        ChatToolbarAction(int id) {
            actionId = id;
        }

        public int getId() {
            return actionId;
        }
    }

    public interface OnActionClickListener {
        void onClick(ChatToolbarAction action, String message);
    }
}