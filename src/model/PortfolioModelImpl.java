package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import static java.time.ZoneId.systemDefault;


/**
 * The class implements PortfolioModel interface
 * that maintains the current state of the portfolio by storing the shares.
 */
public class PortfolioModelImpl implements PortfolioModel {

  protected final List<Share> shares;

  /**
   * Constructs the Portfolio Model and initializes the new shares object.
   */
  protected PortfolioModelImpl() {
    shares = new ArrayList<>();
  }

  /**
   * The constructor initializes the portfolio name and shares.
   *
   * @throws ParseException if the shares aren't parsed properly.
   */
  public PortfolioModelImpl(List<Map<String, String>> sharesMap)
          throws ParseException {
    this.shares = new ArrayList<>();
    for (Map<String, String> shareMap : sharesMap) {
      Share share = new Share().
              setTicker(shareMap.get("ticker")).
              setTimestamp(parseDate(shareMap.get("timestamp"))).
              setOpen(Float.parseFloat(shareMap.get("open"))).
              setClose(Float.parseFloat(shareMap.get("close"))).
              setVolume(Long.parseLong(shareMap.get("volume")));
      int shareQuantity = Integer.parseInt(shareMap.get("quantity"));
      for (Share existingShare : shares) {
        if (existingShare.getTicker().equals(share.getTicker())) {
          if (existingShare.getShareQuantity() + shareQuantity > existingShare.getVolume()) {
            throw new IllegalArgumentException("Quantity cannot be greater than the volume");
          }
          this.checkShareQuantity(shareQuantity, existingShare);
          if (existingShare.getTimestamp().isEqual(share.getTimestamp())) {
            existingShare.setShareQuantity(existingShare.getShareQuantity() + shareQuantity);
            existingShare.setBuyValue(existingShare.getBuyValue() +
                    shareQuantity * Float.parseFloat(shareMap.get("close")));
          }
          return;
        }
      }
      share.setShareQuantity(shareQuantity);
      share.setBuyValue(shareQuantity * Float.parseFloat(shareMap.get("close")));
      shares.add(share);
    }
  }


