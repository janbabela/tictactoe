package data;

import lombok.Data;

@Data
public class Board {

  public String[][] board = new String[3][3];

  public Board() {
    for (int i=0; i<3; i++) {
      for (int j=0; j<3; j++) {
        board[i][j] = "_";
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Board)) {
      return false;
    }
    Board that = (Board) o;
    boolean result = true;
    for (int i =0; i<3; i++) {
      for (int j =0; j<3; j++) {
        if (!(this.getBoard()[i][j].equals(that.getBoard()[i][j]))) {
          result = false;

        }
      }
    }
    return result;
  }

  public boolean equalsTransformed(Board board) {

    boolean result = false;

    Board boardToCheck  = board;
    if ((this.equals(boardToCheck)) || this.equals(boardToCheck.reflectHorizontal()) || this.equals(boardToCheck.reflectVertical())) result = true;
    boardToCheck = board.rotate();
    if ((this.equals(boardToCheck)) || this.equals(boardToCheck.reflectHorizontal()) || this.equals(boardToCheck.reflectVertical())) result = true;
    boardToCheck = board.rotate().rotate();
    if ((this.equals(boardToCheck)) || this.equals(boardToCheck.reflectHorizontal()) || this.equals(boardToCheck.reflectVertical())) result = true;
    boardToCheck = board.rotate().rotate().rotate();
    if ((this.equals(boardToCheck)) || this.equals(boardToCheck.reflectHorizontal()) || this.equals(boardToCheck.reflectVertical())) result = true;

    return result;
  }

  public Board rotate() {
    Board newBoard = new Board();

    newBoard.getBoard()[0][0] = getBoard()[2][0];
    newBoard.getBoard()[2][0] = getBoard()[2][2];
    newBoard.getBoard()[2][2] = getBoard()[0][2];
    newBoard.getBoard()[0][2] = getBoard()[0][0];

    newBoard.getBoard()[0][1] = getBoard()[1][0];
    newBoard.getBoard()[1][0] = getBoard()[2][1];
    newBoard.getBoard()[2][1] = getBoard()[1][2];
    newBoard.getBoard()[1][2] = getBoard()[0][1];

    newBoard.getBoard()[1][1] = getBoard()[1][1];
    return newBoard;
  }

  public Board reflectHorizontal() {
    Board newBoard = new Board();

    for (int i=0; i<3; i++) {
      for (int j = 0; j<3; j++) {
        newBoard.getBoard()[i][j] = getBoard()[2-i][j];
      }
    }

    return newBoard;
  }

  public Board reflectVertical() {
    Board newBoard = new Board();

    for (int i=0; i<3; i++) {
      for (int j = 0; j<3; j++) {
        newBoard.getBoard()[j][i] = getBoard()[j][2-i];
      }
    }

    return newBoard;
  }

  public Board reflectDiagonalMain() {
    Board newBoard = new Board();

    for (int i=0; i<3; i++) {
      for (int j = 0; j<3; j++) {
        newBoard.getBoard()[j][i] = getBoard()[i][j];
      }
    }

    return newBoard;
  }

  public Board reflectDiagonalSecond() {
    Board newBoard = new Board();

    for (int i=0; i<3; i++) {
      for (int j = 0; j<3; j++) {
        newBoard.getBoard()[j][i] = getBoard()[2-i][2-j];
      }
    }

    return newBoard;
  }
}
