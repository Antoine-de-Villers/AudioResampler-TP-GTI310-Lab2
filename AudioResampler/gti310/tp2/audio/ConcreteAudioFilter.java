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
		// TODO Auto-generated method stub
		read();
	}
	
	private void read(){
		//nous devons diviser l'amplitude totale selon un temps de 5.125 sec. pour conserver un meme temps finale
		
		
		
	}
	
	
	
	public boolean validate(){
		
<<<<<<< HEAD
		
		tampon = reader.pop(44);
		
        if((((int)tampon[8]& 0xff) << 24) + (((int) tampon[9]& 0xff) << 16) + (((int)tampon[10]& 0xff) <<8) + ((int)tampon[11]& 0xff) == 0x57415645){ //verification Format 
        	if((((int)tampon[24]& 0xff)) + (((int) tampon[25]& 0xff) << 8) + (((int)tampon[26]& 0xff) <<16) + (((int)tampon[27]& 0xff) <<24) == 44100){ //verification Sample Rate
=======
		byte[] header;
		header = reader.pop(44);
		int test = ((int)header[24]& 0xff) + (((int) header[25]& 0xff) << 8) + (((int)header[26]& 0xff) <<16) + (((int)header[27]& 0xff) << 24);
		System.out.println(test+"\n");
        if((((int)header[8]& 0xff) << 24) + (((int) header[9]& 0xff) << 16) + (((int)header[10]& 0xff) <<8) + ((int)header[11]& 0xff) == 0x57415645){ //verification Format 
        	if( test == 44100){ //verification Sample Rate
>>>>>>> a3e21b881a6146b53acac0bc60b862e55f93668d
			
        		return true;
			
			}else{
				System.out.println("Ce fichier WAVE n'est pas echantillonne a 44.1 kHz");
			}
		}else{
			System.out.println("Ce fichier n'est pas un fichier WAVE");
		}
		
        return false;
        
	}
	


}
