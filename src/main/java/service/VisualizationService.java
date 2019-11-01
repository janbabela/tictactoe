package service;

import data.Board;
import data.Game;

public class VisualizationService {

  public void showGame(Game game) {
    for (int i = 0; i< game.getGame().size(); i++) {
      drawBoard(game.getGame().get(i));
    }
  }

  public void drawBoard(Board board) {
    String[][] boardToDraw = board.getBoard();
    for (int i = 0; i < 3; i++) {
      System.out.println(boardToDraw[i][0] + " " + boardToDraw[i][1] + " " + boardToDraw[i][2]);
    }
    System.out.println(" ");
  }

  public void drawBoard(Game game) {
    Board board = game.getLastBoard();
    drawBoard(board);
  }
}
