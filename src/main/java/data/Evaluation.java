package data;

import lombok.Data;

import java.util.Optional;

@Data
public class Evaluation {

  public Field[][] evaluation = new Field[3][3];

  public Evaluation(String xOrO) {

      for (int i = 0; i< 3; i++) {
        for (int j = 0; j<3; j++) {
          evaluation[i][j] = new Field(xOrO);
        }
      }
  }

  public Evaluation() {

    for (int i = 0; i< 3; i++) {
      for (int j = 0; j<3; j++) {
        evaluation[i][j] = new Field();
      }
    }
  }

  public boolean hasEqualBoards(Evaluation evaluation) {

    boolean result = true;

    for (int i = 0; i< 3; i++) {
      for (int j = 0; j<3; j++) {
        if (!getEvaluation()[i][j].getMark().equals(evaluation.getEvaluation()[i][j].getMark())) result = false;
      }
    }
    return result;
  }

  public Board getBoard() {
    Board resultBoard = new Board();
    for (int i = 0; i<3; i++) {
      for (int j=0; j<3; j++) {
        resultBoard.getBoard()[i][j] = getEvaluation()[i][j].getMark();
      }
     }
    return resultBoard;
  }

  public void setBoard(Board board) {

    for (int i = 0; i<3; i++) {
      for (int j=0; j<3; j++) {
        getEvaluation()[i][j].setMark(board.getBoard()[i][j]);
      }
    }
  }

  public Optional<Evaluation> returnTransformed(Board board) {
    
    Optional<Evaluation> result = Optional.empty();
    
    Board evaluationBoard = this.getBoard();
    
    if (evaluationBoard.equals(board)) result = Optional.of(this);
    if (evaluationBoard.reflectVertical().equals(board)) result = Optional.of(this.reflectVertical());
    if (evaluationBoard.reflectHorizontal().equals(board)) result = Optional.of(this.reflectHorizontal());
    
    if (evaluationBoard.rotate().equals(board)) result = Optional.of(this.rotate());
    if (evaluationBoard.rotate().reflectVertical().equals(board)) result = Optional.of(this.rotate().reflectVertical());
    if (evaluationBoard.rotate().reflectHorizontal().equals(board)) result = Optional.of(this.rotate().reflectHorizontal());

    if (evaluationBoard.rotate().rotate().equals(board)) result = Optional.of(this.rotate().rotate());
    if (evaluationBoard.rotate().rotate().reflectVertical().equals(board)) result = Optional.of(this.rotate().rotate().reflectVertical());
    if (evaluationBoard.rotate().rotate().reflectHorizontal().equals(board)) result = Optional.of(this.rotate().rotate().reflectHorizontal());

    if (evaluationBoard.rotate().rotate().rotate().equals(board)) result = Optional.of(this.rotate().rotate().rotate());
    if (evaluationBoard.rotate().rotate().rotate().reflectVertical().equals(board)) result = Optional.of(this.rotate().rotate().rotate().reflectVertical());
    if (evaluationBoard.rotate().rotate().rotate().reflectHorizontal().equals(board)) result = Optional.of(this.rotate().rotate().rotate().reflectHorizontal());
    
    return result;
  }
  
  
  public Evaluation rotate() {

    Evaluation newEvaluation = new Evaluation();

    newEvaluation.getEvaluation()[0][0].setField(getEvaluation()[2][0]);
    newEvaluation.getEvaluation()[2][0].setField(getEvaluation()[2][2]);
    newEvaluation.getEvaluation()[2][2].setField(getEvaluation()[0][2]);
    newEvaluation.getEvaluation()[0][2].setField(getEvaluation()[0][0]);

    newEvaluation.getEvaluation()[0][1].setField(getEvaluation()[1][0]);
    newEvaluation.getEvaluation()[1][0].setField(getEvaluation()[2][1]);
    newEvaluation.getEvaluation()[2][1].setField(getEvaluation()[1][2]);
    newEvaluation.getEvaluation()[1][2].setField(getEvaluation()[0][1]);

    newEvaluation.getEvaluation()[1][1].setField(getEvaluation()[1][1]);

    return newEvaluation;
  }

  public Evaluation reflectVertical() {

    Evaluation newEvaluation = new Evaluation();

    newEvaluation.getEvaluation()[0][0].setField(getEvaluation()[0][2]);
    newEvaluation.getEvaluation()[1][0].setField(getEvaluation()[1][2]);
    newEvaluation.getEvaluation()[2][0].setField(getEvaluation()[2][2]);

    newEvaluation.getEvaluation()[0][2].setField(getEvaluation()[0][0]);
    newEvaluation.getEvaluation()[1][2].setField(getEvaluation()[1][0]);
    newEvaluation.getEvaluation()[2][2].setField(getEvaluation()[2][0]);

    newEvaluation.getEvaluation()[0][1].setField(getEvaluation()[0][1]);
    newEvaluation.getEvaluation()[1][1].setField(getEvaluation()[1][1]);
    newEvaluation.getEvaluation()[2][1].setField(getEvaluation()[2][1]);

    return newEvaluation;
  }

  public Evaluation reflectHorizontal() {

    Evaluation newEvaluation = new Evaluation();

    newEvaluation.getEvaluation()[0][0].setField(getEvaluation()[2][0]);
    newEvaluation.getEvaluation()[0][1].setField(getEvaluation()[2][1]);
    newEvaluation.getEvaluation()[0][2].setField(getEvaluation()[2][2]);

    newEvaluation.getEvaluation()[2][0].setField(getEvaluation()[0][0]);
    newEvaluation.getEvaluation()[2][1].setField(getEvaluation()[0][1]);
    newEvaluation.getEvaluation()[2][2].setField(getEvaluation()[0][2]);

    newEvaluation.getEvaluation()[1][0].setField(getEvaluation()[1][0]);
    newEvaluation.getEvaluation()[1][1].setField(getEvaluation()[1][1]);
    newEvaluation.getEvaluation()[1][2].setField(getEvaluation()[1][2]);

    return newEvaluation;
  }

}
