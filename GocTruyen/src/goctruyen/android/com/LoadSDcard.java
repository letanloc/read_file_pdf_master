package goctruyen.android.com;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class LoadSDcard extends ListActivity {
	public static final String OPEN_LOG = "TTTH";
	private List<String> item = null;
	private List<String> path = null;
	private String root = "/sdcard";

	ListView lv_sdcard;
	private ImageView img;
	private ProgressDialog myProgressDialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_load_sdcard);
		img = (ImageView) findViewById(R.id.imageView1);
		lv_sdcard = getListView();
		getDir(root);
	}
	//Lấy đường dẫn
	private void getDir(String dirPath) {
		item = new ArrayList<String>();
		path = new ArrayList<String>();

		File f = new File(dirPath);
		File[] files = f.listFiles();

		if (!dirPath.equals(root)) {

			item.add(root);
			path.add(root);

			item.add("../");
			path.add(f.getParent());

		}

		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			path.add(file.getPath());
			if (file.isDirectory())
				item.add(file.getName() + "/");
			else
				item.add(file.getName());
		}

		ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, item);
		setListAdapter(fileList);
	}
	//Hành động click chọn file
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		File file = new File(path.get(position));

		if (file.isDirectory()) {
			if (file.canRead())
				getDir(path.get(position));
			else {
				new AlertDialog.Builder(this)
						.setIcon(R.drawable.ic_launcher)
						.setTitle(
								"[" + file.getName()
										+ "] Không thể đọc thư mục")
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
									}
								}).show();
			}
		} else {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			Uri uri = Uri.parse("file://" + file.getPath());
			String fname = file.getName();
			if (fname.endsWith(".pdf")) {
				Intent i = new Intent(LoadSDcard.this, XemPDF.class);
				i.putExtra(XemPDF.EXTRA_PDFFILENAME,
						file.getPath());
				startActivity(i);
				
			} else if (fname.endsWith(".zip")) {

				
	//Gửi đường dẫn file sang activity Đọc file
				
				Intent i = new Intent(this, XemTruyenZip.class);
				i.putExtra("path", file.getPath());
				final String strLength = Long.toString(file.length() / 1000);
				if (file.length() / 1000 > 1600) {
					Toast.makeText(
							getApplicationContext(),
							"Chọn file nhỏ hơn 1.5Mb",
							2000).show();
				} else {

					myProgressDialog = ProgressDialog.show(LoadSDcard.this,
							"Xin đợi..", "Đang tải dữ liệu", true);
					new Thread() {
						public void run() {
							try {
								Log.i("TTTH", strLength);
								sleep(5000);
							} catch (Exception e) {
								// TODO: handle exception
							}
							myProgressDialog.dismiss();
						}
					}.start();
					startActivity(i);
				}
			} else {

			}
		}
	}

}
