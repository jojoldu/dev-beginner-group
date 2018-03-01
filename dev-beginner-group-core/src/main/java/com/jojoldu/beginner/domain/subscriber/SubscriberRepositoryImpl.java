package com.jojoldu.beginner.domain.subscriber;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.jojoldu.beginner.domain.subscriber.QSubscriber.subscriber;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 1.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@AllArgsConstructor
public class SubscriberRepositoryImpl implements SubscriberRepositoryCustom {

    private JPAQueryFactory queryFactory;

    @Override
    public List<Subscriber> findAllActive(List<String> emails) {
        return queryFactory
                .selectFrom(subscriber)
                .where(inEmails(emails))
                .fetch();
    }

    private BooleanExpression inEmails(List<String> emails) {
        if(emails == null || emails.isEmpty()){
            return null;
        }

        return subscriber.email.in(emails);
    }
}
