package com.etong.android.jxbizusedcar.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

/**
 * Created by Dasheng on 2016/10/12.
 */

public class UC_SpaceItemDecoration extends ItemDecoration {

    private int mLeft1,mCenture,mTop,mRight,mBottom,mflag;

    public UC_SpaceItemDecoration(int left1, int centure, int top, int right, int flag){

        mLeft1 = left1;
        mCenture = centure;
        mTop = top;
        mRight = right;
        mflag = flag;

    }

    public UC_SpaceItemDecoration(int left1, int top, int bottom, int flag){

        mLeft1 = left1;
        mTop = top;
        mBottom = bottom;
        mflag = flag;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        switch (mflag){

            case 0:

                //偶数个孩子
                if (parent.getChildLayoutPosition(view) %2==0) {
                    outRect.left = mLeft1;
                    outRect.top = mTop;
                    outRect.bottom = 0;
                    outRect.right = mCenture;
                }else{

                    outRect.left = mCenture;
                    outRect.top = mTop;
                    outRect.bottom = 0;
                    outRect.right = mRight;

                }
                break;

            case 1:

                if(parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) == 1 || parent.getChildLayoutPosition(view) ==2){

                    outRect.left = mLeft1;
                    outRect.top = mTop;
                    outRect.bottom = 0;
                    outRect.right = 0;
                }

                if(parent.getChildLayoutPosition(view) == 3 || parent.getChildLayoutPosition(view) == 4 || parent.getChildLayoutPosition(view) == 5){

                    outRect.left = mLeft1;
                    outRect.top = mTop;
                    outRect.bottom = mBottom;
                    outRect.right = 0;
                }
                break;

            case 2:

                if(parent.getChildLayoutPosition(view)%3 == 0){   //第一列

                    outRect.left = mLeft1;
                    outRect.top = mTop;
                    outRect.bottom = 0;
                    outRect.right = mCenture;


                }else if(parent.getChildLayoutPosition(view)%3 == 1){  //第二列


                    outRect.left = mCenture;
                    outRect.top = mTop;
                    outRect.bottom = 0;
                    outRect.right = mCenture;

                }else{  //第三列

                    outRect.left = mCenture;
                    outRect.top = mTop;
                    outRect.bottom = 0;
                    outRect.right = mRight;

                }


                if(parent.getChildLayoutPosition(view) /3 == 2){  //第三行

                    outRect.bottom = 60;
                }

                break;

        }


    }
}
