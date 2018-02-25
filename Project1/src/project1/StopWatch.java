package project1;

import java.io.*;
import java.util.*;

/***********************************************************************
 * 
 * Implement the following methods and properties in StopWatch class. 
 * For properties, you will need three instance variables: minutes 
 * (integer), seconds (integer), milliseconds (integer). For methods, 
 * you will need to implement the following (include getters and setters
 * as needed). At this point, assume all values of parameters are valid
 * (i.e., within range) numbers.
 * 
 * @author Nicholas English
 * @version 1.0
 *
 **********************************************************************/
public class StopWatch {

	/** This represents minutes in the StopWatch object */
	private int minutes;

	/** This represents seconds in the StopWatch object */
	private int seconds;

	/** This represents milliseconds in the StopWatch object */
	private int milliseconds;

	/** This represents a flag that suspends the StopWatch Object **/
	private static boolean flag = false;


	public StopWatch() {
		super();
	}

	/*******************************************************************
	 * 
	 * A constructor that initializes the instance variables with the 
	 * provided values.
	 * 
	 * @param minutes
	 *            used to set the number of minutes in the object
	 * @param seconds
	 *            used to set the number of seconds in the object
	 * @param milliseconds
	 *            used to set the number of milliseconds in the object
	 *            
	 ******************************************************************/

	public StopWatch(int minutes, int seconds, int milliseconds) {
		super();

		// Checks for exceptions
		if ( milliseconds < 0 || milliseconds >= 1000)
			throw new IllegalArgumentException();

		if ( seconds < 0 || seconds >= 60)
			throw new IllegalArgumentException();

		if ( minutes < 0 )
			throw new IllegalArgumentException();

		// Sets the "this" StopWatch times
		this.minutes = minutes;
		this.seconds = seconds;
		this.milliseconds = milliseconds;
	}


	/*******************************************************************
	 * 
	 * A constructor that initializes the instance variables with the 
	 * provided values.
	 * 
	 * @param seconds
	 *            used to set the number of seconds in the object
	 * @param milliseconds
	 *            used to set the number of milliseconds in the object
	 * 
	 ******************************************************************/

	public StopWatch(int seconds, int milliseconds) {
		super();

		// Checks for exceptions
		if ( milliseconds < 0 || milliseconds >= 1000)
			throw new IllegalArgumentException();

		if ( seconds < 0 || seconds >= 60)
			throw new IllegalArgumentException();

		// Sets the "this" StopWatch times
		this.seconds = seconds;
		this.milliseconds = milliseconds;
	}

	/*******************************************************************
	 * 
	 * A constructor that initializes the instance variables with the 
	 * provided values.
	 * 
	 * @param milliseconds
	 *            used to set the number of milliseconds in the object
	 *            
	 ******************************************************************/

	public StopWatch(int milliseconds) {
		super();

		// Checks for exceptions
		if ( milliseconds < 0 || milliseconds >= 1000)
			throw new IllegalArgumentException();

		// Sets the "this" StopWatch times
		this.milliseconds = milliseconds;
	}

	/*******************************************************************
	 * 	A constructor that accepts a string as a parameter with the 
	 * 		format of "1:21:300" where 1 indicates minutes, 21 indicates
	 * 		seconds, and 300 indicates milliseconds. OR the format 
	 * 		"15:200" where the 15 indicates seconds, and 200 indicates 
	 * 		milliseconds, OR the format "300" where 300 is milliseconds.
	 * 		If a value is not specified, then it is set to zero.
	 * 
	 * @param startTime
	 * 			  used to find the StopWatch times 
	 *
	 ******************************************************************/

	public StopWatch(String startTime) {

		String[] s = startTime.split(":");

		// Separates the StopWatch into an array
		if (s.length == 3) {
			minutes = Integer.parseInt(s[0]);
			seconds = Integer.parseInt(s[1]);
			milliseconds = Integer.parseInt(s[2]);
		} else if (s.length == 2) {
			minutes = 0;
			seconds = Integer.parseInt(s[0]);
			milliseconds = Integer.parseInt(s[1]);
		} else if (s.length == 1) {
			minutes = 0;
			seconds = 0;
			milliseconds = Integer.parseInt(s[0]);
		} else {
			minutes = 0;
			seconds = 0;
			milliseconds = 0;
		}

		// Checks for exceptions
		if ( milliseconds < 0 || milliseconds >= 1000)
			throw new IllegalArgumentException();

		if ( seconds < 0 || seconds >= 60)
			throw new IllegalArgumentException();

		if ( minutes < 0 )
			throw new IllegalArgumentException();
	}

