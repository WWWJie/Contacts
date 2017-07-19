package com.example.contacts;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class UpdateActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		
		Intent intent =getIntent();
		Bundle bundle= intent.getExtras();
		final String rawid=bundle.getString("rawid");	
		
		ImageButton savebtn=(ImageButton)findViewById(R.id.imageButton1);
		ImageButton backbtn=(ImageButton)findViewById(R.id.imageButton2);
		final EditText edtName = (EditText) findViewById(R.id.editText1);
		final EditText edtTel = (EditText) findViewById(R.id.editText2);
		final EditText edtsno = (EditText) findViewById(R.id.editText3);
		final EditText edtclass = (EditText) findViewById(R.id.editText4);
		final EditText edtemail = (EditText) findViewById(R.id.editText5);
		final EditText edtother = (EditText) findViewById(R.id.editText6);
		
		Cursor  cursor= SqliteOperater.mDb.rawQuery("select * from telephone where rawid=?", new String[]{rawid});			
		if(cursor.moveToNext())
		{
			edtName.setText(cursor.getString(1));
			edtTel.setText(cursor.getString(2));
			edtsno.setText( cursor.getString(3));
			edtclass.setText(cursor.getString(4));
			edtemail.setText( cursor.getString(5));
			edtother.setText( cursor.getString(6));
								
		}
		
		
		savebtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	
								
				final ContentValues content = new ContentValues();
				content.put("name", edtName.getText().toString());
				content.put("tel", edtTel.getText().toString());
				content.put("sno", edtsno.getText().toString());
				content.put("class1", edtclass.getText().toString());
				content.put("email", edtemail.getText().toString());
				content.put("other", edtother.getText().toString());
				String message="";
				
				if(TextUtils.isEmpty(edtName.getText()))
				{
					Toast.makeText(getApplicationContext(), "请输入姓名", Toast.LENGTH_LONG).show();
				}
				else if(TextUtils.isEmpty(edtTel.getText())){
					Toast.makeText(getApplicationContext(), "请输入短号", Toast.LENGTH_LONG).show();
				}
				
				else{
					SqliteOperater.mDb.update("telephone", content, "rawid=?", new String[]{rawid});
					Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();
					Intent intent=new Intent(UpdateActivity.this,MainActivity.class);
					startActivity(intent);
				}       	
				
			}
		});
		
		backbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(UpdateActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
	}
	
}
