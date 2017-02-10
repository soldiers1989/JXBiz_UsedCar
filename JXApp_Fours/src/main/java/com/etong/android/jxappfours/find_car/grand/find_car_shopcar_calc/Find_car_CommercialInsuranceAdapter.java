package com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.utils.AddCommaToMoney;
import com.etong.android.frame.utils.L;
import com.etong.android.jxappfours.R;

import java.util.List;

public class Find_car_CommercialInsuranceAdapter extends BaseAdapter {

	private Context context;
	private List<Find_car_CommercialInsurance> itemList;
    private ItemHolder childHolder;
    
    private AdapterCallback adapterCallback;
	
    public Find_car_CommercialInsuranceAdapter(Context context, List<Find_car_CommercialInsurance> itemList) {
        this.context = context;
        this.itemList = itemList;
    }
    
    public interface AdapterCallback {
        public void callBack(List<Find_car_CommercialInsurance> expandBeanCallList);
    }

    public void setCallback(AdapterCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.find_car_list_item_carloan_insurance, null);
            childHolder = new ItemHolder();
            childHolder.iv_isselect = (ImageView) convertView.findViewById(R.id.iv_insurance_select);
            childHolder.tv_insurance_name = (TextView) convertView.findViewById(R.id.tv_insurance_name);
            childHolder.tv_insurance_money = (TextView) convertView.findViewById(R.id.tv_insurance_money);
            childHolder.vg_insurance = (ViewGroup) convertView.findViewById(R.id.rl_insurance);
;
            convertView.setTag(childHolder);
        } else {
        	childHolder = (ItemHolder) convertView.getTag();
        }
		
			if (itemList.get(position).isChecked()) {
				childHolder.iv_isselect.setSelected(true);
			} else {
				childHolder.iv_isselect.setSelected(false);
			}
		
		childHolder.iv_isselect.setOnClickListener(new ItemClick(position));
		childHolder.vg_insurance.setOnClickListener(new ItemClick(position));
		
		childHolder.tv_insurance_name.setText(itemList.get(position).getInsuranceName());
		childHolder.tv_insurance_money.setText(AddCommaToMoney.AddCommaToMoney(itemList.get(position).getInsuranceMoney()));
		
		return convertView;
	}

	class ItemClick implements View.OnClickListener {

		private int position;
		
		public ItemClick(int position) {
			this.position = position;
		}
		
		@Override
		public void onClick(View v) {
			int size = itemList.size();
			L.d("List中的个数:", size+"");
			boolean isSelect = itemList.get(position).isChecked();
			
			if (isSelect) {
				itemList.get(position).setChecked(false);
			} else {
				itemList.get(position).setChecked(true);
			}

			adapterCallback.callBack(itemList);
			
			notifyDataSetChanged();
		}
		
	}
	
	class ItemHolder {
		ImageView iv_isselect;
		TextView tv_insurance_name;
		TextView tv_insurance_money;
		ViewGroup vg_insurance;
		
	}
	
}
