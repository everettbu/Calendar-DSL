# Calendar Parser

This is a Scala program that parses a text file of event details and outputs a JavaScript file containing an array of events. This is useful for generating calendar events that can be used in a web application.

## Description
Calendar Parser is a Scala program that parses a text file containing events and generates a JavaScript file with the events data. The program reads an input file (input.txt) that contains lines with the following format:
```
<MMM> <D/DD> add '<event name>' (optional: <color>) 
```
For example:
```
Apr 15 add 'Club Fair' (blue)
```
The program extracts the date, event name, and color (if present) from each line, and generates a JavaScript file (events.js) with an array of objects representing the events:
```
const events = [
  { title: 'Club Fair', date: '2023-04-15', color: 'blue' },
  // ...
];
```

## Usage
To use this program, follow these steps:

1. Install Scala on your machine.
2. Clone this repository to your local machine.
```
git clone 'https://github.com/hmc-cs111-spring2023/artifact-everettbu.git'
```
3. Open input.txt and add your event information. Only add one event per line.
4. Open a terminal window and navigate to the project directory.
5. Run the application: 
```
sbt run
```
6. Open the HTML file in your web-browser to view the output calendar.


## Output
The application will create a new file called events.js in the project directory. This file contains an array of JavaScript objects representing the events in the input file. Each object has the following properties:

- title: the name of the event
- date: the date of the event in YYYY-MM-DD format
- color: the color of the event (default is black)

The events.js file is used to populate the web-based calendar application.

## Dependencies
The Calendar Parser uses the following external libraries:

- ical4j: A Java library for parsing iCalendar data.
- scala-io: A Scala library for working with files and streams.

## Author
This program was written by Everett Butler.
