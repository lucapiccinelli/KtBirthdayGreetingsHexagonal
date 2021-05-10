package cgm.experiments.hexagonal

import cgm.experiments.hexagonal.domain.usecases.GreetingsService
import cgm.experiments.hexagonal.doors.repositories.CsvPersonRepository
import cgm.experiments.hexagonal.doors.sendservices.StringSendService
import java.time.LocalDate

object GreeterService {
    fun greet(csvSource: String, today: LocalDate = LocalDate.now()): List<String> {
        val personsRepository = CsvPersonRepository(csvSource)
        val greetingList = mutableListOf<String>()
        val sendService = StringSendService(greetingList)

        GreetingsService(personsRepository, sendService).sendGreetings(today)

        return greetingList
    }

}