package service;

import data.Board;
import data.Evaluation;
import data.Move;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LearningServiceTest {

  private VisualizationService visualizationService = new VisualizationService();
  private LearningService learningService = new LearningService();

  private Board testBoard = new Board();

  @Before
  public void setup() {

    testBoard.getBoard()[0][0] = "X";
    testBoard.getBoard()[1][1] = "X";
    testBoard.getBoard()[2][2] = "O";
  }

  @Test
  public void createEvaluation() {
    Move move = new Move(0,2);
    Evaluation evaluation = learningService.createEvaluation(move, testBoard, "X", "O");
    visualizationService.drawBoard(evaluation.getBoard());
  }
}