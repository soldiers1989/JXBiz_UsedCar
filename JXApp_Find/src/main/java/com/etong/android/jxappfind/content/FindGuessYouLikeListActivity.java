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
import com.etong.android.jxappfind.adapter.FindGuess_likeAdapter;
import com.etong.android.jxappfind.javabean.FindGuessBean;
import com.etong.android.jxappfours.order.FO_OrderActivity;

import java.util.List;
import java.util.Map;

/**
 * @desc 猜你喜欢点击更多页面
 * @createtime 2016/9/12 - 13:53
 * @Created by xiaoxue.
 */
public class FindGuessYouLikeListActivity extends BaseSubscriberActivity {

    private TitleBar mTitleBar;
    private ListView guess_like_listview;
    private List<FindGuessBean> mLikeList;
    private ListAdapter<FindGuessBean> mListAdapter;
    private ImageProvider mImageProvider;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_timebuy_list_activity);
        // 得到传过来的配置
        Bundle bundle = getIntent().getExtras();
        SerializableObject serializableMap = (SerializableObject) bundle
                .get("dataMap");
        Map map = (Map) serializableMap.getObject();

        mLikeList = (List<FindGuessBean>) map.get("mLikeList");
        initView();
    }

    protected void initView() {
        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("猜你喜欢");
        mTitleBar.showBottomLin(false);
        mTitleBar.showBackButton(true);
        mTitleBar.showNextButton(false);
        mImageProvider = ImageProvider.getInstance();
        mImageProvider.initialize(this);
        guess_like_listview = findViewById(R.id.vechile_xianshigou_list, ListView.class);

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
        ((ViewGroup) guess_like_listview.getParent()).addView(emptyListView);
        guess_like_listview.setEmptyView(emptyListView);

        FindGuess_likeAdapter mListAdapter=new FindGuess_likeAdapter(this);
        guess_like_listview.setAdapter(mListAdapter);

        if (null != mLikeList) {
            mListAdapter.updateCarList(mLikeList);
        }
    }
}
