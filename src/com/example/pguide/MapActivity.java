package com.example.pguide;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapActivity extends FragmentActivity implements LocationListener, OnMarkerClickListener, OnInfoWindowClickListener {
	
	GoogleMap gmap;
	private ProgressDialog progressdialog;
	Polyline polyline;

	private CameraPosition cameraPosition;
	double slat = 0;
	double slng = 0;
	double endlat = 0;
	double endlng = 0;
	double myLat = 0;
	double myLng = 0;
	List<List<LatLng>> inAT;
	int nowch;
	List<LatLng> latlngAll;
	List<Integer> idAll;
	List<LatLng>list;
	List<LatLng>Glist;
	List<Marker>marklist;
	List< List<LatLng>> road;
	List<List<Integer>> inid;
	int nowroad;
	ImageButton nextroad;
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


	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {						
			case 1:	
                progressdialog.dismiss();
				if(polyline!=null){
					polyline.remove();
					gmap.clear();
				}
				marklist.clear();
				
                for(int i = 0; i < latlngAll.size(); i++) {
                	addMaker(latlngAll.get(i).latitude, latlngAll.get(i).longitude, Cname[idAll.get(i) - 1], false);
                }
                for(int i = 0; i < idAll.size(); i++) {
                	Log.d("FINALDENIG", "" + idAll.get(i));
                	
                }
                LatLng last = null;
//                for (int i = 0; i < Glist.size() - 1; i++) {
//					LatLng src = Glist.get(i);
//					LatLng dest = Glist.get(i + 1);
//					last = dest;
//					polyline = gmap.addPolyline(new PolylineOptions()
//							.add(new LatLng(src.latitude, src.longitude),
//									new LatLng(dest.latitude, dest.longitude))
//							.width(5).color(Color.RED).geodesic(true));
//				}
                
//				for (int i = 0; i < list.size() - 1; i++) {
//					LatLng src = list.get(i);
//					LatLng dest = list.get(i + 1);
//					last = dest;
//					polyline = gmap.addPolyline(new PolylineOptions()
//							.add(new LatLng(src.latitude, src.longitude),
//									new LatLng(dest.latitude, dest.longitude))
//							.width(5).color(Color.GREEN).geodesic(true));
//				}
				for(int i = 0; i < road.size(); i++) {
					if(i == nowroad) continue;
					for(int j = 0; j < road.get(i).size() - 1; j++) {
						LatLng src = road.get(i).get(j);
						
						LatLng dest = road.get(i).get(j + 1);
						
						polyline = gmap.addPolyline(new PolylineOptions()
						.add(new LatLng(src.latitude, src.longitude),
								new LatLng(dest.latitude, dest.longitude))
						.width(5).color(Color.GREEN).geodesic(true));
					}
				}
				for(int j = 0; j < road.get(nowroad).size() - 1; j++) {
					LatLng src = road.get(nowroad).get(j);
					
					LatLng dest = road.get(nowroad).get(j + 1);
					
					polyline = gmap.addPolyline(new PolylineOptions()
					.add(new LatLng(src.latitude, src.longitude),
							new LatLng(dest.latitude, dest.longitude))
					.width(5).color(Color.RED).geodesic(true));
				}
				double tmplat = latlngAll.get(nowroad).latitude + latlngAll.get(nowroad + 1).latitude;
				tmplat /= 2;
				double tmplng = latlngAll.get(nowroad).longitude + latlngAll.get(nowroad + 1).longitude;
				tmplng /= 2;
				move((new LatLng(tmplat, tmplng)));
				
				
				break;
			}
		};
	};
	public void init() {

		latlngAll = new ArrayList<LatLng>();
		idAll = new ArrayList<Integer> ();
		
		list = new ArrayList<LatLng>();
		Glist = new ArrayList<LatLng>();
		marklist = new ArrayList<Marker>();
		
		road = new ArrayList<List<LatLng>>();
		inAT = new ArrayList<List<LatLng>>();
		inid = new ArrayList<List<Integer>>();
		
		nowroad = 0;
		nowch = 0;
		
	}
	public void move(LatLng vx) {
		cameraPosition = new CameraPosition.Builder()
		.target(vx)
		.zoom(10) // 缩放比例
		.bearing(0) // Sets the orientation of the camera to east
		.tilt(20) // Sets the tilt of the camera to 30 degrees
		.build(); // Creates a CameraPosition from the builder
		gmap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		//gmap.moveCamera(CameraUpdateFactory.newLatLng(vx));
		//gmap.animateCamera(CameraUpdateFactory.zoomTo(10));
	}
	public void add(int t) {
		List<LatLng> lis = new ArrayList<LatLng>();
		if(t == 0) {
			lis.add(new LatLng(39.9164, 116.3971));
			lis.add(new LatLng(39.9388, 116.3402));
			lis.add(new LatLng(39.9927, 116.3903));
			lis.add(new LatLng(40.3539, 116.0178));
		} else {
			lis.add(new LatLng(39.91, 116.39));
			lis.add(new LatLng(39.93, 116.34));
			lis.add(new LatLng(39.99, 116.22));
			
		}
		inAT.add(lis);
		
	}
	private String gres;
	public void getinAT() {
		
		String[] tmp = gres.split("&");
		for(int i = 0; i < tmp.length; i++) {
			Log.d("tmptmptmp", tmp[i]);
		}
		for(int i = 0; i < tmp.length; i++) {

			List<LatLng> lis = new ArrayList<LatLng>();
			List<Integer> idx = new ArrayList<Integer>();
			
			int x = 0;
			for(int j = 0; j < tmp[i].length(); j++) {
				if(tmp[i].charAt(j) == ',') {
					if(x != 0) {
						idx.add(x);
						lis.add(new LatLng(Lat[x - 1], Lng[x - 1]));
						Log.d("hehe", ""+x);
					}
					x = 0;
				} else {
					x = x * 10 + (tmp[i].charAt(j) - '0');
				}
			}
			if(x != 0) {
				idx.add(new Integer(x));
				lis.add(new LatLng(Lat[x - 1], Lng[x - 1]));
			}
			String ss = "";
			for(int j = 0; j < idx.size(); j++) {
				ss += ""+ idx.get(j);
				
			}
			Log.d("S", ss);
			inAT.add(lis);
			inid.add(idx);
			
		}
		for(int i = 0; i < inid.size(); i++) {
			for(int j = 0; j < inid.get(i).size(); j++) {
				Log.d("??????????????????", inid.get(i).get(j) + "");
			}
		}
		Log.d("---------------------", ""+inAT.size());
		
//		add(0);
//		add(1);
		
	}
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("ppp", "sss");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		init();
		nextroad  = (ImageButton)findViewById(R.id.nextroad);
		nextroad.setImageResource(R.drawable.next);
		
		
		Intent intent = this.getIntent();
		Bundle bd = intent.getExtras();
		gres = bd.getString("spot");
		Log.d("GRESSSSgfeafeafeafeafea", gres);
		
		
		getinAT();		//latlngAll.add(new LatLng(39.99, 116.39));
		//Toast.makeText(getApplicationContext(), ""+inAT.size(), Toast.LENGTH_SHORT).show();
		// Getting Google Play availability status
		
		nextroad.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(road.size() != 0) {
					nowroad ++;
					nowroad %= road.size();
				}
				mHandler.sendEmptyMessage(1);
				
			
			}

		});
		
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
        	
        	int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
            
        }else {	// Google Play Services are available	
        	
			// Getting reference to the SupportMapFragment of activity_main.xml
			SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
			gmap = fm.getMap();
			gmap.setMyLocationEnabled(true);
			
			gmap.setOnInfoWindowClickListener(this);
			
			 // Getting LocationManager object from System Service LOCATION_SERVICE
	        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
	
	        // Creating a criteria object to retrieve provider
	        Criteria criteria = new Criteria();
	
	        // Getting the name of the best provider
	        String provider = locationManager.getBestProvider(criteria, true);
	
	        // Getting Current Location
	        Location location = locationManager.getLastKnownLocation(provider);
	
	        if(location!=null){
	        	myLat = location.getLatitude();
	        	myLng = location.getLongitude();
	        }
	
	        locationManager.requestLocationUpdates(provider, 20000, 0, this);
			
			
			Log.d("...................", "in");
	        editpoint();
        }
        
		
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, Menu.FIRST + 1, 1, "下一条路线");
		menu.add(0, Menu.FIRST + 2, 2, "返回");
		return true;
	}

	// 菜单响应
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case Menu.FIRST + 1:
			// 定义输入框界面
			nowch++;
			nowch %= inAT.size();
			nowroad = 0;
			road.clear();
			
			editpoint();
			break;
		case Menu.FIRST + 2:
			//locationManager.removeUpdates(llistener);
			finish();
			break;
		}
		return true;
	}
	private void editpoint() {
		Log.d("edipoint", "in");
		Log.d("MYLATLNG", ""+myLat+","+myLng);
			progressdialog = ProgressDialog.show(MapActivity.this,
								"正在加载", "请稍等", true);
			list.clear();
			Glist.clear();
			latlngAll.clear();
			idAll.clear();
			
			for(int i = 0; i < inAT.get(nowch).size(); i++) {
				latlngAll.add(inAT.get(nowch).get(i));
				idAll.add(inid.get(nowch).get(i));
				
			}

			Log.d("SIZE", ""+nowch+","+inAT.size()+","+latlngAll.size());
						new Thread(new Runnable() {
							@Override
							public void run() {
								
								
								//int presize = Glist.size();
								//getDirection(0);
								//if(presize == Glist.size()) {
									//Glist.add(new LatLng(myLat, myLng));
									
									//Glist.add(latlngAll.get(0));
								//}
								
								// TODO Auto-generated method stub
								for(int i = 0; i < latlngAll.size() - 1; i++) {
									slat = latlngAll.get(i).latitude;
									slng = latlngAll.get(i).longitude;
									endlat = latlngAll.get(i + 1).latitude;
									endlng = latlngAll.get(i + 1).longitude;
									//int presize = road.size();
									getDirection(1);
									
									
									
//									if(presize == list.size()) {
//										list.add(latlngAll.get(i));
//										list.add(latlngAll.get(i + 1));
//									}
								}
								Log.d("Listsize", ""+list.size());
								mHandler.sendEmptyMessage(0);
								mHandler.sendEmptyMessage(1);
							}
						}).start();

	}
	private void getDirection(int op) {

		String DresultString = "";

		String Durl = "http://maps.google.com/maps/api/directions/json?origin="
				+ slat + "," + slng + "&destination=" + endlat + "," + endlng
				+ "&sensor=true&mode=driving";
		HttpClient Dclient = new DefaultHttpClient();
		HttpGet Dget = new HttpGet(Durl);

		HttpResponse response;

		try {
			response = Dclient.execute(Dget);
			HttpEntity Dentity = response.getEntity();
			BufferedReader DbuffReader = new BufferedReader(
					new InputStreamReader(Dentity.getContent()));
			StringBuffer DstrBuff = new StringBuffer();
			String Dresult = null;
			while ((Dresult = DbuffReader.readLine()) != null) {
				DstrBuff.append(Dresult);
			}
			DresultString = DstrBuff.toString();
			

		} catch (Exception e) {

		}
		try {
			final JSONObject jsonObject = new JSONObject(DresultString);
			JSONArray routeArray = jsonObject.getJSONArray("routes");
			JSONObject routes = routeArray.getJSONObject(0);
			JSONObject overviewPolylines = routes
					.getJSONObject("overview_polyline");
			String encodedString = overviewPolylines.getString("points");
			Log.d("test: ", encodedString);
			
			decodePoly(encodedString, op);
			

			

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Caught ArrayIndexOutOfBoundsException: "
					+ e.getMessage());
		}
	}
	private void decodePoly(String encoded, int op) {
		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((lat / 1E5), lng / 1E5);
			poly.add(p);
		}
		road.add(poly);
		
	}

	public void addMaker(double l1, double l2, String name, boolean move) {
		
		LatLng lates = new LatLng(l1, l2);
   
		//marklist.add(gmap.addMarker(new MarkerOptions().position(lates).title("Hello world").snippet("娆㈤ギ鏉ュ仛澶у疂鍋").icon(BitmapDescriptorFactory.fromResource(R.drawable.ww))));
		marklist.add(gmap.addMarker(new MarkerOptions().position(lates).title(name).snippet("欢迎光临")));

		if(move) {
			gmap.moveCamera(CameraUpdateFactory.newLatLng(lates));
			gmap.animateCamera(CameraUpdateFactory.zoomTo(9));
		}
	}


	public ProgressDialog getProgressdialog() {
		return progressdialog;
	}


	public void setProgressdialog(ProgressDialog progressdialog) {
		this.progressdialog = progressdialog;
	}


	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}


	public Handler getmHandler() {
		return mHandler;
	}


	public void setmHandler(Handler mHandler) {
		this.mHandler = mHandler;
	}

	@Override
	public void onInfoWindowClick(Marker marker) {

		for(int i = 0; i < marklist.size(); i++) {
			if(marker.equals(marklist.get(i))) {
				//Toast.makeText(getBaseContext(), "Click id ~ " + idAll.get(i) , Toast.LENGTH_SHORT).show();
				
				Intent it = new Intent(MapActivity.this, InfoActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("idx", ""+ idAll.get(i));
				it.putExtras(bundle);
				startActivity(it);
//				
				break;
			}
		}
        
	}
	@Override
	public boolean onMarkerClick(Marker arg0) {
		return false;
	}
}
