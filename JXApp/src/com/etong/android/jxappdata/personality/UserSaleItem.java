package com.etong.android.jxappdata.personality;

public class UserSaleItem {
	private Double sale_amount;//销售额
	private Double sale_amount_ppi; //销售同比
	private Double gross_profit;//销售毛利
	private Double gross_profit_ppi;//毛利同比
	private String dept_code;
	private String dept_name;//公司名字
	private String sale_date;//时间
	
	
	
	public Double getSale_amount() {
		return sale_amount;
	}
	public void setSale_amount(Double sale_amount) {
		this.sale_amount = sale_amount;
	}
	public Double getSale_amount_ppi() {
		return sale_amount_ppi;
	}
	public void setSale_amount_ppi(Double sale_amount_ppi) {
		this.sale_amount_ppi = sale_amount_ppi;
	}
	public Double getGross_profit() {
		return gross_profit;
	}
	public void setGross_profit(Double gross_profit) {
		this.gross_profit = gross_profit;
	}
	public Double getGross_profit_ppi() {
		return gross_profit_ppi;
	}
	public void setGross_profit_ppi(Double gross_profit_ppi) {
		this.gross_profit_ppi = gross_profit_ppi;
	}
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getSale_date() {
		return sale_date;
	}
	public void setSale_date(String sale_date) {
		this.sale_date = sale_date;
	}
	
	
	
	
}
