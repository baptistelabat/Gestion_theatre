/* -*-Java-*-
 * ###################################################################
 * 
 *  FILE: "GestionnaireReservation.java"
 *                                    created: 2007-03-10 
 *                              
 *  Author: Labat Baptiste / Le Bleis Jacques
 *  E-mail: baptiste.labat@supelec.fr
 *          jacques.lebleis@supelec.fr
 *    
 *  Description: 

 * ###################################################################
 */

package reservation;

import programmation.*;
import ihm.*;
import java.io.*;



public class GestionnaireReservation 
{
    public DateP dateCourante;
    private static GestionnaireReservation singleton;
    
 /*
  * Methode permettant de s'assurer de l'unicité de la dateCourante
  */   
    public static GestionnaireReservation getSingleton() 
    {
        if (singleton == null)//si aucun objet GestionnaireReservation n'a été crée.
		singleton = new GestionnaireReservation(); //on crée un nouveau GestionnaireReservation
		
        return singleton; //On retourne un GestionnaireReservation qui est la seule instance de la classe.
		   
    }
    
/*
 * Méthode permettant d'initialiser la dateCourante au lancement du programme
 */	
    public void initialiserDateCourante(DateP date)
    {
        dateCourante=date;
    }
 /*
  * Réserve un bloc de place et rend un message correspondant au résultat de la réservation
  */   
    public String reserver(DateP date, String categorie, int nbPlacesReservation, String nom) 
    {
        if (date.getNum()>(dateCourante.getNum()+365))
        {
            return "La reservation ne peut se faire que pour une seance dans moins de 365 jours" ;
        }
        
        if (date.getNum()<dateCourante.getNum())
        {
            return "Cette seance est déjà passee";
        }
        
        else
        {
            GestionnaireProgrammation gp=GestionnaireProgrammation.getSingleton();//On utilise l'unique instance de la classe GestionnaireProgrammation

            if (!(gp.getEnsembleSeances().containsKey(new Integer(date.getNum()))))//Si aucune séance n'a été crée (pas encore eu de réservation pour cette date)
            {
                try
                {
                    gp.getEnsembleSeances().put(new Integer(date.getNum()),new Seance(date));
                    Console.ecrireNL("ok");
                }
                
                catch(IOException e)
                {
                    Console.ecrireNL("erreur lecture fichier disposition");
                }
            }
            
         return ((Seance)gp.getEnsembleSeances().get(new Integer(date.getNum()))).reserver(categorie,nbPlacesReservation, nom);
        
        }
           
              
    }
/*
 * Modifie la date courante
 */
    public void modifierDateCourante(DateP date) throws ArithmeticException
    {
        DateP ancienneDate=dateCourante;
                 
        if (date.getNum()<ancienneDate.getNum()) 
        {
            throw new ArithmeticException();
        }
        else 
        {
            dateCourante=date;
            GestionnaireProgrammation.getSingleton().supprimerSeances(ancienneDate,date);
        }
        
    }
/*
 * Permet d'obtenir la date courante
 */
    public DateP getDateCourante() 
    {
	return dateCourante;
    }
/*
 * Annule une réservation et rend un message correspondant au résultat de l'annulation
 */

    public String annuler(DateP date, String categorie, String nom) 
    {
        GestionnaireProgrammation gp=GestionnaireProgrammation.getSingleton();
      
        if (gp.getEnsembleSeances().containsKey(new Integer(date.getNum())))
        {
            return ((Seance)gp.getEnsembleSeances().get(new Integer(date.getNum()))).annuler(categorie, nom);
        }
        
        else
            return "Pas d'annulation possible pour cette date";
    }
    
    public void voir(DateP date , String categorie)
    {  
        GestionnaireProgrammation gp=GestionnaireProgrammation.getSingleton();
        
        if(( date.getNum()>= dateCourante.getNum())&& ( ( date.getNum()<= dateCourante.getNum()+ 365)))
        	{
        		if (gp.getEnsembleSeances().containsKey(new Integer(date.getNum())))
        			((Seance)gp.getEnsembleSeances().get(new Integer(date.getNum()))).voir(categorie) ;
        		else Console.ecrireNL("Il n'y a pas encore eu de reservation pour cette seance");
        	
        	}
        
        else Console.ecrireNL("Il n'est pas possible de voir les places libres pour cette date");
      
    }
}

