package com.jojoldu.beginner.domain.letter;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QLetterContent is a Querydsl query type for LetterContent
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLetterContent extends EntityPathBase<LetterContent> {

    private static final long serialVersionUID = 415620289L;

    public static final QLetterContent letterContent = new QLetterContent("letterContent");

    public final com.jojoldu.beginner.domain.QBaseTimeEntity _super = new com.jojoldu.beginner.domain.QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    public final StringPath contentMarkdown = createString("contentMarkdown");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath img = createString("img");

    public final ListPath<LetterContentMap, QLetterContentMap> letterContents = this.<LetterContentMap, QLetterContentMap>createList("letterContents", LetterContentMap.class, QLetterContentMap.class, PathInits.DIRECT2);

    public final StringPath link = createString("link");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath title = createString("title");

    public QLetterContent(String variable) {
        super(LetterContent.class, forVariable(variable));
    }

    public QLetterContent(Path<? extends LetterContent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLetterContent(PathMetadata metadata) {
        super(LetterContent.class, metadata);
    }

}

