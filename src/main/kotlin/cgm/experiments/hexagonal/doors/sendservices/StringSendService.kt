package cgm.experiments.hexagonal.doors.sendservices

import cgm.experiments.hexagonal.domain.doors.SendService
import cgm.experiments.hexagonal.domain.model.BirthdayMessage

class StringSendService(private val greetingList: MutableList<String>) : SendService {
    override fun send(greetingsMessages: List<BirthdayMessage>) {
        greetingsMessages.forEach { greetingList.add(it.value) }
    }

}