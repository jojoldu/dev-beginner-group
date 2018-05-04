package com.jojoldu.beginner.admin.service

import com.jojoldu.beginner.admin.dto.mail.ArchiveDto
import com.jojoldu.beginner.admin.dto.mail.MailContentDto
import com.jojoldu.beginner.domain.letter.LetterContent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.util.StringUtils
import spock.lang.Ignore
import spock.lang.Specification

/**
 * Created by jojoldu@gmail.com on 2018. 2. 28.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest
class ArchiveServiceTest extends Specification {

    @Autowired
    ArchiveFactory archiveService

    @Ignore
    def "newsletter.hbs로 html파일을 로컬에 생성한다." () {
        given:
        def subject = "테스트파일"
        def contentDtos = Arrays.asList(createContent())
        def dto = new ArchiveDto(subject, contentDtos)

        when:
        File newHtmlFile = archiveService.createHtml(dto)

        then:
        newHtmlFile.exists()
        newHtmlFile.canRead()
        newHtmlFile.delete()
    }

    @Ignore
    def "newsletter.hbs로 html파일을 생성후 s3에 업로드 한다." () {
        given:
        def subject = "테스트아카이브"
        def contentDtos = Arrays.asList(createContent())
        def dto = new ArchiveDto(subject, contentDtos)

        when:
        String url = archiveService.createArchive(dto)

        then:
        println url
        !StringUtils.isEmpty(url)
    }

    MailContentDto createContent(){
        return MailContentDto.builder()
                .subscriberId(1L)
                .baseUrl("aaa")
                .letterContent(
                    LetterContent.builder()
                            .title("a")
                            .content("a")
                            .contentMarkdown("a")
                            .link("a")
                            .img("a")
                            .build())
                .build()
    }
}
