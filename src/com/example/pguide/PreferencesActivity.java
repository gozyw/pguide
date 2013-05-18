package com.example.pguide;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PreferencesActivity extends Activity {
	// private TextView tv;
	private TextView CitySel;
	private Button SearchBtn;
	private ImageButton GotBtn;
	private EditText DurationEdit;
	private CheckBox rq, ht,lp, hs;
	private ProgressDialog progressdialog;
	private Handler myHand = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case 0:
					progressdialog.dismiss();
					//MakeToast("Get info failed");
					break;
				case 1:
					progressdialog.dismiss();
					Intent intent = new Intent(PreferencesActivity.this,UserSelActivity.class);
					intent.putExtra("spot", gres);
					startActivity(intent);
					finish();
					///intent
					//~
					//MakeToast(""+gres);
					
					break;
				default:
					break;
			}
		}
	};
	private String serverip;
	private int serverport;
	private String gres;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pref);
		
		CitySel = (TextView) findViewById(R.id.CitySel);
		SearchBtn = (Button) findViewById(R.id.SearchBtn);
		GotBtn = (ImageButton) findViewById(R.id.gotbtn);
		DurationEdit = (EditText) findViewById(R.id.DurationEdit);
		rq = (CheckBox)findViewById(R.id.rqspot);
		ht = (CheckBox)findViewById(R.id.htspot);
		lp = (CheckBox)findViewById(R.id.lpspot);
		hs = (CheckBox)findViewById(R.id.hsspot);
		
		serverip = getString(R.string.IP);
		serverport = new Integer(getString(R.string.PO));
		
		
		GotBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String Cityss = CitySel.getText().toString();
				if (Cityss.compareTo(getString(R.string.caonima)) == 0) {
					MakeToast("请选择城市");
					return;
				}
				String daystr = DurationEdit.getText().toString();
				if (daystr.length() == 0) {
					MakeToast("请输入逗留时间");
					return;
				}
				writeFileData("Days", daystr);
				char[] tempsp = new String("0000").toCharArray();
				if ( rq.isChecked() ) tempsp[0] = '1';
				if ( ht.isChecked() ) tempsp[1] = '1';
				if ( lp.isChecked() ) tempsp[2] = '1';
				if ( hs.isChecked() ) tempsp[3] = '1';
				String Trans = "A;";
				Trans += readFileData("CityName");
				for ( int i = 0; i < 4; i ++ )
				{
					Trans += "&" + tempsp[i];
				}
				//MakeToast(Trans);
				final String wrstring = Trans;
				
				progressdialog = ProgressDialog.show(PreferencesActivity.this,
						"正在加载", "请稍等", true);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Socket socket = new Socket(serverip, serverport);
							PrintWriter writer = new PrintWriter(socket.getOutputStream());
							writer.println(wrstring);
							writer.flush();
							BufferedReader reader = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));
							gres = reader.readLine();
							Thread.sleep(1000);
							myHand.sendEmptyMessage(1);
						} catch (Exception e) {
							//Log.d("Excep", e.getMessage());
							e.printStackTrace();
							myHand.sendEmptyMessage(0);
						}
					}
				}).start();
				
			}

		});

		SearchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PreferencesActivity.this,
						MainActivity.class);
				// intent.putExtra("City", FinalCity[arg2]);
				startActivity(intent);
				finish();
			}

		});

		// tv=(TextView)findViewById(R.id.mmm);
		// tv.setText(City);
		String LocalCityName = readFileData("CityName");
		if (LocalCityName.length() == 0) {
			CitySel.setText(getString(R.string.caonima));
		} else {

			CitySel.setText(LocalCityName);
		}

		// CitySel.setVisibility(BIND_WAIVE_PRIORITY);

	}
	
	private void MakeToast(String str) {
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		writeFileData("CityName", "");
		super.onDestroy();
	}

	public void writeFileData(String fileName, String message) {
		try {
			FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE);
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String readFileData(String fileName) {
		String res = "";
		try {
			FileInputStream fin = openFileInput(fileName);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
