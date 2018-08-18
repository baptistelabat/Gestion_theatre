/* -*-Java-*-
 * ###################################################################
 * 
 *  FILE: "Categorie.java"
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

import java.util.*;
import ihm.*;

public class Categorie 
{
    private int nbPlacesCategorie;
    private String nomCategorie;//nom donné à la catégorie. Par exemple A ou B.
    private HashMap ensembleBlocs;// HashMap contenant l'ensemble des blocs réservés et ayant pour clé, le nom de la réservation
    private ArrayList tableLibre;//table contenant les blocs libres
 
 /*
  * Construit une catégorie à partir du nombre de places de la catégorie et de son nom
  */   
    public Categorie(int nbPlacesCategorie,String nomCategorie)//constructeur
	{
            this.nbPlacesCategorie=nbPlacesCategorie;
            this.nomCategorie=nomCategorie;
            ensembleBlocs=new HashMap();
            tableLibre=new ArrayList();
            LinkedList L=new LinkedList();
            
            L.addFirst(new BlocPlaces(1,nbPlacesCategorie,null, null));
            tableLibre.add(new Couple(nbPlacesCategorie,L));// crée la table initiale donnant le bloc libre faisant toute la catégorie
	}
/*
 * Rend un entier qui est le nombre de places de la catégorie
 */
	public int getNbPlaces() 
	{
            return nbPlacesCategorie;
	}
/*
 * Rend un String qui est le nom de la catégorie
 */
	public String getNomCategorie() // donne le nom de la catégorie
	{
            return nomCategorie;
	}
/*
 * Rend le tableau tableLibre
 */
	public ArrayList getTableLibre() 
	{
            return tableLibre;
	}
/*
 * Rend la HashMap ensembleBlocs
 */
	public HashMap getEnsembleBlocs() 
	{
            return ensembleBlocs;
	}
/*
 * Reserve un bloc caractérisé par sa taille et le nom de la réservation
 */      
	public String reserver(int nbPlacesReservation, String nom) 
	{
            int siz=tableLibre.size();
                if (siz==0) 
                    return "Categorie complete";
                if (ensembleBlocs.containsKey(nom)) 
                    return "Il y a deja une reservation a ce nom";
            
            int i=0;                   
                while ((((Couple)tableLibre.get(i)).getNbPlaces()<nbPlacesReservation)&&(i<siz-1))
                      i++;
            
		 if (((Couple)tableLibre.get(i)).getNbPlaces()==nbPlacesReservation)
                    {
                        Couple c= (Couple)tableLibre.get(i);
                        LinkedList li=c.getListe();
                        Reservation N=new Reservation((BlocPlaces)li.removeFirst(), nom);//On crée la réservtion et on enlève lengthbloc de tableLibre
                            if (!(N.getPrecedent()==null))
                            {
                                (N.getPrecedent()).setSuivant(N);//On modifie l'adresse du bloc suivant pour le bloc précédent
                            }
                        
                            if (!(N.getSuivant()==null))
                            {   
                                (N.getSuivant()).setPrecedent(N);//On modifie l'adresse du bloc précédent pour le bloc suivant
                            }
                               
                        ensembleBlocs.put(nom,N);//on place la réservation dans ensembleBlocs
                             
                            try 
                            {
                                li.getFirst();
                            }// s'il n'y a plus de blocs libres de la taille utilisée,
                              
                            catch (NoSuchElementException e)
                            { 
                                tableLibre.remove(i);//on supprime la ligne de tablelibre
                            }
                 
                    return "Reservation effectuee dans la categorie "+nomCategorie+ " : places "+N.getPlaceInf()+" à "+N.getPlaceSup();                               
                }
                    
	
			
            if (((Couple)tableLibre.get(i)).getNbPlaces()>nbPlacesReservation+1)
            {
                return insererReservation(i,nbPlacesReservation,nom);
            }
                
            else 
            {
                if (i< siz-1   )
                {   
                    return insererReservation(i+1,nbPlacesReservation, nom);  
                }
                    
                else if ((i==siz-1)&&(((Couple)tableLibre.get(i)).getNbPlaces()==nbPlacesReservation+1))
                     {   
                        return insererReservation(i,nbPlacesReservation, nom);  
                     }
                     
            }
            
                return "Pas de blocs de la taille demandee";   
            }
