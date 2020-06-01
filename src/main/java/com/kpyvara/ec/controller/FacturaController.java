package com.kpyvara.ec.controller;

 
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kpyvara.ec.model.Factura;
import com.kpyvara.ec.service.impl.FacturaServiceImpl;

@RestController
@RequestMapping("/facturas")
public class FacturaController {
	@Autowired
	FacturaServiceImpl service;
	@GetMapping
	public ResponseEntity<List<Factura>> getFactura() {
		List<Factura> pacientes = service.list();
		return new ResponseEntity<>(pacientes, HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<Object> setFactura( @Valid @RequestBody Factura paciente) {
		Factura pac = service.save(paciente);
		// pacientes/4
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pac.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	@GetMapping(value="/generarPdf/{codigo}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> getPdf(@PathVariable("codigo") String codigo ) {
		byte[] docuemento = service.getOneByte(codigo);
		return new ResponseEntity<>(docuemento, HttpStatus.OK);
	}

}