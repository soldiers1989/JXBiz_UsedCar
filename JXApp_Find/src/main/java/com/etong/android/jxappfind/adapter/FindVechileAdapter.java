package com.etong.android.jxappfind.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.jxappfind.R;
import com.etong.android.jxappfind.javabean.FindVechileInfoBean;
import com.etong.android.jxappfours.order.FO_OrderActivity;

import java.util.ArrayList;
import java.util.List;

/**.
 * 限时购  促销车的adapter
 * Created by Administrator on 2016/8/5.
 *
 */
public  class FindVechileAdapter extends PagerAdapter {
    private List<FindVechileInfoBean.ItemsBean[]> listData = null;
    private Context context;
    private int type;

    /**
     * 初始化数据源, 即View数组
     *
     * @param
     */
    public FindVechileAdapter(Context context, List<FindVechileInfoBean.ItemsBean> data,int type) {
        this.context=context;
        this.type =type;
        if (null == listData)
            listData = new ArrayList<FindVechileInfoBean.ItemsBean[]>();
        int position = 0;
        for (int i = 0; i < data.size() / 2; i++) {
            listData.add(new FindVechileInfoBean.ItemsBean[2]);
            listData.get(i)[0] = data.get(position);
            position++;
            listData.get(i)[1] = data.get(position);
            position++;
        }

    }

