package JavaHw;

import java.io.Serializable;

public class Sight implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String zone;
	private String sightName;
	private String category;
	private String photoURL;
	private String description;
	private String address;

	public Sight() {

	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getZone() {
		return zone;
	}

	public void setSightName(String sightName) {
		this.sightName = sightName;
	}

	public String getSightName() {
		return sightName;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return String.format("SightName: %s%n" + "Zone: %s%n" + "Category: %s%n" + "PhotoURL: %s%n"
				+ "Description: %s%n" + "Address: %s%n", sightName, zone, category, photoURL, description, address);
	}
}
