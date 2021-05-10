package cgm.experiments.hexagonal.domain

import cgm.experiments.hexagonal.domain.model.BirthdayMessage
import cgm.experiments.hexagonal.domain.model.Person

object BirthdayMessageTemplate {
    fun birthdayMessage(person: Person): BirthdayMessage = BirthdayMessage(
        """
            Subject: Happy birthday!
    
            Happy birthday, dear ${person.name.firstName}!
        """.trimIndent()
    )
}