/*
 * Methode d'insertion d'un bloc permettant de factoriser le code
 */        
        public String insererReservation(int i,int nbPlacesReservation, String nom)// insére une réservation à la place d' un bloc en i dans tableLibre 
        {
            BlocPlaces Old;
         
            LinkedList l=  ((LinkedList)((Couple)tableLibre.get(i)).getListe());
      
            BlocPlaces J=(BlocPlaces)l.removeFirst();
       
            Reservation N=new Reservation(Old=J,nom);//On crée une réservation à la même place que le bloc libre
            N.setTaille(nbPlacesReservation); // On adapte la taille de ce bloc à la réservation
            int nbPlacesDispo= ((Couple)tableLibre.get(i)).getNbPlaces();
                
                       
            Old.setTaille(nbPlacesDispo-nbPlacesReservation);//La taille de l'ancien bloc est donc réduite
            Old.setPlaceInf(N.getPlaceSup()+1);//Le premier élément de l'ancien bloc est décalé
                
		if(!( N.getPrecedent()==null))
                {
                    (N.getPrecedent()).setSuivant(N);// On demande au bloc précédent de pointer vers la réservation
                }
            
            N.setSuivant(Old);// La réservation pointe vers le bloc de places laissées libres
                 
            Old.setPrecedent(N);//Le bloc de places laissées libres pointe vers la réservation
            ensembleBlocs.put( nom,N);//La réservation est alors rajouté dans la table.
               
                if (((Couple)tableLibre.get(i)).getListe().size()==0)
			tableLibre.remove(i);
                        
                    int k=0;
		while ((k<=tableLibre.size()-1)&&(((Couple)tableLibre.get(k)).getNbPlaces()<nbPlacesDispo-nbPlacesReservation))//on cherche ou placer le bloc de places laissées libre dans tableLibre
			k++;
               
                    
                if ((tableLibre.size()==0)||(!( ((Couple)tableLibre.get(k)).getNbPlaces()==(nbPlacesDispo-nbPlacesReservation))))
                {   
                        tableLibre.add(new Couple(nbPlacesDispo-nbPlacesReservation, new LinkedList()));
                        ((LinkedList)((Couple)tableLibre.get(k)).getListe()).addFirst(Old);
                 }
                    
                else 
                        ((LinkedList)((Couple)tableLibre.get(k)).getListe()).addFirst(Old);
               
                return "Reservation effectuee dans la categorie "+nomCategorie+ " : places "+N.getPlaceInf()+" a "+N.getPlaceSup();
         }
/*
 * Annule une réservation caractérisé par son nom
 */       
        public String annuler(String nom)
        { 
            if (ensembleBlocs.containsKey(nom))
            {
             
                BlocPlaces  R= ((BlocPlaces)ensembleBlocs.get(nom));
                ensembleBlocs.remove(nom);
                int inf=R.getPlaceInf();
                int taille=R.taille();
                Console.ecrireNL(taille);
                BlocPlaces prec=R.getPrecedent();
                BlocPlaces suiv=R.getSuivant();
        
          
                    if ((!(prec==null))&&((prec.estLibre())))//si le bloc précédent est libre
                    {    
                        inf=prec.getPlaceInf();
                        taille=taille+prec.taille();
                        prec=R.getPrecedent().getPrecedent();
                        int i=0;
                            while (((Couple)tableLibre.get(i)).getNbPlaces()<R.getPrecedent().taille())
                            {
                                i++;
                            }
                        ((Couple)tableLibre.get(i)).getListe().remove(R.getPrecedent());
                            
                            if (((LinkedList)((Couple)tableLibre.get(i)).getListe()).size()==0)
                                tableLibre.remove(i);
                 
                    }
                
                    if ((!(suiv==null))&&(suiv.estLibre()))//si le bloc suivant est libre
                    {
                        taille=taille +suiv.taille();
                        suiv=suiv.getSuivant();
                        int i=0;
                            while (((Couple)tableLibre.get(i)).getNbPlaces()<R.getSuivant().taille())
                            { 
                                i++;
                            }
                        ((Couple)tableLibre.get(i)).getListe().remove(R.getSuivant());
            
                            if (((LinkedList)((Couple)tableLibre.get(i)).getListe()).size()==0)
                                tableLibre.remove(i);
              
                    }
                
                    BlocPlaces N=new BlocPlaces(inf, taille, prec, suiv);
          
                    if (!(prec==null))
                        prec.setSuivant(N);
          
                    if (!(suiv==null))
                        suiv.setPrecedent(N);
          
                    int k=0;
                    while ((k<tableLibre.size()-1)&&(((Couple)tableLibre.get(k)).getNbPlaces()<taille))//on cherche ou placer le bloc de places laissées libre dans tableLibre
                    	k++;
               
                 
                
                    if ((tableLibre.size()==0)||(!( ((Couple)tableLibre.get(k)).getNbPlaces()==taille)))
                    {
                        tableLibre.add(k,new Couple(taille, new LinkedList()));
                        ((LinkedList)((Couple)tableLibre.get(k)).getListe()).addFirst(N);
                    }
                
                    else
                    { 
                        ((LinkedList)((Couple)tableLibre.get(k)).getListe()).addFirst(N);
                    }
                
                
            return "L'annulation a bien ete effectuee.";
         
          }
            
          else
              
            return "Il n'y a pas de reservation a ce nom.";
        
        }
  /*
   * Permet de visualiser les blocs de places libres restant
   */     
        public void voir()
        {
            int i;
			if (tableLibre.size()==0)
				Console.ecrireNL("La categorie est complete");
			else
			{
				for (i=0;i<tableLibre.size();i++)
				{
					LinkedList l = ((Couple)tableLibre.get(i)).getListe();
					int j = 0; 
					String k=""; 
             
					while (j< l.size())
					{
						k= k+((BlocPlaces)l.get(j) ).getPlaceInf()+" à "+ ((BlocPlaces)l.get(j)).getPlaceSup()+", ";  
						j++;
					}
					Console.ecrireNL( "Bloc(s) libre(s) de taille "+((Couple)tableLibre.get(i)).getNbPlaces()+ ": places " +k);
				}
			}
        }

}
  

