package com.dbs.ui.components.plugins;

import android.view.View;
import android.view.ViewGroup;

/**
 * When an object of a type is added in {@link DBSPluginsView}, its methods will
 * be called when plugin is getting rendered on view.
 *
 * @author DBS Bank, AppStudio Team
 */
public interface DBSPlugin {
    /**
     * This method is called to notify you that,
     * view for plugin needs to be created.
     */
    View onCreatePluginView(ViewGroup parent);

    /**
     * This method is called to get the plugin type.
     * @see DBSPluginType
     */
    DBSPluginType getPluginType();
}