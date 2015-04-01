package goctruyen.android.com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class XemTruyenZip extends Activity implements OnTouchListener,
	OnClickListener {
	public static final String LOG_TAG = "TTTH";
	private ImageView img;
	private Bundle bundle;
	private String pathzip;
	@SuppressWarnings("rawtypes")
	private ArrayList arrImages;
	private int i = 1;
	private Bitmap result;
	private AlertDialog.Builder builderGoto;

	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;
	private ImageButton imgBtnNext;
	private ImageButton imgBtnBack;
	private AlertDialog.Builder builderMode;
	protected static final int Tu_do = 0;
	protected static final int Can_giua = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_xem_zip);
		img = (ImageView) findViewById(R.id.imageView1);
		img.setOnTouchListener(this);
		
		// ImageButton
		imgBtnNext = (ImageButton) findViewById(R.id.imgb_next);

		imgBtnBack = (ImageButton) findViewById(R.id.imgb_back);

		imgBtnBack.setOnClickListener(this);
		imgBtnNext.setOnClickListener(this);

		imgBtnNext.setVisibility(View.INVISIBLE);
		imgBtnBack.setVisibility(View.INVISIBLE);

	}
	// Nhảy đến trang nào đó
	public void processoptMnuGoto() {
		if (arrImages == null) {
			return;
		}
		final String[] itemsGoto = { "Go to First Page", "Go to Middle Page",
				"Go to Last Page" };

		builderGoto = new AlertDialog.Builder(this);
		builderGoto.setTitle("Go to ");
		builderGoto.setItems(itemsGoto, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				if (arrImages == null) {
					Toast.makeText(getApplicationContext(),
							"Please open zip file ", 3000).show();
				} else {
					
					if (item == 0) {
						String firstImages = Integer.toString(0);
						Toast.makeText(getApplicationContext(), firstImages,
								5000).show();
						img.setImageBitmap((Bitmap) arrImages.get(1));
					} else if (item == 1) {
						String haftImages = Integer.toString((int) arrImages
								.size() / 2);
						Toast.makeText(getApplicationContext(), haftImages,
								5000).show();
						img.setImageBitmap((Bitmap) arrImages
								.get((int) arrImages.size() / 2));
					} else if (item == 2) {
						String totalImages = Integer.toString(arrImages.size());
						Toast.makeText(getApplicationContext(), totalImages,
								5000).show();
						img.setImageBitmap((Bitmap) arrImages.get(arrImages
								.size() - 1));
					}
				}
			}
		});
		builderGoto.show();
	}
	// Liệt kê ảnh có trong zip
	public void getImageList() {
		if (pathzip == null) {
			return;
		}
		try {
			FileInputStream fis;
			fis = new FileInputStream(pathzip);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = null;
			result = null;

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inTempStorage = new byte[32 * 1024];
			options.inDither = false;
			options.inPurgeable = true;
			options.inPurgeable = true;
			arrImages = new ArrayList();
			while ((ze = zis.getNextEntry()) != null) {
				result = BitmapFactory.decodeStream(zis, null, options);
				arrImages.add(result);
			}

			zis.close();
			fis.close();
			Log.i(LOG_TAG, arrImages.toString());
			Log.i(LOG_TAG, Integer.toString(arrImages.size()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getNextImage() {
		if (arrImages == null) {
			return;
		}
		img.setImageBitmap((Bitmap) arrImages.get(i));
	}

	public void getBackImage() {
		if (arrImages == null) {
			return;
		}
		img.setImageBitmap((Bitmap) arrImages.get(i));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		getMenuInflater().inflate(R.menu.mainmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	//Menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case R.id.mnu_open:
			// To do open comics screen
			Intent i = new Intent(this, LoadSDcard.class);
			startActivity(i);
			return true;
		case R.id.mnu_goto:
			// method handler option menu
			processoptMnuGoto();
			return true;
		case R.id.mnu_mode:
			selectMode();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Log.i("TTTH", "onStop");
		if (arrImages != null) {
			arrImages.clear();
			arrImages = null;
		}
		super.onStop();
	}
//bắt đầu load truyện từ file đã chọn
	@Override
	protected void onStart() {
		super.onStart();
		Log.i("TTTH", "onStart");
		bundle = getIntent().getExtras();
		if (bundle != null) {
			pathzip = bundle.getString("path");
			getImageList();
			getNextImage();
		} else {
			Log.i(LOG_TAG, "bundle is null");
		}
	}
	
	//Hành động khi bấm vào màn hình
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		ImageView view = (ImageView) v;

//		dumpEvent(event);
		// Handle touch events here...
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
	//Chạm vào màn hình
		case MotionEvent.ACTION_DOWN:
			savedMatrix.set(matrix);
			start.set(event.getX(), event.getY());
			Log.d("TTTH", "mode=DRAG");
			mode = DRAG;
			break;
	//Nhấc tay khỏi màn hinh	
		case MotionEvent.ACTION_UP:
			imgBtnNext.setVisibility(View.VISIBLE);
			imgBtnBack.setVisibility(View.VISIBLE);
			break;
	// Hành động di chuyển ảnh
		case MotionEvent.ACTION_MOVE:
			//Ẩn nút Next - Previous
			imgBtnNext.setVisibility(View.INVISIBLE);
			imgBtnBack.setVisibility(View.INVISIBLE);
			//Hành động kéo ảnh
			if (mode == DRAG) {
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX() - start.x, event.getY()
						- start.y);
			//hành động zoom ảnh
			} else if (mode == ZOOM) {
				float newDist = spacing(event);
				Log.d("TTTH", "newDist=" + newDist);
				if (newDist > 10f) {
					matrix.set(savedMatrix);
					float scale = newDist / oldDist;
					matrix.postScale(scale, scale, mid.x, mid.y);
				}
			}
			break;
		}
		view.setImageMatrix(matrix);
		return true; // indicate event was handled
	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}
	
	//Chuyển trang Next - Previous
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (arrImages == null) {
			Toast.makeText(this, "please open zip file on sdcard", 2000).show();
			return;
		}
		if (view.getId() == R.id.imgb_next) {
			Log.i(LOG_TAG, "ImageButton NEXT clicked --!! ");
			i = i + 1;
			getNextImage();
			if (i == arrImages.size() - 1) {
				i = 1;
				getNextImage();
			}
		} else if (view.getId() == R.id.imgb_back) {
			Log.i(LOG_TAG, "ImageButton BACK clicked --!! ");
			i = i - 1;
			getBackImage();
			if (i == 0) {
				i = arrImages.size() - 1;
				getBackImage();
			}
		}
	}
	// Chế độ căn chỉnh khung
	public void selectMode() {
		if(arrImages == null){
			return;
		}
		final String[] mode = { "TỰ DO", "CĂN GIỮA"};
		builderMode = new AlertDialog.Builder(this);
		builderMode.setTitle("Chế độ");
		builderMode.setItems(mode, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.i("TTTH", "Items of AlertDialog Mode selected !!!");
				if (which == Tu_do) {
					Log.i("TTTH", "Item FIT_MATRIX has selected");
					img.setScaleType(ScaleType.MATRIX);
				} else if (which == Can_giua) {
					Log.i("TTTH", "Item FIT_CENTER has selected");
					img.setScaleType(ScaleType.FIT_CENTER);
				}
			}
		});
		builderMode.show();

	}
}
