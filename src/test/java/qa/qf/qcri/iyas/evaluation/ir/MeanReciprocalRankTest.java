package qa.qf.qcri.iyas.evaluation.ir;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class MeanReciprocalRankTest extends IrAbstractTest{

private static final double expected = (1.0/1+1.0/2+1.0/3) /3;

  private static List<Map<String, Double>> predictionsMaps;
  private static List<List<String>> predictionsLists;
  private static List<Map<String, Boolean>> golds;
  private static Map<String, Boolean> allGolds;
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    predictionsMaps = new ArrayList<Map<String, Double>>();
 
    predictionsMaps.add(new HashMap<String, Double>());
    predictionsMaps.get(0).put("Q268_R4", 0.25);
    predictionsMaps.get(0).put("Q268_R5", 0.2);
    predictionsMaps.get(0).put("Q268_R10", 0.1);
    predictionsMaps.get(0).put("Q268_R13", 0.0769230769230769);
    predictionsMaps.get(0).put("Q268_R14", 0.0714285714285714);
    predictionsMaps.get(0).put("Q268_R16", 0.0625);
    predictionsMaps.get(0).put("Q268_R19", 0.0526315789473684);
    predictionsMaps.get(0).put("Q268_R27", 0.037037037037037);
    predictionsMaps.get(0).put("Q268_R29", 0.0344827586206897);
    predictionsMaps.get(0).put("Q268_R31", 0.032258064516129);

    predictionsMaps.add(new HashMap<String, Double>());
    predictionsMaps.get(1).put("Q269_R3", 0.333333333333333);
    predictionsMaps.get(1).put("Q269_R7", 0.142857142857143);
    predictionsMaps.get(1).put("Q269_R10", 0.1);
    predictionsMaps.get(1).put("Q269_R20", 0.05);
    predictionsMaps.get(1).put("Q269_R26", 0.0384615384615385);
    predictionsMaps.get(1).put("Q269_R27", 0.037037037037037);
    predictionsMaps.get(1).put("Q269_R29", 0.0344827586206897);
    predictionsMaps.get(1).put("Q269_R38", 0.0263157894736842);
    predictionsMaps.get(1).put("Q269_R40", 0.025);
    predictionsMaps.get(1).put("Q269_R43", 0.0232558139534884);
    
    predictionsMaps.add(new HashMap<String, Double>());
    predictionsMaps.get(2).put("Q270_R37", 0.027027027027027);
    predictionsMaps.get(2).put("Q270_R47", 0.0212765957446809);
    predictionsMaps.get(2).put("Q270_R51", 0.0196078431372549);
    predictionsMaps.get(2).put("Q270_R54", 0.0185185185185185);
    predictionsMaps.get(2).put("Q270_R58", 0.0172413793103448);
    predictionsMaps.get(2).put("Q270_R62", 0.0161290322580645);
    predictionsMaps.get(2).put("Q270_R64", 0.015625);
    predictionsMaps.get(2).put("Q270_R79", 0.0126582278481013);
    predictionsMaps.get(2).put("Q270_R84", 0.0119047619047619);
    predictionsMaps.get(2).put("Q270_R96", 0.0104166666666667);

    predictionsLists = new ArrayList<List<String>>();
    for (int i=0; i<predictionsMaps.size(); i++) {
      predictionsLists.add(
          AveP.getKeysSortedByValue(predictionsMaps.get(i), AveP.DESCENDING));
    }
    
    golds = new ArrayList<Map<String, Boolean>>();
    
    golds.add(new HashMap<String, Boolean>());
    golds.get(0).put("Q268_R4", true);
    golds.get(0).put("Q268_R5", true);
    golds.get(0).put("Q268_R10", true);
    golds.get(0).put("Q268_R13", true);
    golds.get(0).put("Q268_R14", true);
    golds.get(0).put("Q268_R16", true);
    golds.get(0).put("Q268_R19", true);
    golds.get(0).put("Q268_R27", false);
    golds.get(0).put("Q268_R29", true);
    golds.get(0).put("Q268_R31", true);
        
    golds.add(new HashMap<String, Boolean>());
    golds.get(1).put("Q269_R3", false);
    golds.get(1).put("Q269_R7", true);
    golds.get(1).put("Q269_R10", false);
    golds.get(1).put("Q269_R20", false);
    golds.get(1).put("Q269_R26", true);
    golds.get(1).put("Q269_R27", true);
    golds.get(1).put("Q269_R29", false);
    golds.get(1).put("Q269_R38", false);
    golds.get(1).put("Q269_R40", false);
    golds.get(1).put("Q269_R43", false);
    
    golds.add(new HashMap<String, Boolean>());
    golds.get(2).put("Q270_R37", false);
    golds.get(2).put("Q270_R47", false);
    golds.get(2).put("Q270_R51", true);
    golds.get(2).put("Q270_R54", true);
    golds.get(2).put("Q270_R58", true);
    golds.get(2).put("Q270_R62", true);
    golds.get(2).put("Q270_R64", true);
    golds.get(2).put("Q270_R79", true);
    golds.get(2).put("Q270_R84", true);
    golds.get(2).put("Q270_R96", true);    
    
    allGolds = new HashMap<String, Boolean>();
    for (int i=0; i<golds.size(); i++) {
      allGolds.putAll(golds.get(i));
    }
    
  }

