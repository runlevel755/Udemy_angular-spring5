package com.runlevel.springboot.backend.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.runlevel.springboot.backend.apirest.dao.IClientDao;
import com.runlevel.springboot.backend.apirest.entity.Client;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	private IClientDao clientDao;

	@Override
	@Transactional(readOnly = true)
	public List<Client> findAll() {

		return clientDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Client findById(final Long id) {
		return clientDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Client save(final Client client) {
		return clientDao.save(client);
	}

	@Override
	@Transactional
	public void delete(final Long id) {
		clientDao.deleteById(id);

	}

}
