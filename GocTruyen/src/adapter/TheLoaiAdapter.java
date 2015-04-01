package adapter;

import goctruyen.android.com.R;

import java.util.List;

import model.TheLoai;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TheLoaiAdapter extends ArrayAdapter<TheLoai>{
	public TheLoaiAdapter(Context context, List<TheLoai> reports) {
		super(context, R.layout.item_the_loai, reports);
	}
	
	/**
	 * Duoc goi de lay View cua moi item tren ListView 
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		TheLoai vl = getItem(position);
		
		LayoutInflater inflater =((Activity) getContext()).getLayoutInflater();
		View row = inflater.inflate(R.layout.item_the_loai, null, true);
		
		ImageView imgTL = (ImageView) row.findViewById(R.id.imgTL);
		TextView lblTenTL = (TextView) row.findViewById(R.id.lblTenTL);
		
		imgTL.setImageResource(vl.getHinhTL());
		lblTenTL.setText(vl.TenTL);

		return row;
	}
}
