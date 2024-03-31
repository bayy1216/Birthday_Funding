package team.haedal.gifticionfunding.repository.gifticon;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import team.haedal.gifticionfunding.domain.GifticonSort;
import team.haedal.gifticionfunding.entity.gifticon.Gifticon;
import team.haedal.gifticionfunding.domain.GifticonSearch;

import java.util.List;

import static team.haedal.gifticionfunding.entity.gifticon.QGifticon.gifticon;

@RequiredArgsConstructor
@Repository
public class GifticonQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<Gifticon> getGifticonPage(PageRequest pageRequest, GifticonSearch gifticonSearch){
        Long count = jpaQueryFactory.from(gifticon)
                .select(gifticon.count())
                .where(
                        eqName(gifticonSearch.getName()),
                        eqCategory(gifticonSearch.getCategory()),
                        eqBrand(gifticonSearch.getBrand()),
                        betweenPrice(gifticonSearch.getPriceMin(), gifticonSearch.getPriceMax())
                )
                .fetchOne();
        List<Gifticon> gifticons = jpaQueryFactory.from(gifticon)
                .select(gifticon)
                .where(
                        eqName(gifticonSearch.getName()),
                        eqCategory(gifticonSearch.getCategory()),
                        eqBrand(gifticonSearch.getBrand()),
                        betweenPrice(gifticonSearch.getPriceMin(), gifticonSearch.getPriceMax())
                )
                .orderBy(getOrderSpecifier(gifticonSearch.getGifticonSort()))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();
        return new PageImpl<>(gifticons, pageRequest, count);
    }


    private BooleanExpression eqName(String name){
        return name != null ? gifticon.name.eq(name) : null;
    }

    private BooleanExpression eqCategory(String category){
        return category != null ? gifticon.category.eq(category) : null;
    }

    private BooleanExpression eqBrand(String brand){
        return brand != null ? gifticon.brand.eq(brand) : null;
    }

    private BooleanExpression betweenPrice(Integer priceMin, Integer priceMax){
        if(priceMin == null && priceMax == null){
            return null;
        }
        if(priceMin == null){
            return gifticon.price.loe(priceMax);
        }
        if(priceMax == null){
            return gifticon.price.goe(priceMin);
        }
        return gifticon.price.between(priceMin, priceMax);
    }

    private OrderSpecifier<?> getOrderSpecifier(GifticonSort gifticonSort){
        if (gifticonSort == null) {
            return gifticon.id.desc();
        }
        return switch (gifticonSort) {
            case PRICE_ASC -> gifticon.price.asc();
            case PRICE_DESC -> gifticon.price.desc();
            case NAME_ASC -> gifticon.name.asc();
            case NAME_DESC -> gifticon.name.desc();
        };
    }
}
