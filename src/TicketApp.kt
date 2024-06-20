import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TicketApp{
    private val ticketManager: TicketManager = TicketManager()

    fun run() {
        while (true) {
            showMenu()
            val choice = readLine()!!.toIntOrNull() ?: continue
            when (choice) {
                1 -> createTicket()
                2 -> viewTickets()
                3 -> exit()
                else -> println("Invalid choice. Please try again.")
            }
        }
    }

    private fun showMenu() {
        println("Cinema Ticket Booking System")
        println("1. Create Ticket")
        println("2. View Cart")
        println("3. Exit")
        print("Enter your choice: ")
    }

    private fun createTicket() {
        try {
            print("Enter customer name: ")
            val name = readLine()!!
            print("Enter date (yyyy-MM-dd): ")
            val dateInput = readLine()!!
            val date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_DATE)
            val dayOfMonth = date.dayOfMonth

            println("Select location:")
            println("1. 28 Mall")
            println("2. Crescent Mall")
            println("3. Ganjlik Mall")
            print("Enter your choice: ")
            val locationChoice = readLine()!!.toIntOrNull()

            val location = when (locationChoice) {
                1 -> "28 Mall"
                2 -> "Crescent Mall"
                3 -> "Ganjlik Mall"
                else -> {
                    println("Invalid location choice.")
                    return
                }
            }

            val movies = getMovies(dayOfMonth, location)
            if (movies.isEmpty()) {
                println("No movies available.")
                return
            }

            println("Available movies at $location on $date:")
            movies.forEachIndexed { index, movie -> println("${index + 1}. ${movie.time} - ${movie.name} (${movie.price} AZN)") }

            print("Select a movie by number: ")
            val movieChoice = readLine()!!.toIntOrNull()
            if (movieChoice == null || movieChoice !in 1..movies.size) {
                println("Invalid movie choice.")
                return
            }

            val selectedMovie = movies[movieChoice - 1]
            val ticket = ticketManager.createTicket(name, date, location, selectedMovie)
            println("Ticket created: $ticket")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    private fun viewTickets() {
        val tickets = ticketManager.getAllTickets()
        if (tickets.isEmpty()) {
            println("No tickets in the cart.")
        } else {
            var totalPrice = 0.0
            tickets.forEach {
                println(it)
                totalPrice += it.movie.price
            }
            println("Total Price: $totalPrice AZN")
        }
    }

    private fun exit() {
        println("Exiting the application.")
        kotlin.system.exitProcess(0)
    }

    private fun getMovies(day: Int, location: String): List<Movie> {
        val evenDayMovies = mapOf(
            "28 Mall" to listOf(
                Movie("09:00", "Harry Potter and the Philosopher's Stone", 1.0),
                Movie("13:30", "The Godfather", 1.0),
                Movie("17:45", "The Avengers", 1.0)
            ),
            "Crescent Mall" to listOf(
                Movie("11:30", "The Lion King", 3.0),
                Movie("16:00", "Frozen", 3.0),
                Movie("22:00", "Gladiator", 3.0)
            ),
            "Ganjlik Mall" to listOf(
                Movie("10:45", "The Lord of the Rings: The Fellowship of the Ring", 2.0),
                Movie("15:15", "Titanic", 2.0),
                Movie("20:00", "The Dark Knight Rises", 2.0)
            )
        )

        val oddDayMovies = mapOf(
            "28 Mall" to listOf(
                Movie("11:00", "Barbie", 1.0),
                Movie("13:00", "Oppenheimer", 1.0),
                Movie("21:00", "MISSION: IMPOSSIBLE - DEAD RECKONING PART ONE", 1.0)
            ),
            "Crescent Mall" to listOf(
                Movie("12:00", "GODZILLA MINUS ONE", 3.0),
                Movie("17:00", "GUARDIANS OF THE GALAXY VOL. 3", 3.0),
                Movie("22:00", "WHEN EVIL LURKS", 3.0)
            ),
            "Ganjlik Mall" to listOf(
                Movie("09:00", "SPIDER-MAN: ACROSS THE SPIDER-VERSE", 2.0),
                Movie("16:00", "JOHN WICK: CHAPTER 4", 2.0),
                Movie("20:00", "ANATOMY OF A FALL", 2.0),
                Movie("23:00", "M3GAN", 2.0)
            )
        )

        return if (day % 2 == 0) {
            evenDayMovies[location] ?: emptyList()
        } else {
            oddDayMovies[location] ?: emptyList()
        }
    }
}
