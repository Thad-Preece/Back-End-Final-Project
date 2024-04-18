package steam.game.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import steam.game.controller.model.SteamGameData;
import steam.game.controller.model.SteamGameData.SteamGameGameStudio;
import steam.game.controller.model.SteamGameData.SteamGameGamer;
import steam.game.dao.GameStudioDao;
import steam.game.dao.GamerDao;
import steam.game.dao.SteamGameDao;
import steam.game.entity.GameStudio;
import steam.game.entity.Gamer;
import steam.game.entity.SteamGame;

@Service
public class SteamGameService {
	
	@Autowired
	private SteamGameDao steamGameDao;
	@Autowired
	private GameStudioDao gameStudioDao;
	@Autowired 
	private GamerDao gamerDao;

	public SteamGameData saveSteamGame(SteamGameData steamGameData) {
		Long steamGameId = steamGameData.getSteamGameId();
		SteamGame steamGame = findOrCreateSteamGame(steamGameId);
		copySteamGameFields(steamGame, steamGameData);
		return new SteamGameData(steamGameDao.save(steamGame));
		
	}
	
	private void copySteamGameFields(SteamGame steamGame, SteamGameData steamGameData) {
		steamGame.setSteamGameId(steamGameData.getSteamGameId());
		steamGame.setSteamGameName(steamGameData.getSteamGameName());
		steamGame.setSteamGamePrice(steamGameData.getSteamGamePrice());
		steamGame.setSteamGamePublisher(steamGameData.getSteamGamePublisher());
		steamGame.setSteamGameCategory(steamGameData.getSteamGameCategory());
		steamGame.setSteamGameReleaseDate(steamGameData.getSteamGameReleaseDate());
		steamGame.setSteamGameReviews(steamGameData.getSteamGameReviews());
		steamGame.setSteamGameSpecRequirements(steamGameData.getSteamGameSpecRequirements());
	}

	private SteamGame findOrCreateSteamGame(Long steamGameId) {
		
		if(steamGameId == null) {
			return new SteamGame();
		}else {
			return findSteamGameById(steamGameId);
		}
	}

	public SteamGame findSteamGameById(Long steamGameId) {
		
		return steamGameDao.findById(steamGameId)
				.orElseThrow(() -> new NoSuchElementException("Steam game with ID= " + steamGameId + " was not found."));
		
	}

	@Transactional(readOnly = false)
	public SteamGameGameStudio saveGameStudio(Long steamGameId, SteamGameGameStudio steamGameGameStudio) {
		
		SteamGame steamGame = findSteamGameById(steamGameId);
		Long gameStudioId = steamGameGameStudio.getGameStudioId();
		GameStudio gameStudio = findOrCreateGameStudio(steamGameId, gameStudioId);
		copyGameStudioFields(gameStudio, steamGameGameStudio);
		
		steamGame.setGameStudio(gameStudio);
		gameStudio.getSteamGame().add(steamGame);
		GameStudio dbGameStudio = gameStudioDao.save(gameStudio);
		return new SteamGameGameStudio(dbGameStudio);
	}

	private void copyGameStudioFields(GameStudio gameStudio, SteamGameGameStudio steamGameGameStudio) {
		gameStudio.setGameStudioId(steamGameGameStudio.getGameStudioId());
		gameStudio.setGameDevelopers(steamGameGameStudio.getGameDevelopers());
		gameStudio.setGamesDeveloped(steamGameGameStudio.getGamesDeveloped());
		gameStudio.setStudioAddress(steamGameGameStudio.getStudioAddress());
		gameStudio.setStudioPhoneNumber(steamGameGameStudio.getStudioPhoneNumber());
		gameStudio.setStudioName(steamGameGameStudio.getStudioName());
		
	}

	private GameStudio findOrCreateGameStudio(Long steamGameId, Long gameStudioId) {
		
		if(gameStudioId == null) {
			return new GameStudio();
		}else {
			return findGameameStudioById(steamGameId, gameStudioId);
		}
		
	}

	private GameStudio findGameameStudioById(Long steamGameId, Long gameStudioId) {
		return gameStudioDao.findById(gameStudioId)
				.orElseThrow(() -> new NoSuchElementException("Game studio with ID= {}" + gameStudioId + " was not found."));
	}

	public SteamGameGamer saveGamer(Long steamGameId, SteamGameGamer steamGameGamer) {
		
		SteamGame steamGame = findSteamGameById(steamGameId);
		Long gamerId = steamGameGamer.getGamerId();
		Gamer gamer = findOrCreateGamer(steamGameId, gamerId);
		copyGamerFields(gamer, steamGameGamer);
		
		gamer.getSteamGames().add(steamGame);
		steamGame.getGamers().add(gamer);
		Gamer dbGamer = gamerDao.save(gamer);
		return new SteamGameGamer(dbGamer);
		
	}

	private void copyGamerFields(Gamer gamer, SteamGameGamer steamGameGamer) {
		gamer.setGamerFirstName(steamGameGamer.getGamerFirstName());
		gamer.setGamerLastName(steamGameGamer.getGamerLastName());
		gamer.setGamerGameLibrary(steamGameGamer.getGamerGameLibrary());
		gamer.setGamerId(steamGameGamer.getGamerId());
		gamer.setGamerTag(steamGameGamer.getGamerTag());
		
	}

	private Gamer findOrCreateGamer(Long steamGameId, Long gamerId) {
		
		if(gamerId == null) {
			return new Gamer();
		}else {
			return findGamerById(steamGameId, gamerId);
		}
		
	}

	private Gamer findGamerById(Long steamGameId, Long gamerId) {
		
		Gamer gamer = gamerDao.findById(gamerId)
			.orElseThrow(() -> new NoSuchElementException("Gamer with ID= {}" + gamerId + " was not found."));
		
		for(SteamGame steamGame : gamer.getSteamGames()) {
			if(steamGame.getSteamGameId() == steamGameId) {
				boolean isTrue = true;
			if(isTrue) {
				return gamer;
			}else {
				throw new IllegalArgumentException("Steam game= " + steamGame + " was not found.");
			}
		}
	}
		return gamer;
	}


	@Transactional
	public SteamGameData retrieveSteamGameWithId(Long steamGameId) {
		SteamGame steamGame = findSteamGameById(steamGameId);
		SteamGameData steamGameData = new SteamGameData(steamGame);
		return steamGameData;
	}

	public void deleteSteamGameById(Long steamGameId) {
		SteamGame steamGame = findSteamGameById(steamGameId);
		steamGameDao.delete(steamGame);
	}

	public List<SteamGameData> retrieveAllGames() {
		steamGameDao.findAll();
		List<SteamGameData> games = new LinkedList<>();
		
		for(SteamGame steamGame : steamGameDao.findAll()) {
			SteamGameData sgd = new SteamGameData(steamGame);
			sgd.getGamers().clear();
			games.add(sgd);
		}
		return games;
	}
		
	
	
}
