package steam.game.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import steam.game.entity.Gamer;

public interface GamerDao extends JpaRepository<Gamer, Long> {

}
