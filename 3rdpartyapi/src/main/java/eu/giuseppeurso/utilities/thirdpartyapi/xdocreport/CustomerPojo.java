package eu.giuseppeurso.utilities.thirdpartyapi.xdocreport;

import java.util.Collections;
import java.util.List;

/**
 * A model for the Customer.
 * @author Giuseppe Urso
 *
 */
public class CustomerPojo {
	private String name;
	private String surname;
	private String email;
	private String tel;
	
	// Only to show how a list of objects works in the Freemarker ODT template 
	private List<AddressPojo> addressList;
	
	
	public CustomerPojo() {		
	}

	/**
	 * Default Constructor
	 * @param name
	 * @param surname
	 * @param email
	 * @param tel
	 * @param addressList
	 */
	public CustomerPojo(String name, String surname, String email, String tel, List<AddressPojo> addressList) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.tel = tel;
		this.addressList = addressList;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<AddressPojo> getAddressList() {
		//Prevents nullPointer exception
		if (addressList==null) {
			return Collections.emptyList();
		}else{
			return addressList;
		}
	}
	public void setAddressList(List<AddressPojo> addressList) {
		this.addressList = addressList;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	/**
	 * A model for the customer's address.
	 * Only to show how a list of objects works in the Freemarker ODT template.
	 *  
	 * @author Giuseppe Urso
	 *
	 */
	public class AddressPojo {
		
		private String street;
		private String streetNumber;
		private String city;
		private String state;
		
		
		/**
		 * Default Constructor
		 * @param street
		 * @param streetNumber
		 * @param city
		 * @param state
		 */
		public AddressPojo(String street, String streetNumber, String city, String state) {
			this.street = street;
			this.streetNumber = streetNumber;
			this.city = city;
			this.state = state;
		}
		
		public String getStreet() {
			return street;
		}
		public void setStreet(String street) {
			this.street = street;
		}
		public String getStreetNumber() {
			return streetNumber;
		}
		public void setStreetNumber(String streetNumber) {
			this.streetNumber = streetNumber;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
	}
	
}
