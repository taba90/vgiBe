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

import it.volpini.vgi.service.LegendaService;
import it.volpini.vgi.domain.Legenda;
import it.volpini.vgi.exceptions.LinkedElementsExistException;
import it.volpini.vgi.exceptions.ElementNotFoundException;
import it.volpini.vgi.exceptions.NullParamException;
import it.volpini.vgi.general.Esito;

@RestController
@RequestMapping("/legenda")
public class LegendaRestController {
	
	@Autowired
	private LegendaService legendaService;
	
	@PostMapping()
	public Legenda newLegenda(@RequestBody Legenda legenda) throws NullParamException{
		Optional<Legenda> opLegenda=Optional.ofNullable(legenda);
		return legendaService.saveOrUpdate(opLegenda);
	}
	
	@GetMapping("/findAll")
	public List<Legenda> findAll(){
		return legendaService.findAll();
	}
	
	@PatchMapping()
	public Legenda update(@RequestBody Legenda legenda, @PathVariable Long idLegenda) throws NullParamException{
		Optional<Legenda> opLegenda=Optional.ofNullable(legenda);
		return legendaService.saveOrUpdate(opLegenda);
	}
	
	@DeleteMapping("/{idLegenda}")
	public Esito delete(@PathVariable Long idLegenda) throws ElementNotFoundException, NullParamException, LinkedElementsExistException{
		return legendaService.delete(idLegenda);
	}

}
