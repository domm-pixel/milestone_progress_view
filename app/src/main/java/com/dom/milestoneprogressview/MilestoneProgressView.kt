package com.dom.milestoneprogressview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import kotlin.math.abs
import kotlin.math.min

/**
 * Milestone 기반 진행도 커스텀 뷰 (라이브러리 최소 구현)
 * - 앱 리소스 의존 제거
 * - XML/코드로 커스터마이즈 가능(attrs + setters)
 * - DRY: 공통 로직 함수화, 상태 분기 단순화
 */
class MilestoneProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // == Public Model ==
    data class Milestone(
        @FloatRange(from = 0.0, to = 1.0) val progress: Float,
        val title: String,
        val isCompleted: Boolean = false
    )

    // == State ==
    private val milestones = mutableListOf<Milestone>()
    private var currentProgress = 0f

    // == Paints ==
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val progPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val inactivePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val activePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val donePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { textAlign = Paint.Align.CENTER }
    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }

    // == Defaults ==
    private var colorBackground = Color.parseColor("#E5E7EB")
    private var colorProgress   = Color.parseColor("#FF7A00")
    private var colorInactive   = Color.parseColor("#E5E7EB")
    private var colorActive     = Color.parseColor("#FF7A00")
    private var colorCompleted  = Color.parseColor("#FF7A00")
    private var colorText       = Color.parseColor("#4B5563")
    private var colorShadow     = Color.parseColor("#33000000")

    private var barHeightPx      = 2f * density
    private var bigRadiusPx      = 12f * density
    private var smallRadiusPx    = 4f * density
    private var labelTextSizePx  = 14f * scaledDensity
    private var spacingPx        = 6f * density

    init {
        // XML 속성 적용
        context.obtainStyledAttributes(attrs, R.styleable.MilestoneProgressView, defStyleAttr, 0).apply {
            barHeightPx     = getDimension(R.styleable.MilestoneProgressView_mpv_barHeight, barHeightPx)
            bigRadiusPx     = getDimension(R.styleable.MilestoneProgressView_mpv_milestoneRadius, bigRadiusPx)
            smallRadiusPx   = getDimension(R.styleable.MilestoneProgressView_mpv_milestoneSmallRadius, smallRadiusPx)
            labelTextSizePx = getDimension(R.styleable.MilestoneProgressView_mpv_labelTextSize, labelTextSizePx)
            spacingPx       = getDimension(R.styleable.MilestoneProgressView_mpv_spacing, spacingPx)

            colorBackground = getColor(R.styleable.MilestoneProgressView_mpv_colorBackground, colorBackground)
            colorProgress   = getColor(R.styleable.MilestoneProgressView_mpv_colorProgress, colorProgress)
            colorInactive   = getColor(R.styleable.MilestoneProgressView_mpv_colorInactive, colorInactive)
            colorActive     = getColor(R.styleable.MilestoneProgressView_mpv_colorActive, colorActive)
            colorCompleted  = getColor(R.styleable.MilestoneProgressView_mpv_colorCompleted, colorCompleted)
            colorText       = getColor(R.styleable.MilestoneProgressView_mpv_colorText, colorText)
            colorShadow     = getColor(R.styleable.MilestoneProgressView_mpv_colorShadow, colorShadow)

            currentProgress = getFloat(R.styleable.MilestoneProgressView_mpv_progress, currentProgress)
        }.recycle()

        applyPaints()
    }

    // region Public API
    /** 마일스톤 목록 주입 (progress는 0~1 범위) */
    fun setMilestones(newMilestones: List<Milestone>) {
        milestones.clear()
        milestones.addAll(newMilestones.sortedBy { it.progress })
        invalidate()
    }

    /** 진행도 설정(0~1) */
    fun setProgress(@FloatRange(from = 0.0, to = 1.0) progress: Float) {
        currentProgress = progress.coerceIn(0f, 1f)
        for (i in milestones.indices) {
            milestones[i] = milestones[i].copy(isCompleted = currentProgress > milestones[i].progress)
        }
        invalidate()
    }

    /** 진행 애니메이션 (간단 버전) */
    fun setProgressAnimated(@FloatRange(from = 0.0, to = 1.0) target: Float, duration: Long = 600) {
        val start = currentProgress
        val end = target.coerceIn(0f, 1f)
        animate().setDuration(duration).setUpdateListener { a ->
            val t = a.animatedFraction
            setProgress(start + (end - start) * t)
        }.start()
    }
    // endregion

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (milestones.isEmpty()) return

        val w = width.toFloat()
        val h = height.toFloat()
        val barY = h * 0.3f

        // 텍스트 위치 계산
        textPaint.textSize = labelTextSizePx
        val fm = textPaint.fontMetrics
        val labelBaseline = barY + bigRadiusPx + spacingPx * 2 - fm.ascent / 2f

        val leftGuard = paddingLeft.toFloat()
        val rightGuard = w - paddingRight
        val usableLeft = leftGuard + bigRadiusPx
        val usableRight = rightGuard - bigRadiusPx
        val usableW = (usableRight - usableLeft).coerceAtLeast(0f)

        val centers = milestones.map { m ->
            val titleW = textPaint.measureText(m.title)
            val minX = leftGuard + titleW / 2f
            val maxX = rightGuard - titleW / 2f
            val baseX = usableLeft + usableW * m.progress
            baseX.coerceIn(minX, maxX)
        }

        // 라벨
        milestones.forEachIndexed { i, m ->
            textPaint.color = if (m.isCompleted || currentProgress >= m.progress - 0.01f) colorActive else colorText
            canvas.drawText(m.title, centers[i], labelBaseline, textPaint)
        }
        textPaint.color = colorText

        // 바(첫/끝 라벨 중심에 정렬)
        val barLeft = centers.first()
        val barRight = centers.last()
        val top = barY - barHeightPx / 2f
        val bottom = barY + barHeightPx / 2f

        // 배경 바
        bgPaint.color = colorBackground
        canvas.drawRoundRect(barLeft, top, barRight, bottom, barHeightPx / 2f, barHeightPx / 2f, bgPaint)

        // 진행 바
        progPaint.color = colorProgress
        val filledRight = barLeft + (barRight - barLeft) * currentProgress
        if (filledRight > barLeft) {
            canvas.drawRoundRect(barLeft, top, min(filledRight, barRight), bottom, barHeightPx / 2f, barHeightPx / 2f, progPaint)
        }

        // 마일스톤
        milestones.forEachIndexed { i, m ->
            val x = centers[i]; val y = barY
            val isActive = abs(currentProgress - m.progress) < 0.01f && m.progress < 1.0f
            val isDone = m.isCompleted || (currentProgress >= m.progress && m.progress == 1.0f)

            val (r, p) = when {
                isDone -> bigRadiusPx to donePaint
                isActive -> bigRadiusPx to activePaint
                currentProgress > m.progress -> bigRadiusPx to donePaint
                else -> smallRadiusPx to inactivePaint
            }

            // 그림자 (큰 원만)
            if (r == bigRadiusPx) {
                canvas.drawCircle(x, y + 2f, r * 0.9f, shadowPaint)
            }

            if (isActive) {
                canvas.drawCircle(x, y, r, activePaint)
                // 내부 포인트
                canvas.drawCircle(x, y, 4f * density, Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.WHITE; style = Paint.Style.FILL
                })
            } else {
                canvas.drawCircle(x, y, r, p)
            }

            if (isDone || currentProgress > m.progress) drawCheck(canvas, x, y, r * 0.8f)
        }
    }

    // == Helpers ==
    private fun applyPaints() {
        bgPaint.color = colorBackground
        progPaint.color = colorProgress
        inactivePaint.color = colorInactive
        activePaint.color = colorActive
        donePaint.color = colorCompleted
        textPaint.color = colorText

        shadowPaint.color = colorShadow
        shadowPaint.setShadowLayer(4f, 0f, 2f, colorShadow)
        // 소프트 레이어로 간단한 그림자만
        setLayerType(LAYER_TYPE_SOFTWARE, shadowPaint)
    }

    private fun drawCheck(canvas: Canvas, cx: Float, cy: Float, size: Float) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.STROKE
            strokeWidth = size * 0.25f
            strokeCap = Paint.Cap.ROUND
        }
        val path = Path().apply {
            moveTo(cx - size * 0.3f, cy)
            lineTo(cx - size * 0.1f, cy + size * 0.2f)
            lineTo(cx + size * 0.3f, cy - size * 0.2f)
        }
        canvas.drawPath(path, paint)
    }

    private val density get() = resources.displayMetrics.density
    private val scaledDensity get() = resources.displayMetrics.scaledDensity
}