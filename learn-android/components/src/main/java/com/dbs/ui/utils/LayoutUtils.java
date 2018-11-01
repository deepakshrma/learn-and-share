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

package com.dbs.ui.utils;

import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

/**
 * The LayoutUtils class contains helper method and static classes for layout/view.
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */

public final class LayoutUtils {
    private LayoutUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * The GradientBuilder class is useful to create rounded corner drawable, simply passing colors
     * and rounding size.
     */
    public static class GradientBuilder extends GradientDrawable {
        private GradientDrawable.Orientation orientation = GradientDrawable.Orientation.TOP_BOTTOM;
        private int gradientColorPrimary = -1;
        private int gradientColorSecondary = -1;
        private float roundCornerSize = -1;
        private int shape = GradientDrawable.RECTANGLE;

        /**
         * Sets the orientation for gradient.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public GradientBuilder withOrientation(GradientDrawable.Orientation orientation) {
            this.orientation = orientation;
            return this;
        }

        /**
         * Sets the primary color for gradient.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public GradientBuilder withPrimaryColor(int primeColor) {
            this.gradientColorPrimary = primeColor;
            return this;
        }

        /**
         * Sets the secondary color for gradient.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public GradientBuilder withSecondaryColor(int secColor) {
            this.gradientColorSecondary = secColor;
            return this;
        }

        /**
         * Sets the rounded corner size.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public GradientBuilder withRoundedCorner(float size) {
            this.roundCornerSize = size;
            return this;
        }


        /**
         * With Shape
         * @param shape
         * @return
         */
        public GradientBuilder withShape(int shape) {
            this.shape = shape;
            return this;
        }

        /**
         * Creates an {@link GradientDrawable} with the arguments supplied to this
         * builder.
         */
        public GradientDrawable build() {
            int[] colors = null;
            if (gradientColorPrimary != -1 && gradientColorSecondary != -1) {
                colors = new int[]{gradientColorPrimary, gradientColorSecondary};
            }
            if (gradientColorPrimary != -1 && gradientColorSecondary == -1) {
                colors = new int[]{gradientColorPrimary, gradientColorPrimary};
            }
            if (gradientColorPrimary == -1 && gradientColorSecondary != -1) {
                colors = new int[]{gradientColorSecondary, gradientColorSecondary};
            }
            ColorGradientDrawable gradientDrawable = new ColorGradientDrawable(this.orientation, colors);
            if (this.roundCornerSize != -1) {
                gradientDrawable.setCornerRadius(this.roundCornerSize); //set corner radius
            }
            gradientDrawable.setShape(this.shape);
            return gradientDrawable;
        }
    }

    /**
     * There is issue with GradientDrawable. You cant getColors in old APIs. This ColorGradientDrawable is a
     * wrapper class to GradientDrawable.
     * <p>
     * StackOver Flow: https://stackoverflow.com/questions/26501277/how-to-get-the-color-from-gradientdrawable
     */
    public static class ColorGradientDrawable extends GradientDrawable {
        private int[] colors;

        public ColorGradientDrawable(GradientDrawable.Orientation orientation, int[] colors) {
            super(orientation, colors);
            this.colors = colors;
        }

        /**
         * Returns colors for gradient
         */
        public int[] getColors() {
            return colors;
        }
    }

    /**
     * The GradientHelper class is useful to create gradient drawable
     */
    public static class GradientHelper {
        private GradientHelper() {
            // Do nothing on private
        }

        /**
         * Creates circular gradient drawable
         * @param color primary color for gradient
         * @param rounded size of rounded corner
         * @return gradient drawable with color and rounded corners
         */
        public static GradientDrawable getCircleGradientDrawable(int color, float rounded) {
            return new GradientBuilder().withPrimaryColor(color).withRoundedCorner(rounded).build();
        }
    }

    /**
     * The ShapeBuilder class is useful to create shape drawable
     */
    public static class ShapeBuilder {
        private Resources resources;
        private int pinColor = -1;
        private Shape shape;

        /**
         * ShapeBuilder constructor with mandatory field shape and resources.
         */
        public ShapeBuilder(Resources resources, Shape shape) {
            this.resources = resources;
            this.shape = shape;
        }

        public Resources getResources() {
            return resources;
        }

        /**
         * Sets the pin color.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public ShapeBuilder withPinColor(int pinColor) {
            this.pinColor = pinColor;
            return this;
        }

        /**
         * Creates an {@link ShapeDrawable} with the arguments supplied to this
         * builder.
         */
        public ShapeDrawable build() {
            ShapeDrawable drawable = new ShapeDrawable(shape);
            drawable.getPaint().setColor(pinColor);
            return drawable;
        }
    }
}