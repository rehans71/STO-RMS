package com.sto.rms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sto.rms.RmsApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RmsApplication.class)
@WebAppConfiguration
public class RmsApplicationTests {

	@Test
	public void contextLoads() {
	}

}
