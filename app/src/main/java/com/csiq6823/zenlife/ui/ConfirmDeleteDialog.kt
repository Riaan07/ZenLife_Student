package com.csiq6823.zenlife.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.csiq6823.zenlife.R
import com.csiq6823.zenlife.viewmodel.ZenLifeViewModel

class ConfirmDeleteDialog : DialogFragment() {

    // TODO: CHAPTER 15 - Scope this viewmodel initialization to the lifecycle bounds
    // of the parent Host Activity container so that the dialog shares the active data state.
    // Replace this placeholder or initialize your ViewModel cleanly here.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: CHAPTER 15 - Inflate the custom 'dialog_confirm_delete' layout sheet layout hierarchy.
        val view = View(context)

        // TODO: CHAPTER 15 - Wire click triggers to cancel button components to dismiss the modal cleanly.

        // TODO: CHAPTER 15 - Wire click triggers to the confirm deletion button widget.
        // It must safely invoke viewmodel.clearLogs() and then hide the overlay using dismiss().

        return view
    }
}
