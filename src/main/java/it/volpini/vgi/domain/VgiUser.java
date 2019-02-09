package it.volpini.vgi.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="vgi_utenti")
public class VgiUser extends AbstractEntity{
	
	private String username;
	
	private String password;
	
	private String email;
	
	private String nome;
	
	private String cognome;
	
	private Integer anni;
	
	private boolean enabled=false;
	
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
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
		this.locations.add(location);
		location.setVgiUser(this);
	}

	public List<RoleUser> getRuoli() {
		return ruoli;
	}

	public void setRuoli(List<RoleUser> ruoli) {
		this.ruoli = ruoli;
	}
	
	public void addRuolo(RoleUser role) {
		this.getRuoli().add(role);
		role.getUtenti().add(this);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
