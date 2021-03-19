package com.connexence.testPratique;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
public class ChattonController
{
	@Autowired
	private ChattonRepository chattonRepository;

	@GetMapping("/chattons")
	public List<Chatton> getChattons()
	{
		List<Chatton> chattons = chattonRepository.findAll();
		if (chattons.isEmpty())
		{
			Chatton premierChatton = new Chatton();
			premierChatton.setNom("Scott");
			premierChatton.setAge(4);
			chattonRepository.save(premierChatton);

			Chatton deuxiemeChatton = new Chatton();
			deuxiemeChatton.setNom("Marie-Antoinette");
			deuxiemeChatton.setAge(2);
			chattonRepository.save(deuxiemeChatton);

			chattons = chattonRepository.findAll();
		}

		chattons = chattons.stream().filter(f-> f.getAge() <= 15).collect(Collectors.toList());
		//chattons.sort(Comparator.comparing(Chatton::getNom).reversed());
		Collections.reverse(chattons);

		// TODO: Filtrer et inverser la liste des chattons ici
		
		return chattons;
	}

	@RequestMapping(value = "/chattons", method = RequestMethod.POST)
	public ResponseEntity setChattons(@RequestBody Chatton chatton){
		if (chatton.getAge() > 30 || chatton.getNom().isEmpty())
			return new ResponseEntity(HttpStatus.BAD_REQUEST);

		chattonRepository.save(chatton);

		return new ResponseEntity(HttpStatus.OK);
	}

}
