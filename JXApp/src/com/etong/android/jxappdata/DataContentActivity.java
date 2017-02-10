package com.etong.android.jxappdata;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.data.DataContentProvider;
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.frame.update.AppUpdateProvider;
import com.etong.android.frame.update.AppUpdateResultAction;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappdata.common.BaseActivity;
import com.etong.android.jxappdata.user.LoginActivity;
import com.etong.android.jxappdata.user.UserInfo;
import com.etong.android.util.SerializableObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 数据内容页面
 * @author Administrator
 *
 */
public class DataContentActivity extends BaseActivity {

	private int width;
	private int height;
	private String background;
	private String text;
	private int top;
	private int padding;
	private UserInfo userInfo;

	private RelativeLayout mMainLayout;

	private TextView titleName;
	private TextView titleNext;
	private ImageButton titleBack;
	private List<DataContent> dataList;
	private GridView listView;
	// List<String> list;
	private List list;

	private DataContentProvider mDataContentProvider;
	private TitleBar mTitleBar;
	private ImageView type_img;
	private TextView type_name;


	private ListAdapter<String> adapter;

	private JSONArray children;
	private int type;
	private static SharedPublisher mSharedPublisher=SharedPublisher.getInstance();
	 int modulestatus;
	 String[] icon;
	@Override
	protected void onInit(@Nullable Bundle savedInstanceState) {
		
		try {
			initData();

		} catch (Exception e) {
//			ActivitySkipUtil.skipActivity(this,WelcomeActivity.class);

			this.finish();
		}
		setContentView(R.layout.activity_data_content);
		mTitleBar = new TitleBar(this);
		mTitleBar.showNextButton(false);
//		mTitleBar.showNextButton(true);
		// 设置右边"按钮"的图片，无文字
//		mTitleBar.setNextButton("");
//		mTitleBar.getNextButton().setBackgroundResource(R.drawable.bt_more);
		mTitleBar.setTitle("数据内容");
		this.myTitle="数据内容";
//		mTitleBar.setNextOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				StatService.onEvent(DataContentActivity.this, "more", "更多", 1);
//				String [] text={"检测新版本","退出"};
//				 final List<String> items =Arrays.asList(text);
//
//
//			        final PopupWindowUtil popupWindow = new PopupWindowUtil(DataContentActivity.this, items);
//			        popupWindow.setTextColor(DataContentActivity.this.getResources().getColor(R.color.page_change_gray));
//			        popupWindow.setItemClickListener(new AdapterView.OnItemClickListener() {
//			            @Override
//			            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//			            switch (position) {
//						case 0:
//							startCheckNewVersion();
//							StatService.onEvent(DataContentActivity.this, "check_version", "检测版本", 1);
//							popupWindow.dismiss();
//							break;
//						case 1:
//							LoginOut();
//							toastMsg("退出登录成功!");
//							StatService.onEvent(DataContentActivity.this, "login_out", "退出登录", 1);
//							popupWindow.dismiss();
//							break;
//						}
//
//
//			            }
//			        });
//			        //根据后面的数据调节窗口的宽度
//			        popupWindow.show(v, 3);
//
//			}
//		});
	

		initPartView();

	}
	
	

	


	private void LoginOut() {
		
		EtongApplication.getApplication().setUserInfo(null);
		ActivitySkipUtil.skipActivity(this, LoginActivity.class);
		this.finish();
	}


