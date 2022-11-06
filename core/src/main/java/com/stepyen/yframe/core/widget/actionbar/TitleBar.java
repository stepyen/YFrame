package com.stepyen.yframe.core.widget.actionbar;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stepyen.yframe.core.R;
import com.stepyen.yframe.core.util.YFrameUtils;

import java.util.LinkedList;


/**
 * 标题栏
 *
 * @author xuexiang
 * @since 2019/1/14 下午10:08
 */
public class TitleBar extends ViewGroup {
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    public static final int CENTER_CENTER = 0;
    public static final int CENTER_LEFT = 1;
    public static final int CENTER_RIGHT = 2;

    /**
     * 文字默认白色
     */
    private static int DEFAULT_TEXT_COLOR = Color.WHITE;

    private TextView mLeftText;
    private LinearLayout mRightLayout;
    private LinearLayout mCenterLayout;
    private TextView mCenterText;
    private TextView mSubTitleText;
    private View mCustomCenterView;
    private View mDividerView;

    /**
     * 是否是沉浸模式
     */
    private boolean mImmersive;
    /**
     * 屏幕宽
     */
    private int mScreenWidth;

    /**
     * 标题栏的高度
     */
    private int mBarHeight;
    /**
     * 状态栏的高度
     */
    private int mStatusBarHeight;
    /**
     * 点击动作控件的水平方向padding
     */
    private int mActionHorizontalPadding;
    /**
     * 点击动作控件的垂直方向padding
     */
    private int mActionVerticalPadding;
    /**
     * 左右侧文字的padding
     */
    private int mSideTextPadding;
    private int mCenterGravity;

    private int mSideTextSize;
    private int mTitleTextSize;
    private int mSubTitleTextSize;
    private int mActionTextSize;

    private int mSideTextColor;
    private int mTitleTextColor;
    private int mSubTitleTextColor;
    private int mActionTextColor;

