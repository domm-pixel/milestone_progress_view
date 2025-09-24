package com.dommpixel.milestoneprogressview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.min

/**
 * A custom view that displays progress with milestones and labels
 */
class MilestoneProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    data class Milestone(
        val position: Float, // 0.0 to 1.0
        val label: String,
        val isCompleted: Boolean = false
    )

    // Configuration properties
    var milestones: List<Milestone> = emptyList()
        set(value) {
            field = value
            invalidate()
        }

    var progress: Float = 0f
        set(value) {
            field = value.coerceIn(0f, 1f)
            invalidate()
        }

    var progressBarHeight: Float = 8f
        set(value) {
            field = value
            invalidate()
        }

    var milestoneRadius: Float = 12f
        set(value) {
            field = value
            invalidate()
        }

    var progressColor: Int = Color.parseColor("#2196F3")
        set(value) {
            field = value
            invalidate()
        }

    var backgroundColor: Int = Color.parseColor("#E0E0E0")
        set(value) {
            field = value
            invalidate()
        }

    var milestoneColor: Int = Color.parseColor("#4CAF50")
        set(value) {
            field = value
            invalidate()
        }

    var milestoneIncompleteColor: Int = Color.parseColor("#9E9E9E")
        set(value) {
            field = value
            invalidate()
        }

    var textColor: Int = Color.parseColor("#333333")
        set(value) {
            field = value
            invalidate()
        }

    var textSize: Float = 14f * context.resources.displayMetrics.scaledDensity
        set(value) {
            field = value
            textPaint.textSize = value
            invalidate()
        }

    // Paint objects
    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val milestonePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = this@MilestoneProgressView.textSize
    }

    private val tempRect = RectF()

    init {
        // Initialize with default attributes if provided
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MilestoneProgressView,
            0, 0
        ).apply {
            try {
                progressBarHeight = getDimension(R.styleable.MilestoneProgressView_progressBarHeight, progressBarHeight)
                milestoneRadius = getDimension(R.styleable.MilestoneProgressView_milestoneRadius, milestoneRadius)
                progressColor = getColor(R.styleable.MilestoneProgressView_progressColor, progressColor)
                backgroundColor = getColor(R.styleable.MilestoneProgressView_backgroundColor, backgroundColor)
                milestoneColor = getColor(R.styleable.MilestoneProgressView_milestoneColor, milestoneColor)
                milestoneIncompleteColor = getColor(R.styleable.MilestoneProgressView_milestoneIncompleteColor, milestoneIncompleteColor)
                textColor = getColor(R.styleable.MilestoneProgressView_textColor, textColor)
                textSize = getDimension(R.styleable.MilestoneProgressView_textSize, textSize)
                progress = getFloat(R.styleable.MilestoneProgressView_progress, progress)
            } finally {
                recycle()
            }
        }

        // Set initial paint colors
        updatePaintColors()
    }

    private fun updatePaintColors() {
        progressPaint.color = progressColor
        backgroundPaint.color = backgroundColor
        textPaint.color = textColor
        textPaint.textSize = textSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 300
        val desiredHeight = (milestoneRadius * 2 + textSize + 30).toInt()

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> min(desiredWidth, widthSize)
            else -> desiredWidth
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min(desiredHeight, heightSize)
            else -> desiredHeight
        }

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        updatePaintColors()

        val centerY = height / 2f
        val startX = paddingLeft + milestoneRadius
        val endX = width - paddingRight - milestoneRadius
        val progressWidth = endX - startX

        // Draw background progress bar
        tempRect.set(
            startX,
            centerY - progressBarHeight / 2,
            endX,
            centerY + progressBarHeight / 2
        )
        canvas.drawRoundRect(tempRect, progressBarHeight / 2, progressBarHeight / 2, backgroundPaint)

        // Draw progress bar
        val progressEndX = startX + (progressWidth * progress)
        tempRect.set(
            startX,
            centerY - progressBarHeight / 2,
            progressEndX,
            centerY + progressBarHeight / 2
        )
        canvas.drawRoundRect(tempRect, progressBarHeight / 2, progressBarHeight / 2, progressPaint)

        // Draw milestones
        for (milestone in milestones) {
            val milestoneX = startX + (progressWidth * milestone.position)
            
            // Determine milestone color based on completion status
            val color = if (milestone.isCompleted || milestone.position <= progress) {
                milestoneColor
            } else {
                milestoneIncompleteColor
            }
            milestonePaint.color = color

            // Draw milestone circle
            canvas.drawCircle(milestoneX, centerY, milestoneRadius, milestonePaint)

            // Draw milestone label
            val textY = centerY + milestoneRadius + textSize + 8f
            canvas.drawText(milestone.label, milestoneX, textY, textPaint)
        }
    }

    /**
     * Set milestones programmatically
     */
    fun setMilestones(milestones: List<Milestone>) {
        this.milestones = milestones
    }

    /**
     * Update progress (0.0 to 1.0)
     */
    fun setProgress(progress: Float) {
        this.progress = progress
    }

    /**
     * Animate progress to target value
     */
    fun animateProgress(targetProgress: Float, duration: Long = 1000L) {
        val startProgress = this.progress
        val progressDiff = targetProgress - startProgress
        
        animate().setDuration(duration).setUpdateListener { animation ->
            val animatedValue = animation.animatedFraction
            progress = startProgress + (progressDiff * animatedValue)
        }.start()
    }
}