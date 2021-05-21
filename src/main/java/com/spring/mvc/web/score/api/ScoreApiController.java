package com.spring.mvc.web.score.api;

import com.spring.mvc.web.score.domain.Score;
import com.spring.mvc.web.score.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/score")
public class ScoreApiController {

    private final ScoreService scoreService;

    //전체 점수 목록 비동기 조회 처리
    @GetMapping("")
    public ResponseEntity<List<Score>> list() {
        log.info("/api/score GET 비동기 요청!");
        List<Score> scoreList = scoreService.getScoreList();
        return new ResponseEntity<>(scoreList, HttpStatus.OK);
    }

    //점수 정보 등록 비동기 요청 처리
    @PostMapping("")
    // @RequestBody : 클라이언트가 보낸 JSON데이터를 파싱하여 자바 객체로 변환
    public ResponseEntity<String> save(@RequestBody Score score) {
        log.info("/api/score POST! - " + score);

        try {
            scoreService.register(score);
            return new ResponseEntity<>("insertSuccess", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("insertFail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //점수 정보 개별 조회 비동기 처리
    @GetMapping("/{stuNum}")
    public ResponseEntity<Score> selectOne(@PathVariable int stuNum) {
        log.info("/api/score/" + stuNum + " GET !");
        return new ResponseEntity<>(scoreService.getScore(stuNum), HttpStatus.OK);
    }

    //점수 정보 삭제 비동기 처리
    @DeleteMapping("/{stuNum}")
    public ResponseEntity<String> delete(@PathVariable int stuNum) {
        log.info("/api/score/"+stuNum + " DELETE !");
        try {
            scoreService.delete(stuNum);
            return new ResponseEntity<>("delSuccess", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("delFail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
