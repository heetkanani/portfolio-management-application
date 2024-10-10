package model;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static model.FlexiblePortfolioModelImpl.checkPerformancePeriod;
import static model.FlexiblePortfolioModelImpl.getMapIntegerSimpleEntry;

/**
 * The implementation of the share statistics class that is used performs
 * the statistics on the stock so that user is able to identify if the particular
 * stock can be bought on a particular day by viewing the insights of that stock.
 */
public class ShareStatisticsImpl implements ShareStatistics {
  @Override
  public float stockTrendForDay(Map<String, String> shareData) {
    return (Float.parseFloat(shareData.get("close")) - Float.parseFloat(shareData.get("open")));
  }

  @Override
  public float stockTrendForPeriod(Map<String, String> shareData) {
    return (Float.parseFloat(shareData.get("closePriceEndDate"))
            - Float.parseFloat(shareData.get("closePriceStartDate")));
  }

  @Override
  public float xDayMovingAverage(List<Map<String, String>> shareData) {
    Map<String, List<String>> averageData = filterXDayData(shareData);
    float average = 0.0f;
    for (String closingPrice : averageData.get("close")) {
      average += Float.parseFloat(closingPrice);
    }
    return (average / averageData.get("close").size());
  }

  private Map<String, List<String>> filterXDayData(List<Map<String, String>> companyMap) {
    Map<String, List<String>> stockData = new HashMap<>();
    List<String> closingValue = new ArrayList<>();
    String date = companyMap.get(companyMap.size() - 1).get("date");
    int day = Integer.parseInt(companyMap.get(companyMap.size() - 1).get("day"));
    int dateIndex = -1;
    dateIndex = getStartDate(companyMap, date, dateIndex);
    for (int i = dateIndex; i < dateIndex + day; i++) {
      closingValue.add(companyMap.get(i).get("close"));
    }
    stockData.put("close", closingValue);
    return stockData;
  }

  private Map<String, List<String>> filterCrossoverData(List<Map<String,
          String>> companyMap) throws IllegalArgumentException {
    Map<String, String> stockData = new HashMap<>();
    Map<String, List<String>> crossOverData = new HashMap<>();
    String startDate = companyMap.get(companyMap.size() - 1).get("startDate");
    String endDate = companyMap.get(companyMap.size() - 1).get("endDate");
    int startDateIndex = -1;
    int endDateIndex = -1;
    startDateIndex = getStartDate(companyMap, startDate, startDateIndex);
    endDateIndex = getStartDate(companyMap, endDate, endDateIndex);
    checkValidDate(startDateIndex, endDateIndex);

    for (int i = endDateIndex; i <= startDateIndex; i++) {
      List<String> pricesData = new ArrayList<>();
      companyMap.remove(companyMap.size() - 1);
      stockData.put("date", companyMap.get(i).get("timestamp"));
      stockData.put("day", "30");
      companyMap.add(stockData);
      String thirtyDayMovingAverage = String.valueOf(xDayMovingAverage(companyMap));
      String currentDate = companyMap.get(i).get("timestamp");
      String previousClosingPrice = companyMap.get(i + 1).get("close");
      String currentClosingPrice = companyMap.get(i).get("close");
      pricesData.add(thirtyDayMovingAverage);
      pricesData.add(previousClosingPrice);
      pricesData.add(currentClosingPrice);
      crossOverData.put(currentDate, pricesData);
    }
    return crossOverData;
  }

  @Override
  public Map<String, List<String>> crossOvers(List<Map<String,
          String>> shareData) throws IllegalArgumentException {
    Map<String, List<String>> averageData = filterCrossoverData(shareData);
    Map<String, List<String>> crossoverData = new HashMap<>();
    List<String> positives = new ArrayList<>();
    List<String> negatives = new ArrayList<>();
    for (String date : averageData.keySet()) {
      String crossover = calculateCrossover(averageData.get(date));
      if (crossover.equals("POSITIVE")) {
        positives.add(date);
      } else if (crossover.equals("NEGATIVE")) {
        negatives.add(date);
      }
      crossoverData.put("POSITIVES", positives);
      crossoverData.put("NEGATIVES", negatives);
    }
    if (crossoverData.isEmpty()) {
      throw new IllegalStateException("No crossover data found for the given time period");
    }
    return crossoverData;
  }

