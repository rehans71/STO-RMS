package com.sto.rms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RmsApplication.class, webEnvironment=WebEnvironment.DEFINED_PORT)
public class RmsApplicationTests {

	@Test
	public void contextLoads() {
	}

}
