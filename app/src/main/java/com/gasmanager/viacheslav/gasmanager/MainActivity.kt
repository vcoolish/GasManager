package com.gasmanager.viacheslav.gasmanager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val TAG = "myLogs"
    val PAGE_COUNT = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val setbtn = findViewById(R.id.setbtn) as ImageView
        val pager = findViewById(R.id.pager) as ViewPager
        val pagerAdapter = MyFragmentPagerAdapter(getSupportFragmentManager())
        pager.setAdapter(pagerAdapter);
        val MY_SETTINGS = "my_settings"
        val sp = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE)
        val hasVisited: Boolean = sp.getBoolean("hasVisited", false)
        if (!hasVisited) {
            val intent = Intent(this, AdditionActivity::class.java)
            startActivity(intent)
            finish()
        }

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {


            override fun onPageSelected(position: Int) {
                Log.d(TAG, "onPageSelected, position = " + position);
            }


            override fun onPageScrolled(position: Int, positionOffset: Float,
                                        positionOffsetPixels: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        });




        fab.setOnClickListener {
            val intent = Intent(this, Addfuel::class.java)
            startActivity(intent)
        }

        setbtn.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }


        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val item = menu.findItem(R.id.action_settings)
        item.isVisible = false
        super.onPrepareOptionsMenu(menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.cars -> {
                Toast.makeText(this, resources.getText(R.string.nomenu),
                        Toast.LENGTH_LONG).show();
            }
            R.id.records -> {
                val intent = Intent(this, EntryActivity::class.java)
                startActivity(intent)
            }
            R.id.advice -> {
                val intent = Intent(this, AdviceActivity::class.java)
                startActivity(intent)
            }
            R.id.howto -> {
                val intent = Intent(this, HowtoActivity::class.java)
                startActivity(intent)
            }
            R.id.about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }
            R.id.feedback -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("market://details?id=com.gasmanager.viacheslav.gasmanager")
                startActivity(intent)
            }
            R.id.donate -> {
                val intent = Intent(this, PaymentActivity::class.java)
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private inner class MyFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return PageFragment.newInstance(position)
        }


        override fun getCount(): Int {
            return PAGE_COUNT
        }

        override fun getPageTitle(position: Int): CharSequence {
            val titles = arrayOf(getResources().getText(R.string.maintab1), getResources().getText(R.string.maintab2), getResources().getText(R.string.maintab3), getResources().getText(R.string.maintab4))
            return titles[position]
        }

    }
//    private fun addChartPage (monthnum: [Int]?, values: [Double]?, )
//
//
}