	/*******************************************************************
	 * 
	 * A method that returns true if "this" StopWatch object is exactly 
	 * the same as the other StopWatch object; this.minutes equals 
	 * other.minutes and this.seconds equals other.seconds and so on.
	 * 
	 * @param other
	 * 			used to represent the "other" StopWatch.
	 * @return
	 * 			returns true or false if this.StopWatch is equal to
	 * 				other.StopWatch.
	 * 
	 ******************************************************************/

	public boolean equals(StopWatch other) {

		if ((this.minutes == other.minutes)
				&& (this.seconds == other.seconds)
				&& (this.milliseconds == other.milliseconds))
			return true;
		else
			return false;
	}

	/*******************************************************************
	 * 
	 * A method that returns true if "this" StopWatch object is exactly 
	 * the same as the other StopWatch object; this.minutes equals 
	 * ((StopWatch) other).minutes and so on.
	 * 
	 * @param other
	 * 			used to represent the "other" StopWatch.
	 * @return
	 * 			returns true or false if this.StopWatch is equal to
	 * 				other.StopWatch.
	 * 
	 ******************************************************************/

	public boolean equals(Object other) {

		StopWatch temp;
		if (other instanceof StopWatch) {
			temp = (StopWatch) other;

			if ((this.minutes == temp.minutes)
					&& (this.seconds == temp.seconds)
					&& (this.milliseconds == temp.milliseconds))
				return true;
			else
				return false;
		}

		return false;
	}

	/*******************************************************************
	 * 
	 * A static method that returns true if StopWatch object s1 is 
	 * exactly the same as StopWatch object s2.
	 * 
	 * @param s1
	 * 			used as a representation of the first StopWatch.
	 * @param s2
	 * 			used as a representation of the second StopWatch.
	 * @return
	 * 			returns true or false if this.StopWatch is equal to
	 * 				other.StopWatch.
	 * 
	 ******************************************************************/

	public static boolean equals(StopWatch s1, StopWatch s2) {

		//Used to return true or false if the two StopWatches are equal.
		boolean ifEqualStopWatches = false;

		if(flag != true) {
			if ((s1.minutes == s2.minutes)
					&& (s1.seconds == s2.seconds)
					&& (s1.milliseconds == s2.milliseconds))
				ifEqualStopWatches = true;
			else
				ifEqualStopWatches =  false;
		}

		return ifEqualStopWatches;


	}

	/*******************************************************************
	 * 
	 * A method that return 1 if "this" StopWatch object is greater than
	 * the other StopWatch object; returns -1 if the "this" StopWatch 
	 * object is less than the other StopWatch; returns 0 if the "this"
	 * StopWatch object is equal to the other StopWatch object.
	 * 
	 * @param other
	 * 			used to represent the "other" StopWatch.
	 * @return
	 * 			returns either -1, 0, or 1. See above for cases.
	 * 
	 ******************************************************************/

