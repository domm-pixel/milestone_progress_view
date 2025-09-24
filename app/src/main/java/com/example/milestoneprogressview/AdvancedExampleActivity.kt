package com.example.milestoneprogressview

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.dommpixel.milestoneprogressview.MilestoneProgressView

/**
 * Advanced example showing different configurations and use cases
 */
class AdvancedExampleActivity : AppCompatActivity() {

    private lateinit var progressView1: MilestoneProgressView
    private lateinit var progressView2: MilestoneProgressView
    private lateinit var progressView3: MilestoneProgressView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advanced_example)

        initializeViews()
        setupExamples()
        setupButtons()
    }

    private fun initializeViews() {
        progressView1 = findViewById(R.id.progressView1)
        progressView2 = findViewById(R.id.progressView2)
        progressView3 = findViewById(R.id.progressView3)
    }

    private fun setupExamples() {
        // Example 1: Software Development Lifecycle
        val developmentMilestones = listOf(
            MilestoneProgressView.Milestone(0.0f, "계획", true),
            MilestoneProgressView.Milestone(0.2f, "설계", true),
            MilestoneProgressView.Milestone(0.4f, "구현", true),
            MilestoneProgressView.Milestone(0.6f, "테스트", false),
            MilestoneProgressView.Milestone(0.8f, "배포", false),
            MilestoneProgressView.Milestone(1.0f, "유지보수", false)
        )
        
        progressView1.apply {
            setMilestones(developmentMilestones)
            setProgress(0.45f)
            progressColor = Color.parseColor("#3F51B5")
            milestoneColor = Color.parseColor("#4CAF50")
            backgroundColor = Color.parseColor("#E8EAF6")
        }

        // Example 2: Learning Path
        val learningMilestones = listOf(
            MilestoneProgressView.Milestone(0.0f, "입문", true),
            MilestoneProgressView.Milestone(0.3f, "기초", true),
            MilestoneProgressView.Milestone(0.6f, "중급", true),
            MilestoneProgressView.Milestone(1.0f, "고급", false)
        )
        
        progressView2.apply {
            setMilestones(learningMilestones)
            setProgress(0.7f)
            progressColor = Color.parseColor("#FF9800")
            milestoneColor = Color.parseColor("#FF5722")
            backgroundColor = Color.parseColor("#FFF3E0")
            progressBarHeight = 12f
            milestoneRadius = 16f
        }

        // Example 3: Fitness Goals
        val fitnessMilestones = listOf(
            MilestoneProgressView.Milestone(0.0f, "시작", true),
            MilestoneProgressView.Milestone(0.25f, "1개월", true),
            MilestoneProgressView.Milestone(0.5f, "3개월", true),
            MilestoneProgressView.Milestone(0.75f, "6개월", false),
            MilestoneProgressView.Milestone(1.0f, "목표달성", false)
        )
        
        progressView3.apply {
            setMilestones(fitnessMilestones)
            setProgress(0.6f)
            progressColor = Color.parseColor("#9C27B0")
            milestoneColor = Color.parseColor("#E91E63")
            backgroundColor = Color.parseColor("#F3E5F5")
            textColor = Color.parseColor("#4A148C")
        }
    }

    private fun setupButtons() {
        findViewById<Button>(R.id.btnAnimateAll).setOnClickListener {
            progressView1.animateProgress(1.0f, 3000L)
            progressView2.animateProgress(1.0f, 3000L)
            progressView3.animateProgress(1.0f, 3000L)
        }

        findViewById<Button>(R.id.btnResetAll).setOnClickListener {
            progressView1.setProgress(0.45f)
            progressView2.setProgress(0.7f)
            progressView3.setProgress(0.6f)
        }

        findViewById<Button>(R.id.btnRandomProgress).setOnClickListener {
            val random1 = (0..100).random() / 100f
            val random2 = (0..100).random() / 100f
            val random3 = (0..100).random() / 100f
            
            progressView1.animateProgress(random1, 1500L)
            progressView2.animateProgress(random2, 1500L)
            progressView3.animateProgress(random3, 1500L)
        }
    }
}