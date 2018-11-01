package com.dbs.ui.components.plugins;

import com.dbs.components.R;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class DBSPluginTypeTest {

    @Test
    public void getHeightDimenResourceIdForSmallPlugin() {
        assertEquals(R.dimen.plugin_height_small, DBSPluginType.SMALL.getHeightDimenResourceId());
    }

    @Test
    public void getHeightDimenResourceIdForMediumPlugin() {
        assertEquals(R.dimen.plugin_height_medium, DBSPluginType.MEDIUM.getHeightDimenResourceId());
    }

    @Test
    public void getHeightDimenResourceIdForLargePlugin() {
        assertEquals(R.dimen.plugin_height_large, DBSPluginType.LARGE.getHeightDimenResourceId());
    }
}