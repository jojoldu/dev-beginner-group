package com.jojoldu.beginner.domain.letter;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QLetter is a Querydsl query type for Letter
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLetter extends EntityPathBase<Letter> {

    private static final long serialVersionUID = 983438488L;

    public static final QLetter letter = new QLetter("letter");

    public final com.jojoldu.beginner.domain.QBaseTimeEntity _super = new com.jojoldu.beginner.domain.QBaseTimeEntity(this);

    public final StringPath archiveUrl = createString("archiveUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<LetterContentMap, QLetterContentMap> letterContents = this.<LetterContentMap, QLetterContentMap>createList("letterContents", LetterContentMap.class, QLetterContentMap.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final DatePath<java.time.LocalDate> sendDate = createDate("sendDate", java.time.LocalDate.class);

    public final StringPath sender = createString("sender");

    public final EnumPath<LetterStatus> status = createEnum("status", LetterStatus.class);

    public final StringPath subject = createString("subject");

    public QLetter(String variable) {
        super(Letter.class, forVariable(variable));
    }

    public QLetter(Path<? extends Letter> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLetter(PathMetadata metadata) {
        super(Letter.class, metadata);
    }

}

