//Source file: C:\\Documents and Settings\\lebleis_jac\\Bureau\\esu\\ihm\\Console.java

/* -*-Java-*-
 * ###################################################################
 * 
 *  FILE: "Console.java"
 *                                    created: 2002-07-16 17:38:50 
 *                                last update: 2003-05-05 15:17:39 
 *  Author: Fr�d�ric Boulanger
 *  E-mail: Frederic.Boulanger@supelec.fr
 *    mail: Sup�lec -- Service Informatique
 *          Plateau de Moulon, 3 rue Joliot-Curie, F-91912 Gif-sur-Yvette cedex
 *     www: http://wwwsi.supelec.fr/fb/
 *  
 *  Description: 
 * 
 *  History
 * 
 *  modified   by  rev reason
 *  ---------- --- --- -----------
 *  2002-07-16 FBO 1.0 original
 *  2002-07-17 FBO 1.1 Console est abstraite (pas d'instanciation)
 *  2002-07-18 FBO 1.2 Nettoyage, messages d'erreur pour les nombres (YN)
 *  2003-05-05 FBO 1.3 Nouvelle m�thode sauterBlancsDansLigne
 * ###################################################################
 */

package ihm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.HashMap;

/**
 * Classe permettant des entr�es-sorties simplifi�es sur la console.
 */
public abstract class Console 
{
   
   /**
    * Ligne courante du flux d'entr�e.
    */
   private static String ligne_;
   
   /**
    * Position du prochain caract�re � lire dans la ligne courante.
    */
   private static int position_;
   
   /**
    * Nombre de caract�res dans la ligne courante.
    */
   private static int longueur_;
   
   /**
    * Dernier token lu au cours de la lecture d'un nombre.
    */
   private static String dernierToken_;
   private static HashMap maxRepresentable_;
   private static HashMap minRepresentable_;
   
   /**
    * Indique si on est � la fin du flux.
    */
   private static boolean eof_;
   
   /**
    * Flux de caract�res duquel sont lues les lignes.
    */
   private static BufferedReader in_;
   
   /**
    * Format utilis� pour lire et �crire les nombres.
    */
   private static NumberFormat format_;
   
   static 
   {
    // On choisit des r�glages "standards" pour avoir un
    // environnement homog�ne sur toutes les machines
    // quelle que soit leur locale de d�faut, ainsi que
    // pour que les m�thodes d'affichage produisent un
    // r�sultat lisible par les m�thodes de lecture.
    format_ = NumberFormat.getNumberInstance(Locale.US);
    
    // Initialisation des tables contenant les bornes repr�sentables
    // des diff�rents types scalaires.
    maxRepresentable_ = new HashMap();
    maxRepresentable_.put("octet", new Double(Byte.MAX_VALUE));
    maxRepresentable_.put("short", new Double(Short.MAX_VALUE));
    maxRepresentable_.put("int", new Double(Integer.MAX_VALUE));
    maxRepresentable_.put("long", new Double(Long.MAX_VALUE));
    maxRepresentable_.put("float", new Double(Float.MAX_VALUE));
    maxRepresentable_.put("double", new Double(Double.MAX_VALUE));
    minRepresentable_ = new HashMap();
    minRepresentable_.put("octet", new Double(Byte.MIN_VALUE));
    minRepresentable_.put("short", new Double(Short.MIN_VALUE));
    minRepresentable_.put("int", new Double(Integer.MIN_VALUE));
    minRepresentable_.put("long", new Double(Long.MIN_VALUE));
    minRepresentable_.put("float", new Double(- Float.MAX_VALUE));
    minRepresentable_.put("double", new Double(- Double.MAX_VALUE));
    
    reouvrir();    
   }
   
   /**
    * Ouvre un nouveau flux de caract�res sur <code>System.in</code>.
    * @roseuid 43FF3DCC038C
    */
   public static void reouvrir() 
   {
    ligne_ = null;
    position_ = 0;
    longueur_ = 0;
    dernierToken_ = null;
    in_ = new BufferedReader(new InputStreamReader(System.in));
    eof_ = false;    
   }
   
