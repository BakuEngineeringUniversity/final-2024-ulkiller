import java.time.LocalDate

class TicketManager {
    private val tickets = mutableListOf<Ticket>()
    private var nextId = 1

    fun createTicket(customerName: String, date: LocalDate, location: String, movie: Movie): Ticket {
        val ticket = Ticket(nextId++, customerName, date, location, movie)
        tickets.add(ticket)
        return ticket
    }

    fun getAllTickets(): List<Ticket> {
        return tickets.toList()
    }
}
