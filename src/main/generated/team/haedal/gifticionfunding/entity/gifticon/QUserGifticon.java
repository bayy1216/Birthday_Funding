package team.haedal.gifticionfunding.entity.gifticon;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserGifticon is a Querydsl query type for UserGifticon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserGifticon extends EntityPathBase<UserGifticon> {

    private static final long serialVersionUID = -531599197L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserGifticon userGifticon = new QUserGifticon("userGifticon");

    public final team.haedal.gifticionfunding.entity.common.QBaseTimeEntity _super = new team.haedal.gifticionfunding.entity.common.QBaseTimeEntity(this);

    public final team.haedal.gifticionfunding.entity.user.QUser buyer;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> expirationDate = createDate("expirationDate", java.time.LocalDate.class);

    public final StringPath giftCode = createString("giftCode");

    public final QGifticon gifticon;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final team.haedal.gifticionfunding.entity.user.QUser owner;

    public final DatePath<java.time.LocalDate> usedDate = createDate("usedDate", java.time.LocalDate.class);

    public QUserGifticon(String variable) {
        this(UserGifticon.class, forVariable(variable), INITS);
    }

    public QUserGifticon(Path<? extends UserGifticon> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserGifticon(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserGifticon(PathMetadata metadata, PathInits inits) {
        this(UserGifticon.class, metadata, inits);
    }

    public QUserGifticon(Class<? extends UserGifticon> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buyer = inits.isInitialized("buyer") ? new team.haedal.gifticionfunding.entity.user.QUser(forProperty("buyer")) : null;
        this.gifticon = inits.isInitialized("gifticon") ? new QGifticon(forProperty("gifticon")) : null;
        this.owner = inits.isInitialized("owner") ? new team.haedal.gifticionfunding.entity.user.QUser(forProperty("owner")) : null;
    }

}

