import java.io.{File, FileWriter}
import java.text.SimpleDateFormat
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

    // Check if the input is for adding or removing an event
    parts(2) match {
      case "add" =>
        // Create a new event
        val event = new VEvent(new DateTime(date.atStartOfDay(ZoneId.systemDefault()).toInstant.toEpochMilli),
          new DateTime(date.atStartOfDay(ZoneId.systemDefault()).plusDays(1).minusSeconds(1).toInstant.toEpochMilli), eventName)

        // Add the event to the calendar
        cal.getComponents.add(event)

        // Get the calendar as a string
        val calendarString = cal.toString()

        // Print the calendar to the console
        println(s"Added event: $eventName on $date")

        // Append the event to the output file
        val outputFile = new File("calendar.ics")
        val fileWriter = new FileWriter(outputFile, true) // set the "append" flag to true
        fileWriter.write(calendarString)
        fileWriter.close()

      case "remove" =>
        // Create a date format object to match the date format used in the VEvent object
        val dateFormat = new SimpleDateFormat("yyyyMMdd")

        // Search for events with matching name and start date
        val eventsToRemove = cal.getComponents.asScala.collect {
          case event: VEvent if event.getSummary.getValue == eventName &&
            dateFormat.format(event.getStartDate.getDate) == dateFormat.format(new DateTime(date.atStartOfDay(ZoneId.systemDefault()).toInstant.toEpochMilli)) => event
        }

        // Remove matching events
        eventsToRemove.foreach(event => cal.getComponents.remove(event))

        // Print the calendar to the console
        if (eventsToRemove.nonEmpty) {
          println(s"Removed event: $eventName on $date")
        } else {
          println(s"No event found: $eventName on $date")
        }

        // Overwrite the output file with the updated calendar
        val outputFile = new File("calendar.ics")
        val fileWriter = new FileWriter(outputFile)
        fileWriter.write(cal.toString)
        fileWriter.close()

      case _ =>
        println("Invalid input. Please enter 'add' or 'remove'.")
    }
  }
}
