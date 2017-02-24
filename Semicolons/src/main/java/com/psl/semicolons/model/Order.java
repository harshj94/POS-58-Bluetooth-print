package com.psl.semicolons.model;

public class Order {
	private String total_amount;

	private String contact_number;

	private Items[] items;

	private String order_id;

	private String email_id;

	private String cust_id;

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getContact_number() {
		return contact_number;
	}

	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}

	public Items[] getItems() {
		return items;
	}

	public void setItems(Items[] items) {
		this.items = items;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	@Override
	public String toString() {
		return "ClassPojo [total_amount = " + total_amount + ", contact_number = " + contact_number + ", items = " + items + ", order_id = " + order_id + ", email_id = " + email_id + ", cust_id = " + cust_id + "]";
	}
}
