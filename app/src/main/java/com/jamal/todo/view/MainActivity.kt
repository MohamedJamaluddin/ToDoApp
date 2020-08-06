package com.jamal.todo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jamal.todo.R
import com.jamal.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initVariables()
        viewModel.navigateToChildFragment(TaskFragment.newInstance())
    }

    private fun initVariables() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.initFragmentManager(supportFragmentManager)
    }

    fun addFab(): FloatingActionButton? {
        val fab = findViewById<FloatingActionButton>(R.id.fab_bottom_main)
        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add))
        fab.show()
        return fab
    }


    fun doneFab(): FloatingActionButton? {
        val fab = findViewById<FloatingActionButton>(R.id.fab_bottom_main)
        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_tick))
        fab.show()
        return fab
    }
}