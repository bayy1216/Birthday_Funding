package team.haedal.gifticionfunding.service.funding;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.haedal.gifticionfunding.domain.funding.FundingArticleCreate;
import team.haedal.gifticionfunding.domain.giftucon.GifticonCreate;
import team.haedal.gifticionfunding.entity.funding.FundingArticleSubscriber;
import team.haedal.gifticionfunding.entity.gifticon.Gifticon;
import team.haedal.gifticionfunding.entity.user.Friendship;
import team.haedal.gifticionfunding.entity.user.User;
import team.haedal.gifticionfunding.fixture.EntityGenerator;
import team.haedal.gifticionfunding.repository.funding.FundingArticleJpaRepository;
import team.haedal.gifticionfunding.repository.funding.FundingArticleSubscriberJpaRepository;
import team.haedal.gifticionfunding.repository.gifticon.GifticonJpaRepository;
import team.haedal.gifticionfunding.repository.user.FriendshipJpaRepository;
import team.haedal.gifticionfunding.repository.user.UserJpaRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
class FundingServiceTest extends EntityGenerator {
    @Autowired private FundingService fundingService;
    @Autowired private FundingArticleJpaRepository fundingArticleJpaRepository;
    @Autowired private GifticonJpaRepository gifticonJpaRepository;
    @Autowired private UserJpaRepository userJpaRepository;
    @Autowired private FriendshipJpaRepository friendshipJpaRepository;
    @Autowired private FundingArticleSubscriberJpaRepository fundingArticleSubscriberJpaRepository;


    @DisplayName("펀딩 생성이 정상 작동한다.")
    @Test
    void createFunding() {
        Gifticon gifticon1 = createGifticon("gifticon1");
        Gifticon gifticon2 = createGifticon("gifticon2");
        gifticonJpaRepository.saveAll(List.of(gifticon1, gifticon2));

        List<Long> gifticonsId = List.of(gifticon1.getId(), gifticon2.getId());

        FundingArticleCreate create = FundingArticleCreate.builder()
                .title("title")
                .content("content")
                .startAt(LocalDateTime.of(2022,10,24,0,0,0))
                .endAt(LocalDateTime.of(2022,10,28,0,0,0))
                .build();
        User user1 = super.createUser("email1", "nickname1");
        User user2 = super.createUser("email2", "nickname2");
        userJpaRepository.save(user2); userJpaRepository.save(user1);


        Long id = fundingService.createFunding(create, gifticonsId, user1.getId());

        assertThat(id).isNotNull();
    }

    @DisplayName("펀딩 생성시, 존재하지 않는 기프티콘 ID가 포함되어 있으면 IllegalArgumentException을 던진다.")
    @Test
    void createFundingWithNotExistGifticonId() {
        Gifticon gifticon1 = createGifticon("gifticon1");
        Gifticon gifticon2 = createGifticon("gifticon2");
        gifticonJpaRepository.saveAll(List.of(gifticon1, gifticon2));

        List<Long> gifticonsId = List.of(gifticon1.getId(), gifticon2.getId(), 100L);

        FundingArticleCreate create = FundingArticleCreate.builder()
                .title("title")
                .content("content")
                .startAt(LocalDateTime.of(2022,10,24,0,0,0))
                .endAt(LocalDateTime.of(2022,10,28,0,0,0))
                .build();
        User user1 = super.createUser("email1", "nickname1");
        userJpaRepository.save(user1);

        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            fundingService.createFunding(create, gifticonsId, user1.getId());
        });

        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 기프티콘 ID가 포함되어 있습니다.");
    }

    @DisplayName("펀딩 생성시, 친구들에게 펀딩구독 정보가 정상적으로 생성된다.")
    @Test
    void createFundingWithFriend() {
        //given
        Gifticon gifticon1 = createGifticon("gifticon1");
        Gifticon gifticon2 = createGifticon("gifticon2");
        gifticonJpaRepository.saveAll(List.of(gifticon1, gifticon2));

        List<Long> gifticonsId = List.of(gifticon1.getId(), gifticon2.getId());

        FundingArticleCreate create = FundingArticleCreate.builder()
                .title("title")
                .content("content")
                .startAt(LocalDateTime.of(2022,10,24,0,0,0))
                .endAt(LocalDateTime.of(2022,10,28,0,0,0))
                .build();
        User user1 = super.createUser("email1", "nickname1");
        User user2 = super.createUser("email2", "nickname2");
        User user3 = super.createUser("email3", "nickname3");
        userJpaRepository.save(user2); userJpaRepository.save(user1); userJpaRepository.save(user3);
        Friendship friendship1 = Friendship.create(user1, user2);
        Friendship friendship2 = Friendship.create(user2, user1);
        Friendship friendship3 = Friendship.create(user2, user3);
        Friendship friendship4 = Friendship.create(user3, user2);
        friendshipJpaRepository.save(friendship1); friendshipJpaRepository.save(friendship2);
        friendshipJpaRepository.save(friendship3); friendshipJpaRepository.save(friendship4);


        //when
        Long id = fundingService.createFunding(create, gifticonsId, user1.getId());
        List<FundingArticleSubscriber> subscribers = fundingArticleSubscriberJpaRepository.findAll();




        //then
        assertThat(id).isNotNull();
        assertThat(subscribers.size()).isEqualTo(3);
    }

    @Test
    void joinFunding() {
    }


    private Gifticon createGifticon(String name){
        GifticonCreate create = GifticonCreate.builder()
                .brand("brand")
                .name(name)
                .price(10000)
                .description("ddsecs")
                .expirationPeriod(200)
                .imageUrl("dasdas")
                .category("category")
                .build();
        return Gifticon.create(create);

    }
}