package com.example.slackbot.app

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.POST
import static org.apache.http.entity.ContentType.TEXT_PLAIN
import static java.nio.charset.StandardCharsets.UTF_8
import groovyx.net.http.HTTPBuilder
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.util.EntityUtils
import groovy.json.*

@Component
class Gyazo {
    @Value('${gyazo.token}')
    def token

    static final def gyazoUrl = "https://upload.gyazo.com"

    /**
     * Gyazoにファイルをアップロードする
     * @return image url
     **/
    String upload(file) {
        // upload確認
        // https://gyazo.com/captures

        def http = new HTTPBuilder(gyazoUrl)

        http.request( POST, TEXT ) { req ->
            uri.path = "/api/upload"
            req.entity = MultipartEntityBuilder
                    .create()
                    .setCharset(UTF_8)
                    .addTextBody("access_token", token, TEXT_PLAIN)
                    .addBinaryBody("imagedata", file)
                    .build()
            response.success = { res, reader ->
                def root = new JsonSlurper().parseText(EntityUtils.toString(res.getEntity()))
                return root.url
            }
            response.failure = { res, reader ->
                // 失敗時
                return null
            }
        }
    }
}
