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
 * ����view
 * @author xcp001
 *
 */
public class SlidingView extends ViewGroup
{
    private FrameLayout frameLayout;
    private Scroller scroller;
    //VelocityTracker�����ٶȸ��ٵ���˼�����ǿ��Ի�ô���������꣬���ݰ��µ�ʱ����Լ򵥵ļ�����ٶȵĴ�С��
    private VelocityTracker velocityTracker;
    private int touchSlop;
    private float lastMotionX;//�ϴε����x����ֵ
    private float lastMotionY;//�ϴε����y����ֵ
    private static final int SNAP_VELOCITY = 200;//�������ٶ�
    private View leftView;//��ߵĲ˵�
    private View rightView;//�ұߵĲ˵�
    private boolean isBeingDragged;//�ɲ����Ի�
    private boolean canSlideRight = true;//�ɲ������һ�

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

    //���������¼�
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
                final float xDiff = Math.abs(dx);//x���ƶ��ľ���ֵ
                final float yDiff = Math.abs(y - lastMotionY);//��ȡy���ƶ��ľ���ֵ
                //�ж��ǲ������һ���
                if (xDiff > touchSlop && xDiff > yDiff)
                {
                    // ������һ�
                    if (x < lastMotionX)
                    {//�һ�
                        if (0 == getScrollX())
                        {//��ߵĲ˵���û�д򿪣�
                            if (rightView.getWidth() != getScrollX())
                            {
                                if (!canSlideRight)
                                {
                                    // �˵�û����
                                    System.out.println("�����һ�");
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
                            System.out.println("��ߵĲ˵���");
                            isBeingDragged = true;
                            lastMotionX = x;

                        }
                    }
                    else
                    {//��
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
                    {// ��
                    	
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
                        //��߲˵��������ұߵĲ˵�Ҫ����Ϊ�����ӣ���Ȼ�ұߵĲ˵����ס��߲˵���һС����
                        rightView.setVisibility(View.INVISIBLE);
                        leftView.setVisibility(View.VISIBLE);
                    }
                    else if (deltaX > 0 && oldScrollX > 0)
                    {//�һ�
                     //�ұ߲˵���������ߵĲ˵�Ҫ����Ϊ������
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
    {// ʹ���滬���涨��λ�ã�������ָ�����Ǿ�ͣ���ǣ����߳�������
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
        postInvalidate();// ˢ��
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

    //��ʾ��ߵĲ˵�
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

    //��ʾ�ұߵĲ˵�
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
