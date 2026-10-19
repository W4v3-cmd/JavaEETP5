package com.example.demo.entities;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
@Entity
public class Contact implements Serializable{
	// l'email sert d'identifiant (cle primaire) donc il est forcement unique
	@Id
	private String email;
	private int note;

	// chaque contact appartient a une seule personne
	@ManyToOne
	private Personne personne;

	@Override
	public String toString() {
		return "Contact [email=" + email + ", note=" + note + "]";
	}
	public Contact() {
		super();
	}
	public Contact(String email,int note,Personne personne) {
		super();
		this.email = email;
		this.note=note;
		this.personne=personne;
	}
	// getteur et setteurs //
	public int getNote() {
		return note;
	}
	public void setNote(int note) {
		this.note = note;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Personne getPersonne() {
		return personne;
	}
	public void setPersonne(Personne personne) {
		this.personne = personne;
	}
}
