package com.victor.lib.common.view.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes
import com.victor.lib.common.R
import com.victor.lib.common.util.Loger

class ExpandableTextView2 : AppCompatTextView {
    private val TAG = "ExpandableTextView"

    private var onExpandListeners: ArrayList<OnExpandListener>? = null
    private var expandInterpolator: TimeInterpolator? = null
    private var collapseInterpolator: TimeInterpolator? = null

    var canExpend = false
    private var mMaxLines = 0
    private var animationDuration: Long = 0
    private var animating = false
    private var expanded = false
    private var collapsedHeight = 0
    private val defaultAnimDuration = 500

    // 新增属性
    private var expandText = "展开"
    private var collapseText = "收起"
    private var actionTextColor = Color.BLUE // 默认蓝色
    private var originalText: CharSequence = "" // 保存原始文本

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(context, attrs, defStyleAttr)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        context.withStyledAttributes(
            attrs, R.styleable.ExpandableTextView, defStyleAttr, 0
        ) {
            animationDuration = getInt(
                R.styleable.ExpandableTextView_animation_duration, defaultAnimDuration
            ).toLong()

            // 读取新增属性
            expandText = getString(R.styleable.ExpandableTextView_expandText) ?: "展开"
            collapseText = getString(R.styleable.ExpandableTextView_collapseText) ?: "收起"
            actionTextColor = getColor(R.styleable.ExpandableTextView_stateTextColor, Color.BLUE)
        }

        mMaxLines = maxLines
        onExpandListeners = ArrayList()
        expandInterpolator = AccelerateDecelerateInterpolator()
        collapseInterpolator = AccelerateDecelerateInterpolator()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        originalText = text ?: ""
        if (text.isNullOrEmpty()) {
            super.setText(text, type)
            return
        }

        // 先设置原始文本计算行数
        super.setText(text, type)

