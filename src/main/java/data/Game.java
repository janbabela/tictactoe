package data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Game {

  public List<Board> game = new ArrayList<Board>();

  public Game() {
    Board board_initial = new Board();
    game.add(board_initial);
  }

  public Board getLastBoard() {
    return getGame().get(getGame().size()-1);
  }
}
