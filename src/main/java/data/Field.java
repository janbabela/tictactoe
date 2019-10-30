package data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Field {

  public double evaluationValue;

  public int gamesPlayedOn;

  public String mark;

  public Field(String xOrO) {
    mark = "_";
    gamesPlayedOn = 0;
    if (xOrO.equals("X")) evaluationValue = -0.1;
    if (xOrO.equals("O")) evaluationValue = 0.1;
  }

  public Field() {
    mark = "_";
    gamesPlayedOn = 0;
    evaluationValue = 0;
  }

}
