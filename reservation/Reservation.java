/* -*-Java-*-
 * ###################################################################
 * 
 *  FILE: "Reservation.java"
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

public class Reservation extends BlocPlaces 
{

    private String nom;// nom auquel se fait la réservation
    private DateP dateReservation;//date du jour à laquelle s'est faite la réservation

/*
 * Construit une réservation à partir d'un bloc de la classe mère
 */
    public Reservation( BlocPlaces bloc, String nom)
    {
    	placeInf=bloc.getPlaceInf();
    	taille=bloc.taille();
    	precedent=bloc.getPrecedent();
    	suivant=bloc.getSuivant();
    	this.nom=nom;
    	dateReservation=GestionnaireReservation.getSingleton().dateCourante;
    }
/*
 * Construit une réservation de manière directe
 */
    public Reservation(int placeInf, int taille, String nom)//construction directe
    {
    	this.nom=nom;
    	dateReservation=GestionnaireReservation.getSingleton().dateCourante;
	
    }
/*
 * Dit que le bloc est réservé
 */
    public boolean estLibre()
    {
		return false;// par définition le bloc est réservé
    }
    
}