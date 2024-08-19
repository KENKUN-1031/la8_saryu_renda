package com.example.testproject

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.testproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //バインディングクラスの変数
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root)}
        //タップを変える変数を準備
        var tapCount: Int = 0
        var second = 10
        // valとvarの違い
        val timer: CountDownTimer = object : CountDownTimer(10000,1000) {
            //タイマー終了時の処理
            override fun onFinish() {
                binding.startButton.isVisible = true
                // 多分color.xmlから読み込んだ色に変更してる(DOMと同じ原理) ↓
                binding.tapButton.backgroundTintList = ContextCompat.getColorStateList(this@MainActivity, R.color.gray) //呼び出し方なんで "R"??  Pixel 5 API 30 が影響してるかも?(R選択した)
                // 終わったタイミングかだから変数を初期化
                second = 10
                tapCount = 0
            }
            // 一秒ごとに呼ばれる (onTickがそもそも一秒に一回のscheduler)
            override fun onTick(millisUntilFinished: Long) {
                binding.tapButton.backgroundTintList = ContextCompat.getColorStateList(this@MainActivity, R.color.pink)
                second -= 1
                binding.secondText.text = second.toString()
            }
        }

        binding.startButton.setOnClickListener {
            binding.countText.text = tapCount.toString() //反映させる(str型)
            binding.startButton.isVisible = false //startButton押されたら透明になる
            timer.start() //timer開始してる(メソッド呼び出してる)
        }
        //ボタン押された時の処理
        binding.tapButton.setOnClickListener {
            if (second < 10) {
//                tapCount = tapCount + 1
                tapCount += 1
                //変数をviewに反映させる
                binding.countText.text = tapCount.toString()
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}