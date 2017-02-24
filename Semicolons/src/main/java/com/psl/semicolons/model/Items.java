package com.psl.semicolons.model;

public class Items {
	private String total_p;

	private String item_name;

	private String quantity;

	private String unit_price;

	public String getTotal_p() {
		return total_p;
	}

	public void setTotal_p(String total_p) {
		this.total_p = total_p;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(String unit_price) {
		this.unit_price = unit_price;
	}

	@Override
	public String toString() {
		return "ClassPojo [total_p = " + total_p + ", item_name = " + item_name + ", quantity = " + quantity + ", unit_price = " + unit_price + "]";
	}
}
