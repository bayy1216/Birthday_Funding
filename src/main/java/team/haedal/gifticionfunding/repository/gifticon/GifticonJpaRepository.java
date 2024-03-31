package team.haedal.gifticionfunding.repository.gifticon;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import team.haedal.gifticionfunding.core.exception.ResourceNotFoundException;
import team.haedal.gifticionfunding.entity.gifticon.Gifticon;

import java.util.Optional;

public interface GifticonJpaRepository extends JpaRepository<Gifticon, Long> {

    default @NonNull Gifticon getById(Long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("Gifticon", id));
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select g from Gifticon g where g.id = :id")
    Optional<Gifticon> findByIdForUpdate(@Param("id") Long id);
}