  private String calculateCrossover(List<String> crossoverPrices) {
    float thirtyDayMovingAverage = Float.parseFloat(crossoverPrices.get(0));
    float previousClosingPrice = Float.parseFloat(crossoverPrices.get(1));
    float currentClosingPrice = Float.parseFloat(crossoverPrices.get(2));
    if (previousClosingPrice < thirtyDayMovingAverage &&
            thirtyDayMovingAverage < currentClosingPrice) {
      return "POSITIVE";
    } else if (previousClosingPrice > thirtyDayMovingAverage &&
            thirtyDayMovingAverage > currentClosingPrice) {
      return "NEGATIVE";
    }
    return "";
  }

  private String calculateMovingCrossover(List<String> movingCrossoverPrices) {
    float xDayMovingAverageCurrentDate = Float.parseFloat(movingCrossoverPrices.get(0));
    float xDayMovingAveragePreviousDate = Float.parseFloat(movingCrossoverPrices.get(1));
    float yDayMovingAverageCurrentDate = Float.parseFloat(movingCrossoverPrices.get(2));
    float yDayMovingAveragePreviousDate = Float.parseFloat(movingCrossoverPrices.get(3));

    if (xDayMovingAverageCurrentDate > yDayMovingAverageCurrentDate &&
            xDayMovingAveragePreviousDate < yDayMovingAveragePreviousDate) {
      return "POSITIVE";
    } else if (xDayMovingAverageCurrentDate < yDayMovingAverageCurrentDate &&
            xDayMovingAveragePreviousDate > yDayMovingAveragePreviousDate) {
      return "NEGATIVE";
    }
    return "";
  }

  @Override
  public Map<String, List<String>> movingCrossovers(List<Map<String, String>> shareData) {
    Map<String, List<String>> averageData = filterMovingCrossoverData(shareData);
    Map<String, List<String>> crossoverData = new HashMap<>();
    List<String> positives = new ArrayList<>();
    List<String> negatives = new ArrayList<>();
    for (String date : averageData.keySet()) {
      String crossover = calculateMovingCrossover(averageData.get(date));
      if (crossover.equals("POSITIVE")) {
        positives.add(date);
      } else if (crossover.equals("NEGATIVE")) {
        negatives.add(date);
      }
      crossoverData.put("POSITIVES", positives);
      crossoverData.put("NEGATIVES", negatives);
    }
    if (crossoverData.isEmpty()) {
      throw new IllegalStateException("No crossover data found for the given time period");
    }
    return crossoverData;
  }

