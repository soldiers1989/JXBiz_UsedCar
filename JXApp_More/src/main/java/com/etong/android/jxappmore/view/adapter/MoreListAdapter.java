package com.etong.android.jxappmore.view.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etong.android.jxappmore.R;
import com.etong.android.jxappmore.javabeam.MoreTitleBeam;
import com.etong.android.jxappmore.widget.MoreLineGridView;
import com.etong.android.jxappmore.widget.MoreViewPager;

import java.util.ArrayList;
import java.util.List;


public class MoreListAdapter extends BaseAdapter implements OnPageChangeListener {
    private Context context;
    private List<MoreTitleBeam> mList;
    private LayoutInflater inflater;
    private ItemHolder childHolder;
    private List<String> titleNames;
    private List<View> mViewPagerGridList;


    public Integer[] mCurrentPagers;//保存listview的页面位置

    public MoreListAdapter(Context context, List<MoreTitleBeam> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);

        //初始化保存listview的页面位置的数组，并设置默认值
        mCurrentPagers = new Integer[mList.size()];
        for(int i=0;i<mList.size();i++){
            mCurrentPagers[i]=0;
        }
    }


    // 当列表数据发生变化时,用此方法来更新列表
    public void updateListView(List<MoreTitleBeam> mList) {
        this.mList = mList;

        //初始化保存listview的页面位置的数组，并设置默认值
        mCurrentPagers = new Integer[mList.size()];
        for(int i=0;i<mList.size();i++){
            mCurrentPagers[i]=0;
        }

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.more_items_lv, null);
            childHolder = new ItemHolder();
            childHolder.titleName = (TextView) convertView.findViewById(R.id.more_txt_listtitle);
            childHolder.myViewPager = (MoreViewPager) convertView.findViewById(R.id.more_vp_content);
            childHolder.ll_point = (LinearLayout) convertView.findViewById(R.id.more_ll_point);

            convertView.setTag(childHolder);

        } else {
            childHolder = (ItemHolder) convertView.getTag();
        }

        // 获取屏幕像素相关信息
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        // 当只有三个或三个以下是ViewPager设置为一行否则设置为两行
        if(mList.get(position).getItemBeamList().size()<=3){
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) childHolder.myViewPager.getLayoutParams(); // 取控件mGrid当前的布局参数
            relativeParams.height = (int)((248/2) * dm.density +0.5f);// 当控件的高强制设成124dp
            childHolder.myViewPager.setLayoutParams(relativeParams);
        }else{
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) childHolder.myViewPager.getLayoutParams(); // 取控件mGrid当前的布局参数
            relativeParams.height = (int)(248 * dm.density +0.5f);// 当控件的高强制设成248dp
            childHolder.myViewPager.setLayoutParams(relativeParams);
        }

        childHolder.titleName.setText(mList.get(position).getTitleName());
        mViewPagerGridList = new ArrayList<>();

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LayoutInflater inflater = getLayoutInflater();

        // 每页显示最大条目个数
        int pageSize =context.getResources().getInteger(R.integer.MoreHomePageHeaderColumn) * 2;
        //页数
        int pageCount = (int) Math.ceil(mList.get(position).getItemBeamList().size()*1.0/pageSize);


//        if (null == mPoint) {
        final ImageView[] mPoint = new ImageView[pageCount];//设置滑动小圆点ImageView
//        }

        //当ViewPager页数大于1时才显示小圆点布局
        if (pageCount <= 1) {
            childHolder.ll_point.setVisibility(View.GONE);
        } else {
            childHolder.ll_point.setVisibility(View.VISIBLE);
        }
        childHolder.ll_point.removeAllViews();

        // 小圆点图标
        for (int i = 0; i < pageCount; i++) {
            if (null == mPoint[i]) {

                // 创建一个ImageView, 并设置宽高. 将该对象放入到数组中
                ImageView imageView = new ImageView(convertView.getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(5, 0, 5, 0);
                imageView.setLayoutParams(params);

                mPoint[i] = imageView;

                // 将小圆点放入到布局中
                childHolder.ll_point.addView(mPoint[i]);
                if (i == mCurrentPagers[position]) {
                    mPoint[i]
                            .setBackgroundResource(R.mipmap.ic_point_checked);
                } else {
                    mPoint[i]
                            .setBackgroundResource(R.mipmap.ic_point_unchecked);
                }
            }
        }


        //获取屏幕的宽度,单位px
        int screenWidth  = context.getResources().getDisplayMetrics().widthPixels;
        //获取GridView中每个item的宽度 = 屏幕宽度 / GridView显示的列数
        int columnWidth = (int) Math.ceil((screenWidth)*1.0 / (context.getResources().getInteger(R.integer.MoreHomePageHeaderColumn)) );

        for(int index = 0;index < pageCount;index++){
            MoreLineGridView grid = (MoreLineGridView) inflater.inflate(R.layout.more_layout_gv,childHolder.myViewPager,false);
            //设置GridView每个item的宽度
            grid.setColumnWidth(columnWidth);
//            grid.setTotal()

            //设置GirdView的布局参数(宽和高，宽为包裹父容器，高 = columnWidth)
            grid.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,columnWidth));
            grid.setAdapter(new MoreGridViewAdapter(context,mList.get(position).getItemBeamList(),index));
            mViewPagerGridList.add(grid);
        }
        childHolder.myViewPager.setAdapter(new MoreViewPagerAdapter(mViewPagerGridList));

        //设置ViewPager的页面切换监听事件
        childHolder.myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int arg0) {

                for (int i = 0; i < mPoint.length; i++) {
                    if (i == arg0) {
                        mPoint[i]
                                .setBackgroundResource(R.mipmap.ic_point_checked);
                    } else {
                        mPoint[i]
                                .setBackgroundResource(R.mipmap.ic_point_unchecked);
                    }
                }
//                L.d("--------------position>",position+"");
//                L.d("--------------pager>",arg0+"");
                mCurrentPagers[position] = arg0;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        //设置viewpager页面为记录下来的上次滑动viewpager的当前页面
        //一定要写在viewpager的页面切换监听事件的后面，不然会影响onPageSelected()
        if(null != mCurrentPagers){
//            L.d("+++++++++++++++>",position+"------"+mCurrentPagers[position]+"");
            childHolder.myViewPager.setCurrentItem(mCurrentPagers[position]);

        }

        return convertView;
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
        } else if (position == 1) {
        } else if (position == 2) {
        }
    }


    class ItemHolder {
        TextView titleName;
        MoreViewPager myViewPager;
        LinearLayout ll_point;

    }
}