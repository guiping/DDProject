package com.kiudysng.ddproject.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.kiudysng.ddproject.R
import com.kiudysng.ddproject.utils.AiUtils.pxToSp
import com.kiudysng.ddproject.utils.AiUtils.spToPx
import java.util.Arrays
import java.util.Collections

class FlowTagLayout : ViewGroup {
    private var context: Context
    private var childrenBounds: Array<Rect?>?=null
    var tagList: MutableList<String>? = null
    private var onTagClickListener: OnTagClickListener? = null
    private val defaultColor = Color.parseColor("#666666")
    private val defaultTextSize = spToPx(16f)

    /****************** 自定义的Attribute  */
    private var leftMargin = 0
    private var rightMargin = 0
    private var topMargin = 0
    private var bottomMargin = 0
    private var leftPadding = 0
    private var rightPadding = 0
    private var topPadding = 0
    private var bottomPadding = 0
    private var background = 0
    private var textColor = 0
    private var textSize = 0f

    constructor(context: Context) : super(context) {
        this.context = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
    ) {
        this.context = context
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowTagLayout)
        leftMargin = typedArray.getDimension(R.styleable.FlowTagLayout_item_leftMargin, 0f).toInt()
        rightMargin =
            typedArray.getDimension(R.styleable.FlowTagLayout_item_rightMargin, 0f).toInt()
        topMargin = typedArray.getDimension(R.styleable.FlowTagLayout_item_topMargin, 0f).toInt()
        bottomMargin =
            typedArray.getDimension(R.styleable.FlowTagLayout_item_bottomMargin, 0f).toInt()
        leftPadding =
            typedArray.getDimension(R.styleable.FlowTagLayout_item_leftPadding, 0f).toInt()
        rightPadding =
            typedArray.getDimension(R.styleable.FlowTagLayout_item_rightPadding, 0f).toInt()
        topPadding = typedArray.getDimension(R.styleable.FlowTagLayout_item_topPadding, 0f).toInt()
        bottomPadding =
            typedArray.getDimension(R.styleable.FlowTagLayout_item_bottomPadding, 0f).toInt()
        background = typedArray.getResourceId(R.styleable.FlowTagLayout_item_background, -1)
        textColor = typedArray.getColor(R.styleable.FlowTagLayout_item_textColor, defaultColor)
        textSize = typedArray.getDimension(R.styleable.FlowTagLayout_item_textSize, defaultTextSize)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 已用宽度
        var widthUsed = paddingLeft
        // 已用高度
        var heightUsed = paddingTop
        // 当前行高度
        var lineHeight = 0
        // 当前行宽度
        val lineWidth = 0
        // 本身的宽度
        var parentWidth = 0
        // 本身的高度
        var parentHeight = 0
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        val specHeightMode = MeasureSpec.getMode(heightMeasureSpec)
        val childCount = childCount
        if (childrenBounds == null) {
            childrenBounds = arrayOfNulls(childCount)
        } else if (childrenBounds!!.size < childCount) {
            childrenBounds = Arrays.copyOf(childrenBounds, childCount)
        }

        // 遍历测量子view
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            // 测量子view
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
            val lp = child.layoutParams as MarginLayoutParams
            // 判断是否换行（1.不是非限制  2.已用宽度+当前添加的child宽度+左右的padding 是否大于 父view自身测量的宽度）
            val paddingRight = paddingRight

            widthUsed += leftMargin
            if (specWidthMode != MeasureSpec.UNSPECIFIED &&
                widthUsed + child.measuredWidth + rightMargin + getPaddingRight() > specWidth
            ) {
                parentWidth = specWidth
                widthUsed = paddingLeft + leftMargin
                // 已用高度 = 之前已用高度 + 当前行的高度
                heightUsed = heightUsed + lineHeight
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
            }
            var childBounds = childrenBounds!![i]
            if (childBounds == null) {
                childrenBounds!![i] = Rect()
                childBounds = childrenBounds!![i]
            }
            lineHeight = child.measuredHeight + topMargin + bottomMargin

