package lsck.attack;

import java.util.Arrays;

import lsck.bitwise.BitVector;

/**
 * Represents a group of fills associated with the value of a test statistic.
 */
public class ScoredFills {
  
  private final double score;
  private final BitVector[] fills;
  
  public ScoredFills(double score, BitVector[] fills) {
    this.score = score;
    this.fills = Arrays.copyOf(fills, fills.length);
  }
  
  public double getScore() {
    return score;
  }
  
  public BitVector getFill(int index) {
    return fills[index];
  }
  
  public int getFillCount() {
    return fills.length;
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    
    builder.append(score);
    builder.append(':');
    
    for (int i = 0; i < fills.length; i++) {
      builder.append(' ');
      builder.append(fills[i]);
    }
    
    return builder.toString();
  }
}
