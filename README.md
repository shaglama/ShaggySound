# ShaggySound
Sound mixer for java.  Supports wav, au, aiff,and mp3 files. Multiple output formats supported (dependent on sound card).
All sounds are mixed and then output to a single line. Sound looping supported. 
Runs on its own thread so as not to block ui. 

All neccessary libraries are included in jar file. The jar file is runnable and contains a Test class that will play a sound from the jar to ensure all is working as well as output the sound file types supported by your system.

To use ShaggySound in your project, make sure the jar file is included in the build path of your project and then use the appropriate classes. 

An alternative is to include the source files in your project. However, when using this method all required libraries must also be on the build path of your project. These can be found in the /libs folder. 

The main class of use is the Mixer class. This does all the work. For example:

import javax.sound.sampled.AudioFormat;
import com.shaglama.shaggysound.mixer.Mixer;
import com.shaglama.shaggysound.mixer.lineIn.LineIn;


AudioFormat outFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,48000f,8,2,2,48000f,true);

Mixer mixer = new Mixer(); //uses default output format (PCM 16 Stereo 44100)
OR
Mixer mixer = new Mixer(outFormat);//uses specified output format

File file = new File("path/to/file");
InputStream inStream = mixer.getClass().getClassLoader().getResourceAsStream("path/to/resource");
LineIn line1;

line1 = mixer.addLine("path/to/file"); //add a sound file to mixer and save sound as variable for later removal if desired
mixer.addLine(file);//add a sound File to mixer
mixer.addLine(inStream);//add an InputStream to mixer
mixer.addLoopingLine("path/to/file",2);//add a sound file that plays 2 times to mixer

mixer.start();

To remove a sound that has been added to mixer:
mixer.removeLine(line1);



