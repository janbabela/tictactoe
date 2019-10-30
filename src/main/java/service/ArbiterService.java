package service;

import data.Board;
import data.Move;

import java.util.Optional;

public class ArbiterService {

  public BoardService boardService = new BoardService();

  public void checkWinner(Board board, String xOrO) {

    Optional<Move> win = boardService.checkWinningLoosing(board, xOrO, true);
    if (win.isPresent()) {
      System.out.println("Winner is: " + xOrO);
      System.exit(0);
    }
  }

}
