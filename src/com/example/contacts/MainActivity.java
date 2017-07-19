package com.example.contacts;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	int iCurrentPos = 0;
	BaseAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SqliteOperater.initDatabase(this);
		
		ImageButton addbtn=(ImageButton)findViewById(R.id.imageButton1);
		ListView listView=(ListView)findViewById(R.id.listView1);
		final List<Map<String, String>> ListItems = new ArrayList<Map<String,String>>();
		Cursor cursor = SqliteOperater.mDb.rawQuery("select * from telephone", null);
		
		while(cursor.moveToNext())
		{
			Map<String, String> item = new HashMap<String, String>();
			item.put("rawid", cursor.getString(0));
			item.put("name", cursor.getString(1));
			item.put("number", cursor.getString(2));
			item.put("xuehao", cursor.getString(3));
			item.put("banji", cursor.getString(4));
			ListItems.add(item);
			
		}
		adapter = new SimpleAdapter(this.getApplicationContext(), 
				  ListItems, 
				  R.layout.item, 
				  new String[] {"name","number","xuehao","banji"}, 
				  new int[]{R.id.textView1,R.id.textView4,R.id.textView5,R.id.textView6}){
			
				public View getView(int position, View convertView, ViewGroup parent) {
		            View view = super.getView(position, convertView, parent);
		            ImageButton callbtn=(ImageButton)view.findViewById(R.id.imageButton1);
		            TextView tel=(TextView)view.findViewById(R.id.textView4);
		            
		            final String phone=tel.getText().toString();
		            callbtn.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Uri uri = Uri.parse("tel:"+phone);							
							Intent intent = new Intent(Intent.ACTION_DIAL, uri);							
							startActivity(intent);
							
						}
						
					});
					return view;
					
		            
		        }
				
			
		};
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Map<String, String> item = (Map<String, String>) arg0.getItemAtPosition(arg2);
				Intent intent=new Intent(MainActivity.this,UpdateActivity.class);
				intent.putExtra("rawid", item.get("rawid").toString());
				startActivityForResult(intent,1000);				
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				final Map<String, String> item = (Map<String, String>) arg0.getItemAtPosition(arg2);
				final int rawid=Integer.parseInt(item.get("rawid"));
				
                iCurrentPos = arg2;
                new AlertDialog.Builder(MainActivity.this)
	            .setTitle("删除" ) 
	            .setMessage("确定删除该记录？") 
	            .setPositiveButton("是" , 
	            		new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								SqliteOperater.mDb.delete("telephone", "rawid=?",new String[]{item.get("rawid").toString()});
								ListItems.remove(iCurrentPos);
								adapter.notifyDataSetChanged();			
							}
						})
	            .setNegativeButton("否",
	            		new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								
							}
						})
	            .show();
				
				return true;
			}
			
		});		
		addbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,AddActivity.class);
				startActivityForResult(intent,1000);
			}
		});
		
		
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
	    super.onActivityResult(requestCode, resultCode, data);
	    
	if( requestCode == 1000 && resultCode == 1001)
	    
	{
	        
		Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_LONG).show();
		adapter.notifyDataSetChanged();	
	    
	}
	    
	else if(requestCode == 1000 && resultCode == 1002)
	    
	{
	    		    	
		Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
		adapter.notifyDataSetChanged();	
	    }
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
