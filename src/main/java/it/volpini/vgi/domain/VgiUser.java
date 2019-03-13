package it.volpini.vgi.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="vgi_utenti")
public class VgiUser extends AbstractEntity{
	
	@NotNull
	@Column(unique=true, nullable=false)
	private String username;
	
	@NotNull
	@Column(unique=true, nullable=false)
	private String password;
	
	@Email
	@Column(unique=true, nullable=false)
	private String email;
	
	@NotNull
	private Integer anni;
		
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="vgiUser")
	//@JoinColumn(name="user_id", referencedColumnName="long_id", nullable= true)
	private List<UserLocation> locations;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			   name="Users_Roles",
			   joinColumns=@JoinColumn(name="User_id", referencedColumnName="long_id"),
			   inverseJoinColumns=@JoinColumn(name="Role_id", referencedColumnName="long_id"))
	private List<RoleUser> ruoli;

	public VgiUser(Long id) {
		this.id=id;
	}
	public VgiUser () {
		
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAnni() {
		return anni;
	}

	public void setAnni(Integer anni) {
		this.anni = anni;
	}

	public List<UserLocation> getLocations() {
		return locations;
	}

	public void setLocations(List<UserLocation> locations) {
		this.locations = locations;
	}
	
	public void addLocation(UserLocation location) {
		if(this.locations== null) {
			this.locations=(new ArrayList<UserLocation>());
		}
		this.locations.add(location);
	}

	public List<RoleUser> getRuoli() {
		return ruoli;
	}

	public void setRuoli(List<RoleUser> ruoli) {
		this.ruoli = ruoli;
	}
	
	public void addRuolo(RoleUser role) {
		if(this.ruoli==null) {
			this.ruoli = new ArrayList<RoleUser>();
		}
		this.ruoli.add(role);
	}

}
