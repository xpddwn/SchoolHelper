package com.slidingmenu;

import java.util.Timer;
import java.util.TimerTask;

import com.slidingmenu.view.SlidingMenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 学生客户端
 * 
 * @author yoyo
 *
 */
public class MainActivity extends FragmentActivity
{
    private SlidingMenu slidingMenu;//滑动框架

    private LeftFragment leftFragment;//左侧Fragment
    private RightFragment rightFragment;//右侧Fragment
    private CenterFragment centerFragment;//中间主Fragment

    private SchoolNewsFragment friendFragment;
    private BoyaFragment settingFragment;
    private BuaAmuseFragment buaamuseFragment;
    private ScholarFragment scholarFragment;
    private SchoolCarFragemnt schoolcarFragment;
    private SchoolCalendarFragment schoolcalendarFragment;
    private SchoolWorkFragment schoolworkFragment;
    private StipendFragment stipendFragment;
    private UesrinfoFragment userinfoFragment;

    private ImageView showLeft;//左边菜单
    private ImageView showRight;//右边菜单

    private FragmentTransaction t;
    private Boolean isQuit = false;
    private Timer timer = new Timer();
    public SharedPreferences mysp;
    
    public int status =0;
    
    public int getstatus(){
    	return status;
    }
    
    public void setstatus(){
    	status = 0;
    }

    @Override
    protected void onCreate(Bundle arg0)
    {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.sliding_1_activity);
        
        slidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
        
        //创建SharedPreferences 本地存储
        mysp = this.getSharedPreferences("SP", MODE_PRIVATE);

        //设置frameLayout设置到RelativeLayout中:左边、中间、右边
        slidingMenu.setLeftView(getLayoutInflater().inflate(
                R.layout.left_frame, null));
        slidingMenu.setRightView(getLayoutInflater().inflate(
                R.layout.right_frame, null));
        slidingMenu.setCenterView(getLayoutInflater().inflate(
                R.layout.center_frame, null));