   /**
    * Rend un caract�re lu sur la console. Le caract�re n'a vraiment �t� lu
    * que si eof() est faux apr�s la lecture.
    * @return char
    * @roseuid 43FF3DCC038D
    */
   public static char lireChar() 
   {
    if (suivantExiste()) {
      return ligne_.charAt(position_++);
    } else {
      return '*';   // on peut rendre n'importe quoi
    }    
   }
   
   /**
    * Annule l'effet de la derni�re invocation de <code>lireChar</code>.
    * Ne fonctionne que si la derni�re methode de lecture invoqu�e est
    * <code>lireChar</code> et que <code>eof</code> �tait faux lors de cette 
    * invocation. Le comportement de <code>remettreDernierChar</code> est
    * ind�fini dans les autres cas.
    * @roseuid 43FF3DCC039B
    */
   public static void remettreDernierChar() 
   {
    if ((ligne_ == null) || (position_ == 0)) {
      // Si le dernier lireChar() a rencontr� EOF, on annule
      eof_ = false;
    } else {
      // Sinon, on revient en arri�re d'un caract�re
      position_ --;
    }    
   }
   
   /**
    * Ignore tous les caract�res restant sur la ligne si on a d�j�
    * lu au moins un caract�re de la ligne.
    * @roseuid 43FF3DCC039C
    */
   public static void jeterResteLigne() 
   {
    if (position_ > 0) {
      position_ = longueur_;
    }    
   }
   
   /**
    * Ignore tous les caract�res "blancs" (espace, tabulation etc.)
    * Apr�s l'invocation de cette m�thode, on est soit en fin de flux,
    * soit sur un caract�re non-blanc.
    * @roseuid 43FF3DCC03AB
    */
   public static void sauterLesBlancs() 
   {
    char c;
    
    do {
      c = lireChar();
    } while ((!eof()) && Character.isWhitespace(c));
    
    if (!eof()) {
      remettreDernierChar();
    }    
   }
   
   /**
    * Ignore tous les caract�res "blancs" dans la ligne courante.
    * Apr�s l'invocation de cette m�thode, on est soit en fin de flux,
    * soit en fin de ligne, soit sur un caract�re non-blanc.
    * @roseuid 43FF3DCC03AC
    */
   public static void sauterBlancsDansLigne() 
   {
    if (!suivantExiste()) {
      return;
    }
    
    while ((position_ < (longueur_ - 1))
           && Character.isWhitespace(ligne_.charAt(position_))) {
      position_++;
    }    
   }
   
   /**
    * Rend un mot (suite de caract�res sans "blanc") lu sur la console.
    * @return java.lang.String
    * @roseuid 43FF3DCC03AD
    */
   public static String lireMot() 
   {
    sauterLesBlancs();
    if (eof()) {
      return "";
    }
    int blanc = position_;
    while (   (blanc < longueur_)
           && (!Character.isWhitespace(ligne_.charAt(blanc))) ) {
      blanc ++;
    }
    String mot = ligne_.substring(position_, blanc);
    position_ = blanc;
    return mot;    
   }
   
   /**
    * Rend une cha�ne contenant tous les caract�res rencontr�s sur le
    * flux jusqu'� une fin de ligne ou la fin du flux.  L'�ventuel
    * marqueur de fin de ligne ne fait pas partie de la cha�ne rendue.
    * @return java.lang.String
    * @roseuid 43FF3DCC03BA
    */
   public static String lireLigne() 
   {
    if (!suivantExiste()) { // assure que le tampon est rempli,
      return "";            //        contrairement � eof().
    }
    String lue = ligne_.substring(position_, longueur_ - 1);
    position_ = longueur_;
    return lue;    
   }
   
   /**
    * Rend un bool�en dont la repr�sentation est lue � la console. 
    * Re-essaye tant que les caract�res lus ne peuvent pas �tre
    * interpr�t�s comme un bool�en et que la fin du flux de caract�res
    * n'est pas rencontr�e.  Les mots <code>"true"</code> et
    * <code>"t"</code> sont interpr�t�s comme la valeur bool�enne
    * <code>true</code>, sans tenir compte de la casse.  Les mots
    * <code>"false"</code> et <code>"f"</code> sont interpr�t�s comme la
    * valeur bool�enne <code>false</code>, sans tenir compte de la casse.
    * @return boolean
    * @roseuid 43FF3DCC03BB
    */
   public static boolean lireBool() 
   {
    sauterLesBlancs();
    while (true) {
      String mot = lireMot();
      if (mot == null) {
        return false;
      }
      if (mot.equalsIgnoreCase("true") || mot.equalsIgnoreCase("t")) {
        return true;
      }
      if (mot.equalsIgnoreCase("false") || mot.equalsIgnoreCase("f")) {
        return false;
      }
      System.err.println("Erreur : booleen '"
                         + mot
                         + "' incorrect; ignore."
                        );
    }    
   }
   
