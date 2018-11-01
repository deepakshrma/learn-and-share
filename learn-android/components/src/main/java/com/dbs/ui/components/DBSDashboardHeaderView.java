package com.dbs.ui.components;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dbs.components.R;
import com.dbs.ui.models.WeatherDetails;
import com.dbs.ui.utils.GreetingUtil;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * The DBSDashboardHeaderView component is to create header view for dashboard.
 * It contains an oval button saying "Tell me more"
 * Shows weather information fetched from specified URL
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSDashboardHeaderView extends AppBarLayout {

    private View showMoreButton;
    private View weatherDetailsView;
    private Locale locale;
    private boolean customGreetingMessage;
    private Disposable weatherDisposable;
    private DBSDashboardToolbarView toolbarView;

    public DBSDashboardHeaderView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public DBSDashboardHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dbs_dashboard_header, this, true);
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.DBSDashboardHeaderView);
        @DrawableRes int backgroundImageDrawable = attributes.getResourceId(R.styleable.DBSDashboardHeaderView_headerBackgroundDrawable, -1);
        boolean shouldShowMore = attributes.getBoolean(R.styleable.DBSDashboardHeaderView_shouldShowMore, false);
        String customMessage = attributes.getString(R.styleable.DBSDashboardHeaderView_customGreetingMessage);
        attributes.recycle();

        setHeaderBackground(backgroundImageDrawable);
        showMoreButton = findViewById(R.id.dashboard_header_show_more);
        showMoreButton.setVisibility(shouldShowMore ? VISIBLE : GONE);
        weatherDetailsView = findViewById(R.id.dashboard_weather);
        setCustomGreetingMessage(customMessage);
    }


    @Override
    protected void onDetachedFromWindow() {
        if (weatherDisposable != null) {
            weatherDisposable.dispose();
        }
        super.onDetachedFromWindow();
    }

    /**
     * Enable show more details button
     */
    public void enableShowMoreDetails() {
        showMoreButton.setVisibility(VISIBLE);
    }

    /**
     * Sets background for header view
     *
     * @param backgroundResource drawable to be set as background
     */
    public void setHeaderBackground(@DrawableRes int backgroundResource) {
        ImageView imageView = findViewById(R.id.dashboard_header_background_image_view);
        Picasso.get().load(backgroundResource).into(imageView);
    }

    /**
     * Sets listener for header view
     */
    public void setHeaderListener(IListener listener) {
        showMoreButton.setOnClickListener(v -> listener.showMore());
    }

    /**
     * Subscribe to weather details.
     *
     * @param weatherObservable observable for weather details
     */
    public void subscribeToWeatherDetails(Observable<WeatherDetails> weatherObservable) {
        weatherDisposable = weatherObservable.subscribeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(this::updateWeatherDetails, error -> weatherDetailsView.setVisibility(GONE));
    }

    /**
     * Sets custom greeting message
     */
    public void setCustomGreetingMessage(String message) {
        if (message != null) {
            ((TextView) findViewById(R.id.dashboard_greeting_message)).setText(message);
            customGreetingMessage = true;
        }
    }

    /**
     * Setup toolbar in header view
     *
     * @param leftItemDrawable  drawable to be displayed as left icon
     * @param rightItemDrawable drawable to be displayed as right icon
     */
    public void setupToolbar(@DrawableRes int leftItemDrawable, @DrawableRes int rightItemDrawable) {
        toolbarView = new DBSDashboardToolbarView(getContext());
        toolbarView.setLeftNavigationItemDrawable(leftItemDrawable);
        toolbarView.setRightNavigationItemDrawable(rightItemDrawable);
        toolbarView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        toolbarView.setFitsSystemWindows(true);
        ((ConstraintLayout) findViewById(R.id.header_view)).addView(toolbarView);
    }

    /**
     * Sets the badge value on left notification icon
     *
     * @param notificationBadgeValue badge value to be displayed.
     */
    public void setLeftNavigationItemBadgeValue(int notificationBadgeValue) {
        if (toolbarView != null) {
            toolbarView.setLeftNavigationItemBadgeValue(notificationBadgeValue);
        }
    }

    /**
     * Sets click listener for toolbar left icon
     */
    public void setToolbarLeftIconClickListener(DBSDashboardToolbarView.ILeftItemClickListener listener) {
        if (toolbarView != null) {
            toolbarView.setOnLeftItemClickListener(listener);
        }
    }

    /**
     * Sets click listener for toolbar right icon
     */
    public void setToolbarRightIconClickListener(DBSDashboardToolbarView.IRightItemClickListener listener) {
        if (toolbarView != null) {
            toolbarView.setOnRightItemClickListener(listener);
        }
    }

    private void updateWeatherDetails(WeatherDetails weatherDetails) {
        weatherDetailsView.setVisibility(VISIBLE);
        ((TextView) findViewById(R.id.dashboard_temperature)).setText(weatherDetails.getTemperature());
        ((TextView) findViewById(R.id.dashboard_weather_info)).setText(getLocaleStringResource(weatherDetails.getWeatherInfo()));
        ((TextView) findViewById(R.id.dashboard_weather_message)).setText(weatherDetails.getMessage());
        if (!customGreetingMessage) {
            String message = getResources().getString(R.string.dashboard_greeting_message, getLocaleStringResource(GreetingUtil.getGreetingMessageResource()));
            ((TextView) findViewById(R.id.dashboard_greeting_message)).setText(message);
        }
        ((ImageView) findViewById(R.id.dashboard_weather_icon)).setImageDrawable(getResources().getDrawable(weatherDetails.getWeatherIcon()));
    }

    private String getLocaleStringResource(int resourceId) {
        if (resourceId == -1) {
            return null;
        }

        Configuration config = new Configuration(getResources().getConfiguration());
        config.setLocale(locale);
        return getContext().createConfigurationContext(config).getText(resourceId).toString();
    }

    /**
     * Available types of config with which we can create dashboard header.
     */
    public enum Config {
        SINGAPORE,
        TAIWAN
    }

    /**
     * When an object of a type is attached to {@link DBSDashboardHeaderView}, its methods will
     * be called on user interaction
     */
    public interface IListener {
        /**
         * This method is called when show more details button is clicked.
         */
        void showMore();
    }

    /**
     * Builder for {@link DBSDashboardHeaderView}
     */
    public static class Builder {
        private Context context;
        private Config config;
        private int notificationBadgeValue;
        private DBSDashboardToolbarView.ILeftItemClickListener toolbarLeftIconClickListener;
        private DBSDashboardToolbarView.IRightItemClickListener toolbarRightIconClickListener;

        /**
         * Constructor with mandatory fields.
         *
         * @param context Current context
         * @param config  Config with which we can create this header.
         * @see Config
         */
        public Builder(Context context, Config config) {
            this.context = context;
            this.config = config;
        }

        /**
         * Sets the badge value
         *
         * @return The current instance to be able to do chaining of builder calls
         */
        public Builder withNotificationBadgeValue(int notificationBadgeValue) {
            this.notificationBadgeValue = notificationBadgeValue;
            return this;
        }

        /**
         * Sets toolbar left icon click listener
         *
         * @return The current instance to be able to do chaining of builder calls
         */
        public Builder withToolbarLeftIconClickListener(DBSDashboardToolbarView.ILeftItemClickListener listener) {
            toolbarLeftIconClickListener = listener;
            return this;
        }

        /**
         * Sets toolbar right icon click listener
         *
         * @return The current instance to be able to do chaining of builder calls
         */
        public Builder withToolbarRightIconClickListener(DBSDashboardToolbarView.IRightItemClickListener listener) {
            toolbarRightIconClickListener = listener;
            return this;
        }

        /**
         * Creates an {@link DBSDashboardHeaderView} with the arguments supplied to this
         * builder.
         */
        public DBSDashboardHeaderView build() {
            DBSDashboardHeaderView dashboardHeaderView = new DBSDashboardHeaderView(context);

            if (config == Config.SINGAPORE) {
                dashboardHeaderView.setHeaderBackground(R.drawable.dashboard_header_background_sg);
                dashboardHeaderView.enableShowMoreDetails();
                dashboardHeaderView.setupToolbar(R.drawable.ic_bell, R.drawable.ic_chatbot);
                if (notificationBadgeValue != -1) {
                    dashboardHeaderView.setLeftNavigationItemBadgeValue(notificationBadgeValue);
                }
                if (toolbarLeftIconClickListener != null) {
                    dashboardHeaderView.setToolbarLeftIconClickListener(toolbarLeftIconClickListener);
                }
                if (toolbarRightIconClickListener != null) {
                    dashboardHeaderView.setToolbarRightIconClickListener(toolbarRightIconClickListener);
                }
                dashboardHeaderView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                return dashboardHeaderView;
            } else if (config == Config.TAIWAN) {
                dashboardHeaderView.setHeaderBackground(R.drawable.dashboard_header_background_tw);
                dashboardHeaderView.setupToolbar(R.drawable.ic_bell, R.drawable.ic_lock);
                if (notificationBadgeValue != -1) {
                    dashboardHeaderView.setLeftNavigationItemBadgeValue(notificationBadgeValue);
                }
                if (toolbarLeftIconClickListener != null) {
                    dashboardHeaderView.setToolbarLeftIconClickListener(toolbarLeftIconClickListener);
                }
                if (toolbarRightIconClickListener != null) {
                    dashboardHeaderView.setToolbarRightIconClickListener(toolbarRightIconClickListener);
                }
                dashboardHeaderView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                dashboardHeaderView.locale = Locale.TAIWAN;
                return dashboardHeaderView;
            }
            return dashboardHeaderView;
        }
    }
}