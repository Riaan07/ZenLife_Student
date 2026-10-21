package com.csiq6823.zenlife.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.csiq6823.zenlife.R

class ComfortBreakDialog : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_comfort_break, container, false)
        view.findViewById<Button>(R.id.btn_dialog_close).setOnClickListener { dismiss() }
        return view
    }
}