package goctruyen.android.com;

import java.net.URISyntaxException;
import java.util.List;

import model.NoiDung;
import model.PhanHoi;
import model.TacGia;
import model.TheLoai;
import DAO.NoiDungDAO;
import DAO.PhanHoiDAO;
import DAO.TacGiaDAO;
import DAO.TheLoaiDAO;
import adapter.NoiDungAdapter;
import adapter.PhanHoiAdapter;
import adapter.TacGiaAdapter;
import adapter.TheLoaiAdapter;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class QuanLyAdmin extends ActionBarActivity {
	NoiDungDAO daoND;
	TheLoaiDAO daoTL;
	PhanHoiDAO daoPH;
	TacGiaDAO daoTG;
	
	ListView lvND,lvTL,lvTG,lvPH;
	NoiDungAdapter adapterND;
	TheLoaiAdapter adapterTL;
	TacGiaAdapter adapterTG;
	PhanHoiAdapter adapterPH;
	
	List<TheLoai> lstTheLoai;
	List<TacGia> lstTacGia;
	
	ArrayAdapter<String> adaTheLoai,adaTacGia;
	Spinner spnTheLoai,spnTacGia;
	EditText edtTitle,edtFile,edtQuocGia,edtHinh,edtMaNd;
	
	private int maND;
	private static final int FILE_SELECT_CODE = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		
		daoND = new NoiDungDAO(this);
		daoTL = new TheLoaiDAO(this);
		daoTG = new TacGiaDAO(this);
		daoPH = new PhanHoiDAO(this);
		
		spnTheLoai = (Spinner)findViewById(R.id.spnTheLoai);
		spnTacGia = (Spinner)findViewById(R.id.spnTacGia);

		edtTitle = (EditText)findViewById(R.id.edtTitle);
		edtFile = (EditText)findViewById(R.id.edtFileAdmin);
		edtHinh = (EditText)findViewById(R.id.edtHinh);
		edtQuocGia = (EditText)findViewById(R.id.edtQuocGia);
         
         Button btnThem = (Button)findViewById(R.id.btnThem);
         final Button btnXoa = (Button)findViewById(R.id.btnXoa);
         final Button btnSua = (Button)findViewById(R.id.btnSua);
         final Button btnXem = (Button)findViewById(R.id.btnXem);
        // Set Spinner Thể loại
         spnTL();
 		//Spinner Tác giả
 		spnTG();
 		
         btnXoa.setEnabled(false);
         btnSua.setEnabled(false);
         
         btnThem.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try{
						
						final String vlTL = spnTheLoai.getSelectedItem().toString();
						final String vlTG = spnTacGia.getSelectedItem().toString();
						
					 	NoiDung nd = new NoiDung();
					 	
					 	nd.TenTruyen = edtTitle.getText().toString();
					 	nd.setFile(getResources().getIdentifier("goctruyen.android.com:drawable/"+edtFile.getText().toString(), null, null));
					 	nd.setHinh(getResources().getIdentifier("goctruyen.android.com:drawable/"+edtHinh.getText().toString(), null, null));
					 	nd.MaTL = vlTL;
					 	nd.MaTG = vlTG;
					 	nd.QuocGia = edtQuocGia.getText().toString();
						
					 	daoND.insert(nd);
					 	Toast.makeText(QuanLyAdmin.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
					 	//load dữ liệu lên ListView
					 	loadND();
					 }catch(Exception e){
						
					 		}
				}
				
         });
         btnXem.setOnClickListener(new OnClickListener() {
			
        	 @Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				final Dialog di = new Dialog(QuanLyAdmin.this);
 				di.setContentView(R.layout.admin_list_noidung);
 				di.setTitle("Danh sách truyện");
 				
 				lvND= (ListView)di.findViewById(R.id.lvND);
 				//load dữ liệu lên ListView
 				loadND();
 				lvND.setOnItemClickListener(new OnItemClickListener() {

 					@Override
 					public void onItemClick(AdapterView<?> arg0, View arg1,
 							int position, long arg3) {
 						
 						// TODO Auto-generated method stub
 						NoiDung nd = adapterND.getItem(position);
 						
 						edtTitle.setText(nd.getTenTruyen());
 						edtFile.setText(nd.getFile());
 						edtHinh.setText(nd.getHinh());
 						
 						edtQuocGia.setText(nd.getQuocGia());
 						
 						 btnXoa.setEnabled(true);
 				         btnSua.setEnabled(true);
 						di.dismiss();
 					}
 					
 				});
				
				di.show();
			}
		});
         
         btnSua.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					final String vlTL = spnTheLoai.getSelectedItem().toString();
					final String vlTG = spnTacGia.getSelectedItem().toString();
					
					try{
					NoiDung nd = new NoiDung();
					
				 	nd.TenTruyen = edtTitle.getText().toString();
				 	nd.setFile(getResources().getIdentifier("goctruyen.android.com:drawable/"+edtFile.getText().toString(), null, null));
				 	nd.setHinh(getResources().getIdentifier("goctruyen.android.com:drawable/"+edtHinh.getText().toString(), null, null));
				 	nd.MaTL = vlTL;
				 	nd.MaTG = vlTG;
				 	nd.QuocGia = edtQuocGia.getText().toString();
					
				 	daoND.update(nd);
				 	Toast.makeText(QuanLyAdmin.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
				 	loadND();
					}catch(Exception e){
						
					}
				}
			});
         btnXoa.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					try{
						daoND.delete(maND);
						Toast.makeText(QuanLyAdmin.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
						loadND();
					}catch(Exception e){
						
					}
				}
			});
