package team.haedal.gifticionfunding.entity.funding;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFundingArticleGifticon is a Querydsl query type for FundingArticleGifticon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFundingArticleGifticon extends EntityPathBase<FundingArticleGifticon> {

    private static final long serialVersionUID = 1092406185L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFundingArticleGifticon fundingArticleGifticon = new QFundingArticleGifticon("fundingArticleGifticon");

    public final team.haedal.gifticionfunding.entity.common.QBaseTimeEntity _super = new team.haedal.gifticionfunding.entity.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QFundingArticle fundingArticle;

    public final team.haedal.gifticionfunding.entity.gifticon.QGifticon gifticon;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public QFundingArticleGifticon(String variable) {
        this(FundingArticleGifticon.class, forVariable(variable), INITS);
    }

    public QFundingArticleGifticon(Path<? extends FundingArticleGifticon> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFundingArticleGifticon(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFundingArticleGifticon(PathMetadata metadata, PathInits inits) {
        this(FundingArticleGifticon.class, metadata, inits);
    }

    public QFundingArticleGifticon(Class<? extends FundingArticleGifticon> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fundingArticle = inits.isInitialized("fundingArticle") ? new QFundingArticle(forProperty("fundingArticle"), inits.get("fundingArticle")) : null;
        this.gifticon = inits.isInitialized("gifticon") ? new team.haedal.gifticionfunding.entity.gifticon.QGifticon(forProperty("gifticon")) : null;
    }

}

