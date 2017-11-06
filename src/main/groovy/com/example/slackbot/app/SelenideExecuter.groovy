package com.example.slackbot.app

import com.codeborne.selenide.*
import org.springframework.stereotype.Component

import static com.codeborne.selenide.Selenide.*;

@Component
class SelenideExecuter {

    /**
     * 指定されたURLのキャプチャを取得し、キャプチャと指定されたセレクタのエレメントのサイズを返す
     *
     * @param url キャプチャを取得するURL
     * @param selectors サイズを取得するセレクタ
     * @return [file: キャプチャファイル, x: x, y: y, width: width, height: height]
     */
    Map getCapture(url, selectors = ["body"]) {

        def tempDirPath = File.createTempDir().absolutePath
        System.setProperty("chromeoptions.args", "headless")
        Configuration.browser = WebDriverRunner.CHROME
        Configuration.reportsFolder = tempDirPath

        // Selenideでキャプチャを取得
        open(url);

        def fileName = new Date().format("yyyyMMddHHmmssSSS")
        Screenshots.takeScreenShot(fileName)

        // 指定されたセレクタのサイズを取得
        def map = getElementSize($("html"), selectors)
        // Closeするとサイズは取得できない
        close()

        map["file"] = new File("${tempDirPath}/${fileName}.png")

        return map
    }

    Map getElementSize(element, selectors) {
        def targetElement  = selectors.inject(element) {elm, selector ->
            elm = elm.$(selector)
        }

        def location = targetElement.location
        def dimension = targetElement.size

        return [
                x : location.x,
                y : location.y,
                width : dimension.width,
                height : dimension.height,
        ]
    }
}
