package com.example.myprodWeb;

import com.example.myprodWeb.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayActivity extends Activity {
	String name;
	String password;
	String birthday;
	String sex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		Intent intent=getIntent();
		name=intent.getStringExtra("name");
		password=intent.getStringExtra("password");
		birthday=intent.getStringExtra("birthday");
		sex=intent.getStringExtra("sex");
		TextView tv_name=(TextView)findViewById(R.id.tvname);
		TextView tv_password=(TextView)findViewById(R.id.tvpassword);
		TextView tv_birthday=(TextView)findViewById(R.id.tvbirthday);
		TextView tv_sex=(TextView)findViewById(R.id.tvsex);
		Button OK_btn=(Button)findViewById(R.id.OK_btn);
		tv_name.setText("姓名："+name);
		tv_password.setText("密码："+password);
		tv_birthday.setText("生日："+birthday);
		tv_sex.setText("性别："+sex);
		//if(sex=="男") sex="male";
		//else sex="female";
		OK_btn.setOnClickListener(new OnClickListener(){
 
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				new Thread() {//开启子线程访问网络
					public void run() {
		                   //调用LoginService里面的方法请求网络获取数据
						final String result = RegService.RegByClientGet(name,password,birthday,sex);
						
						if ((result != null)&&result.equals("1")) {
		                        //使用UI线程弹出toast
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									//Toast.makeText(LoginActivity.this, result, 0).show();
									Toast.makeText
									(
											DisplayActivity.this,
											"恭喜您，注册成功", 
											Toast.LENGTH_SHORT
									).show();
								}
							});
						} 
						if ((result != null)&&result.equals("2")) {
	                        //使用UI线程弹出toast
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								//Toast.makeText(LoginActivity.this, result, 0).show();
								Toast.makeText
								(
										DisplayActivity.this,
										"该号码已注册，请直接登录或使用另一号码注册！", 
										Toast.LENGTH_SHORT
								).show();
							}
						});
					} 
						else {// 请求失败 使用UI线程弹出toast
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(DisplayActivity.this, "注册失败...", 0)
											.show();
								}
							});
						}
					};
				}.start();
				Intent intent=new Intent();
				
				intent.setClassName(DisplayActivity.this,"com.example.myprodWeb.LoginActivity");
				intent.putExtra("name", name);
				intent.putExtra("password", password);
				
				startActivity(intent);
			}
			
		});
		
	}

}
