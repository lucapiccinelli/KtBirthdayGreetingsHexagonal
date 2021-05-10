package cgm.experiments.hexagonal.domain.usecases

import cgm.experiments.hexagonal.domain.BirthdayGreetings
import cgm.experiments.hexagonal.doors.repositories.CsvPersonRepository
import cgm.experiments.hexagonal.doors.sendservices.StringSendService
import java.time.LocalDate

class GreetingsService(
    private val personsRepository: CsvPersonRepository,
    private val sendService: StringSendService
) {

    fun sendGreetings(today: LocalDate) {
        val allPersons = personsRepository.listAll()
        val greetingsMessages = BirthdayGreetings.greetPersons(allPersons, today)
        sendService.send(greetingsMessages)
    }
}