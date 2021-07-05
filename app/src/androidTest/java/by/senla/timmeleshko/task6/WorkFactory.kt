package by.senla.timmeleshko.task6

import by.senla.timmeleshko.task6.model.dto.WorkDto
import java.util.concurrent.atomic.AtomicInteger

class WorkFactory {

    private val counter = AtomicInteger(0)

    fun createWork() : WorkDto {
        val id = counter.incrementAndGet()
        val work = WorkDto(
            work_id = "4674",
            media_id = "296649",
            name = "work $id",
            uri = "work:\\/\\/4674"
        )
        work.indexInResponse = -1
        return work
    }
}