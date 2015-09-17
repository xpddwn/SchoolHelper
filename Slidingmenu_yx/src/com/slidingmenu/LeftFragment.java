package com.slidingmenu;

import com.slidingmenu.Util.BitmapUtil;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;  

public class LeftFragment extends Fragment implements OnClickListener
{
	private TextView mainpage;
    private TextView tvTalk;
    private TextView schollcar;
    private TextView schollcalendar;
    private TextView buaamuse;
    private TextView boya;
    private TextView schoolwork;
    private TextView scholar;
    private TextView stipend;
    
    private ImageView head; 
    private SharedPreferences headsp;
    
    private Thread myThread;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        //给控件加监听
        
        mainpage.setOnClickListener(this);
        tvTalk.setOnClickListener(this);
        schollcar.setOnClickListener(this);
        schollcalendar.setOnClickListener(this);
        buaamuse.setOnClickListener(this);
        schoolwork.setOnClickListener(this);
        scholar.setOnClickListener(this);
        stipend.setOnClickListener(this);
        boya.setOnClickListener(this);
        head.setOnClickListener(this);
    }
    
    public void restartthread() {
    	if(myThread != null)	
    	{	if (myThread.isAlive()) {
    			myThread.interrupt();
    		}
    		myThread = newThread();
    		myThread.start();
    	}
    	else{
    		myThread = newThread();
    		myThread.start();
    	}
    	}
    
    public Thread newThread(){
    	myThread = new Thread(new Runnable() {
    		
    		@Override
    		public void run() {
    			// TODO Auto-generated method stub
    			while(true){
    				int status = 0;
    				try{
    					status = ((MainActivity)getActivity()).getstatus();
    				}catch(Exception e){
    					e.printStackTrace();
    				}
    				
    				Message msg = new Message();
    				if(status == 1){
    					System.out.println("the picture has changed");
    					msg.what = 1;
    					myhandler.sendMessage(msg);
    					((MainActivity)getActivity()).setstatus();
    				}
    				else{
    					continue;
    				}
    			}
    		}
    	});
    	return myThread;
    }
    
    
    public Handler myhandler = new Handler(){
    	public void handleMessage(Message msg){
    		switch(msg.what){
    		case 1:
    			 String imageBase64 = headsp.getString("avatar", "null");
    				if (imageBase64.equalsIgnoreCase("null")) {
    					Resources res = getResources();
    					Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.test);
    					Bitmap mypic = BitmapUtil.drawRoundBitmap(bitmap, 5);

    					head.setImageBitmap(mypic);
    				} else {
    					byte[] base64Bytes = Base64.decode(imageBase64, Base64.DEFAULT);
    					ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
    					head.setImageDrawable(Drawable.createFromStream(bais, "image"));
    				}
    				myThread.interrupt();
    				break;
    		default:
    			myThread.interrupt();
    			break;
    		}
    	}
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.left, null);
        mainpage = (TextView)view.findViewById(R.id.mainpage);
        tvTalk = (TextView) view.findViewById(R.id.news);
        schollcar = (TextView) view.findViewById(R.id.schoolcar);
        schollcalendar = (TextView) view.findViewById(R.id.schoolcalendar);
        buaamuse = (TextView) view.findViewById(R.id.buaamuse);
        scholar = (TextView) view.findViewById(R.id.scholar);
        stipend = (TextView) view.findViewById(R.id.stipend);
        boya = (TextView) view.findViewById(R.id.boya);
        schoolwork = (TextView) view.findViewById(R.id.works);
        head = (ImageView) view.findViewById(R.id.head_image);
        
        
        headsp = ((MainActivity)getActivity()).mysp;
        return view;
    }

    @Override
    public void onClick(View v)
    {
        int id = 0;
        switch (v.getId())
        {
        	case R.id.mainpage:
        		id = 0;
        		break;
            case R.id.news:
                id = 1;
                break;
            case R.id.boya:
                id = 2;
                break;
            case R.id.works:
                id = 3;
                break;
            case R.id.schoolcar:
            	id = 4;
            	break;
            case R.id.schoolcalendar:
            	id = 5;
            	break;	
            case R.id.buaamuse:
            	id = 6;
            	break;
            case R.id.scholar:
            	id = 7;
            	break;	
            case R.id.stipend:
            	id = 8;
            	break;
            case R.id.head_image:
            	id = 9;
            	break;
        }
        //调用Sliding1Activity中的setFragment替换中间的Fragment
        ((MainActivity) getActivity()).setFragment(id);
    }
    
    @Override
    public void onStart(){
    	super.onStart();
        restartthread();
    	System.out.println("activity onStart()");
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	System.out.println("activity onResume()");
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	System.out.println("activity onPause()");
    }
    
    @Override
    public void onStop(){
    	super.onStop();
    	myThread.interrupt();
    	System.out.println("activity onStop()");
    }
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	System.out.println("activity onDestroy()");
    }
    
    @Override
    public void onDestroyView(){
    	super.onDestroyView();
    	System.out.println("activity onDestroyView()");
    }
    
    @Override
    public void onDetach(){
    	super.onDetach();
    	System.out.println("activity onDetach()");
    }
}
