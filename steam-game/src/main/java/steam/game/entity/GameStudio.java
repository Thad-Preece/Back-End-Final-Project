package steam.game.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class GameStudio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gameStudioId;
	
	private String gameDevelopers;
	private String studioAddress;
	private String studioName;
	private String studioPhoneNumber;
	private String gamesDeveloped;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "gameStudio", cascade = CascadeType.ALL, 
	orphanRemoval = true)
	private Set<SteamGame> steamGame = new HashSet<>();
	
}
