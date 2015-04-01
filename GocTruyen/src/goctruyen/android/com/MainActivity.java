package goctruyen.android.com;


import java.util.HashMap;

import model.PhanHoi;
import DAO.PhanHoiDAO;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView.OnSliderClickListener;
import com.daimajia.slider.library.SliderTypes.TextSliderView;


public class MainActivity extends ActionBarActivity implements  BaseSliderView.OnSliderClickListener {
	EditText txtUser,txtPass;
	EditText edtName,edtEmail,edtFb;
	RadioGroup radGroup;
	PhanHoiDAO daoPH;
	private SliderLayout mDemoSlider;
	private static final int FILE_SELECT_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * Code Sile
         */
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Doremon",R.drawable.doremin);
        file_maps.put("Conan",R.drawable.conan);
        file_maps.put("Kinh Di",R.drawable.skull);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.getBundle()
                    .putString("extra",name);

           mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        
        /// End Slide
        
        
        daoPH = new PhanHoiDAO(this);
        // Mở Danh mục (thể loại) truyện
        Button btnDM = (Button)findViewById(R.id.btnDanhMuc);
        btnDM.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, TheLoaiTruyen.class);
				startActivity(i);
			}
		});

    }
    //Load file từ thẻ
    public void loadFile(View v){
    	Intent i = new Intent(this, LoadSDcard.class);
		startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    // Action Bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	    	int id = item.getItemId();
	    	if (id == R.id.action_feedback){
	    		//User gửi phản hồi
	    		final Dialog dia = new Dialog(MainActivity.this);
	            dia.setContentView(R.layout.feedback_user);
	            dia.setTitle("Phản hồi về ứng dụng");
	            
	            final RadioGroup  radGroup = (RadioGroup)dia.findViewById(R.id.radioGroup1);
	            int selectedId = radGroup.getCheckedRadioButtonId();
				final RadioButton radioDG = (RadioButton) dia.findViewById(selectedId);
				
	            edtName = (EditText)dia.findViewById(R.id.edtFbName);
			    edtEmail = (EditText)dia.findViewById(R.id.edtFbEmail);
			    edtFb = (EditText)dia.findViewById(R.id.edtFb);
			    
			    
			    Button btnSend = (Button)dia.findViewById(R.id.btnFb);
			    
			    btnSend.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						try{
							if(edtName.getText().toString().trim().equals("") && 
									edtFb.getText().toString().trim().equals("") &&
									edtEmail.getText().toString().trim().equals("")){
								Toast.makeText(MainActivity.this, "Bạn chưa nhập nội dung",
										Toast.LENGTH_SHORT).show();
							}else if(edtName.getText().toString().trim().equals("")){
								Toast.makeText(MainActivity.this, "Bạn chưa nhập Tên",
										Toast.LENGTH_SHORT).show();
							}else if(edtFb.getText().toString().trim().equals("")){
								Toast.makeText(MainActivity.this, "Bạn chưa nhập Ý kiến",
										Toast.LENGTH_SHORT).show();
							}else {
								PhanHoi ph = new PhanHoi();
								ph.Ten = edtName.getText().toString();
								ph.Email = edtEmail.getText().toString();
								ph.DanhGia = radioDG.getText().toString();
								ph.NoiDung = edtFb.getText().toString();
								
								daoPH.insert(ph);
								
								Toast.makeText(MainActivity.this, "Cảm ơn bạn đã chia sẻ ý kiến!",
										Toast.LENGTH_LONG).show();
								dia.dismiss();
							}
						}catch(Exception e){
							Toast.makeText(MainActivity.this, "Gửi không thành công",
									Toast.LENGTH_LONG).show();
						}
					}
				});
			    dia.show();
	    	}
	    	
	        if (id == R.id.action_login) {
	        	// Đăng nhập admin
	        	final Dialog dia = new Dialog(MainActivity.this);
	            dia.setContentView(R.layout.activity_login_admin);
	            dia.setTitle("Login Admin");
	            
	            
	            txtUser = (EditText)dia.findViewById(R.id.edtID);
			    txtPass = (EditText)dia.findViewById(R.id.edtPass);
			    Button btnLogin = (Button)dia.findViewById(R.id.btnLoginAdmin);
			    
			    btnLogin.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(txtUser.getText().toString().trim().equals("") && 
								txtPass.getText().toString().trim().equals("")){
							Toast.makeText(MainActivity.this, "Bạn chưa nhập Username hoặc Password",
									Toast.LENGTH_SHORT).show();
						}else if(!(txtUser.getText().toString().equals("admin"))){
							Toast.makeText(MainActivity.this, "Username ko đúng!",
									Toast.LENGTH_SHORT).show();
						}else if(!(txtPass.getText().toString().equals("admin"))){
							Toast.makeText(MainActivity.this, "Password ko đúng!",
									Toast.LENGTH_SHORT).show();
						}else if(txtUser.getText().toString().trim().equals("admin") &&
								txtPass.getText().toString().trim().equals("admin")){					
							Intent i = new Intent(MainActivity.this, QuanLyAdmin.class);
							startActivity(i);
							dia.dismiss();
					}
						
					}
				});
			    dia.show();
	            return true;
	        }
    		
    		
        return super.onOptionsItemSelected(item);
    }
	@Override
	public void onSliderClick(BaseSliderView slider) {
		// TODO Auto-generated method stub
		
	}
    
}

