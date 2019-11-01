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

  public List<Evaluation> updateEvaluationStep(List<Evaluation> evaluationsForStep, Game game, String winner, int step,
      String xOrO) {

    Board previousBoard = game.getGame().get(step - 1);

    Optional<Evaluation> evaluationForPosition = commonStrategyService
        .boardInEvaluations(previousBoard, evaluationsForStep);

    Move lastMove = identifyLastMove(game, step);

    if (evaluationForPosition.isPresent()) {

      Move transformedMove = returnTransformed(lastMove, previousBoard, evaluationForPosition.get().getBoard());
      Evaluation updatedEvaluation  = updateEvaluation(transformedMove, evaluationForPosition.get(), winner, xOrO);
      evaluationsForStep.remove(evaluationForPosition);
      evaluationsForStep.add(updatedEvaluation);

    } else {

      Evaluation newEvaluation = createEvaluation(lastMove, previousBoard, winner,
          xOrO);
      evaluationsForStep.add(newEvaluation);
    }

    return evaluationsForStep;
  }

  private Evaluation updateEvaluation(Move move, Evaluation evaluation, String winner, String xOrO) {

    double previousEvaluationValue = evaluation.getEvaluation()[move.getRow()][move.getColumn()].getEvaluationValue();
    int previousGamesPlayedOn = evaluation.getEvaluation()[move.getRow()][move.getColumn()].getGamesPlayedOn();

    if ((winner.equals("X") && xOrO.equals("O")) || (winner.equals("O") && xOrO.equals("X"))) {
      evaluation.getEvaluation()[move.getRow()][move.getColumn()].updateField(previousGamesPlayedOn + 1,
          (previousEvaluationValue * previousGamesPlayedOn - 1) / (previousGamesPlayedOn + 1));

    } else if ((winner.equals("X") && xOrO.equals("X")) || (winner.equals("O") && xOrO.equals("O"))) {
      evaluation.getEvaluation()[move.getRow()][move.getColumn()].updateField(previousGamesPlayedOn + 1,
          (previousEvaluationValue * previousGamesPlayedOn + 1) / (previousGamesPlayedOn + 1));
    } else {
      // draw
      double drawValue = xOrO.equals("X") ? -0.1 : 0.1;

      evaluation.getEvaluation()[move.getRow()][move.getColumn()].updateField(previousGamesPlayedOn + 1,
          (previousEvaluationValue * previousGamesPlayedOn + drawValue) / (previousGamesPlayedOn + 1));
    }

    return evaluation;
  }

  public Evaluation createEvaluation(Move lastMove, Board previousBoard, String winner, String xOrO) {

    Evaluation resultEvaluation = createFirstEvaluation(lastMove, previousBoard, winner, xOrO);
    Move moveToChange = lastMove;

    double drawValue = xOrO.equals("X") ? -0.1 : 0.1;
    double updateValue = xOrO.equals(winner) ? 1 : -1;
    if (winner.equals("D")) updateValue = drawValue;


    if (previousBoard.equals(previousBoard.rotate())) {
      moveToChange = lastMove.rotate();
      resultEvaluation.getEvaluation()[moveToChange.getRow()][moveToChange.getColumn()].updateField(1, updateValue);
      moveToChange = lastMove.rotate().rotate();
      resultEvaluation.getEvaluation()[moveToChange.getRow()][moveToChange.getColumn()].updateField(1, updateValue);
      moveToChange = lastMove.rotate().rotate().rotate();
      resultEvaluation.getEvaluation()[moveToChange.getRow()][moveToChange.getColumn()].updateField(1, updateValue);
    }
    else  {
      if (previousBoard.equals(previousBoard.reflectHorizontal())) {
        moveToChange = lastMove.reflectHorizontal();
      }
      if (previousBoard.equals(previousBoard.reflectVertical())) {
        moveToChange = lastMove.reflectVertical();
      }
      if (previousBoard.equals(previousBoard.reflectDiagonalMain())) {
        moveToChange = lastMove.reflectDiagonalMain();
      }
      if (previousBoard.equals(previousBoard.reflectDiagonalSecond())) {
        moveToChange = lastMove.reflectDiagonalSecond();
      }
      resultEvaluation.getEvaluation()[moveToChange.getRow()][moveToChange.getColumn()].updateField(1, updateValue);
    }

    return resultEvaluation;
  }

  private Evaluation createFirstEvaluation(Move move, Board previousBoard, String winner, String xOrO) {

    Evaluation evaluationCreated = new Evaluation();
    String mark;
    double drawValue = xOrO.equals("X") ? -0.1 : 0.1;

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        mark = previousBoard.getBoard()[i][j];
        evaluationCreated.getEvaluation()[i][j].setMark(mark);
        if (mark.equals("X") || mark.equals("O"))
          evaluationCreated.getEvaluation()[i][j].setEvaluationValue(-2);
      }
    }
    evaluationCreated.getEvaluation()[move.getRow()][move.getColumn()].setEvaluationValue(winner.equals(xOrO) ? 1 : -1);
    if (winner.equals("D")) {
      evaluationCreated.getEvaluation()[move.getRow()][move.getColumn()].setEvaluationValue(drawValue);
    }
    evaluationCreated.getEvaluation()[move.getRow()][move.getColumn()].setGamesPlayedOn(1);

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

    //todo make also for X
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
  
  private Move returnTransformed(Move move, Board board, Board transformedBoard) {
    Move transformedMove = move;
    Board boardToCheck  = board;
    if  (transformedBoard.equals(boardToCheck.reflectHorizontal())) transformedMove = move.reflectHorizontal();
    if  (transformedBoard.equals(boardToCheck.reflectVertical())) transformedMove = move.reflectVertical();
    boardToCheck = board.rotate();
    if ((transformedBoard.equals(boardToCheck))) transformedMove = move.rotate();
    if (transformedBoard.equals(boardToCheck.reflectHorizontal())) transformedMove = move.rotate().reflectHorizontal();
    if (transformedBoard.equals(boardToCheck.reflectVertical())) transformedMove = move.rotate().reflectVertical();
    boardToCheck = board.rotate().rotate();
    if ((transformedBoard.equals(boardToCheck))) transformedMove = move.rotate().rotate();
    if (transformedBoard.equals(boardToCheck.reflectHorizontal())) transformedMove = move.rotate().rotate().reflectHorizontal();
    if (transformedBoard.equals(boardToCheck.reflectVertical())) transformedMove = move.rotate().rotate().reflectVertical();
    boardToCheck = board.rotate().rotate().rotate();
    if ((transformedBoard.equals(boardToCheck))) transformedMove = move.rotate().rotate().rotate();
    if (transformedBoard.equals(boardToCheck.reflectHorizontal())) transformedMove = move.rotate().rotate().rotate().reflectHorizontal();
    if (transformedBoard.equals(boardToCheck.reflectVertical())) transformedMove = move.rotate().rotate().rotate().reflectVertical();

    return transformedMove;
  }

}
