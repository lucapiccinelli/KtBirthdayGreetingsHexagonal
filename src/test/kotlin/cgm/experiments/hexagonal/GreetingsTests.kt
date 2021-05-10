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
        val personsRepository = CsvPersonRepository(csvSource)
        val greetingList = mutableListOf<String>()
        val sendService = StringSendService(greetingList)

        GreetingsService(personsRepository, sendService).sendGreetings(today)


        return greetingList
    }

}

class GreetingsService(
    private val personsRepository: CsvPersonRepository,
    private val sendService: StringSendService) {

    fun sendGreetings(today: LocalDate) {
        val allPersons = personsRepository.listAll()
        val greetingsMessages = BirthdayGreetings.greetPersons(allPersons, today)
        sendService.send(greetingsMessages)
    }
}

class StringSendService(private val greetingList: MutableList<String>) {
    fun send(greetingsMessages: List<BirthdayMessage>) {
        greetingsMessages.forEach { greetingList.add(it.value) }
    }

}

object BirthdayGreetings {
    fun greetPersons(allPersons: List<Person>, today: LocalDate): List<BirthdayMessage> =
        allPersons
            .filter { person -> person.isBirthday(today) }
            .map { person -> BirthdayMessageTemplate.birthdayMessage(person) }
}

object BirthdayMessageTemplate {
    fun birthdayMessage(person: Person): BirthdayMessage = BirthdayMessage("""
            Subject: Happy birthday!
    
            Happy birthday, dear ${person.name.firstName}!
        """.trimIndent())
}

data class BirthdayMessage(val value: String)

class CsvPersonRepository(val csvSource: String) {

    fun listAll(): List<Person> {
        val csvFormat = CSVFormat.EXCEL.withDelimiter(',').withHeader()
        val csv = CSVParser.parse(csvSource, csvFormat)

        return csv.map { csvRecord -> csvToPerson(csvRecord) }
    }

    private fun csvToPerson(csvRecord: CSVRecord): Person {
        val firstName = csvRecord[1].trim()
        val lastName = csvRecord[0].trim()
        val dateOfBirth = LocalDate.parse(csvRecord[2].trim(), DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val email = csvRecord[3].trim()

        return Person(Name(firstName, lastName), dateOfBirth, email)
    }

}

data class Person(
    val name: Name,
    val dateOfBirth: LocalDate,
    val email: String){

    fun isBirthday(today: LocalDate = LocalDate.now()) =
        dateOfBirth.month == today.month && dateOfBirth.dayOfMonth == today.dayOfMonth
}

data class Name(
    val firstName: String,
    val lastName: String)
