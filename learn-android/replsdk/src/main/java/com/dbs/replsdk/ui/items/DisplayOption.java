package com.dbs.replsdk.ui.items;


import android.support.annotation.DimenRes;
import android.support.annotation.IdRes;

import com.dbs.replsdk.R;

public class DisplayOption {

    private boolean isMe;
    private @IdRes
    int bgRes;
    private long itemId;
    private @DimenRes
    int maxWidth;
    private @DimenRes
    int fixedWidth;

    /**
     * No args constructor for use in serialization
     */
    public DisplayOption() {
        isMe = false;
        bgRes = R.drawable.bg_bubble_rounded;
        itemId = Long.MIN_VALUE;
        maxWidth = R.dimen.dbs_bubble_max_width;
        fixedWidth = 0;
    }

    boolean isIsMe() {
        return isMe;
    }

    public void setIsMe(boolean isMe) {
        this.isMe = isMe;
    }

    DisplayOption withIsMe(boolean isMe) {
        this.isMe = isMe;
        return this;
    }

    int getBgRes() {
        return bgRes;
    }

    public void setBgRes(int bgRes) {
        this.bgRes = bgRes;
    }

    public DisplayOption withBgRes(int bgRes) {
        this.bgRes = bgRes;
        return this;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public DisplayOption withItemId(long itemId) {
        this.itemId = itemId;
        return this;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public DisplayOption withMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    int getFixedWidth() {
        return fixedWidth;
    }

    public void setFixedWidth(int fixedWidth) {
        this.fixedWidth = fixedWidth;
    }

    DisplayOption withFixedWidth(int fixedWidth) {
        this.fixedWidth = fixedWidth;
        return this;
    }

}