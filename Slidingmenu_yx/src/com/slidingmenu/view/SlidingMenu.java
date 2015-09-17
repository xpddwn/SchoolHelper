package com.slidingmenu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * �������
 * @author yoyo
 *
 */
public class SlidingMenu extends RelativeLayout
{
    private SlidingView centerView;
    private View leftView;
    private View rightView;

    public SlidingMenu(Context context)
    {
        super(context);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public SlidingMenu(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void addViews(View left, View center, View right)
    {
        setLeftView(left);
        setRightView(right);
        setCenterView(center);
    }

    //���������ߵ�view
    public void setLeftView(View view)
    {
        LayoutParams behindParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.FILL_PARENT);
        addView(view, behindParams);
        leftView = view;
    }

    //���ұߵ�view����leftview������
    public void setRightView(View view)
    {
        LayoutParams behindParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.FILL_PARENT);
        behindParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        addView(view, behindParams);
        rightView = view;
    }

    //���������view��
    public void setCenterView(View view)
    {
        LayoutParams aboveParams = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        centerView = new SlidingView(getContext());
        addView(centerView, aboveParams);
        centerView.setView(view);
        centerView.invalidate();
        centerView.setLeftView(leftView);
        centerView.setRightView(rightView);
    }

    public void setCanSlideRight(boolean can)
    {
        centerView.setCanSlideRight(can);
    }

    public void showLeftView()
    {
        centerView.showLeftView();
    }

    public void showRightView()
    {
        centerView.showRightView();
    }

}
