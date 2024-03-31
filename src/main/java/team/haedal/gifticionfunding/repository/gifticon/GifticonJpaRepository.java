package team.haedal.gifticionfunding.repository.gifticon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import team.haedal.gifticionfunding.core.exception.ResourceNotFoundException;
import team.haedal.gifticionfunding.entity.gifticon.Gifticon;

public interface GifticonJpaRepository extends JpaRepository<Gifticon, Long> {

    default @NonNull Gifticon getById(Long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("Gifticon", id));
    }
}
