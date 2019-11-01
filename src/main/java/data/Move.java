package data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Move {

  int row;

  int column;

  public Move rotate() {

    int newRow = 0;
    int newColumn = 0;

    if (row == 0) {
      newRow = column;
      newColumn = 2;
    }
    if (row == 1) {
      newRow = column;
      newColumn = 1;
    }
    if (row == 2) {
      newRow = column;
      newColumn = 0;
    }

    return new Move(newRow, newColumn);
  }

  public Move reflectVertical() {

    return new Move(row, 2-column);
  }

  public Move reflectHorizontal() {

    return new Move(2 - row, column);
  }

  public Move reflectDiagonalMain() {

    return new Move(column, row);
  }

  public Move reflectDiagonalSecond() {

    return new Move(2 - column, 2 - row);
  }

}
