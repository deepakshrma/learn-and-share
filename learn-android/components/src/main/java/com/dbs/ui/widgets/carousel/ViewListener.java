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

package com.dbs.ui.widgets.carousel;

import android.view.View;

/**
 * When an object of a type is attached, its methods will
 * be called when view is to be set for position.
 *
 * @author DBS Bank, AppStudio Team
 */
public interface ViewListener {
    /**
     * This method is called to get the view for position.
     *
     * @param position position of the view.
     * @return View to be set at the position.
     */
    View setViewForPosition(int position);
}