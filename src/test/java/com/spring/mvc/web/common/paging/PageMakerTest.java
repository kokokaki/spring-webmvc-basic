package com.spring.mvc.web.common.paging;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;

class PageMakerTest {

    @Test
    @DisplayName("URI파라미터 생성 테스트")
    void makeUriTest() {

        UriComponents build = UriComponentsBuilder.newInstance()
                .queryParam("age", 30)
                .queryParam("name", "김철수")
                .build();

        System.out.println("build = " + build.toString());
    }

}