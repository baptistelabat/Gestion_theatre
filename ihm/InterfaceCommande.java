package ihm;
import programmation.*;
import reservation.*;

public  class InterfaceCommande //implements Vues, Controle 
{

	public int saisirJour()
	{
		return Console.lireInt();
	}
	public int saisirMois()
	{
		return Console.lireInt();
	}
	public int saisirAnnee()
	{
		return Console.lireInt();
	}
	public String saisirCategorie()
	{
		return Console.lireMot();
	}

	public int saisirNbPlaces()
	{
		return Console.lireInt();
	 }

	public String saisirNom()
	{
		return Console.lireMot();
	}

	public int saisirChoixMenu()
	{
		return Console.lireInt();
	}

	public void demandeJour()
	{
		Console.ecrireNL("Entrez le jour (entier entre 1 et 30) :");
	}
	
	public void demandeMois()
	{
		Console.ecrireNL("Entrez le mois (entier entre 1 et 12) :");
	}
	public void demandeAnnee()
	{
		Console.ecrireNL("Entrez l'annee :");
	}
	
	public DateP demandeDate()
	{	
		demandeJour();
		int jour=saisirJour();
                while ((jour<1)||(jour>30))
                {
                    Console.ecrireNL("Le jour est compris entre 1 et 30");
                 demandeJour();
                  jour=saisirJour();
                }
		demandeMois();
		int mois=saisirMois();
                  while ((mois<1)||(mois>12))
                {
                    Console.ecrireNL("Le mois est compris entre 1 et 12");
                 demandeMois();
                  mois=saisirMois();
                }
		demandeAnnee();
		int annee=saisirAnnee();
                 while (annee<0)
                {
                    Console.ecrireNL("JC est mort depuis longtemps");
                 demandeMois();
                  annee=saisirAnnee();
                 }
		return new DateP(jour, mois, annee);	
	}

	public void demandeCategorie()
	{
		Console.ecrireNL("Entrer la categorie :");
	}

	public void demandeNbPlaces()
	{
		Console.ecrireNL("Entrer le nombre de places :");
	}

	public void demandeNom()
	{
		Console.ecrireNL("Entrer le nom de la reservation :");
	}

	public void afficherMenu()
	{       Console.ecrireNL("");
		Console.ecrireNL("Bonjour, nous sommes le "+GestionnaireReservation.getSingleton().getDateCourante().afficherDate());
		Console.ecrireNL("Voici votre menu");
		Console.ecrireNL("1 : nouvelle reservation");
		Console.ecrireNL("2 : annulation");
		Console.ecrireNL("3 : voir les places libres pour une seance");
		Console.ecrireNL("4 : changer la date courante");
		Console.ecrireNL("5 : quitter");
   
	}
        
}