package qa.qf.qcri.iyas.evaluation.ir;


import org.junit.Assert;
import org.junit.Test;

/**
 * @author albarron
 * Qatar Computing Research Institute, 2016
 */
public class RecallTest extends IrAbstractTest{



  /**
   * We test that the recalls of a given ranking are the expected ones. In this
   * exercise, the actual ranking contains the following sequence:
   * <br/>
   * relevant, relevant, irrelevant, relevant, irrelevant,
   * <br/> 
   * and there are three relevant documents. Therefore the expected recalls at each k are:
   * <br/>
   * 0.333, 0.666, 0.666, 1.0, 1.0 
   */
  @Test
  public void testComputeRecallsListOfStringMapOfStringBoolean() {
    double[] expected = {0.333, 0.666, 0.666, 1.0, 1.0};
    double[] actual = Recall.computeRecalls(ranking, gold);
    Assert.assertArrayEquals(expected, actual, delta);
  }

  @Test
  public void testComputeRecallsListOfStringMapOfStringBooleanInt() {
    double[] expected = {0.333, 0.666, 0.666, 1.0};
    double[] actual = Recall.computeRecalls(ranking, gold, 4);
    Assert.assertArrayEquals(expected, actual, delta);
  }

}
