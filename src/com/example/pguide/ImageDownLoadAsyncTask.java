package com.example.pguide;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ImageDownLoadAsyncTask extends AsyncTask<Void, Void, Bitmap> {
	private String imagePath;
	private ImageView imageView;
	private static final String ALBUM_PATH = "/sdcard/pb";
	private Context context;
	private AssetManager assetManager;
	private int Image_width;
	private final String file = "images/";
	private LinearLayout progressbar;
	private TextView loadtext;


	public ImageDownLoadAsyncTask(Context context, String imagePath,
			ImageView imageView, int Image_width) {
		this.imagePath = imagePath;
		this.imageView = imageView;
		this.context = context;
		assetManager = this.context.getAssets();
		this.Image_width = Image_width;
	}

	public void setLoadtext(TextView loadtext) {
		this.loadtext = loadtext;
	}

	public void setProgressbar(LinearLayout progressbar) {
		this.progressbar = progressbar;
	}

	@Override
	protected Bitmap doInBackground(Void... params) {

		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = false;
			InputStream inputStream = assetManager.open(file + imagePath);
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null,
					options);
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap drawable) {
		// TODO Auto-generated method stub
		super.onPostExecute(drawable);
		if (drawable != null) {
			LayoutParams layoutParams = imageView.getLayoutParams();
			int height = drawable.getHeight();
			int width = drawable.getWidth();
			layoutParams.height = (height * Image_width) / width;

			imageView.setLayoutParams(layoutParams);
			imageView.setImageBitmap(drawable);
		}
		if (progressbar.isShown() || loadtext.isShown()) {
			progressbar.setVisibility(View.GONE);
			loadtext.setVisibility(View.GONE);
		}

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (!loadtext.isShown()) {
			loadtext.setVisibility(View.VISIBLE);
		}

	}
}