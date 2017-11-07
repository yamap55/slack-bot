package com.example.slackbot

import com.example.slackbot.app.Gyazo
import com.example.slackbot.app.SelenideExecuter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SlackBotApplication implements CommandLineRunner {

	@Autowired
	Gyazo gyazo

	@Autowired
	SelenideExecuter selenide

	static void main(String[] args) {
		SpringApplication.run SlackBotApplication, args
	}

	@Override
	void run(String... args) throws Exception {
		def capInfo = selenide.getCapture("https://weather.yahoo.co.jp/weather/13/4410.html", [".forecastCity"])
		println gyazo.upload(capInfo.file)
	}
}
