import java.time.LocalDate

interface Operations {
    fun createTicket(customerName: String, date: LocalDate, location: String, movie: Movie): Ticket
    fun getAllTickets(): List<Ticket>
}