   /**
    * Rend un octet dont la repr�sentation d�cimale est lue sur la
    * console.  Re-essaie tant que les caract�res lus ne constituent pas la
    * repr�sentation d'un entier, ou si l'entier correspondant ne peut pas
    * �tre cod� sur un octet.
    * @return byte
    * @roseuid 43FF3DCC03CA
    */
   public static byte lireOctet() 
   {
    format_.setParseIntegerOnly(true); // on cherche � lire un entier
    while (!eof()) {             // tant qu'on peut r�-essayer
      Number n = lireNombre();   // on cherche � lire un nombre
      if (n == null) {           // si le format est incorrect
        continue;                // on r�-essaye
      }
      if (verifBornes(n, "octet")) { // si ce qu'on a lu tient dans un octet
        return n.byteValue();        // on le rend, sinon, on r�-essaye
      }
    }
    return -1;  // il faut bien rendre quelquechose...    
   }
   
   /**
    * Rend un short dont la repr�sentation d�cimale est lue sur la
    * console.  Re-essaie tant que les caract�res lus ne constituent pas la
    * repr�sentation d'un entier, ou si l'entier correspondant ne peut pas
    * �tre cod� sur un short.
    * @return short
    * @roseuid 43FF3DCC03CB
    */
   public static short lireShort() 
   {
    format_.setParseIntegerOnly(true); // idem que lireOctet()
    while (!eof()) {
      Number n = lireNombre();
      if (n == null) {
        continue;
      }
      if (verifBornes(n, "short")) {
        return n.shortValue();
      }
    }
    return -1;    
   }
   
   /**
    * Rend un int dont la repr�sentation d�cimale est lue sur la console. 
    * Re-essaie tant que les caract�res lus ne constituent pas la
    * repr�sentation d'un entier, ou si l'entier correspondant ne peut pas
    * �tre cod� sur un int.
    * @return int
    * @roseuid 43FF3DCC03DA
    */
   public static int lireInt() 
   {
    format_.setParseIntegerOnly(true); // idem que lireOctet()
    while (!eof()) {
      Number n = lireNombre();
      if (n == null) {
        continue;
      }
      if (verifBornes(n, "int")) {
        return n.intValue();
      }
    }
    return -1;    
   }
   
   /**
    * Rend un long dont la repr�sentation d�cimale est lue sur la console. 
    * Re-essaie tant que les caract�res lus ne constituent pas la
    * repr�sentation d'un entier, ou si l'entier correspondant ne peut pas
    * �tre cod� sur un long.
    * @return long
    * @roseuid 43FF3DCC03DB
    */
   public static long lireLong() 
   {
    format_.setParseIntegerOnly(true); // idem que lireOctet()
    while (!eof()) {
      Number n = lireNombre();
      if (n == null) {
        continue;
      }
      if (verifBornes(n, "long")) {
        return n.longValue();
      }
    }
    return -1;    
   }
   
   /**
    * Rend un float dont la repr�sentation d�cimale est lue sur la
    * console.  Re-essaie tant que les caract�res lus ne constituent pas la
    * repr�sentation d'un flottant, ou si le flottant correspondant ne peut
    * pas �tre cod� sur un float.
    * @return float
    * @roseuid 43FF3DCD0001
    */
   public static float lireFloat() 
   {
    format_.setParseIntegerOnly(false); // ici, on accepte les non-entiers
    while (!eof()) {
      Number n = lireNombre();
      if (n == null) {
        continue;
      }
      if (verifBornes(n, "float")) {
        return n.floatValue();
      }
    }
    return -1.0f;    
   }
   
