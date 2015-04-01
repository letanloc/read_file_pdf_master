package adapter;

import goctruyen.android.com.R;

import java.util.List;

import model.PhanHoi;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PhanHoiAdapter extends ArrayAdapter<PhanHoi>{
	public PhanHoiAdapter(Context context, List<PhanHoi> reports) {
		super(context, R.layout.item_phan_hoi, reports);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		PhanHoi vl = getItem(position);
		
		LayoutInflater inflater =((Activity) getContext()).getLayoutInflater();
		View row = inflater.inflate(R.layout.item_phan_hoi, null, true);
		
		TextView lblTen = (TextView) row.findViewById(R.id.lblTenPH);
		TextView lblEmail = (TextView) row.findViewById(R.id.lblEmailPH);
		TextView lblDanhGia = (TextView) row.findViewById(R.id.lblDanhGiaPH);
		TextView lblFH = (TextView) row.findViewById(R.id.lblNdFH);
		
		
		lblTen.setText(vl.Ten);
		lblEmail.setText(vl.Email);
		lblDanhGia.setText(vl.DanhGia);
		lblFH.setText(vl.NoiDung);

		return row;
	}
}