            // 保存child的位置
            childBounds!![widthUsed, heightUsed + topMargin, widthUsed + child.measuredWidth] =
                heightUsed + lineHeight - bottomMargin
            // 当前行已用的宽度
            widthUsed = widthUsed + child.measuredWidth + rightMargin
        }
        // 因为换行parentWidth会被赋值，所以需要对比是否换行过赋值后的parentWidth
        parentWidth = Math.max(parentWidth, widthUsed + paddingRight)
        // 父view的高度 = 已用高度 + 当前行高度 + padding
        parentHeight = heightUsed + lineHeight + paddingBottom
        setMeasuredDimension(
            resolveSizeAndState(parentWidth, widthMeasureSpec, 0),
            resolveSizeAndState(parentHeight, heightMeasureSpec, 0)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val childRect = childrenBounds!![i]
            view.layout(childRect!!.left, childRect.top, childRect.right, childRect.bottom)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    /**
     * 必须重写这个方法，不然measure的时候child.getLayoutParams()的margin是为0的
     *
     * @param attrs
     * @return
     */
    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(getContext(), attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
        )
    }

    /**
     * 添加tags列表
     *
     * @param list
     */
    fun addTags(list: List<String>?) {
        if (tagList == null) {
            tagList = Collections.synchronizedList(ArrayList())
        } else {
            tagList!!.clear()
        }
        tagList!!.addAll(list!!)
        loadTagView()
    }

    /**
     * 初始化tags
     */
    private fun loadTagView() {
        removeAllViews()
        for (i in tagList!!.indices) {
            setTagContent(i)
        }
    }

    /**
     * 添加到尾部
     *
     * @param label
     */
    fun addTag(label: String) {
        tagList!!.add(label)
        setTagContent(tagList!!.size - 1)
    }

    /**
     * 添加tag到index位置
     *
     * @param index
     * @param label
     */
    fun addTagOfIndex(index: Int, label: String) {
        tagList!!.add(index, label)
        setTagOfIndex(index)
    }

    /**
     * 初始化item
     *
     * @param i
     */
    @SuppressLint("NewApi")
    private fun setTagContent(i: Int) {
        LayoutInflater.from(context).inflate(R.layout.item_tag, this)
        // 这里用是getChildAt(index)获取子view，不能直接拿上面inflate的view
        val textView = getChildAt(i).findViewById<TextView>(R.id.item_text)
        val deleteTag = getChildAt(i).findViewById<ImageView>(R.id.iv_tag_delete)
        val lp = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        lp.setMargins(leftPadding, topPadding, rightPadding, bottomPadding)
        textView.layoutParams = lp
        textView.text = tagList!![i]
        textView.setTextColor(textColor)
        textView.textSize = pxToSp(textSize)
        if (background == -1) {
            getChildAt(i).background = null
        } else {
            getChildAt(i).background = resources.getDrawable(background)
        }
        getChildAt(i).setOnClickListener {
            if (onTagClickListener != null) {
                onTagClickListener!!.tagClick(i)
            }
        }
        deleteTag.setOnClickListener { removeTagOfIndex(i) }
    }

    /**
     * 添加到index位置，使用的是addView(view ,index)，最后需要把点击事件重置
     *
     * @param i
     */
    @SuppressLint("NewApi")
    private fun setTagOfIndex(i: Int) {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tag, null, false)
        addView(view, i)
        // 这里用是getChildAt(index)获取子view，不能直接拿上面inflate的view
        val textView = getChildAt(i).findViewById<TextView>(R.id.item_text)
        val lp = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        lp.setMargins(leftPadding, topPadding, rightPadding, bottomPadding)
        textView.layoutParams = lp
        textView.text = tagList!![i]
        textView.setTextColor(textColor)
        textView.textSize = pxToSp(textSize)
        if (background == -1) {
            getChildAt(i).background = null
        } else {
            getChildAt(i).background = resources.getDrawable(background)
        }
        for (j in 0 until childCount) {
            // 去掉点击事件
            getChildAt(j).setOnClickListener(null)
            // 重新绑定点击监听
            getChildAt(j).setOnClickListener {
                if (onTagClickListener != null) {
                    onTagClickListener!!.tagClick(j)
                }
            }
        }
    }

    /**
     * 移除最后一个tag
     */
    fun removeTag() {
        getChildAt(tagList!!.size - 1).setOnClickListener(null)
        removeViewAt(tagList!!.size - 1)
        tagList!!.removeAt(tagList!!.size - 1)
    }

    /**
     * 移除index位置的tag
     *
     * @param index
     */
    fun removeTagOfIndex(index: Int) {
        getChildAt(index).setOnClickListener(null)
        removeViewAt(index)
        tagList!!.removeAt(index)
        for (j in index until childCount) {
            // 去掉点击事件
            getChildAt(j).setOnClickListener(null)
            // 重新绑定点击监听
            getChildAt(j).setOnClickListener {
                if (onTagClickListener != null) {
                    onTagClickListener!!.tagClick(j)
                }
            }
        }
    }

    fun setTagClickListener(onTagClickListener: OnTagClickListener?) {
        this.onTagClickListener = onTagClickListener
    }

    interface OnTagClickListener {
        fun tagClick(position: Int)
    }
}