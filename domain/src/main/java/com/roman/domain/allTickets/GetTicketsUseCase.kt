package com.roman.domain.allTickets

import com.roman.entity.Tickets
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(
    private val ticketsRepository: AllTicketsRepository
) {

    suspend fun execute(): Tickets {
        return ticketsRepository.getTicketInformFull()
    }
}