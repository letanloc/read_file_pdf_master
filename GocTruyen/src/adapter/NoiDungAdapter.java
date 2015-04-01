package adapter;

import goctruyen.android.com.R;

import java.util.List;

import model.NoiDung;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoiDungAdapter extends ArrayAdapter<NoiDung>{
	public NoiDungAdapter(Context context, List<NoiDung> reports) {
		super(context, R.layout.item_noi_dung, reports);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		NoiDung vl = getItem(position);
		
		LayoutInflater inflater =((Activity) getContext()).getLayoutInflater();
		View row = inflater.inflate(R.layout.item_noi_dung, null, true);
		
		TextView lblTenTruyen = (TextView) row.findViewById(R.id.lblTenTruyen);
		TextView lblTG = (TextView) row.findViewById(R.id.lblTacGia);
		TextView lblQG = (TextView) row.findViewById(R.id.lblQuocGia);
		TextView lblTL = (TextView) row.findViewById(R.id.lblTheLoai);
		ImageView lblHi = (ImageView) row.findViewById(R.id.imgNd);
		
		
		lblTenTruyen.setText(vl.TenTruyen);
		lblTG.setText(vl.MaTG);
		lblQG.setText(vl.QuocGia);
		lblTL.setText(vl.MaTL);
		lblHi.setImageResource(vl.getHinh());
		
//		lblFile.setVisibility(View.INVISIBLE);
		
		return row;
	}
}

