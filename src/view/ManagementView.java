package view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

/**
 * The interface represents the menu options of flexible portfolio and stock analysis.
 * The interface extends the existing portfolio view interface to use the existing view methods.
 */
public interface ManagementView extends PortfolioView {

  /**
   * The method displays the operations that can be performed on the flexible portfolio.
   * It doesn't implement the operations.
   *
   * @throws IOException If an I/O error occurs.
   */
  void flexiblePortfolioOptions() throws IOException;

  /**
   * The method displays the operations that can be performed on the portfolio after it has been
   * loaded.
   * It doesn't implement the operations.
   *
   * @throws IOException If an I/O error occurs.
   */
  void loadedPortfolioOptions() throws IOException;

  /**
   * The method displays the operations that can be performed on the flexible portfolio after it
   * has been loaded.
   * It doesn't implement the operations.
   *
   * @throws IOException If an I/O error occurs.
   */
  void loadedFlexiblePortfolioOptions() throws IOException;

  /**
   * The method displays the operations for the stock trend analysis for a specific stock.
   *
   * @param stockName The name of the stock on which analysis operations are performed.
   *                  It doesn't implement the operations.
   * @throws IOException If an I/O error occurs.
   */
  void stockTrendMenuOptions(String stockName) throws IOException;

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
  void displayGraph(Map<LocalDate, String> values, String name, String startDate,
                    String endDate, int scale, String title) throws IOException;

  /**
   * The method displays the different type of portfolios, user can choose.
   *
   * @throws IOException If an I/O error occurs.
   */
  void portfolioOptions() throws IOException;

}
