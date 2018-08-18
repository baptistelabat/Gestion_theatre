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
    protected int placeInf;//premi�re place du bloc
    protected int taille;//taille du bloc
    protected BlocPlaces suivant;//adresse du bloc suivant
    protected BlocPlaces precedent;//adresse du bloc pr�c�dent

    public BlocPlaces()
	{
	}
/*
 * Construit un BlocPlaces � partir de la premi�re place, de la taille du bloc, du bloc pr�c�dent et du bloc suivant
 */
	public BlocPlaces(int placeInf, int taille, BlocPlaces precedent, BlocPlaces suivant)// cr�e un nouveau bloc
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
 * Rend un entier correspondant � la derni�re place du bloc
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
            return true;// par d�finition, oui, sinon il s'agirait d'une r�servation
	}
/*
 * Rend un entier correspondant � la premi�re place du bloc
 */
	public int getPlaceInf() 
	{      
		return placeInf;
	}
/*
 * Permet d'avoir l'acc�s au bloc suivant
 */  
	public BlocPlaces getSuivant()
	{
            return suivant;
	}
 /*
  * Permet d'avoir l'acc�s au bloc pr�c�dent
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
  * Modifie le bloc pr�c�dent
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
  * Modifie la premi�re place du bloc
  */     
	public void setPlaceInf(int place)
	{
            placeInf=place;
	}
}