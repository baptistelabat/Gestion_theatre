/* -*-Java-*-
 * ###################################################################
 * 
 *  FILE: "Couple.java"
 *                                    created: 2007-03-10 
 *                              
 *  Author: Labat Baptiste / Le Bleis Jacques
 *  E-mail: baptiste.labat@supelec.fr
 *          jacques.lebleis@supelec.fr
 *    

 * ###################################################################
 */


package reservation;

import java.util.*;


public class Couple 
{
   private int nbPlaces;
   private LinkedList list;
/*
 * Constructeur de Couple
 */
   Couple(int nbPlaces  ,LinkedList i) 
   {
      this.nbPlaces =nbPlaces;
      this.list = i;
   }
/*
 * Rend un entier donnant le premier élément du couple
 */
   public int getNbPlaces() 
   {
      return this.nbPlaces;
   }
/*
 * Rend une liste qui correspond au deuxième élément du couple
 */
   public LinkedList getListe() 
   {
      return this.list;
   }

}