  /**
   * The method converts the String date to LocalDate format.
   *
   * @param date The String date given in "yyyy-MM-dd" format.
   * @return formatted date in LocalDate type.
   * @throws ParseException if the date provided is not valid.
   */
  // Changed from private to protected and static
  protected static LocalDate parseDate(String date) throws ParseException {
    LocalDate formattedDate;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      dateFormat.setLenient(false);
      formattedDate = dateFormat.parse(date).toInstant().atZone(systemDefault()).toLocalDate();
    } catch (ParseException e) {
      throw new ParseException("Provide a valid date", 1);
    }
    return formattedDate;
  }

  /**
   * The method returns the list of shares in the portfolio.
   *
   * @return List of Share object.
   * @throws IllegalStateException if the shares is empty.
   */
  @Override
  public List<Share> totalComposition() throws IllegalStateException {
    if (this.shares.isEmpty()) {
      throw new IllegalStateException("There are no stocks in the portfolio.");
    }
    return this.shares;
  }


  @Override
  public float totalValue(List<Map<String, String>> values) throws ParseException {
    float value = 0;
    for (Map<String, String> m : values) {
      if (isPastOrToday(parseDate(m.get("timestamp")), parseDate(m.get("fileTimeStamp")))) {
        value += Float.parseFloat(m.get("quantity")) * Float.parseFloat(m.get("open"));
      } else {
        value += Float.parseFloat(m.get("quantity")) * Float.parseFloat(m.get("close"));
      }
    }
    return value;
  }

  /**
   * The method checks whether the dates given is today or past.
   *
   * @param shareDate The date of the share.
   * @param fileDate  The date of the file read.
   * @return true if date is today, false if not.
   */
  private boolean isPastOrToday(LocalDate shareDate, LocalDate fileDate) {
    LocalTime currentTime = LocalTime.now();
    return currentTime.isAfter(LocalTime.parse("09:00:00")) &&
            currentTime.isBefore((LocalTime.parse("16:00:00")))
            && shareDate.isEqual(fileDate) && shareDate.isEqual(LocalDate.now());
  }

  /**
   * The method checks if the given share quantity doesn't exceed the share volume.
   *
   * @param shareQuantity given quantity of the share.
   * @param existingShare volume of the share available.
   * @throws IllegalStateException if the quantity exceeds the share volume.
   */
  void checkShareQuantity(float shareQuantity, Share existingShare)
          throws IllegalArgumentException {
    if (shareQuantity > existingShare.getVolume()) {
      throw new IllegalArgumentException("Share Quantity exceeds share's volume");
    }
  }


  protected static AbstractMap.SimpleEntry<String,
          Double> checkPerformancePeriod(LocalDate startDate, LocalDate endDate) {
    long daysDifference = startDate.until(endDate, ChronoUnit.DAYS);
    double interval = Math.ceil((double) daysDifference / 30);
    if (interval <= 30) {
      return new AbstractMap.SimpleEntry<>("DAY", interval);
    } else if (Math.ceil(interval / 30) <= 6) {
      return new AbstractMap.SimpleEntry<>("MONTH", Math.ceil(interval / 30));
    } else {
      return new AbstractMap.SimpleEntry<>("YEAR", Math.ceil(interval / 30 / 30));
    }
  }

  /**
   * The method calculates the values of inflexible/normal portfolio for a given time range
   * inorder to plot it. It also calculates the scale and
   * type of period that needs to be plotted on the y-axis (day, year, month). The method was
   * added in this interface to implement a new requirement to view the performance of portfolio.
   *
   * @param startDate The start date from where the portfolio values need to be calculated.
   * @param endDate   The end date upto which the portfolio values need to be calculated.
   * @param values    map of date and its respective values on that date for each
   *                  stock in the portfolio.
   * @return A pair of hashmap of date and its respective values to be displayed.
   * @throws ParseException If the dates passed isn't parsed properly.
   */
  @Override
  public AbstractMap.SimpleEntry<Map<LocalDate,
          String>, Integer> getPerformanceOfPortfolio(LocalDate startDate,
                                                      LocalDate endDate,
                                                      Map<LocalDate,
                                                              List<Map<String, String>>> values)
          throws ParseException {
    String performancePeriod = checkPerformancePeriod(startDate, endDate).getKey();
    int steps = checkPerformancePeriod(startDate, endDate).getValue().intValue();
    LocalDate currentDate = startDate;
    Map<LocalDate, List<Map<String, String>>> filteredValues = new TreeMap<>();
    if (performancePeriod.equals("DAY")) {
      while (currentDate.isBefore(endDate)) {
        if (!values.get(currentDate).isEmpty()) {
          filteredValues.put(currentDate, values.get(currentDate));
        }
        currentDate = currentDate.plusDays(steps);
      }
    } else if (performancePeriod.equals("MONTH")) {
      currentDate = startDate.withDayOfMonth(currentDate.lengthOfMonth());
      addUpdatedDateValues(startDate, endDate, values, currentDate, filteredValues,
              performancePeriod,
              steps);
    } else {
      currentDate = LocalDate.of(startDate.getYear(), 12, 31);
      addUpdatedDateValues(startDate, endDate, values, currentDate, filteredValues,
              performancePeriod,
              steps);
    }
    return formatValues(filteredValues, performancePeriod);
  }


  private static void addUpdatedDateValues(LocalDate startDate, LocalDate endDate,
                                           Map<LocalDate, List<Map<String, String>>> values,
                                           LocalDate currentDate, Map<LocalDate,
          List<Map<String, String>>> filteredValues, String period, int steps) {
    while (currentDate.isBefore(endDate)) {
      if (!values.get(currentDate).isEmpty()) {
        filteredValues.put(currentDate, values.get(currentDate));
      } else {
        while (values.get(currentDate).isEmpty()) {
          if (currentDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Please provide a working day as a start date !");
          }
          currentDate = currentDate.minusDays(1);
        }
        filteredValues.put(currentDate, values.get(currentDate));
      }
      currentDate = period.equals("MONTH") ? currentDate.plusMonths(steps)
              : currentDate.plusYears(steps);
    }
  }


  private AbstractMap.SimpleEntry<Map<LocalDate,
          String>, Integer> formatValues(Map<LocalDate, List<Map<String, String>>>
                                                 values, String period) throws ParseException {
    Map<LocalDate, Float> temp = new TreeMap<>();
    for (Map.Entry<LocalDate, List<Map<String, String>>> entry : values.entrySet()) {
      temp.put(entry.getKey(), totalValue(entry.getValue()));
    }
    return getMapIntegerSimpleEntry(period, temp);
  }

  protected static AbstractMap.SimpleEntry<Map<LocalDate,
          String>, Integer> getMapIntegerSimpleEntry(String period, Map<LocalDate, Float> temp) {
    Map<LocalDate, String> map = new TreeMap<>();
    float minValue = Float.MAX_VALUE;
    float maxValue = Float.MIN_VALUE;
    for (Map.Entry<LocalDate, Float> entry : temp.entrySet()) {
      String date = changeDateFormat(period, entry);
      minValue = Math.min(minValue, entry.getValue());
      maxValue = Math.max(maxValue, entry.getValue());
      map.put(entry.getKey(), date + "," + entry.getValue());
    }
    int scale = (int) Math.floor(minValue);
    while (maxValue / scale <= 30) {
      scale = scale / 2;
    }
    return new AbstractMap.SimpleEntry<>(map, scale);
  }

  static String changeDateFormat(String period, Map.Entry<LocalDate, Float> entry) {
    String date;
    if (period.equals("YEAR")) {
      date = entry.getKey().format(DateTimeFormatter.ofPattern("yyyy",
              Locale.ENGLISH));
    } else if (period.equals("MONTH")) {
      date = entry.getKey().format(DateTimeFormatter.ofPattern("MMM yyyy",
              Locale.ENGLISH));
    } else {
      date = entry.getKey().format(DateTimeFormatter.ofPattern("dd MMM yyyy",
              Locale.ENGLISH));
    }
    return date;
  }
}
 