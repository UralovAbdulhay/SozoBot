package asia.sozo.sozobot.repository;

import asia.sozo.sozobot.entity.TgUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<TgUser, Long> {


}
