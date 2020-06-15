package com.runlevel.springboot.backend.apirest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runlevel.springboot.backend.apirest.entity.Client;

public interface IClientDao extends JpaRepository<Client, Long> {

}
