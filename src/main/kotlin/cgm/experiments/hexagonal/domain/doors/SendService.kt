package cgm.experiments.hexagonal.domain.doors

import cgm.experiments.hexagonal.domain.model.BirthdayMessage

interface SendService {
    fun send(greetingsMessages: List<BirthdayMessage>)
}