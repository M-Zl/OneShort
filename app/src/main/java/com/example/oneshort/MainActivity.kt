package com.example.oneshort

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oneshort.data.RetrofitClient
import com.example.oneshort.data.Schedule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.oneshort.ScheduleAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var dimBackground: View
    private lateinit var newScheduleMenu: LinearLayout
    private var isMenuOpen = false
    private lateinit var recyclerView: RecyclerView
    private lateinit var scheduleAdapter: ScheduleAdapter
    private val scheduleList = mutableListOf<Schedule>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabAdd = findViewById(R.id.fab_add)
        dimBackground = findViewById(R.id.dim_background)
        newScheduleMenu = findViewById(R.id.new_schedule_menu)

        fabAdd.setOnClickListener {
            toggleMenu()
        }

        dimBackground.setOnClickListener {
            closeMenu()
        }

        newScheduleMenu.setOnClickListener {
            val intent = Intent(this, AddScheduleActivity::class.java)
            startActivity(intent)
            closeMenu()
        }

        recyclerView = findViewById(R.id.recycler_schedule)
        recyclerView.layoutManager = LinearLayoutManager(this)

        scheduleAdapter = ScheduleAdapter(scheduleList) { schedule ->
            val intent = Intent(this, EditScheduleActivity::class.java).apply {
                putExtra("SCHEDULE_TITLE", schedule.title)
                putExtra("SCHEDULE_DATE", schedule.date)
            }
            startActivity(intent)
        }

        recyclerView.adapter = scheduleAdapter

        // 🔹 🔄 서버에서 오늘 일정 불러오기 호출 위치
        loadTodaySchedules()
    }

    private fun loadTodaySchedules() {
        RetrofitClient.scheduleApi.getTodaySchedules(1).enqueue(object : Callback<List<Schedule>> {
            override fun onResponse(
                call: Call<List<Schedule>>,
                response: Response<List<Schedule>>
            ) {
                if (response.isSuccessful) {
                    val schedules = response.body() ?: emptyList()
                    scheduleList.clear()
                    scheduleList.addAll(schedules)
                    scheduleAdapter.notifyDataSetChanged()
                    Log.d("스케줄", "받은 일정 목록: $schedules")
                } else {
                    Log.e("스케줄", "응답 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Schedule>>, t: Throwable) {
                Log.e("스케줄", "서버 연결 실패: ${t.message}")
            }
        })
    }

    override fun onBackPressed() {
        if (isMenuOpen) {
            closeMenu()
        } else {
            super.onBackPressed()
        }
    }

    private fun toggleMenu() {
        if (isMenuOpen) {
            closeMenu()
        } else {
            openMenu()
        }
    }

    private fun openMenu() {
        fabAdd.setImageResource(R.drawable.ic_close)
        fabAdd.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
        dimBackground.visibility = View.VISIBLE
        newScheduleMenu.visibility = View.VISIBLE
        isMenuOpen = true
    }

    private fun closeMenu() {
        fabAdd.setImageResource(R.drawable.ic_add_white)
        fabAdd.backgroundTintList = ColorStateList.valueOf(Color.BLACK)
        dimBackground.visibility = View.GONE
        newScheduleMenu.visibility = View.GONE
        isMenuOpen = false
    }
}