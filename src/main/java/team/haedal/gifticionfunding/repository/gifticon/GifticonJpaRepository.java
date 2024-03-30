package team.haedal.gifticionfunding.repository.gifticon;

import org.springframework.data.jpa.repository.JpaRepository;
import team.haedal.gifticionfunding.entity.gifticon.Gifticon;

public interface GifticonJpaRepository extends JpaRepository<Gifticon, Long> {
}
