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

  public Field(Field field) {
    mark = field.getMark();
    gamesPlayedOn = field.getGamesPlayedOn();
    evaluationValue = field.getEvaluationValue();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Field)) {
      return false;
    }
    Field that = (Field) o;
    boolean result = true;
    if (!(getMark().equals(that.getMark()) && getEvaluationValue() == that.getEvaluationValue() && getGamesPlayedOn() == that.getGamesPlayedOn())) result = false;
    return result;
  }

  public void updateField(int newGamesPlayedOn, double newEvaluationValue) {
    gamesPlayedOn = newGamesPlayedOn;
    evaluationValue = newEvaluationValue;
  }

  public void setField(Field field) {
    mark = field.getMark();
    gamesPlayedOn = field.getGamesPlayedOn();
    evaluationValue = field.getEvaluationValue();
  }

}
