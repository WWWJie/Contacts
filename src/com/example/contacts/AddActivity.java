package com.example.contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		SqliteOperater.initDatabase(this);
		
		ImageButton savebtn=(ImageButton)findViewById(R.id.imageButton1);
		ImageButton backbtn=(ImageButton)findViewById(R.id.imageButton2);
		final EditText edtName = (EditText) findViewById(R.id.editText1);
		final EditText edtTel = (EditText) findViewById(R.id.editText2);
		final EditText edtsno = (EditText) findViewById(R.id.editText3);
		final EditText edtclass = (EditText) findViewById(R.id.editText4);
		final EditText edtemail = (EditText) findViewById(R.id.editText4);
		final EditText edtother = (EditText) findViewById(R.id.editText6);
		
		savebtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(TextUtils.isEmpty(edtName.getText()))
				{
					Toast.makeText(getApplicationContext(), "请输入姓名", Toast.LENGTH_LONG).show();
				}
				else if(TextUtils.isEmpty(edtTel.getText())){
					Toast.makeText(getApplicationContext(), "请输入短号", Toast.LENGTH_LONG).show();
				}
				else{
				
				
				ContentValues content = new ContentValues();
				
				content.put("name", edtName.getText().toString());
				content.put("tel", edtTel.getText().toString());
				content.put("sno", edtsno.getText().toString());
				content.put("class1", edtclass.getText().toString());
				content.put("email", edtemail.getText().toString());
				content.put("other", edtother.getText().toString());
				SqliteOperater.mDb.insert("telephone", null, content);
				Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_LONG).show();
				Intent intent=new Intent(AddActivity.this,MainActivity.class);
				startActivity(intent);
				
				/*new Builder(AddActivity.this)
                .setTitle("确认" ) 
                .setMessage("添加成功" ) 
                .setPositiveButton("是" , 
                		new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						Intent intent=new Intent(AddActivity.this,MainActivity.class);
						startActivity(intent);
					}
				}
                )
                .show();*/
				}
			}
		});
		
		backbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AddActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
	}

}
