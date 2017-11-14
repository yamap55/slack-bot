package com.example.slackbot.setting

import groovy.transform.ToString
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ToString
@Component
@ConfigurationProperties(prefix = "anniversary")
class Anniversary {
    List<Day> days
    @ToString
    static class Day {
        String name
        String url
        List<String> selectors
    }
}
