package it.volpini.vgi.restcontroller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.volpini.vgi.domain.IntervalloEta;
import it.volpini.vgi.general.Result;
import it.volpini.vgi.service.IntervalloEtaService;

@RestController
@RequestMapping("/intervallo")
public class IntervalloEtaRestController {
	
	@Autowired
	private IntervalloEtaService intervalloService;

	@PostMapping("/add")
	public Result<IntervalloEta> save (@RequestBody IntervalloEta ie) {
		Optional<IntervalloEta> oIe=Optional.ofNullable(ie);
		return intervalloService.save(oIe);
	}
	
	@PatchMapping("/update")
	public Result<IntervalloEta> update (@RequestBody IntervalloEta ie) {
		Optional<IntervalloEta> oIe=Optional.ofNullable(ie);
		return intervalloService.update(oIe);
	}
	
	@GetMapping("/all")
	public Result<IntervalloEta> getAll () {
		return intervalloService.findAllIntervalli();
	}
}
