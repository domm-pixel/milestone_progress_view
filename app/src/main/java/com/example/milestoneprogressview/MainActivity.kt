package com.example.milestoneprogressview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.dommpixel.milestoneprogressview.MilestoneProgressView

class MainActivity : AppCompatActivity() {

    private lateinit var milestoneProgressView1: MilestoneProgressView
    private lateinit var milestoneProgressView2: MilestoneProgressView
    private lateinit var progressSeekBar: SeekBar
    private lateinit var animateButton: Button
    private lateinit var advancedExampleButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupMilestones()
        setupControls()
    }

    private fun initializeViews() {
        milestoneProgressView1 = findViewById(R.id.milestoneProgressView1)
        milestoneProgressView2 = findViewById(R.id.milestoneProgressView2)
        progressSeekBar = findViewById(R.id.progressSeekBar)
        animateButton = findViewById(R.id.animateButton)
        advancedExampleButton = findViewById(R.id.advancedExampleButton)
    }

    private fun setupMilestones() {
        // Setup first progress view with project milestones
        val projectMilestones = listOf(
            MilestoneProgressView.Milestone(0.0f, "시작", true),
            MilestoneProgressView.Milestone(0.25f, "기획", true),
            MilestoneProgressView.Milestone(0.5f, "개발", false),
            MilestoneProgressView.Milestone(0.75f, "테스트", false),
            MilestoneProgressView.Milestone(1.0f, "출시", false)
        )
        milestoneProgressView1.setMilestones(projectMilestones)
        milestoneProgressView1.setProgress(0.3f)

        // Setup second progress view with learning milestones
        val learningMilestones = listOf(
            MilestoneProgressView.Milestone(0.0f, "기초", true),
            MilestoneProgressView.Milestone(0.33f, "중급", true),
            MilestoneProgressView.Milestone(0.66f, "고급", false),
            MilestoneProgressView.Milestone(1.0f, "마스터", false)
        )
        milestoneProgressView2.setMilestones(learningMilestones)
        milestoneProgressView2.setProgress(0.5f)
    }

    private fun setupControls() {
        // Setup SeekBar to control progress
        progressSeekBar.max = 100
        progressSeekBar.progress = 30

        progressSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val progressFloat = progress / 100f
                milestoneProgressView1.setProgress(progressFloat)
                milestoneProgressView2.setProgress(progressFloat)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Setup animate button
        animateButton.setOnClickListener {
            milestoneProgressView1.animateProgress(1.0f, 2000L)
            milestoneProgressView2.animateProgress(1.0f, 2000L)
            progressSeekBar.progress = 100
        }

        // Setup advanced example button
        advancedExampleButton.setOnClickListener {
            val intent = Intent(this, AdvancedExampleActivity::class.java)
            startActivity(intent)
        }
    }
}