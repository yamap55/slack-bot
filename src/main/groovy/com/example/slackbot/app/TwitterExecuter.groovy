package com.example.slackbot.app

import org.springframework.stereotype.Component
import twitter4j.*

@Component
class TwitterExecuter {

    def twitter = new TwitterFactory().getInstance()
    def query = new Query()

    /** 指定された条件で検索し、最新の1件を返す **/
    def searchFirstTweet(query) {
        this.query.query = query
        this.query.count = 1

        // 検索実行
        def result = twitter.search(this.query)
        result.tweets[0].text
    }
}
