import java.time.{LocalDate, ZoneId}
import java.time.format.DateTimeFormatter
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.{Calendar, DateTime}
import scala.collection.JavaConverters._

object CalendarParser {

  def main(args: Array[String]): Unit = {
    // Prompt the user for input
    println("Enter the event in the format 'MMM dd add/remove <event name>'")
    val input = scala.io.StdIn.readLine()

    // Split the input into parts
    val parts = input.split("\\s+")

    // Parse the date
    val dateStr = s"${LocalDate.now().getYear} ${parts(0)} ${parts(1)}"
    val date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy MMM dd"))

    // Parse the event name
    val eventName = input.split("'")(1)

    // Create a new calendar instance
    val cal = new Calendar()

    // if (input == "print events") {
    //   println("All events in the calendar:")
    //   cal.getComponents.asScala.foreach {
    //     case event: VEvent =>
    //       val summary = event.getSummary.getValue
    //       val startDate = LocalDate.ofInstant(event.getStartDate.getDate.toInstant, java.time.ZoneId.systemDefault())
    //       println(s"$startDate: $summary")
    //     case _ =>
    //       // Ignore non-VEvent components
    //   }
    // }

    // Check if the input is for adding or removing an event
    parts(2) match {
      case "add" =>
        // Create a new event
        val event = new VEvent(new DateTime(date.atStartOfDay(ZoneId.systemDefault()).toInstant.toEpochMilli),
          new DateTime(date.atStartOfDay(ZoneId.systemDefault()).plusHours(1).toInstant.toEpochMilli), eventName)

        // Add the event to the calendar
        cal.getComponents.add(event)

        // Get the calendar as a string
        val calendarString = cal.toString()

        // Print the calendar to the console
        println(s"Added event: $eventName on $date")

      case "remove" =>
        // Search for events with matching name and start date
        val eventsToRemove = cal.getComponents.asScala.collect {
          case event: VEvent if event.getSummary.getValue == eventName &&
            event.getStartDate.getDate == new DateTime(date.atStartOfDay(ZoneId.systemDefault()).toInstant.toEpochMilli) => event
        }

        // Remove matching events
        eventsToRemove.foreach(event => cal.getComponents.remove(event))

        // Get the calendar as a string
        val calendarString = cal.toString()

        // Print the calendar to the console
        if (eventsToRemove.nonEmpty) {
          println(s"Removed event: $eventName on $date\n$calendarString")
        } else {
          println(s"No event found: $eventName on $date\n$calendarString")
        }

      case _ =>
        println("Invalid input. Please enter 'add' or 'remove'.")
    }
  }
}
