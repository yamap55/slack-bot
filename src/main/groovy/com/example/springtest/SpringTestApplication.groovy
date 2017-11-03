package com.example.springtest

import com.example.springtest.app.ComponentTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SpringTestApplication implements CommandLineRunner {

	@Autowired
	ComponentTest componentTest;
	static void main(String[] args) {
		SpringApplication.run SpringTestApplication, args
	}

	@Override
	void run(String... args) throws Exception {
		componentTest.hoge()
	}
}