   /**
    * Rend un double dont la repr�sentation d�cimale est lue sur la
    * console.  Re-essaie tant que les caract�res lus ne constituent pas la
    * repr�sentation d'un flottant, ou si le flottant correspondant ne peut
    * pas �tre cod� sur un double.
    * @return double
    * @roseuid 43FF3DCD0002
    */
   public static double lireDouble() 
   {
    format_.setParseIntegerOnly(false); // ici, on accepte les non-entiers
    while (!eof()) {
      Number n = lireNombre();
      if (n == null) {
        continue;
      }
      if (verifBornes(n, "double")) {
        return n.doubleValue();
      }
    }
    return -1.0;    
   }
   
   /**
    * Indique si la fin du flux est atteinte.
    * @return boolean
    * @roseuid 43FF3DCD0003
    */
   public static boolean eof() 
   {
    return eof_;    
   }
   
   /**
    * Affiche une cha�ne sur la console.
    * @param s
    * @roseuid 43FF3DCD0011
    */
   public static void ecrire(String s) 
   {
    System.out.print(s);    
   }
   
   /**
    * Affiche un caract�re sur la console.
    * @param c
    * @roseuid 43FF3DCD0013
    */
   public static void ecrire(char c) 
   {
    System.out.print(c);    
   }
   
   /**
    * Affiche un bool�en sur la console.
    * @param b
    * @roseuid 43FF3DCD0022
    */
   public static void ecrire(boolean b) 
   {
    System.out.print(b);    
   }
   
   /**
    * Affiche la repr�sentation d�cimale d'un octet sur la console.
    * @param b
    * @roseuid 43FF3DCD0030
    */
   public static void ecrire(byte b) 
   {
    System.out.print(b);    
   }
   
   /**
    * Affiche la repr�sentation d�cimale d'un short sur la console.
    * @param s
    * @roseuid 43FF3DCD0032
    */
   public static void ecrire(short s) 
   {
    System.out.print(s);    
   }
   
   /**
    * Affiche la repr�sentation d�cimale d'un int sur la console.
    * @param i
    * @roseuid 43FF3DCD0041
    */
   public static void ecrire(int i) 
   {
    System.out.print(i);    
   }
   
   /**
    * Affiche la repr�sentation d�cimale d'un long sur la console.
    * @param l
    * @roseuid 43FF3DCD0043
    */
   public static void ecrire(long l) 
   {
    System.out.print(l);    
   }
   
   /**
    * Affiche la repr�sentation d�cimale d'un float sur la console.
    * @param f
    * @roseuid 43FF3DCD0050
    */
   public static void ecrire(float f) 
   {
    System.out.print(f);    
   }
   
   /**
    * Affiche la repr�sentation d�cimale d'un double sur la console.
    * @param d
    * @roseuid 43FF3DCD005F
    */
   public static void ecrire(double d) 
   {
    System.out.print(d);    
   }
   
   /**
    * Passe au d�but de la ligne suivante de la console.
    * @roseuid 43FF3DCD0061
    */
   public static void alaligne() 
   {
    System.out.println();    
   }
   
   /**
    * Affiche une cha�ne sur la console et passe � la ligne.
    * @param s
    * @roseuid 43FF3DCD006F
    */
   public static void ecrireNL(String s) 
   {
    ecrire(s);
    alaligne();    
   }
   
   /**
    * Affiche un caract�re sur la console et passe � la ligne.
    * @param c
    * @roseuid 43FF3DCD0071
    */
   public static void ecrireNL(char c) 
   {
    ecrire(c);
    alaligne();    
   }
   
   /**
    * Affiche un bool�en sur la console et passe � la ligne.
    * @param b
    * @roseuid 43FF3DCD007F
    */
   public static void ecrireNL(boolean b) 
   {
    ecrire(b);
    alaligne();    
   }
   
   /**
    * Affiche la repr�sentation d�cimale d'un octet sur la console 
    * et passe � la ligne.
    * @param b
    * @roseuid 43FF3DCD008F
    */
   public static void ecrireNL(byte b) 
   {
    ecrire(b);
    alaligne();    
   }
   
   /**
    * Affiche la repr�sentation d�cimale d'un short sur la console 
    * et passe � la ligne.
    * @param s
    * @roseuid 43FF3DCD0091
    */
   public static void ecrireNL(short s) 
   {
    ecrire(s);
    alaligne();    
   }
   
