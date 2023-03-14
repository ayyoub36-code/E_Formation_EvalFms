package fr.fms.entities;

public class OrderItem {

	private int id;
	private int idFormation;
	private int idOrder;

	public OrderItem(int id, int idFormation, int idOrder) {
		this.id = id;
		this.idFormation = idFormation;
		this.idOrder = idOrder;

	}

	public OrderItem(int idFormation, int idOrder) {
		this.idFormation = idFormation;
		this.idOrder = idOrder;
	}

	public OrderItem(int idFormation) {
		this.idFormation = idFormation;
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

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", idFormation=" + idFormation + ", idOrder=" + idOrder + "]";
	}

}
