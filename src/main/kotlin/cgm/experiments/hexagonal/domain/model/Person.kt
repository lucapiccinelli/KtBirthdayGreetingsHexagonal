package cgm.experiments.hexagonal.domain.model

import java.time.LocalDate

data class Person(
    val name: Name,
    val dateOfBirth: LocalDate,
    val email: String){

    fun isBirthday(today: LocalDate = LocalDate.now()) =
        dateOfBirth.month == today.month && dateOfBirth.dayOfMonth == today.dayOfMonth
}