package team.haedal.gifticionfunding.entity.funding;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFundingArticle is a Querydsl query type for FundingArticle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFundingArticle extends EntityPathBase<FundingArticle> {

    private static final long serialVersionUID = 1518385632L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFundingArticle fundingArticle = new QFundingArticle("fundingArticle");

    public final team.haedal.gifticionfunding.entity.common.QBaseTimeEntity _super = new team.haedal.gifticionfunding.entity.common.QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> endAt = createDateTime("endAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final StringPath title = createString("title");

    public final team.haedal.gifticionfunding.entity.user.QUser user;

    public QFundingArticle(String variable) {
        this(FundingArticle.class, forVariable(variable), INITS);
    }

    public QFundingArticle(Path<? extends FundingArticle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFundingArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFundingArticle(PathMetadata metadata, PathInits inits) {
        this(FundingArticle.class, metadata, inits);
    }

    public QFundingArticle(Class<? extends FundingArticle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new team.haedal.gifticionfunding.entity.user.QUser(forProperty("user")) : null;
    }

}

