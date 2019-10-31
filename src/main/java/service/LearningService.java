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

  public List<Evaluation> updateEvaluationStep(List<Evaluation> evaluationsForStep, Game game, String winner, int step, String xOrO) {

    Board previousBoard = game.getGame().get(step-1);

    Optional<Evaluation> evaluationForPosition = commonStrategyService.boardInEvaluations(previousBoard, evaluationsForStep);

    Move lastMove = identifyLastMove(game, step);

    if (evaluationForPosition.isPresent()) {

      evaluationsForStep = updateEvaluationsOriginalRotatedAndReflected(lastMove, evaluationForPosition.get(), winner, xOrO);

    }
    else {
      List<Evaluation> newEvaluations = createEvaluationsOriginalRotatedAndReflected(lastMove, previousBoard, winner, xOrO);
      evaluationsForStep.addAll(newEvaluations);
    }

    return evaluationsForStep;
  }

  private List<Evaluation> updateEvaluationsOriginalRotatedAndReflected(Move move, Evaluation previousEvaluation, String winner, String xOrO) {

    List<Evaluation> evaluationList = new ArrayList<>();

    Evaluation newEvaluation;
    newEvaluation = updateEvaluation(move, previousEvaluation, winner, xOrO);
    evaluationList.remove(previousEvaluation);
    evaluationList.add(newEvaluation);
    newEvaluation = updateEvaluation(move, reflectEvaluation(previousEvaluation), winner, xOrO);
    evaluationList.remove(reflectEvaluation(previousEvaluation));
    evaluationList.add(newEvaluation);
    newEvaluation = updateEvaluation(move, rotateEvaluation(previousEvaluation), winner, xOrO);
    evaluationList.remove(rotateEvaluation(previousEvaluation));
    evaluationList.add(newEvaluation);
    newEvaluation = updateEvaluation(move, reflectEvaluation(rotateEvaluation(previousEvaluation)), winner, xOrO);
    evaluationList.remove(reflectEvaluation(rotateEvaluation(previousEvaluation)));
    evaluationList.add(newEvaluation);
    newEvaluation = updateEvaluation(move, rotateEvaluation(rotateEvaluation(previousEvaluation)), winner, xOrO);
    evaluationList.remove(rotateEvaluation(rotateEvaluation(previousEvaluation)));
    evaluationList.add(newEvaluation);
    newEvaluation = updateEvaluation(move, reflectEvaluation(rotateEvaluation(rotateEvaluation(previousEvaluation))), winner, xOrO);
    evaluationList.remove(reflectEvaluation(rotateEvaluation(rotateEvaluation(previousEvaluation))));
    evaluationList.add(newEvaluation);
    newEvaluation = updateEvaluation(move, rotateEvaluation(rotateEvaluation(rotateEvaluation(previousEvaluation))), winner, xOrO);
    evaluationList.remove(rotateEvaluation(rotateEvaluation(rotateEvaluation(previousEvaluation))));
    evaluationList.add(newEvaluation);
    newEvaluation = updateEvaluation(move, reflectEvaluation(rotateEvaluation(rotateEvaluation(rotateEvaluation(previousEvaluation)))), winner, xOrO);
    evaluationList.remove(reflectEvaluation(rotateEvaluation(rotateEvaluation(rotateEvaluation(previousEvaluation)))));
    evaluationList.add(newEvaluation);

    return evaluationList;
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

  private List<Evaluation> createEvaluationsOriginalRotatedAndReflected(Move move, Board previousBoard, String winner, String xOrO) {
    
    List<Evaluation> evaluationList = new ArrayList<>();

    Evaluation newEvaluation = createEvaluation(move, previousBoard, winner, xOrO);
    evaluationList.add(newEvaluation);
    newEvaluation = createEvaluation(move, reflectBoard(previousBoard), winner, xOrO);
    evaluationList.add(newEvaluation);
    newEvaluation = createEvaluation(move, rotateBoard(previousBoard), winner, xOrO);
    evaluationList.add(newEvaluation);
    newEvaluation = createEvaluation(move, reflectBoard(rotateBoard(previousBoard)), winner, xOrO);
    evaluationList.add(newEvaluation);
    newEvaluation = createEvaluation(move, rotateBoard(rotateBoard(previousBoard)), winner, xOrO);
    evaluationList.add(newEvaluation);
    newEvaluation = createEvaluation(move, reflectBoard(rotateBoard(rotateBoard(previousBoard))), winner, xOrO);
    evaluationList.add(newEvaluation);
    newEvaluation = createEvaluation(move, rotateBoard(rotateBoard(rotateBoard(previousBoard))), winner, xOrO);
    evaluationList.add(newEvaluation);
    newEvaluation = createEvaluation(move, reflectBoard(rotateBoard(rotateBoard(rotateBoard(previousBoard)))), winner, xOrO);
    evaluationList.add(newEvaluation);
    
    return evaluationList;
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

  private Board rotateBoard(Board originalBoard) {
    
    Board newBoard = originalBoard;
    
    newBoard.getBoard()[0][0] = originalBoard.getBoard()[2][0];
    newBoard.getBoard()[2][0] = originalBoard.getBoard()[2][2];
    newBoard.getBoard()[2][2] = originalBoard.getBoard()[0][2];
    newBoard.getBoard()[0][2] = originalBoard.getBoard()[0][0];
    
    newBoard.getBoard()[0][1] = originalBoard.getBoard()[1][0];
    newBoard.getBoard()[1][0] = originalBoard.getBoard()[2][1];
    newBoard.getBoard()[2][1] = originalBoard.getBoard()[1][2];
    newBoard.getBoard()[1][2] = originalBoard.getBoard()[0][1];    

    return newBoard;
  }

  private Board reflectBoard(Board originalBoard) {

    Board newBoard = originalBoard;

    newBoard.getBoard()[0][0] = originalBoard.getBoard()[0][2];
    newBoard.getBoard()[1][0] = originalBoard.getBoard()[1][2];
    newBoard.getBoard()[2][0] = originalBoard.getBoard()[2][2];

    newBoard.getBoard()[0][2] = originalBoard.getBoard()[0][0];
    newBoard.getBoard()[1][2] = originalBoard.getBoard()[1][0];
    newBoard.getBoard()[2][2] = originalBoard.getBoard()[2][0];

    return newBoard;
  }
  
  private Evaluation rotateEvaluation(Evaluation originalEvaluation) {

    Evaluation newEvaluation = originalEvaluation;

    newEvaluation.getEvaluation()[0][0] = originalEvaluation.getEvaluation()[2][0];
    newEvaluation.getEvaluation()[2][0] = originalEvaluation.getEvaluation()[2][2];
    newEvaluation.getEvaluation()[2][2] = originalEvaluation.getEvaluation()[0][2];
    newEvaluation.getEvaluation()[0][2] = originalEvaluation.getEvaluation()[0][0];

    newEvaluation.getEvaluation()[0][1] = originalEvaluation.getEvaluation()[1][0];
    newEvaluation.getEvaluation()[1][0] = originalEvaluation.getEvaluation()[2][1];
    newEvaluation.getEvaluation()[2][1] = originalEvaluation.getEvaluation()[1][2];
    newEvaluation.getEvaluation()[1][2] = originalEvaluation.getEvaluation()[0][1];

    return newEvaluation;
  }

  private Evaluation reflectEvaluation(Evaluation originalEvaluation) {

    Evaluation newEvaluation = originalEvaluation;

    newEvaluation.getEvaluation()[0][0] = originalEvaluation.getEvaluation()[0][2];
    newEvaluation.getEvaluation()[1][0] = originalEvaluation.getEvaluation()[1][2];
    newEvaluation.getEvaluation()[2][0] = originalEvaluation.getEvaluation()[2][2];

    newEvaluation.getEvaluation()[0][2] = originalEvaluation.getEvaluation()[0][0];
    newEvaluation.getEvaluation()[1][2] = originalEvaluation.getEvaluation()[1][0];
    newEvaluation.getEvaluation()[2][2] = originalEvaluation.getEvaluation()[2][0];

    return newEvaluation;
  }
  
}
