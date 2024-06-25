package com.roman.domain.allTickets

import com.roman.entity.Tickets

interface AllTicketsRepository {
    suspend fun getTicketInformFull(): Tickets
}