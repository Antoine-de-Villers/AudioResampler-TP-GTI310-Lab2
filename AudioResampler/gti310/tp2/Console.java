package gti310.tp2;

import java.util.Scanner;
import gti310.tp2.audio.AudioFilter;
import gti310.tp2.audio.ConcreteAudioFilter;

public class Console {

	
	
	public void run(){
		String fichierAManipuler, fichierACreer;
		AudioFilter audioFilter;
		
		Scanner sc = new Scanner(System.in);
		boolean commandeValide = false;
		System.out.println("Audio Resample project!");
		while (commandeValide != true) {
			System.out.println("Veuillez enter la commande requise (/help pour voir les commandes accessibles)");
			String commande = sc.nextLine().toLowerCase();
			if (commande.equals("/help")) {
				System.out.println("Voici les commandes disponibles: \n" + "/audiofilter \n/quit \nVous pouvez aussi "
						+ "�crire la commande d�sir�e et rajouter help � la suite afin de savoir � quoi sert cette commande"
						+ " (exemple /audioresample help");
			}
			if (commande.equals("/quit")){
				System.out.println("Merci d'avoir utilis� Audio Resample Project!");
				System.exit(0);
			}
			if (commande.equals("/audiofilter")){
				System.out.println("Veuillez entrer le nom du fichier a manipuler ainsi que le nom du fichier que"
						+ " l'application cr�era dans le format suivant: \nfichier�Manipuler fichier�Cr��");
				String fichiersRecus = sc.nextLine();
				String fichiersSplittes[] = fichiersRecus.split(" ");
				fichierAManipuler = fichiersSplittes[0];
				fichierACreer = fichiersSplittes[1];
				audioFilter = new ConcreteAudioFilter(fichierAManipuler, fichierACreer);
				commandeValide = true;
				
			}
			if (commande.equals("/quit help")){
				System.out.println("Commande utilis�e afin de quitter le programme");
			}
			if (commande.equals("/audiofilter help")){
				System.out.println("Le programme audio filter prend en charge"
						+ " un fichier .wav de 44.1 kHz de st�r�o ou mono 8 ou 16 bits"
						+ " \npuis le manipule afin de "
						+ "remettre un fichier .wav de 8kHz.");
			}
		}
	}
}

