package service;

import data.Board;
import data.Game;

import java.util.Scanner;

public class MoveService {

  public Game addMove(int i, int j, String xOrO, Game game) {

    Board lastBoard = game.getGame().get(game.getGame().size()-1);
    Board newBoard = new Board();
    String[][] newBoardData = new String[3][3];
    for (int a=0; a<3; a++) {
      for (int b=0; b<3; b++) {
        newBoardData[a][b] = lastBoard.getBoard()[a][b];
      }
    }
    newBoard.setBoard(newBoardData);
    newBoard.getBoard()[i][j] = xOrO;
    game.getGame().add(newBoard);
    return game;
  }

  public Game addInputMove(Scanner scan, Game game, String xOrO) {
    int oMove = scan.nextInt();
    int row = (oMove-1)/3;
    int column = (oMove-1) % 3;
    return addMove(row,column,xOrO, game);
  }
}
