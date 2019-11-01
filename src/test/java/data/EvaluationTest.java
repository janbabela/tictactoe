package data;

import org.junit.Before;
import org.junit.Test;
import service.VisualizationService;

import static org.junit.Assert.*;

public class EvaluationTest {

  private VisualizationService visualizationService = new VisualizationService();

  private Board testBoard = new Board();
  private Evaluation testEvaluation = new Evaluation();

  @Before
  public void setup() {

    testBoard.getBoard()[0][0] = "X";
    testBoard.getBoard()[0][1] = "O";
    testBoard.getBoard()[1][2] = "X";
    testBoard.getBoard()[2][1] = "O";
    testBoard.getBoard()[0][2] = "O";
    testBoard.getBoard()[2][2] = "O";
    testBoard.getBoard()[1][0] = "X";
    testBoard.getBoard()[2][0] = "O";

    testEvaluation.setBoard(testBoard);
  }


  @Test
  public void rotate() {
    visualizationService.drawBoard(testEvaluation.getBoard());
    visualizationService.drawBoard(testEvaluation.rotate().getBoard());
  }

  @Test
  public void reflectVertical() {
    visualizationService.drawBoard(testEvaluation.getBoard());
    visualizationService.drawBoard(testEvaluation.reflectVertical().getBoard());
  }

  @Test
  public void reflectHorizontall() {
    visualizationService.drawBoard(testEvaluation.getBoard());
    visualizationService.drawBoard(testEvaluation.reflectHorizontal().getBoard());
  }

}