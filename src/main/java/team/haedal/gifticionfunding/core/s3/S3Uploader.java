package team.haedal.gifticionfunding.core.s3;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class S3Uploader {
//    private final AmazonS3 amazonS3;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    public String upload(MultipartFile multipartFile){
//        if(multipartFile.isEmpty() || Objects.isNull(multipartFile)){
//            throw new IllegalArgumentException("파일이 비어있거나 존재하지 않습니다.");
//        }
//        //TODO
//        return null;
//    }
//}