	public int compareTo(StopWatch other) {

		if (this.minutes > other.minutes) {
			return 1;
		} else if (this.minutes < other.minutes) {
			return -1;
		} else {
			if (this.seconds > other.seconds) {
				return 1;
			} else if (this.seconds < other.seconds) {
				return -1;
			} else {
				if (this.milliseconds > other.milliseconds) {
					return 1;
				} else if (this.milliseconds < other.milliseconds) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	/*******************************************************************
	 * 
	 * A method that adds the number of milliseconds to "this" StopWatch 
	 * object. 
	 * 
	 * @param milliseconds
	 * 			used to represent the milliseconds that "this" StopWatch 
	 * 				is to be increased by.
	 * 
	 ******************************************************************/

	public void add(int milliseconds) {

		if ( milliseconds < 0)
			throw new IllegalArgumentException();

		if(flag != true) {

			this.milliseconds += milliseconds;

			this.seconds += this.milliseconds / 1000;
			this.milliseconds = this.milliseconds % 1000;

			this.minutes += this.seconds / 60;
			this.seconds = this.seconds % 60;
		}

		// Checks for exceptions
		if ( this.milliseconds < 0  || this.milliseconds >= 1000)
			throw new IllegalArgumentException();

		if ( this.seconds < 0 || this.seconds >= 60)
			throw new IllegalArgumentException();

		if ( this.minutes < 0 )
			throw new IllegalArgumentException();

	}

	/*******************************************************************
	 * 
	 * A method that adds StopWatch other to the "this" StopWatch.
	 * 
	 * @param other
	 * 			used to represent the "other" StopWatch.
	 * 
	 ******************************************************************/

	public void add(StopWatch other) {

		if(flag != true) {

			other.milliseconds += (other.minutes * 60 * 1000);
			other.milliseconds += (other.seconds * 1000);

			add(other.milliseconds);
		}

	}

	/*******************************************************************
	 * 
	 * A method that increments the "this" StopWatch by 1 milliseconds.
	 * 
	 ******************************************************************/

	public void inc() {

		if(flag != true) {

			this.milliseconds++;

			this.seconds += this.milliseconds / 1000;
			this.milliseconds = this.milliseconds % 1000;

			this.minutes += this.seconds / 60;
			this.seconds = this.seconds % 60;
		}

		// Checks for exceptions
		if ( milliseconds < 0 || milliseconds >= 1000)
			throw new IllegalArgumentException();

		if ( seconds < 0 || seconds >= 60)
			throw new IllegalArgumentException();

		if ( minutes < 0 )
			throw new IllegalArgumentException();
	}

	/*******************************************************************
	 * 
	 * A method that subtracts the number of milliseconds from "this" 
	 * StopWatch object. Assume the parameter "milliseconds" is 
	 * positive.
	 * 
	 * @param milliseconds
	 * 			used to represent the milliseconds that "this" StopWatch 
	 * 				is to be decreased by.
	 * 
	 ******************************************************************/

	public void sub(int milliseconds) {

		// Checks for exceptions
		if ( milliseconds < 0 || milliseconds >= 1000)
			throw new IllegalArgumentException();


		if(flag != true) {

			this.milliseconds += (this.minutes * 60 * 1000);
			this.milliseconds += (this.seconds * 1000);

			this.milliseconds -= milliseconds;

			this.seconds += this.milliseconds / 1000;
			this.milliseconds = this.milliseconds % 1000;

			this.minutes += this.seconds / 60;
			this.seconds = this.seconds % 60;
		}

		// Checks for exceptions
		if ( this.milliseconds < 0 || this.milliseconds >= 1000)
			throw new IllegalArgumentException();

		if ( this.seconds < 0 || this.seconds >= 60)
			throw new IllegalArgumentException();

		if ( this.minutes < 0 )
			throw new IllegalArgumentException();
	}

	/*******************************************************************
	 * 
	 * A method that subtracts StopWatch other from the "this" 
	 * StopWatch.
	 * 
	 * @param other
	 * 			used to represent the "other" StopWatch time, which will
	 * 				be used to decrease "this" StopWatch by.
	 * 
	 ******************************************************************/

	public void sub(StopWatch other) {

		// Checks for exceptions
		if ( this.milliseconds < 0 )
			throw new IllegalArgumentException();

		if(flag != true) {

			other.milliseconds += (other.minutes * 60 * 1000);
			other.milliseconds += (other.seconds * 1000);

			sub(other.milliseconds);
		}
	}

	/*******************************************************************
	 * 
	 * A method that decrements the "this" StopWatch by 1 millisecond.
	 * 
	 ******************************************************************/

	public void dec() {

		if(flag != true) {

			this.milliseconds += (this.minutes * 60 * 1000);
			this.milliseconds += (this.seconds * 60);

			this.milliseconds--;

			this.seconds += this.milliseconds / 1000;
			this.milliseconds = this.milliseconds % 1000;

			this.minutes += this.seconds / 60;
			this.seconds = this.seconds % 60;
		}

		// Checks for exceptions
		if ( milliseconds < 0 || milliseconds >= 1000)
			throw new IllegalArgumentException();

		if ( seconds < 0 || seconds >= 60)
			throw new IllegalArgumentException();

		if ( minutes < 0 )
			throw new IllegalArgumentException();
	}

	/*******************************************************************
	 * 
	 * Method that returns a string that represents a StopWatch with the 
	 * following format: "1:06:010". Display the minutes as is, if 
	 * seconds < 10 then display with a leading "0", and always display 
	 * milliseconds with 3 digits.
	 * 
	 * @return
	 * 			returns the StopWatch back as a string in the correct
	 * 			format.
	 * 
	 ******************************************************************/

	public String toString() {
		String endTime;

		endTime = minutes + ":";

		if (seconds < 10) {
			endTime = endTime.concat("0" + seconds + ":");
		} 
		else {
			endTime = endTime.concat(seconds + ":");
		}

		if (milliseconds < 100) {
			endTime = endTime.concat("0");

			if (milliseconds < 10) {
				endTime = endTime.concat("0" + milliseconds);
			} else {
				endTime = endTime
						.concat(Integer.toString(milliseconds));
			}
		} else {
			endTime = endTime.concat(Integer.toString(milliseconds));
		}

		return endTime;
	}

	/*******************************************************************
	 * 
	 * A method that saves the "this" StopWatch to a file
	 * 
	 * @param fileName
	 *			used to reference the location of the file to save as.
	 * 
	 ******************************************************************/

	public void save(String fileName) {

		PrintWriter outputFile = null;

		try {
			outputFile = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
		}
		catch (Exception ERROR_Save) {
			ERROR_Save.printStackTrace();
		}

		outputFile.println(toString());
		outputFile.close();
	}

	/*******************************************************************
	 * 
	 * A method that loads the "this" StopWatch from a file.
	 * 
	 * @param fileName
	 * 			used to reference the file that is to be opened.
	 * 
	 ******************************************************************/

	public void load(String fileName) {
		String loadedTime;

		try {
			Scanner fileReader = new Scanner(new File(fileName));
			Scanner lineReader;

			loadedTime = fileReader.next();
			String[] s = loadedTime.split(":");

			if (s.length == 3) {
				minutes = Integer.parseInt(s[0]);
				seconds = Integer.parseInt(s[1]);
				milliseconds = Integer.parseInt(s[2]);

			} else if (s.length == 2) {
				minutes = 0;
				seconds = Integer.parseInt(s[0]);
				milliseconds = Integer.parseInt(s[1]);

			} else if (s.length == 1) {
				minutes = 0;
				seconds = 0;
				milliseconds = Integer.parseInt(s[0]);

			} else {
				minutes = 0;
				seconds = 0;
				milliseconds = 0;
			}
		}
		catch (Exception ERROR_Load) {
			ERROR_Load.printStackTrace();
		}

	}

	/*******************************************************************
	 * 
	 * This method suspends all operations (add, sub, inc, dec) when the
	 * flag = true. Otherwise all StopWatch objects can be mutated.
	 * 
	 * @param flag
	 * 				used to hold a boolean statement that will either 
	 * 				suspend the code or continue to allow it to operate.
	 *
	 ******************************************************************/

	public static void suspend(boolean flag) {

		flag = true;
	}

	/*******************************************************************
	 * 
	 * Gets the provided variable.
	 * 
	 * @return minutes
	 * 			returns the minutes in the StopWatch.
	 * 
	 ******************************************************************/

	public int getMinutes() {
		return minutes;
	}

	/*******************************************************************
	 * 
	 * Sets the provided variable.
	 * 
	 * @param minutes
	 * 			Sets the minutes in the StopWatch.
	 * 
	 ******************************************************************/

	public void setMinutes(int minutes) {
		if ( minutes < 0 )
			throw new IllegalArgumentException();

		this.minutes = minutes;
	}

	/*******************************************************************
	 * 
	 * Gets the provided variable.
	 * 
	 * @return seconds
	 * 			returns the seconds in the StopWatch.
	 * 
	 ******************************************************************/

	public int getSeconds() {
		return seconds;
	}

	/*******************************************************************
	 * 
	 * Sets the provided variable.
	 * 
	 * @param seconds
	 * 			Sets the seconds in the StopWatch.
	 * 
	 ******************************************************************/

	public void setSeconds(int seconds) {
		if ( seconds < 0 || seconds >= 60)
			throw new IllegalArgumentException();

		this.seconds = seconds;
	}

	/*******************************************************************
	 * 
	 * Gets the provided variable.
	 * 
	 * @return milliseconds
	 * 			returns the milliseconds in the StopWatch.
	 * 
	 ******************************************************************/

	public int getMilliseconds() {
		return milliseconds;
	}

	/*******************************************************************
	 * 
	 * Sets the provided variable.
	 * 
	 * @param milliseconds
	 * 			Sets the milliseconds in the StopWatch.
	 * 
	 ******************************************************************/

	public void setMilliseconds(int milliseconds) {
		if ( milliseconds < 0 || milliseconds >= 1000)
			throw new IllegalArgumentException();

		this.milliseconds = milliseconds;
	}

}

/**********************************************************************/