/////////// Quản lý Thể loại
         
		Button btnAddTL = (Button)findViewById(R.id.btnAddTL);
		btnAddTL.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog di = new Dialog(QuanLyAdmin.this);
	            di.setContentView(R.layout.admin_the_loai);
	            di.setTitle("Quản lý thể loại");
	            // Lấy Control
	            final EditText edtMaTL = (EditText)di.findViewById(R.id.edtMaTL);
	            final EditText edtHinhTL = (EditText)di.findViewById(R.id.edtHinhTL);
	            final EditText edtTenTL = (EditText)di.findViewById(R.id.edtTenTL);
	            final Button btnThemTL = (Button)di.findViewById(R.id.btnThemTL);
	            final Button btnXoaTL = (Button)di.findViewById(R.id.btnXoaTL);
	            final Button btnSuaTL = (Button)di.findViewById(R.id.btnSuaTL);
	            
	            btnXoaTL.setEnabled(false);
	            btnSuaTL.setEnabled(false);
	            
	       		lvTL= (ListView)di.findViewById(R.id.lvTL);
	       		loadTL();
	       		lvTL.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						btnSuaTL.setEnabled(true);
						btnXoaTL.setEnabled(true);
						
						TheLoai tl = adapterTL.getItem(position);
						edtMaTL.setText(tl.getMaTL());
						edtHinhTL.setText(tl.getHinhTL());
						edtTenTL.setText(tl.getTenTL());
					}
				});
						
