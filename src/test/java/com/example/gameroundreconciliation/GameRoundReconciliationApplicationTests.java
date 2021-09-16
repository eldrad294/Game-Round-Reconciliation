package com.example.gameroundreconciliation;

import com.example.gameroundreconciliation.controllers.Microservice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GameRoundReconciliationApplicationTests {

    @Autowired
    private Microservice microservice;

	@Test
	void contextLoads() {
        assertThat(microservice).isNotNull();
	}

}
