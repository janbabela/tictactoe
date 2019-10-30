package service;

import data.Board;
import data.Evaluation;
import data.Game;
import data.Move;

import java.util.ArrayList;
import java.util.List;

public class LearningService {

  public List<Evaluation> updateEvaluationStep(List<Evaluation> evaluationsForStep, Game game, String winner, int step) {
//    List<Evaluation> previousEvaluationsForPostion = evaluations.get(step);
//    Move move = identifyLastMove(game, step);
//    Evaluation updatedEvaluation = updateEvaluation(move, previousEvaluation, winner, step);
//    evaluations.put(step, updatedEvaluation);
    return new ArrayList<>();
  }

  private Evaluation updateEvaluation(Move move, Evaluation evaluation, String winner, int count) {

    if (winner.equals("X")) {
      double previousEvaluationValue = evaluation.getEvaluation()[move.getRow()][move.getColumn()].getEvaluationValue();
      int previousGamesPlayedOn = evaluation.getEvaluation()[move.getRow()][move.getColumn()].getGamesPlayedOn();
      evaluation.getEvaluation()[move.getRow()][move.getColumn()].setGamesPlayedOn(previousGamesPlayedOn + 1);
      evaluation.getEvaluation()[move.getRow()][move.getColumn()]
          .setEvaluationValue((previousEvaluationValue * previousGamesPlayedOn - 1) / (previousGamesPlayedOn + 1));

    } else if (winner.equals("O")) {
      double previousEvaluationValue = evaluation.getEvaluation()[move.getRow()][move.getColumn()].getEvaluationValue();
      int previousGamesPlayedOn = evaluation.getEvaluation()[move.getRow()][move.getColumn()].getGamesPlayedOn();
      evaluation.getEvaluation()[move.getRow()][move.getColumn()].setGamesPlayedOn(previousGamesPlayedOn + 1);
      evaluation.getEvaluation()[move.getRow()][move.getColumn()]
          .setEvaluationValue((previousEvaluationValue * previousGamesPlayedOn + 1) / (previousGamesPlayedOn + 1));
    } else {
      // draw
      double previousEvaluationValue = evaluation.getEvaluation()[move.getRow()][move.getColumn()].getEvaluationValue();
      int previousGamesPlayedOn = evaluation.getEvaluation()[move.getRow()][move.getColumn()].getGamesPlayedOn();
      evaluation.getEvaluation()[move.getRow()][move.getColumn()].setGamesPlayedOn(previousGamesPlayedOn + 1);
      evaluation.getEvaluation()[move.getRow()][move.getColumn()]
          .setEvaluationValue((previousEvaluationValue * previousGamesPlayedOn + 0.1) / (previousGamesPlayedOn + 1));
    }

    return evaluation;
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
