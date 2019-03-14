package it.volpini.vgi.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.volpini.vgi.domain.Legenda;
import it.volpini.vgi.exceptions.ElementNotFoundException;
import it.volpini.vgi.general.Esito;
import it.volpini.vgi.service.LegendaService;

@RestController
@RequestMapping("/legenda")
public class LegendaRestController {
	
	@Autowired
	private LegendaService legendaService;
	
	@PostMapping()
	public Legenda newLegenda(@RequestBody @Valid Legenda legenda) {
		return legendaService.saveOrUpdate(legenda);
	}
	
	@GetMapping("/findAll")
	public List<Legenda> findAll(){
		return legendaService.findAll();
	}
	
	@PatchMapping()
	public Legenda update(@RequestBody @Valid Legenda legenda) {
		return legendaService.saveOrUpdate(legenda);
	}
	
	@DeleteMapping("/{idLegenda}")
	public Esito delete(@PathVariable Long idLegenda) throws ElementNotFoundException{
		return legendaService.delete(idLegenda);
	}

}
