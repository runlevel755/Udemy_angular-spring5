package com.runlevel.springboot.backend.apirest.services;

import java.util.List;

import com.runlevel.springboot.backend.apirest.entity.Client;

public interface IClientService {

	public List<Client> findAll();

	public Client findById(Long id);

	public Client save(Client client);

	public void delete(Long id);
}
