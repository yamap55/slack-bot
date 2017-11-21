package com.example.slackbot

import com.example.slackbot.app.Slack
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SlackBotApplication {

	@Autowired
	Slack slack

	static void main(String[] args) {
		def ctx = SpringApplication.run SlackBotApplication, args
		def app = ctx.getBean(SlackBotApplication)
		app.run(args)
	}

	void run(String... args) throws Exception {
	  	slack.execute()
		slack.start()
	}
}
