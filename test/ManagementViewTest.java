import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import view.ManagementView;
import view.ManagementViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for Management View Model.
 */
public class ManagementViewTest {
  ManagementView view;

  private ByteArrayOutputStream getOutputBytes() {
    InputStream in = new ByteArrayInputStream("".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new ManagementViewImpl(in, out);
    return bytes;
  }

  @Test
  public void testFlexiblePortfolioOptions() throws IOException {
    ByteArrayOutputStream bytes = getOutputBytes();
    view.flexiblePortfolioOptions();
    assertEquals("1. View all stocks.\n" +
            "2. Buy a particular stock.\n" +
            "3. Sell a particular stock.\n" +
            "4. Get the total value of portfolio.\n" +
            "5. Get the total composition of portfolio.\n" +
            "6. Get the total investment of portfolio.\n" +
            "7. Save the portfolio.\n" +
            "8. Back to main menu.\n" +
            "Choose an option: ", bytes.toString());
  }

  @Test
  public void testLoadedPortfolioOptions() throws IOException {
    ByteArrayOutputStream bytes = getOutputBytes();
    view.loadedPortfolioOptions();
    assertEquals("1. Total Composition of the portfolio.\n2. " +
            "Total Value of the portfolio.\n3. Performance of the portfolio." +
            "\n4. Back to menu.\nChoose an option: ", bytes.toString());
  }

  @Test
  public void testLoadedFlexiblePortfolioOptions() throws IOException {
    ByteArrayOutputStream bytes = getOutputBytes();
    view.loadedFlexiblePortfolioOptions();
    assertEquals("1. View all stocks." +
            "\n2. Buy a particular stock.\n" +
            "3. Sell a particular stock.\n" +
            "4. Get the total value of portfolio.\n" +
            "5. Get the total composition of portfolio.\n" +
            "6. Get the total investment of portfolio.\n" +
            "7. View performance of portfolio.\n" +
            "8. Save the portfolio.\n" +
            "9. Back to main menu." +
            "\nChoose an option: ", bytes.toString());
  }

  @Test
  public void testStockTrendMenuOptions() throws IOException {
    ByteArrayOutputStream bytes = getOutputBytes();
    view.stockTrendMenuOptions("alphabet");
    assertEquals("", bytes.toString());
  }

  @Test
  public void testDisplayGraph() throws IOException {
    ByteArrayOutputStream bytes = getOutputBytes();
    Map<LocalDate, String> map = new HashMap<>();
    map.put(LocalDate.of(2020, 1, 1), "1 Jan 2020,20");
    map.put(LocalDate.of(2020, 1, 2), "2 Jan 2020,100");
    map.put(LocalDate.of(2020, 1, 3), "3 Jan 2020,200");
    map.put(LocalDate.of(2020, 1, 4), "4 Jan 2020,120");
    map.put(LocalDate.of(2020, 1, 5), "5 Jan 2020,10");
    view.displayGraph(map, "p5", "2020-01-01", "2020-01-05",
            10, "portfolio");
    assertEquals("\nPerformance of portfolio p5 from 2020-01-01 to 2020-01-05\n" +
            "\n5 Jan 2020: *" +
            "\n4 Jan 2020: ************" +
            "\n3 Jan 2020: ********************" +
            "\n2 Jan 2020: **********" +
            "\n1 Jan 2020: **" +
            "\n\nScale: * = 10" +
            "\n\n", bytes.toString());
  }

  @Test
  public void testPortfolioOptions() throws IOException {
    ByteArrayOutputStream bytes = getOutputBytes();
    view.portfolioOptions();
    assertEquals("1. Normal Portfolio.\n" +
            "2. Flexible Portfolio.\n" +
            "Choose an option: ", bytes.toString());
  }
}