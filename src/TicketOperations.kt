import java.time.LocalDate

class TicketOperations : Operations {
    private val ticketManager = TicketManager()

    override fun createTicket(customerName: String, date: LocalDate, location: String, movie: Movie): Ticket {
        return ticketManager.createTicket(customerName, date, location, movie)
    }

    override fun getAllTickets(): List<Ticket> {
        return ticketManager.getAllTickets()
    }
}