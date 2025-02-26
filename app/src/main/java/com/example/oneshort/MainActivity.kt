package com.example.oneshort

import android.content.Intent  // ← 여기에 추가!
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // + 버튼 클릭 이벤트 추가
        val fabAdd: FloatingActionButton = findViewById(R.id.fab_add)
        fabAdd.setOnClickListener {
            // 일정 추가 화면으로 이동
            val intent = Intent(this, AddScheduleActivity::class.java)
            startActivity(intent)
        }
    }
}