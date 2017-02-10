package com.etong.android.jxappfind.content;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfind.R;
import com.etong.android.jxappfind.adapter.FindActivityAdapter;
import com.etong.android.jxappfind.javabean.FindVechileInfoBean;
import com.etong.android.jxappfind.utils.FindProvider;
import com.etong.android.jxappfours.order.FO_OrderActivity;

import java.util.List;
import java.util.Map;

/**
 * 限时购更多
 * Created by Administrator on 2016/9/1.
 */
public class FindTimeLimitedBuyListActivity extends BaseSubscriberActivity{


    private List<FindVechileInfoBean.ItemsBean> mTimeLimitedBuyList;
    private Long finishTime;
    private TitleBar mTitleBar;
    private ListView mList;
    private FindProvider mFindProvider;
    private ListAdapter<FindVechileInfoBean.ItemsBean> mListAdapter;
    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_timebuy_list_activity);

        // 得到传过来的配置
        Bundle bundle = getIntent().getExtras();
        SerializableObject serializableMap = (SerializableObject) bundle
                .get("dataMap");
        Map map = (Map) serializableMap.getObject();

        mTimeLimitedBuyList = (List<FindVechileInfoBean.ItemsBean>) map.get("mTimeLimitedBuyList");




        //得到剩余倒计时时间
//        finishTime = bundle.getLong("finishTime");


        initView();
    }

    protected void initView() {
        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("限时购");
        mTitleBar.showBackButton(true);
        mTitleBar.showNextButton(false);
        mTitleBar.showBottomLin(false);
        mList = findViewById(R.id.vechile_xianshigou_list, ListView.class);

        //添加listView数据为空显示
        View emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        ImageView default_empty_img = (ImageView) emptyListView.findViewById(R.id.default_empty_img);
        default_empty_lv_textview.setText("");
        default_empty_img.setBackgroundResource(R.drawable.ic_no_data);
      /*  default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //刷新数据
                mMessageProvider.MessageType(String.valueOf(1),true);
            }
        });*/
        ((ViewGroup) mList.getParent()).addView(emptyListView);
        mList.setEmptyView(emptyListView);

        mFindProvider = FindProvider.getInstance();

        FindActivityAdapter mListAdapter=new FindActivityAdapter(this);
        mList.setAdapter(mListAdapter);

        if(null!=mTimeLimitedBuyList){
            for(int i=0;i<mTimeLimitedBuyList.size();i++){
                if(mTimeLimitedBuyList.get(i).getPicturesList()==null){
                    mTimeLimitedBuyList.remove(i);//移除掉传过来为空的情况
                }
            }
            mListAdapter.updateCarList(mTimeLimitedBuyList);
        }

    }
}
