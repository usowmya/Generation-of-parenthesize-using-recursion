import java.util.ArrayList;

// Class to handle return of multiple arguments between recursive calls
class mulArg {
    // two arguments are used - one for storing the values and the other for 
    //storing respective different combinations of paranthesized expressions
	ArrayList<Float> outputs = new ArrayList<>();
	ArrayList<String> brackets = new ArrayList<>();
    //mapping the arraylists to their respective values
	mulArg(ArrayList<Float> outputs,ArrayList<String> brackets){
		this.outputs=outputs;
		this.brackets=brackets;
	}
}