  private static int getIndexOfDate(List<Map<String, String>> values, String date) {
    for (int i = 0; i < values.size(); i++) {
      Map<String, String> dates = values.get(i);
      if (dates.get("timestamp").equals(date)) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public AbstractMap.SimpleEntry<Map<LocalDate, String>,
          Integer> getPerformanceOfStock(LocalDate startDate, LocalDate endDate,
                                         List<Map<String, String>> values) {
    String performancePeriod = checkPerformancePeriod(startDate, endDate).getKey();
    LocalDate currentDate = startDate;
    int steps = checkPerformancePeriod(startDate, endDate).getValue().intValue();
    Map<LocalDate, Float> map = new TreeMap<>();
    if (performancePeriod.equals("DAY")) {
      while (currentDate.isBefore(endDate)) {
        int idx = getIndexOfDate(values, currentDate.toString());
        if (idx != -1) {
          map.put(currentDate, Float.parseFloat(values.get(idx).get(
                  "close")));
        }
        currentDate = currentDate.plusDays(steps);
      }
    } else if (performancePeriod.equals("MONTH")) {
      currentDate = startDate.withDayOfMonth(currentDate.lengthOfMonth());
      addUpdatedDateValues(startDate, endDate, values, currentDate, map, steps, performancePeriod);
    } else {
      currentDate = LocalDate.of(startDate.getYear(), 12, 31);
      addUpdatedDateValues(startDate, endDate, values, currentDate, map, steps, performancePeriod);
    }
    return formatValues(map, performancePeriod);
  }

  private static void addUpdatedDateValues(LocalDate startDate, LocalDate endDate,
                                           List<Map<String, String>> values,
                                           LocalDate currentDate, Map<LocalDate,
          Float> map, int steps, String period) {
    while (currentDate.isBefore(endDate)) {
      int idx = getIndexOfDate(values, currentDate.toString());
      if (idx == -1) {
        while (idx == -1) {
          if (currentDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Please provide a working day as a start date !");
          }
          currentDate = currentDate.minusDays(1);
          idx = getIndexOfDate(values, currentDate.toString());
        }
      }
      map.put(currentDate, Float.parseFloat(values.get(idx).get("close")));
      currentDate = period.equals("YEAR") ? currentDate.plusYears(steps) :
              currentDate.plusMonths(steps);
    }
  }

  private AbstractMap.SimpleEntry<Map<LocalDate, String>,
          Integer> formatValues(Map<LocalDate, Float> values, String period) {
    return getMapIntegerSimpleEntry(period, values);
  }


  private Map<String, List<String>> filterMovingCrossoverData(List<Map<String,
          String>> companyMap) {
    Map<String, List<String>> movingCrossovers = new HashMap<>();
    String startDate = companyMap.get(companyMap.size() - 1).get("startDate");
    String endDate = companyMap.get(companyMap.size() - 1).get("endDate");
    String xDay = companyMap.get(companyMap.size() - 1).get("xday");
    String yDay = companyMap.get(companyMap.size() - 1).get("yday");
    int startDateIndex = -1;
    int endDateIndex = -1;
    startDateIndex = getStartDate(companyMap, startDate, startDateIndex);
    endDateIndex = getStartDate(companyMap, endDate, endDateIndex);
    checkValidDate(startDateIndex, endDateIndex);
    for (int i = endDateIndex; i <= startDateIndex; i++) {
      Map<String, String> stockData = new HashMap<>();
      List<String> pricesData = new ArrayList<>();
      companyMap.remove(companyMap.size() - 1);
      String currentDate = companyMap.get(i).get("timestamp");

      //X day - Current Date
      generateDayMap(companyMap, stockData, i, xDay, pricesData);

      //X day - Previous Date
      companyMap.remove(companyMap.size() - 1);
      generateDayMap(companyMap, stockData, i + 1, xDay, pricesData);

      //Y day - Current Date
      companyMap.remove(companyMap.size() - 1);
      generateDayMap(companyMap, stockData, i, yDay, pricesData);

      //Y day - Previous Date
      companyMap.remove(companyMap.size() - 1);
      generateDayMap(companyMap, stockData, i + 1, yDay, pricesData);
      movingCrossovers.put(currentDate, pricesData);
    }
    return movingCrossovers;
  }

  private void generateDayMap(List<Map<String, String>> companyMap,
                              Map<String, String> stockData, int i,
                              String day, List<String> pricesData) {
    stockData.put("date", companyMap.get(i).get("timestamp"));
    stockData.put("day", day);
    companyMap.add(stockData);
    String xDayMovingAverageCurrentDate = String.valueOf(xDayMovingAverage(companyMap));
    pricesData.add(xDayMovingAverageCurrentDate);
  }

  private static void checkValidDate(int startDateIndex, int endDateIndex) {
    if (startDateIndex == -1 || endDateIndex == -1) {
      throw new IllegalArgumentException("No data found for the specified date");
    }
  }

  private static int getStartDate(List<Map<String, String>> companyMap,
                                  String startDate, int startDateIndex) {
    for (int i = 0; i <= companyMap.size(); i++) {
      Map<String, String> startDates = companyMap.get(i);
      if (startDates.get("timestamp").equals(startDate)) {
        startDateIndex = i;
        break;
      }
    }
    return startDateIndex;
  }

}
