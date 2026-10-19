package com.example.demo.web;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.ContactRepository;
import com.example.demo.dao.PersonneRepository;
import com.example.demo.entities.Contact;
import com.example.demo.entities.Personne;
@Controller
public class Ctr {
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	PersonneRepository personneRepository;
	@RequestMapping("/")
	public String index() {return "index";}
	@RequestMapping("/contacts")
		public String contacts(Model model) throws InterruptedException
	{List<Contact> contacts=contactRepository.findAll();
									model.addAttribute("contacts",contacts);
							return "liste";}

	// ---------- ajout d'un contact ----------
	@RequestMapping("/ajout")
	public String ajout(Model model)
	{	// il faut la liste des personnes pour la liste deroulante
		model.addAttribute("personnes",personneRepository.findAll());
		return "add";}
	@RequestMapping("/save")
	public String save(@RequestParam String email,@RequestParam String note,@RequestParam Long personneId,Model model) throws InterruptedException
	{	// on verifie que l'email existe pas deja
		if (contactRepository.existsById(email))
		{	model.addAttribute("erreur","Cet email existe deja !");
			return ajout(model);
		}
		Personne p=personneRepository.findById(personneId).get();
		contactRepository.save(new Contact(email,Integer.parseInt(note),p));
		 return contacts(model);}

	// ---------- suppression d'un contact ----------
	@RequestMapping("/delete")
	public String delete()
	{	return "delete";}
	@RequestMapping("/deleteSave")
	public String deleteSave(@RequestParam String email,Model model)
	{
	Optional<Contact> contact=contactRepository.findById(email);
		 if (contact.isPresent())
		 {	contactRepository.deleteById(email);
			model.addAttribute("message","Le contact "+email+" a ete supprime");
		 }
		 else
			model.addAttribute("message","Aucun contact avec cet email");
		return "delete";}

	// ---------- modification d'un contact ----------
	@RequestMapping("/modifierContact")
	public String modifierContact(@RequestParam String email,Model model)
	{	Contact c=contactRepository.findById(email).get();
		model.addAttribute("contact",c);
		model.addAttribute("personnes",personneRepository.findAll());
		return "modifContact";}
	@RequestMapping("/updateContact")
	public String updateContact(@RequestParam String email,@RequestParam String note,@RequestParam Long personneId,Model model) throws InterruptedException
	{	Contact c=contactRepository.findById(email).get();
		c.setNote(Integer.parseInt(note));
		c.setPersonne(personneRepository.findById(personneId).get());
		contactRepository.save(c);
		return contacts(model);}

	// ---------- recherches sur les notes ----------
	@RequestMapping("/malNotes")
	public String malNotes(Model model)
	{	List<Contact> contacts=contactRepository.findByNoteLessThan(10);
		model.addAttribute("contacts",contacts);
		return "malnotes";}
	@RequestMapping("/recherche")
	public String recherche(@RequestParam(required = false) Integer note, Model model)
	{	if (note != null)
		{	List<Contact> contacts=contactRepository.findByNote(note);
			model.addAttribute("contacts",contacts);
		}
		return "recherche";}

	// ---------- trouver la personne qui a un contact donne ----------
	@RequestMapping("/proprietaire")
	public String proprietaire(@RequestParam(required = false) String email, Model model)
	{	if (email != null)
		{	Optional<Contact> contact=contactRepository.findById(email);
			if (contact.isPresent())
				model.addAttribute("personne",contact.get().getPersonne());
			else
				model.addAttribute("erreur","Aucun contact avec cet email");
		}
		return "proprietaire";}
}
