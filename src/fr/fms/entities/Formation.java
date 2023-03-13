package fr.fms.entities;

public class Formation {
	private int id;
	private String name;
	private String description;
	private int duration;
	private boolean isFaceToFace;
	private double price;
	private int idCategory;

	public Formation(int id, String name, String description, int duration, boolean isFaceToFace, double price,
			int idCategory) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.isFaceToFace = isFaceToFace;
		this.price = price;
		this.idCategory = idCategory;
	}

	public Formation(String name, String description, int duration, boolean isFaceToFace, double price,
			int idCategory) {
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.isFaceToFace = isFaceToFace;
		this.price = price;
		this.idCategory = idCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isFaceToFace() {
		return isFaceToFace;
	}

	public void setFaceToFace(boolean isFaceToFace) {
		this.isFaceToFace = isFaceToFace;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Formation [id=" + id + ", name=" + name + ", description=" + description + ", duration=" + duration
				+ ", isFaceToFace=" + isFaceToFace + ", price=" + price + ", idCategory=" + idCategory + "]";
	}

}
