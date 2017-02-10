package com.etong.android.frame.widget;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.etong.android.frame.R;

public class TitleBar {
	View mTitleBarBackground; 	// TitleBar的背景色
	View mTitleBar;
	View mTitleName;
	View mBackButton;
	View mNextButton;
	View mDivider;
	View mLine;
	Activity mContainer;

	public TitleBar(View container) {
		if (null == container) {
			return;
		}

		View titleBar = container.findViewById(R.id.titlebar_default);
		initView(titleBar);

	}

	void initView(View titleBar) {
		if (null == titleBar) {
			return;
		}
		mTitleBar = titleBar;

		mTitleBarBackground = titleBar.findViewById(R.id.titlebar_background);
		mTitleName = titleBar.findViewById(R.id.titlebar_title_name);
		mBackButton = titleBar.findViewById(R.id.titlebar_back_button);
		mNextButton = titleBar.findViewById(R.id.titlebar_next_button);
		mDivider = titleBar.findViewById(R.id.titlebar_divider_line);
		mLine=titleBar.findViewById(R.id.titlebar_divider_line);

		showBackButton(true);
		showNextButton(false);
	}

	public TitleBar(Activity container) {
		mContainer = container;
		if (null == container) {
			return;
		}

		View titleBar = container.findViewById(R.id.titlebar_default);
		initView(titleBar);
	}

	public TitleBar(Activity container, int layout) {
		mContainer = container;
		if (null == container) {
			return;
		}

		View titleBar = container.findViewById(layout);
		initView(titleBar);
	}

	public void setTitle(String title) {
		if (null == mTitleName)
			return;
		((TextView) mTitleName).setText(title);
	}

	public void showBackButton(boolean enable) {

		if (null == mBackButton)
			return;

		if (enable) {
			mBackButton.setVisibility(View.VISIBLE);
			mBackButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (null != mContainer)
						mContainer.finish();
				}

			});
		} else {
			mBackButton.setVisibility(View.GONE);
		}
	}

	public void showNextButton(boolean enable) {

		if (null == mNextButton)
			return;

		if (enable) {
			mNextButton.setVisibility(View.VISIBLE);
		} else {
			mNextButton.setVisibility(View.GONE);
		}
	}
	
	public void showBottomLin(boolean enable){
		if (null == mLine)
			return;

		if (enable) {
			mLine.setVisibility(View.VISIBLE);
		} else {
			mLine.setVisibility(View.GONE);
		}
	}

	public void setOnClickListener(OnClickListener listener) {
		if (null != mNextButton)
			mNextButton.setOnClickListener(listener);

		if (null != mBackButton) {
			mBackButton.setOnClickListener(listener);
		}
	}
	
	public void setNextOnClickListener(OnClickListener listener) {
		if (null != mNextButton)
			mNextButton.setOnClickListener(listener);
	}

	public void setBackOnClickListener(OnClickListener listener) {
		if (null != mBackButton)
			mBackButton.setOnClickListener(listener);
	}
	public View getNextButton() {
		return mNextButton;
	}

	public View getDivider() {
		return mDivider;
	}

	public void setNextButton(String name) {
		showNextButton(true);
		if (null == mNextButton)
			return;
		((TextView) mNextButton).setText(name);
	}

	public int getHeight() {
		if (null != mTitleBar)
			return mTitleBar.getHeight();

		return 0;
	}

	public View getTitleName() {
		return mTitleName;
	}

	public View getBackButton() {
		return mBackButton;
	}

	/**为TitleBar设置背景色*/
	public void setmTitleBarBackground(String mColor) {
		mTitleBarBackground.setBackgroundColor(Color.parseColor(mColor));
	}
	/**为标题文字设置颜色*/
	public void setTitleTextColor(String mTextColor) {
		((TextView)mTitleName).setTextColor(Color.parseColor(mTextColor));
	}
	/**
	 * 为右上角文字设置颜色
	 */
	public void setNextTextColor(String mTextColor) {
		if (null == mNextButton)
			return;
		((TextView) mNextButton).setTextColor(Color.parseColor(mTextColor));
	}
}
