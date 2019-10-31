package service;

import data.Game;
import strategy.StrategyO;
import strategy.StrategyX;

import java.util.Scanner;

public class GameService {

  public VisualizationService visualizationService = new VisualizationService();
  public MoveService moveService = new MoveService();
  public ArbiterService arbiterService = new ArbiterService();
  public StrategyX strategyX = new StrategyX();
  public StrategyO strategyO = new StrategyO();

  public void playMoreGames(String xOrO) {

    playGame(xOrO);
    System.out.println("Next game? ");
    Scanner scanInput = new Scanner(System.in);
    String nextGame = scanInput.next();

    while (nextGame.equals("Y")) {
      playGame(xOrO);
      System.out.println("Next game? ");
      nextGame = scanInput.next();
    }
  }

  public void playGame(String xOrO) {

    Game game = new Game();
    Scanner scanInput = new Scanner(System.in);
    int countMoves = 0;

    if (xOrO.equals("O")) {

      game = strategyX.doMove(game);
      visualizationService.drawBoard(game);
      countMoves++;
      while (countMoves < 9) {
        game = moveService.addInputMove(scanInput, game, "O");
        visualizationService.drawBoard(game);
        if (arbiterService.checkWinner(game.getLastBoard(), "O")) break;
        countMoves++;

        game = strategyX.doMove(game);
        visualizationService.drawBoard(game);
        if (arbiterService.checkWinner(game.getLastBoard(), "X")) break;
        countMoves++;
      }

      if (countMoves == 9) System.out.println("Game ends in draw.");

    } else if (xOrO.equals("X")) {
      System.out.println("Do your first move.");
      game = moveService.addInputMove(scanInput, game, "X");
      visualizationService.drawBoard(game);
      countMoves++;

      while (countMoves < 9) {

        game = strategyO.doMove(game);
        visualizationService.drawBoard(game);
        if (arbiterService.checkWinner(game.getLastBoard(), "O")) {
          strategyO.updateEvaluations(game, "O");
          break;
        }
        countMoves++;

        game = moveService.addInputMove(scanInput, game, "X");
        visualizationService.drawBoard(game);
        if (arbiterService.checkWinner(game.getLastBoard(), "X")) {
          strategyO.updateEvaluations(game, "X");
          break;
        }
        countMoves++;
      }
      if (countMoves == 9) {
        System.out.println("Game ends in draw.");
        strategyO.updateEvaluations(game, "D");
      }
    } else if (xOrO.equals("P")) {
      try {
        game = strategyX.doMove(game);
        visualizationService.drawBoard(game);
        countMoves++;
        Thread.sleep(1000);
        while (countMoves < 9) {
          game = strategyO.doMove(game);
          visualizationService.drawBoard(game);
          arbiterService.checkWinner(game.getLastBoard(), "O");
          countMoves++;
          Thread.sleep(1000);

          game = strategyX.doMove(game);
          visualizationService.drawBoard(game);
          arbiterService.checkWinner(game.getLastBoard(), "X");
          countMoves++;
          Thread.sleep(1000);
        }
        System.out.println("Game ends in draw.");
      } catch (InterruptedException e) {
        System.out.println("Interrupted with exception: " + e.getMessage());
      }
    } else {
      game = strategyX.doMove(game);
      countMoves++;
      while (countMoves < 9) {
        game = strategyO.doMove(game);
        arbiterService.checkWinner(game.getLastBoard(), "O");
        countMoves++;

        game = strategyX.doMove(game);
        arbiterService.checkWinner(game.getLastBoard(), "X");
        countMoves++;
      }
    }
  }
}