package com.jojoldu.beginner.admin.service;

import com.jojoldu.beginner.admin.dto.mail.ArchiveDto;
import com.jojoldu.beginner.staticuploader.aws.StaticUploader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by jojoldu@gmail.com on 2018. 2. 27.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@AllArgsConstructor
@Component
public class ArchiveFactory {

    private NewsLetterFactory newsLetterFactory;
    private StaticUploader staticUploader;

    public String createArchive(ArchiveDto dto){
        File newHtmlFile = createHtml(dto);
        String uploadUrl = staticUploader.upload(newHtmlFile);
        removeNewFile(newHtmlFile);
        return uploadUrl;
    }

    public File createHtml(ArchiveDto dto){
        File newHtmlFile = new File(dto.getSubject()+".html");
        String content = newsLetterFactory.createContent(dto.getContentGroupDto());
        try {
            FileUtils.writeStringToFile(newHtmlFile, content, Charset.forName("UTF-8"));
            return newHtmlFile;
        } catch (IOException e) {
            String message = "HTML 파일 생성중 오류 발생: " + dto.getSubject();
            log.error(message, e);
            throw new IllegalArgumentException(message);
        }
    }

    public void removeNewFile(File targetFile) {
        if(targetFile.delete()){
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

}
