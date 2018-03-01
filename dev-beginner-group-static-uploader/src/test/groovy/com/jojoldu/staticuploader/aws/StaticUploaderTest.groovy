package com.jojoldu.staticuploader.aws

import spock.lang.Specification

import java.util.stream.Collectors
import java.util.stream.Stream

/**
 * Created by jojoldu@gmail.com on 2017. 11. 21.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

class StaticUploaderTest extends Specification {

    def "파일을 읽어 S3에 전송한다" () {
        given:
        File file = new File(getTestImagePath())
        StaticUploader staticUploader = new StaticUploader()
        def fileName = "StaticUploader_테스트파일" + ".jpg"

        when:
        String url = staticUploader.uploadToS3(file, StaticUploader.BUCKET_NAME, fileName)

        then:
        println url
    }

    def "파일을 읽어 S3 버킷/디렉토리에 전송한다" () {
        given:
        File file = new File(getTestImagePath())
        StaticUploader staticUploader = new StaticUploader()
        def fileName = "StaticUploader_테스트파일" + ".jpg"
        def bucket = StaticUploader.BUCKET_NAME + "/"+StaticUploader.ARCHIVE_DIR_NAME

        when:
        String url = staticUploader.uploadToS3(file, bucket, fileName)

        then:
        println url
    }


    def "현재위치 출력"() {
        expect:
        println System.getProperty("user.dir")
        String filePath = getTestImagePath()
        println filePath

    }

    private String getTestImagePath() {
        String dir = System.getProperty("user.dir")
        String filePath = Stream.of(dir, "src", "test", "resources", "static", "image", "test.jpg")
                .collect(Collectors.joining(File.separator))
        filePath
    }

}
