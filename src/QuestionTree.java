/** This class should be the one to create the tree of questions and answers. It should try to ask questions and take
 * guesses about what the object the user is thinking about. If the user wants to remember the games, it should try 
 * and have it save into a .txt document and be able to retrieve it back later on. At the end, the class should try to report
 * how many games were played and how many times the computer won. 
 *@author Alan To
 *
 */

import java.util.*;
import java.io.*;

public class QuestionTree {
   UserInterface my;
   private int totalGames;
   private int totalWins;
   private QuestionNode overallRoot;
   
   /** Will be the QuestionTree default constructor. It should initialize everything that's in the fields. 
    *@param ui will be to connect my and ui to UserInterface class
    */
   public QuestionTree (UserInterface ui) {
      my = ui;
      overallRoot = new QuestionNode("Jedi"); //will at first ask if the object is a jedi
      this.totalGames = 0;
      this.totalWins = 0;
   }
   
   /** Play should use recurssion call play helper method. Everytime the play method is used, it should increment amount of games */
   public void play() {
      overallRoot = play(overallRoot);
      totalGames++; //everytime play is called, we increment the amount of total games for one of the methods
   }
   
   /**Helper Method for play, it should do most of the work. The method should be able to ask yes or no questions to the user
    * and take a guess on what object the user is guessing. The method should also take in what the user's object was and have it distinguish from the 
    * guess. This is so we could expand the tree and have it get smarter with more questions and answers.
    *@param root will be used to access questionnode
    *@return the question nodes 
    */
   private QuestionNode play(QuestionNode root) {
      if (root.right == null && root.left == null) { //when computer hit a leaf and can't ask anymore questions, it has to take a guess
         my.print("Would your object happen to be " + root.data + "?");
         
         boolean yesNo = my.nextBoolean();
         if (yesNo == true) { //when the computer wins
            my.println("I win!"); 
            totalWins++;
         }
         
         else { //when computer is incorrect
            my.print("I lose. What is your object?");
            String correctObject = my.nextLine();
            QuestionNode newAnswer = new QuestionNode(correctObject); //we would add droid into new object
            
            my.print("Type a yes/no to distinguish your item from " + root.data + ":");
            String newQuestion = my.nextLine(); //user would make a new question
            QuestionNode question = new QuestionNode(newQuestion);
            my.print("And what is the answer for your object?");
            boolean answer = my.nextBoolean();
            if (answer == true) { //if they say yes
               question.right = root;
               question.left = newAnswer;
            }
            else { //if they say no
               question.left = root;
               question.right = newAnswer;
            }
            root = question;
         }
      }   
      else { //when we didn't hit leaf yet and we gotta keep going down
         my.print(root.data); //ask questions by going down the tree 
         if (my.nextBoolean()) {
            root.left = play(root.left);
         }
         else {
            root.right = play(root.right);
         }
      }
      return root;
   }      
  
   /** The method save is used to call the helper save method*/
   public void save(PrintStream output) {
      save(output, overallRoot); //recursion
   }
   
   /**The helper save method should use the tree created and have each line start with either Q: to 
    * to indicate a question node or A: to indicate an answer (a leaf)
    *@param output store in output file
    *@param root to access question node root
    */
   private void save (PrintStream output, QuestionNode root) {
      if (root != null) {
         if (root.left != null && root.right != null) {
           // my.println("Q:" + root.data);
            output.println("Q:" + root.data);
            save(output, root.left);
            save(output, root.right); //to go down the tree
         }
         else { //when we do hit leaf node and it's the answer
            output.println("A:" + root.data);
         //   my.println("A:" + root.data);
         }
      }
   } 
   
   /** The method should call its private helper to read tree from the file user inserted.
    *@param input will be used to know what the user inserted
    */
   public void load(Scanner input) {
      overallRoot = load(overallRoot, input);
   }
   
   /** The load method should try read a tree from a file. It should replace current tree nodes
    * with a new tree using the information the that file.
    *@param root to access question nodes
    *@input will be use to knw what the user inserted
    *@return the the tree question node
    */
   private QuestionNode load (QuestionNode root, Scanner input) {
      if (input.hasNextLine()) {
         String line = input.nextLine();
         root = new QuestionNode(line.substring(2)); //this is to put the question or answer in without the Q: or A:
         
         if (line.charAt(0) == 'Q') {
            root = new QuestionNode(line.substring(2)); //After Q:, we put the question in the tree
            root.left = load(root, input);
            root.right = load(root, input);
         }
      }
      return root;
   }
   
   /** Method should try and display the amount of games the user have played on the tree
    *@return the total number of games user has played
    */
   public int totalGames() {
      return totalGames;
   }
   
   /** Method should show how many times the computer guessed the user object
    *@return the total number of times the computer gets the object correct and wins
    */
   public int gamesWon() {
      return totalWins;
   }
  
}