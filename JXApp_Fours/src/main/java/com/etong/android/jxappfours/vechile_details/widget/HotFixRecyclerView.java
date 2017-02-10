package com.etong.android.jxappfours.vechile_details.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
/**
 * 解决RecyclerView.stopScroll为空的方法
 * @author Administrator
 *
 */
public class HotFixRecyclerView extends RecyclerView {

	public HotFixRecyclerView( Context context )
    {
        super( context );
    }

    public HotFixRecyclerView( Context context, AttributeSet attrs )
    {
        super( context, attrs );
    }

    public HotFixRecyclerView( Context context, AttributeSet attrs, int defStyle )
    {
        super( context, attrs, defStyle );
    }

    @Override
    public void stopScroll()
    {
        try
        {
            super.stopScroll();
        }
        catch( NullPointerException exception )
        {
        	
            /**
             *  The mLayout has been disposed of before the 
             *  RecyclerView and this stops the application 
             *  from crashing.
             */
        }
    }

}
