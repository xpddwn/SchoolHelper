package com.slidingmenu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * 滑动view
 * @author xcp001
 *
 */
public class SlidingView extends ViewGroup
{
    private FrameLayout frameLayout;
    private Scroller scroller;
    //VelocityTracker就是速度跟踪的意思。我们可以获得触摸点的坐标，根据按下的时间可以简单的计算出速度的大小。
    private VelocityTracker velocityTracker;
    private int touchSlop;
    private float lastMotionX;//上次点击的x坐标值
    private float lastMotionY;//上次点击的y坐标值
    private static final int SNAP_VELOCITY = 200;//滑动的速度
    private View leftView;//左边的菜单
    private View rightView;//右边的菜单
    private boolean isBeingDragged;//可不可以滑
    private boolean canSlideRight = true;//可不可以右滑

    public boolean isCanSlideRight()
    {
        return canSlideRight;
    }

    public void setCanSlideRight(boolean canSlideRight)
    {
        this.canSlideRight = canSlideRight;
    }

    public SlidingView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SlidingView(Context context)
    {
        super(context);
        init();
    }

    public SlidingView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        frameLayout.measure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init()
    {
        frameLayout = new FrameLayout(getContext());
        frameLayout.setBackgroundColor(0xff000000);
        scroller = new Scroller(getContext());
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        super.addView(frameLayout);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        final int width = r - l;
        final int height = b - t;
        frameLayout.layout(0, 0, width, height);

    }

    public void setView(View v)
    {
        if (frameLayout.getChildCount() > 0)
        {
            frameLayout.removeAllViews();
        }
        frameLayout.addView(v);
    }

