package it.volpini.vgi.domain;

import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class GhostUser extends AbstractEntity{
	
	private String username;
	
	private Integer anni;
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, mappedBy="ghostUser")
	//@JoinColumn(name="gosth_id", referencedColumnName="long_id", nullable=true)
	private List<UserLocation> locations;
	
	public GhostUser() {
		
	}
	
	public GhostUser(Long id) {
		this.id=id;
	}
	
	public GhostUser(Optional<VgiUser> user) {
		if (user.isPresent()) {
			this.anni = user.get().getAnni();
			this.username = user.get().getUsername();
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
		location.setGosthUser(this);
	}
	
	
	
	

}
