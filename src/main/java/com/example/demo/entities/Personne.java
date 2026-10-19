package com.example.demo.entities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
@Entity
public class Personne implements Serializable{
	@Id @GeneratedValue
	private Long id;
	private String nom;
	private String prenom;
	private String adresse;

	// cascade REMOVE : si on supprime la personne ca supprime aussi ses contacts
	@OneToMany(mappedBy="personne", cascade=CascadeType.REMOVE)
	private List<Contact> contacts = new ArrayList<Contact>();

	public Personne() {
		super();
	}
	public Personne(String nom, String prenom, String adresse) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
	}
	@Override
	public String toString() {
		return "Personne [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse + "]";
	}
	// calcule la moyenne des notes des contacts
	public double getMoyenne() {
		if (contacts.size() == 0)
			return 0;
		int total = 0;
		for (Contact c : contacts) {
			total = total + c.getNote();
		}
		return (double) total / contacts.size();
	}
	// getteur et setteurs //
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
}
