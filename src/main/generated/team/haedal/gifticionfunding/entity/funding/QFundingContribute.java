package team.haedal.gifticionfunding.entity.funding;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFundingContribute is a Querydsl query type for FundingContribute
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFundingContribute extends EntityPathBase<FundingContribute> {

    private static final long serialVersionUID = 97542467L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFundingContribute fundingContribute = new QFundingContribute("fundingContribute");

    public final team.haedal.gifticionfunding.entity.common.QBaseTimeEntity _super = new team.haedal.gifticionfunding.entity.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QFundingArticle fundingArticle;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final team.haedal.gifticionfunding.entity.user.QUser user;

    public final team.haedal.gifticionfunding.entity.gifticon.QUserGifticon userGifticon;

    public QFundingContribute(String variable) {
        this(FundingContribute.class, forVariable(variable), INITS);
    }

    public QFundingContribute(Path<? extends FundingContribute> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFundingContribute(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFundingContribute(PathMetadata metadata, PathInits inits) {
        this(FundingContribute.class, metadata, inits);
    }

    public QFundingContribute(Class<? extends FundingContribute> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fundingArticle = inits.isInitialized("fundingArticle") ? new QFundingArticle(forProperty("fundingArticle"), inits.get("fundingArticle")) : null;
        this.user = inits.isInitialized("user") ? new team.haedal.gifticionfunding.entity.user.QUser(forProperty("user")) : null;
        this.userGifticon = inits.isInitialized("userGifticon") ? new team.haedal.gifticionfunding.entity.gifticon.QUserGifticon(forProperty("userGifticon"), inits.get("userGifticon")) : null;
    }

}

