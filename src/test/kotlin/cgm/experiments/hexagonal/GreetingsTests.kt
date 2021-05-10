package cgm.experiments.hexagonal

import io.kotest.matchers.shouldBe
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
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
        val allPersons = listAllPersons(csvSource)

        val allBirthdays = findBirthdays(allPersons, today)

        return sendMessages(allBirthdays)
    }

    private fun sendMessages(allBirthdays: List<Person>) =
        allBirthdays.map { person -> birtdayMessage(person) }

    private fun findBirthdays(
        allPersons: List<Person>,
        today: LocalDate
    ) = allPersons.filter { person -> isBirthday(person, today) }

    private fun listAllPersons(csvSource: String): List<Person> {
        val csvFormat = CSVFormat.EXCEL.withDelimiter(',').withHeader()
        val csv = CSVParser.parse(csvSource, csvFormat)

        val allPersons = csv.map { csvRecord -> csvToPerson(csvRecord) }
        return allPersons
    }

    private fun birtdayMessage(person: Person) = """
                    Subject: Happy birthday!
        
                    Happy birthday, dear ${person.name.firstName}!
                """.trimIndent()

    private fun csvToPerson(csvRecord: CSVRecord): Person {
        val firstName = csvRecord[1].trim()
        val lastName = csvRecord[0].trim()
        val dateOfBirth = LocalDate.parse(csvRecord[2].trim(), DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val email = csvRecord[3].trim()

        return Person(Name(firstName, lastName), dateOfBirth, email)
    }

    private fun isBirthday(person: Person, today: LocalDate) =
        person.dateOfBirth.month == today.month && person.dateOfBirth.dayOfMonth == today.dayOfMonth

}

data class Person(
    val name: Name,
    val dateOfBirth: LocalDate,
    val email: String)

data class Name(
    val firstName: String,
    val lastName: String)
