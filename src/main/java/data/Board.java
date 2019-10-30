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

}
