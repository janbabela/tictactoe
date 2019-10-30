package data;

import lombok.Data;

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
}
