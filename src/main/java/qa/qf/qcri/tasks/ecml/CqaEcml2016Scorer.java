package qa.qf.qcri.tasks.ecml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import qa.qf.qcri.iyas.evaluation.ir.AveP;
import qa.qf.qcri.iyas.evaluation.ir.MeanAvgPrecision;
import qa.qf.qcri.iyas.evaluation.ir.MeanReciprocalRank;
import qa.qf.qcri.iyas.evaluation.ir.Precision;


/**
 * This class computes different evaluation metrics for a score file, given the 
 * reference relevance file for the ECML 2016 challenge on community question 
 * answering. The included metrics are:
 * <ul>
 * <li> MAP - mean average precision
 * <li> MRR - mean reciprocal rank
 * <li> P@1 - (average) precision at ranking 1
 * <li> P@5 - (average) precision at ranking 5
 * </ul> 
 * 
 * @author albarron
 * Qatar Computing Research Institute, 2016
 */
public class CqaEcml2016Scorer {

  /** The gold labels to compute the average precision values against */
  private Map<String, Boolean> goldLabels;

  /**
   * Load the gold file to compute all the average precisions against. 
   * @param goldFile path to the goldfile 
   */
  public CqaEcml2016Scorer(String goldFile) {
    goldLabels = CqaEcml2016ScoreFileReader.getLabels(goldFile);
  }
  
  /**
   * Compute all the average precision values necessary to further compute the 
   * ttest.
   * @param file input file with predictions.
   * @return Map of precisions against gold for every ranking in the file.
   */
  public double getMap(String file) {
    //Map<String, Double> precs = new HashMap<String, Double>();
    Map<String, Map<String, Double>> rankings = 
        CqaEcml2016ScoreFileReader.getScoresPerQuery(file);
    
    List<Map<String, Double>> lRankings = new ArrayList<Map<String, Double>>();
    for (String k : rankings.keySet()) {
      lRankings.add(rankings.get(k));
    }
    return MeanAvgPrecision.computeWithMapRankings(lRankings, goldLabels);
  }

  public double getMrr(String file) {
    //Map<String, Double> precs = new HashMap<String, Double>();
    Map<String, Map<String, Double>> rankings = 
        CqaEcml2016ScoreFileReader.getScoresPerQuery(file);
    
    List<Map<String, Double>> lRankings = new ArrayList<Map<String, Double>>();
    for (String k : rankings.keySet()) {
      lRankings.add(rankings.get(k));
    }
    return MeanReciprocalRank.computeWithMapRankings(lRankings, goldLabels);
  }
  
  public double getPatK(String file, int k) {
  //Map<String, Double> precs = new HashMap<String, Double>();
    Map<String, Map<String, Double>> rankings = 
        CqaEcml2016ScoreFileReader.getScoresPerQuery(file);
    
    double avgPrec=0.0;
    int i = 0;
    for (String key : rankings.keySet()) {
      avgPrec +=Precision.computePrecisionAtK(
          AveP.getKeysSortedByValue(rankings.get(key), AveP.DESCENDING),  
          goldLabels, 
          k);
      i++;
    }    
    return avgPrec/i;
  }
  
  public static void main(String[] args) {
    String goldFile =null;;
    String predFile1=null;
    // create the command line parser
    CommandLineParser parser = new DefaultParser();

    // create the Options
    Options options = new Options();
    options.addOption("g", "gold", true, 
        "File with the gold labels)");
    options.addOption("i", "input", true, 
        "File with predictions");   
    try {
      // parse the command line arguments
      CommandLine line = parser.parse( options, args );

      HelpFormatter formatter = new HelpFormatter();
      // validate that block-size has been set
      if(! line.hasOption( "g" ) || ! line.hasOption("i")) {
        System.out.println("The files with gold labels and predictions  "
            + "are mandatory");
        formatter.printHelp( "Semeval2016Scorer", options );
        System.exit(-1);
      }

      goldFile = line.getOptionValue("g");
      predFile1 = line.getOptionValue("i");
    } catch( ParseException exp ) {
      System.out.println( "Unexpected exception:" + exp.getMessage() );
    }

    CqaEcml2016Scorer ss = new CqaEcml2016Scorer(goldFile);
    
    double map = ss.getMap(predFile1);
    double mrr = ss.getMrr(predFile1);
    double pAt1 = ss.getPatK(predFile1, 1);
    double pAt5 = ss.getPatK(predFile1, 5);
    
    System.out.format("MAP = %f%n", map);
    System.out.format("MRR = %f%n", mrr);
    System.out.format("P@1 = %f%n", pAt1);
    System.out.format("P@5 = %f%n", pAt5);
  }
  
  
}
