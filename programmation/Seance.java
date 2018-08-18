/* -*-Java-*-
 * ###################################################################
 * 
 *  FILE: "Seance.java"
 *                                    created: 2007-03-10 
 *                              
 *  Author: Labat Baptiste / Le Bleis Jacques
 *  E-mail: baptiste.labat@supelec.fr
 *          jacques.lebleis@supelec.fr
 *    
 * 

 * ###################################################################
 */

package programmation;

import reservation.*;
import java.util.*;
import java.io.*;
import java.lang.Integer;
import ihm.*;


public class Seance 
{
  private DateP date;
  private HashMap disposition;
/*
 * Construit une nouvelle s�ance
 */   
public Seance(DateP date) throws IOException
    {
        date=this.date;
        disposition=new HashMap();// initialement sans cat�gorie
        String nomfich="c:\\Program Files\\Gestion Theatre\\disposition.txt";//fichier dans lequel sera lu la disposition
        Console.ecrireNL("Lecture du fichier disposition.txt");
            try
            {
                BufferedReader entree=new BufferedReader (new FileReader (nomfich));//d�finition d'une entr�e voir p544
                Console.ecrireNL("Lecture en cours");
		
                    while(true)//on lit ligne par ligne
                    {
                        String ligneLue=entree.readLine();
                        
                        if (ligneLue==null) break;//on arr�te de lire quand on arrive � la fin du fichier
                            StringTokenizer tok=new StringTokenizer (ligneLue, " ");// le fichier text contiendra une cat�gorie par ligne
                            String nomCategorie=tok.nextToken();
                            int nbint=Integer.parseInt(tok.nextToken());
                            disposition.put(nomCategorie,new Categorie(nbint, nomCategorie));//le premier �l�ment sera le nom de la cat�gorie, le deuxi�me le nombre de place
                    }
              
                Console.ecrireNL("Fin de la lecture");
                entree.close();//on referme le fichier
            }
            
         catch(FileNotFoundException exc)
         {
            Console.ecrireNL("Erreur d'ouverture");
         }
    }
/*
 * Rend la date d'une s�ance
 */
    public DateP getDate() 
    {
        return date;
    }
/*
 * Rend la disposition de la salle pour une s�ance
 */
    public HashMap getDisposition() 
    {
        return disposition;
    }
/*
 * Permet de conna�tre les diff�rentes cat�gories d'une s�ance
 */ 
    public String getCategories()
    {
        Iterator k=disposition.keySet().iterator();
        String s="  ";
            while (k.hasNext())
            {
                s= s+(String)k.next()+" , ";
            }
        return s;
    }
 /*
  * R�serve un bloc de place et rend un message sur le r�sultat de la r�servation
  */   
    public String reserver(String categorie, int nbPlacesReservation, String nom) 
    {
        if (disposition.containsKey(categorie))
        {
            return ((Categorie )disposition.get(categorie)).reserver(nbPlacesReservation, nom);
        }
        else 
            return "Erreur categorie : les categorie sont "+getCategories();
   
    }
 /*
  * Annule une r�servation et rend un message sur le r�sultat de l'annulation
  */   
    public String annuler(String categorie, String nom)
    {
        if (disposition.containsKey(categorie))
        {
            return ((Categorie)disposition.get(categorie)).annuler(nom);
        }
        else 
            return "Erreur categorie : les categorie sont "+getCategories();
    }
/*
 * Pemet de visualiser l'�tat d'une cat�gorie
 */
    public void voir( String categorie )
    {
        if (disposition.containsKey(categorie))
        {
           ((Categorie)disposition.get(categorie)).voir();
        }
        else
            Console.ecrireNL("Erreur categorie : les categorie sont "+getCategories());
    }  
 }