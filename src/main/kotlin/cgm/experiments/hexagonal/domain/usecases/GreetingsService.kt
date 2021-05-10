package cgm.experiments.hexagonal.domain.usecases

import cgm.experiments.hexagonal.domain.BirthdayGreetings
import cgm.experiments.hexagonal.domain.doors.PersonRepository
import cgm.experiments.hexagonal.domain.doors.SendService
import java.time.LocalDate

class GreetingsService(
    private val personsRepository: PersonRepository,
    private val sendService: SendService
) {

    fun sendGreetings(today: LocalDate) {
        val allPersons = personsRepository.listAll()
        val greetingsMessages = BirthdayGreetings.greetPersons(allPersons, today)
        sendService.send(greetingsMessages)
    }
}