   /**
    * Affiche la repr�sentation d�cimale d'un int sur la console 
    * et passe � la ligne.
    * @param i
    * @roseuid 43FF3DCD009F
    */
   public static void ecrireNL(int i) 
   {
    ecrire(i);
    alaligne();    
   }
   
   /**
    * Affiche la repr�sentation d�cimale d'un long sur la console 
    * et passe � la ligne.
    * @param l
    * @roseuid 43FF3DCD00AD
    */
   public static void ecrireNL(long l) 
   {
    ecrire(l);
    alaligne();    
   }
   
   /**
    * Affiche la repr�sentation d�cimale d'un float sur la console 
    * et passe � la ligne.
    * @param f
    * @roseuid 43FF3DCD00BD
    */
   public static void ecrireNL(float f) 
   {
    ecrire(f);
    alaligne();    
   }
   
   /**
    * Affiche la repr�sentation d�cimale d'un double sur la console 
    * et passe � la ligne.
    * @param d
    * @roseuid 43FF3DCD00BF
    */
   public static void ecrireNL(double d) 
   {
    ecrire(d);
    alaligne();    
   }
   
   /**
    * Lit un nombre sur le flux associ� � la console, selon le format
    * <code>fmt</code>.  Le format est utilis� � partir du premier
    * caract�re non-blanc suivant la position courante.  �choue et rend
    * <code>null</code> si les caract�res lus ne peuvent pas �tre
    * interpr�t�s comme la repr�sentation d�cimale d'un nombre.
    * @return java.lang.Number
    * @roseuid 43FF3DCD00CD
    */
   private static Number lireNombre() 
   {
    // On commence par ignorer les blancs
    sauterLesBlancs();
    if (eof()) {
      return null;
    }
    // L'analyse d�bute � la position courante dans la ligne
    ParsePosition pos = new ParsePosition(position_);
    Number lu = format_.parse(ligne_, pos);
    // Si l'analyse n'a pas fait progresser la position, il y a 
    // une erreur de format.
    if (pos.getIndex() == position_) {
      System.err.println("Erreur : nombre '"
                         + lireMot()
                         + "' incorrect; ignore."
                        );
      return null;
    }
    // On m�morise le token lu, au cas o� il devrait �tre affich�
    dernierToken_ = ligne_.substring(position_, pos.getIndex());
    // On fait progresser la position dans la ligne courante.
    position_ = pos.getIndex();
    return lu;    
   }
   
   /**
    * Indique si le nombre <code>n</code> peut �tre repr�sent� 
    * dans le domaine du type <code>type</code>.
    * @param n
    * @param type
    * @return boolean
    * @roseuid 43FF3DCD00DC
    */
   private static boolean verifBornes(Number n, String type) 
   {
    String erreur = null;
    
    if (n.doubleValue() > ((Double) maxRepresentable_.get(type)).doubleValue()) {
      erreur = new String("grand");
    } else if (n.doubleValue() < ((Double) minRepresentable_.get(type)).doubleValue()) {
      erreur = new String("petit");
    }
    if (erreur == null) {
      return true;
    }
    
    System.err.println("Erreur : nombre '"
                     + dernierToken_
                     + "' trop " + erreur + " pour un "
                     + type + "; ignore.");
    return false;    
   }
   
   /**
    * Indique s'il existe un caract�re suivant dans le flux. Peut provoquer
    * une lecture sur la console.
    * @return boolean
    * @roseuid 43FF3DCD00FC
    */
   private static boolean suivantExiste() 
   {
    if (eof_) {
      return false; // Si on a d�j� rencontr� la fin de fichier, yenaplus
    }
    if (position_ < longueur_) {
      return true;  // Si on n'a pas �puis� la ligne courante, yenaencore
    }
    // On n'a rien en stock, il faut lire une nouvelle ligne
    try {
      ligne_ = in_.readLine();
    } catch (Exception e) {
      eof_ = true;  // on traite les erreurs d'E/S comme une fin de fichier
      return false;
    }
    // readLine rend null pour indiquer la fin de fichier
    if (ligne_ == null) {
      eof_ = true;
      return false;
    }
    // On ajoute le '\n' qui permettra de d�tecter les fins de 
    // lignes avec lireChar().
    ligne_ = ligne_ + '\n';
    longueur_ = ligne_.length();
    position_ = 0;
    return true;    
   }
}
