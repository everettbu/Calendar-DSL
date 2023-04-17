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

    def readCalendar(file: File): List[VEvent] = {
    val fileReader = new FileReader(file)
    val calendar = new CalendarBuilder().build(fileReader)
    val events = calendar.getComponents.asScala.collect {case event: VEvent => event}
    fileReader.close()
    events.toList
  }

    def main(args: Array[String]): Unit = {
    // Parse the file
    val inputFilePath = "input.txt"
    val inputLines = scala.io.Source.fromFile(inputFilePath).getLines().filter(_.nonEmpty).toList

    // Create a new calendar instance
    val cal = new Calendar()

    // Initialize an empty array for the events
    var events = Array.empty[(String, String)]

    // Loop through the input lines
    for (input <- inputLines) {
      // Split the input into parts
      val parts = input.split("\\s+")

      // Parse the date
      val dateStr = s"${LocalDate.now().getYear} ${parts(0)} ${parts(1)}"
      val date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy MMM dd"))

      // Parse the event name
      val eventName = input.split("'")(1)

      // Check if the input is for adding or removing an event
      parts(2) match {
        case "add" =>
          // Add the event to the array
          events = events :+ (date.format(DateTimeFormatter.ofPattern("MM-dd")), eventName)
          println(s"Added event: $eventName on $date")

        case "remove" =>
          println("not working yet")

        case _ =>
          println("Invalid input. Please enter 'add' or 'remove'.")
      }
    }

  // Write the events array to a new file
    val eventsFile = new File("events.js")
    val eventsWriter = new FileWriter(eventsFile)
    eventsWriter.write(s"const events = [\n")
    for ((date, title) <- events) {
      eventsWriter.write(s""" {title: "$title", date: "$date"},\n""")
    }
    eventsWriter.write("];\n")
    eventsWriter.close()
  }
}
