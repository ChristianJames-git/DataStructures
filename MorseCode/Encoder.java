import java.util.Scanner;
public class Encoder extends morseCoder {
    public static void main(String[] args) {
    	String end = "fuck off";
    	Scanner keyboard = new Scanner(System.in);
    	morseCoder test = new Encoder();
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
    	return super.readIn(keyboard).toLowerCase();
    }
    
    public String code(String userInput) {
        char[] chars = userInput.toCharArray();
        String str = "";
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < english.length; j++) {
                if (english[j] == chars[i]) {
                    str = str + morse[j] + " ";
                }
            }
        }
        return str;
    }
    
    public void printCode(String code) {
    	System.out.println(code);
    }
}
