package com.spring.mvc.web.common.upload;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@Log4j2
public class FileUploadController {

    //업로드파일 저장 경로
    private static final String UPLOAD_PATH = "D:\\developing_soon9\\upload";

    //upload-form.jsp를 열어주는 처리
    @GetMapping("/upload-form")
    public String uploadForm() {
        return "upload/upload-form";
    }

    //파일 업로드 요청 처리
    @PostMapping("/upload")
    //MultipartFile: 클라이언트가 전송한 파일의 여러 데이터를 담고있는 객체
    public String upload(@RequestParam("file") List<MultipartFile> fileList) throws IOException {

        for (MultipartFile file : fileList) {
            log.info("/upload POST!");
            log.info("# 파일명 - " + file.getOriginalFilename());
            log.info("# 용량(byte) - " + file.getSize());
            log.info("# 파일타입 - " + file.getContentType());
            log.info("========================================");

            //업로드파일의 정보객체를 생성
            //File클래스의 생성자를 통해 첫번째 파라미터로 저장경로를
            //두번째 파라미터로 파일명을 넣어주세요
            //File uploadFile = new File(UPLOAD_PATH, file.getOriginalFilename());

            //해당 저장위치에 저장명령
//            try {
//                file.transferTo(uploadFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            String responseFilePath = FileUtils.uploadFile(file, UPLOAD_PATH);
            log.info("저장경로: " + responseFilePath);
        }
        return "upload/upload-form";
    }

}
