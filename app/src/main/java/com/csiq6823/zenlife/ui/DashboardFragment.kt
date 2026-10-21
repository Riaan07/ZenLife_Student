package com.csiq6823.zenlife.ui

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.csiq6823.zenlife.R
import com.csiq6823.zenlife.background.BackgroundTimerEngine
import com.csiq6823.zenlife.viewmodel.ZenLifeViewModel

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private val viewModel: ZenLifeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        // TODO: CHAPTER 10 - Instantiate and bind a vertical LinearLayoutManager to your list view widget container.

        var totalDailyScrollMinutes = 0

        // TODO: CHAPTER 8 & 13 - Connect an architecture lifecycle-aware observer to viewmodel.allLogs.
        // Extract cumulative scrolling records inside the live data stream, update totalDailyScrollMinutes,
        // and instantly pass the collection snapshot down into a new instance of your LogAdapter.

        // Action Click Buttons
        view.findViewById<Button>(R.id.btn_log_walk).setOnClickListener {
            // TODO: CHAPTER 13 - Invoke ViewModel activity logging execution methods for WALKING
        }
        view.findViewById<Button>(R.id.btn_log_scroll).setOnClickListener {
            // TODO: CHAPTER 13 - Invoke ViewModel activity logging execution methods for DOOM_SCROLLING
        }
        view.findViewById<Button>(R.id.btn_fingerprint).setOnClickListener {
            // TODO: STUDENT ID FINGERPRINT TASK - Invoke activity logging execution methods for your custom metric
        }

        view.findViewById<Button>(R.id.btn_start_timer).setOnClickListener {
            // TODO: SYSTEM TASK HOOKUP - Forward totalDailyScrollMinutes into the provided BackgroundTimerEngine tracker.
            // TODO: CHAPTER 14 - Execute declarative routing actions to pop open the custom layout break dialog.
        }

        // TODO: CHAPTER 15 - Inject your contextual dashboard action bar menu using the MenuProvider framework.
        // Ensure you strictly bound the menu's lifecycle visibility to the RESUMED lifecycle state to avoid layout leaks.
        // Tapping the clear action item must navigate to the confirm deletion dialog overlay.
    }
}
