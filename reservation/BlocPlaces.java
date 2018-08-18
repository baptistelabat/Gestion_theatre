/* -*-Java-*-
 * ###################################################################
 * 
 *  FILE: "BlocPlaces.java"
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


public class BlocPlaces 
{
    protected int placeInf;//première place du bloc
    protected int taille;//taille du bloc
    protected BlocPlaces suivant;//adresse du bloc suivant
    protected BlocPlaces precedent;//adresse du bloc précédent

    public BlocPlaces()
	{
	}
/*
 * Construit un BlocPlaces à partir de la première place, de la taille du bloc, du bloc précédent et du bloc suivant
 */
	public BlocPlaces(int placeInf, int taille, BlocPlaces precedent, BlocPlaces suivant)// crée un nouveau bloc
	{
            this.placeInf=placeInf;
            this.taille=taille;
            this.suivant=suivant;
            this.precedent=precedent;
        }
/*
 * Rend un entier renseignant sur la taille du bloc
 */  
	public int taille() 
	
	{
            return taille;
	}
/*
 * Rend un entier correspondant à la dernière place du bloc
 */
	public int getPlaceSup() 
	{
            return placeInf+taille-1;
	}
 /*
  * Dit si le bloc est libre
  */  
        public boolean estLibre() 
	{
            return true;// par définition, oui, sinon il s'agirait d'une réservation
	}
/*
 * Rend un entier correspondant à la première place du bloc
 */
	public int getPlaceInf() 
	{      
		return placeInf;
	}
/*
 * Permet d'avoir l'accès au bloc suivant
 */  
	public BlocPlaces getSuivant()
	{
            return suivant;
	}
 /*
  * Permet d'avoir l'accès au bloc précédent
  */       
	public BlocPlaces getPrecedent()
	{
            return precedent;
	}
 /*
  * Modifie le bloc suivant
  */       
	public void setSuivant(BlocPlaces bloc)
	{
            suivant=bloc;
	}
 /*
  * Modifie le bloc précédent
  */       
	public void setPrecedent(BlocPlaces bloc)
	{
            precedent=bloc;
	}
/*
 * Modifie la taille du bloc
 */   
	public void setTaille(int taille)
	{
            this.taille=taille;
	}
 /*
  * Modifie la première place du bloc
  */     
	public void setPlaceInf(int place)
	{
            placeInf=place;
	}
}