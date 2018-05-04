package com.jojoldu.beginner.domain.letter;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QLetterContentMap is a Querydsl query type for LetterContentMap
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLetterContentMap extends EntityPathBase<LetterContentMap> {

    private static final long serialVersionUID = -646607653L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLetterContentMap letterContentMap = new QLetterContentMap("letterContentMap");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLetter letter;

    public final QLetterContent letterContent;

    public QLetterContentMap(String variable) {
        this(LetterContentMap.class, forVariable(variable), INITS);
    }

    public QLetterContentMap(Path<? extends LetterContentMap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLetterContentMap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLetterContentMap(PathMetadata metadata, PathInits inits) {
        this(LetterContentMap.class, metadata, inits);
    }

    public QLetterContentMap(Class<? extends LetterContentMap> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.letter = inits.isInitialized("letter") ? new QLetter(forProperty("letter")) : null;
        this.letterContent = inits.isInitialized("letterContent") ? new QLetterContent(forProperty("letterContent")) : null;
    }

}

