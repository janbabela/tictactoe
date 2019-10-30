import service.GameService;

import java.util.Scanner;

public class Main {

  public GameService gameService = new GameService();

  public static void main(final String[] args) {

    Main main = new Main();
    main.run();

  }

  public void run() {

    Scanner scanInput = new Scanner(System.in);
    System.out.println("Choose to play as 1st (X) or 2nd (O).");
    String xOrO = scanInput.next();

    gameService.playGame(xOrO);

  }

}
