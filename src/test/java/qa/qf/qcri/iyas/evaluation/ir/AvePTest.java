package qa.qf.qcri.iyas.evaluation.ir;
 

import static org.hamcrest.CoreMatchers.is;

// import static org.junit.Assert.*;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author albarron
 * Qatar Computing Research Institute, 2016
 */
public class AvePTest extends IrAbstractTest{


  /**
   * Test the average precision computation in which no change in recall is 
   * considered. Two tests are considered: one in which a false instance is 
   * located before some relevant items (expected value=0.9765) and one in 
   * which the false one appears at the end only (expected value=1.0). 
   */
  @Test
  public void testGetAveragePrecision() {
    Map<String, Double> predict = new HashMap<String, Double>();

    predict = new HashMap<String, Double>();
    predict.put("Q268_R4", 0.25);
    predict.put("Q268_R5", 0.2);
    predict.put("Q268_R10", 0.1);
    predict.put("Q268_R13", 0.0769230769230769);
    predict.put("Q268_R14", 0.0714285714285714);
    predict.put("Q268_R16", 0.0625);
    predict.put("Q268_R19", 0.0526315789473684);
    predict.put("Q268_R27", 0.037037037037037);
    predict.put("Q268_R29", 0.0344827586206897);
    predict.put("Q268_R31", 0.032258064516129);
    
    
    Map<String, Boolean> goldRelevance = new HashMap<String, Boolean>();
    goldRelevance.put("Q268_R4", true);
    goldRelevance.put("Q268_R5", true);
    goldRelevance.put("Q268_R10", true);
    goldRelevance.put("Q268_R13", true);
    goldRelevance.put("Q268_R14", true);
    goldRelevance.put("Q268_R16", true);
    goldRelevance.put("Q268_R19", true);
    goldRelevance.put("Q268_R27", false);
    goldRelevance.put("Q268_R29", true);
    goldRelevance.put("Q268_R31", true);
    
    //The 8th-ranked is false: MAP should be 0.9765
    double expectedAvgPrecision = 0.9765;
    double predictedAvgPrecision = AveP.getAveragePrecision(predict, goldRelevance);
    assertEquals(expectedAvgPrecision, predictedAvgPrecision, delta);
    
    //Only the 10th ranked is false: MAP should be 1
    expectedAvgPrecision =1.0;
    goldRelevance.put("Q268_R27", true);
    goldRelevance.put("Q268_R31", false);
    predictedAvgPrecision = AveP.getAveragePrecision(predict, goldRelevance);
    assertEquals(expectedAvgPrecision, predictedAvgPrecision, delta);
    
  }
  
  /**
   * Test the average precision version in which the change in recall is considered. 
   */
  @Test
  public void testGetAveragePrecision2() {
    Map<String, Double> predict = new HashMap<String, Double>();

    predict = new HashMap<String, Double>();
    predict.put("Q268_R4", 0.25);
    predict.put("Q268_R5", 0.2);
    predict.put("Q268_R10", 0.1);
    predict.put("Q268_R13", 0.0769230769230769);
    predict.put("Q268_R14", 0.0714285714285714);
    predict.put("Q268_R16", 0.0625);
    predict.put("Q268_R19", 0.0526315789473684);
    predict.put("Q268_R27", 0.037037037037037);
    predict.put("Q268_R29", 0.0344827586206897);
    predict.put("Q268_R31", 0.032258064516129);
    
    
    Map<String, Boolean> goldRelevance = new HashMap<String, Boolean>();
    goldRelevance.put("Q268_R4", true);
    goldRelevance.put("Q268_R5", true);
    goldRelevance.put("Q268_R10", true);
    goldRelevance.put("Q268_R13", true);
    goldRelevance.put("Q268_R14", true);
    goldRelevance.put("Q268_R16", true);
    goldRelevance.put("Q268_R19", true);
    goldRelevance.put("Q268_R27", false);
    goldRelevance.put("Q268_R29", true);
    goldRelevance.put("Q268_R31", true);
    
    //The 8th-ranked is false: MAP should be 0.9765
    double expectedAvgPrecision = 0.9765;
    double predictedAvgPrecision = AveP.getAveragePrecision2(predict, goldRelevance);
    assertEquals(expectedAvgPrecision, predictedAvgPrecision, delta);
  }

  /**Check that a map gets sorted by values and the expected DESCENDING
   * order is obtained.
   */
  @Test
  public void testSortMapByValues() {
    // Sort the map
    Map<String, Double> sortedMap = AveP.sortMapByValues(unsortedMap, AveP.DESCENDING);
    
    // Iterate over the map to guarantee the order is the expected one.
    Iterator<?> entries = sortedMap.entrySet().iterator();
    int i =0;
    while (entries.hasNext()) {
      Entry<String, Double> thisEntry = (Entry<String, Double>) entries.next();
      System.out.println(thisEntry.getKey());
      assertEquals(thisEntry.getKey(), expected.get(i++));
    }
    // The following assert does not work as it simply checks that key-value
    // pairs exist in the two maps, regardless of the order.
    //    assertEquals(sortedMap, unsortedMap);

  }
  
  @Test
  public void testGetKeysSortedByValue() {
    List<String> sortedList = AveP.getKeysSortedByValue(unsortedMap, AveP.DESCENDING);
    assertThat(sortedList, is(expected));
  }
  
}
