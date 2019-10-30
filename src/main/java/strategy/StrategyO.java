package strategy;

import data.Board;
import data.Evaluation;
import data.Game;
import data.Move;
import service.BoardService;
import service.CommonStrategyService;
import service.LearningService;
import service.MoveService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StrategyO {

  MoveService moveService = new MoveService();
  BoardService boardService = new BoardService();
  CommonStrategyService commonStrategyService = new CommonStrategyService();
  LearningService learningService = new LearningService();

  private Map<Integer, List<Evaluation>> evaluations = new HashMap<>();

  public StrategyO() {
    Evaluation testEvaluation2 = new Evaluation();
    testEvaluation2.getEvaluation()[0][1].setEvaluationValue(-1);
    testEvaluation2.getEvaluation()[0][1].setGamesPlayedOn(1);
    testEvaluation2.getEvaluation()[1][0].setEvaluationValue(-1);
    testEvaluation2.getEvaluation()[1][0].setGamesPlayedOn(1);
    testEvaluation2.getEvaluation()[2][1].setEvaluationValue(-1);
    testEvaluation2.getEvaluation()[2][1].setGamesPlayedOn(1);
    testEvaluation2.getEvaluation()[1][2].setEvaluationValue(-1);
    testEvaluation2.getEvaluation()[1][2].setGamesPlayedOn(1);
    testEvaluation2.getEvaluation()[1][1].setEvaluationValue(-2);
    testEvaluation2.getEvaluation()[1][1].setMark("X");
    testEvaluation2.getEvaluation()[1][1].setGamesPlayedOn(1);
    List<Evaluation> testEvaluationForStep2 = new ArrayList<>();
    testEvaluationForStep2.add(testEvaluation2);
    evaluations.put(2, testEvaluationForStep2);
    evaluations.put(4, new ArrayList<>());
    evaluations.put(6, new ArrayList<>());
    evaluations.put(8, new ArrayList<>());
  }

  public Game doMove(Game game) {
    Move nextMove;
    Board lastBoard = game.getLastBoard();
    Optional<Move> winning = boardService.checkWinningLoosing(lastBoard, "O", false);
    if (winning.isPresent()) {
      nextMove = winning.get();
    } else {
      Optional<Move> notLoosing = boardService.checkWinningLoosing(lastBoard, "X", false);
      if (notLoosing.isPresent()) {
        nextMove = notLoosing.get();
      } else {
        nextMove = makeStrategicMove(lastBoard, game.getGame().size());
      }
    }
    return moveService.addMove(nextMove.getRow(), nextMove.getColumn(), "O", game);
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

  public void updateEvaluations(Game game, String winner) {

    int countMoves = game.getGame().size();

    List<Evaluation> evaluationsForStep2 = evaluations.get(2);
    evaluationsForStep2 = learningService.updateEvaluationStep(evaluationsForStep2, game, winner, 2);
    evaluations.put(2, evaluationsForStep2);

    List<Evaluation> evaluationsForStep4 = evaluations.get(4);
    evaluationsForStep4 = learningService.updateEvaluationStep(evaluationsForStep4, game, winner, 4);
    evaluations.put(4, evaluationsForStep4);

    if (countMoves >= 6) {
      List<Evaluation> evaluationsForStep6 = evaluations.get(6);
      evaluationsForStep6 = learningService.updateEvaluationStep(evaluationsForStep6, game, winner, 6);
      evaluations.put(6, evaluationsForStep6);
    }
    if (countMoves >= 8) {
      List<Evaluation> evaluationsForStep8 = evaluations.get(8);
      evaluationsForStep8 = learningService.updateEvaluationStep(evaluationsForStep8, game, winner, 8);
      evaluations.put(8, evaluationsForStep8);
    }
  }



}
