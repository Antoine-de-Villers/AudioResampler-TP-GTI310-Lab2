package gti310.tp2;

import java.util.Scanner;

public class Application {

	/**
	 * Launch the application
	 * 
	 * @param args
	 *            This parameter is ignored
	 */
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		boolean commandeValide = false;
		System.out.println("Audio Resample project!");
		while (commandeValide != true) {
			System.out.println("Veuillez enter la commande requise (/help pour voir les commandes accessibles)");
			String commande = sc.nextLine().toLowerCase();
			if (commande.equals("/help")) {
				System.out.println("Voici les commandes disponibles: /n" + "/audiofilter /n /quit /n ");
			}
			if (commande.equals("/quit")){
				System.out.println("Merci d'avoir utilisé Audio Resample Project!");
				System.exit(0);
			}
			if (commande.equals("/audioresample")){
				commandeValide = true;
			}
		}
	}
}
