import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import model.EmptyPortfolioException;
import model.ManagementModel;

/**
 * The class implements a mock model of the ManagementModel interface. The class is useful for
 * controller testing to see if the controller reaches the appropriate management model methods.
 */
public class ManagementMockModel implements ManagementModel {
  StringBuilder messageLog;

  /**
   * Constructs the mock model that appends the method strings.
   *
   * @param messageLog message to be appended.
   */
  public ManagementMockModel(StringBuilder messageLog) {
    this.messageLog = messageLog;
  }

  @Override
  public void loadFlexiblePortfolio(String name, List<String> shares,
                                    List<Map<String, String>> strategy, float amount) {
    messageLog.append("\nReached load flexible portfolio\n").append(name);
  }

  @Override
  public void createFlexiblePortfolio(String name) {
    messageLog.append("\nReached create flexible portfolio\n").append(name);

  }

  @Override
  public void sell(int shareQuantity, String shareName, LocalDate date) {
    messageLog.append("\nReached sell\n").append(shareName).append("\n")
            .append(shareQuantity).append("\n").append(date.toString());
  }

  @Override
  public float getTotalInvestment(LocalDate date) {
    messageLog.append("\nReached total investment\n").append(date.toString());
    return 0;
  }

  @Override
  public float stockAnalysisDay(Map<String, String> shareData) {
    messageLog.append("\nReached stock analysis today\n");
    return 0;
  }

  @Override
  public float stockAnalysisPeriod(Map<String, String> shareData) {
    messageLog.append("\nReached stock analysis period\n");
    return 0;
  }

  @Override
  public float xDayMovingAverage(List<Map<String, String>> shareData) {
    messageLog.append("\nReached x day moving average\n");
    return 0;
  }

  @Override
  public Map<String, List<String>> crossOvers(List<Map<String, String>> shareData)
          throws IllegalArgumentException {
    messageLog.append("\nReached crossover\n");
    return null;
  }

  @Override
  public Map<String, List<String>> movingCrossovers(List<Map<String, String>> shareData) {
    messageLog.append("\nReached moving crossover\n");
    return null;
  }

  @Override
  public AbstractMap.SimpleEntry<Map<LocalDate,
          String>, Integer> getPerformanceOfFlexiblePortfolio(LocalDate startDate,
                                                              LocalDate endDate,
                                                              Map<LocalDate,
                                                                      List<Map<String,
                                                                              String>>> values) {
    messageLog.append("\nReached performance of flexible portfolio\n").append(startDate)
            .append("\n").append(endDate);

    return null;
  }

  @Override
  public AbstractMap.SimpleEntry<Map<LocalDate,
          String>, Integer> getPerformanceOfInFlexiblePortfolio(LocalDate startDate,
                                                                LocalDate endDate,
                                                                Map<LocalDate,
                                                                        List<Map<String,
                                                                                String>>> values) {
    messageLog.append("\nReached performance of inflexible\n").append(startDate)
            .append("\n").append(endDate);
    return null;
  }

  @Override
  public AbstractMap.SimpleEntry<Map<LocalDate,
          String>, Integer> getPerformanceOfStock(LocalDate startDate,
                                                  LocalDate endDate, List<Map<String,
          String>> values) {
    messageLog.append("\nReached performance of stock\n").append(startDate)
            .append("\n").append(endDate);
    return null;
  }

  @Override
  public List<String> totalCompositionOfFlexiblePortfolio() throws EmptyPortfolioException {
    messageLog.append("\nReached total composition of flexible portfolio\n");
    return null;
  }

  @Override
  public float totalValueOfFlexiblePortfolio(List<Map<String, String>> values)
          throws EmptyPortfolioException {
    messageLog.append("\nReached total value of flexible portfolio\n");
    return 0;
  }

  @Override
  public void buy(int shareQuantity, Map<String, String> data) throws EmptyPortfolioException {
    messageLog.append("\nReached buy\n").append(shareQuantity).append("\n");
  }

  @Override
  public void investFixedAmount(float amount, List<Map<String, String>> data) {
    messageLog.append("\nReached invest fixed amount\n").append(amount);
  }

  @Override
  public List<String> getTransactionOfFlexiblePortfolio() {
    messageLog.append("\nReached get transaction of the flexible portfolio\n");
    return null;
  }

  @Override
  public void dollarCostStrategy(float amount, List<Map<String, String>> data) {
    messageLog.append("\nReached dollar cost Strategy\n").append(amount);
  }

  @Override
  public List<String> getStrategy() {
    messageLog.append("\nReached get strategy \n");
    return null;
  }

  @Override
  public void addStrategyToList(String strategy) {
    messageLog.append("\nReached add Strategy to list\n").append(strategy);
  }

  @Override
  public void createPortfolio(String portfolioName, List<Map<String, String>> sharesMap) {
    messageLog.append("\nReached create portfolio\n").append(portfolioName);
  }

  @Override
  public List<String> totalComposition() throws EmptyPortfolioException {
    messageLog.append("\nReached total composition of portfolio\n");
    return null;
  }

  @Override
  public float totalValue(List<Map<String, String>> values) throws EmptyPortfolioException {
    messageLog.append("\nReached total value of portfolio\n");
    return 0;
  }

}
