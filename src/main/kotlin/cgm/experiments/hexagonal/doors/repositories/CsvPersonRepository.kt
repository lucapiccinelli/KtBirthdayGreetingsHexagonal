package cgm.experiments.hexagonal.doors.repositories

import cgm.experiments.hexagonal.domain.model.Name
import cgm.experiments.hexagonal.domain.model.Person
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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