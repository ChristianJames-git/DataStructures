import java.util.Scanner;

public class ItalianEncoder extends Encoder {
	public static void main(String[] args) {
		String end = "fuck off";
    	Scanner keyboard = new Scanner(System.in);
    	Encoder test = new ItalianEncoder();
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
		System.out.println(" This is an English to Italian Translator.  ");
        System.out.println(" Please enter what you would like translate ");
        System.out.println("             into Italian. ");
        System.out.println(" ============================================ ");

        String userInput = keyboard.nextLine().toLowerCase();
        return userInput;
	}
	
	public void printCode(String code) {
		char[] chars = code.toCharArray();
		char[] decoding = {'.', '-', ' ', '|'};
		String str = "";
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == decoding[0])
                str = str + "Pizza";
            else if (chars[i] == decoding[1])
            	str = str + "Pasta";
            else if (chars[i] == decoding[2])
            	str = str + " ";
            else if (chars[i] == decoding[3])
            	str = str + " | ";
        }
        System.out.println(str);
	}
}
