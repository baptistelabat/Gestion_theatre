/* -*-Java-*-
 * ###################################################################
 * 
 *  FILE: "DateP.java"
 *                                    created: 2007-03-10 
 *                              
 *  Author: Labat Baptiste / Le Bleis Jacques
 *  E-mail: baptiste.labat@supelec.fr
 *          jacques.lebleis@supelec.fr
 *    
 *  Description: Attribution d'un numéro à chaque jour de l'année

 * ###################################################################
 */


package programmation;

public class DateP // Date existe déjà dans java.util
{	
   private int num;//numéro unique attribuée à une date
/*
 * Construit une date
 */
   public DateP(int jour, int mois, int annee)
   {
        num=jour+12*mois+365*annee;
    }
/*
 * Rend un entier correspondant au numéro correspondant à la date
 */	
   public int getNum()
   {
        return num;   
    }
 /*
  * Permet d'afficher la date sous un format standard
  */   
   public String afficherDate()
   {
        int a=num/365;
        int m=(num-365*a)/12;
        int j=(num-365*a-12*m);
         
        return j+"/"+m+"/"+a;
    } 
    
}