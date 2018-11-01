package com.dbs.ui.components;

/**
 * The MenuItem is a class which holds data for {@link DBSMenuItemView}
 * Identifier, image resource id and title.
 *
 * @author DBS Bank, AppStudio Team
 */
public class MenuItem {
    private int id = -1;
    private int resId = 0;
    private String title = "";

    public MenuItem(int id, int resId, String title) {
        this.id = id;
        this.resId = resId;
        this.title = title;
    }

    /**
     * Returns identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Sets identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns image resource id
     */
    public int getResId() {
        return resId;
    }

    /**
     * Sets image resource id
     */
    public void setResId(int resId) {
        this.resId = resId;
    }

    /**
     * Returns title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title
     */
    public void setTitle(String title) {
        this.title = title;
    }
}