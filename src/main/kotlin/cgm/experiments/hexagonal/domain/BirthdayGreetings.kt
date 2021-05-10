package cgm.experiments.hexagonal.domain

import cgm.experiments.hexagonal.domain.model.BirthdayMessage
import cgm.experiments.hexagonal.domain.model.Person
import java.time.LocalDate

object BirthdayGreetings {
    fun greetPersons(allPersons: List<Person>, today: LocalDate): List<BirthdayMessage> =
        allPersons
            .filter { person -> person.isBirthday(today) }
            .map { person -> BirthdayMessageTemplate.birthdayMessage(person) }
}