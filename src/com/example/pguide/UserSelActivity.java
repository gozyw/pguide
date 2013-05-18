package com.example.pguide;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.EncodingUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class UserSelActivity extends Activity {

	private ListView spotList;
	private ImageButton makeBtn;

	private String Strname[] = new String[] { "故宫博物院", "北京动物园", "北京十三陵",
			"八达岭长城", "鸟巢", "北京颐和园", "天坛公园", "天安门广场", "慕田峪长城" };
	private String soc[] = new String[] { "4.4", "3.83", "4.26", "4.50",
			"4.32", "4.06", "3.50", "4.44", "3.56" };
	private int resp[] = new int[33];
	private int sz;

	private ProgressDialog progressdialog;
	private Handler myHand = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				progressdialog.dismiss();
				// MakeToast("Get info failed");
				break;
			case 1:
				progressdialog.dismiss();
				Intent intent = new Intent(UserSelActivity.this,
						MapActivity.class);
				intent.putExtra("spot", gres);
				startActivity(intent);
				finish();
				// /intent
				// ~
				// MakeToast(""+gres);

				break;
			default:
				break;
			}
		}
	};
	private String serverip;
	private int serverport;
	private String gres;

	private boolean fck[] = new boolean[33];
	private int accnt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usersel);

		accnt = 0;
		Arrays.fill(fck, false);

		Intent intent = this.getIntent();
		Bundle bd = intent.getExtras();
		String spotstr = bd.getString("spot");
		Log.d("Res", spotstr);
		sz = 0;
		int len = spotstr.length();
		for (int i = 0; i < len; i++) {
			if (spotstr.charAt(i) >= '0' && spotstr.charAt(i) <= '9') {
				resp[sz] = 0;
				int j;
				for (j = i; j < len; j++) {
					if (spotstr.charAt(j) >= '0' && spotstr.charAt(j) <= '9')
						resp[sz] = resp[sz] * 10 + spotstr.charAt(j) - '0';
					else
						break;
				}
				i = j;
				sz++;
			}
		}

		spotList = (ListView) findViewById(R.id.spotList);
		makeBtn = (ImageButton) findViewById(R.id.makebtn);

		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.vlist,
				new String[] { "hname", "hsc", "head", "cbox" }, new int[] {
						R.id.hname, R.id.hsc, R.id.head, R.id.cbox });
		spotList.setAdapter(adapter);
		spotList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// MakeToast("position: " + arg2);

				View vl = arg1;
				CheckBox cbox = (CheckBox) vl.findViewById(R.id.cbox);
				// MakeToast("position: " + arg2);
				// TODO Auto-generated method stub
				// CheckBox cbox = (CheckBox)findViewById(R.id.cbox);
				if (cbox.isChecked())
				{
					cbox.setChecked(false);
					accnt --;
					fck[arg2] = false;
				}
				else
				{
					cbox.setChecked(true);
					accnt ++;
					fck[arg2] = true;
				}
				// MakeToast(FinalCity[arg2]);
				//

			}

		});

		makeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String Trans = "C;";
				Trans += readFileData("Days");
				if ( accnt < 2 )
				{
					MakeToast("再多选点呗");
					return ;
				}
				for ( int i = 0; i < sz; i ++ )
				{
					if ( fck[i] ) Trans += "&" + resp[i];
				}
				
				Log.d("Trans",Trans);
				final String wrstring = Trans;
				progressdialog = ProgressDialog.show(UserSelActivity.this,
						"正在加载", "请稍等", true);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Socket socket = new Socket(serverip, serverport);
							PrintWriter writer = new PrintWriter(socket
									.getOutputStream());
							writer.println(wrstring);
							writer.flush();
							BufferedReader reader = new BufferedReader(
									new InputStreamReader(socket
											.getInputStream()));
							gres = reader.readLine();
							Thread.sleep(1000);
							myHand.sendEmptyMessage(1);
						} catch (Exception e) {
							// Log.d("Excep", e.getMessage());
							e.printStackTrace();
							myHand.sendEmptyMessage(0);
						}
					}
				}).start();
			}

		});
		
	}

	private void MakeToast(String str) {
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_sel, menu);
		return true;
	}

	private Object Query(int code) {
		switch (code) {
		case 0:
			return R.drawable.a0;
		case 1:
			return R.drawable.a1;
		case 2:
			return R.drawable.a2;
		case 3:
			return R.drawable.a3;
		case 4:
			return R.drawable.a4;
		case 5:
			return R.drawable.a5;
		case 6:
			return R.drawable.a6;
		case 7:
			return R.drawable.a7;
		case 8:
			return R.drawable.a8;
		case 9:
			return R.drawable.a0;
		default:
			return R.drawable.a0;
		}
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < sz; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("hname", Strname[resp[i] - 1]);
			map.put("hsc", soc[resp[i] - 1]);
			map.put("head", Query(resp[i] - 1));
			map.put("cbox", false);
			list.add(map);

		}

		return list;
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
