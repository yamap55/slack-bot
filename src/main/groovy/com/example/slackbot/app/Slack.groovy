package com.example.slackbot.app

import com.example.slackbot.setting.Anniversary
import org.riversun.slacklet.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Slack {
    @Value('${slack.token}')
    String slackToken

    def slackService

    @Autowired
    Anniversary anniversary

    @Autowired
    Gyazo gyazo

    @Autowired
    SelenideExecuter selenide

    @Autowired
    ImageEditor imageEditor

    @Autowired
    TwitterExecuter twitterExecuter

    def start() {
        // slackletserviceを開始(slackに接続)
        slackService.start()
    }

    def stop() {
        slackService.stop()
    }

    def execute() {
            println "slackToken : ${slackToken}"
            println "anniversary : ${anniversary}"
            this.slackService = new SlackletService(slackToken)

        // slackletを追加する
        slackService.addSlacklet(new Slacklet() {
            @Override
            public void onMessagePosted(SlackletRequest req, SlackletResponse resp) {
                // メッセージがユーザーからポストされた
                // メッセージ本文を取得
                String content = req.getContent();
                def sender = req.sender
                if (sender.realName == "slackbot") {
                    return
                }
                // メッセージがポストされたチャンネルに対して、BOTからメッセージを送る
                resp.reply("「${sender.realName}」が「${content}」て言いましたね。")
            }

            @Override
            public void onMentionedMessagePosted(SlackletRequest req, SlackletResponse resp) {
                String content = req.getContent();
                def sender = req.sender
                if (sender.realName == "slackbot") {
                    return
                }

                resp.reply("「${sender.realName}」が「${content}」て話しかけましたね。")
                if (content.contains("stop")) {
                    resp.reply("「stop」します。")
                    stop()
                    // System.exit(0)
                } else if (content.contains("天気")) {
                    def capInfo = selenide.getCapture("https://weather.yahoo.co.jp/weather/13/4410.html", [".forecastCity"])
                    def weatherImage = imageEditor.subImageForMac(capInfo)
                    def url = gyazo.upload(weatherImage)

                    def twitterMessage = twitterExecuter.searchFirstTweet("from:Yahoo_weather 東京の天気")
                    resp.reply("${twitterMessage}\n${url}")
                } else if (content.contains("記念日")) {
                    def messages = anniversary.days.collect {
                        def capInfo = selenide.getCapture(it.url, it.selectors)
                        def image = imageEditor.subImageForMac(capInfo)
                        def url = gyazo.upload(image)
                        "${it.name}\n${url}"
                    }
                    resp.reply(messages.join("\n\n"))
                }
            }
        })
    }
}
