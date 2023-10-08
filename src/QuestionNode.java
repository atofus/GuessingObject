public class QuestionNode {
   public String data;
   public QuestionNode left; //yes should be left side
   public QuestionNode right; //no should be right side
   
   
   public QuestionNode(String data) {
      this(data, null, null);
   }
   
   public QuestionNode (String data, QuestionNode yes, QuestionNode no) {
      this.data = data;
      this.right = yes;
      this.left = no;
   }
}