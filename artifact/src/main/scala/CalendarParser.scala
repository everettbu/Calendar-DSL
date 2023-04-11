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
    // Read the input from a file
    val inputFile = new File("input.txt")
    val input = Source.fromFile(inputFile).mkString.trim()

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
        println(s"Added event: $eventName on $date")

        // Append the event to the output file
        val outputFile = new File("calendar.ics")
        val fileWriter = new FileWriter(outputFile, true)
        fileWriter.write(cal.toString)
        fileWriter.close()

      case "remove" =>
        println("not working")

      case _ =>
        println("Invalid input. Please enter 'add' or 'remove'.")
    }
  }

  def readCalendar(file: File): List[VEvent] = {
    val fileReader = new FileReader(file)
    val calendar = new CalendarBuilder().build(fileReader)
    val events = calendar.getComponents.asScala.collect {
      case event: VEvent => event
    }
    fileReader.close()
    events.toList
  }
}

