package it.volpini.vgi.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ruoli")
public class RoleUser extends AbstractEntity {

	private String roleName;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Users_Roles", joinColumns = @JoinColumn(name = "Role_id", referencedColumnName = "long_id"), inverseJoinColumns = @JoinColumn(name = "User_id", referencedColumnName = "long_id"))
	private List<VgiUser> utenti;

	public RoleUser() {

	}

	
	public RoleUser(String roleName) {
		this.roleName = roleName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<VgiUser> getUtenti() {
		return utenti;
	}

	public void setUtenti(List<VgiUser> utenti) {
		this.utenti = utenti;
	}

	public void addUtente(VgiUser utente) {
		if (this.utenti == null) {
			this.utenti = new ArrayList<VgiUser>();
		}
		this.utenti.add(utente);
	}

}
