package com.jojoldu.beginner.domain.statistics.mail;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QMailLinkClick is a Querydsl query type for MailLinkClick
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMailLinkClick extends EntityPathBase<MailLinkClick> {

    private static final long serialVersionUID = 1701175765L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMailLinkClick mailLinkClick = new QMailLinkClick("mailLinkClick");

    public final com.jojoldu.beginner.domain.QBaseTimeEntity _super = new com.jojoldu.beginner.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.jojoldu.beginner.domain.letter.QLetterContent letterContent;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final com.jojoldu.beginner.domain.subscriber.QSubscriber subscriber;

    public QMailLinkClick(String variable) {
        this(MailLinkClick.class, forVariable(variable), INITS);
    }

    public QMailLinkClick(Path<? extends MailLinkClick> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMailLinkClick(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMailLinkClick(PathMetadata metadata, PathInits inits) {
        this(MailLinkClick.class, metadata, inits);
    }

    public QMailLinkClick(Class<? extends MailLinkClick> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.letterContent = inits.isInitialized("letterContent") ? new com.jojoldu.beginner.domain.letter.QLetterContent(forProperty("letterContent")) : null;
        this.subscriber = inits.isInitialized("subscriber") ? new com.jojoldu.beginner.domain.subscriber.QSubscriber(forProperty("subscriber")) : null;
    }

}

