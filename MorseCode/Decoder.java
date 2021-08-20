import java.util.Scanner;
public class Decoder extends morseCoder {
	public static void main(String[] args) {
    	String end = "!";
    	Scanner keyboard = new Scanner(System.in);
    	morseCoder test = new Decoder();
    	String userInput = test.readIn(keyboard);
    	String code = "";
    	while (!userInput.equals(end)) {
    		code = test.code(userInput);
    		test.printCode(code);
    		userInput = test.readIn(keyboard);
    	}
    	keyboard.close();
    	System.exit(0);
    }
	
	public String readIn(Scanner keyboard) {
		return super.readIn(keyboard);
	}
	
	public String code(String userInput) {
    	String[] letters = userInput.split(" ");
    	String str = "";
        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < english.length; j++) {
                if (morse[j].equals(letters[i])) {
                    str = str + english[j];
                }
            }
        }
        return str;
	}
	
	public void printCode(String code) {
    	System.out.println(code);
    }
}
