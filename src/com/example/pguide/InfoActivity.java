package com.example.pguide;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.http.util.EncodingUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity<Progressdialog> extends Activity {
	private ProgressDialog progressdialog;
	private RatingBar ratingbar;
	private ImageView imageView1;
	
	private TextView spotname, tickp, weather, indoc;

	private String serverip;
	private int serverport;
	private String gres;
	private Handler myHand = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				progressdialog.dismiss();
				MakeToast("Get info failed");
				break;
			case 1:
				progressdialog.dismiss();	
				//MakeToast(gres);
				Log.d("detail",gres);
				String str[] = gao(gres + "&");
				
				spotname.setText(Cname[Integer.valueOf(str[0]) - 1]);
				weather.setText(readFileData("Weather"));
				Query((int)(Integer.valueOf(str[0]) - 1));
				indoc.setText(str[8]);
				
				Double da = Double.valueOf(str[5]);
				Double db = Double.valueOf(str[6]);
				ratingbar.setRating((float)(da/db));
				
				tickp.setText("门票:    " + str[4]+"元");
				
				break;
			default:
				break;
			}
		}
	};
	
	private void Query(int code) {
		switch (code) {
		case 0:
			imageView1.setImageResource(R.drawable.a0);break;
		case 1:
			imageView1.setImageResource(R.drawable.a1);break;
		case 2:
			imageView1.setImageResource(R.drawable.a2);break;
		case 3:
			imageView1.setImageResource(R.drawable.a3);break;
		case 4:
			imageView1.setImageResource(R.drawable.a4);break;
		case 5:
			imageView1.setImageResource(R.drawable.a5);break;
		case 6:
			imageView1.setImageResource(R.drawable.a6);break;
		case 7:
			imageView1.setImageResource(R.drawable.a7);break;
		case 8:
			imageView1.setImageResource(R.drawable.a8);break;
		case 9:
			imageView1.setImageResource(R.drawable.a0);break;
		default:
			imageView1.setImageResource(R.drawable.a0);
		}
	}
	private String[] gao(String str)
	{
		String res[] = new String[9];
		for ( int i = 0; i<9;i++ )
		{
			int ats = str.indexOf('&');
			res[i] = str.substring(0, ats);
			str = str.substring(ats + 1, str.length());
			//Log.d("detailz",res[i]+"," +str);
		}
		return res;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		spotname = (TextView)findViewById(R.id.spotname);
		tickp = (TextView)findViewById(R.id.tickp);
		weather = (TextView)findViewById(R.id.weather);
		indoc = (TextView)findViewById(R.id.indoc);
		imageView1 = (ImageView)findViewById(R.id.imageView1);
		ratingbar = (RatingBar) findViewById(R.id.ratingbar);

		serverip = getString(R.string.IP);
		serverport = new Integer(getString(R.string.PO));
		
		Intent intent = this.getIntent();
		Bundle bd = intent.getExtras();
		String tmp = bd.getString("idx");
		final String wrstring = "B;"+tmp;
		Toast.makeText(getBaseContext(), "Come in" + tmp , Toast.LENGTH_SHORT).show();
		progressdialog = ProgressDialog.show(InfoActivity.this,
				"正在加载", "请稍等", true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Log.d("in?", "??");
					Socket socket = new Socket(serverip, serverport);

					Log.d("in?", "??");
					PrintWriter writer = new PrintWriter(socket
							.getOutputStream());
					
					writer.println(wrstring);
					writer.flush();

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(socket
									.getInputStream()));
					gres = reader.readLine();
					Log.d("nininininini???", gres);
					
					//Thread.sleep(1000);
					myHand.sendEmptyMessage(1);
				} catch (Exception e) {
					// Log.d("Excep", e.getMessage());
					e.printStackTrace();
					myHand.sendEmptyMessage(0);
				}
			}
		}).start();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}

	private void MakeToast(String str) {
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
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
	
	
	
	public String Cname[] = new String[] {
			"故宫博物院",
			"北京动物园",
			"北京十三陵",
			"八达岭长城",
			"国家体育场",
			"北京颐和园",
			"天坛公园",
			"天安门广场",
			"慕田峪长城"
	};
	public double Lat[] = new double[] {
			39.9164,
			39.9388,
			40.2721,
			40.3539,
			39.9929,
			39.9996,
			39.8818,
			39.9060,
			40.4319,
	
	};
	public double Lng[] = new double [] {
			116.3971,
			116.3402,
			116.2248,
			116.0178,
			116.3965,
			116.2739,
			116.4091,
			116.3976,
			116.5583
	};
}
