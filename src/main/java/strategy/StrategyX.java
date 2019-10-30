package strategy;

import data.Board;
import data.Evaluation;
import data.Game;
import data.Move;
import service.BoardService;
import service.CommonStrategyService;
import service.MoveService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StrategyX {

  MoveService moveService = new MoveService();
  BoardService boardService = new BoardService();
  CommonStrategyService commonStrategyService = new CommonStrategyService();

  private Map<Integer, List<Evaluation>> evaluations = new HashMap<>();

  public StrategyX() {
    evaluations.put(1, new ArrayList<>());
    evaluations.put(3, new ArrayList<>());
    evaluations.put(5, new ArrayList<>());
    evaluations.put(5, new ArrayList<>());
    evaluations.put(9, new ArrayList<>());
  }

  public Game doMove(Game game) {
    Move nextMove;
    Board lastBoard = game.getLastBoard();
    Optional<Move> winning = boardService.checkWinningLoosing(lastBoard, "X", false);
    if (winning.isPresent()) {
      nextMove = winning.get();
    }
    else {
      Optional<Move> notLoosing = boardService.checkWinningLoosing(lastBoard, "O", false);
      if (notLoosing.isPresent()) {
        nextMove = notLoosing.get();
      }
      else {
        nextMove = makeStrategicMove(lastBoard, game.getGame().size());
      }
    }
    return moveService.addMove(nextMove.getRow(), nextMove.getColumn(), "X", game);
  }

  private Move makeStrategicMove(Board board, int step) {

    List<Evaluation> evaluationsForStep = evaluations.get(step);

    Optional<Evaluation> evaluationForPosition = commonStrategyService.boardInEvaluations(board, evaluationsForStep);

    if (evaluationForPosition.isPresent()) {
      return commonStrategyService.makeBestMove(evaluationForPosition.get());
    } else {
      return boardService.makeRandomMove(board);
    }
  }

}
