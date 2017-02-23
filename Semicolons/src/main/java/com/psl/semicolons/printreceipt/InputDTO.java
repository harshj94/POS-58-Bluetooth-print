package com.psl.semicolons.printreceipt;

public class InputDTO {

	private String username;
	private String pizzaName;
	private Integer pizzaQuantity;
	private Long pizzaPrice;

	public InputDTO() {
		super();
	}

	public InputDTO(String username, String pizzaName, Integer pizzaQuantity, Long pizzaPrice) {
		super();
		this.username = username;
		this.pizzaName = pizzaName;
		this.pizzaQuantity = pizzaQuantity;
		this.pizzaPrice = pizzaPrice;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPizzaName() {
		return pizzaName;
	}

	public void setPizzaName(String pizzaName) {
		this.pizzaName = pizzaName;
	}

	public Integer getPizzaQuantity() {
		return pizzaQuantity;
	}

	public void setPizzaQuantity(Integer pizzaQuantity) {
		this.pizzaQuantity = pizzaQuantity;
	}

	public Long getPizzaPrice() {
		return pizzaPrice;
	}

	public void setPizzaPrice(Long pizzaPrice) {
		this.pizzaPrice = pizzaPrice;
	}
}
