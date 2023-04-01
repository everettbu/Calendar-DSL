import java.io.FileOutputStream
import java.time.{LocalDateTime, ZoneId}
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.{Calendar, DateTime}
import net.fortuna.ical4j.util.RandomUidGenerator

object CalendarApp {
  def main(args: Array[String]): Unit = {
    // create a new calendar instance
    val cal = new Calendar()

    // create a new event
    val event = new VEvent(new DateTime(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant.toEpochMilli),
      new DateTime(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant.toEpochMilli), "Test Event")

    // add a random UID to the event
    event.getProperties.add(new RandomUidGenerator().generateUid())

    // add the event to the calendar
    cal.getComponents.add(event)

    // get the calendar as a string
    val calendarString = cal.toString()

    // print the calendar to the console
    println(calendarString)
  }
}