;
	            btnThemTL.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try{
							TheLoai tl = new TheLoai();
							tl.MaTL = edtMaTL.getText().toString();
							tl.setHinhTL(getResources().getIdentifier("goctruyen.android.com:drawable/"+edtHinhTL.getText().toString(), null, null));
							tl.TenTL = edtTenTL.getText().toString();
							if(edtMaTL.getText().toString().trim().equals("")){
								Toast.makeText(QuanLyAdmin.this, "Chưa nhập mã thể loại", Toast.LENGTH_SHORT).show();
							}else if(edtTenTL.getText().toString().trim().equals("")){
								Toast.makeText(QuanLyAdmin.this, "Chưa nhập tên thể loại", Toast.LENGTH_SHORT).show();
							} else {
								daoTL.insert(tl);
								//load dữ liệu lên ListView
								loadTL();
							 	Toast.makeText(QuanLyAdmin.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
							 	spnTL();
							}
						}catch(Exception e){
							 Toast.makeText(QuanLyAdmin.this, "Thất bại", Toast.LENGTH_SHORT).show();
						 		}
					}
				});
	            btnSuaTL.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						TheLoai tl = new TheLoai();
						tl.MaTL = edtMaTL.getText().toString();
						tl.setHinhTL(getResources().getIdentifier("goctruyen.android.com:drawable/"+edtHinhTL.getText().toString(), null, null));
						tl.TenTL = edtTenTL.getText().toString();
						
						daoTL.update(tl);
						loadTL();
					}
				});
	            btnXoaTL.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
					String MaTL = edtMaTL.getText().toString();
					daoTL.delete(MaTL);
					loadTL();
					}
				});
			di.show();
			}
		});
////////////// Quản Lý tác giả
		Button btnAddTG = (Button)findViewById(R.id.btnAddTG);
		btnAddTG.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog di = new Dialog(QuanLyAdmin.this);
	            di.setContentView(R.layout.admin_tac_gia);
	            di.setTitle("Quản lý tác giả");
	            // Lấy Control
	            final EditText edtMaTG = (EditText)di.findViewById(R.id.edtMaTG);
	            final EditText edtTenTG = (EditText)di.findViewById(R.id.edtTenTG);
	            final Button btnThemTG = (Button)di.findViewById(R.id.btnThemTG);
	            final Button btnXoaTG = (Button)di.findViewById(R.id.btnXoaTG);
	            final Button btnSuaTG = (Button)di.findViewById(R.id.btnSuaTG);
	            
	            btnXoaTG.setEnabled(false);
	            btnSuaTG.setEnabled(false);
	            
	       		lvTG= (ListView)di.findViewById(R.id.lvTG);
	       		//load dữ liệu lên ListView
	       		loadTG();
	       		lvTG.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						btnXoaTG.setEnabled(true);
			            btnSuaTG.setEnabled(true);
						
						TacGia tg = adapterTG.getItem(position);
						edtMaTG.setText(tg.getMaTG());
						edtTenTG.setText(tg.getTenTG());
					}
	       			
				});
	
	            btnThemTG.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try{
							TacGia tg = new TacGia();
							tg.MaTG = edtMaTG.getText().toString();
							tg.TenTG = edtTenTG.getText().toString();
							
							if(edtMaTG.getText().toString().trim().equals("")){
								Toast.makeText(QuanLyAdmin.this, "Chưa nhập mã tác giả", Toast.LENGTH_SHORT).show();
							}
							if(edtTenTG.getText().toString().trim().equals("")){
								Toast.makeText(QuanLyAdmin.this, "Chưa nhập tên tác giả", Toast.LENGTH_SHORT).show();
							} else {
								daoTG.insert(tg);
								//load dữ liệu lên ListView
								loadTG();
							 	Toast.makeText(QuanLyAdmin.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
							 	spnTG();
							}
						}catch(Exception e){
							 Toast.makeText(QuanLyAdmin.this, "Thất bại", Toast.LENGTH_SHORT).show();
						 		}
					}
				});
	            btnSuaTG.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						TacGia tg = new TacGia();
						tg.MaTG = edtMaTG.getText().toString();
						tg.TenTG = edtTenTG.getText().toString();
						
						daoTG.update(tg);
						loadTG();
					}
				});
	            btnXoaTG.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String matg = edtMaTG.getText().toString();
						
						daoTG.delete(matg);
						loadTG();
					}
				});
			di.show();
			}
		});
		Button btnQlPH = (Button)findViewById(R.id.btnQlPH);
		btnQlPH.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dialog di = new Dialog(QuanLyAdmin.this);
				di.setTitle("Phản hồi của người dùng");
				di.setContentView(R.layout.admin_list_phanhoi);
				
				lvPH = (ListView)di.findViewById(R.id.lvPH);
				//load dữ liệu lên ListView
				loadPH();
				lvPH.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int position, long arg3) {
						// TODO Auto-generated method stub
						PhanHoi ph = adapterPH.getItem(position);
						daoPH.delete(ph.MaPH);
						loadPH();
						return false;
					}
				
				});
				di.show();
			}
		});
		
	}
	
