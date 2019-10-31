package service;

import data.Board;
import data.Move;

import java.util.Optional;

public class ArbiterService {

  public BoardService boardService = new BoardService();

  public boolean checkWinner(Board board, String xOrO) {

    Optional<Move> win = boardService.checkWinningLoosing(board, xOrO, true);
    if (win.isPresent()) {
      System.out.println("Winner is: " + xOrO);
      return true;
    }
    return false;
  }

}
