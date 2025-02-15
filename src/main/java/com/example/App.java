/*
 * c2017-2023 Courtney Brown 
 * Class: Main Class for Hello World for CC3 Class Projects streaming MIDI, etc.
 * Description: Demonstration of MIDI file manipulations, etc. & 'MelodyPlayer' sequencer
 * 
 */

package com.example;


//importing the JMusic stuff
import jm.music.data.*;
import jm.util.*;

//make sure this class name matches your file name, if not fix.
public class App {

	static MelodyPlayer player; //play a midi sequence
	static MidiFileToNotes midiNotes; //read a midi file
	static int noteCount = 0; 

	//WINDOWS - uncomment if Windows and comment the OS X line below -- Windows and OS X handle file paths differently
	//static String filePath = "mid\\MaryHadALittleLamb.mid";

	//OS X
	static String filePath = "mid/MaryHadALittleLamb.mid"; //path to the midi file -- you can change this to your file location/name


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//setup the melody player
		//uncomment below when you are ready to test or present sound output
		//make sure that it is commented out for your final submit to github (eg. when pushing)
		setup();
		playMelody();

		//add your hello, world! code here!

		//uncomment to debug your midi file
		//this code MUST be commited when submitting unit tests to github
		//playMidiFileDebugTest(filePath); 
	}

	//doing all the setup stuff
	public static void setup() {


		//playMidiFile(filePath); //use to debug -- this will play the ENTIRE file -- use ONLY to check if you have a valid path & file & it plays
								  //it will NOT let you know whether you have opened file to get the data in the form you need for the assignment

		midiSetup(filePath);
	}

	//plays the midi file using the player -- so sends the midi to an external synth such as Kontakt or a DAW like Ableton or Logic
	static public void playMelody() {

		//NOTE: for assert() to work, you need to change the Java extension settings to run with assertions enabled
		assert(player != null); //this will throw an error if player is null -- eg. if you haven't called setup() first

		while( !player.atEndOfMelody() )
		{
			player.play(); //play each note in the sequence -- the player will determine whether is time for a note onset
		}
		
	}

	//opens the midi file, extracts a voice, then initializes a melody player with that midi voice (e.g. the melody)
 	//filePath -- the name of the midi file to play
	static void midiSetup(String filePath) {

		//Change the bus to the relevant port -- if you have named it something different OR you are using Windows
		player = new MelodyPlayer(100, "Bus 1"); //sets up the player with your bus. 
		//player.listDevices(); //prints available midi devices to the console -- find your device

		midiNotes = new MidiFileToNotes(filePath); // creates a new MidiFileToNotes -- reminder -- ALL objects in Java
													// must
													// be created with "new". Note how every object is a pointer or
													// reference. Every. single. one.

		// // which line to read in --> this object only reads one line (or ie, voice or
		// ie, one instrument)'s worth of data from the file
		midiNotes.setWhichLine(0); // this assumes the melody is midi channel 0 -- this is usually but not ALWAYS
									// the case, so you can try other channels as well, if 0 is not working out for
									// you.

		noteCount = midiNotes.getPitchArray().size(); //get the number of notes in the midi file

		//NOTE: for assert() to work, you need to change the Java extension settings to run with assertions enabled
		assert(noteCount > 0); // make sure it got some notes (throw an error to alert you, the coder, if not)


		//sets the player to the melody to play the voice grabbed from the midi file above
		player.setMelody(midiNotes.getPitchArray());
 		player.setRhythm(midiNotes.getRhythmArray());

	}

	static void resetMelody() {
		player.reset();

	}

	//this function is not currently called. you may call this from setup() if you want to test
	//this just plays the midi file -- all of it via your software synth. You will not use this function in upcoming projects
	//but it could be a good debug tool.
	//filename -- the name of the midi file to play
	static void playMidiFileDebugTest(String filename) {
		Score theScore = new Score("Temporary score");
		Read.midi(theScore, filename);
		Play.midi(theScore);
	}
}