package it.volpini.vgi.restcontroller;

import java.util.Optional;

import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.volpini.vgi.service.GeodataProcessService;

@RestController
@RequestMapping("/process")
public class GeodataProcessRestController {
	
	@Autowired
	private GeodataProcessService process;
	
	@GetMapping("/heathmap")
	public byte[] getHeathMap(@RequestParam(value="annoDa", required=false) Integer annoA, 
			@RequestParam(value="annoA", required=false) Integer annoB, 
			@RequestParam(value="idLegenda", required=false) Long idLegenda, 
			@RequestParam(value="geom", required=false) Geometry geom,
			@RequestParam(value="width", required=false) Integer width,
			@RequestParam(value="height", required=false) Integer height,
			@RequestParam(value="radius", required=false) Integer kernelRadius,
			@RequestParam(value="pixelPerCell") Integer pixelPerCell) throws Exception {
		return process.generateHeathMap(Optional.ofNullable(annoA), Optional.ofNullable(annoB), Optional.ofNullable(idLegenda), 
				Optional.ofNullable(geom), Optional.ofNullable(width), Optional.ofNullable(height), Optional.ofNullable(kernelRadius), 
				Optional.ofNullable(pixelPerCell));
	}
	
	
}
