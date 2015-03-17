This project tries to create a conversion of the Java port of the APE physics engine suited for use on the Android platform.

<h1>Progress</h1>
<b>The things that got resolved are:</b><br>
Object creation and Garbage Collection<br>
Moved from using doubles to Fixed Point<br>
General speed-ups <br>
<br>
<b>Working on now:</b><br>
Getting the testbed out there in an.apk<br>
Getting rid of the Vector objects and start using int arrays.<br>
Finding an alternative for Math.cos/sin/atan2 beacuse the Fixed Point implementations have issues<br>

<h1>Credits:</h1>
Android optimization and implementation by Michiel van den Anker <br>
Java port by Theo Galanakis <br>
Original APE, written in AS3, by Alec Cove  <br>
Android game template by 'Snowflake'  <br>
Fixed point math library by Henry Minsky  <br>
<br>
Visit the original project page here:<br>
<a href='http://code.google.com/p/ape/'>http://code.google.com/p/ape/</a>