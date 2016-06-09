package qa.qf.qcri.tasks.ecml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import qa.qf.qcri.iyas.evaluation.ir.AveP;
import qa.qf.qcri.iyas.utils.U;



/**
 * This is a reader for the SemEval 2016 Task 3 on CQA official output file.
 * It contains static method that return a {@code java.util.Map} in different formats:
 * <ul>
 * <li /> ids and scores
 * <li /> ids and label (boolean, as it is binary)
 * <li /> rankings (ordered in a list)
 * <ul> 
 * 
 * These formats are enough to open both gold and prediction files.
 * 
 * @author albarron
 * Qatar Computing Research Institute, 2016
 */
public class CqaEcml2016ScoreFileReader {

  
  private static final char FIELD_SEPARATOR = '\t';
  
  /** The official format for the output file contains five fields */
  private static final int EXPECTED_FIELDS = 3;
  
  
  /** Location of the document id. */
  private static final int LOC_DOC_ID = 0;
 
  /** Location of the score of the document with respect to the query */
  private static final int LOC_DOC_SCORE = 1;
  
  /** Location of the label of the document (true or false) */
  private static final int LOC_DOC_LABEL = 2;
  
  /**
   * Get the labels assigned to each instance in the score file
   * @param scoreFile path to the file with scores and labels
   * @return map with IDs and true/false
   */
  public static Map<String, Boolean> getLabels(String scoreFile) {
    Map<String, Boolean> scoreMap = new LinkedHashMap<String, Boolean>();
    List<ScoreRecord> records = loadDataset(scoreFile);
    for (ScoreRecord sr : records) {
      scoreMap.put(sr.docID, sr.label);
    }
    return scoreMap;
  }
  
  /**
   * Get the labels assigned to each instance in the score file, divided per ranking
   * @param scoreFile path to the file with scores and labels
   * @return map with query IDs and internal maps with doc IDs and true/false
   */
  public static Map<String, Map<String, Boolean>> getLabelsPerQuery(String scoreFile) {
    Map<String, Map<String, Boolean>> scoreMap = new HashMap<String, Map<String, Boolean>>();
    List<ScoreRecord> records = loadDataset(scoreFile);
    for (ScoreRecord sr : records) {
      if (! scoreMap.containsKey(sr.queryID)) {
        scoreMap.put(sr.queryID, new HashMap<String, Boolean>());
      }
      scoreMap.get(sr.queryID).put(sr.docID, sr.label);
    }
    return scoreMap;
  }
   
  /**
   * Get the scores assigned to each instance in the score file
   * @param scoreFile path to the file with scores and labels
   * @return map with IDs and scores
   */
  public static Map<String, Double> getScores(String scoreFile) {
    Map<String, Double> scoreMap = new LinkedHashMap<String, Double>();
    List<ScoreRecord> records = loadDataset(scoreFile);
    for (ScoreRecord sr : records) {
      scoreMap.put(sr.docID, sr.score);
    }
    return scoreMap;
  }
  
  /**
   * Get the scores assigned to each instance in the score file, divided per ranking
   * @param scoreFile path to the file with scores and labels
   * @return map with query IDs and internal maps with doc IDs and scores
   */
  public static Map<String, Map<String, Double>> getScoresPerQuery(String scoreFile) {
    Map<String, Map<String, Double>> scoreMap = new LinkedHashMap<String, Map<String, Double>>();
    List<ScoreRecord> records = loadDataset(scoreFile);
    for (ScoreRecord sr : records) {
      if (! scoreMap.containsKey(sr.queryID)) {
        scoreMap.put(sr.queryID, new LinkedHashMap<String, Double>());
      }
      scoreMap.get(sr.queryID).put(sr.docID, sr.score);
    }
    return scoreMap;
  }
  
  public static Map<String, Map<String, Boolean>> getPredictionsPerQuery(String scoreFile) {
    Map<String, Map<String, Boolean>> scoreMap = new LinkedHashMap<String, Map<String, Boolean>>();
    List<ScoreRecord> records = loadDataset(scoreFile);
    for (ScoreRecord sr : records) {
      if (! scoreMap.containsKey(sr.queryID)) {
        scoreMap.put(sr.queryID, new LinkedHashMap<String, Boolean>());
      }
      scoreMap.get(sr.queryID).put(sr.docID, sr.label);
    }
    return scoreMap;
  }
  
  /**
    TODO not sure if this works or even if it's useful at all 
   * Get the scores assigned to each instance in the score file, divided per ranking
   * @param scoreFile path to the file with scores and labels
   * @return map with query IDs and ordered list of ranked documents
   */
  public static Map<String, List<String>> getRankingPerQueryAsList(String scoreFile) {
    Map<String, List<String>> scoreMap = new LinkedHashMap<String, List<String>>(); 
    Map<String, Map<String, Double>> tmpMap = getScoresPerQuery(scoreFile);
    
    for (String q : tmpMap.keySet()) {
      scoreMap.put(q, AveP.getKeysSortedByValue(tmpMap.get(q), AveP.DESCENDING));
    }
    return scoreMap;
  }
  
  
  /**
   * Loads the input file in tab-separated CSV format and returns all the 
   * records 
   * @param scoreFile input score file
   * @return List of ScoreRecords, with all the fields in the file.
   */
  private static List<ScoreRecord> loadDataset(String scoreFile) {
    File f = new File(scoreFile);
    U.ifFalseCrash(f.canRead(), "I cannot read the input score file " + scoreFile);
    
    List<ScoreRecord> records = new ArrayList<ScoreRecord>();
    CSVParser csvReader;
    CSVFormat csvFileFormat = CSVFormat.DEFAULT
        .withDelimiter(FIELD_SEPARATOR)
        .withQuote(null);
    try {
      csvReader = new CSVParser(new FileReader(scoreFile), csvFileFormat);
      Iterator<CSVRecord> it = csvReader.iterator();
      int i=0;
      while (it.hasNext()) {
        CSVRecord record = it.next();
        U.ifFalseCrash(record.size() == EXPECTED_FIELDS, 
            String.format("Line %d in file %s has %d fields: %d expected", 
            i, scoreFile, record.size(), EXPECTED_FIELDS)); 
        
        ScoreRecord sr = new ScoreRecord();
        sr.queryID = record.get(LOC_DOC_ID).substring(0, record.get(LOC_DOC_ID).indexOf("_"));
        sr.docID = record.get(LOC_DOC_ID);

        sr.score = Double.parseDouble(record.get(LOC_DOC_SCORE));
        sr.label = Boolean.parseBoolean(record.get(LOC_DOC_LABEL));
        records.add(sr);
        i++;
      }
      csvReader.close();
    } catch (FileNotFoundException e) {
//      logger.fatal(String.format("%nERROR while reading CSV file %s%n", scoreFile), e);
      System.exit(-1);
    } catch (IOException e) {
      System.err.println("%nERROR no CSV file found" +  e);
      System.exit(-1);
    }
//    logger.info(String.format("Records loaded from file %s: %d", scoreFile, records.size()));
   
    return records;
  }
  
  
}

/**
 * An internal class to store all the fields of each record in the score and 
 * label file.
 * @author albarron
 *
 */
class ScoreRecord {
  String queryID;
  String docID;
  double score;
  boolean label;
}