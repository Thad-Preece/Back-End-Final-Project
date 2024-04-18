package steam.game.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import steam.game.entity.SteamGame;

public interface SteamGameDao extends JpaRepository<SteamGame, Long> {

}
