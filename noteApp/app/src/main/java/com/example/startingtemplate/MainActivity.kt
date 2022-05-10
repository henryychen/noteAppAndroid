package com.example.startingtemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    // create view model using delegation
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // layout is defined in "res/layouts/activity_main.xml"
        // (it must have a NavHostFragment element)
        setContentView(R.layout.activity_main)

        // observe viewModel properties here
        viewModel.numDisplayedNotes.observe(this) {
            if (it == viewModel.allNotes.value?.size) {
                if (it == 1) {
                    supportActionBar?.subtitle = "($it note)"
                } else {
                    supportActionBar?.subtitle = "($it notes)"
                }
            } else {
                supportActionBar?.subtitle = "(matching $it of ${viewModel.allNotes.value?.size} notes)"
            }
        }

        viewModel.snackBarMessage.observe(this) {
            println(it)
            var snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), "$it", 1000)
            snackbar.show()
        }

        // if you need the navController in activity (for bottom nav, etc.)
//        val navController = findNavController(findViewById(R.id.navhostfragment))
    }

    // region Material ActionBar options menu setup and events

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // uncomment line below to add ActionBar options menu
//        menuInflater.inflate(R.menu.action_menu, menu)
        // (menu is defined in "res/menus/action_menu.xml")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.actionName -> {
                // update viewModel for this action
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //endregion
}