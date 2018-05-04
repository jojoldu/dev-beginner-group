package com.jojoldu.beginner.admin.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * Created by jojoldu@gmail.com on 2018. 2. 28.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
public class FileUtils {

    public static void removeNewFile(File targetFile) {
        if(targetFile.delete()){
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }
}
