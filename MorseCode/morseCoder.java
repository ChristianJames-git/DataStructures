import java.util.Scanner;
public abstract class morseCoder {
	public char[] english = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            ',', '.', '?', ' '};
	public String[] morse = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..",
            ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.",
            "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----",
            "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.",
            "-----", "--..--", ".-.-.-", "..--..", "|"};
	
	public String readIn(Scanner keyboard) {
		System.out.println(" This is an English to Morse Code Translator.  ");
        System.out.println(" Please enter what you would like translate ");
        System.out.println("             into Morse Code. ");
        System.out.println(" ============================================ ");

        String userInput = keyboard.nextLine();
        return userInput;
	}
	
	public String code(String userInput) {
		return null;
	}
	
	public void printCode(String code) {
		System.out.println("Fucking Failure");
	}
}
