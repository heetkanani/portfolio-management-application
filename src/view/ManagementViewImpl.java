package view;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Map;

/**
 * The class implements the ManagementView and its methods. The class also extends the existing
 * portfolio view and it's methods. Using the super class it initializes the input and
 * appendable stream to capture inputs and outputs in the command line.
 */
public class ManagementViewImpl extends PortfolioViewImpl implements ManagementView {
  /**
   * The constructor initializes the InputStream and Appendable.
   *
   * @param in  The InputStream input.
   * @param out The Appendable output.
   */
  public ManagementViewImpl(InputStream in, Appendable out) {
    super(in, out);
  }

  /**
   * The method displays the operations that can be performed on the flexible portfolio.
   * It doesn't implement the operations.
   *
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public void flexiblePortfolioOptions() throws IOException {
    super.out.append("1. View all stocks.").append("\n")
            .append("2. Buy a particular stock.").append("\n")
            .append("3. Sell a particular stock.").append("\n")
            .append("4. Get the total value of portfolio.").append("\n")
            .append("5. Get the total composition of portfolio.").append("\n")
            .append("6. Get the total investment of portfolio.").append("\n")
            .append("7. Invest Fixed Amount of stock").append("\n")
            .append("8. Create strategy on a portfolio").append("\n")
            .append("9. Save the portfolio.").append("\n")
            .append("10. Back to main menu.").append("\n")
            .append("Choose an option: ");
  }

  /**
   * The method displays the operations that can be performed on the portfolio after it has been
   * loaded.
   * It doesn't implement the operations.
   *
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public void loadedPortfolioOptions() throws IOException {
    super.out.append("1. Total Composition of the portfolio.").append("\n")
            .append("2. Total Value of the portfolio.").append("\n")
            .append("3. Performance of the portfolio.").append("\n")
            .append("4. Back to menu.").append("\n")
            .append("Choose an option: ");
  }

  /**
   * The method displays the operations that can be performed on the flexible portfolio after it
   * has been loaded.
   * It doesn't implement the operations.
   *
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public void loadedFlexiblePortfolioOptions() throws IOException {
    super.out.append("1. View all stocks.").append("\n")
            .append("2. Buy a particular stock.").append("\n")
            .append("3. Sell a particular stock.").append("\n")
            .append("4. Get the total value of portfolio.").append("\n")
            .append("5. Get the total composition of portfolio.").append("\n")
            .append("6. Get the total investment of portfolio.").append("\n")
            .append("7. View performance of portfolio.").append("\n")
            .append("8. Save the portfolio.").append("\n")
            .append("9. Invest Fixed Amount of stock").append("\n")
            .append("10. Create strategy on a portfolio").append("\n")
            .append("11. Back to main menu.").append("\n")

            .append("Choose an option: ");
  }

  /**
   * The method displays the operations for the stock trend analysis for a specific stock.
   *
   * @param stockName The name of the stock on which analysis operations are performed.
   *                  It doesn't implement the operations.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public void stockTrendMenuOptions(String stockName) throws IOException {
    super.out.append("\nOperations on stock ").append(stockName.toUpperCase()).append("\n")
            .append("------------------------------------------------------\n")
            .append("1. Daily stock price change.").append("\n")
            .append("2. Stock price change over a period of time. ").append("\n")
            .append("3. X - Day Moving Average. ").append("\n")
            .append("4. Crossover over a period of time. ").append("\n")
            .append("5. Moving Crossover over a period of time. ").append("\n")
            .append("6. Performance of the stock.").append("\n")
            .append("7. Back to main menu.").append("\n")
            .append("------------------------------------------------------\n")
            .append("Choose an option: ");
  }

  /**
   * The method displays the bar graph based on the values provided to it.
   * It doesn't calculate the values.
   *
   * @param values    The values to be displayed on the bar graph.
   * @param name      name of stock or portfolio.
   * @param startDate start date of the values to be displayed.
   * @param endDate   end date of the values to be displayed.
   * @param scale     value each asterisk represents.
   * @param title     title of the graph.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public void displayGraph(Map<LocalDate, String> values, String name, String startDate,
                           String endDate, int scale, String title) throws IOException {
    this.out.append("\nPerformance of ").
            append(title).
            append(" ").
            append(name).
            append(" from ").
            append(startDate).
            append(" to ").
            append(endDate).append("\n").append("\n");

    for (Map.Entry<LocalDate, String> entrySet : values.entrySet()) {
      String[] row = entrySet.getValue().split(",");
      int noOfAsterisks = (int) (Float.parseFloat(row[1]) / scale);
      StringBuilder asterisks = new StringBuilder();
      for (int i = 0; i < noOfAsterisks; i++) {
        asterisks.append('*');
      }
      this.out.append(row[0]).append(": ").append(asterisks.toString()).append(
              "\n");
    }
    this.out.append("\n").append("Scale: * = ").append(String.valueOf(scale)).append("\n\n");
  }

  /**
   * The method displays the different type of portfolios, user can choose.
   *
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public void portfolioOptions() throws IOException {
    this.out.append("1. Normal Portfolio.").append("\n")
            .append("2. Flexible Portfolio.").append("\n")
            .append("Choose an option: ");
  }
}
