package com.spring.mvc.web.score.repository;

import com.spring.mvc.web.score.domain.Score;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScoreMapperTest {

    @Autowired
    private ScoreMapper mapper;

    @Test
    void saveTest() {

        Score score = new Score();
        score.setName("마이바티스");
        score.setKor(10);
        score.setEng(20);
        score.setMath(30);
        score.calcTotalAvg();

        mapper.save(score);
    }

    @Test
    void removeTest() {
        mapper.remove(7);
    }

    @Test
    void findAllTest() {

        List<Score> list = mapper.findAll();

        System.out.println("==========================");
        for (Score score : list) {
            System.out.println(score);
        }
        assertEquals(3, list.size());
    }

    @Test
    void findOneTest() {
        Score score = mapper.findOne(4);
        System.out.println("=============================");
        System.out.println("score = " + score);

        assertEquals("맛밤", score.getName());
    }

}