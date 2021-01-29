package org.jrichardsz.poc.voce;

import voce.*;
import java.io.File;


public class RecognitionTest
{
	public static void main(String[] args)
	 throws Exception{
		Utils.setPrintDebug(true);

		if(args == null || args.length < 1){
			throw new Exception("voice and grammar paths are required.");
		}

		String configPath = args[0];

		String vocePath = configPath + File.separator + "voice";
		String grammarPath = configPath + File.separator + "grammar";
		String grammarName = PathHelper.getGrammarFileName(grammarPath);

		System.out.println("vocePath:"+vocePath);
		System.out.println("grammarPath:"+grammarPath);
		System.out.println("grammarName:"+grammarName);

		voce.SpeechInterface.init(vocePath, false, true,grammarPath, grammarName);

		System.out.println("This is a speech recognition test. "
			+ "Speak agree your .gram file into the microphone. "
			+ "Speak 'bye' to quit.");

		boolean quit = false;
		while (!quit)
		{
			try
			{
				Thread.sleep(200);
			}
			catch (InterruptedException e)
			{
			}

			while (voce.SpeechInterface.getRecognizerQueueSize() > 0)
			{
				String s = voce.SpeechInterface.popRecognizedString();

				// Check if the string contains 'quit'.
				if (-1 != s.indexOf("bye"))
				{
					quit = true;
				}

				System.out.println("You said: " + s);
			}
		}

		voce.SpeechInterface.destroy();
		System.exit(0);
	}
}
