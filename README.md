# Calendar Parser

Calendar Parser is a Scala program that parses a text file of event details and outputs a JavaScript file containing an array of events. This is useful for generating calendar events that can be used in a web application.

## Getting Started
To use this application, you will need to have Scala installed on your machine.

1. To install the program, clone this repository to your local machine.
```
git clone 'https://github.com/hmc-cs111-spring2023/artifact-everettbu.git'
```

2. Open a terminal window and navigate to the project directory

3. Open input.txt and add your event information in the following format:
```
<MMM> <D/DD> add '<event name>' (optional: <color>) 
```
For example:
```
Apr 15 add 'Club Fair' (green)
```

4. Run the application: 
```
sbt run
```

5. Open the HTML file in your web


## Output
The application will create a new file called events.js in the project directory. This file contains an array of JavaScript objects representing the events in the input file. Each object has the following properties:

- title: the name of the event
- date: the date of the event in YYYY-MM-DD format
- color: the color of the event (default is black)

The events.js file is used to populate the web-based calendar application.

