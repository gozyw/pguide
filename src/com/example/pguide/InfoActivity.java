package com.example.pguide;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.util.EncodingUtils;





import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity<Progressdialog> extends Activity  implements
LazyScrollView.OnScrollListener{
	
	
	
	//-----------------

	private LazyScrollView lazyScrollView;
	private LinearLayout waterfall_container;
	private ArrayList<LinearLayout> linearLayouts;

	private LinearLayout progressbar;

	private TextView loadtext;

	private AssetManager assetManager;

	private List<String> image_filenames; 
	private ImageDownLoadAsyncTask asyncTask;

	private int current_page = 0;
	private int count = 8;
	private int column = 2;

	private int item_width;
	private final String file = "images";
	//private Bundle savedInstanceState;

	public void initView() {
		setContentView(R.layout.main);
		lazyScrollView = (LazyScrollView) findViewById(R.id.waterfall_scroll);
		lazyScrollView.getView();
		lazyScrollView.setOnScrollListener(this);
		waterfall_container = (LinearLayout) findViewById(R.id.waterfall_container);
		progressbar = (LinearLayout) findViewById(R.id.progressbar);
		loadtext = (TextView) findViewById(R.id.loadtext);

		item_width = getWindowManager().getDefaultDisplay().getWidth() / column;
		linearLayouts = new ArrayList<LinearLayout>();

		for (int i = 0; i < column; i++) {
			LinearLayout layout = new LinearLayout(this);
			LinearLayout.LayoutParams itemParam = new LinearLayout.LayoutParams(
					item_width, LayoutParams.WRAP_CONTENT);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setLayoutParams(itemParam);
			linearLayouts.add(layout);
			waterfall_container.addView(layout);
		}

	}
	
	public void waterfall() {
		//super.onCreate(savedInstanceState);
		initView();
		assetManager = this.getAssets();
		try {
			image_filenames = Arrays.asList(assetManager.list(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		addImage(current_page, count);

	}

	private void addImage(int current_page, int count) {
		int j = 0;
		int imagecount = image_filenames.size();
		for (int i = current_page * count; i < count * (current_page + 1)
				&& i < imagecount; i++) {
			addBitMapToImage(image_filenames.get(i), j, i);
			j++;
			if (j >= column)
				j = 0;
		}

	}

	private void addBitMapToImage(String imageName, int j, int i) {
		ImageView imageView = getImageview(imageName);
		asyncTask = new ImageDownLoadAsyncTask(this, imageName, imageView,
				item_width);

		asyncTask.setProgressbar(progressbar);
		asyncTask.setLoadtext(loadtext);
		asyncTask.execute();

		imageView.setTag(i);
		linearLayouts.get(j).addView(imageView);

//		imageView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(Main.this,
//						"", Toast.LENGTH_SHORT)
//						.show();
//
//			}
//		});
	}


	public ImageView getImageview(String imageName) {
		BitmapFactory.Options options = getBitmapBounds(imageName);
		// 创建显示图片的对象
		ImageView imageView = new ImageView(this);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.FILL_PARENT);
		imageView.setLayoutParams(layoutParams);
		//
		imageView.setMinimumHeight(options.outHeight);
		imageView.setMinimumWidth(options.outWidth);
		imageView.setPadding(2, 0, 2, 2);
		imageView.setBackgroundResource(R.drawable.image_border);
		if (options != null)
			options = null;
		return imageView;
	}


	public BitmapFactory.Options getBitmapBounds(String imageName) {
		int h, w;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		InputStream is = null;
		try {
			is = assetManager.open(file + "/" + imageName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BitmapFactory.decodeStream(is, null, options);
		return options;

	}

	@Override
	public void onBottom() {
		addImage(++current_page, count);

	}

	@Override
	public void onTop() {

	}

	@Override
	public void onScroll() {

	}
	
	//这个是网上找到的优化内存的相关代码  一下不知道怎么融入进去  可
	//------------------
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
		
		
		
		imageView1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				waterfall();
			}
		});
		
		
		

		serverip = getString(R.string.IP);
		serverport = new Integer(getString(R.string.PO));
		
		Intent intent = this.getIntent();
		Bundle bd = intent.getExtras();
		String tmp = bd.getString("idx");
		final String wrstring = "B;"+tmp;
		//Toast.makeText(getBaseContext(), "Come in" + tmp , Toast.LENGTH_SHORT).show();
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
