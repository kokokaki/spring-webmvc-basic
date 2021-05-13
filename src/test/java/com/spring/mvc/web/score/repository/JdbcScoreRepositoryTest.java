package com.spring.mvc.web.score.repository;

import com.spring.mvc.web.score.domain.Score;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//스프링에게 빈을 주입받으려면
@SpringBootTest
class JdbcScoreRepositoryTest {

    @Autowired
    @Qualifier("jr")
    private ScoreRepository repository;

    @Test
    void saveTest() {
        Score score = new Score();
        score.setName("말고기");
        score.setKor(78);
        score.setEng(89);
        score.setMath(99);
        score.calcTotalAvg();

        repository.save(score);
    }

    @Test
    void removeTest() {
        repository.remove(1);
    }

}