package steam.game.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import steam.game.controller.model.SteamGameData;
import steam.game.controller.model.SteamGameData.SteamGameGameStudio;
import steam.game.controller.model.SteamGameData.SteamGameGamer;
import steam.game.service.SteamGameService;

@RestController
@RequestMapping("/steam_game")
@Slf4j
public class SteamGameController {

	@Autowired
	private SteamGameService steamGameService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public SteamGameData createSteamGame(@RequestBody SteamGameData steamGameData) {
		log.info("Request for Steam listing received= {}", steamGameData);
		return steamGameService.saveSteamGame(steamGameData);
	}
	
	@PutMapping("/{steamGameId}")
	public SteamGameData updateSteamGame(@PathVariable Long steamGameId,
			@RequestBody SteamGameData steamGameData) {
		
		steamGameData.setSteamGameId(steamGameId);
		log.info("Updating steam game {}", steamGameData);
		return steamGameService.saveSteamGame(steamGameData);
	}
	
	@PostMapping("/{steamGameId}/gameStudio")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SteamGameGameStudio createGameStudio(@PathVariable Long steamGameId,
			@RequestBody SteamGameGameStudio steamGameGameStudio) {
		log.info("Game studio {} to Steam game with ID {}", steamGameGameStudio, steamGameId);
		return steamGameService.saveGameStudio(steamGameId, steamGameGameStudio);
	}
	
	@PostMapping("/{steamGameId}/gamer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SteamGameGamer createSteamGameGamer(@PathVariable Long steamGameId,
			@RequestBody SteamGameGamer steamGameGamer) {
		log.info("Gamer account {} with steam game ID= {}", steamGameGamer, steamGameId);
		return steamGameService.saveGamer(steamGameId, steamGameGamer);
	}
	
	@GetMapping
	public List<SteamGameData> listAllGames() {
		return steamGameService.retrieveAllGames();
	}
	
	@GetMapping("/{steamGameId}")
	public SteamGameData retrieveSteamGame(@PathVariable Long steamGameId) {
		return steamGameService.retrieveSteamGameWithId(steamGameId);
	}
	
	@DeleteMapping("/{steamGameId}")
	public Map<String, String> deleteSteamGameById(@PathVariable Long steamGameId) {
		log.info("Deleting steam game with ID= {}", steamGameId);
		steamGameService.deleteSteamGameById(steamGameId);
		return Map.of("message", "Deletion of steam game with ID=" + steamGameId + " was successful.");
	}
	
}
