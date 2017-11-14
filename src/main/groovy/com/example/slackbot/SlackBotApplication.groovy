package com.example.slackbot

import com.example.slackbot.app.Gyazo
import com.example.slackbot.app.ImageEditor
import com.example.slackbot.app.SelenideExecuter
import com.example.slackbot.app.TwitterExecuter
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

	@Autowired
	TwitterExecuter twitterExecuter

	static void main(String[] args) {
		def ctx = SpringApplication.run SlackBotApplication, args
		def app =ctx.getBean(SlackBotApplication)
		app.run(args)
	}

	void run(String... args) throws Exception {
		def capInfo = selenide.getCapture("https://weather.yahoo.co.jp/weather/13/4410.html", [".forecastCity"])
		def hatenaCapInfo1 = selenide.getCapture("http://counting.hatelabo.jp/count/amgtleca46", [".main-count"])
		def hatenaCapInfo2 = selenide.getCapture("http://counting.hatelabo.jp/count/xp2p2eyaa9", [".main-count"])

		[
			imageEditor.subImageForMac(capInfo),
			imageEditor.subImageForMac(hatenaCapInfo1),
			imageEditor.subImageForMac(hatenaCapInfo2)
		].each {println gyazo.upload(it)}

		println twitterExecuter.searchFirstTweet("from:Yahoo_weather 東京の天気")
	}
}
