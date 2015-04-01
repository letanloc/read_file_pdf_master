package goctruyen.android.com;

import java.util.List;

import model.TheLoai;
import DAO.NoiDungDAO;
import DAO.TheLoaiDAO;
import adapter.NoiDungAdapter;
import adapter.TheLoaiUserAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TheLoaiTruyen extends ActionBarActivity {
	TheLoaiDAO daoTL;
	TheLoaiUserAdapter adapterTL;
	List<TheLoai> lstTheLoai;
	
	NoiDungDAO daoND;
	NoiDungAdapter adapterND;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		
		ListView lvTLUser = (ListView)findViewById(R.id.lvTheLoaiUser);
		final TextView lblTenTL = (TextView) findViewById(R.id.lblTenTL);
		// Load dữ liệu thể loại lên List
		daoTL = new TheLoaiDAO(this);
		lstTheLoai = daoTL.getTheLoai();
		adapterTL = new TheLoaiUserAdapter(this, lstTheLoai);
		lvTLUser.setAdapter(adapterTL);
		
		lvTLUser.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
					String matl = lstTheLoai.get(position).MaTL;

					Intent in = new Intent(TheLoaiTruyen.this, ListTruyen.class);
					Bundle b = new Bundle();
					
					b.putString("matl", matl);
					in.putExtra("pos", b);
					startActivity(in);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phan_loai_truyen, menu);
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
