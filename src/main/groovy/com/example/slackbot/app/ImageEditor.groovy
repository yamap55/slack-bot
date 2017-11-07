package com.example.slackbot.app

import org.springframework.stereotype.Component

import javax.imageio.ImageIO;

@Component
class ImageEditor {

    static File subImage(inputFile, x, y, width, height) {
        // 取得したキャプチャを切り抜き
        def inputImage = ImageIO.read(inputFile)
        def outputImage = inputImage.getSubimage(x, y, width, height)
        def tempDirPath = File.createTempDir().absolutePath
        def fileName = "${new Date().format('yyyyMMddHHmmssSSS')}.png"
        def outputFile = new File("${tempDirPath}/${fileName}")
        ImageIO.write(outputImage, "PNG", outputFile)
        return outputFile
    }
}
