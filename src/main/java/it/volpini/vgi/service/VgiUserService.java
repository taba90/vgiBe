package it.volpini.vgi.service;

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
import it.volpini.vgi.general.CostantiVgi;
import it.volpini.vgi.general.Esito;
import it.volpini.vgi.general.Result;

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
	
	
	public Result<VgiUser> saveUser(Optional<VgiUser> opuser) {
		Result<VgiUser> result = new Result<>();
		Esito esito;
		VgiUser user = null;
		try {
			if (opuser.isPresent()) {
				user = opuser.get();
				String pwdCrypted = pwdEncoder.encode(user.getPassword());
				user.setPassword(pwdCrypted);
				user.setEnabled(true);
				RoleUser role=roleService.findByRoleName(RoleUserService.ROLE_USER);
				user.getRuoli().add(role);
				save(user);
				esito = new Esito(CostantiVgi.CODICE_OK, CostantiVgi.DESCR_OK);
			} else {
				esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE);
			}
		} catch (Throwable t) {
			esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE + t.getMessage());
		}
		result.setEsito(esito);
		return result;
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
	
	public Result<VgiUser> selfDeleteUser() {
		Result<VgiUser> result = new Result<VgiUser>();
		Esito esito;
		try {
			Optional<Long> id = getIdAuthenticatedUser();
			if (id.isPresent()) {
				Optional<VgiUser> vgiUser = findById(id.get());
				GhostUser ghost = new GhostUser(vgiUser);
				ghostDao.save(ghost);
				Optional<Long> idGu = ghostDao.findGhostUserIdByUsername(ghost.getUsername());
				if (idGu.isPresent()) {
					ghost = new GhostUser(idGu.get());
					List<UserLocation> locations = locationDao.findByVgiUser_id(vgiUser.get().getId());
					for (UserLocation l : locations) {
						l.setGosthUser(ghost);
						l.setVgiUser(null);
						locationDao.save(l);
					}
					esito = new Esito(CostantiVgi.CODICE_OK, CostantiVgi.DESCR_OK);
				} else {
					esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE
							+ " non sono riuscito a recuperare l'id del record sostitutivo dell'utente");
				}
			} else {
				esito = new Esito(CostantiVgi.CODICE_ERRORE,
						CostantiVgi.DESCR_ERRORE + " non sono riuscito a recuperare l'id dell'utente da cancellare");
			}
		} catch (Exception e) {
			esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE + e.getMessage());
		}
		result.setEsito(esito);
		return result;
	}

}
