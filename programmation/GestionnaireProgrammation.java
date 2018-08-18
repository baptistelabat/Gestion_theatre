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
 * Construit la structure qui contiendra les enregistrements des séances
 */	
    public GestionnaireProgrammation()
    {
      ensembleSeances=new HashMap();
    }
 /*
  * Permet de s'assurer de l'unicité de la programmation
  */ 
    public static GestionnaireProgrammation getSingleton() 
    {
      if (singleton == null)
        singleton = new GestionnaireProgrammation(); 
        
      return singleton;    
    }
/*
 * Permet à d'autres classes d'accéder à ensembleSeances
 */
    public HashMap getEnsembleSeances()
    {
      return ensembleSeances;
    }
  
/*
 * Permet de supprimer les anciennes séances lors d'un changement de date
 */
    public void supprimerSeances(DateP ancienneDate, DateP nouvelleDate) 
    {
      for (int i=ancienneDate.getNum(); i<=(nouvelleDate.getNum()-1) ;i++)
        {
          GestionnaireProgrammation.getSingleton().getEnsembleSeances().remove(new Integer(i));
        }
    }

}