    /**
     * 从ViewPager中删除集合中对应索引的View对象
     */
    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeViewAt(position);// removeView(listData.get(position));
    }

    /**
     * 获取ViewPager的个数
     */
    @Override
    public int getCount() {
        return listData.size();
    }

    /**
     * 从View集合中获取对应索引的元素, 并添加到ViewPager中
     */
    @SuppressLint("InflateParams")
    @Override
    public Object instantiateItem(View container, int position) {
        // 通过系统提供的实例获得一个LayoutInflater对象
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
        // 第一个参数为xml文件中view的id，第二个参数为此view的父组件，可以为null，android会自动寻找它是否拥有父组件
        View mLayout = inflater.inflate(R.layout.find_vechile_item_vp, null);
        onPaint(mLayout, listData.get(position)[0],
                listData.get(position)[1], position);
        ((ViewPager) container).addView(mLayout, 0);
        return mLayout;
    }

    /**
     * 是否将显示的ViewPager页面与instantiateItem返回的对象进行关联 这个方法是必须实现的
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void onPaint(View view, final FindVechileInfoBean.ItemsBean leftD,
                      final FindVechileInfoBean.ItemsBean rightD, int position){

        final Intent intent = new Intent();
//                intent.setClass(FindMainFragment.this.getActivity(),
//                        VechileStyleActivity.class);

        TextView title1 = (TextView) view
                .findViewById(R.id.find_txt_vechile_title1);

        ImageView image1 = (ImageView) view
                .findViewById(R.id.find_img_vechile_image1);

        TextView price1 = (TextView) view
                .findViewById(R.id.find_txt_vechile_price1);
        TextView discard_price1 = (TextView) view
                .findViewById(R.id.find_txt_discard_price1);



        TextView title2 = (TextView) view
                .findViewById(R.id.find_txt_vechile_title2);

        ImageView image2 = (ImageView) view
                .findViewById(R.id.find_img_vechile_image2);


        TextView price2 = (TextView) view
                .findViewById(R.id.find_txt_vechile_price2);
        TextView discard_price2 = (TextView) view
                .findViewById(R.id.find_txt_discard_price2);

        RelativeLayout left = (RelativeLayout) view
                .findViewById(R.id.find_rl_vechile_left);
        final RelativeLayout right = (RelativeLayout) view
                .findViewById(R.id.find_rl_vechile_right);

        title1.setText(leftD.getName());
        price1.setText(leftD.getPrice()+"万");
        discard_price1.setText(leftD.getOriginal_price()+"万");
        discard_price1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //删除线
        // zhijiang1.setText(leftD.getZhiiang());
        try {
            ImageProvider.getInstance().loadImage(image1, leftD.getPicturesList().get(0).getUrl(),R.mipmap.find_activity_ic);
        } catch (Exception e) {
        }

        left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(type==1){
//                    Intent intent=new Intent(context,FindWebViewActivity.class);
//                    context.startActivity(intent);
//                    Intent intent=new Intent(context,Fours_Order_OrderActivity.class);
                    Intent intent=new Intent(context,FO_OrderActivity.class);
                    intent.putExtra("flag", 4);
                    intent.putExtra("isSelectCar", true);
                    intent.putExtra("vTitleName", leftD.getName());
                    intent.putExtra("carsetId", leftD.getCarset_id());
                    if(null==leftD.getVid()){
                        intent.putExtra("vid", "-1");
                    }else {
                        intent.putExtra("vid", leftD.getVid()+"");
                    }
                    context.startActivity(intent);
                }
                if(type==2){
//                    Intent intent=new Intent(context,Fours_Order_OrderActivity.class);
                    Intent intent=new Intent(context,FO_OrderActivity.class);
                    intent.putExtra("flag", 4);
                    intent.putExtra("isSelectCar", true);
                    intent.putExtra("vTitleName", leftD.getName());
                    intent.putExtra("carsetId", leftD.getCarset_id());
                    if(null==leftD.getVid()){
                        intent.putExtra("vid", "-1");
                    }else {
                        intent.putExtra("vid", leftD.getVid()+"");
                    }
                    context.startActivity(intent);
//                    Intent intent=new Intent(context,FindPromotionInfoActivity.class);
////                    intent.putExtra("carseriesId",);
////                    intent.putExtra("cartypeId",);
//                    context.startActivity(intent);
                }
//                intentActivity(leftD.getType());

//                        intent.putExtra("CarsetId", leftD.getCarset_id());
//                        intent.putExtra("huodong_id", leftD.getHuodong_id());
//                        startActivity(intent);
            }
        });

        if (rightD.getPicturesList()!=null) {
            title2.setText(rightD.getName());
            // zhijiang2.setText(rightD.getZhiiang());
            price2.setText(rightD.getPrice()+"万");
            discard_price2.setText(rightD.getOriginal_price()+"万");
            discard_price2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //删除线
            ImageProvider.getInstance().loadImage(image2,
                    rightD.getPicturesList().get(0).getUrl(),R.mipmap.find_activity_ic);
            right.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(type==1){
//                        Intent intent=new Intent(context,Fours_Order_OrderActivity.class);//询底价
                        Intent intent=new Intent(context,FO_OrderActivity.class);//询底价
                        intent.putExtra("flag", 4);
                        intent.putExtra("isSelectCar", true);
                        intent.putExtra("vTitleName", rightD.getName());
                        intent.putExtra("carsetId", rightD.getCarset_id());
                        if(null==rightD.getVid()){
                            intent.putExtra("vid", "-1");
                        }else {
                            intent.putExtra("vid", rightD.getVid()+"");
                        }
                        context.startActivity(intent);
//                        Intent intent=new Intent(context,FindWebViewActivity.class);
//                        context.startActivity(intent);
                    }
                    if(type==2){
//                        Intent intent=new Intent(context,Fours_Order_OrderActivity.class);
                        Intent intent=new Intent(context,FO_OrderActivity.class);
                        intent.putExtra("flag", 4);
                        intent.putExtra("isSelectCar", true);
                        intent.putExtra("vTitleName", rightD.getName());
                        intent.putExtra("carsetId", rightD.getCarset_id());
                        if(null==rightD.getVid()){
                            intent.putExtra("vid", "-1");
                        }else {
                            intent.putExtra("vid", rightD.getVid()+"");
                        }
                        context.startActivity(intent);
//                        Intent intent=new Intent(context,FindPromotionInfoActivity.class);
//                        context.startActivity(intent);
                    }
//                    intentActivity(rightD.getType());
//                            intent.putExtra("CarsetId", rightD.getCarset_id());
//                            intent.putExtra("huodong_id",
//                                    rightD.getHuodong_id());
//                            startActivity(intent);
                }
            });
        } else {
            right.setVisibility(View.INVISIBLE);
        }

    }


   /* public void intentActivity(int type){
        if(type==1){

        }
        if(type==2){

        }
    }*/

}
