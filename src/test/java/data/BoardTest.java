package data;

import org.junit.Before;
import org.junit.Test;

import service.VisualizationService;

public class BoardTest {

  private VisualizationService visualizationService = new VisualizationService();
  
  private Board testBoard = new Board();
  
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
    
  }

  @Test
  public void rotate_should_rotateBoard() {

    visualizationService.drawBoard(testBoard);
    visualizationService.drawBoard(testBoard.rotate());

  }

  @Test
  public void reflectHorizontal() {

    visualizationService.drawBoard(testBoard);
    visualizationService.drawBoard(testBoard.reflectHorizontal());
    
  }

  @Test
  public void reflectVertical() {

    visualizationService.drawBoard(testBoard);
    visualizationService.drawBoard(testBoard.reflectVertical());

  }

  @Test
  public void reflectDiagonalMain() {
    visualizationService.drawBoard(testBoard);
    visualizationService.drawBoard(testBoard.reflectDiagonalMain());


  }

  @Test
  public void reflectDiagonalSecond() {

    visualizationService.drawBoard(testBoard);
    visualizationService.drawBoard(testBoard.reflectDiagonalSecond());

  }
}