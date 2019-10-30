package service;

import data.Board;
import data.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class BoardService {

  Random rand = new Random();

  public Optional<Move> checkWinningLoosing(Board board, String xOrO, boolean arbiter) {

    String[][] boardData = board.getBoard();
    Optional<Integer> winningMove;

    for (int i = 0; i < 3; i++) {
      // check rows
      winningMove = checkTripletForWinLoose(boardData[i], xOrO, arbiter);
      if (winningMove.isPresent()) {
        Move move = new Move(i, winningMove.get());
        return Optional.ofNullable(move);
      }
    }
    for (int j = 0; j < 3; j++) {
      // check columns
      String[] column = { boardData[0][j], boardData[1][j], boardData[2][j] };
      winningMove = checkTripletForWinLoose(column, xOrO, arbiter);
      if (winningMove.isPresent()) {
        Move move = new Move(winningMove.get(), j);
        return Optional.ofNullable(move);
      }
    }
    String[] firstDiagonal = { boardData[0][0], boardData[1][1], boardData[2][2] };
    winningMove = checkTripletForWinLoose(firstDiagonal, xOrO, arbiter);
    if (winningMove.isPresent()) {
      Move move = new Move(winningMove.get(), winningMove.get());
      return Optional.ofNullable(move);
    }

    String[] secondDiagonal = { boardData[0][2], boardData[1][1], boardData[2][0] };
    winningMove = checkTripletForWinLoose(secondDiagonal, xOrO, arbiter);
    if (winningMove.isPresent()) {
      Move move = new Move(winningMove.get(), 2 - winningMove.get());
      return Optional.ofNullable(move);
    }

    return Optional.ofNullable(null);
  }

  private Optional<Integer> checkTripletForWinLoose(String[] triplet, String xOrO, boolean arbiter) {
    if (!arbiter) {
      return checkTripletForWinLooseStrategy(triplet, xOrO);
    }
    else {
      return checkTripletForWinLooseArbiter(triplet, xOrO);
    }
  }

  private Optional<Integer> checkTripletForWinLooseStrategy(String[] triplet, String xOrO) {
    if (triplet[0].equals(xOrO) && triplet[1].equals(xOrO) && triplet[2].equals("_")) {
      return Optional.ofNullable(2);
    }
    if (triplet[0].equals(xOrO) && triplet[2].equals(xOrO) && triplet[1].equals("_")) {
      return Optional.ofNullable(1);
    }
    if (triplet[1].equals(xOrO) && triplet[2].equals(xOrO) && triplet[0].equals("_")) {
      return Optional.ofNullable(0);
    }
    return Optional.ofNullable(null);
  }

  private Optional<Integer> checkTripletForWinLooseArbiter(String[] triplet, String xOrO) {
    if (triplet[0].equals(xOrO) && triplet[1].equals(xOrO) && triplet[2].equals(xOrO)) {
      return Optional.ofNullable(2);
    }
    return Optional.ofNullable(null);
  }

  public Move makeRandomMove(Board board) {

    List<Move> freeMoves = findFreeMoves(board);
    if (freeMoves.size() == 0) {
      return null;
    } else {
      int randomMove = rand.nextInt(freeMoves.size());
      return freeMoves.get(randomMove);
    }
  }

  public List<Move> findFreeMoves(Board board) {
    List<Move> result = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board.getBoard()[i][j] == "_") {
          Move newFreeMove = new Move(i, j);
          result.add(newFreeMove);
        }
      }
    }
    return result;
  }
}
