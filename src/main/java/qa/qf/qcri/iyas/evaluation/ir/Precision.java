package qa.qf.qcri.iyas.evaluation.ir;

import java.util.List;
import java.util.Map;

import qa.qf.qcri.iyas.utils.U;

/**
 * Implementation of precisions at a given k.
 * 
 * @author albarron
 * Qatar Computing Research Institute, 2016
 */
public class Precision extends IrAbstract{

  
  /**
   * Compute the precision at k.
   * 
   * @param ranking list of documents in the ranking; should be ordered by 
   *    relevance and all the identifiers must exist in the gold map. 
   * @param gold Documents id with boolean values: whether their are relevant or not.
   * @param k 
   * @return precision at k
   */
  public static double computePrecisionAtK(List<String> ranking, Map<String, Boolean> gold, int k) {
    U.ifFalseCrash(k > 0, "k should be > 0");
    U.ifFalseCrash(GoldContainsAllinRanking(ranking, gold), 
       "At least one id in the predicted set is not included in the gold set");
    
    if (k > ranking.size()) {
      System.out.format("Warning: k=%d is bigger than the size of the ranking\n", k);
    }
    double precision = 0.0;
    int tp =0;
    int i;
    for (i=0; i<Math.min(ranking.size(), k); i++) {  
      if (gold.get(ranking.get(i))) { 
        tp++;         //if true, the document is relevant
      } 
    }
    precision = tp*1.0 / (i);
    return precision;
  }

  
  /**
   * Compute the precision at k for k=[1,threshold]. The default threshold is 10.
   * Use {@code setThreshold} to set a different default threshold or 
   * {@code computePrecisions(ranking, gold, threshold)} to use a different value
   * for one particular case.
   * <br/>
   * The minimum between threshold and size of ranking is considered as the max k. 
   * @param ranking list of documents in the ranking; should be ordered by relevance and
   * all the identifiers must exist in the gold map. 
   * @param gold Documents id with boolean values: whether their are relevant or not.
   * @return  array of precisions at k for k=[1, threshold]
   */
  public static  double[] computePrecisions(List<String> ranking, Map<String, Boolean> gold) {
    return computePrecisions(ranking, gold, thres);
  }

  /**
   * Compute the precision at k for k=[1,threshold]. 
   * Use {@code computePrecisions(ranking, gold)} to consider the default 
   * threshold of 10.
   * <br/>
   * The minimum between threshold and size of ranking is considered as the max k.
   * @param ranking list of documents in the ranking; should be ordered by relevance and
   * all the identifiers must exist in the gold map. 
   * @param gold Documents id with boolean values: whether their are relevant or not.
   * @param threshold max value for k
   * @return array of precisions at k for k=[1, threshold]
   */
  public static  double[] 
  computePrecisions(List<String> ranking, Map<String, Boolean> gold, int threshold) {
    U.ifFalseCrash(threshold > 0, "The threshold should be > 0");
    U.ifFalseCrash(GoldContainsAllinRanking(ranking, gold), 
       "There is at least one id in the predicted set which is not included in the gold set");

    double[] precisions = new double[Math.min(ranking.size(), threshold)];
    int tp =0;
       
    for (int i=0; i<Math.min(ranking.size(), threshold); i++) {  
      if (gold.get(ranking.get(i))) { 
        tp++;         //if true, the document is relevant
      } 
      precisions[i] = tp / (i+1.0);
      //System.out.println(precisions[i]);
    }
    return precisions;
  }
  

}