    //监听滑动事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        final int action = ev.getAction();
        final float x = ev.getX();
        final float y = ev.getY();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                lastMotionX = x;
                lastMotionY = y;
                isBeingDragged = false;
                break;
            case MotionEvent.ACTION_MOVE:
                final float dx = x - lastMotionX;
                final float xDiff = Math.abs(dx);//x轴移动的绝对值
                final float yDiff = Math.abs(y - lastMotionY);//获取y轴移动的绝对值
                //判断是不是左右滑动
                if (xDiff > touchSlop && xDiff > yDiff)
                {
                    // 如果是右滑
                    if (x < lastMotionX)
                    {//右滑
                        if (0 == getScrollX())
                        {//左边的菜单有没有打开？
                            if (rightView.getWidth() != getScrollX())
                            {
                                if (!canSlideRight)
                                {
                                    // 菜单没打开了
                                    System.out.println("不能右划");
                                    isBeingDragged = false;
                                }
                                else
                                {
                                    isBeingDragged = true;
                                    lastMotionX = x;
                                }
                            }
                        }
                        else
                        {
                            System.out.println("左边的菜单打开");
                            isBeingDragged = true;
                            lastMotionX = x;

                        }
                    }
                    else
                    {//左滑
                        isBeingDragged = true;
                        lastMotionX = x;
                    }
                }
                break;

        }
        return isBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (velocityTracker == null)
        {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);

        final int action = event.getAction();
        final float x = event.getX();
        final float y = event.getY();

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished())
                {
                    scroller.abortAnimation();
                }
                lastMotionX = x;
                lastMotionY = y;
                if (getScrollX() == -getLeftViewWidth()
                        && lastMotionX < getLeftViewWidth())
                {
                    return false;
                }
                if (getScrollX() == getRightViewWidth()
                        && lastMotionX > getLeftViewWidth())
                {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isBeingDragged)
                {
                    enableChildernCache();
                    final float deltaX = lastMotionX - x;
                    lastMotionX = x;
                    float oldScrollX = getScrollX();
                    float scrollX = oldScrollX + deltaX;

                    if (deltaX < 0 && oldScrollX < 0)
                    {// 左滑
                    	
                        final float leftBound = 0;
                        final float rightBound = -getLeftViewWidth();
                        if (scrollX > leftBound)
                        {
                            scrollX = leftBound;
                        }
                        else if (scrollX < rightBound)
                        {
                            scrollX = rightBound;
                        }
                        //左边菜单滑出，右边的菜单要设置为不可视，不然右边的菜单会盖住左边菜单的一小部分
                        rightView.setVisibility(View.INVISIBLE);
                        leftView.setVisibility(View.VISIBLE);
                    }
                    else if (deltaX > 0 && oldScrollX > 0)
                    {//右滑
                     //右边菜单滑出，左边的菜单要设置为不可视
                        rightView.setVisibility(View.VISIBLE);
                        leftView.setVisibility(View.INVISIBLE);
                        final float rightBound = getRightViewWidth();
                        final float leftBound = 0;
                        if (scrollX < leftBound)
                        {
                            scrollX = leftBound;
                        }
                        else if (scrollX > rightBound)
                        {
                            scrollX = rightBound;
                        }

                    }
                    scrollTo((int) scrollX, getScrollY());
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                if (isBeingDragged)
                {
                    final VelocityTracker velocityTrackers = velocityTracker;
                    velocityTrackers.computeCurrentVelocity(SNAP_VELOCITY);
                    int velocityX = (int) velocityTrackers.getXVelocity();
                    int oldScrollX = getScrollX();
                    int dx = 0;
                    if (oldScrollX < 0)
                    {
                        if (oldScrollX < -getLeftViewWidth() / 2
                                || velocityX > SNAP_VELOCITY)
                        {
                            dx = -getLeftViewWidth() - oldScrollX;
                        }
                        else if (oldScrollX >= -getLeftViewWidth() / 2
                                || velocityX < -SNAP_VELOCITY)
                        {
                            dx = -oldScrollX;
                        }
                    }
                    else
                    {
                        if (oldScrollX > getRightViewWidth() / 2
                                || velocityX < -SNAP_VELOCITY)
                        {
                            dx = getRightViewWidth() - oldScrollX;
                        }
                        else if (oldScrollX <= getRightViewWidth() / 2
                                || velocityX > SNAP_VELOCITY)
                        {
                            dx = -oldScrollX;
                        }
                    }
                    smoothScrollTo(dx);
                    clearClildrenCache();
                }
                break;
        }
        if (velocityTracker != null)
        {
            velocityTracker.recycle();
            velocityTracker = null;
        }

        return true;
    }

    @Override
    public void computeScroll()
    {
        if (!scroller.isFinished())
        {
            if (scroller.computeScrollOffset())
            {
                int oldX = getScrollX();
                int oldY = getScrollY();
                int x = scroller.getCurrX();
                int y = scroller.getCurrY();
                if (oldX != x || oldY != y)
                {
                    scrollTo(x, y);
                }
                invalidate();
            }
            else
            {
                clearClildrenCache();
            }
        }
        else
        {
            clearClildrenCache();
        }
    }

    void clearClildrenCache()
    {
        final int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            final View layout = (View) getChildAt(i);
            layout.setDrawingCacheEnabled(false);
        }
    }

    void smoothScrollTo(int dx)
    {// 使界面滑到规定的位置，不会手指划到那就停在那，或者超出界面
        int duration = 500;
        int oldScrollX = getScrollX();
        scroller.startScroll(oldScrollX, getScrollY(), dx, getScrollY(),
                duration);
        invalidate();
    }

    void enableChildernCache()
    {
        final int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            final View layout = (View) getChildAt(i);
            layout.setDrawingCacheEnabled(true);
        }

    }

    @Override
    public void scrollTo(int x, int y)
    {
        super.scrollTo(x, y);
        postInvalidate();// 刷新
    }

    private int getLeftViewWidth()
    {
        if (leftView == null)
        {
            return 0;
        }
        return leftView.getWidth();
    }

    private int getRightViewWidth()
    {
        if (rightView == null)
        {
            return 0;
        }
        return rightView.getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }

    public View getLeftView()
    {
        return leftView;
    }

    public void setLeftView(View leftView)
    {
        this.leftView = leftView;
    }

    public View getRightView()
    {
        return rightView;
    }

    public void setRightView(View rightView)
    {
        this.rightView = rightView;
    }

    //显示左边的菜单
    public void showLeftView()
    {
        int menuWidth = leftView.getWidth();
        int oldScrollX = getScrollX();
        rightView.setVisibility(View.INVISIBLE);
        leftView.setVisibility(View.VISIBLE);
        if (oldScrollX == 0)
        {
            smoothScrollTo(-menuWidth);
        }
        else if (oldScrollX == -menuWidth)
        {
            smoothScrollTo(menuWidth);
        }
    }

    //显示右边的菜单
    public void showRightView()
    {
        int menuWidth = rightView.getWidth();
        rightView.setVisibility(View.VISIBLE);
        leftView.setVisibility(View.INVISIBLE);
        int oldScrollX = getScrollX();
        if (oldScrollX == 0)
        {
            smoothScrollTo(menuWidth);
        }
        else if (oldScrollX == menuWidth)
        {
            smoothScrollTo(-menuWidth);
        }
    }
}
