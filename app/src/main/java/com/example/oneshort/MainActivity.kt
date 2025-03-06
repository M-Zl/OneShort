package com.example.oneshort

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager



class MainActivity : AppCompatActivity() {
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var dimBackground: View
    private lateinit var newScheduleMenu: LinearLayout
    private var isMenuOpen = false  // 메뉴 상태 변수
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

        // 배경을 클릭하면 메뉴 닫기
        dimBackground.setOnClickListener {
            closeMenu()
        }

        // 신규 일정 등록 메뉴에서 일정 추가 버튼 클릭 시 새로운 화면으로 이동
        newScheduleMenu.setOnClickListener {
            val intent = Intent(this, AddScheduleActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recycler_schedule)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // RecyclerView 어댑터 설정
        scheduleAdapter = ScheduleAdapter(scheduleList) { schedule ->
            // 클릭한 일정 정보를 EditScheduleActivity로 전달
            val intent = Intent(this, EditScheduleActivity::class.java).apply {
                putExtra("SCHEDULE_TITLE", schedule.title)
                putExtra("SCHEDULE_DATE", schedule.date)
            }
            startActivity(intent)
        }

        recyclerView.adapter = scheduleAdapter

        // 일정 데이터 샘플 추가 (테스트용)
        scheduleList.add(Schedule("장미랑 커피 마시기", "2025년 2월 26일"))
        scheduleList.add(Schedule("회의 참석", "2025년 2월 27일"))

        scheduleAdapter.notifyDataSetChanged()
    }

    private fun toggleMenu() {
        if (isMenuOpen) {
            closeMenu()
        } else {
            openMenu()
        }
    }

    private fun openMenu() {
        fabAdd.setImageResource(R.drawable.ic_close) // `X` 아이콘으로 변경
        fabAdd.backgroundTintList = ColorStateList.valueOf(Color.WHITE) // 배경 화이트
        dimBackground.visibility = View.VISIBLE // 배경 어둡게
        newScheduleMenu.visibility = View.VISIBLE // 신규 일정 메뉴 표시
        isMenuOpen = true
    }

    private fun closeMenu() {
        fabAdd.setImageResource(R.drawable.ic_add_white) // `+` 아이콘으로 변경
        fabAdd.backgroundTintList = ColorStateList.valueOf(Color.BLACK) // 배경 블랙
        dimBackground.visibility = View.GONE // 배경 원래대로
        newScheduleMenu.visibility = View.GONE // 신규 일정 메뉴 숨김
        isMenuOpen = false
    }
}