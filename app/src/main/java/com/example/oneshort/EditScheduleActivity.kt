package com.example.oneshort

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EditScheduleActivity : AppCompatActivity() {
    private lateinit var tvDate: TextView
    private lateinit var etScheduleTitle: EditText
    private lateinit var spinnerStartTime: Spinner
    private lateinit var spinnerEndTime: Spinner
    private lateinit var btnDelete: Button
    private lateinit var btnUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_schedule)

        // View 연결
        tvDate = findViewById(R.id.tv_schedule_date)
        etScheduleTitle = findViewById(R.id.et_schedule_title)
        spinnerStartTime = findViewById(R.id.spinner_start_time)
        spinnerEndTime = findViewById(R.id.spinner_end_time)
        btnDelete = findViewById(R.id.btn_delete)
        btnUpdate = findViewById(R.id.btn_update)

        // 기존 일정 데이터 받아오기
        val scheduleTitle = intent.getStringExtra("scheduleTitle")
        val scheduleDate = intent.getStringExtra("scheduleDate")

        // 데이터 설정
        tvDate.text = scheduleDate
        etScheduleTitle.setText(scheduleTitle)

        // 00시부터 23시까지의 리스트 생성
        val startTimeList = (0..23).map { "${it} 시" }

        // 시작 시간 어댑터 설정
        val startAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, startTimeList)
        spinnerStartTime.adapter = startAdapter

        // 시작 시간을 선택하면 종료 시간을 자동 업데이트
        spinnerStartTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // 🔹 선택한 시작 시간 이후부터 24시까지 종료 시간 리스트 설정
                val filteredEndTimeList = (position + 1..24).map { "${it} 시" } // 24시 포함
                val endAdapter = ArrayAdapter(this@EditScheduleActivity, android.R.layout.simple_spinner_dropdown_item, filteredEndTimeList)
                spinnerEndTime.adapter = endAdapter

                // 🔹 기본적으로 종료 시간을 리스트의 첫 번째 값으로 설정
                if (filteredEndTimeList.isNotEmpty()) {
                    spinnerEndTime.setSelection(0)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // 버튼 클릭 이벤트 설정
        btnUpdate.setOnClickListener {
            // To Do List :::  일정 수정 기능 추가할 부분 (DB 업데이트 또는 SharedPreferences)
        }

        btnDelete.setOnClickListener {
            // To Do List :::  일정 삭제 기능 추가할 부분 (DB 삭제 또는 SharedPreferences)
        }
    }

    // 화면 터치 시 키보드 숨기기
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                view.clearFocus()  // EditText 포커스 해제
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}
