package com.etong.android.jxappusedcar.content;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.etong.android.jxappusedcar.R;

/**
 * @desc (自定义Dialog)
 * @createtime 2016/10/11 0011--16:11
 * @Created by wukefan.
 */
public class UC_Dialog extends Dialog {

    private Context context;
    private String title;
    private String buttonText;
    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {

        public void doConfirm();

    }

    public UC_Dialog(Context context, String title, String buttonText) {
        super(context, R.style.ToastDialog);
        this.context = context;
        this.title = title;
        this.buttonText = buttonText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.used_car_dialog_view, null);
        setContentView(view);

        TextView tvTitle = (TextView) view.findViewById(R.id.uc_dialog_title);
        Button btnDialog = (Button) view.findViewById(R.id.uc_dialog_btn);

        tvTitle.setText(title);
        btnDialog.setText(buttonText);


        btnDialog.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9); // 宽度设置为屏幕的0.9
        lp.height = (int) (d.heightPixels * 0.32); // 高度设置为屏幕的0.32
        dialogWindow.setAttributes(lp);
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (v.getId() == R.id.uc_dialog_btn) {
                clickListenerInterface.doConfirm();
            }
        }

    }

}
