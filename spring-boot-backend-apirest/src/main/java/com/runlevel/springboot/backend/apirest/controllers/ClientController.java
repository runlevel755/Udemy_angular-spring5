package com.runlevel.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<?> findById(@PathVariable("id") final Long id) {

		Client client = null;
		final Map<String, Object> response = new HashMap<>();

		try {
			client = clientService.findById(id);

		} catch (final DataAccessException e) {
			response.put("Mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (client == null) {
			response.put("Mensaje", "El cliente ID: ".concat(id.toString().concat(" No existe en la base de datos!!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Client>(client, HttpStatus.OK);
	}

	@PostMapping("/clients")
	// @ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody final Client client) {

		Client newClient = null;
		final Map<String, Object> response = new HashMap<>();

		try {
			newClient = clientService.save(client);
		} catch (final DataAccessException e) {
			response.put("Mensaje", "Error al guardar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido creado con éxito!");
		response.put("cliente", newClient);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/clients/{id}")
	// @ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<?> update(@RequestBody final Client client, @PathVariable final Long id) {

		final Client currentClient = clientService.findById(id);
		Client updatedClient = null;
		final Map<String, Object> response = new HashMap<>();

		if (currentClient == null) {
			response.put("Mensaje", "Error: No se pudo editar, el cliente ID: "
					.concat(id.toString().concat(" No existe en la base de datos!!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			currentClient.setName(client.getName());
			currentClient.setLastname(client.getLastname());
			currentClient.setEmail(client.getEmail());
			currentClient.setCreateAt(client.getCreateAt());

			updatedClient = clientService.save(currentClient);

		} catch (final DataAccessException e) {
			response.put("Mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		response.put("cliente", updatedClient);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/clients/{id}")
	// @ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable final Long id) {

		final Map<String, Object> response = new HashMap<>();

		try {
			clientService.delete(id);
		} catch (final DataAccessException e) {
			response.put("Mensaje", "Error al eliminar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente ha sido eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

}
