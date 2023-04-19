import java.io.{File, FileWriter, FileReader}
import java.text.SimpleDateFormat
import java.time.{LocalDate, ZoneId}
import java.time.format.DateTimeFormatter
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.{Calendar, DateTime}
import scala.collection.JavaConverters._
import scala.io.Source

object CalendarParser {

    def main(args: Array[String]): Unit = {
    // Parse the file
    val inputFilePath = "input.txt"
    val inputLines = scala.io.Source.fromFile(inputFilePath).getLines().filter(_.nonEmpty).toList

    // Create a new calendar instance
    val cal = new Calendar()

    // Initialize an empty mutable list for the events
    var events = List[(String, String, Option[String])]()

    // Loop through the input lines
    for (input <- inputLines) {
      // Split the input into parts
      val parts = input.split("\\s+")

      // Parse the date
      val dateStr = s"${LocalDate.now().getYear} ${parts(0)} ${parts(1)}"
      val date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy MMM dd"))

      // Parse the event name and style (if present)
      val eventName = input.split("'")(1)
      val eventColor = if (input.contains("(")) Some(input.split("\\(")(1).split("\\)")(0)) else None

      // Check if the input is for adding or removing an event
      parts(2) match {
        case "add" =>
          // Add the event to the list
          events = events :+ (date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), eventName, eventColor)
          println(s"Added event: $eventName on $date")

        case "remove" =>
          println("not working yet")

        case _ =>
          println("Invalid input. Please enter 'add' or 'remove'.")
      }
    }

    // Write the events list to a new file
    val eventsFile = new File("events.js")
    val eventsWriter = new FileWriter(eventsFile)
    eventsWriter.write(s"const events = [\n")
    for ((date, title, color) <- events) {
      eventsWriter.write(s""" {title: "$title", date: "$date",""")
      if (color.isDefined) {
        eventsWriter.write(s""" color: "${color.get}"""")
      }
      eventsWriter.write(s"""},\n""")
    }
    eventsWriter.write("];\n")
    eventsWriter.close()
  }
}