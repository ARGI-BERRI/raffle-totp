package dev.argia.raffletotp

import dev.turingcomplete.kotlinonetimepassword.*
import java.time.Instant
import java.util.concurrent.TimeUnit

@Suppress("SpellCheckingInspection")
val secretKey = "CQWEQPEBFCZEA2U7LOWE2MTLM6YPTYA"

fun main() {
    val secretByte = secretKey.toByteArray()
    val totpConfig = TimeBasedOneTimePasswordConfig(
        codeDigits = 6,
        hmacAlgorithm = HmacAlgorithm.SHA1,
        timeStep = 30,
        timeStepUnit = TimeUnit.SECONDS
    )

    val totpGenerator = TimeBasedOneTimePasswordGenerator(secretByte, totpConfig)
    var time = Instant.now()

    var attempt = 0
    while (true) {
        if (attempt % 100_000 == 0) {
            println("${attempt / 100_000}M complete...")
            println("Current time: $time")
        }

        val token = totpGenerator.generate(time)

        if (token == "777777") {
            println("---------------------------------------------------------")
            println("The X-DAY: $time")
            println("The token: $token")
            println("Attempt: $attempt")
            return
        }

        time = time.plusSeconds(30L)

        attempt++
    }
}