//
//  @AfterClass
//  public static void tearDownAfterClass() throws Exception {
//  }

  @Test
  public void testGetReciprocalRank() {
    Map<String, Double> predict = new HashMap<String, Double>();

    predict = new HashMap<String, Double>();
    predict.put("Q268_R4", 0.25);
    predict.put("Q268_R5", 0.2);
    predict.put("Q268_R10", 0.1);
    predict.put("Q268_R13", 0.0769230769230769);
    predict.put("Q268_R14", 0.0714285714285714);

    Map<String, Boolean> goldRelevance = new HashMap<String, Boolean>();
    goldRelevance.put("Q268_R4", true);
    goldRelevance.put("Q268_R5", true);
    goldRelevance.put("Q268_R10", true);
    goldRelevance.put("Q268_R13", true);
    goldRelevance.put("Q268_R14", true);
    
    //The 1st-ranked is right. RR should be 1
    double expectedReciprocalRank = 1/1;
    double predictedReciprocalRank = 
        MeanReciprocalRank.getReciprocalRank(predict, goldRelevance);
//    System.out.println(expectedReciprocalRank + " " + predictedReciprocalRank);
    assertEquals(expectedReciprocalRank, predictedReciprocalRank, delta);
    
    //The 1st- and 2nd-ranked are wrong. It should be 1/3
    expectedReciprocalRank =1.0/3;
    goldRelevance.put("Q268_R4", false);
    goldRelevance.put("Q268_R5", false);
    predictedReciprocalRank = 
        MeanReciprocalRank.getReciprocalRank(predict, goldRelevance);
//    System.out.println(expectedReciprocalRank + " " + predictedReciprocalRank);
    assertEquals(expectedReciprocalRank, predictedReciprocalRank, delta);
  }

  @Test
  public void testComputeReciprocalRanks() {
    double[] actual = MeanReciprocalRank.computeReciprocalRanks(
        predictionsLists, golds);
//    System.out.println(actual);
    double[] expecteds = {1.0/1, 1.0/2, 1.0/3};
    Assert.assertArrayEquals(expecteds, actual, delta);
  }

  @Test
  public void testComputeWithRankingList() {
    double actual = MeanReciprocalRank.computeWithRankingList(
        predictionsLists, golds);
//    System.out.println(actual);
    Assert.assertEquals(expected, actual, delta);
  }
  
  @Test
  public void testComputeWithMapRankings() {
    double actual = MeanReciprocalRank.computeWithMapRankings(predictionsMaps, allGolds);
  Assert.assertEquals(expected, actual, delta);
  }

//@Test
//public void testComputeWithMapRankings() {
//  
//}
  
  @Test
  public void testComputeWithListRankings() {
    double actual = MeanReciprocalRank.computeWithListRankings(
        predictionsLists, allGolds);
    Assert.assertEquals(expected, actual, 0.0001);  

  }

  @Test
  public void testComputeWithRankingMap() {
    double actual = MeanReciprocalRank.computeWithRankingMap(predictionsMaps, golds);
  Assert.assertEquals(expected, actual, delta);
  }
}
