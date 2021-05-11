package com.spring.mvc.web.score.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString
public class Score {

    //순차적인 학번 부여 필드
    private static int sequence;

    private int stuNum; //학번
    private String name; //이름
    private int kor; //국어점수
    private int eng;
    private int math;
    private int total; //총점
    private double average; //평균

    public Score() {
        this.stuNum = ++sequence;
        calcTotalAvg();
    }

    public Score(String name, int kor, int eng, int math) {
        this();
        this.name = name;
        this.kor = kor;
        this.eng = eng;
        this.math = math;
    }

    //총점, 평균을 구하는 메서드
    public void calcTotalAvg() {
        this.total = kor + eng + math;
        this.average = Math.round((this.total / 3.0) * 10 * 2) / (10.0 * 2);
    }
}
