package steam.game.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import steam.game.entity.GameStudio;

public interface GameStudioDao extends JpaRepository<GameStudio, Long> {

}
