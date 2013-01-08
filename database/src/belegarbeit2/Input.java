package belegarbeit2;

import java.io.IOException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

  /**
   * This method captures an user input of the type int in a specified
   * range.
   *
   * @param min Minimum value
   * @param max Maximum value
   * @return The (int) input of the user
   */
  public static int intInput(int min, int max) {
    Scanner sc = new Scanner(System.in);
    boolean run = true;
    int input = 0;
    while (run) {
      boolean check = true;
      try {
        input = sc.nextInt();
      } catch (InputMismatchException e) {
        sc.next();
        check = false;
        System.err.println("\nFehler: Die Eingabe ist falsch. Bitte versuchen Sie es noch einmal!\n");
      }
      if (check && (input >= min && input <= max)) {
        run = false;
      } else {
        System.err.printf("Fehler : Die Nummer muss zwischen " + min + " und " + max + " sein.");
        System.err.println("Bitte versuchen Sie es noch einmal: ");
      }
    }
    return input;
  }

  public static int intInput() {
    Scanner sc = new Scanner(System.in);
    boolean run = true;
    int input = 0;
    while (run) {
      try {
        input = sc.nextInt();
        run = false;
      } catch (InputMismatchException e) {
        sc.next();
        System.err.println("\nFehler: Die Eingabe ist falsch. Bitte versuchen Sie es noch einmal!\n");
      }
    }
    return input;
  }

  public static String stringInput() {
    Scanner sc = new Scanner(System.in);
    String input = sc.nextLine();
    return input;
  }

  public static char charInput() {
    Scanner sc = new Scanner(System.in);
    String input = sc.nextLine();
    char[] array = input.toCharArray();
    return array[0];
  }

  public static int stringToInt() {
    int dummy = 0;
    boolean run = true;
    while (run) {
      String input = stringInput();
      if (!input.equals("")) {
        try {
          dummy = Integer.parseInt(input);
          run = false;

        } catch (NumberFormatException e) {
          System.out.println("Dies ist keine Zahl. Bitte versuchen Sie es erneut!");
        }
      }

    }
    return dummy;
  }
}