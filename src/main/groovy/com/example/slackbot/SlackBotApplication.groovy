package com.example.slackbot

import com.example.slackbot.app.ComponentTest
import com.example.slackbot.app.Gyazo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SpringTestApplication implements CommandLineRunner {

	@Autowired
	ComponentTest componentTest

	@Autowired
	Gyazo gyazo

	static void main(String[] args) {
		SpringApplication.run SpringTestApplication, args
	}

	@Override
	void run(String... args) throws Exception {
		componentTest.hoge()

		println gyazo.upload(new File("/Users/yamap_55/Desktop/pic1.png"))
	}
}
