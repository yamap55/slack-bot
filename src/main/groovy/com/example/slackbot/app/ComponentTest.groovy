package com.example.slackbot.app

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ComponentTest {
    @Value('${test.value:aaa}')
    def str

    def hoge() {
        println "hoge"
        println str
    }
}