    private Drawable mLeftImageResource;
    private String mLeftTextString;
    private String mTitleTextString;
    private String mSubTextString;
    private int mDividerColor;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.XFrameTitleBarStyle);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
        init(context);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        if (isInEditMode()) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, 0);
        if (typedArray != null) {
            mBarHeight = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_barHeight, Utils.resolveDimension(context, R.attr.xframe_actionbar_height, Utils.getDimensionPixelSize(getContext(), R.dimen.xframe_default_actionbar_height)));
            mImmersive = typedArray.getBoolean(R.styleable.TitleBar_tb_immersive, false);

            mActionHorizontalPadding = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_action_horizontal_padding, Utils.resolveDimension(context, R.attr.xframe_actionbar_action_horizontal_padding));
            mActionVerticalPadding = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_action_vertical_padding, Utils.resolveDimension(context, R.attr.xframe_actionbar_action_vertical_padding));
            mSideTextPadding = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_sideTextPadding, Utils.resolveDimension(context, R.attr.xframe_actionbar_side_text_padding, Utils.getDimensionPixelSize(getContext(), R.dimen.xframe_default_sidetext_padding)));
            mCenterGravity = typedArray.getInt(R.styleable.TitleBar_tb_centerGravity, CENTER_CENTER);

            mSideTextSize = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_sideTextSize, Utils.resolveDimension(context, R.attr.xframe_actionbar_action_text_size, Utils.getDimensionPixelSize(getContext(), R.dimen.xframe_default_action_text_size)));
            mTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_titleTextSize, Utils.resolveDimension(context, R.attr.xframe_actionbar_title_text_size, Utils.getDimensionPixelSize(getContext(), R.dimen.xframe_default_title_text_size)));
            mSubTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_subTitleTextSize, Utils.resolveDimension(context, R.attr.xframe_actionbar_sub_text_size, Utils.getDimensionPixelSize(getContext(), R.dimen.xframe_default_sub_text_size)));
            mActionTextSize = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_actionTextSize, Utils.resolveDimension(context, R.attr.xframe_actionbar_action_text_size, Utils.getDimensionPixelSize(getContext(), R.dimen.xframe_default_action_text_size)));

            mSideTextColor = typedArray.getColor(R.styleable.TitleBar_tb_sideTextColor, Utils.resolveColor(getContext(), R.attr.xframe_actionbar_text_color, DEFAULT_TEXT_COLOR));
            mTitleTextColor = typedArray.getColor(R.styleable.TitleBar_tb_titleTextColor, Utils.resolveColor(getContext(), R.attr.xframe_actionbar_text_color, DEFAULT_TEXT_COLOR));
            mSubTitleTextColor = typedArray.getColor(R.styleable.TitleBar_tb_subTitleTextColor, Utils.resolveColor(getContext(), R.attr.xframe_actionbar_text_color, DEFAULT_TEXT_COLOR));
            mActionTextColor = typedArray.getColor(R.styleable.TitleBar_tb_actionTextColor, Utils.resolveColor(getContext(), R.attr.xframe_actionbar_text_color, DEFAULT_TEXT_COLOR));

            mLeftImageResource = typedArray.getDrawable(R.styleable.TitleBar_tb_leftImageResource);
            mLeftTextString = typedArray.getString(R.styleable.TitleBar_tb_leftText);
            mTitleTextString = typedArray.getString(R.styleable.TitleBar_tb_titleText);
            mSubTextString = typedArray.getString(R.styleable.TitleBar_tb_subTitleText);
            mDividerColor = typedArray.getColor(R.styleable.TitleBar_tb_dividerColor, Color.TRANSPARENT);

            typedArray.recycle();
        }
    }

    private void init(Context context) {
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        if (mImmersive) {
            mStatusBarHeight = getStatusBarHeight();
        }
        initView(context);
    }

    private void initView(Context context) {

        mLeftText = new TextView(context);
        mCenterLayout = new LinearLayout(context);
        mRightLayout = new LinearLayout(context);
        mDividerView = new View(context);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

        mLeftText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSideTextSize);
        mLeftText.setTextColor(mSideTextColor);
        mLeftText.setText(mLeftTextString);
        if (mLeftImageResource != null) {
            mLeftText.setCompoundDrawablesWithIntrinsicBounds(mLeftImageResource, null, null, null);
        }
        mLeftText.setSingleLine();

        mLeftText.setGravity(Gravity.CENTER_VERTICAL);
        mLeftText.setPadding(mSideTextPadding, 0, mSideTextPadding, 0);

        mCenterText = new TextView(context);
        mSubTitleText = new TextView(context);
        if (!TextUtils.isEmpty(mSubTextString)) {
            mCenterLayout.setOrientation(LinearLayout.VERTICAL);
        }
        mCenterText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
        mCenterText.setTextColor(mTitleTextColor);
        mCenterText.setText(mTitleTextString);
        mCenterText.setSingleLine();
        mCenterText.setEllipsize(TextUtils.TruncateAt.MARQUEE);

        mSubTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSubTitleTextSize);
        mSubTitleText.setTextColor(mSubTitleTextColor);
        mSubTitleText.setText(mSubTextString);
        mSubTitleText.setSingleLine();
        mSubTitleText.setPadding(0, YFrameUtils.dp2px(2), 0, 0);
        mSubTitleText.setEllipsize(TextUtils.TruncateAt.END);

        if (mCenterGravity == CENTER_LEFT) {
            setCenterGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        } else if (mCenterGravity == CENTER_RIGHT) {
            setCenterGravity(Gravity.CENTER_VERTICAL | Gravity.END);
        } else {
            setCenterGravity(Gravity.CENTER);
        }
        mCenterLayout.addView(mCenterText);
        mCenterLayout.addView(mSubTitleText);

        mRightLayout.setPadding(mSideTextPadding, 0, mSideTextPadding, 0);

        mDividerView.setBackgroundColor(mDividerColor);

        addView(mLeftText, layoutParams);
        addView(mCenterLayout);
        addView(mRightLayout, layoutParams);
        addView(mDividerView, new LayoutParams(LayoutParams.MATCH_PARENT, 1));
    }

    public TitleBar setImmersive(boolean immersive) {
        mImmersive = immersive;
        if (mImmersive) {
            mStatusBarHeight = getStatusBarHeight();
        } else {
            mStatusBarHeight = 0;
        }
        return this;
    }

    /**
     * 设置状态栏高度
     *
     * @param height
     * @return
     */
    public TitleBar setHeight(int height) {
        mBarHeight = height;
        setMeasuredDimension(getMeasuredWidth(), mBarHeight);
        return this;
    }

    public TitleBar setLeftImageResource(int resId) {
        mLeftText.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
        return this;
    }

    /**
     * 设置左侧图标
     *
     * @param resId
     * @return
     */
    public TitleBar setBackImageResource(int resId) {
        if (resId != 0) {
            mLeftImageResource = Utils.getDrawable(getContext(), resId);
            mLeftImageResource.setBounds(0, 0, YFrameUtils.dp2px( 12), YFrameUtils.dp2px(22));
            mLeftText.setCompoundDrawables(mLeftImageResource, null, null, null);
        } else {
            mLeftImageResource = null;
            mLeftText.setCompoundDrawables(null, null, null, null);
        }
        return this;
    }

    /**
     * 设置左侧点击事件
     *
     * @param l
     * @return
     */
    public TitleBar setLeftClickListener(OnClickListener l) {
        mLeftText.setOnClickListener(l);
        return this;
    }

    /**
     * 设置左侧文字
     *
     * @param title
     * @return
     */
    public TitleBar setLeftText(CharSequence title) {
        mLeftText.setText(title);
        return this;
    }

    /**
     * 设置左侧文字
     *
     * @param resId
     * @return
     */
    public TitleBar setLeftText(int resId) {
        mLeftText.setText(resId);
        return this;
    }

    /**
     * 设置左侧文字大小
     *
     * @param size
     * @return
     */
    public TitleBar setLeftTextSize(float size) {
        mLeftText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        return this;
    }

    /**
     * 设置左侧文字的最大长度
     *
     * @param maxEms
     * @return
     */
    public TitleBar setLeftTextMaxEms(int maxEms) {
        mLeftText.setMaxEms(maxEms);
        return this;
    }

    /**
     * 设置左侧文字的最大宽度
     *
     * @param maxPixels
     * @return
     */
    public TitleBar setLeftTextMaxWidth(int maxPixels) {
        mLeftText.setMaxWidth(maxPixels);
        return this;
    }

    /**
     * 设置左侧文字长度超出的处理
     *
     * @param where
     * @return
     */
    public TitleBar setLeftTextEllipsize(TextUtils.TruncateAt where) {
        mLeftText.setEllipsize(where);
        return this;
    }

    /**
     * 左侧文字的颜色
     *
     * @param color
     * @return
     */
    public TitleBar setLeftTextColor(int color) {
        mLeftText.setTextColor(color);
        return this;
    }

    /**
     * 设置左侧文字是否可显示
     *
     * @param visible
     * @return
     */
    public TitleBar setLeftVisible(boolean visible) {
        mLeftText.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置标题文字
     *
     * @param title
     * @return
     */
    public TitleBar setTitle(CharSequence title) {
        int index = title.toString().indexOf("\n");
        if (index > 0) {
            setTitle(title.subSequence(0, index), title.subSequence(index + 1, title.length()), LinearLayout.VERTICAL);
        } else {
            index = title.toString().indexOf("\t");
            if (index > 0) {
                setTitle(title.subSequence(0, index), "  " + title.subSequence(index + 1, title.length()), LinearLayout.HORIZONTAL);
            } else {
                mCenterText.setText(title);
                mSubTitleText.setVisibility(View.GONE);
            }
        }
        return this;
    }

    /**
     * 设置标题和副标题的文字
     *
     * @param title       标题
     * @param subTitle    副标题
     * @param orientation 对齐方式
     * @return
     */
    public TitleBar setTitle(CharSequence title, CharSequence subTitle, int orientation) {
        mCenterLayout.setOrientation(orientation);
        mCenterText.setText(title);

        mSubTitleText.setText(subTitle);
        mSubTitleText.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 设置标题和副标题的文字
     *
     * @param subTitle 副标题
     * @return
     */
    public TitleBar setSubTitle(CharSequence subTitle) {
        mSubTitleText.setText(subTitle);
        mSubTitleText.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 设置中间内容的对齐方式
     *
     * @param gravity
     * @return
     */
    public TitleBar setCenterGravity(int gravity) {
        mCenterLayout.setGravity(gravity);
        mCenterText.setGravity(gravity);
        mSubTitleText.setGravity(gravity);
        return this;
    }

    /**
     * 设置中心点击
     *
     * @param l
     * @return
     */
    public TitleBar setCenterClickListener(OnClickListener l) {
        mCenterLayout.setOnClickListener(l);
        return this;
    }

    /**
     * 设置标题文字
     *
     * @param resId
     * @return
     */
    public TitleBar setTitle(int resId) {
        setTitle(getResources().getString(resId));
        return this;
    }

    /**
     * 设置标题文字颜色
     *
     * @param resId
     * @return
     */
    public TitleBar setTitleColor(int resId) {
        mCenterText.setTextColor(resId);
        return this;
    }

    /**
     * 设置标题文字大小
     *
     * @param size
     * @return
     */
    public TitleBar setTitleSize(float size) {
        mCenterText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        return this;
    }

    /**
     * 设置标题背景
     *
     * @param resId
     * @return
     */
    public TitleBar setTitleBackground(int resId) {
        mCenterText.setBackgroundResource(resId);
        return this;
    }

    public TitleBar setSubTitleColor(int resId) {
        mSubTitleText.setTextColor(resId);
        return this;
    }

    public TitleBar setSubTitleSize(float size) {
        mSubTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        return this;
    }

    public TitleBar setCustomTitle(View titleView) {
        if (titleView == null) {
            mCenterText.setVisibility(View.VISIBLE);
            if (mCustomCenterView != null) {
                mCenterLayout.removeView(mCustomCenterView);
            }

        } else {
            if (mCustomCenterView != null) {
                mCenterLayout.removeView(mCustomCenterView);
            }
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            mCustomCenterView = titleView;
            mCenterLayout.addView(titleView, layoutParams);
            mCenterText.setVisibility(View.GONE);
        }
        return this;
    }

    public TitleBar setDivider(Drawable drawable) {
        mDividerView.setBackgroundDrawable(drawable);
        return this;
    }

    public TitleBar setDividerColor(int color) {
        mDividerView.setBackgroundColor(color);
        return this;
    }

    public TitleBar setDividerHeight(int dividerHeight) {
        mDividerView.getLayoutParams().height = dividerHeight;
        return this;
    }

    public TitleBar setActionTextColor(int colorResId) {
        mActionTextColor = colorResId;
        return this;
    }

    /**
     * Function to set a click listener for Title TextView
     *
     * @param listener the onClickListener
     */
    public TitleBar setOnTitleClickListener(OnClickListener listener) {
        mCenterText.setOnClickListener(listener);
        return this;
    }

    /**
     * Adds a list of {@link Action}s.
     *
     * @param actionList the actions to add
     */
    public TitleBar addActions(ActionList actionList) {
        int actions = actionList.size();
        for (int i = 0; i < actions; i++) {
            addAction(actionList.get(i));
        }
        return this;
    }

    /**
     * Adds a new {@link Action}.
     *
     * @param action the action to add
     */
    public View addAction(Action action) {
        final int index = mRightLayout.getChildCount();
        return addAction(action, index);
    }

    /**
     * Adds a new {@link Action} at the specified index.
     *
     * @param action the action to add
     * @param index  the position at which to add the action
     */
    public View addAction(Action action, int index) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        View view = inflateAction(action);
        mRightLayout.addView(view, index, params);
        return view;
    }

    /**
     * Removes all action views from this action bar
     */
    public void removeAllActions() {
        mRightLayout.removeAllViews();
    }

    /**
     * Remove a action from the action bar.
     *
     * @param index position of action to remove
     */
    public void removeActionAt(int index) {
        mRightLayout.removeViewAt(index);
    }

    /**
     * Remove a action from the action bar.
     *
     * @param action The action to remove
     */
    public void removeAction(Action action) {
        int childCount = mRightLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = mRightLayout.getChildAt(i);
            if (view != null) {
                final Object tag = view.getTag();
                if (tag instanceof Action && tag.equals(action)) {
                    mRightLayout.removeView(view);
                }
            }
        }
    }

    /**
     * Returns the number of actions currently registered with the action bar.
     *
     * @return action count
     */
    public int getActionCount() {
        return mRightLayout.getChildCount();
    }

    /**
     * Inflates a {@link View} with the given {@link Action}.
     *
     * @param action the action to inflate
     * @return a view
     */
    protected View inflateAction(Action action) {
        if (action == null) {
            return null;
        }

        View view = null;

        if (TextUtils.isEmpty(action.text)) {
            ImageView img = new ImageView(getContext());
            img.setImageDrawable(action.drawable);
            view = img;
        }else{
            TextView text = new TextView(getContext());
            text.setGravity(Gravity.CENTER);
            text.setText(action.text);
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, action.textSize == 0 ? mActionTextSize : action.textSize);
            //字体大于等于16sp自动加粗
            if (YFrameUtils.px2sp(mActionTextSize) >= 16) {
                TextPaint tp = text.getPaint();
                tp.setFakeBoldText(true);
            }
            text.setTextColor(action.textColor == 0 ? mActionTextColor : action.textColor);

            if (action.drawable != null) {
                action.drawable.setBounds(0, 0, YFrameUtils.dp2px(19), YFrameUtils.dp2px( 19));
                text.setCompoundDrawables(null, action.drawable, null, null);
            }

            view = text;
        }

        int[] padding = action.padding;
        if (padding != null) {
            view.setPadding(padding[0], padding[1], padding[2], padding[3]);
        } else {
            view.setPadding(mActionHorizontalPadding, mActionVerticalPadding, mActionHorizontalPadding, mActionVerticalPadding);
        }

        if (action.clickListener !=null) {
            view.setOnClickListener(action.clickListener);
        }

        return view;
    }

    public View getViewByAction(Action action) {
        return findViewWithTag(action);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height;
        if (heightMode != MeasureSpec.EXACTLY) {
            height = mBarHeight + mStatusBarHeight;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mBarHeight, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.getSize(heightMeasureSpec) + mStatusBarHeight;
        }

        measureChild(mLeftText, widthMeasureSpec, heightMeasureSpec);
        measureChild(mRightLayout, widthMeasureSpec, heightMeasureSpec);
        if (mLeftText.getMeasuredWidth() > mRightLayout.getMeasuredWidth()) {
            mCenterLayout.measure(
                    MeasureSpec.makeMeasureSpec(mScreenWidth - 2 * mLeftText.getMeasuredWidth(), MeasureSpec.EXACTLY)
                    , heightMeasureSpec);
        } else {
            mCenterLayout.measure(
                    MeasureSpec.makeMeasureSpec(mScreenWidth - 2 * mRightLayout.getMeasuredWidth(), MeasureSpec.EXACTLY)
                    , heightMeasureSpec);
        }
        measureChild(mDividerView, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLeftText.layout(0, mStatusBarHeight, mLeftText.getMeasuredWidth(), mLeftText.getMeasuredHeight() + mStatusBarHeight);
        mRightLayout.layout(mScreenWidth - mRightLayout.getMeasuredWidth(), mStatusBarHeight,
                mScreenWidth, mRightLayout.getMeasuredHeight() + mStatusBarHeight);
        if (mCenterGravity == CENTER_LEFT) {
            mCenterLayout.layout(mLeftText.getMeasuredWidth(), mStatusBarHeight,
                    mScreenWidth - mLeftText.getMeasuredWidth(), getMeasuredHeight());
        } else if (mCenterGravity == CENTER_RIGHT) {
            mCenterLayout.layout(mRightLayout.getMeasuredWidth(), mStatusBarHeight,
                    mScreenWidth - mRightLayout.getMeasuredWidth(), getMeasuredHeight());
        } else {
            if (mLeftText.getMeasuredWidth() > mRightLayout.getMeasuredWidth()) {
                mCenterLayout.layout(mLeftText.getMeasuredWidth(), mStatusBarHeight,
                        mScreenWidth - mLeftText.getMeasuredWidth(), getMeasuredHeight());
            } else {
                mCenterLayout.layout(mRightLayout.getMeasuredWidth(), mStatusBarHeight,
                        mScreenWidth - mRightLayout.getMeasuredWidth(), getMeasuredHeight());
            }
        }

        mDividerView.layout(0, getMeasuredHeight() - mDividerView.getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight());
    }

    /**
     * 计算状态栏高度高度
     * getStatusBarHeight
     *
     * @return
     */
    public static int getStatusBarHeight() {
        return getInternalDimensionSize(Resources.getSystem(), STATUS_BAR_HEIGHT_RES_NAME);
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * A {@link LinkedList} that holds a list of {@link Action}s.
     */
    @SuppressWarnings("serial")
    public static class ActionList extends LinkedList<Action> {
    }


    public static class Action {
        public CharSequence text;
        public int textColor;
        public int textSize;
        public Drawable drawable;
        public int[] padding;
        public OnClickListener clickListener;

        private Action(Builder builder) {
            text = builder.text;
            textColor = builder.textColor;
            textSize = builder.textSize;
            drawable = builder.drawable;
            padding = builder.padding;
            clickListener = builder.clickListener;

        }

        public static Builder newBuilder() {
            return new Builder();
        }


        public static final class Builder {
            private CharSequence text;
            private int textColor;
            private int textSize;
            private Drawable drawable;
            private int[] padding;
            private OnClickListener clickListener;
            private Builder( ) {

            }

            public Builder text(CharSequence val) {
                text = val;
                return this;
            }

            public Builder textColor(int val) {
                textColor = YFrameUtils.getColor(val);
                return this;
            }

            public Builder textSize(int val) {
                textSize = YFrameUtils.getDimens(val);
                return this;
            }

            public Builder drawable(int val) {
                drawable = YFrameUtils.getDrawable(val);
                return this;
            }

            public Builder padding(int padding) {
                padding = YFrameUtils.dp2px(padding);
                this.padding = new int[]{padding, padding, padding, padding};
                return this;
            }

            public Builder padding(int left, int top, int right, int bottom) {
                left = YFrameUtils.dp2px(left);
                top = YFrameUtils.dp2px(top);
                right = YFrameUtils.dp2px(right);
                bottom = YFrameUtils.dp2px(bottom);
                this.padding = new int[]{left, top, right, bottom};
                return this;
            }


            public Builder clickListener(OnClickListener val) {
                clickListener = val;
                return this;
            }

            public Action build() {
                return new Action(this);
            }
        }
    }

    public TextView getLeftText() {
        return mLeftText;
    }

    public TextView getSubTitleText() {
        return mSubTitleText;
    }

    public TextView getCenterText() {
        return mCenterText;
    }

    public View getDividerView() {
        return mDividerView;
    }
}