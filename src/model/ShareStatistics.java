package model;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * An interface class that performs the statistics on the stock so that user is able to identify if
 * the particular stock can be bought on a particular day by viewing the insights of that stock.
 */
public interface ShareStatistics {

  /**
   * For a particular day if that particular stock lost its value, or it has gained.
   *
   * @param shareData data of the share for which the trend is to be viewed for.
   * @return value if the stock has gained or lost.
   */
  float stockTrendForDay(Map<String, String> shareData);

  /**
   * Over a period of time if that particular stock lost its value, or it has gained.
   *
   * @param shareData data of the share for which the trend is to be viewed for.
   * @return value if the stock has gained or lost over a period of time.
   */
  float stockTrendForPeriod(Map<String, String> shareData);

  /**
   * Calculates the moving average of that particular stock based on the last X days provided.
   *
   * @param shareData data of the share for which the trend is to be viewed for.
   * @return X day moving average of the stock.
   */
  float xDayMovingAverage(List<Map<String, String>> shareData);

  /**
   * Calculates the crossover of that particular stock over the given period of time, stating that
   * if that stock has a 'BUY' or 'SELL' opportunity.
   *
   * @param shareData data of the share for which the trend is to be viewed for.
   * @return that array of positive and negative crossover dates.
   * @throws IllegalArgumentException if any value is wrongly entered.
   */
  Map<String, List<String>> crossOvers(List<Map<String, String>> shareData)
          throws IllegalArgumentException;

  /**
   * Calculates the moving crossover of that particular stock over the given period of time,
   * stating that if that stock has a 'BUY' or 'SELL' opportunity.
   *
   * @param shareData data of the share for which the trend is to be viewed for.
   * @return that array of positive and negative moving crossover dates.
   */
  Map<String, List<String>> movingCrossovers(List<Map<String, String>> shareData);

  /**
   * The method calculates the values of a particular stock for a given time range
   * inorder to plot it. It also calculates the scale and
   * type of period that needs to be plotted on the y-axis (day, year, month).
   *
   * @param startDate The start date from where the stock values need to be calculated.
   * @param endDate   The end date upto which the stock values need to be calculated.
   * @param values    map of date and its respective values on that date for the stock.
   * @return A pair of hashmap of date and its respective values to be displayed.
   * @throws ParseException If the dates passed isn't parsed properly.
   */
  AbstractMap.SimpleEntry<Map<LocalDate,
          String>, Integer> getPerformanceOfStock(LocalDate startDate,
                                                  LocalDate endDate,
                                                  List<Map<String, String>> values)
          throws ParseException;
}
