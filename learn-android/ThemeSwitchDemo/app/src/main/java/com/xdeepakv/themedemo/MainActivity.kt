package com.xdeepakv.themedemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.xdeepakv.themedemo.ThemeUtils.Companion.THEMES
import com.xdeepakv.themedemo.ThemeUtils.Companion.loadTheme
import com.xdeepakv.themedemo.ThemeUtils.Companion.switchTheme
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadTheme(this)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        init()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun init() {
        val spinner = findViewById(R.id.themeSpinner) as Spinner
        val adapter = ArrayAdapter.createFromResource(this, R.array.theme_names, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0, false)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val themeName = parent.getItemAtPosition(pos);
                Toast.makeText(this@MainActivity, "loading: $themeName", Toast.LENGTH_SHORT).show();
                when (themeName) {
                    "India" -> switchTheme(this@MainActivity, THEMES.INDIA)
                    "Indonesia" -> switchTheme(this@MainActivity, THEMES.INDONESIA)
                    "Singapore" -> switchTheme(this@MainActivity, THEMES.SINGAPORE)
                }
            }
        }
    }
}
