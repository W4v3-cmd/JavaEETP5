package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Personne;

public interface PersonneRepository extends JpaRepository<Personne,Long>
{
	// spring genere la requete tout seul : les personnes dont la liste de contacts est vide
	List<Personne> findByContactsIsEmpty();
}
