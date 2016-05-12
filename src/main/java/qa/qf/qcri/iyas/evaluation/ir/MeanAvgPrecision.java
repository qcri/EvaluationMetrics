package qa.qf.qcri.iyas.evaluation.ir;

import java.util.List;
import java.util.Map;

import qa.qf.qcri.iyas.utils.U;

/**
 * This class implements Mean average precision 
 * @author albarron
 * Qatar Computing Research Institute, 2016
 */
public class MeanAvgPrecision {
  
  public static double 
  computeWithRankingList(List<List<String>> rankings, List<Map<String, Boolean>> gold) {
    U.ifFalseCrash(rankings.size() == gold.size(), 
        "The size of predictions and gold should be identical");
    double map = 0;
    
    for (int i =0; i< rankings.size(); i++) {
      map += AveP.getAveragePrecision(rankings.get(i), gold.get(i));
    }
//    logger.debug("Considered ranking items: " + rankings.size());
    map /= rankings.size();
    System.out.println("MAP = " + map);
    return map;
  }
  
  /**
   * <br/>
   * In this case, for each ranking one gold exists.
   * @param ranking
   * @param gold
   * @return
   */
  public static double 
  computeWithRankingMap(List<Map<String, Double>> rankings, List<Map<String, Boolean>> gold) {
    U.ifFalseCrash(rankings.size() == gold.size(), 
        "The size of predictions and gold should be identical");
    double map = 0;
    
    for (int i =0; i< rankings.size(); i++) {
      map += AveP.getAveragePrecision(rankings.get(i), gold.get(i));
    }
    
    map /= rankings.size();
    
    return map;
  }
  
  /**
   * <br/>
   * In this case, one single gold exists for all the rankings
   * @param predictons
   * @param gold
   * @return
   */
  public static double
  computeWithMapRankings(List<Map<String, Double>> rankings, Map<String, Boolean> gold) {
    double map = 0;
    
    for (int i =0; i< rankings.size(); i++) {
      map += AveP.getAveragePrecision(rankings.get(i), gold);
    }
    
    map /= rankings.size();
    return map;
  }
  
  public static double
  computeWithListRankings(List<List<String>> rankings, Map<String, Boolean> gold) {
    double map = 0;
    
    for (int i =0; i< rankings.size(); i++) {
      map += AveP.getAveragePrecision(rankings.get(i), gold);
    }
    map /= rankings.size();
    return map;
  }
  
  
  
}
