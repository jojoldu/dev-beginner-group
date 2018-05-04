package com.jojoldu.beginner.domain.subscriber;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QSubscriber is a Querydsl query type for Subscriber
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSubscriber extends EntityPathBase<Subscriber> {

    private static final long serialVersionUID = 867621208L;

    public static final QSubscriber subscriber = new QSubscriber("subscriber");

    public final com.jojoldu.beginner.domain.QBaseTimeEntity _super = new com.jojoldu.beginner.domain.QBaseTimeEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final BooleanPath certified = createBoolean("certified");

    public final StringPath certifyMessage = createString("certifyMessage");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public QSubscriber(String variable) {
        super(Subscriber.class, forVariable(variable));
    }

    public QSubscriber(Path<? extends Subscriber> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubscriber(PathMetadata metadata) {
        super(Subscriber.class, metadata);
    }

}