//	//Loa file
//    public void loadFile(View v){
////    	Intent i = new Intent(MainActivity.this, Details.class);
////    	startActivity(i);	
//    	 Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
//    	    intent.setType("*/*"); 
//    	    intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//    	    try {
//    	        startActivityForResult(
//    	                Intent.createChooser(intent, "Select a File to Upload"),
//    	                FILE_SELECT_CODE);
//    	    } catch (android.content.ActivityNotFoundException ex) {
//    	        // Potentially direct the user to the Market with a Dialog
//    	        Toast.makeText(this, "Please install a File Manager.", 
//    	                Toast.LENGTH_SHORT).show();
//    	    }
//    }
//    // Trả kết quả về sau khi chọn file
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case FILE_SELECT_CODE:
//            	EditText edtFile = (EditText)findViewById(R.id.edtFileAdmin);
//            if (resultCode == RESULT_OK) {
//                // Lấy ký tự(Uri) của file đã chọn
//                Uri uri = data.getData();
//                Log.d("TAG", "File Uri: " + uri.toString());
//                // Lấy đường dẫn
//				try {
//					String path = FileUtils.getPath(this, uri);
//					//Set đường dẫn vào EditText
//					edtFile.setText(path);
//				} catch (URISyntaxException e) {
//
//				}
//            }
//            break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
	// Spinner Thể loại
	public void spnTL(){
		lstTheLoai = daoTL.getTheLoai();
 		String[] arrTheLoai = new String[lstTheLoai.size()];
 		for(int i=0;i<lstTheLoai.size();i++){
 			arrTheLoai[i] = lstTheLoai.get(i).TenTL;
 		}
 		adaTheLoai = new ArrayAdapter<String>(this, 
 				android.R.layout.simple_spinner_item, arrTheLoai);
 		spnTheLoai.setAdapter(adaTheLoai);
	}
	//Spinner Tác giả
	public void spnTG(){
		lstTacGia = daoTG.getTacGia();
 		String[] arrTacGia = new String[lstTacGia.size()];
 		for(int i=0;i<lstTacGia.size();i++){
 			arrTacGia[i] = lstTacGia.get(i).TenTG;
 		}
 		adaTacGia = new ArrayAdapter<String>(this, 
 				android.R.layout.simple_spinner_item, arrTacGia);
 		spnTacGia.setAdapter(adaTacGia);
	}
	// load Danh sách Nội dung
	public void loadND(){
		daoND = new NoiDungDAO(this);
		List<NoiDung> nd = daoND.getNoiDung();
		adapterND = new NoiDungAdapter(this, nd);
		lvND.setAdapter(adapterND);
	}
	// load danh sách thể loại
	public void loadTL(){
		daoTL = new TheLoaiDAO(this);
		List<TheLoai> tl = daoTL.getTheLoai();
		adapterTL = new TheLoaiAdapter(this, tl);
		lvTL.setAdapter(adapterTL);
	}
	//load danh sách tác giả
	public void loadTG(){
		daoTG = new TacGiaDAO(this);
		List<TacGia> tg = daoTG.getTacGia();
		adapterTG = new TacGiaAdapter(this, tg);
		lvTG.setAdapter(adapterTG);
	}
	//load ds phản hồi
		public void loadPH(){
			daoPH = new PhanHoiDAO(this);
			List<PhanHoi> ph = daoPH.getPhanHoi();
			adapterPH = new PhanHoiAdapter(this, ph);
			lvPH.setAdapter(adapterPH);
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_management, menu);
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
