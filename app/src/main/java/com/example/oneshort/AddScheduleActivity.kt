package com.example.oneshort

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddScheduleActivity : AppCompatActivity() {
    private lateinit var tvDate: TextView
    private lateinit var etScheduleTitle: EditText
    private lateinit var spinnerStartTime: Spinner
    private lateinit var spinnerEndTime: Spinner
    private lateinit var btnCancel: Button
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_schedule)

        // View 연결
        tvDate = findViewById(R.id.tv_schedule_date)
        etScheduleTitle = findViewById(R.id.et_schedule_title)
        spinnerStartTime = findViewById(R.id.spinner_start_time)
        spinnerEndTime = findViewById(R.id.spinner_end_time)
        btnCancel = findViewById(R.id.btn_cancel)
        btnRegister = findViewById(R.id.btn_register)

        // 현재 날짜 받아오기 (임시값: 실제 앱에서는 DatePicker 등 활용 가능)
        tvDate.text = "2025년 3월 7일"

        // 00시부터 23시까지 리스트 생성
        val timeList = (0..23).map { "${it} 시" }

        // 시작 시간 어댑터 설정
        val startAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, timeList)
        spinnerStartTime.adapter = startAdapter

        // 종료 시간 어댑터 설정 (초기값은 시작 시간보다 1시간 늦은 값부터)
        spinnerEndTime.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, timeList)

        // 시작 시간을 선택하면 종료 시간을 자동 업데이트
        spinnerStartTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // 시작 시간 이후의 시간만 종료시간으로 설정
                val filteredTimeList = timeList.drop(position + 1) // 시작 시간 이후의 값만 리스트에 포함
                val endAdapter = ArrayAdapter(this@AddScheduleActivity, android.R.layout.simple_spinner_dropdown_item, filteredTimeList)
                spinnerEndTime.adapter = endAdapter

                // 기본적으로 종료시간을 리스트의 첫 번째 값으로 설정
                if (filteredTimeList.isNotEmpty()) {
                    spinnerEndTime.setSelection(0)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // 일정 등록 버튼 클릭 이벤트
        btnRegister.setOnClickListener {
            val title = etScheduleTitle.text.toString()
            val startTime = spinnerStartTime.selectedItem.toString()
            val endTime = spinnerEndTime.selectedItem.toString()

            if (title.isEmpty()) {
                Toast.makeText(this, "일정 제목을 입력해주세요!", Toast.LENGTH_SHORT).show()
            } else {
                // 🔥 To Do: 일정 저장 기능 (DB 저장 또는 SharedPreferences 활용)
                Toast.makeText(this, "일정이 추가되었습니다!\n$title\n$startTime ~ $endTime", Toast.LENGTH_SHORT).show()
                finish() // 현재 액티비티 종료
            }
        }

        // 취소 버튼 클릭 이벤트
        btnCancel.setOnClickListener {
            finish() // 현재 화면 종료
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
