package qa.qf.qcri.iyas.evaluation.ir;


import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PRFATest extends IrAbstractTest {

  private static  Map<String, Boolean> preds;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
   
    preds = new HashMap<String, Boolean>();
    preds.put("rel1", true);
    preds.put("rel2", true);
    preds.put("irrel1", true);
    preds.put("rel3", true);
    preds.put("irrel2", false);
    
    gold = new HashMap<String, Boolean>();
    gold.put("rel1", true);
    gold.put("rel2", true);
    gold.put("irrel1", false);
    gold.put("rel3", true);
    gold.put("irrel2", false);
    
    PRFA.load(preds, gold);    
  }

  @Test
  public void testGetAccuracy() {
    double expected = 0.8;
    double actual = PRFA.getAccuracy();
    Assert.assertEquals(expected, actual, delta);
  }

  @Test
  public void testGetFmeasure() {
    double expected = 0.8571;
    double actual = PRFA.getFmeasure();
    Assert.assertEquals(expected, actual, delta);
  }

  @Test
  public void testGetFmeasureDouble() {
    double expected = 0.5357;
    double actual = PRFA.getFmeasure(2);
    Assert.assertEquals(expected, actual, delta);
  }

  @Test
  public void testGetPrecision() {
    double expected = 0.75;
    double actual = PRFA.getPrecision();
    Assert.assertEquals(expected, actual, delta);
  }
  
  @Test
  public void testGetRecall() {
    double expected = 1.0;
    double actual = PRFA.getRecall();
    Assert.assertEquals(expected, actual, delta);
    
  }

}
