package fr.fms.entities;

public class OrderItem {

	private int id;
	private int idFormation;
	private int idOrder;
	private double priceF;

	public OrderItem(int id, int idFormation, int idOrder, double priceF) {
		this.id = id;
		this.idFormation = idFormation;
		this.idOrder = idOrder;
		this.priceF = priceF;
	}

	public OrderItem(int idFormation, int idOrder, double priceF) {
		this.idFormation = idFormation;
		this.idOrder = idOrder;
		this.priceF = priceF;
	}

	public int getIdFormation() {
		return idFormation;
	}

	public void setIdFormation(int idFormation) {
		this.idFormation = idFormation;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public double getPriceF() {
		return priceF;
	}

	public void setPriceF(double priceF) {
		this.priceF = priceF;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", idFormation=" + idFormation + ", idOrder=" + idOrder + ", priceF=" + priceF
				+ "]";
	}

}
