package com.example.oneshort

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 일정 데이터 클래스
data class Schedule(val title: String, val date: String)

class ScheduleAdapter(private var scheduleList: MutableList<Schedule>, private val onItemClick: (Schedule) -> Unit) :
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    class ViewHolder(view: View, private val onItemClick: (Schedule) -> Unit) : RecyclerView.ViewHolder(view) {
        private val tvScheduleTitle: TextView = view.findViewById(R.id.tv_schedule_title)
        private val tvScheduleDate: TextView = view.findViewById(R.id.tv_schedule_date)

        fun bind(schedule: Schedule) {
            tvScheduleTitle.text = schedule.title
            tvScheduleDate.text = schedule.date

            // 일정 클릭 이벤트 추가
            itemView.setOnClickListener {
                onItemClick(schedule)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(scheduleList[position])
    }

    override fun getItemCount() = scheduleList.size

    // 🔥 새로운 일정 리스트로 업데이트하는 함수
    fun updateList(newList: List<Schedule>) {
        scheduleList.clear()
        scheduleList.addAll(newList)
        notifyDataSetChanged()
    }
}
