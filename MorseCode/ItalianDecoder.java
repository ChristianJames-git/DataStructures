import java.util.Scanner;

public class ItalianDecoder extends Decoder {
	public static void main(String[] args) {
		String end = "!";
    	Scanner keyboard = new Scanner(System.in);
    	Decoder test = new ItalianDecoder();
    	String userInput = test.readIn(keyboard);
    	while (!userInput.equals(end)) {
    		test.printCode(userInput);
    		userInput = test.readIn(keyboard);
    	}
    	keyboard.close();
    	System.exit(0);
	}
	
	public String readIn(Scanner keyboard) {
		System.out.println(" This is an Italian to English Translator.  ");
        System.out.println(" Please enter what you would like translate ");
        System.out.println("             into English. ");
        System.out.println(" ============================================ ");

        String userInput = keyboard.nextLine();
        return userInput;
	}
	
	public void printCode(String italian) {
		String code = "";
		for (int i = 0; i < italian.length(); i++) {
			if (italian.charAt(i) == 'P') {
				switch (italian.charAt(i+1)) {
					case 'i':
						code = code + ".";
						break;
					case 'a':
						code = code + "-";
						break;
					default:
						System.out.println("You Fucked Up Bad");
				}
			} else if (italian.charAt(i) == ' ') {
				if (i + 1 < italian.length()) {
					switch (italian.charAt(i+1)) {
					case 'P':
						code = code + " ";
						break;
					case '|':
						code = code + " |";
						break;
					default:
						System.out.println("You Fucked Up Badly");
					}
				}
			}
		}
		System.out.println(code);
		String a = super.code(code);
		super.printCode(a);
	}
}
