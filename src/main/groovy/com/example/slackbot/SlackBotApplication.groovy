package com.example.slackbot

import com.example.slackbot.app.Gyazo
import com.example.slackbot.app.ImageEditor
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

	@Autowired
	ImageEditor imageEditor

	static void main(String[] args) {
		SpringApplication.run SlackBotApplication, args
	}

	@Override
	void run(String... args) throws Exception {
		def capInfo = selenide.getCapture("https://weather.yahoo.co.jp/weather/13/4410.html", [".forecastCity"])

		// Macでは何故か値を2倍する必要あり
		def f = imageEditor.subImage(capInfo.file, capInfo.x * 2, capInfo.y * 2, capInfo.width * 2, capInfo.height * 2)
		println gyazo.upload(f)
	}
}
