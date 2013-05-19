package com.example.pguide;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.http.util.EncodingUtils;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.widget.TextView;

public class WelActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wel);
		writeFileData("CityName", "");
		PackageManager pm = getPackageManager();
		try {
		    PackageInfo pi = pm.getPackageInfo("com.lyt.android", 0);
		} catch (NameNotFoundException e) {
		    e.printStackTrace();
		}

		new Handler().postDelayed(new Runnable(){

		@Override
		public void run() {
		    Intent intent = new Intent(WelActivity.this,PreferencesActivity.class);
		    startActivity(intent);
		    finish();
		}

		}, 2500);
		/*
		Intent intent = new Intent(WelActivity.this, PreferencesActivity.class);
		//intent.putExtra("City", FinalCity[arg2]);
		startActivity(intent);
		finish();*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void writeFileData(String fileName,String message) { 
		try { 
			FileOutputStream fout =openFileOutput(fileName, MODE_PRIVATE);
			byte [] bytes = message.getBytes(); 
			fout.write(bytes); 
			fout.close(); 
		}  catch(Exception e) { 
			e.printStackTrace(); 
		} 
	}

	public String readFileData(String fileName){ 
		String res=""; 
		try { 
			FileInputStream fin = openFileInput(fileName); 
			int length = fin.available(); 
			byte [] buffer = new byte[length]; 
			fin.read(buffer);     
			res = EncodingUtils.getString(buffer, "UTF-8"); 
			fin.close();     
		} catch(Exception e) { 
			e.printStackTrace(); 
		} 
		return res; 
	} 

}

