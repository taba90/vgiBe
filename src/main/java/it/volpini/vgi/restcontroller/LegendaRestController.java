package it.volpini.vgi.restcontroller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.volpini.vgi.service.LegendaService;
import it.volpini.vgi.domain.Legenda;
import it.volpini.vgi.general.Result;

@RestController
@RequestMapping("/legenda")
public class LegendaRestController {
	
	@Autowired
	private LegendaService legendaService;
	
	@PostMapping("/new")
	public Result<Legenda> newLegenda(@RequestBody Legenda legenda){
		Optional<Legenda> opLegenda=Optional.ofNullable(legenda);
		return legendaService.saveOrUpdate(opLegenda);
	}
	
	@GetMapping("/findAll")
	public Result<Legenda> findAll(){
		return legendaService.findAllLegenda();
	}
	
	@PatchMapping("/update")
	public Result<Legenda> update(@RequestBody Legenda legenda){
		Optional<Legenda> opLegenda=Optional.ofNullable(legenda);
		return legendaService.saveOrUpdate(opLegenda);
	}

}
