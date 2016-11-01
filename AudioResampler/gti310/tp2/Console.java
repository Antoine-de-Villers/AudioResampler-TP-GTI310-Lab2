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
						+ "ecrire la commande desiree et rajouter help a la suite afin de savoir a quoi sert cette commande"
						+ " (exemple /audioresample help");
			}
			else if (commande.equals("/quit")){
				quit();
			}
			else if (commande.equals("/audiofilter")){
				System.out.println("Veuillez entrer le nom du fichier a manipuler ainsi que le nom du fichier que"
						+ " l'application creera dans le format suivant: \nfichierAManipuler.wav fichierACree.wav");
				String fichiersRecus = sc.nextLine();
				if (validateFormat(fichiersRecus)){
				audioFilter = new ConcreteAudioFilter(fichierAManipuler, fichierACreer);
				if (((ConcreteAudioFilter) audioFilter).validate()){
				audioFilter.process();
				commandeValide = true;
				}
				}	
			}
			else if (commande.equals("/quit help")){
				System.out.println("Commande utilisee afin de quitter le programme (peut etre utilisee a tout moment");
			}
			else if (commande.equals("/audiofilter help")){
				System.out.println("Le programme audio filter prend en charge"
						+ " un fichier .wav de 44.1 kHz de stereo ou mono 8 ou 16 bits"
						+ " \npuis le manipule afin de "
						+ "remettre un fichier .wav de 8kHz.");
			}
			else {
				System.out.println("Vous avez entre une commande invalide");
			}
			if (commandeValide==true){
				System.out.printf("\nLe programme s'est bien execute et le fichier %s a ete cree. (/quit pour quitter) "
						+ "\n\n\n\n", fichierACreer);
				commandeValide=false;
			}
		}
	}
	public void quit(){
		System.out.println("Merci d'avoir utilise Audio Resample Project!");
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
			System.out.println("Vous n'avez pas respecter le format demande");
		}
		return validate;
	}
}

