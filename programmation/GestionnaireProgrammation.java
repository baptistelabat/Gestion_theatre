/* -*-Java-*-
 * ###################################################################
 * 
 *  FILE: "GestionnaireProgrammation.java"
 *                                    created: 2007-03-10 
 *                              
 *  Author: Labat Baptiste / Le Bleis Jacques
 *  E-mail: baptiste.labat@supelec.fr
 *          jacques.lebleis@supelec.fr
 *    
 *  Description: 

 * ###################################################################
 */


package programmation;

import java.util.*;

public class GestionnaireProgrammation 
{
    private static GestionnaireProgrammation singleton;

    private HashMap ensembleSeances;
/*
 * Construit la structure qui contiendra les enregistrements des s�ances
 */	
    public GestionnaireProgrammation()
    {
      ensembleSeances=new HashMap();
    }
 /*
  * Permet de s'assurer de l'unicit� de la programmation
  */ 
    public static GestionnaireProgrammation getSingleton() 
    {
      if (singleton == null)
        singleton = new GestionnaireProgrammation(); 
        
      return singleton;    
    }
/*
 * Permet � d'autres classes d'acc�der � ensembleSeances
 */
    public HashMap getEnsembleSeances()
    {
      return ensembleSeances;
    }
  
/*
 * Permet de supprimer les anciennes s�ances lors d'un changement de date
 */
    public void supprimerSeances(DateP ancienneDate, DateP nouvelleDate) 
    {
      for (int i=ancienneDate.getNum(); i<=(nouvelleDate.getNum()-1) ;i++)
        {
          GestionnaireProgrammation.getSingleton().getEnsembleSeances().remove(new Integer(i));
        }
    }

}