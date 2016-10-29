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
		read();
	}
	private void read() {
		// nous devons diviser l'amplitude totale selon un temps de 5.125 sec.
		// pour conserver un meme temps finale
		changementHeader();
		downSample();
	}

	private void downSample() {
		// resample the sample with correct frequency
		int sampleSize = 44100; // nombre de KHz du sample de base
		int finalSampleSize = 8000; // nombre de KHz du sample a la fin
		int channelSize = ((int) tampon[22] & 0xff) + (((int) tampon[23] & 0xff) << 8);
		int bitPerSample = ((int) tampon[34] & 0xff) + (((int) tampon[35] & 0xff) << 8);
		double tempsFinal = getTime();
		byte[] newTampon = new byte[1];
		System.out.println(getTime());
		double ratio = 5.5125; // ratio de conversion

		for (int temps = 0; temps <= tempsFinal; temps++) {
			
			for (double i = 5.5120; i < sampleSize*channelSize*bitPerSample/8; i = i + ratio) {
				
				if(bitPerSample == 8){
					if (i - ((int) i) > (0.5125)) {
						tampon = reader.pop(5);
					} else {
						tampon = reader.pop(6);
					}
					newTampon[0] = tampon[0];
					writer.push(newTampon);	
				}
				
				/*** PARTI QUI MARCHE PAS ENCORE ****
				else{
					if (i - ((int) i) > (0.5125)) {
						tampon = reader.pop(5*2);
					} else {
						tampon = reader.pop(6*2);
					}
					newTampon[0] = tampon[0];
					newTampon[1] = tampon[1];
					writer.push(newTampon);
				}*/
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
		
		
		//changement sample rate;
		tampon[24]= (byte) 8000;
		tampon[25]=	(byte) ((8000 >> 8) & 0xff);	
		tampon[26]= (byte) ((8000 >> 16) & 0xff);
		tampon[27]= (byte) ((8000 >> 24) & 0xff);
		
		//changement byte rate
		int byteChange = (((int) tampon[28] & 0xff)) + (((int) tampon[29] & 0xff) << 8) + (((int) tampon[30] & 0xff) << 16)
				+ (((int) tampon[31] & 0xff));
		byteChange /=5.5125;
				
		tampon[28]= (byte) byteChange;
		tampon[29]=	(byte) ((byteChange >> 8) & 0xff);	
		tampon[30]= (byte) ((byteChange >> 16) & 0xff);
		tampon[31]= (byte) ((byteChange >> 24) & 0xff);		
		
		//changement de subchunk2size
		byteChange = (((int) tampon[40] & 0xff)) + (((int) tampon[41] & 0xff) << 8) + (((int) tampon[42] & 0xff) << 16)
				+ (((int) tampon[43] & 0xff));
		int chunk = byteChange +36;
		byteChange /=5.5125;
		
		tampon[40]= (byte) byteChange;
		tampon[41]=	(byte) ((byteChange >> 8) & 0xff);	
		tampon[42]= (byte) ((byteChange >> 16) & 0xff);
		tampon[43]= (byte) ((byteChange >> 24) & 0xff);
		
		//changement chunk
		tampon[4]= (byte) chunk;
		tampon[5]=	(byte) ((chunk >> 8) & 0xff);	
		tampon[6]= (byte) ((chunk >> 16) & 0xff);
		tampon[7]= (byte) ((chunk >> 24) & 0xff);
		writer.push(tampon);
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
