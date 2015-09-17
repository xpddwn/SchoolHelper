package com.slidingmenu;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.slidingmenu.Util.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class UserLogin extends Activity{

	private ImageView back;
	private EditText nickname;
	private EditText password;
	private String user_name;
	private String user_password;
	
	private Button btn_bind;
	private Button btn_edit;
	
	/**
	 * @param args
	 */
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_login);
		
		back = (ImageView)findViewById(R.id.iv_left);
		nickname = (EditText)findViewById(R.id.nickname);
		password = (EditText)findViewById(R.id.passport);
		btn_bind = (Button)findViewById(R.id.btn_bind);
		btn_edit = (Button)findViewById(R.id.btn_edit);
		
		user_name = nickname.getText().toString();
		user_password = password.getText().toString();
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserLogin.this.finish();
			}
		});
		
		btn_bind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("test");
				method();
			}
		});
	}
	
	private void method(){
		RequestParams login_request = new RequestParams();
//		login_request.put("username", user_name);
//		login_request.put("password", user_password);
		String tag = "5";
		
		HttpUtil.get(tag,login_request, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonobject) {
				// TODO Auto-generated method stub
				super.onSuccess(jsonobject);
				String statuscode = null;
				try {
					System.out.println("login test ");
					statuscode = jsonobject.getString("ok");
					System.out.println("statuscode " + statuscode);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
				}
				
				if (statuscode.equalsIgnoreCase("true")) {
					Toast mytoast = Toast.makeText(getApplicationContext(),
							"绑定成功", Toast.LENGTH_SHORT);
					mytoast.setGravity(Gravity.CENTER_HORIZONTAL
							| Gravity.CENTER_VERTICAL, 0, 0);
					mytoast.show();
					UserLogin.this.finish();
				}else if (statuscode.equalsIgnoreCase("false")) {
					Toast mytoast = Toast.makeText(getApplicationContext(),
							"绑定失败", Toast.LENGTH_SHORT);
					mytoast.setGravity(Gravity.CENTER_HORIZONTAL
							| Gravity.CENTER_VERTICAL, 0, 0);
					mytoast.show();
				}
			}

			public void onFailure(Throwable arg0) { 
				System.out.println("onfailure");
			}

			public void onFinish() { 
				System.out.println("Setting onfinish");
			}

			@Override
			protected void handleFailureMessage(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.handleFailureMessage(arg0, arg1);
				System.out.println("onfailuremessage" + arg0 + arg1);
			};
		});
	}

}
