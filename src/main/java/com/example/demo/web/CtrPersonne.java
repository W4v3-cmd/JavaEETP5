package com.example.demo.web;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.PersonneRepository;
import com.example.demo.entities.Personne;
@Controller
public class CtrPersonne {
	@Autowired
	PersonneRepository personneRepository;

	@RequestMapping("/personnes")
	public String personnes(Model model)
	{	List<Personne> personnes=personneRepository.findAll();
		model.addAttribute("personnes",personnes);
		return "personnes";}

	// ---------- ajout ----------
	@RequestMapping("/ajoutPersonne")
	public String ajoutPersonne()
	{	return "addPersonne";}
	@RequestMapping("/savePersonne")
	public String savePersonne(@RequestParam String nom,@RequestParam String prenom,@RequestParam String adresse,Model model)
	{	personneRepository.save(new Personne(nom,prenom,adresse));
		return personnes(model);}

	// ---------- suppression ----------
	@RequestMapping("/deletePersonne")
	public String deletePersonne(@RequestParam Long id,Model model)
	{	if (personneRepository.existsById(id))
			personneRepository.deleteById(id);
		return personnes(model);}

	// ---------- modification ----------
	@RequestMapping("/modifierPersonne")
	public String modifierPersonne(@RequestParam Long id,Model model)
	{	Personne p=personneRepository.findById(id).get();
		model.addAttribute("personne",p);
		return "modifPersonne";}
	@RequestMapping("/updatePersonne")
	public String updatePersonne(@RequestParam Long id,@RequestParam String nom,@RequestParam String prenom,@RequestParam String adresse,Model model)
	{	Personne p=personneRepository.findById(id).get();
		p.setNom(nom);
		p.setPrenom(prenom);
		p.setAdresse(adresse);
		personneRepository.save(p);
		return personnes(model);}

	// ---------- personnes sans contact ----------
	@RequestMapping("/sansContact")
	public String sansContact(Model model)
	{	model.addAttribute("personnes",personneRepository.findByContactsIsEmpty());
		return "sanscontact";}

	// ---------- personne avec la meilleure moyenne ----------
	@RequestMapping("/meilleureMoyenne")
	public String meilleureMoyenne(Model model)
	{	List<Personne> personnes=personneRepository.findAll();
		Personne meilleure=null;
		// on parcourt toutes les personnes et on garde celle qui a la plus grande moyenne
		for (Personne p : personnes)
		{	if (p.getContacts().size() > 0)
			{	if (meilleure == null || p.getMoyenne() > meilleure.getMoyenne())
					meilleure=p;
			}
		}
		model.addAttribute("personne",meilleure);
		return "meilleure";}
}
