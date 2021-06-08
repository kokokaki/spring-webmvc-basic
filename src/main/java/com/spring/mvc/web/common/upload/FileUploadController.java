package com.spring.mvc.web.common.upload;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
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

    //비동기 파일 업로드 요청처리
    @PostMapping("/upload-ajax")
    @ResponseBody
    public ResponseEntity<List<String>> uploadAjax(List<MultipartFile> files) throws IOException {
        log.info("/upload-ajax 비동기 POST 요청 - " + files.get(0).getOriginalFilename());

        List<String> pathList = new ArrayList<>();
        for (MultipartFile file : files) {
            String path = FileUtils.uploadFile(file, UPLOAD_PATH);
            pathList.add(path);
        }
        return new ResponseEntity<>(pathList, HttpStatus.OK);
    }

    //파일 로딩 비동기 요청 처리
    //요청URI: /loadFile?fileName=/2021/06/08/s_dsfdfsdfsfds_cat.jpg
    @GetMapping("/loadFile")
    @ResponseBody
    public ResponseEntity<byte[]> loadFile(String fileName) {
        log.info("/loadFile GET! - request file full-path : " + (UPLOAD_PATH + fileName));

        //1. 클라이언트가 요청한 파일명을 이용하여
        //   파일의 전체 경로를 만들고 파일 정보 객체 생성
        File file = new File(UPLOAD_PATH + fileName);

        //파일을 찾아보고 없으면 404 상태코드와 함께 에러 리턴
        if (!file.exists()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        try {
            //2. 서버에 해당 파일이 저장되어 있다면 InputStream을 통해 파일을 로딩
            InputStream in = new FileInputStream(file);

            //3. 응답 헤더에 파일의 컨텐츠 미디어 타입을 설정
            //  ex) image/gif, text/html, application/json
            HttpHeaders headers = new HttpHeaders();

            //이미지 여부를 확인하기 위해 파일명에서 확장자 추출
            String ext = FileUtils.getFileExtension(fileName);
            MediaType mediaType = FileUtils.getMediaType(ext);

            //이미지 여부 확인에 따라 헤더 설정
            if (mediaType != null) {
                //이미지인 경우
                headers.setContentType(mediaType);
            } else {
                //이미지가 아닌 경우: 다운로드 기능을 활성화하는 미디어타입 설정
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                //파일명을 원래대로 복구
                fileName = fileName.substring(fileName.lastIndexOf("_") + 1);
                //파일명이 한글인 경우를 대비해 인코딩을 재설정
                String encoding = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

                //첨부파일 형식으로 다운로드하도록 헤더에 설정추가
                headers.add("Content-Disposition",
                        "attachment; filename=\"" + encoding + "\"");
            }

            //4. 파일데이터를 바이트배열형식으로 클라이언트에 전송
            byte[] fileData = IOUtils.toByteArray(in);
            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
