package gti310.tp2.audio;

import java.io.File;
import java.io.FileNotFoundException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import gti310.tp2.io.*;

public class ConcreteAudioFilter implements AudioFilter {
	File fichierAManipuler;
	File fichierACreer;
	FileSource reader;
	FileSink writer;
	byte[] tampon;

	public ConcreteAudioFilter(String fichierAManipuler, String fichierACreer) {
		// TODO Auto-generated constructor stub

		try {
			reader = new FileSource(fichierAManipuler);
			this.fichierAManipuler= new File(fichierAManipuler);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer = new FileSink(fichierACreer);
			this.fichierACreer= new File(fichierACreer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void process() {
		
		tampon[24]= (byte) 8000;
		tampon[25]=	(byte) (8000 >> 8) & 0xff;	
		tampon[26]= (byte) (8000 >> 16) & 0xff;
		tampon[27]= (byte) (8000 >> 24) & 0xff;
		
		
		read();
	}
	private void read() {
		// nous devons diviser l'amplitude totale selon un temps de 5.125 sec.
		// pour conserver un meme temps finale

		downSample();
	}

	private void downSample() {
		// resample the sample with correct frequency
		int sampleSize = 44100; // nombre de KHz du sample de base
		int finalSampleSize = 8000; // nombre de KHz du sample à la fin
		int channelSize = ((int) tampon[22] & 0xff) + (((int) tampon[23] & 0xff) << 8);
		int bitPerSample = ((int) tampon[34] & 0xff) + (((int) tampon[35] & 0xff) << 8);
		double tempsFinal = getTime();
		byte[] newTampon = new byte[1];
		System.out.println(getTime());
		double ratio = sampleSize / finalSampleSize; // ratio de conversion

		for (int temps = 0; temps < tempsFinal; temps++) {

			for (double i = 0; i < sampleSize; i = i + ratio) {
				if (i - (int) i > 0.5) {
					tampon = reader.pop(6);
				} else {
					tampon = reader.pop(5);
				}
				for (int j = 0; j < tampon.length - 1; j++) {
					newTampon[0] += tampon[j];
				}
				newTampon[0] /= tampon.length;
				writer.push(newTampon);

			}
		}

	}

	private double getTime() {
		AudioInputStream stream = null;

		try {
			stream = AudioSystem.getAudioInputStream(fichierAManipuler);

			AudioFormat format = stream.getFormat();

			return fichierAManipuler.length() / format.getSampleRate() / (format.getSampleSizeInBits() / 8.0)
					/ format.getChannels();
		} catch (Exception e) {
			// log an error
			return -1;
		} finally {
			try {
				stream.close();
			} catch (Exception ex) {
			}
		}
	}
	private void changementHeader(){
		//Changement chunkSize[4-7], sampleRate[24-27], byteRate[28-31], Subchunk2Size[40-43]  
		
		
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
