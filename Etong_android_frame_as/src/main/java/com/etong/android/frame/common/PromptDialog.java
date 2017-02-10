package com.etong.android.frame.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.etong.android.frame.R;

public class PromptDialog extends Dialog {

	Context context;

	public PromptDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_prompt);
	}

}
