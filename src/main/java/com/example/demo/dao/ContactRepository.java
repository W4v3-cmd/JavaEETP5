package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Contact;

// la cle primaire de Contact c'est l'email donc c'est un String
public interface ContactRepository extends JpaRepository<Contact,String>
{
	List<Contact> findByNoteLessThan(int note);
	List<Contact> findByNote(int note);
}
