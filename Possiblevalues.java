import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Possiblevalues {
    // expr is used to store the user entered unparanthesized formula
    static String expr;
    // computation is carried out in float notation per requirement and then
    // rounded to integers 
	static ArrayList <Integer> int_values = new ArrayList<>();
    
    // main() function
    public static void main(String[] args) 
    {
    	Scanner sc = new Scanner(System.in);
        // Obtaining user input for unparenthesized formula and resulting value
        System.out.println("Enter an unparanthesized formula:");
	    expr = sc.nextLine();
	    expr = expr.replaceAll("\\s","");
	    System.out.println("Enter a resulting Value:");
	    int find_val =sc.nextInt();
        int len = expr.length(); 
        // function call to solve the problem
        mulArg Out = compute_All(expr, 0, (len-1));
        // all computed output values and their corresponding paranthesized expressions are stored in Out
        // the output float values are converted to integers for comparison with user input resulting value
        for(int vn=0;vn<Out.outputs.size();vn++){
	        int_values.add(Math.round(Out.outputs.get(vn)));
        }
        // finds the index of the first occurrence of resulting value in the outputs array list
        // as computations are done from the right side and stored in order, the first occurrence will
        // have the expression with most operations on the right
        int f  = int_values.indexOf(find_val);
        // 'indexOf' function returns '-1' if given value is not found
        if(f>=0){
	        System.out.println("Output: " + Out.brackets.get(f));
	    }
	    else{
	        System.out.println("not possible");
	    }
    }
	
    // performs appropriate computations based on the operator 
    private static float compute(float a, char op, float b) 
	{ 
	    if (op=='+')   
	    	return a+b; 
	    else if (op=='-')   
	    	return a-b; 
	    else if (op == '*') 
	    	return a*b;
	    else if (op == '/')
	        return a/b;
	    else
	        return 0;
	} 

    // computes values with different combinations of paranthesization
    // based on binary tree concept - each operator in the expression is made the first node and then computed recursively
    private static mulArg compute_All(String expr, int low, int high) 
    {
        // declaration of data types used in the function
		ArrayList<Float> outputs = new ArrayList<>();
		ArrayList<String> brackets = new ArrayList<>();
		ArrayList<String> l_brackets = new ArrayList<>();
		ArrayList<String> r_brackets = new ArrayList<>();
		ArrayList<String> brackets1 = new ArrayList<>();
        
        // if the input expression has one character, the value is added to outputs and brackets array list 
        if (low == high) 
	    { 
	        outputs.add((float)Character.getNumericValue(expr.charAt(low))); 
	        brackets.add(Character.toString(expr.charAt(low))); 
	        return new mulArg(outputs,brackets); 
	    } 
	  
        // if the input expression consists of three characters, an output value is computed using compute() with the middle
        // character as operator. Output and respective paranthesized expressions are stored
        if (low == (high-2)) 
	    { 
	        float num = compute((float)Character.getNumericValue(expr.charAt(low)), expr.charAt(low+1), 
	                       (float)Character.getNumericValue(expr.charAt(low+2)));
	        outputs.add((float) num); 
	        brackets.add("("+expr.charAt(low)+expr.charAt(low+1)+expr.charAt(low+2)+")");
	        return new mulArg(outputs,brackets); 
	    } 
	  
	    // each iteration corresponds to an operator in the input expression
	    for (int i=low+1; i<=high; i+=2) 
	    { 
            // sub-expression to the left of the selected operator is used as input for recursive call of compute_All() function 
            // output values and corresponding paranthesized expressions are stored in their respective array lists
            mulArg l = compute_All(expr, low, i-1);
	        ArrayList <Float> L = l.outputs;
	        l_brackets.addAll(l.brackets);
            // sub-expression to the right of the selected operator is used as input for recursive call of compute_All() function
            // output values and corresponding paranthesized expressions are stored in their respective array lists
            mulArg r = compute_All(expr, i+1, high);
	        ArrayList <Float> R = r.outputs;
	        r_brackets.addAll(r.brackets);
            // computed values corresponding to the left side of the selected operator are iterated through
            for (int ls=0; ls<L.size(); ls++) 
	        { 
                // computed values corresponding to the right side of the selected operator are iterated through
                for (int rs=0; rs<R.size(); rs++) 
	            { 
                    // the evaluated left and right side values are computed accordinly with the selected operator
                    // the output of computation is stored in the array list
	                float Out = compute(L.get(ls), expr.charAt(i),  R.get(rs));
	                outputs.add(Out);
	            } 
	        }
            // computed combinations of paranthesized expressions corresponding to the left side of the selected operator are iterated through
            for (int sb1=0; sb1<l_brackets.size(); sb1++) 
	        { 
                // computed combinations of paranthesized expressions corresponding to the right side of the selected operator are iterated through
                for (int sb2=0; sb2<r_brackets.size(); sb2++) 
	            { 
                    // an appropriate paranthesized expression is formed using found left and right side paranthesized expression
                    // the selected operator is placed in the middle 
                    String ex = "(" + l_brackets.get(sb1) + Character.toString(expr.charAt(i)) + r_brackets.get(sb2) + ")";
                    // computed expressions are stored to an array list
                    brackets1.add(ex);
	            } 
	        }
            // the array lists used to store left and right side values to the selected operator are cleared before 
            // the next operator in the input expression is selected
            l_brackets.clear();
	        r_brackets.clear();
        } 
        // all computed output values in the 'outputs' array list and their respective paranthesized expressions
        // in 'brackets1' array list are returned
	    return  new mulArg(outputs,brackets1); 
	}

}
