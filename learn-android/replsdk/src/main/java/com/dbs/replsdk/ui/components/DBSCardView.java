package com.dbs.replsdk.ui.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dbs.replsdk.R;
import com.dbs.ui.components.DBSTextView;
import com.dbs.ui.utils.StringUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by deepak on 8/10/18.
 */

public class DBSCardView extends ConstraintLayout {
    Context context;
    ImageView profileImage;
    DBSTextView titleView;
    DBSTextView descView;
    DBSTextView actionButton1;
    DBSTextView actionButton2;
    LinearLayout actionButton1Container;
    LinearLayout actionButton2Container;
//    RecyclerView alternativeQuestionsView;

    public DBSCardView(@NonNull Context context) {
        this(context, null, NO_ID);
    }

    public DBSCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, NO_ID);
    }

    public DBSCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inflate(context, R.layout.view_carousel_content, this);
        initView(attrs);
    }

    public void setCard(String imgUrl, String title, String desc) {
        if (StringUtils.isNotEmpty(imgUrl)) {
            Picasso.get().load(imgUrl).placeholder(R.drawable.placeholder)// Place holder image from drawable folder
                    .error(R.drawable.placeholder).fit()
                    .into(profileImage);
        }
        if (StringUtils.isNotEmpty(title)) {
            titleView.setText(title);
        }
        if (StringUtils.isNotEmpty(desc)) {
            descView.setText(desc);
        } else {
            descView.setVisibility(GONE);
        }
    }

    /**
     * Sets default layout properties for menu item
     */
    public void setDefaultLayout() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
    }

    /**
     * Sets width of menu item
     */
    public void setWidth(int width) {
        getLayoutParams().width = width;
    }

    private void initView(AttributeSet attrs) {
        profileImage = findViewById(R.id.card_view_img);
        titleView = findViewById(R.id.card_view_title);
        descView = findViewById(R.id.card_view_desc);
        descView = findViewById(R.id.card_view_desc);
        actionButton1 = findViewById(R.id.actionButton1);
        actionButton2 = findViewById(R.id.actionButton2);
        actionButton1Container = findViewById(R.id.actionButton1Container);
        actionButton2Container = findViewById(R.id.actionButton2Container);
    }
}