	private void initPartView() {
		listView = findViewById(R.id.listView, GridView.class);
		
		//dp转px
		float small_width = 34 * mDensity + 0.5f;
		//一个图片的宽度
		final float width = (mWidth - small_width) / 2;
		final float high = (float) (width / 1.261);
		final AbsListView.LayoutParams rl_main = new AbsListView.LayoutParams(
				(int) width, (int) high);
		adapter = new ListAdapter<String>(this,
				R.layout.activity_data_content_adapter) {

			@Override
			protected void onPaint(View view, final String data,
					final int position) {

				RelativeLayout mMainLayout = (RelativeLayout) view
						.findViewById(R.id.rl_mian);

				mMainLayout.setLayoutParams(rl_main);
				ImageView type_img = (ImageView) view
						.findViewById(R.id.iv_type_icon);
				TextView type_name = (TextView) view
						.findViewById(R.id.tv_type_name);
				type_img.setBackgroundResource(getImageIdByName(icon[position]));
//				type_name.setText(data.get("title").toString());
				type_name.setText(data);
//				System.out.println(data.get("title").toString());
				
				view.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						L.d("time start", System.currentTimeMillis() + "");
						if((int) children.getJSONObject(position).get("moduleStatus")!=4){
							showItemData(data, position);							
						}else{
							toastMsg("您暂无权限！");
						}
					}
				});
			}
		};

		adapter.addAll(list);
		listView.setAdapter(adapter);
		
	}

	/**  
     * 根据图片名称获取R.java中对应的id  
     *   
     * @param name  
     * @return  
     */  
    public static int getImageIdByName (String name) {  
        int value = 0;  
        if (null != name) {  
            if (name.indexOf(".") != -1) {  
                name = name.substring(0, name.indexOf("."));  
            }  
            Class<com.etong.android.jxappdata.R.drawable> cls = R.drawable.class;
            try {    
                value = cls.getDeclaredField(name).getInt(null);    
            } catch (Exception e) {    
                  
            }             
        }  
        return value;  
    }  

	
	protected void showItemData(String data, int position) {
		type=(int)children.getJSONObject(position).get("type");
			if ((int) children.getJSONObject(position).get("moduleStatus")!= 3) {
				Intent intent=new Intent(DataContentActivity.this,ComeSoonActivity.class);
				if((int) children.getJSONObject(position).get("moduleStatus")==1){
					intent.putExtra("name", data);
					intent.putExtra("data", "正在接入");
				}else if((int) children.getJSONObject(position).get("moduleStatus")==2){
					intent.putExtra("name", data);
					intent.putExtra("data", "尚未接入");
				}
			 startActivity(intent);
			 
		} else{
             if(type == 0){
            	 if((int)children.getJSONObject(position).get("moduleStatus")==3){
            		 Intent intent = new Intent(DataContentActivity.this, DataItemActivity.class);
            		 Map map = new HashMap<>();
            		 map.put("name", data);
//            		 map.put("icon",small_icon[position]);
            		 map.put("data",children.getJSONObject(position));
            		 
            		 // 传递数据
            		 final SerializableObject myMap = new SerializableObject();
            		 myMap.setObject(map);// 将map数据添加到封装的myMap中
            		 Bundle bundle = new Bundle();
            		 bundle.putSerializable("dataMap", myMap);
            		 intent.putExtras(bundle);
            		 startActivity(intent);
            	 }else if((int)children.getJSONObject(position).get("moduleStatus")==4){
            		 toastMsg("您暂无权限！");
            	 }else{
            		 Intent intent=new Intent(DataContentActivity.this,ComeSoonActivity.class);
     				if((int) children.getJSONObject(position).get("moduleStatus")==1){
     					intent.putExtra("name", data);
     					intent.putExtra("data", "正在接入");
     				}else if((int) children.getJSONObject(position).get("moduleStatus")==2){
     					intent.putExtra("name", data);
     					intent.putExtra("data", "尚未接入");
     				}
     			 startActivity(intent);
            	 }
             }else if(type == 1){
            	 Intent intent = new Intent(DataContentActivity.this, ChartsActivity.class); 
            	 Map map = new HashMap<>();
            	 map.put("itemName", data);
            	 map.put("data",children.getJSONObject(position));
            	 
            	 // 传递数据
            	 final SerializableObject myMap = new SerializableObject();
            	 myMap.setObject(map);// 将map数据添加到封装的myMap中
            	 Bundle bundle = new Bundle();
            	 bundle.putSerializable("dataMap", myMap);
            	 intent.putExtras(bundle);
            	 startActivity(intent);
             }
		}

	}

	
	public void initData() {
		list = new ArrayList<>();
//		 JSONObject obj=JSONObject.parseObject(mSharedPublisher.getString("userShared"));
//		 JSONObject authority=obj.getJSONObject("authority");
		 JSONArray authoritys= FrameEtongApplication.getApplication().getUserInfo().getAuthority();
		 JSONObject jsonObject =authoritys.getJSONObject(0);
		 JSONArray childrens=jsonObject.getJSONArray("children");
		 JSONObject object=childrens.getJSONObject(0);
		  children= object.getJSONArray("children");
		  icon = new String[children.size()];
		 for(int i=0;i<children.size();i++){
			 String resourcename=children.getJSONObject(i).getString("resourceName");
			 icon[i]= children.getJSONObject(i).getString("icon");
			 modulestatus=(int) children.getJSONObject(i).get("moduleStatus");
			 if(modulestatus==3){
//				 typeIcon[i]=typeIcon1[i];
				 icon[i]=icon[i]+"_highlighted";
			 }
			 list.add(resourcename);
			 
		 }
		

	}

	
	protected void  startCheckNewVersion(){
		// 自动更新
//		 AppUpdateProvider.getInstance().getUpdateInfo();
		AppUpdateProvider.getInstance().getUpdateInfo(
				"http://payment.suiyizuche.com:8080/version/app/1007",
				new AppUpdateResultAction() {

					@Override
					public void noUpdate() {
						toastMsg("暂无更新");
						
					}

					@Override
					public void fail(int errCode, String errStr) {
						// TODO Auto-generated method stub
						switch (errCode) {
						case AppUpdateProvider.ERR_NULL:// 返回更新内容为空
							
						case AppUpdateProvider.ERR_NETWORK: // 网络异常
						
						case AppUpdateProvider.ERR_CANCLE:// 取消更新
						case AppUpdateProvider.ERR_LATER:// 稍后更新
							
							break;
						}
					}
				});
	}
}
