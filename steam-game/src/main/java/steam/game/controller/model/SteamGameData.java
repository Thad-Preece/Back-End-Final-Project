package steam.game.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import steam.game.entity.GameStudio;
import steam.game.entity.Gamer;
import steam.game.entity.SteamGame;

@Data
@NoArgsConstructor
public class SteamGameData {

	private Long steamGameId;
	private String steamGameName;
	private String steamGameCategory;
	private String steamGameReleaseDate;
	private String steamGamePrice;
	private String steamGamePublisher;
	private String steamGameSpecRequirements;
	private String steamGameReviews;
	
	private Set<SteamGameGamer> gamers = new HashSet<>();
	
	public SteamGameData(SteamGame steamGame) {
		this.steamGameId = steamGame.getSteamGameId();
		this.steamGameName = steamGame.getSteamGameName();
		this.steamGameCategory = steamGame.getSteamGameCategory();
		this.steamGameReleaseDate = steamGame.getSteamGameReleaseDate();
		this.steamGamePrice = steamGame.getSteamGamePrice();
		this.steamGamePublisher = steamGame.getSteamGamePublisher();
		this.steamGameSpecRequirements = steamGame.getSteamGameSpecRequirements();
		this.steamGameReviews = steamGame.getSteamGameReviews();
		
		for(Gamer gamer : steamGame.getGamers()) {
			gamers.add(new SteamGameGamer(gamer));
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class SteamGameGameStudio {
		
		private Long gameStudioId;
		private String gameDevelopers;
		private String studioAddress;
		private String studioName;
		private String studioPhoneNumber;
		private String gamesDeveloped;
		
		private Set<SteamGameData> steamGames = new HashSet<>();
		
		public SteamGameGameStudio(GameStudio gameStudio) {
			this.gameStudioId = gameStudio.getGameStudioId();
			this.gameDevelopers = gameStudio.getGameDevelopers();
			this.studioAddress = gameStudio.getStudioAddress();
			this.studioName = gameStudio.getStudioName();
			this.studioPhoneNumber = gameStudio.getStudioPhoneNumber();
			this.gamesDeveloped = gameStudio.getGamesDeveloped();
		
		
		for(SteamGame steamGame : gameStudio.getSteamGame()) {
			steamGames.add(new SteamGameData(steamGame));
		}
	}
}
	
	@Data
	@NoArgsConstructor
	public static class SteamGameGamer {
		
		private Long gamerId;
		private String gamerTag;
		private String gamerFirstName;
		private String gamerLastName;
		private String gamerGameLibrary;

		public SteamGameGamer(Gamer gamer) {
			this.gamerId = gamer.getGamerId();
			this.gamerTag = gamer.getGamerTag();
			this.gamerFirstName = gamer.getGamerFirstName();
			this.gamerLastName = gamer.getGamerLastName();
			this.gamerGameLibrary = gamer.getGamerGameLibrary();
		}
		
	}
	
}
