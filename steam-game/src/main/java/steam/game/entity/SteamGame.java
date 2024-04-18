package steam.game.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class SteamGame {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long steamGameId;
	
	private String steamGameName;
	private String steamGameCategory;
	private String steamGameReleaseDate;
	private String steamGamePrice;
	private String steamGamePublisher;
	private String steamGameSpecRequirements;
	private String steamGameReviews;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "steam_game_gamer", joinColumns =
	@JoinColumn(name = "steam_game_id"), inverseJoinColumns =
	@JoinColumn(name = "gamer_id"))
	private Set<Gamer> gamers = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "game_studio_id")
	private GameStudio gameStudio;
	
}
