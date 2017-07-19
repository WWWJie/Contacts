package com.example.contacts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

public class SqliteOperater {
	public static SQLiteDatabase mDb = null;
	public static void initDatabase(Activity act)
	{
		
	    String databaseFilename =act.getFilesDir()+"/teldb";   
	    File file = new File(act.getFilesDir(),"teldb");   
	    if(!file.exists())
	    {
		    FileOutputStream os = null;   
		    try{   
		    	os = new FileOutputStream(databaseFilename);
		    }catch(FileNotFoundException e){   
		    	e.printStackTrace();   
		    }   
		    InputStream is = act.getResources().openRawResource(R.raw.teldb);//�õ����ݿ��ļ���������   
		    byte[] buffer = new byte[8192];   
		    int count = 0;   
		    try{   
		        
		    	while((count=is.read(buffer))>0){   
		    		os.write(buffer, 0, count);   
		    		os.flush();   
		    	}   
		        is.close();   
		        os.close();   
		    }catch(IOException e){   
		    	e.printStackTrace();   
		    }   
	    }
	    
	    mDb=SQLiteDatabase.openDatabase(databaseFilename, null, SQLiteDatabase.OPEN_READWRITE);
	}
}
