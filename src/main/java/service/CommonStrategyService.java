package service;

import data.Board;
import data.Evaluation;
import data.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CommonStrategyService {

  Random rand = new Random();

  public Optional<Evaluation> boardInEvaluations(Board board, List<Evaluation> evaluationsForStep) {

    Optional<Evaluation> result = Optional.empty();

    for (Evaluation evaluation : evaluationsForStep) {
      if (evaluation.getBoard().equalsTransformed(board)) {
        result = evaluation.returnTransformed(board);
      }

    }
    return result;
  }

  public Move makeBestMove(Evaluation evaluationForPosition) {

    List<Move> bestMoves = new ArrayList<>();

    double bestValue = -1;

    for (int i = 0; i<3; i++) {
      for (int j = 0; j<3; j++) {
        double newValue = evaluationForPosition.getEvaluation()[i][j].getEvaluationValue();
        if (newValue > bestValue) {
          bestMoves = new ArrayList<>();
          bestMoves.add(new Move(i ,j ));
          bestValue = newValue;
        }
        else if (newValue == bestValue) {
          bestMoves.add(new Move(i ,j ));
        }
      }
    }

    int randomBestMove = rand.nextInt(bestMoves.size());
    return bestMoves.get(randomBestMove);
  }

}
