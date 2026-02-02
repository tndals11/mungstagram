package com.example.mungstragram._common.utils;

import com.example.mungstragram._common.error.ErrorCode;
import com.example.mungstragram._common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class FileUtils {

    private final String uploadDir = "C:/mungstagram/upload/";

    public String saveFile(MultipartFile file)  {

        // 파일이 없으면 예외처리
        if (file == null || file.isEmpty()) {
            throw new CustomException(ErrorCode.FILE_NOT_FOUND);
        }

        // 폴더 생성
        File folder = new File(uploadDir);

        // 폴더가 존재하지않는다면
        if (!folder.exists()) {
            // 모든 상위 폴더까지 생성
            folder.mkdirs();
        }

        // 사용자가 올린 사진 : 내강아지.png
        String originalName = file.getOriginalFilename();

        // 랜덤 숫자 생성
        String uuid = UUID.randomUUID().toString();

        // .png를 .을 기준으로 뒤에 명 자르기
        String extension = originalName.substring(originalName.lastIndexOf("."));

        // 랜덤숫자랑 + 자른 순수 이미지이름 합치기
        String savedName = uuid + extension;

        try {
            File targetFile = new File(uploadDir, savedName);
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return savedName;
    }

    public void deleteFile(String profileImage) {
        if (profileImage == null || profileImage.isEmpty()) {
            return;
        }

        File file = new File(uploadDir, profileImage);

        if (file.exists()) {
            if (file.delete()) {
                log.info("파일 삭제에 성공했습니다. {}", profileImage);
            } else {
                log.error("파일 삭제에 실패했습니다. {}", profileImage);
            }
        }
    }
}
