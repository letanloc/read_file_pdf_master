package adapter;

import goctruyen.android.com.R;

import java.util.List;

import model.TacGia;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TacGiaAdapter extends ArrayAdapter<TacGia>{
	public TacGiaAdapter(Context context, List<TacGia> reports) {
		super(context, R.layout.item_tac_gia, reports);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		TacGia vl = getItem(position);
		
		LayoutInflater inflater =((Activity) getContext()).getLayoutInflater();
		View row = inflater.inflate(R.layout.item_tac_gia, null, true);
		
		TextView lblMaTL = (TextView) row.findViewById(R.id.lblMaTG);
		TextView lblTenTL = (TextView) row.findViewById(R.id.lblTenTG);
		
		lblMaTL.setText(vl.MaTG);
		lblTenTL.setText(vl.TenTG);

		return row;
	}
}

