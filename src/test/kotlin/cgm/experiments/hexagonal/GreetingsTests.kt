package cgm.experiments.hexagonal

import io.kotest.matchers.shouldBe
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
    fun greet(csvSource: String, today: LocalDate = LocalDate.now()): List<String> {
        val csvFormat = CSVFormat.EXCEL.withDelimiter(',').withHeader()
        val csv = CSVParser.parse(csvSource, csvFormat)

        val allPersons = csv.map { csvRecord ->
            val firstName = csvRecord[1].trim()
            val lastName = csvRecord[0].trim()
            val dateOfBirth = LocalDate.parse(csvRecord[2].trim(), DateTimeFormatter.ofPattern("yyyy/MM/dd"))
            val email = csvRecord[3].trim()

            Person(firstName, lastName, dateOfBirth, email)
        }

        val allBirthdays = allPersons.filter { person ->
            person.dateOfBirth.month == today.month && person.dateOfBirth.dayOfMonth == today.dayOfMonth
        }

        val birthdayMessages = allBirthdays.map { person ->
            """
                Subject: Happy birthday!
    
                Happy birthday, dear ${person.firstName}!
            """.trimIndent()
        }

        return birthdayMessages
    }

}

data class Person(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val email: String)
