package service;

import data.Board;
import data.Evaluation;
import data.Game;
import data.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LearningService {

  CommonStrategyService commonStrategyService = new CommonStrategyService();

  public List<Evaluation> updateEvaluationStep(List<Evaluation> evaluationsForStep, Game game, String winner, int step) {

    Evaluation newEvaluation;

    Board previousBoard = game.getGame().get(step-1);

    Optional<Evaluation> evaluationForPosition = commonStrategyService.boardInEvaluations(previousBoard, evaluationsForStep);

    Move lastMove = identifyLastMove(game, step);

    if (evaluationForPosition.isPresent()) {

      newEvaluation = updateEvaluation(lastMove, evaluationForPosition.get(), winner, "0"); //todo make the same for X
      evaluationsForStep.remove(evaluationForPosition.get());
      evaluationsForStep.add(newEvaluation);
    }
    else {
      newEvaluation = createEvaluation(lastMove, previousBoard, winner, "O");
      evaluationsForStep.add(newEvaluation);
    }

    return evaluationsForStep;
  }

  private Evaluation updateEvaluation(Move move, Evaluation evaluation, String winner, String xOrO) {

    if ((winner.equals("X") && xOrO.equals("O")) || (winner.equals("O") && xOrO.equals("X"))) {
      double previousEvaluationValue = evaluation.getEvaluation()[move.getRow()][move.getColumn()].getEvaluationValue();
      int previousGamesPlayedOn = evaluation.getEvaluation()[move.getRow()][move.getColumn()].getGamesPlayedOn();
      evaluation.getEvaluation()[move.getRow()][move.getColumn()].setGamesPlayedOn(previousGamesPlayedOn + 1);
      evaluation.getEvaluation()[move.getRow()][move.getColumn()]
          .setEvaluationValue((previousEvaluationValue * previousGamesPlayedOn - 1) / (previousGamesPlayedOn + 1));

    } else if ((winner.equals("X") && xOrO.equals("X")) || (winner.equals("O") && xOrO.equals("O"))) {
      double previousEvaluationValue = evaluation.getEvaluation()[move.getRow()][move.getColumn()].getEvaluationValue();
      int previousGamesPlayedOn = evaluation.getEvaluation()[move.getRow()][move.getColumn()].getGamesPlayedOn();
      evaluation.getEvaluation()[move.getRow()][move.getColumn()].setGamesPlayedOn(previousGamesPlayedOn + 1);
      evaluation.getEvaluation()[move.getRow()][move.getColumn()]
          .setEvaluationValue((previousEvaluationValue * previousGamesPlayedOn + 1) / (previousGamesPlayedOn + 1));
    } else {
      // draw
      double drawValue = xOrO.equals("X") ? -0.1 : 0.1;

      double previousEvaluationValue = evaluation.getEvaluation()[move.getRow()][move.getColumn()].getEvaluationValue();
      int previousGamesPlayedOn = evaluation.getEvaluation()[move.getRow()][move.getColumn()].getGamesPlayedOn();
      evaluation.getEvaluation()[move.getRow()][move.getColumn()].setGamesPlayedOn(previousGamesPlayedOn + 1);
      evaluation.getEvaluation()[move.getRow()][move.getColumn()]
          .setEvaluationValue((previousEvaluationValue * previousGamesPlayedOn + drawValue) / (previousGamesPlayedOn + 1));
    }

    return evaluation;
  }

  private Evaluation createEvaluation(Move move, Board previousBoard, String winner, String xOrO) {

    Evaluation evaluationCreated = new Evaluation();
    String mark;
    double drawValue = xOrO.equals("X") ? -0.1 : 0.1;

    for (int i = 0; i<3; i++) {
      for (int j = 0; j<3; j++) {
        mark = previousBoard.getBoard()[i][j];
        evaluationCreated.getEvaluation()[i][j].setMark(mark);
        if (mark.equals("X") || mark.equals("O")) evaluationCreated.getEvaluation()[i][j].setEvaluationValue(-2);
      }
    }
    evaluationCreated.getEvaluation()[move.getRow()][move.getRow()].setEvaluationValue(winner.equals(xOrO) ? 1 : -1);
    if (winner.equals("D")) {
      evaluationCreated.getEvaluation()[move.getRow()][move.getRow()].setEvaluationValue(drawValue);
    }
    evaluationCreated.getEvaluation()[move.getRow()][move.getRow()].setGamesPlayedOn(1);

    return evaluationCreated;
  }


  private Move identifyLastMove(Game game, int count) {

    if (count == 2) {
      return findFirstMove(game.getGame().get(2));
    } else {
      return findLastMove(game.getGame().get(count - 2), game.getGame().get(count));
    }
  }

  private Move findFirstMove(Board board) {

    Move move = new Move(2, 2);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board.getBoard()[i][j].equals("O")) {
          move = new Move(i, j);
        }
      }
    }
    return move;
  }

  private Move findLastMove(Board previousBoard, Board lastBoard) {

    Move move = new Move(2, 2);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (lastBoard.getBoard()[i][j].equals("O") && previousBoard.getBoard()[i][j].equals("_")) {
          move = new Move(i, j);
        }
      }
    }
    return move;
  }

}
