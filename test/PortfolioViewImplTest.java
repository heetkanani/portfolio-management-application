import org.junit.Test;

import static org.junit.Assert.assertEquals;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import view.PortfolioView;
import view.PortfolioViewImpl;

/**
 * A JUnit test class for Portfolio View Model.
 */
public class PortfolioViewImplTest {

  PortfolioView view;

  private ByteArrayOutputStream getOutputBytes(String sequence) throws IOException {
    InputStream in = new ByteArrayInputStream(sequence.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioViewImpl(in, out);
    return bytes;
  }

  @Test
  public void testPortfolioMenuOptions() throws IOException {
    ByteArrayOutputStream bytes = getOutputBytes("");
    view.portfolioMenuOptions();
    assertEquals(bytes.toString(), "1. Create a portfolio.\n" +
            "2. View a portfolio.\n" +
            "3. Import your file.\n" +
            "4. List all portfolios.\n" +
            "5. Exit.\n" +
            "Choose an option: ");
  }

  @Test
  public void testStockMenuOptions() throws IOException {
    ByteArrayOutputStream bytes = getOutputBytes("");
    view.stockMenuOptions();
    assertEquals(bytes.toString(), "1. View all stocks.\n" +
            "2. Add a particular stock.\n" +
            "3. Get the total value of portfolio.\n" +
            "4. Get the total composition of portfolio.\n" +
            "5. Save the portfolio.\n" +
            "6. Back to main menu.\n" +
            "Choose an option: ");
  }

  @Test
  public void testSetInputValue() throws IOException {
    ByteArrayOutputStream bytes = getOutputBytes("10");
    assertEquals(Integer.parseInt("10"), view.setInputValue());
  }

  @Test
  public void testSetInputString() throws IOException {
    ByteArrayOutputStream bytes = getOutputBytes("hi");
    assertEquals("hi", view.setInputString());
  }

  @Test
  public void testSetInputMessage() throws IOException {
    ByteArrayOutputStream bytes = getOutputBytes("");
    view.setInputMessage("This is a message");
    assertEquals(bytes.toString(), "This is a message\n");
  }

  @Test
  public void testSetErrorMessage() throws IOException {
    ByteArrayOutputStream bytes = getOutputBytes("");
    view.setErrorMessage("Please enter valid date.");
    assertEquals(bytes.toString(), "\u001B[31m" + "Please enter valid date.\n" + "\033[0m");
  }


}