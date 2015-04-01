package goctruyen.android.com;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import model.NoiDung;
import DAO.NoiDungDAO;
import adapter.NoiDungAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListTruyen extends ActionBarActivity {
	NoiDungDAO daoND;
	NoiDungAdapter adapterND;
	private List<String> path = null;
	TextView pathTr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_truyen);
		
		pathTr = (TextView)findViewById(R.id.pathTruyen);
		pathTr.setVisibility(View.INVISIBLE);
		ListView lvNdUser = (ListView)findViewById(R.id.lvNDUser);
		
		// Load truyện 
		daoND = new NoiDungDAO(this);
		List<NoiDung> nd = daoND.getNoiDung();
		adapterND = new NoiDungAdapter(this, nd);
		lvNdUser.setAdapter(adapterND);
		
		//Click vào item trên list
		lvNdUser.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				NoiDung nd = adapterND.getItem(position);
				//Set đường dẫn vào textview
				pathTr.setText(nd.getFile());
				//Lấy đường dẫn từ TextView
				String a = pathTr.getText().toString();
				//Gửi đường dẫn file cho Activity đọc PDF
				Intent i = new Intent(ListTruyen.this, XemPDF.class);
				i.putExtra(XemPDF.EXTRA_PDFFILENAME,
						a);
				startActivity(i);
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_manga, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
