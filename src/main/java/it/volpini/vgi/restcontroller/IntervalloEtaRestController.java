package it.volpini.vgi.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.volpini.vgi.domain.IntervalloEta;
import it.volpini.vgi.exceptions.LinkedElementsExistException;
import it.volpini.vgi.exceptions.NullParamException;
import it.volpini.vgi.general.Esito;
import it.volpini.vgi.service.IntervalloEtaService;

@RestController
@RequestMapping("/intervallo")
public class IntervalloEtaRestController {
	
	@Autowired
	private IntervalloEtaService intervalloService;

	@PostMapping()
	public IntervalloEta save (@RequestBody IntervalloEta ie) throws NullParamException {
		
		return intervalloService.saveOrUpdate(Optional.ofNullable(ie));
	}
	
	@PatchMapping()
	public IntervalloEta update (@RequestBody IntervalloEta ie) throws NullParamException {
		return intervalloService.saveOrUpdate(Optional.ofNullable(ie));
	}
	
	@GetMapping("/all")
	public List<IntervalloEta> getAll () {
		return intervalloService.findAll();
	}
	
	@DeleteMapping("/{idIntervallo}")
	public Esito delete (@PathVariable Long idIntervallo) throws LinkedElementsExistException {
		return intervalloService.deleteIntervallo(idIntervallo);
	}
}
