package cgm.experiments.hexagonal

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate

class GreetingsTests {

    @Test
    internal fun name() {
        val employesCsvSource = """
            last_name, first_name, date_of_birth, email
            Doe, John, 1982/10/08, john.doe@foobar.com
            Ann, Mary, 1975/09/11, mary.ann@foobar.com
        """.trimIndent()

        val johnsBirthday = LocalDate.of(2021, 10, 8)
        GreeterService.greet(employesCsvSource, today = johnsBirthday) shouldBe listOf("""
            Subject: Happy birthday!

            Happy birthday, dear John!
        """.trimIndent())
    }
}

object GreeterService {
    fun greet(csvSource: String, today: LocalDate = LocalDate.now()): List<String> = emptyList()

}