        post {
            canExpend = calLines(text.toString())
            if (canExpend) {
                updateTextWithAction()
            }
        }
    }

    private fun updateTextWithAction() {
        if (!canExpend) return

        if (expanded) {
            // 展开状态 - 在文本末尾添加收起按钮
            val builder = SpannableStringBuilder(originalText)
            builder.append(" ") // 添加一个空格
            val start = builder.length
            builder.append(collapseText)
            builder.setSpan(
                ForegroundColorSpan(actionTextColor),
                start,
                builder.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            super.setText(builder, BufferType.SPANNABLE)
        } else {
            // 收起状态 - 在最后一行末尾添加展开按钮
            val layout = layout ?: return

            if (lineCount > mMaxLines) {
                val lastLineStart = layout.getLineStart(mMaxLines - 1)
                val lastLineEnd = layout.getLineEnd(mMaxLines - 1)
                var lastLineText = originalText.substring(lastLineStart, lastLineEnd)

                // 计算可用空间
                val paint = paint
                val availableWidth = width - paddingLeft - paddingRight
                val lineWidth = paint.measureText(lastLineText)
                val actionWidth = paint.measureText(" $expandText") // 添加空格

                // 如果当前行放不下，就截断文本并添加...
                if (lineWidth + actionWidth > availableWidth) {
                    // 找到可以截断的位置
                    var end = lastLineText.length
                    while (end > 0) {
                        val subText = lastLineText.substring(0, end) + "... $expandText"
                        if (paint.measureText(subText) <= availableWidth) {
                            lastLineText = subText
                            break
                        }
                        end--
                    }
                } else {
                    lastLineText += " $expandText"
                }

                // 构建最终文本
                val builder = SpannableStringBuilder(originalText.substring(0, lastLineStart))
                builder.append(lastLineText)

                // 设置动作文本颜色
                val start = builder.length - expandText.length
                val end = builder.length
                builder.setSpan(
                    ForegroundColorSpan(actionTextColor),
                    start,
                    end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                super.setText(builder, BufferType.SPANNABLE)
            }
        }
    }

    private fun calLines(text: String): Boolean {
        if (width == 0) return false
        val availableWidth = width - paddingLeft - paddingRight
        if (availableWidth <= 0) return false

        val paint = paint
        val lineCount = Layout.getDesiredWidth(text, paint) / availableWidth
        return lineCount > mMaxLines
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var hMeasureSpec = heightMeasureSpec
        if (mMaxLines == 0 && !this.expanded && !this.animating) {
            hMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, hMeasureSpec)
    }

    fun toggle(): Boolean {
        return if (expanded) collapse() else expand()
    }

    fun expand(): Boolean {
        if (!expanded && !animating && mMaxLines >= 0 && canExpend) {
            notifyOnExpand()
            measure(
                MeasureSpec.makeMeasureSpec(this.measuredWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            collapsedHeight = this.measuredHeight
            animating = true
            maxLines = Int.MAX_VALUE

            measure(
                MeasureSpec.makeMeasureSpec(this.measuredWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            val expandedHeight = this.measuredHeight

            val valueAnimator = ValueAnimator.ofInt(collapsedHeight, expandedHeight)
            valueAnimator.addUpdateListener { animation ->
                this@ExpandableTextView2.height = animation.animatedValue as Int
            }

            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    this@ExpandableTextView2.maxHeight = Int.MAX_VALUE
                    this@ExpandableTextView2.minHeight = 0
                    var layoutParams = this@ExpandableTextView2.layoutParams
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    this@ExpandableTextView2.layoutParams = layoutParams
                    expanded = true
                    animating = false
                    updateTextWithAction() // 更新文本显示
                }
            })

            valueAnimator.interpolator = expandInterpolator
            valueAnimator.setDuration(animationDuration).start()
            return true
        }
        return false
    }

    fun collapse(): Boolean {
        if (expanded && !animating && mMaxLines >= 0 && canExpend) {
            notifyOnCollapse()
            val expandedHeight = this.measuredHeight
            animating = true

            val valueAnimator = ValueAnimator.ofInt(expandedHeight, collapsedHeight)
            valueAnimator.addUpdateListener { animation ->
                this@ExpandableTextView2.height = animation.animatedValue as Int
            }

            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    expanded = false
                    animating = false
                    maxLines = mMaxLines
                    var layoutParams = this@ExpandableTextView2.layoutParams
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    this@ExpandableTextView2.layoutParams = layoutParams
                    updateTextWithAction() // 更新文本显示
                }
            })

            valueAnimator.interpolator = collapseInterpolator
            valueAnimator.setDuration(animationDuration).start()
            return true
        }
        return false
    }

    fun setAnimationDuration(animationDuration: Long) {
        this.animationDuration = animationDuration
    }

    fun addOnExpandListener(onExpandListener: OnExpandListener) {
        onExpandListeners?.add(onExpandListener)
    }

    fun removeOnExpandListener(onExpandListener: OnExpandListener) {
        onExpandListeners?.remove(onExpandListener)
    }

    fun setInterpolator(interpolator: TimeInterpolator?) {
        expandInterpolator = interpolator
        collapseInterpolator = interpolator
    }

    fun setExpandInterpolator(expandInterpolator: TimeInterpolator?) {
        this.expandInterpolator = expandInterpolator
    }

    fun getExpandInterpolator(): TimeInterpolator? {
        return expandInterpolator
    }

    fun setCollapseInterpolator(collapseInterpolator: TimeInterpolator?) {
        this.collapseInterpolator = collapseInterpolator
    }

    fun getCollapseInterpolator(): TimeInterpolator? {
        return collapseInterpolator
    }

    fun isExpanded(): Boolean {
        return expanded
    }

    // 新增方法：设置展开/收起按钮文本
    fun setActionText(expandText: String, collapseText: String) {
        this.expandText = expandText
        this.collapseText = collapseText
        if (canExpend) {
            updateTextWithAction()
        }
    }

    // 新增方法：设置动作文本颜色
    fun setActionTextColor(color: Int) {
        this.actionTextColor = color
        if (canExpend) {
            updateTextWithAction()
        }
    }

    private fun notifyOnCollapse() {
        onExpandListeners?.forEach { it.onCollapse(this) }
    }

    private fun notifyOnExpand() {
        onExpandListeners?.forEach { it.onExpand(this) }
    }

    interface OnExpandListener {
        fun onExpand(view: ExpandableTextView2)
        fun onCollapse(view: ExpandableTextView2)
    }

    class SimpleOnExpandListener : OnExpandListener {
        override fun onExpand(view: ExpandableTextView2) {}
        override fun onCollapse(view: ExpandableTextView2) {}
    }
}