        //将fragment和frameLayout相绑定
        t = this.getSupportFragmentManager().beginTransaction();
        leftFragment = new LeftFragment();
        t.replace(R.id.left_frame, leftFragment);
        rightFragment = new RightFragment();
        t.replace(R.id.right_frame, rightFragment);
        centerFragment = new CenterFragment();
        t.replace(R.id.center_frame1, centerFragment);
        t.commit();
        showLeft = (ImageView) findViewById(R.id.iv_left);
        showRight = (ImageView) findViewById(R.id.iv_right);
        showLeft.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showLeft();
            }
        });
        showRight.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                showRight();
            }
        });
    }

    //替换fragment的方法
    public void setFragment(int id)
    {
        t = this.getSupportFragmentManager().beginTransaction();
        switch (id)
        {
	        case 0:
	            centerFragment = new CenterFragment();
	            t.replace(R.id.center_frame1, centerFragment);
	            if (rightFragment == null)
	            {
	                rightFragment = new RightFragment();
	                t.replace(R.id.right_frame, rightFragment);
	            }
	            showRight.setVisibility(View.VISIBLE);
	            slidingMenu.setCanSlideRight(true);
	            showLeft();
	            break;
        
            case 1:
                friendFragment = new SchoolNewsFragment();
                t.replace(R.id.center_frame1, friendFragment);
                if (rightFragment == null)
                {
                    rightFragment = new RightFragment();
                    t.replace(R.id.right_frame, rightFragment);
                }
                showRight.setVisibility(View.VISIBLE);
                slidingMenu.setCanSlideRight(true);
                showLeft();
                break;
                
            case 2:
                settingFragment = new BoyaFragment();
                t.replace(R.id.center_frame1, settingFragment);
                //将右边的fragment移除掉，这样右边的菜单就不会滚出来
                if (rightFragment != null)
                {
                    t.remove(rightFragment);
                    rightFragment = null;
                }
                showRight.setVisibility(View.GONE);
                //设置成不能右滑
                slidingMenu.setCanSlideRight(false);
                showLeft();
                break;
                
            case 4:
            	schoolcarFragment = new SchoolCarFragemnt();
                t.replace(R.id.center_frame1, schoolcarFragment);
                //将右边的fragment移除掉，这样右边的菜单就不会滚出来
                if (rightFragment == null)
                {
                    rightFragment = new RightFragment();
                    t.replace(R.id.right_frame, rightFragment);
                }
                showRight.setVisibility(View.VISIBLE);
                slidingMenu.setCanSlideRight(true);
                showLeft();
                break;
                
            case 6:
            	buaamuseFragment = new BuaAmuseFragment();
                t.replace(R.id.center_frame1, buaamuseFragment);
                //将右边的fragment移除掉，这样右边的菜单就不会滚出来
                if (rightFragment == null)
                {
                    rightFragment = new RightFragment();
                    t.replace(R.id.right_frame, rightFragment);
                }
                showRight.setVisibility(View.VISIBLE);
                slidingMenu.setCanSlideRight(true);
                showLeft();
                break;
                
             case 7:
            	 scholarFragment = new ScholarFragment();
                t.replace(R.id.center_frame1, scholarFragment);
                if (rightFragment == null)
                {
                    rightFragment = new RightFragment();
                    t.replace(R.id.right_frame, rightFragment);
                }
                showRight.setVisibility(View.VISIBLE);
                slidingMenu.setCanSlideRight(true);
                showLeft();
                break;
                
             case 5:
            	 schoolcalendarFragment = new SchoolCalendarFragment();
                t.replace(R.id.center_frame1, schoolcalendarFragment);
                if (rightFragment == null)
                {
                    rightFragment = new RightFragment();
                    t.replace(R.id.right_frame, rightFragment);
                }
                showRight.setVisibility(View.VISIBLE);
                slidingMenu.setCanSlideRight(true);
                showLeft();
                break;
                
             case 3:
            	 schoolworkFragment = new SchoolWorkFragment();
                t.replace(R.id.center_frame1, schoolworkFragment);
                if (rightFragment == null)
                {
                    rightFragment = new RightFragment();
                    t.replace(R.id.right_frame, rightFragment);
                }
                showRight.setVisibility(View.VISIBLE);
                slidingMenu.setCanSlideRight(true);
                showLeft();
                break;
                
             case 8:
            	 stipendFragment = new StipendFragment();
                t.replace(R.id.center_frame1, stipendFragment);
                if (rightFragment == null)
                {
                    rightFragment = new RightFragment();
                    t.replace(R.id.right_frame, rightFragment);
                }
                showRight.setVisibility(View.VISIBLE);
                slidingMenu.setCanSlideRight(true);
                showLeft();
                break;

             case 9:
            	 userinfoFragment = new UesrinfoFragment();
                t.replace(R.id.center_frame1, userinfoFragment);
                if (rightFragment == null)
                {
                    rightFragment = new RightFragment();
                    t.replace(R.id.right_frame, rightFragment);
                }
                showRight.setVisibility(View.VISIBLE);
                slidingMenu.setCanSlideRight(true);
                showLeft();
                break;
                
             case 10:
            	 schoolcalendarFragment = new SchoolCalendarFragment();
                t.replace(R.id.center_frame1, schoolcalendarFragment);
                if (rightFragment == null)
                {
                    rightFragment = new RightFragment();
                    t.replace(R.id.right_frame, rightFragment);
                }
                showRight.setVisibility(View.VISIBLE);
                slidingMenu.setCanSlideRight(true);
                break; 
                
             case 11:
            	 schoolcarFragment = new SchoolCarFragemnt();
            	 t.replace(R.id.center_frame1, schoolcarFragment);
            	 if(rightFragment == null)
            	 {
            		 rightFragment = new RightFragment();
            		 t.replace(R.id.right_frame, rightFragment);
            	 }
            	 showRight.setVisibility(View.VISIBLE);
            	 slidingMenu.setCanSlideRight(true);
            	 break;
            	 
             case 12:
            	 buaamuseFragment = new BuaAmuseFragment();
            	 t.replace(R.id.center_frame1, buaamuseFragment);
            	 if(rightFragment == null)
            	 {
            		 rightFragment = new RightFragment();
            		 t.replace(R.id.right_frame, rightFragment);
            	 }
            	 showRight.setVisibility(View.VISIBLE);
            	 slidingMenu.setCanSlideRight(true);
            	 break;
            	 
              case 13:
            	 friendFragment = new SchoolNewsFragment();
            	 t.replace(R.id.center_frame1, friendFragment);
            	 if(rightFragment == null)
            	 {
            		 rightFragment = new RightFragment();
            		 t.replace(R.id.right_frame, rightFragment);
            	 }
            	 showRight.setVisibility(View.VISIBLE);
            	 slidingMenu.setCanSlideRight(true);
            	 break;
            	 
              case 14:
            	 settingFragment = new BoyaFragment();
            	 t.replace(R.id.center_frame1, settingFragment);
            	 if(rightFragment == null)
            	 {
            		 rightFragment = new RightFragment();
            		 t.replace(R.id.right_frame, rightFragment);
            	 }
            	 showRight.setVisibility(View.VISIBLE);
            	 slidingMenu.setCanSlideRight(true);
            	 break;
            	 
              case 15:
             	 userinfoFragment = new UesrinfoFragment();
                 t.replace(R.id.center_frame1, userinfoFragment);
                 if (rightFragment == null)
                 {
                     rightFragment = new RightFragment();
                     t.replace(R.id.right_frame, rightFragment);
                 }
                 showRight.setVisibility(View.VISIBLE);
                 slidingMenu.setCanSlideRight(true);
                 break;
                 
              case 16:
            	  centerFragment = new CenterFragment();
            	  t.replace(R.id.center_frame1, centerFragment);
            	  if(rightFragment == null){
            		  rightFragment = new RightFragment();
            		  t.replace(R.id.right_frame, rightFragment);
            	  }
            	  showRight.setVisibility(View.VISIBLE);
            	  slidingMenu.setCanSlideRight(true);
        }
        t.commit();
    }

    public void showLeft()
    {
    	status = 1;
        slidingMenu.showLeftView();
    }

    public void showRight()
    {
        slidingMenu.showRightView();
    }
    
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)  
    { 
        if (keyCode == KeyEvent.KEYCODE_BACK )  
        {  
        	if(!isQuit){
        		isQuit = true;
        		
        		setFragment(16);

        		Toast.makeText(getBaseContext(),
                        "再按一次返回键退出应用",Toast.LENGTH_LONG).show();
        		
        		TimerTask task = null;
                task = new TimerTask() {
                    @Override
                    public void run() {
                        isQuit = false;
                    }
                };
                timer.schedule(task, 2000);
        	}
        	else{
        		finish();
        	}  
        }  
          
        return false;  
          
    }      
}
