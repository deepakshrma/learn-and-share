package com.dbs.ui.components.plugins;

import com.dbs.components.R;

/**
 * The DBSPluginType is different types supported for plugins.
 *
 * @author DBS Bank, AppStudio Team
 * @see DBSPlugin
 */
public enum DBSPluginType {
    SMALL(R.dimen.plugin_height_small),
    MEDIUM(R.dimen.plugin_height_medium),
    LARGE(R.dimen.plugin_height_large);

    private int heightDimenResourceId;

    DBSPluginType(final int heightDimenResourceId) {
        this.heightDimenResourceId = heightDimenResourceId;
    }

    /**
     * Returns the height resource id for a plugin type.
     */
    public int getHeightDimenResourceId() {
        return heightDimenResourceId;
    }
}