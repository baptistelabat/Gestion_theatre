/* -*-Java-*-
 * ###################################################################
 * 
 *  FILE: "Controleur.java"
 *                                    created: 2007-03-10 
 *                              
 *  Author: Labat Baptiste / Le Bleis Jacques
 *  E-mail: baptiste.labat@supelec.fr
 *          jacques.lebleis@supelec.fr
 *    
 *  Description: Clas

 * ###################################################################
 */

package theatre;

import ihm.*;
import reservation.*;
import programmation.*;


public class Controleur 
{
   public Controleur() 
   {
   }

   public void affiche()
    {
       	GestionnaireReservation gr=GestionnaireReservation.getSingleton();
        InterfaceCommande D=new InterfaceCommande();
        Console.ecrireNL("Date courante");
        DateP date=D.demandeDate();
        gr.initialiserDateCourante(date);
        D.afficherMenu();
        choixMenu();
    }
   
    public void choixMenu()
    {
        InterfaceCommande D=new InterfaceCommande();
        GestionnaireReservation gr=GestionnaireReservation.getSingleton();
 
        switch (D.saisirChoixMenu())
        {
	
            case 1 :// faire une réservation
                    {
                        Console.ecrireNL("");
                        Console.ecrireNL("Nouvelle reservation");
			
                        DateP dateReservation=D.demandeDate();
                        
                        D.demandeCategorie();
                            String categorie=D.saisirCategorie();
			
                        D.demandeNom();
                            String nom=D.saisirNom();
			
                        D.demandeNbPlaces();
                            int nbPlaces=D.saisirNbPlaces();
			
                        Console.ecrireNL(gr.reserver(dateReservation,categorie,nbPlaces,nom));
                        
                        D.afficherMenu();
                        choixMenu();
                    }

			   break;

            case 2 : // annuler une réservation
                    {
                        Console.ecrireNL("");
                        Console.ecrireNL("Annulation d'une reservation");
			            DateP dateAnnulation=D.demandeDate();
			            D.demandeCategorie();
                        String categorie=D.saisirCategorie();
			            D.demandeNom();
                        String nom=D.saisirNom();			   
                        Console.ecrireNL(gr.annuler(dateAnnulation,categorie,nom));                           
                        D.afficherMenu();                           
                        choixMenu();
                    }
            			   break;
                           
            case 3 : // voir les séances
                     {
                         Console.ecrireNL("");
                         Console.ecrireNL("voir les places restantes");
			             DateP datePrévision =D.demandeDate();
                         D.demandeCategorie();
                         String categorie=D.saisirCategorie();                       
                         gr.voir( datePrévision , categorie);                   
                         D.afficherMenu();                           
                         choixMenu();                           
                      }           
			   break;
		  
            case 4 : //modifier la date courante
                     {
                         Console.ecrireNL("");
                         Console.ecrireNL("Modification de la date courante");			   
                         DateP nouvelleDate=D.demandeDate();                        
                         try
                            {
                                gr.modifierDateCourante(nouvelleDate);
                            }                        
                        catch (ArithmeticException e)
                            {
                                Console.ecrireNL("Erreur : date anterieure à l'ancienne date");
                            }                        
                         D.afficherMenu();  
                         choixMenu();				
                    }				  
                        break;
                        
            case 5 : 	Console.ecrireNL("Au revoir");			
                        break;
                        
            default : 
                    {
                        Console.ecrireNL("choix impossible");
                        D.afficherMenu();
                        choixMenu();
                    }                   
        }     
    }	    
}