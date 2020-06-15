package com.runlevel.springboot.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.runlevel.springboot.backend.apirest.entity.Client;
import com.runlevel.springboot.backend.apirest.services.IClientService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ClientController {

	@Autowired
	private IClientService clientService;

	@GetMapping("/clients")
	public List<Client> findAll() {
		return clientService.findAll();
	}

	@GetMapping("/clients/{id}")
	public Client findById(@PathVariable("id") final Long id) {
		return clientService.findById(id);
	}

	@PostMapping("/clients")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Client create(@RequestBody final Client client) {
		return clientService.save(client);
	}

	@PutMapping("/clients/{id}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Client update(@RequestBody final Client client, @PathVariable final Long id) {
		final Client updatedClient = clientService.findById(id);

		updatedClient.setName(client.getName());
		updatedClient.setLastname(client.getLastname());
		updatedClient.setEmail(client.getEmail());

		return clientService.save(updatedClient);
	}

	@DeleteMapping("/clients/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable final Long id) {
		clientService.delete(id);
	}

}
