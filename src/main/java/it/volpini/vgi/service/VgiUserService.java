package it.volpini.vgi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.volpini.vgi.dao.GhostUserDao;
import it.volpini.vgi.dao.UserLocationDao;
import it.volpini.vgi.dao.VgiUserDao;
import it.volpini.vgi.domain.GhostUser;
import it.volpini.vgi.domain.RoleUser;
import it.volpini.vgi.domain.UserLocation;
import it.volpini.vgi.domain.VgiUser;
import it.volpini.vgi.exceptions.ElementNotFoundException;
import it.volpini.vgi.exceptions.NullParamException;
import it.volpini.vgi.exceptions.UserNotInSessionException;
import it.volpini.vgi.general.CostantiVgi;
import it.volpini.vgi.general.Esito;

@Service
@Transactional
public class VgiUserService {
	
	@Autowired
	private VgiUserDao vgiUserDao;
	@Autowired
	private UserLocationDao locationDao;
	@Autowired
	private GhostUserDao ghostDao;
	@Autowired
	private RoleUserService roleService;
	@Autowired
    private BCryptPasswordEncoder pwdEncoder;
	
	
	public Esito saveUser(Optional<VgiUser> opuser) throws NullParamException {
		VgiUser user = opuser
				.orElseThrow(() -> new NullParamException("Uno o più parametri non sono presenti nella request"));
		String pwdCrypted = pwdEncoder.encode(user.getPassword());
		user.setPassword(pwdCrypted);
		user.setEnabled(true);
		RoleUser role = roleService.findByRoleName(RoleUserService.ROLE_USER);
		if (user.getRuoli() != null) {
			user.getRuoli().add(role);
		} else {
			List<RoleUser> ruoli = new ArrayList<RoleUser>();
			ruoli.add(role);
			user.setRuoli(ruoli);
		}
		save(user);
		return new Esito(CostantiVgi.DESCR_OK, true);
	}
	
	public Optional<Long> getIdAuthenticatedUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(findByUsername(auth.getName()).getId());
	}
	
	public VgiUser save(VgiUser vgiUser) {
		return vgiUserDao.save(vgiUser);
	}
	
	public Optional<VgiUser> findById(Long id){
		return vgiUserDao.findById(id);
	}
	
	public VgiUser findByUsername(String username){
		return vgiUserDao.findByUsernameIgnoreCase(username);
	}
	
	public List<VgiUser> findAll(){
		return vgiUserDao.findAll();
	}
	
	public void delete(Long id) {
		vgiUserDao.deleteById(id);
	}
	
	public Esito selfDeleteUser() throws UserNotInSessionException, ElementNotFoundException {
		Optional<Long> id = getIdAuthenticatedUser();
		Optional<VgiUser> vgiUser = findById(
				id.orElseThrow(() -> new UserNotInSessionException("L'utente non risulta essere in sessione")));
		GhostUser ghost = new GhostUser(vgiUser);
		ghostDao.save(ghost);
		Optional<Long> idGu = ghostDao.findGhostUserIdByUsername(ghost.getUsername());
		ghost = new GhostUser(
				idGu.orElseThrow(() -> new ElementNotFoundException("Non ho trovato l'elemento indicato")));
		List<UserLocation> locations = locationDao.findByVgiUser_id(vgiUser.get().getId());
		for (UserLocation l : locations) {
			l.setGosthUser(ghost);
			l.setVgiUser(null);
			locationDao.save(l);
		}
		return new Esito(CostantiVgi.DESCR_OK, true);

	}

}
