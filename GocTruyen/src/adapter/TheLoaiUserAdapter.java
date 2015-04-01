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

public class TheLoaiUserAdapter extends ArrayAdapter<TheLoai>{
	public TheLoaiUserAdapter(Context context, List<TheLoai> reports) {
		super(context, R.layout.item_the_loai_user, reports);
	}
	
	/**
	 * Duoc goi de lay View cua moi item tren ListView 
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		TheLoai vl = getItem(position);
		
		LayoutInflater inflater =((Activity) getContext()).getLayoutInflater();
		View row = inflater.inflate(R.layout.item_the_loai_user, null, true);
		
		ImageView imgTL = (ImageView) row.findViewById(R.id.imgTlUser);
		TextView lblTenTL = (TextView) row.findViewById(R.id.lblTenTruyenUser);
		
		imgTL.setImageResource(vl.getHinhTL());
		lblTenTL.setText(vl.TenTL);

		return row;
	}
}