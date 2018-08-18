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

    private String nom;// nom auquel se fait la r�servation
    private DateP dateReservation;//date du jour � laquelle s'est faite la r�servation

/*
 * Construit une r�servation � partir d'un bloc de la classe m�re
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
 * Construit une r�servation de mani�re directe
 */
    public Reservation(int placeInf, int taille, String nom)//construction directe
    {
    	this.nom=nom;
    	dateReservation=GestionnaireReservation.getSingleton().dateCourante;
	
    }
/*
 * Dit que le bloc est r�serv�
 */
    public boolean estLibre()
    {
		return false;// par d�finition le bloc est r�serv�
    }
    
}