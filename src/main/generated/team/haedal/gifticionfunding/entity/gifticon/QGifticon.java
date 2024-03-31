package team.haedal.gifticionfunding.entity.gifticon;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGifticon is a Querydsl query type for Gifticon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGifticon extends EntityPathBase<Gifticon> {

    private static final long serialVersionUID = -1745519816L;

    public static final QGifticon gifticon = new QGifticon("gifticon");

    public final team.haedal.gifticionfunding.entity.common.QBaseTimeEntity _super = new team.haedal.gifticionfunding.entity.common.QBaseTimeEntity(this);

    public final StringPath brand = createString("brand");

    public final StringPath category = createString("category");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Integer> expirationPeriod = createNumber("expirationPeriod", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Long> stock = createNumber("stock", Long.class);

    public QGifticon(String variable) {
        super(Gifticon.class, forVariable(variable));
    }

    public QGifticon(Path<? extends Gifticon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGifticon(PathMetadata metadata) {
        super(Gifticon.class, metadata);
    }

}

