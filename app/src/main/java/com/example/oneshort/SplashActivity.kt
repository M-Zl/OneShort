package com.example.oneshort

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 2000 // 2초 딜레이

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)  // 커스텀 splash 레이아웃 적용

        // 일정 시간 지연 이후 실행하기 위한 코드
        // 이전 키를 눌렀을 때 스플래스 스크린 화면으로 이동을 방지하기 위해
        // 이동한 다음 사용안함으로 finish 처리
        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_DELAY)
    }
}
