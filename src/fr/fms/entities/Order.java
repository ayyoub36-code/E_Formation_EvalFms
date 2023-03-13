package fr.fms.entities;

import java.sql.Date;

public class Order {

	private int id;
	private Date date;
	private int idCustomer;
	private double totalAmount;

	public Order(int id, Date date, int idCustomer, double totalAmount) {
		this.id = id;
		this.date = date;
		this.idCustomer = idCustomer;
		this.totalAmount = totalAmount;
	}

	public Order(Date date, int idCustomer, double totalAmount) {
		this.date = date;
		this.idCustomer = idCustomer;
		this.totalAmount = totalAmount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", date=" + date + ", idCustomer=" + idCustomer + ", totalAmount=" + totalAmount
				+ "]";
	}

}
