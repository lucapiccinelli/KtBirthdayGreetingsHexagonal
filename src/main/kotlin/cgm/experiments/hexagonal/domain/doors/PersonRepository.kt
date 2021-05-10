package cgm.experiments.hexagonal.domain.doors

import cgm.experiments.hexagonal.domain.model.Person

interface PersonRepository {
    fun listAll(): List<Person>
}