package com.csiq6823.zenlife.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.csiq6823.zenlife.R
import com.csiq6823.zenlife.data.WellnessLog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LogAdapter(private val logs: List<WellnessLog>) : RecyclerView.Adapter<LogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        // TODO: CHAPTER 9 & 10 - Inflate the list_item_log XML layout file securely using an explicit LayoutInflater.
        // Ensure you supply correct parameters to prevent runtime parent view pre-attachment errors.
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_log, parent, false)
        return LogViewHolder(view)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        // TODO: CHAPTER 10 & 11 - Calculate cumulative totals by processing a historic sublist snapshot
        // from the dataset list down to the current item row index position.
        val history = logs.subList(position, logs.size)
        val runningWalkTotal = history.filter { it.type == "WALKING" }.sumOf { it.durationMinutes }
        val runningScrollTotal = history.filter { it.type == "DOOM_SCROLLING" }.sumOf { it.durationMinutes }
        val runningHydrationCount = history.filter { it.type == "HYDRATION" }.sumOf { it.durationMinutes }


        holder.bind(logs[position], runningWalkTotal, runningScrollTotal, runningHydrationCount)
    }

    override fun getItemCount() = logs.size
}

class LogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val cardView: CardView = itemView.findViewById(R.id.item_card)
    private val titleText: TextView = itemView.findViewById(R.id.text_title)
    private val detailText: TextView = itemView.findViewById(R.id.text_detail)

    private val MAX_TARGET_WALK = 60
    private val MAX_LIMIT_SCROLL = 120
    private val TARGET_HYDRATION_ML = 2000

    private val timeFormatter = SimpleDateFormat("dd MMM, HH:mm:ss", Locale.getDefault())
    private var isShowingDetails = false

    fun bind(log: WellnessLog, currentWalk: Int, currentScroll: Int, currentHydration: Int) {
        val humanTime = timeFormatter.format(Date(log.timestamp))
        titleText.text = "${log.type.replace("_", " ")} [$humanTime]"

        // TODO: CHAPTER 10 & 11 - Manage card recycling safety by enforcing an initial state flag reset.
        // Then, construct your baseline, multi-branch contextual layout formatting map logic.
        // Remember to map your unique assigned Student ID Fingerprint metric requirements here!
        isShowingDetails = false
        val overview: String
        val expanded: String
        when (log.type) {
            "WALKING" -> {
                cardView.setCardBackgroundColor(Color.parseColor("#E3F2FD"))
                val remaining = (60 - currentWalk).coerceAtLeast(0)
                overview = "$remaining mins remaining to meet daily goals!"
                expanded = "Progress: $currentWalk/60 mins done. Keep going!"
            }
            "DOOM_SCROLLING" -> {
                cardView.setCardBackgroundColor(Color.parseColor("#FFCDD2"))
                val remaining = (120 - currentScroll).coerceAtLeast(0)
                overview = "$remaining mins remaining before your safety limit."
                expanded = "Doom scrolling: $currentScroll/120 mins used."
            }
            "HYDRATION" -> {
                cardView.setCardBackgroundColor(Color.parseColor("#E0F7FA"))
                val millilitres = currentHydration * 250
                overview = "$millilitres ml consumed"
                expanded = "Hydration progress: $millilitres ml consumed so far."
            }
            else -> {
                cardView.setCardBackgroundColor(Color.WHITE)
                overview = "Activity recorded"
                expanded = "Activity details"
            }
        }
        detailText.text = overview




        // TODO: CHAPTER 10 - Bind an interactive click listener to 'itemView' to flip the boolean visibility flag
        // and switch presentation strings back and forth cleanly when the row is tapped repeatedly.
        itemView.setOnClickListener {
            isShowingDetails = !isShowingDetails
            detailText.text = if (isShowingDetails) expanded else overview
        }
    }
}
