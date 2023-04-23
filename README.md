# Calendar Parser

Calendar Parser is a Scala program that parses a text file containing event details and outputs a JavaScript file containing an array of events. This is used to generate calendar events that can be used in an external web application.

## Description

The program reads an input file (input.txt) that contains lines with the following format:
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
This array is then read into the HTML file and adds the events to the calendar output:
```
<script src="events.js"></script>
```


## Usage
To use this program, follow these steps:

1. Install Scala and sbt on your machine.
2. Clone this repository to your local machine.
```
git clone 'https://github.com/hmc-cs111-spring2023/artifact-everettbu.git'
```
3. Open input.txt and add your event information. Only add one event per line. An example input file is provided in the repository for reference.
4. Open a terminal window and navigate to the project directory.
5. Run the application using the following command: 
```
sbt run
```
6. Open the HTML file in your web-browser to view the output calendar.


## Output
The application will create a new file called events.js in the project directory. This file contains an array of JavaScript objects representing the events in the input file. Each object has the following properties:

- title: the name of the event
- date: the date of the event in YYYY-MM-DD format
- color: the color of the event (default is black)

Here's an example of what the events.js file would look like based on the example input given in the description:
```
const events = [
  { title: 'Club Fair', date: '2023-04-15', color: 'blue' },
  // ...
];
```

The events.js file is then used to populate the web-based calendar application.

## Author
This program was written by Everett Butler.
