package com.xdeepakv.themedemo

import android.app.Activity
import android.content.Intent
import android.preference.PreferenceManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat


class ThemeUtils {
    companion object {
        enum class THEMES(val themeName: String, val styleId: Int) {
            INDIA("India", R.style.AppTheme_NoActionBar_India),
            INDONESIA("Indonesia", R.style.AppTheme_NoActionBar_Indo),
            SINGAPORE("Singapore", R.style.AppTheme_NoActionBar_Singapore);

            fun getName(): String {
                return themeName
            }

            fun value(): Int {
                return styleId
            }
        }

        fun switchTheme(activity: Activity, theme: THEMES) {
            if (getThemeName(activity) == theme.themeName) {
                return
            }
            val pref = PreferenceManager
                    .getDefaultSharedPreferences(activity.application)
            pref.edit().putString("THEME_NAME", theme.themeName).commit()
            val restartIntent = Intent(activity, activity::class.java)

            val extras = activity.intent.extras
            if (extras != null) {
                restartIntent.putExtras(extras!!)
            }
            ActivityCompat.startActivity(
                    activity,
                    restartIntent,
                    ActivityOptionsCompat
                            .makeCustomAnimation(activity, android.R.anim.fade_in, android.R.anim.fade_out)
                            .toBundle()
            )
            activity.finish()
//            context.recreate()
        }

        fun loadTheme(activity: Activity) {
            when (getThemeName(activity)) {
                THEMES.SINGAPORE.themeName -> activity.setTheme(THEMES.SINGAPORE.styleId)
                THEMES.INDONESIA.themeName -> activity.setTheme(THEMES.INDONESIA.styleId)
                else -> activity.setTheme(THEMES.INDIA.styleId)
            }
        }

        private fun getThemeName(activity: Activity): String {
            val pref = PreferenceManager
                    .getDefaultSharedPreferences(activity.application)
            return pref.getString("THEME_NAME", THEMES.INDIA.themeName);

        }
    }
}