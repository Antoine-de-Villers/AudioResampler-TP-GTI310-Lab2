package gti310.tp2;

import java.util.Scanner;
import gti310.tp2.audio.AudioFilter;
import gti310.tp2.audio.ConcreteAudioFilter;

public class Console {

	private String fichierAManipuler, fichierACreer="";
	
	
	public void run(){
		AudioFilter audioFilter;
		Scanner sc = new Scanner(System.in);
		boolean commandeValide = false;
		
		System.out.println("Audio Resample project!");
		while (commandeValide != true) {
			System.out.println("Veuillez enter la commande requise (/help pour voir les commandes accessibles)");
			String commande = sc.nextLine().toLowerCase();
			if (commande.equals("/help")) {
				System.out.println("Voici les commandes disponibles: \n" + "/audiofilter \n/quit \nVous pouvez aussi "
						+ "écrire la commande désirée et rajouter help à la suite afin de savoir à quoi sert cette commande"
						+ " (exemple /audioresample help");
			}
			else if (commande.equals("/quit")){
				quit();
			}
			else if (commande.equals("/audiofilter")){
				System.out.println("Veuillez entrer le nom du fichier a manipuler ainsi que le nom du fichier que"
						+ " l'application créera dans le format suivant: \nfichierÀManipuler.wav fichierÀCréé.wav");
				String fichiersRecus = sc.nextLine();
				if (validateFormat(fichiersRecus)){
				audioFilter = new ConcreteAudioFilter(fichierAManipuler, fichierACreer);
				if (audioFilter.validate()){
				audioFilter.process();
				}
				commandeValide = true;
				}	
			}
			else if (commande.equals("/quit help")){
				System.out.println("Commande utilisée afin de quitter le programme (peut être utilisée à tout moment");
			}
			else if (commande.equals("/audiofilter help")){
				System.out.println("Le programme audio filter prend en charge"
						+ " un fichier .wav de 44.1 kHz de stéréo ou mono 8 ou 16 bits"
						+ " \npuis le manipule afin de "
						+ "remettre un fichier .wav de 8kHz.");
			}
			else {
				System.out.println("Vous avez entré une commande invalide");
			}
			if (commandeValide==true){
				System.out.printf("\nLe programme s'est bien exécuté et le fichier %s a été créé. (/quit pour quitter) "
						+ "\n\n\n\n", fichierACreer);
				commandeValide=false;
			}
		}
	}
	public void quit(){
		System.out.println("Merci d'avoir utilisé Audio Resample Project!");
		System.exit(0);
	}
	public boolean validateFormat(String s){
		boolean validate = false;
		try {
			String fichiersSplittes[] = s.split(" ");
			fichierAManipuler = fichiersSplittes[0];
			fichierACreer = fichiersSplittes[1];
			if (fichierAManipuler.endsWith(".wav")&&fichierACreer.endsWith(".wav")){
				validate = true;	
			} else{
				System.out.println("Au moins un de vos fichiers n'est pas de type .wav");
			}
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Vous n'avez pas respecter le format demandé");
		}
		return validate;
	}
}

