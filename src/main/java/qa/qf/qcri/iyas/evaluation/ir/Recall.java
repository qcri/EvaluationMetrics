package qa.qf.qcri.iyas.evaluation.ir;

import java.util.List;
import java.util.Map;

import qa.qf.qcri.iyas.utils.U;

/**
 * Class to compute recalls at different levels of k for a given ranking.
 * @author albarron
 * Qatar Computing Research Institute, 2016
 */
public class Recall extends IrAbstract{
  
  /**
   * Compute the recall at k for k=[1,threshold]. The default threshold is 10.
   * Use {@code setThreshold} to set a different default threshold or 
   * {@code computeRecalls(ranking, gold, threshold)} to use a different value
   * for one particular case.
   * <br/>
   * The minimum between threshold and size of ranking is considered as the max k. 
   * @param ranking list of documents in the ranking; should be ordered by relevance and
   * all the identifiers must exist in the gold map. 
   * @param gold Documents id with boolean values: whether their are relevant or not.
   * @return  array of recalls
   */
  public static double[] computeRecalls(List<String> ranking, Map<String, Boolean> gold) {
    return computeRecalls(ranking, gold, thres);
  }
  
  /**
   * Compute the recall at k for k=[1,threshold]. 
   * Use {@code computeRecalls(ranking, gold)} to consider the default threshold of 10.
   * <br/>
   * The minimum between threshold and size of ranking is considered as the max k.
   * @param ranking list of documents in the ranking; should be ordered by relevance and
   * all the identifiers must exist in the gold map. 
   * @param gold Documents id with boolean values: whether their are relevant or not.
   * @param threshold max value for k
   * @return array of recalls at k for k=[1, threshold]
   */
  public static double[] 
  computeRecalls(List<String> ranking, Map<String, Boolean> gold, int threshold) {
    U.ifFalseCrash(threshold > 0, "The threshold should ge > 0");
    U.ifFalseCrash(GoldContainsAllinRanking(ranking, gold), 
        "There is at least one id in the predicted set which is not included in the gold set");
    
    double[] recalls = new double[Math.min(threshold, ranking.size())];
    int tp=0;
    double relevant =0;
    
    for (boolean isRelevant : gold.values()) {
      if (isRelevant) {
        relevant++;
      }
    }
    
    for (int i=0; i<Math.min(ranking.size(), threshold); i++) {  
      if (gold.get(ranking.get(i))) { 
        tp++;//if true, the document is relevant
      } 
      recalls[i] = tp / relevant;
      //System.out.println(recalls[i]);
    }
    return recalls;
  }
}
