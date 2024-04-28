package team.haedal.gifticionfunding.repository.gifticon;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.haedal.gifticionfunding.entity.gifticon.UserGifticon;

import java.util.List;

public interface UserGifticonJpaRepository extends JpaRepository<UserGifticon, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select ug from UserGifticon ug where ug.id in :id")
    List<UserGifticon> findAllByGifticonIdsInForUpdate(@Param("ids") List<Long> gifticonIds);
}
