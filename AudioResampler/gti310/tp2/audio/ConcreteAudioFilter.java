package gti310.tp2.audio;

import java.io.FileNotFoundException;

import gti310.tp2.io.*;

public class ConcreteAudioFilter implements AudioFilter {

	FileSource reader;
	FileSink writer;
	byte[] tampon;

	public ConcreteAudioFilter(String fichierAManipuler, String fichierACreer) {
		// TODO Auto-generated constructor stub

		try {
			reader = new FileSource(fichierAManipuler);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer = new FileSink(fichierACreer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void process() {
		
		tampon[24]= (byte) 8000;
		tampon[25]=	(byte) (8000 >> 8) & 0xff;	
		tampon[26]= 
		tampon[27]=
		read();
	}

	private void read() {
		// nous devons diviser l'amplitude totale selon un temps de 5.125 sec.
		// pour conserver un meme temps finale

		downsample();
	}
	
	private void downsample(){
		// resample the sample with correct frequency
		int sampleSize = 44100; //nombre de KHz du sample de base
		int finalSampleSize = 8000; //nombre de KHz du sample � la fin
		int channelSize = ((int)tampon[22] & 0xff) + (((int) tampon[23] & 0xff) << 8);
		int bitPerSample = ((int)tampon[34] & 0xff) + (((int)tampon[35] & 0xff) << 8);
		byte[] newTampon;
		double ratio = sampleSize/finalSampleSize; //ratio de conversion
		int tempo;
		
		for (double i=0;i<sampleSize;i=i+ratio){
				if (i-(int)i>0.5){
				tampon= reader.pop(5);
				}
				else {
				tampon= reader.pop(6);	
				}
		}
		
		
	}

	public boolean validate() {
		tampon = reader.pop(44);
		// verification Format in big endian
		if ((((int) tampon[8] & 0xff) << 24) + (((int) tampon[9] & 0xff) << 16) + (((int) tampon[10] & 0xff) << 8)
				+ ((int) tampon[11] & 0xff) == 0x57415645) {
			// verification Sample Rate in little endian
			if ((((int) tampon[24] & 0xff)) + (((int) tampon[25] & 0xff) << 8) + (((int) tampon[26] & 0xff) << 16)
					+ (((int) tampon[27] & 0xff) << 24) == 44100) {
				return true;
			} else {
				System.out.println("Ce fichier WAVE n'est pas echantillonne a 44.1 kHz");
			}
		} else {
			System.out.println("Ce fichier n'est pas un fichier WAVE");
		}
		return false;
	}

}
