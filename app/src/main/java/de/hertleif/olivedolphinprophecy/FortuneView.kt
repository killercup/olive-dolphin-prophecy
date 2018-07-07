package de.hertleif.olivedolphinprophecy

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.roundToInt


class FortunateView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val TAG = "OlDolPro.FortuneView"
    var fortune: Outcome = Outcome.Yes

    private var texts: ArrayList<FortuneText> = arrayListOf()
    private var previousWidth = 0
    private var previousHeight = 0

    private val padding = dip2px(24)
    private val lineSpacing = dip2px(8)

    fun setStyle(style: Outcome): FortunateView {
        this.fortune = style
        return this
    }

    override fun onMeasure(w: Int, h: Int) {
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) {
            //Log.d(TAG, "no canvas :O")
            return
        }

        calcTexts(canvas.width, canvas.height)

        var textsHeight = 0 //+ top + paddingTop + topPaddingOffset
        for (textView in texts) {
            textView.draw(canvas, padding.toFloat(), textsHeight.toFloat())
            textsHeight += textView.height() + lineSpacing
        }
    }

    private val gestureDetector: GestureDetector

    init {
        this.gestureDetector = GestureDetector(getContext(), GestureListener())
    }

    private var onTouchCallback: (() -> Unit)? = null

    fun setOnTouchCallback(fn: (() -> Unit)) {
        this.onTouchCallback = fn
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var result = gestureDetector.onTouchEvent(event)
        if (!result && event != null) {
            if (event.action === MotionEvent.ACTION_UP) {
                onTouchCallback?.invoke()
                result = true
            }
        }
        return result
    }

    internal inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }
    }

    private fun calcTexts(width: Int, height: Int) {
        if (previousHeight == height && previousWidth == width && texts.size > 0) {
            //Log.d(TAG, "no text changes, skipping")
            return
        }

        val maxHeight = height - padding
        val maxWidth = width - (2 * padding)

        var textsHeight = 0
        var textCount = 0

        for (text in fortune.texts().shuffled()) {
            val textView: FortuneText
            try {
                textView = newText(text, fortune, maxWidth, maxHeight - textsHeight)
            } catch (e: NoMoreSpaceException) {
                //Log.d(TAG, "no more space :(", e)
                break
            }
            textCount += 1
            textsHeight += textView.height() + lineSpacing
            texts.add(textView)
        }

        //Log.d(TAG, "done, added $textCount texts")
    }

    private fun newText(content: String, style: Outcome, maxWidth: Int, maxHeight: Int): FortuneText {
        return FortuneText(context)
                .setContent(content)
                .applyStyle(style)
                .autoSize(maxWidth, maxHeight)
    }

    private fun dip2px(dip: Int): Int {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip.toFloat(), resources.displayMetrics))
    }

    internal inner class FortuneText(private val context: Context) {
        private val TAG = "OlDolPro.Text"
        private val textPaint: TextPaint
        private val textBounds: Rect = Rect(0, 0, 0, 0)

        private var content: String = ""
        private var centerOffset = 0f
        private var fontSize = 0f
        private var height = 0

        private val minFontSize = 14f
        private val maxFontSize = 400f

        init {
            this.textPaint = TextPaint(TextPaint.ANTI_ALIAS_FLAG.or(TextPaint.LINEAR_TEXT_FLAG))
        }

        fun applyStyle(style: Outcome): FortuneText {
            if (style == Outcome.Yes) {
                textPaint.typeface = ResourcesCompat.getFont(context, R.font.lobster)
                textPaint.color = ContextCompat.getColor(context, R.color.colorYes)
            } else {
                textPaint.typeface = ResourcesCompat.getFont(context, R.font.bungee)
                textPaint.color = ContextCompat.getColor(context, R.color.colorNo)
            }
            return this
        }

        fun setContent(content: String): FortuneText {
            this.content = content
            return this
        }

        fun autoSize(maxWidth: Int, maxHeight: Int): FortuneText {
            fontSize = Math.min(maxFontSize, maxHeight.toFloat())
            while (fontSize >= minFontSize) {
                //Log.d(TAG, "autoSize with $fontSize")
                textPaint.textSize = fontSize

                //val w = textPaint.measureText(content)
                //val h = fontSize

                textPaint.getTextBounds(content, 0, content.length, textBounds)
                val w = textBounds.width()
                val h = Math.max(textBounds.height(), fontSize.roundToInt())

                //Log.d(TAG, "autoSize with font size $fontSize gives us a $w x $h box (max is $maxWidth x $maxHeight)")
                if (w <= maxWidth && height() <= maxHeight) {
                    centerOffset = (maxWidth - w) / 2f
                    height = h + 0

                    //Log.d(TAG, "$fontSize looks good, using that")
                    return this
                } else {
                    fontSize -= 1
                }
            }
            //Log.d(TAG, "autoSize gives up")
            throw NoMoreSpaceException()
        }

        fun height(): Int {
            return this.height
        }

        fun draw(canvas: Canvas, left: Float, top: Float) {
            //Log.d(TAG, "drawing '$content' at ${left + centerOffset},$top with a height of ${height()}")
            canvas.drawText(content, left + centerOffset, top + height(), textPaint)
        }
    }

    internal inner class NoMoreSpaceException : Exception()
}

