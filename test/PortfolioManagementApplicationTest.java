import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;

import controller.PortfolioController;
import controller.PortfolioControllerImpl;
import model.ManagementModel;
import model.ManagementModelImpl;
import view.ManagementView;
import view.ManagementViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test class for Portfolio Management Application class.
 */
public class PortfolioManagementApplicationTest {
  private StringBuilder outputString;

  private void formatBytes(String str) {
    str = str.replaceAll("\u001b\\[[;\\d]*m", "");
    str = str.replaceAll("\n", "");
    outputString.append(str);
  }

  private ByteArrayOutputStream generateInputString(String sequence) {
    try {
      outputString = new StringBuilder();
      InputStream in = new ByteArrayInputStream(sequence.getBytes());
      ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();
      PrintStream out = new PrintStream(outputBytes);
      ManagementModel portfolioManagementModel = new ManagementModelImpl();
      ManagementView portfolioView = new ManagementViewImpl(in, out);
      PortfolioController portfolioController =
              new PortfolioControllerImpl(portfolioManagementModel, portfolioView);
      portfolioController.run();
      return outputBytes;
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    return null;
  }

  @Test
  public void testCreatePortfolio() {
    ByteArrayOutputStream bytes = generateInputString("1\np5\n6\n5\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/p5.csv");
    assertFalse(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio." +
            "2. View a portfolio." +
            "3. Import your file." +
            "4. List all portfolios." +
            "5. Exit.Choose an option: " +
            "Enter the name of the portfolio: " +
            "1. View all stocks." +
            "2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio." +
            "5. Save the portfolio." +
            "6. Back to main menu." +
            "Choose an option: " +
            "1. Create a portfolio." +
            "2. View a portfolio." +
            "3. Import your file." +
            "4. List all portfolios." +
            "5. Exit.Choose an option: " +
            "Exiting the application...", outputString.toString());
  }

  @Test
  public void testAddStock() {
    ByteArrayOutputStream bytes = generateInputString("1\np6\n2\nAlphabet\n40\n6\n5\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/p6.csv");
    assertFalse(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio." +
            "2. View a portfolio." +
            "3. Import your file." +
            "4. List all portfolios." +
            "5. Exit.Choose an option: " +
            "Enter the name of the portfolio: " +
            "1. View all stocks." +
            "2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio." +
            "5. Save the portfolio." +
            "6. Back to main menu." +
            "Choose an option: " +
            "Enter the name of the company: " +
            "Enter the quantity of the stock: " +
            "---------------------------------------------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\t" +
            "QUANTITYGOOG\t2024-03-13\t140.06\t140.77\t19589151\t40" +
            "---------------------------------------------------------" +
            "1. View all stocks." +
            "2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio." +
            "5. Save the portfolio." +
            "6. Back to main menu.Choose an option: " +
            "1. Create a portfolio." +
            "2. View a portfolio." +
            "3. Import your file." +
            "4. List all portfolios." +
            "5. Exit.Choose an option: " +
            "Exiting the application...", outputString.toString());
  }

  @Test
  public void testSavePortfolio() {
    ByteArrayOutputStream bytes = generateInputString("1\np7\n2\nAlphabet\n40\n5\n6\n5\n");
    formatBytes(bytes.toString());
    assertEquals(
            "------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
                    "1. Create a portfolio." +
                    "2. View a portfolio." +
                    "3. Import your file." +
                    "4. List all portfolios." +
                    "5. Exit.Choose an option: " +
                    "Enter the name of the portfolio: " +
                    "1. View all stocks." +
                    "2. Add a particular stock." +
                    "3. Get the total value of portfolio." +
                    "4. Get the total composition of portfolio." +
                    "5. Save the portfolio." +
                    "6. Back to main menu." +
                    "Choose an option: " +
                    "Enter the name of the company: " +
                    "Enter the quantity of the stock: " +
                    "---------------------------------------------------------" +
                    "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
                    "GOOG\t2024-03-13\t140.06\t140.77\t19589151\t40" +
                    "---------------------------------------------------------" +
                    "1. View all stocks." +
                    "2. Add a particular stock." +
                    "3. Get the total value of portfolio." +
                    "4. Get the total composition of portfolio." +
                    "5. Save the portfolio." +
                    "6. Back to main menu." +
                    "Choose an option: " +
                    "Portfolio saved successfully." +
                    "1. View all stocks." +
                    "2. Add a particular stock.3. Get the total value of portfolio" +
                    ".4. Get the total composition of portfolio.5. Save the portfolio.6. " +
                    "Back to main menu.Choose an option: 1. Create a portfolio." +
                    "2. View a portfolio." +
                    "3. Import your file.4. List all portfolios.5. Exit." +
                    "Choose an option: Exiting the application...",
            outputString.toString()
    );
    File f1 = new File("portfolios/p7.csv");
    assertTrue(f1.exists());

  }

  @Test
  public void testViewPortfolio() {
    ByteArrayOutputStream bytes = generateInputString("2\np1\n6\n5\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/p1.csv");
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio." +
            "2. View a portfolio." +
            "3. Import your file." +
            "4. List all portfolios." +
            "5. Exit.Choose an option: " +
            "Enter the name of the portfolio: " +
            "Portfolio p1 loaded successfully." +
            "1. View all stocks." +
            "2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio." +
            "5. Save the portfolio." +
            "6. Back to main menu." +
            "Choose an option: " +
            "1. Create a portfolio." +
            "2. View a portfolio." +
            "3. Import your file." +
            "4. List all portfolios." +
            "5. Exit.Choose an option: " +
            "Exiting the application...", outputString.toString());
  }

  @Test
  public void testImportFile() {
    ByteArrayOutputStream bytes = generateInputString("3\np1\n6\n5\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/p1.csv");
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio.2. View a portfolio.3. Import your file.4. " +
            "List all portfolios." +
            "5. Exit.Choose an option: Enter the name of your file(.csv): " +
            "Portfolio p1 loaded successfully.1. View all stocks." +
            "2. Add a particular stock.3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: " +
            "1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Exiting the application...", outputString.toString());
  }

  @Test
  public void testListAllPortfolios() {
    ByteArrayOutputStream bytes = generateInputString("4\n5\n");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "-------- PORTFOLIOS ---------" +
            "myPortfolio.csv" +
            "p1.csv" +
            "p2.csv" +
            "p234.csv" +
            "p3.csv" +
            "p567.csv" +
            "p6.csv-----------------------------" +
            "1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Exiting the application...", outputString.toString());
    File f = new File("portfolios/p1.csv");
    File f1 = new File("portfolios/p2.csv");
    File f2 = new File("portfolios/p3.csv");

    assertTrue(f.exists());
    assertTrue(f1.exists());
    assertTrue(f2.exists());

  }

  @Test
  public void testViewAllStocks() {
    ByteArrayOutputStream bytes = generateInputString("1\np10\n1\nAlp\n2\nAlphabet\n40\n5\n6\n5\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/p10.csv");
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios." +
            "5. Exit.Choose an option: Enter the name of the portfolio: " +
            "1. View all stocks.2. Add a particular stock.3. " +
            "Get the total value of portfolio.4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu." +
            "Choose an option: Search ticker name from A-Z :  " +
            "-----------------------------------------" +
            "STOCKS\tCOMPANY NAME" +
            "ALPS\tAlpine Summit Energy Partners Inc" +
            "ALPP\tAlpine 4 Holdings Inc - Class " +
            "AALPN\tAlpine Immune Sciences Inc" +
            "-----------------------------------------" +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio.4. " +
            "Get the total composition of portfolio.5. " +
            "Save the portfolio.6. Back to main menu.Choose an option: " +
            "Enter the name of the company: Enter the quantity of the stock: " +
            "---------------------------------------------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
            "GOOG\t2024-03-13\t140.06\t140.77\t19589151\t40" +
            "---------------------------------------------------------" +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu." +
            "Choose an option: Portfolio saved successfully." +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio." +
            "5. Save the portfolio." +
            "6. Back to main menu.Choose an option: " +
            "1. Create a portfolio.2. View a portfolio." +
            "3. Import your file.4. List all portfolios.5. Exit.Choose an option: " +
            "Exiting the application...", outputString.toString());
  }

  @Test
  public void testViewPortfolioGetValue() {
    ByteArrayOutputStream bytes = generateInputString("2\np1\n3\n2024-03-08\n6\n5\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/p1.csv");
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
                    "1. Create a portfolio.2. View a portfolio.3. Import your file." +
                    "4. List all portfolios.5. Exit.Choose an option: Enter the name of " +
                    "the portfolio: " +
                    "Portfolio p1 loaded successfully.1. View all stocks." +
                    "2. Add a particular stock.3. Get the total value of portfolio." +
                    "4. Get the total composition of portfolio.5. Save the portfolio." +
                    "6. Back to main menu.Choose an option: Enter the date (YYYY-MM-DD): " +
                    "Total value of portfolio on 2024-03-08: 517.329961. " +
                    "View all stocks.2. Add a particular stock." +
                    "3. Get the total value of portfolio." +
                    "4. Get the total composition of portfolio." +
                    "5. Save the portfolio.6. Back to main menu.Choose an option: " +
                    "1. Create a portfolio.2. View a portfolio.3. Import your file." +
                    "4. List all portfolios.5. Exit.Choose an option: Exiting the application...",
            outputString.toString());
  }

  @Test
  public void testViewPortfolioComposition() {
    ByteArrayOutputStream bytes = generateInputString("2\np1\n4\n6\n5\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/p1.csv");
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Enter the name of the portfolio: Portfolio p1 loaded successfully." +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: " +
            "------------- PORTFOLIO COMPOSITION ---------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
            "AFYA\t2024-03-08\t137.07\t138.94\t22462576\t23" +
            "FEAM\t2024-03-08\t45.75\t45.71\t21820\t25" +
            "--------------------------------------------------------" +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio.4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu." +
            "Choose an option: 1. Create a portfolio.2. View a portfolio." +
            "3. Import your file.4. List all portfolios.5. Exit." +
            "Choose an option: Exiting the application...", outputString.toString());
  }

  @Test
  public void testCreatePortfolioGetValue() {
    ByteArrayOutputStream bytes = generateInputString("1\np30\n2\nAlphabet\n40\n2\n" +
            "goldman\n20\n3\n2024-03-11\n6\n5\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/p30.csv");
    assertFalse(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Enter the name of the portfolio: 1. View all stocks." +
            "2. Add a particular stock.3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio.5. Save the portfolio" +
            ".6. Back to main menu.Choose an option: " +
            "Enter the name of the company: " +
            "Enter the quantity of the stock: " +
            "---------------------------------------------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
            "GOOG\t2024-03-13\t140.06\t140.77\t19589151\t40" +
            "---------------------------------------------------------" +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio." +
            "5. Save the portfolio." +
            "6. Back to main menu.Choose an option: " +
            "Enter the name of the company: " +
            "Enter the quantity of the stock: " +
            "---------------------------------------------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
            "GOOG\t2024-03-13\t140.06\t140.77\t19589151\t40" +
            "GPIQ\t2024-03-13\t46.21\t45.99\t40651\t20" +
            "---------------------------------------------------------" +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu." +
            "Choose an option: Enter the date (YYYY-MM-DD): " +
            "Total value of portfolio on 2024-03-11: 6471.81. " +
            "View all stocks.2. Add a particular stock.3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: 1. Create a portfolio." +
            "2. View a portfolio.3. Import your file.4. List all portfolios." +
            "5. Exit.Choose an option: Exiting the application...", outputString.toString());
  }

  @Test
  public void testCreatePortfolioComposition() {
    ByteArrayOutputStream bytes = generateInputString("1\np34\n2\nAlphabet\n40" +
            "\n2\ngoldman\n20\n4\n6\n5\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/p34.csv");
    assertFalse(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Enter the name of the portfolio: 1. View all stocks." +
            "2. Add a particular stock.3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: Enter the name of the company: " +
            "Enter the quantity of the stock: " +
            "---------------------------------------------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
            "GOOG\t2024-03-13\t140.06\t140.77\t19589151\t40" +
            "---------------------------------------------------------" +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu." +
            "Choose an option: Enter the name of the company: " +
            "Enter the quantity of the stock: " +
            "---------------------------------------------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
            "GOOG\t2024-03-13\t140.06\t140.77\t19589151\t40" +
            "GPIQ\t2024-03-13\t46.21\t45.99\t40651\t20" +
            "---------------------------------------------------------" +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio.4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu.Choose an option: " +
            "------------- PORTFOLIO COMPOSITION ---------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
            "GOOG\t2024-03-13\t140.06\t140.77\t19589151\t40" +
            "GPIQ\t2024-03-13\t46.21\t45.99\t40651\t20" +
            "--------------------------------------------------------" +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio.4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu." +
            "Choose an option: 1. Create a portfolio." +
            "2. View a portfolio.3. Import your file.4. List all portfolios." +
            "5. Exit.Choose an option: Exiting the application...", outputString.toString());
  }

  @Test
  public void testAddStockAfterPortfolioSaved() {
    ByteArrayOutputStream bytes = generateInputString("1\np36\n2\nAlphabet\n40\n" +
            "2\ngoldman\n20\n5\n2\n6\n5\n");
    formatBytes(bytes.toString());

    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio.2. View a portfolio.3. Import your file.4. " +
            "List all portfolios.5. Exit.Choose an option: Enter the name of the" +
            " portfolio: 1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio.4. Get the total composition of " +
            "portfolio.5. Save the portfolio.6. Back to main menu.Choose an option: " +
            "Enter the name of the company: Enter the quantity of the stock: " +
            "---------------------------------------------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
            "GOOG\t2024-03-13\t140.06\t140.77\t19589151\t40" +
            "---------------------------------------------------------" +
            "1. View all stocks.2. Add a particular stock.3. " +
            "Get the total value of portfolio.4. Get the total composition " +
            "of portfolio.5. Save the portfolio.6. Back to main menu." +
            "Choose an option: Enter the name of the company: " +
            "Enter the quantity of the stock: " +
            "---------------------------------------------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
            "GOOG\t2024-03-13\t140.06\t140.77\t19589151\t40" +
            "GPIQ\t2024-03-13\t46.21\t45.99\t40651\t20" +
            "---------------------------------------------------------" +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu.Choose an option: " +
            "Portfolio saved successfully.1. View all stocks.2. " +
            "Add a particular stock.3. Get the total value of portfolio.4. " +
            "Get the total composition of portfolio.5. Save the portfolio.6. " +
            "Back to main menu.Choose an option: " +
            "Operation cannot be performed on saved portfolio." +
            "1. View all stocks.2. Add a particular stock.3. " +
            "Get the total value of portfolio.4. Get the total composition " +
            "of portfolio.5. Save the portfolio.6. Back to main menu." +
            "Choose an option: 1. Create a portfolio.2. View a portfolio.3. " +
            "Import your file.4. List all portfolios.5. Exit.Choose an option: " +
            "Exiting the application...", outputString.toString());
    File f = new File("portfolios/p36.csv");
    assertTrue(f.exists());
  }

  @Test
  public void testCreateAndSaveMultiplePortfolio() {
    ByteArrayOutputStream bytes = generateInputString("1\np56\n2\nAlphabet\n40\n5\n6\n" +
            "1\np57\n2\ngoldman\n40\n5\n6\n5\n");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Enter the name of the portfolio: 1. View all stocks." +
            "2. Add a particular stock.3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: Enter the name of the company: " +
            "Enter the quantity of the stock: " +
            "---------------------------------------------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
            "GOOG\t2024-03-13\t140.06\t140.77\t19589151\t40" +
            "---------------------------------------------------------" +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio.4. Get the total composition of portfolio" +
            ".5. Save the portfolio.6. Back to main menu.Choose an option: " +
            "Portfolio saved successfully.1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio.4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu.Choose an option: " +
            "1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Enter the name of the portfolio: 1. View all stocks." +
            "2. Add a particular stock.3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: Enter the name of the company: " +
            "Enter the quantity of the stock: " +
            "---------------------------------------------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
            "GPIQ\t2024-03-13\t46.21\t45.99\t40651\t40" +
            "---------------------------------------------------------" +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio.5. Save the portfolio.6. " +
            "Back to main menu.Choose an option: Portfolio saved successfully.1. " +
            "View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu.Choose an option: " +
            "1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Exiting the application...", outputString.toString());
    File f1 = new File("portfolios/p56.csv");
    assertTrue(f1.exists());
    File f2 = new File("portfolios/p57.csv");
    assertTrue(f2.exists());
  }

  @Test
  public void testCreateAndSaveMultiplePortfolioWithMultipleStocks() {
    ByteArrayOutputStream bytes = generateInputString("1\np86\n2\nAlphabet\n40\n" +
            "2\ngoldman\n40\n5\n6\n" +
            "1\np87\n2\ngoldman\n40\n2\nnasdaq\n40\n5\n6\n5\n");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio." +
            "2. View a portfolio." +
            "3. Import your file.4. List all portfolios.5. Exit.Choose an option: " +
            "Enter the name of the portfolio: 1. View all stocks." +
            "2. Add a particular stock.3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: " +
            "Enter the name of the company: Enter the quantity of the stock:" +
            " ---------------------------------------------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
            "GOOG\t2024-03-13\t140.06\t140.77\t19589151\t40" +
            "---------------------------------------------------------" +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio.4. Get the total composition of portfolio" +
            ".5. Save the portfolio.6. Back to main menu." +
            "Choose an option: Enter the name of the company: " +
            "Enter the quantity of the stock: " +
            "---------------------------------------------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
            "GOOG\t2024-03-13\t140.06\t140.77\t19589151\t40" +
            "GPIQ\t2024-03-13\t46.21\t45.99\t40651\t40" +
            "---------------------------------------------------------" +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu.Choose an option: " +
            "Portfolio saved successfully.1. View all stocks." +
            "2. Add a particular stock.3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: 1. Create a portfolio." +
            "2. View a portfolio.3. Import your file.4. List all portfolios." +
            "5. Exit.Choose an option: Enter the name of the portfolio: " +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio.4. Get the total composition of " +
            "portfolio.5. Save the portfolio.6. Back to main menu." +
            "Choose an option: Enter the name of the company: " +
            "Enter the quantity of the stock: " +
            "---------------------------------------------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
            "GPIQ\t2024-03-13\t46.21\t45.99\t40651\t40" +
            "---------------------------------------------------------" +
            "1. View all stocks.2. Add a particular stock.3. " +
            "Get the total value of portfolio.4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu.Choose an option: " +
            "Enter the name of the company: Enter the quantity of the stock: " +
            "---------------------------------------------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
            "GPIQ\t2024-03-13\t46.21\t45.99\t40651\t40" +
            "HNDL\t2024-03-13\t21.13\t21.02\t142645\t40" +
            "---------------------------------------------------------" +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: " +
            "Portfolio saved successfully.1. View all stocks." +
            "2. Add a particular stock.3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: 1. Create a portfolio." +
            "2. View a portfolio.3. Import your file.4. List all portfolios." +
            "5. Exit.Choose an option: Exiting the application...", outputString.toString());
    File f3 = new File("portfolios/p86.csv");
    assertTrue(f3.exists());
    File f4 = new File("portfolios/p87.csv");
    assertTrue(f4.exists());
  }

  @Test
  public void testAddStockWithFractionStockQuantity() {
    ByteArrayOutputStream bytes = generateInputString("1\np96\n2\nAlphabet\n23.4\n6\n5\n");
    formatBytes(bytes.toString());
    File f1 = new File("portfolios/p96.csv");
    assertFalse(f1.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Enter the name of the portfolio: " +
            "1. View all stocks.2. Add a particular stock.3. " +
            "Get the total value of portfolio.4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu.Choose an option: " +
            "Enter the name of the company: Enter the quantity of the stock: " +
            "Please provide valid quantity." +
            "1. View all stocks.2. Add a particular stock.3. " +
            "Get the total value of portfolio.4. " +
            "Get the total composition of portfolio.5. " +
            "Save the portfolio.6. Back to main menu." +
            "Choose an option: 1. Create a portfolio.2. View a portfolio.3. " +
            "Import your file.4. List all portfolios.5. Exit." +
            "Choose an option: Exiting the application...", outputString.toString());
  }

  @Test
  public void testInvalidGetValueDate() {
    ByteArrayOutputStream bytes = generateInputString("2\np1\n3\n2024-02-30\n6\n5\n");
    File f = new File("portfolios/p1.csv");
    formatBytes(bytes.toString());
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Enter the name of the portfolio: Portfolio p1 loaded successfully." +
            "1. View all stocks.2. Add a particular stock.3. " +
            "Get the total value of portfolio.4. Get the total composition " +
            "of portfolio.5. Save the portfolio.6. Back to main menu." +
            "Choose an option: Enter the date (YYYY-MM-DD): " +
            "Provide a valid date" +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: " +
            "1. Create a portfolio.2. View a portfolio." +
            "3. Import your file.4. List all portfolios.5. Exit.Choose an option:" +
            " Exiting the application...", outputString.toString());
  }

  @Test
  public void testInvalidGetValueDateString() {
    ByteArrayOutputStream bytes = generateInputString("2\np1\n3\najdk279\n6\n5\n");
    File f1 = new File("portfolios/p1.csv");
    formatBytes(bytes.toString());

    assertTrue(f1.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file.4. " +
            "List all portfolios.5. Exit.Choose an option: " +
            "Enter the name of the portfolio: " +
            "Portfolio p1 loaded successfully.1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio.4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu.Choose an option: " +
            "Enter the date (YYYY-MM-DD): Provide a valid date1. View all stocks." +
            "2. Add a particular stock.3. Get the total value of portfolio.4. " +
            "Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: 1. Create a portfolio." +
            "2. View a portfolio.3. Import your file.4. List all portfolios." +
            "5. Exit.Choose an option: Exiting the application...", outputString.toString());
  }

  @Test
  public void testInvalidCompanyName() {
    ByteArrayOutputStream bytes = generateInputString("1\np90\n2\nGoogle\n23\n6\n5\n");
    File f1 = new File("portfolios/p90.csv");
    formatBytes(bytes.toString());

    assertFalse(f1.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Enter the name of the portfolio: " +
            "1. View all stocks.2. Add a particular stock.3. " +
            "Get the total value of portfolio.4. Get the total composition " +
            "of portfolio.5. Save the portfolio.6. Back to main menu." +
            "Choose an option: Enter the name of the company: " +
            "Enter the quantity of the stock: " +
            "No stock data found for Google" +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio.4. " +
            "Get the total composition of portfolio.5. Save the portfolio.6. " +
            "Back to main menu.Choose an option: 1. Create a portfolio.2. " +
            "View a portfolio.3. Import your file.4. List all portfolios.5. " +
            "Exit.Choose an option: Exiting the application...", outputString.toString());
  }

  @Test
  public void testEmptyPortfolioSave() {
    ByteArrayOutputStream bytes = generateInputString("1\np96\n5\n6\n5\n");
    formatBytes(bytes.toString());
    File f1 = new File("portfolios/p96.csv");
    assertFalse(f1.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio.2. View a portfolio.3. " +
            "Import your file.4. List all portfolios.5. Exit.Choose an option: " +
            "Enter the name of the portfolio: 1. View all stocks.2. " +
            "Add a particular stock.3. Get the total value of portfolio.4. " +
            "Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: " +
            "There are no stocks in the portfolio." +
            "1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu.Choose an option: " +
            "1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Exiting the application...", outputString.toString());
  }

  @Test
  public void testEmptyComposition() {
    ByteArrayOutputStream bytes = generateInputString("1\np25\n4\n6\n5\n");
    File f1 = new File("portfolios/p25.csv");
    formatBytes(bytes.toString());
    assertFalse(f1.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Enter the name of the portfolio: 1. View all stocks.2. " +
            "Add a particular stock.3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio.5. " +
            "Save the portfolio.6. Back to main menu.Choose an option: " +
            "------------- PORTFOLIO COMPOSITION ---------------------" +
            "TICKER\tDATE\t\tOPEN\tCLOSE\t  VOLUME\tQUANTITY" +
            "Please add stock to view the composition" +
            "1. View all stocks.2. Add a particular stock.3. " +
            "Get the total value of portfolio.4. " +
            "Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: " +
            "1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Exiting the application...", outputString.toString());
  }

  @Test
  public void testGetValueOnAvailableDateBeforeUnAvailable() {
    ByteArrayOutputStream bytes = generateInputString("2\np1\n3\n2024-03-09\n6\n5\n");
    File f = new File("portfolios/p1.csv");
    formatBytes(bytes.toString());
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: Enter the name of the portfolio: " +
            "Portfolio p1 loaded successfully.1. View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio.4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu." +
            "Choose an option: Enter the date (YYYY-MM-DD): " +
            "Total value of portfolio on 2024-03-09: 0.00. " +
            "View all stocks.2. Add a particular stock.3. Get the total value of portfolio" +
            ".4. Get the total composition of portfolio.5. " +
            "Save the portfolio.6. Back to main menu.Choose an option: " +
            "1. Create a portfolio.2. View a portfolio.3. " +
            "Import your file.4. List all portfolios.5. Exit.Choose an option: " +
            "Exiting the application...", outputString.toString());
  }

  @Test
  public void testGetValueOnAvailableDateBeforeAvailable() {
    ByteArrayOutputStream bytes = generateInputString("2\np1\n3\n2024-03-08\n6\n5\n");
    File f = new File("portfolios/p1.csv");
    formatBytes(bytes.toString());
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Enter the name of the portfolio: Portfolio p1 loaded successfully." +
            "1. View all stocks.2. Add a particular stock.3. " +
            "Get the total value of portfolio.4. Get the total composition of portfolio." +
            "5. Save the portfolio.6. Back to main menu.Choose an option: " +
            "Enter the date (YYYY-MM-DD): " +
            "Total value of portfolio on 2024-03-08: 517.329961. " +
            "View all stocks.2. Add a particular stock." +
            "3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: " +
            "1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Exiting the application...", outputString.toString());
  }

  @Test
  public void testAddStockZeroQuantity() {
    ByteArrayOutputStream bytes = generateInputString("1\np556\n2\nAlphabet\n0\n6\n5\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/p556.csv");
    assertFalse(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. List all portfolios.5. Exit.Choose an option: " +
            "Enter the name of the portfolio: 1. View all stocks." +
            "2. Add a particular stock.3. Get the total value of portfolio." +
            "4. Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: Enter the name of the company: " +
            "Enter the quantity of the stock: Please provide valid quantity." +
            "1. View all stocks.2. Add a particular stock.3. Get the total value of portfolio" +
            ".4. Get the total composition of portfolio.5. Save the portfolio." +
            "6. Back to main menu.Choose an option: 1. Create a portfolio." +
            "2. View a portfolio.3. Import your file.4. List all portfolios." +
            "5. Exit.Choose an option: Exiting the application...", outputString.toString());
  }

  @Test
  public void testAddStockNegativeQuantity() {
    ByteArrayOutputStream bytes = generateInputString("1\np566\n2\nAlphabet\n-34\n6\n5\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/p566.csv");
    assertFalse(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
                    "1. Create a portfolio.2. View a portfolio." +
                    "3. Import your file.4. List all portfolios." +
                    "5. Exit.Choose an option: Enter the name of the portfolio: " +
                    "1. View all stocks.2. Add a particular stock.3. " +
                    "Get the total value of portfolio.4. Get the total composition " +
                    "of portfolio.5. Save the portfolio.6. Back to main menu.Choose an " +
                    "option: Enter the name of the company: Enter the quantity of the stock: " +
                    "Please provide valid quantity.1. View all stocks.2. Add a particular stock." +
                    "3. Get the total value of portfolio." +
                    "4. Get the total composition of portfolio." +
                    "5. Save the portfolio.6. Back to main menu.Choose an option: " +
                    "1. Create a portfolio.2. View a portfolio.3. Import your file." +
                    "4. List all portfolios.5. Exit.Choose an option: Exiting the application...",
            outputString.toString());
  }

  @Test
  public void testStockGreaterThanVolume() {
    ByteArrayOutputStream bytes = generateInputString("1\np535\n2\n" +
            "Goldman\n100000\n6\n5\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/p535.csv");
    assertFalse(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
                    "------1. Create a portfolio.2. View a portfolio.3. " +
                    "Import your file.4. List all portfolios.5. Exit.Choose an option: " +
                    "Enter the name of the portfolio: 1. View all stocks.2. " +
                    "Add a particular stock.3. Get the total value of portfolio.4. " +
                    "Get the total composition of portfolio.5. Save the portfolio.6. " +
                    "Back to main menu.Choose an option: Enter the name of the company: " +
                    "Enter the quantity of the stock: Quantity cannot be greater than the " +
                    "volume1. View all stocks.2. Add a particular stock.3. " +
                    "Get the total value of portfolio.4. Get the total composition of portfolio" +
                    ".5. Save the portfolio.6. Back to main menu.Choose an option: " +
                    "1. Create a portfolio.2. View a portfolio.3. Import your file.4. " +
                    "List all portfolios.5. Exit.Choose an option: Exiting the application...",
            outputString.toString());
  }

  //Flexible portfolio tests.
  @Test
  public void testBuyStock() {
    ByteArrayOutputStream bytes = generateInputString("1\n2\nfp1\n2\nAlphabet" +
            "\n34\n2024-03-25\n7\n6\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/flexible/fp1.csv");
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
                    "1. Create a portfolio.2. View a portfolio.3. Import your file.4." +
                    " Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an " +
                    "option: 1. Normal Portfolio.2. Flexible Portfolio.Choose an option: " +
                    "Enter the name of the portfolio: 1. View all stocks.2. Buy a pa" +
                    "rticular stock.3. Sell a particular stock.4. Get the total value " +
                    "of portfolio.5. Get the total composition of portfolio.6. Get the " +
                    "total investment of portfolio.7. Save the portfolio.8. Back to main " +
                    "menu.Choose an option: Enter the name of the company: Enter the " +
                    "quantity of the stock: Enter the date when you want to add: 34 Alphabet " +
                    "stock/s  bought successfully.---------------------------------------" +
                    "------------------TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
                    "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t34\t5139.0996-------" +
                    "--------------------------------------------------1. View all stocks." +
                    "2. Buy a particular stock.3. Sell a particular stock.4. Get the t" +
                    "otal value of portfolio.5. Get the total composition of portfolio.6. " +
                    "Get the total investment of portfolio.7. Save the portfolio.8. Back to " +
                    "main menu.Choose an option: Portfolio saved successfully.1. " +
                    "Create a portfolio.2. View a portfolio.3. Import your file.4. " +
                    "Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option: " +
                    "Exiting the application...",
            outputString.toString());
  }

  @Test
  public void testMultipleBuyStockOnMultipleDate() {
    ByteArrayOutputStream bytes = generateInputString("1\n2\nfp2\n2\nAlphabet" +
            "\n34\n2024-03-25\n2\nAlphabet\n34\n2024-03-20\n7\n6\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/flexible/fp2.csv");
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
                    "1. Create a portfolio.2. View a portfolio.3. Import your file.4. " +
                    "Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option: 1" +
                    ". Normal Portfolio.2. Flexible Portfolio.Choose an option: Enter the name" +
                    " of the portfolio: 1. View all stocks.2. Buy a particular stock.3. Sell " +
                    "a particular stock.4. Get the total value of portfolio.5. Get the " +
                    "total composition of portfolio.6. Get the total investment of portfolio" +
                    ".7. Save the portfolio.8. Back to main menu.Choose an option:" +
                    " Enter the name of the company: Enter the quantity of the stock: " +
                    "Enter the date when you want to add: 34 Alphabet stock/s  bought " +
                    "successfully.---------------------------------------------------------" +
                    "TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
                    "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t34\t5139.0996" +
                    "---------------------------------------------------------" +
                    "1. View all stocks.2. Buy a particular stock.3. Sell a particular stock" +
                    ".4. Get the total value of portfolio.5. Get the total " +
                    "composition of portfolio.6. Get the total investment of portfolio." +
                    "7. Save the portfolio.8. Back to main menu.Choose an option: " +
                    "Enter the name of the company: Enter the quantity of the stock: " +
                    "Enter the date when you want to add: 34 Alphabet stock/s  " +
                    "bought successfully.----------------------------------------------------" +
                    "-----" +
                    "TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
                    "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t34\t5139.0996GOOG\t" +
                    "2024-03-20\t148.79\t149.68\t17729996\t34\t5089.1196-------" +
                    "--------------------------------------------------1. View all stocks." +
                    "2. Buy a particular stock.3. Sell a particular stock.4. " +
                    "Get the total value of portfolio.5. Get the total composition of " +
                    "portfolio.6. Get the total investment of portfolio.7. S" +
                    "ave the portfolio.8. Back to main menu.Choose an option: Portfolio " +
                    "saved successfully.1. Create a portfolio.2. View a portfolio.3. Impo" +
                    "rt your file.4. Stock Trend Analysis.5. List all portfolios.6. Exit.Ch" +
                    "oose an option: Exiting the application...",
            outputString.toString());
  }

  @Test
  public void testSell() {
    ByteArrayOutputStream bytes = generateInputString("1\n2\nfp3\n2\nAlphabet" +
            "\n34\n2024-03-20\n3\nAlphabet\n30\n2024-03-25\n7\n6\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/flexible/fp3.csv");
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file.4. " +
            "Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option: 1. " +
            "Normal Portfolio.2. Flexible Portfolio.Choose an option: Enter the name of" +
            " the portfolio: 1. View all stocks.2. Buy a particular stock.3. Sell a " +
            "particular stock.4. Get the total value of portfolio.5. Get the total " +
            "composition of portfolio.6. Get the total investment of portfolio.7. " +
            "Save the portfolio.8. Back to main menu.Choose an option: Enter the name " +
            "of the company: Enter the quantity of the stock: Enter the date when " +
            "you want to add: 34 Alphabet stock/s  bought successfully.--------------" +
            "-------------------------------------------TICKER\tDATE\tOPEN\tCLOSE" +
            "\tVOLUME\tQUANTITY\tBUY VALUEGOOG\t2024-03-20\t150.95\t151.15\t15114728" +
            "\\t34\t5139.0996--------------------------------------------------------" +
            "-1. View all stocks.2. Buy a particular stock.3. Sell a particular " +
            "stock.4. Get the total value of portfolio.5. Get the total composition " +
            "of portfolio.6. Get the total investment of portfolio.7. Save the " +
            "portfolio.8. Back to main menu.Choose an option: Enter the name of " +
            "the stock: Enter the quantity of the stock: Enter the date when you " +
            "want to sell: 30 Alphabet stock/s  sold successfully.----------------" +
            "-----------------------------------------TICKER\tDATE\tOPEN\tCLOSE\t" +
            "VOLUME\tQUANTITY\tBUY VALUEGOOG\t2024-03-25\t150.95\t151.15\t15114728" +
            "\t4\t5139.0996--------------------------------------------------------" +
            "-1. View all stocks.2. Buy a particular stock.3. Sell a particular stock" +
            ".4. Get the total value of portfolio.5. Get the total composition of " +
            "portfolio.6. Get the total investment of portfolio.7. Save the portfolio." +
            "8. Back to main menu.Choose an option: Portfolio saved successfully.1" +
            ". Create a portfolio.2. View a portfolio.3. Import your file.4. Stock " +
            "Trend Analysis.5. List all portfolios.6. Exit.Choose an option: Exiting " +
            "the application...", outputString.toString());
  }

  @Test
  public void testBuySellFractionAmountShare() {
    ByteArrayOutputStream bytes = generateInputString("1\n2\nfp4\n2\nAlphabet\n" +
            "34\n2024-03-25\n3\nAlphabet\n22.5\n2024-03-25\n7\n6\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/flexible/fp4.csv");
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION" +
            " ------1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option:" +
            " 1. Normal Portfolio.2. Flexible Portfolio.Choose an option: Enter the n" +
            "ame of the portfolio: 1. View all stocks.2. Buy a particular stock.3. " +
            "Sell a particular stock.4. Get the total value of portfolio.5. Get the " +
            "total composition of portfolio.6. Get the total investment of portfolio." +
            "7. Save the portfolio.8. Back to main menu.Choose an option: Enter the " +
            "name of the company: Enter the quantity of the stock: Enter the date when " +
            "you want to add: 34 Alphabet stock/s  bought successfully.-------------" +
            "--------------------------------------------TICKER\tDATE\tOPEN\tCLOSE\t" +
            "VOLUME\tQUANTITY\tBUY VALUEGOOG\t2024-03-25\t150.95\t151.15\t15114728\t34" +
            "\t5139.0996---------------------------------------------------------1. " +
            "View all stocks.2. Buy a particular stock.3. Sell a particular stock.4. " +
            "Get the total value of portfolio.5. Get the total composition of portfoli" +
            "o.6. Get the total investment of portfolio.7. Save the portfolio.8. Back " +
            "to main menu.Choose an option: Enter the name of the stock: Enter the " +
            "quantity of the stock: null1. View all stocks.2. Buy a particular stock." +
            "3. Sell a particular stock.4. Get the total value of portfolio.5. Get the " +
            "total composition of portfolio.6. Get the total investment of portfolio.7. " +
            "Save the portfolio.8. Back to main menu.Choose an option: Invalid choice. " +
            "Please select from the above menu options.1. View all stocks.2. Buy a " +
            "particular stock.3. Sell a particular stock.4. Get the total value of " +
            "portfolio.5. Get the total composition of portfolio.6. Get the total " +
            "investment of portfolio.7. Save the portfolio.8. Back to main menu.Choose" +
            " an option: Invalid choice. Please select from the above menu " +
            "options.1. View all stocks.2. Buy a particular stock.3. Sell a " +
            "particular stock.4. Get the total value of portfolio.5. Get the" +
            " total composition of portfolio.6. Get the total investment of " +
            "portfolio.7. Save the portfolio.8. Back to main menu.Choose an " +
            "option: Portfolio saved successfully.1. Create a portfolio.2. " +
            "View a portfolio.3. Import your file.4. Stock Trend Analysis.5. " +
            "List all portfolios.6. Exit.Choose an option: Exiting the application..." +
            "", outputString.toString());
  }

  @Test
  public void testTotalValue() {
    ByteArrayOutputStream bytes = generateInputString("1\n2\nfp5\n2\nAlphabet" +
            "\n34\n2024-03-25\n" +
            "3\nAlphabet\n22\n2024-03-25\n4\n2024-03-25\n7\n6\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/flexible/fp4.csv");
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION" +
            " ------1. Create a portfolio.2. View a portfolio.3. Import your file" +
            ".4. Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option" +
            ": 1. Normal Portfolio.2. Flexible Portfolio.Choose an option: Enter the " +
            "name of the portfolio: 1. View all stocks.2. Buy a particular stock.3. S" +
            "ell a particular stock.4. Get the total value of portfolio.5. Get the to" +
            "tal composition of portfolio.6. Get the total investment of portfolio.7." +
            " Save the portfolio.8. Back to main menu.Choose an option: Enter the name " +
            "of the company: Enter the quantity of the stock: Enter the date when you want" +
            " to add: 34 Alphabet stock/s  bought successfully.------------------------" +
            "---------------------------------TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY" +
            "\tBUY VALUEGOOG\t2024-03-25\t150.95\t151.15\t15114728\t34\t5139.0996-------" +
            "--------------------------------------------------1. View all stocks.2." +
            " Buy a particular stock.3. Sell a particular stock.4. Get the total value" +
            " of portfolio.5. Get the total composition of portfolio.6. Get the total " +
            "investment of portfolio.7. Save the portfolio.8. Back to main menu.Choose " +
            "an option: Enter the name of the stock: Enter the quantity of the stock: " +
            "Enter the date when you want to sell: 22 Alphabet stock/s  sold successf" +
            "ully.---------------------------------------------------------" +
            "TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
            "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t12\t5139.0996-" +
            "--------------------------------------------------------1. View all stocks" +
            ".2. Buy a particular stock.3. Sell a particular stock.4. Get the total" +
            " value of portfolio.5. Get the total composition of portfolio.6. Get " +
            "the total investment of portfolio.7. Save the portfolio.8. Back to " +
            "main menu.Choose an option: Enter the date (YYYY-MM-DD): Total val" +
            "ue of portfolio on 2024-03-25: $1813.79991. View all stocks.2. Buy a" +
            " particular stock.3. Sell a particular stock.4. Get the total value of " +
            "portfolio.5. Get the total composition of portfolio.6. Get the total " +
            "investment of portfolio.7. Save the portfolio.8. Back to main menu.Choose " +
            "an option: Portfolio saved successfully.1. Create a portfolio.2. View a " +
            "portfolio.3. Import your file.4. Stock Trend Analysis.5. List all " +
            "portfolios.6. Exit.Choose an option: Exiting the application.." +
            ".", outputString.toString());
  }

  @Test
  public void testTotalComposition() {
    ByteArrayOutputStream bytes = generateInputString("1\n2\nfp6\n2\n" +
            "Alphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n22\n2024-03-25\n5\n7\n6\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/flexible/fp6.csv");
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file.4. " +
            "Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option: 1. " +
            "Normal Portfolio.2. Flexible Portfolio.Choose an option: Enter the name " +
            "of the portfolio: 1. View all stocks.2. Buy a particular stock.3. Sell a " +
            "particular stock.4. Get the total value of portfolio.5. Get the total" +
            " composition of portfolio.6. Get the total investment of portfolio.7. S" +
            "ave the portfolio.8. Back to main menu.Choose an option: Enter the name" +
            " of the company: Enter the quantity of the stock: Enter the date when y" +
            "ou want to add: 34 Alphabet stock/s  bought successfully.---------------" +
            "------------------------------------------" +
            "TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
            "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t34\t5139.0996" +
            "---------------------------------------------------------1. View all stocks" +
            ".2. Buy a particular stock.3. Sell a particular stock.4. Get the total " +
            "value of portfolio.5. Get the total composition of portfolio.6. Get the " +
            "total investment of portfolio.7. Save the portfolio.8. Back to main menu." +
            "Choose an option: Enter the name of the stock: Enter the quantity of " +
            "the stock: Enter the date when you want to sell: 22 Alphabet stock/s  " +
            "sold successfully.---------------------------------------------------" +
            "------TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
            "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t12\t5139.0996--------" +
            "-------------------------------------------------1. View all stocks." +
            "2. Buy a particular stock.3. Sell a particular stock.4. Get the total " +
            "value of portfolio.5. Get the total composition of portfolio.6. Get " +
            "the total investment of portfolio.7. Save the portfolio.8. Back to main " +
            "menu.Choose an option: ------------- PORTFOLIO COMPOSITION ----------" +
            "-----------TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
            "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t12\t5139.0996-----------" +
            "---------------------------------------------1. View all stocks.2. Buy" +
            " a particular stock.3. Sell a particular stock.4. Get the total value of " +
            "portfolio.5. Get the total composition of portfolio.6. Get the total " +
            "investment of portfolio.7. Save the portfolio.8. Back to main menu." +
            "Choose an option: Portfolio saved successfully.1. Create a portfolio.2. " +
            "View a portfolio.3. Import your file.4. Stock Trend Analysis.5. " +
            "List all portfolios.6. Exit.Choose an option: Exiting the application" +
            "...", outputString.toString());
  }

  @Test
  public void testTotalInvestment() {
    ByteArrayOutputStream bytes = generateInputString("1\n2\npf7\n2\n" +
            "Alphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n22\n2024-03-25\n6\n2024-03-25\n7\n6\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/flexible/fp7.csv");
    assertFalse(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file.4. " +
            "Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option: 1." +
            " Normal Portfolio.2. Flexible Portfolio.Choose an option: Enter the name " +
            "of the portfolio: 1. View all stocks.2. Buy a particular stock.3. Sell a p" +
            "articular stock.4. Get the total value of portfolio.5. Get the total " +
            "composition of portfolio.6. Get the total investment of portfolio.7." +
            " Save the portfolio.8. Back to main menu.Choose an option: Enter the name " +
            "of the company: Enter the quantity of the stock: Enter the date when you " +
            "want to add: 34 Alphabet stock/s  bought successfully.-----------------------" +
            "----------------------------------TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\t" +
            "QUANTITY\tBUY VALUEGOOG\t2024-03-25\t150.95\t151.15\t15114728\t34\t5139.0996" +
            "---------------------------------------------------------1. View all stocks." +
            "2. Buy a particular stock.3. Sell a particular stock.4. Get the total value" +
            " of portfolio.5. Get the total composition of portfolio.6. Get the total " +
            "investment of portfolio.7. Save the portfolio.8. Back to main menu.Choose an " +
            "option: Enter the name of the stock: Enter the quantity of the stock:" +
            " Enter the date when you want to sell: 22 Alphabet stock/s  sold successfully" +
            ".---------------------------------------------------------" +
            "TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
            "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t12\t5139.0996-------" +
            "--------------------------------------------------1. View all stocks" +
            ".2. Buy a particular stock.3. Sell a particular stock.4. Get the total value" +
            " of portfolio.5. Get the total composition of portfolio.6. Get the " +
            "total investment of portfolio.7. Save the portfolio.8. Back to main " +
            "menu.Choose an option: Enter the date (YYYY-MM-DD): Total Investment " +
            "on 2024-03-25 is 5139.0996$ 1. View all stocks.2. Buy a particular " +
            "stock.3. Sell a particular stock.4. Get the total value of portfolio.5" +
            ". Get the total composition of portfolio.6. Get the total investment " +
            "of portfolio.7. Save the portfolio.8. Back to main menu.Choose an option: " +
            "Portfolio saved successfully.1. Create a portfolio.2. View a portfolio." +
            "3. Import your file.4. Stock Trend Analysis.5. List all portfolios.6. " +
            "Exit.Choose an option: Exiting the application...", outputString.toString());
  }

  @Test
  public void testBuyStockAfterLoad() {
    ByteArrayOutputStream bytes = generateInputString("1\n2\npf8\n2\n" +
            "Alphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n22\n2024-03-25\n6\n2024-03-25\n7\n6\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/flexible/fp8.csv");
    assertFalse(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file.4. " +
            "Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option: 1. " +
            "Normal Portfolio.2. Flexible Portfolio.Choose an option: Enter the name of " +
            "the portfolio: 1. View all stocks.2. Buy a particular stock.3. Sell a " +
            "particular stock.4. Get the total value of portfolio.5. Get the total " +
            "composition of portfolio.6. Get the total investment of portfolio.7. " +
            "Save the portfolio.8. Back to main menu.Choose an option: Enter the name " +
            "of the company: Enter the quantity of the stock: Enter the date when you" +
            " want to add: 34 Alphabet stock/s  bought successfully.-------------------" +
            "--------------------------------------" +
            "TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
            "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t34\t5139.0996-" +
            "--------------------------------------------------------1. View all stocks" +
            ".2. Buy a particular stock.3. Sell a particular stock.4. Get the total " +
            "value of portfolio.5. Get the total composition of portfolio.6. Get the " +
            "total investment of portfolio.7. Save the portfolio.8. Back to main menu." +
            "Choose an option: Enter the name of the stock: Enter the quantity of the" +
            " stock: Enter the date when you want to sell: 22 Alphabet stock/s  sold " +
            "successfully.---------------------------------------------------------" +
            "TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
            "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t12\t5139.0996------" +
            "---------------------------------------------------1. View all stocks." +
            "2. Buy a particular stock.3. Sell a particular stock.4. Get the total v" +
            "alue of portfolio.5. Get the total composition of portfolio.6. Get the " +
            "total investment of portfolio.7. Save the portfolio.8. Back to main menu" +
            ".Choose an option: Enter the date (YYYY-MM-DD): Total Investment on " +
            "2024-03-25 is $5139.0996 1. View all stocks.2. Buy a particular stock.3. " +
            "Sell a particular stock.4. Get the total value of portfolio.5. Get the " +
            "total composition of portfolio.6. Get the total investment of portfolio." +
            "7. Save the portfolio.8. Back to main menu.Choose an option: Portfolio " +
            "saved successfully.1. Create a portfolio.2. View a portfolio.3. Import " +
            "your file.4. Stock Trend Analysis.5. List all portfolios.6. Exit.Choose " +
            "an option: Exiting the application...", outputString.toString());
  }

  @Test
  public void testSellStockAfterLoad() {
    ByteArrayOutputStream bytes = generateInputString("2\n2\nfp1\n2\n" +
            "Alphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n30\n2024-03-25\n8\n6\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/flexible/fp1.csv");
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION" +
            " ------1. Create a portfolio.2. View a portfolio.3. Import your file.4." +
            " Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option: " +
            "1. Normal Portfolio.2. Flexible Portfolio.Choose an option: Enter your" +
            " portfolio name: Portfolio fp1 loaded successfully.1. View all stocks." +
            "2. Buy a particular stock.3. Sell a particular stock.4. Get the total " +
            "value of portfolio.5. Get the total composition of portfolio.6. Get the" +
            " total investment of portfolio.7. View performance of portfolio.8. Save" +
            " the portfolio.9. Back to main menu.Choose an option: Enter the name of" +
            " the company: Enter the quantity of the stock: Enter the date when you " +
            "want to add: 34 Alphabet stock/s  bought successfully.------------------" +
            "---------------------------------------" +
            "TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
            "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t72\t15417.299" +
            "---------------------------------------------------------" +
            "1. View all stocks.2. Buy a particular stock.3. Sell a particular " +
            "stock.4. Get the total value of portfolio.5. Get the total " +
            "composition of portfolio.6. Get the total investment of portfolio." +
            "7. View performance of portfolio.8. Save the portfolio.9. Back to " +
            "main menu.Choose an option: Enter the name of the stock: Enter the" +
            " quantity of the stock: Enter the date when you want to sell: 30 Alphabet" +
            " stock/s  sold successfully.------------------------------------------" +
            "---------------TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
            "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t42\t15417.299-------------" +
            "--------------------------------------------1. View all stocks.2. " +
            "Buy a particular stock.3. Sell a particular stock.4. Get the total " +
            "value of portfolio.5. Get the total composition of portfolio.6. Get the " +
            "total investment of portfolio.7. View performance of portfolio.8. Save" +
            " the portfolio.9. Back to main menu.Choose an option: Portfolio saved" +
            " successfully.1. Create a portfolio.2. View a portfolio.3. Import your " +
            "file.4. Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option:" +
            " Exiting the application...", outputString.toString());
  }

  @Test
  public void testSellMoreThanBuy() {
    ByteArrayOutputStream bytes = generateInputString("2\n2\nfp1\n2" +
            "\nAlphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n54\n2024-03-25\n8\n6\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/flexible/fp1.csv");
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION" +
            " ------1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option:" +
            " 1. Normal Portfolio.2. Flexible Portfolio.Choose an option: Enter you" +
            "r portfolio name: Portfolio fp1 loaded successfully.1. View all stocks." +
            "2. Buy a particular stock.3. Sell a particular stock.4. Get the total " +
            "value of portfolio.5. Get the total composition of portfolio.6. Get the " +
            "total investment of portfolio.7. View performance of portfolio.8. Save " +
            "the portfolio.9. Back to main menu.Choose an option: Enter the name of " +
            "the company: Enter the quantity of the stock: Enter the date when you want " +
            "to add: 34 Alphabet stock/s  bought successfully.------------------------" +
            "---------------------------------" +
            "TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
            "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t76\t20556.398--" +
            "-------------------------------------------------------" +
            "1. View all stocks.2. Buy a particular stock.3. Sell a particular stock." +
            "4. Get the total value of portfolio.5. Get the total composition of " +
            "portfolio.6. Get the total investment of portfolio.7. View performance " +
            "of portfolio.8. Save the portfolio.9. Back to main menu.Choose an option: " +
            "Enter the name of the stock: Enter the quantity of the stock: Enter the " +
            "date when you want to sell: 54 Alphabet stock/s  sold successfully.-------" +
            "--------------------------------------------------" +
            "TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
            "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t22\t20556.398----" +
            "-----------------------------------------------------1. View all s" +
            "tocks.2. Buy a particular stock.3. Sell a particular stock.4. Get the " +
            "total value of portfolio.5. Get the total composition of portfolio.6. " +
            "Get the total investment of portfolio.7. View performance of portfolio." +
            "8. Save the portfolio.9. Back to main menu.Choose an option: Portfolio" +
            " saved successfully.1. Create a portfolio.2. View a portfolio.3. Impo" +
            "rt your file.4. Stock Trend Analysis.5. List all portfolios.6. Exit.Ch" +
            "oose an option: Exiting the application...", outputString.toString());
  }

  @Test
  public void testTotalCompositionAfterLoad() {
    ByteArrayOutputStream bytes = generateInputString("2\n2\nfp1\n2\nAlphabet" +
            "\n34\n2024-03-25\n" +
            "3\nAlphabet\n22\n2024-03-25\n5\n8\n6\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/flexible/fp1.csv");
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION" +
            " ------1. Create a portfolio.2. View a portfolio.3. Import your file" +
            ".4. Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option:" +
            " 1. Normal Portfolio.2. Flexible Portfolio.Choose an option: Enter your" +
            " portfolio name: Portfolio fp1 loaded successfully.1. View all stocks.2." +
            " Buy a particular stock.3. Sell a particular stock.4. Get the total value " +
            "of portfolio.5. Get the total composition of portfolio.6. Get the total " +
            "investment of portfolio.7. View performance of portfolio.8. Save the portfolio." +
            "9. Back to main menu.Choose an option: Enter the name of the company: Enter" +
            " the quantity of the stock: Enter the date when you want to add: 34 " +
            "Alphabet stock/s  bought successfully.----------------------------------" +
            "-----------------------TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
            "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t36\t30834.598-----------------" +
            "----------------------------------------1. View all stocks.2. Buy a " +
            "particular stock.3. Sell a particular stock.4. Get the total value of portfolio" +
            ".5. Get the total composition of portfolio.6. Get the total investment of " +
            "portfolio.7. View performance of portfolio.8. Save the portfolio.9. Back " +
            "to main menu.Choose an option: Enter the name of the stock: Enter the qua" +
            "ntity of the stock: Enter the date when you want to sell: 22 Alphabet stock/s  " +
            "sold successfully.------------------------------------------------------" +
            "---TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUEGOOG\t2024-03-25" +
            "\t150.95\t151.15\t15114728\t14\t30834.598-------------------------------" +
            "--------------------------1. View all stocks.2. Buy a particular stock.3." +
            " Sell a particular stock.4. Get the total value of portfolio.5. Get the" +
            " total composition of portfolio.6. Get the total investment of portfolio" +
            ".7. View performance of portfolio.8. Save the portfolio.9. Back to main" +
            " menu.Choose an option: ------------- PORTFOLIO COMPOSITION ------------" +
            "---------TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUEGOOG\t2024" +
            "-03-25\t150.95\t151.15\t15114728\t14\t30834.598--------------------------" +
            "------------------------------1. View all stocks.2. Buy a particular stock" +
            ".3. Sell a particular stock.4. Get the total value of portfolio.5. Get the" +
            " total composition of portfolio.6. Get the total investment of portfolio." +
            "7. View performance of portfolio.8. Save the portfolio.9. Back to main menu" +
            ".Choose an option: Portfolio saved successfully.1. Create a portfolio.2." +
            " View a portfolio.3. Import your file.4. Stock Trend Analysis.5. List all" +
            " portfolios.6. Exit.Choose an option: Exiting the applic" +
            "ation...", outputString.toString());
  }

  @Test
  public void testTotalInvestmentAfterLoad() {
    ByteArrayOutputStream bytes = generateInputString("2\n2\nfp1\n2\n" +
            "Alphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n22\n2024-03-25\n6\n2024-03-25\n8\n6\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/flexible/fp1.csv");
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION" +
            " ------1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option: " +
            "1. Normal Portfolio.2. Flexible Portfolio.Choose an option: Enter your " +
            "portfolio name: Portfolio fp1 loaded successfully.1. View all stocks.2. " +
            "Buy a particular stock.3. Sell a particular stock.4. Get the total value " +
            "of portfolio.5. Get the total composition of portfolio.6. Get the total " +
            "investment of portfolio.7. View performance of portfolio.8. Save the " +
            "portfolio.9. Back to main menu.Choose an option: Enter the name of the " +
            "company: Enter the quantity of the stock: Enter the date when you want " +
            "to add: 34 Alphabet stock/s  bought successfully.-----------------------" +
            "----------------------------------" +
            "TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
            "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t48\t35973.695----" +
            "-----------------------------------------------------1. View all stocks." +
            "2. Buy a particular stock.3. Sell a particular stock.4. Get the total " +
            "value of portfolio.5. Get the total composition of portfolio.6. Get " +
            "the total investment of portfolio.7. View performance of portfolio.8. " +
            "Save the portfolio.9. Back to main menu.Choose an option: Enter the " +
            "name of the stock: Enter the quantity of the stock: Enter the date " +
            "when you want to sell: 22 Alphabet stock/s  sold successfully.------" +
            "---------------------------------------------------" +
            "TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
            "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t26\t35973.695---------" +
            "------------------------------------------------1. View all stocks.2." +
            " Buy a particular stock.3. Sell a particular stock.4. Get the total " +
            "value of portfolio.5. Get the total composition of portfolio.6. Get " +
            "the total investment of portfolio.7. View performance of portfolio.8. " +
            "Save the portfolio.9. Back to main menu.Choose an option: Enter the date" +
            " (YYYY-MM-DD): Total Investment on 2024-03-25 is $30834.5981. " +
            "View all stocks.2. Buy a particular stock.3. Sell a particular stock." +
            "4. Get the total value of portfolio.5. Get the total composition of" +
            " portfolio.6. Get the total investment of portfolio.7. View performance " +
            "of portfolio.8. Save the portfolio.9. Back to main menu.Choose an option" +
            ": Portfolio saved successfully.1. Create a portfolio.2. View a" +
            " portfolio.3. Import your file.4. Stock Trend Analysis.5. List all " +
            "portfolios.6. Exit.Choose an option: Exiting the" +
            " application...", outputString.toString());
  }

  @Test
  public void testTotalValueBeforePortfolioCreated() {
    ByteArrayOutputStream bytes = generateInputString("2\n2\nfp1\n2\n" +
            "Alphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n22\n2024-03-25\n4\n2019-03-25\n8\n6\n");
    formatBytes(bytes.toString());
    File f = new File("portfolios/flexible/fp1.csv");
    assertTrue(f.exists());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an " +
            "option: 1. Normal Portfolio.2. Flexible Portfolio.Choose an option" +
            ": Enter your portfolio name: Portfolio fp1 loaded successfully.1. " +
            "View all stocks.2. Buy a particular stock.3. Sell a particular " +
            "stock.4. Get the total value of portfolio.5. Get the total " +
            "composition of portfolio.6. Get the total investment of portfolio.7. " +
            "View performance of portfolio.8. Save the portfolio.9. Back to main " +
            "menu.Choose an option: Enter the name of the company: Enter the " +
            "quantity of the stock: Enter the date when you want to add: 34 " +
            "Alphabet stock/s  bought successfully.----------------------------" +
            "-----------------------------" +
            "TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
            "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t60\t41112.797-" +
            "--------------------------------------------------------1. " +
            "View all stocks.2. Buy a particular stock.3. Sell a particular stock." +
            "4. Get the total value of portfolio.5. Get the total composition of" +
            " portfolio.6. Get the total investment of portfolio.7. View " +
            "performance of portfolio.8. Save the portfolio.9. Back to main menu." +
            "Choose an option: Enter the name of the stock: Enter the quantity of" +
            " the stock: Enter the date when you want to sell: 22 Alphabet stock/s " +
            " sold successfully.------------------------------------------------" +
            "---------TICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE" +
            "GOOG\t2024-03-25\t150.95\t151.15\t15114728\t38\t41112.797---------" +
            "------------------------------------------------1. View all stocks" +
            ".2. Buy a particular stock.3. Sell a particular stock.4. Get the total" +
            " value of portfolio.5. Get the total composition of portfolio.6. Get" +
            " the total investment of portfolio.7. View performance of portfolio.8. " +
            "Save the portfolio.9. Back to main menu.Choose an option: Enter the" +
            " date (YYYY-MM-DD): Total value of portfolio on 2019-03-25: $0.01. " +
            "View all stocks.2. Buy a particular stock.3. Sell a particular stock.4." +
            " Get the total value of portfolio.5. Get the total composition of " +
            "portfolio.6. Get the total investment of portfolio.7. View performance " +
            "of portfolio.8. Save the portfolio.9. Back to main menu.Choose an " +
            "option: Portfolio saved successfully.1. Create a portfolio.2. View a " +
            "portfolio.3. Import your file.4. Stock Trend Analysis.5. List all p" +
            "ortfolios.6. Exit.Choose an option: Exiting the " +
            "application...", outputString.toString());
  }

  //Stock trend analysis test case
  @Test
  public void testGainOrLossOnADay() {
    ByteArrayOutputStream bytes = generateInputString("4\nAlphabet\n1\n2024-03-25\n7\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO " +
            "MANAGEMENT APPLICATION ------1. Create a portfolio.2." +
            " View a portfolio.3. Import your file.4. Stock Trend Analysis." +
            "5. List all portfolios.6. Exit.Choose an option: Enter the name " +
            "of the company: Operations on stock ALPHABET------------------" +
            "------------------------------------1. Daily stock price change." +
            "2. Stock price change over a period of time. 3. X - Day Moving " +
            "Average. 4. Crossover over a period of time. 5. Moving Crossover " +
            "over a period of time. 6. Performance of the stock.7. Back to main menu" +
            ".------------------------------------------------------" +
            "Choose an option: Enter the date (YYYY-MM-DD):You may buy Alphabet " +
            "as it has gained: +$0.19999695Operations on stock ALPHABET-------" +
            "-----------------------------------------------1. Daily stock price " +
            "change.2. Stock price change over a period of time. 3. X - Day Moving Average." +
            " 4. Crossover over a period of time. 5. Moving Crossover over " +
            "a period of time. 6. Performance of the stock.7. Back to main menu." +
            "------------------------------------------------------" +
            "Choose an option: 1. Create a portfolio.2. View a portfolio.3. Import " +
            "your file.4. Stock Trend Analysis.5. List all portfolios.6. Exit." +
            "Choose an option: Exiting the application...", outputString.toString());
  }

  @Test
  public void testGainOrLossOnADayOnUnavailableDate() {
    ByteArrayOutputStream bytes = generateInputString("4\nAlphabet\n1\n2024-03-23\n7\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION" +
            " ------1. Create a portfolio.2. View a portfolio.3. " +
            "Import your file.4. Stock Trend Analysis.5. List all " +
            "portfolios.6. Exit.Choose an option: Enter the name of " +
            "the company: Operations on stock ALPHABET----------------" +
            "--------------------------------------1. Daily stock price " +
            "change.2. Stock price change over a period of time. 3. X - " +
            "Day Moving Average. 4. Crossover over a period of time. 5." +
            " Moving Crossover over a period of time. 6. Performance of " +
            "the stock.7. Back to main menu.-------------------------------" +
            "-----------------------Choose an option: Enter the date (YYYY-MM-DD)" +
            ":Share not found on this 2024-03-23" +
            "Operations on stock ALPHABET-------------------------------" +
            "-----------------------1. Daily stock price change.2. " +
            "Stock price change over a period of time. 3. X - Day Moving Average" +
            ". 4. Crossover over a period of time. 5. Moving Crossover over " +
            "a period of time. 6. Performance of the stock.7. Back to main menu." +
            "------------------------------------------------------" +
            "Choose an option: 1. Create a portfolio.2. View a portfolio.3. " +
            "Import your file.4. Stock Trend Analysis.5. List all portfolios" +
            ".6. Exit.Choose an option: Exiting the application...", outputString.toString());
  }

  @Test
  public void testGainOrLossPeriod() {
    ByteArrayOutputStream bytes = generateInputString("4\nAlphabet\n2\n" +
            "2023-01-03\n2024-03-25\n7\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO " +
            "MANAGEMENT APPLICATION ------1. Create a portfolio.2. " +
            "View a portfolio.3. Import your file.4. Stock Trend Analysis." +
            "5. List all portfolios.6. Exit.Choose an option: Enter the name " +
            "of the company: Operations on stock ALPHABET---------------------" +
            "---------------------------------1. Daily stock price change.2. " +
            "Stock price change over a period of time. 3. X - Day Moving " +
            "Average. 4. Crossover over a period of time. 5. Moving " +
            "Crossover over a period of time. 6. Performance of the stock.7. " +
            "Back to main menu.---------------------------------------------" +
            "---------Choose an option: Enter the starting date (YYYY-MM-DD):" +
            "Enter the ending date (YYYY-MM-DD):You may buy Alphabet " +
            "as it has gained: +$61.449997O" +
            "perations on stock ALPHABET------------------------------------" +
            "------------------1. Daily stock price change.2. Stock price change " +
            "over a period of time. 3. X - Day Moving Average. 4. Crossover over" +
            " a period of time. 5. Moving Crossover over a period of time. 6. " +
            "Performance of the stock.7. Back to main menu.----------------------" +
            "--------------------------------Choose an option: 1. Create a portfolio.2. " +
            "View a portfolio.3. Import your file.4. Stock Trend Analysis.5. " +
            "List all portfolios.6. Exit.Choose an option: Exiting the" +
            " application...", outputString.toString());
  }

  @Test
  public void testGainLossPeriodUnavailableDate() {
    ByteArrayOutputStream bytes = generateInputString("4\nAlphabet\n2\n" +
            "2023-01-01\n2024-03-25\n7\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION" +
            " ------1. Create a portfolio.2. View a portfolio.3. Import your file" +
            ".4. Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option: " +
            "Enter the name of the company: Operations on stock ALPHABET--------------" +
            "----------------------------------------1. Daily stock price change.2. " +
            "Stock price change over a period of time. 3. X - Day Moving Average. 4. " +
            "Crossover over a period of time. 5. Moving Crossover over a period of " +
            "time. 6. Performance of the stock.7. Back to main menu.-----------------" +
            "-------------------------------------Choose an option: Enter the starting " +
            "date (YYYY-MM-DD):Enter the ending date (YYYY-MM-DD):Share not found " +
            "on this 2023-01-01Operations on stock ALPHABET------------------" +
            "------------------------------------1. Daily stock price change.2" +
            ". Stock price change over a period of time. 3. X - Day Moving Average." +
            " 4. Crossover over a period of time. 5. Moving Crossover over a period " +
            "of time. 6. Performance of the stock.7. Back to main menu.------------" +
            "------------------------------------------Choose an option: 1. Create a " +
            "portfolio.2. View a portfolio.3. Import your file.4. Stock Trend" +
            " Analysis.5. List all portfolios.6. Exit.Choose an option: Exiting the " +
            "application...", outputString.toString());
  }

  @Test
  public void testLoseGainPeriodStartDateGreaterEndDate() {
    ByteArrayOutputStream bytes = generateInputString("4\nAlphabet\n" +
            "2\n2024-03-25\n2024-03-18\n7\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT " +
            "APPLICATION ------1. Create a portfolio.2. View a portfolio.3. " +
            "Import your file.4. Stock Trend Analysis.5. List all portfolios.6." +
            " Exit.Choose an option: Enter the name of the company: Operations on " +
            "stock ALPHABET-----------------------------------------------------" +
            "-1. Daily stock price change.2. Stock price change over a period of " +
            "time. 3. X - Day Moving Average. 4. Crossover over a period of time. " +
            "5. Moving Crossover over a period of time. 6. Performance of the stock." +
            "7. Back to main menu.-----------------------------------------------" +
            "-------Choose an option: Enter the starting date (YYYY-MM-DD):" +
            "Enter the ending date (YYYY-MM-DD):Start date cannot be greater then " +
            "End dateOperations on stock ALPHABET--------------------------------" +
            "----------------------1. Daily stock price change.2. Stock price " +
            "change over a period of time. 3. X - Day Moving Average. 4. Crossover " +
            "over a period of time. 5. Moving Crossover over a period of time. 6. " +
            "Performance of the stock.7. Back to main menu.-----------------------" +
            "-------------------------------Choose an option: 1. Create a portfolio." +
            "2. View a portfolio.3. Import your file.4. Stock Trend Analysis.5. " +
            "List all portfolios.6. Exit.Choose an option: Exiting the " +
            "application...", outputString.toString());
  }

  @Test
  public void testXDayMovingAverage() {
    ByteArrayOutputStream bytes = generateInputString("4\nAlphabet\n3\n" +
            "2024-03-25\n100\n7\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT" +
            " APPLICATION ------1. Create a portfolio.2. View a portfolio." +
            "3. Import your file.4. Stock Trend Analysis.5. List all " +
            "portfolios.6. Exit.Choose an option: Enter the name of the company: " +
            "Operations on stock ALPHABET--------------------------------" +
            "----------------------1. Daily stock price change.2. Stock price " +
            "change over a period of time. 3. X - Day Moving Average. 4. Crossover " +
            "over a period of time. 5. Moving Crossover over a period of time. 6. " +
            "Performance of the stock.7. Back to main menu.-----------------------" +
            "-------------------------------Choose an option: Enter the date " +
            "(YYYY-MM-DD):Enter the 'X' day: 100-Day moving average for Alphabet " +
            "is $140.4472Operations on stock ALPHABET-----------------------------------" +
            "-------------------1. Daily stock price change.2. Stock price change" +
            " over a period of time. 3. X - Day Moving Average. 4. Crossover over" +
            " a period of time. 5. Moving Crossover over a period of time. 6. " +
            "Performance of the stock.7. Back to main menu.---------------------" +
            "---------------------------------Choose an option: 1. Create a portfolio." +
            "2. View a portfolio.3. Import your file.4. Stock Trend Analysis.5. " +
            "List all portfolios.6. Exit.Choose an option: Exiting the " +
            "application...", outputString.toString());
  }

  @Test
  public void testXDayMovingAverageXNegative() {
    ByteArrayOutputStream bytes = generateInputString("4\nAlphabet\n3\n" +
            "2024-03-25\n-100\n7\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT " +
            "APPLICATION ------1. Create a portfolio.2. View a portfolio.3. " +
            "Import your file.4. Stock Trend Analysis.5. List all portfolios." +
            "6. Exit.Choose an option: Enter the name of the company: " +
            "Operations on stock ALPHABET-----------------------------------" +
            "-------------------1. Daily stock price change.2. Stock price " +
            "change over a period of time. 3. X - Day Moving Average. 4. Crossover " +
            "over a period of time. 5. Moving Crossover over a period of time. 6. " +
            "Performance of the stock.7. Back to main menu.-----------------------" +
            "-------------------------------Choose an option: Enter the date" +
            " (YYYY-MM-DD):Enter the 'X' day: Please enter a valid day.Operations " +
            "on stock ALPHABET------------------------------------------------" +
            "------1. Daily stock price change.2. Stock price change over " +
            "a period of time. 3. X - Day Moving Average. 4. Crossover over " +
            "a period of time. 5. Moving Crossover over a period of time. 6. " +
            "Performance of the stock.7. Back to main menu.----------------" +
            "--------------------------------------Choose an option: 1. Create a" +
            " portfolio.2. View a portfolio.3. Import your file.4. Stock Trend " +
            "Analysis.5. List all portfolios.6. Exit.Choose an option: Exiting " +
            "the application...", outputString.toString());
  }

  @Test
  public void testCrossover() {
    ByteArrayOutputStream bytes = generateInputString("4\nAlphabet\n4\n" +
            "2023-01-03\n2024-03-25\n7\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION" +
            " ------1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option:" +
            " Enter the name of the company: Operations on stock ALPHABET------" +
            "------------------------------------------------1. Daily stock price" +
            " change.2. Stock price change over a period of time. 3. X - Day Moving" +
            " Average. 4. Crossover over a period of time. 5. Moving Crossover over " +
            "a period of time. 6. Performance of the stock.7. Back to main menu.------" +
            "------------------------------------------------Choose an option: " +
            "Enter the starting date (YYYY-MM-DD):Enter the ending date (YYYY-MM-DD):" +
            "Positive Crossover Dates: " +
            "[2023-11-14, 2024-03-14, 2023-12-07, 2023-10-02, 2023-10-04, " +
            "2023-04-27, 2023-05-05, 2023-02-15, 2023-03-15, 2023-06-22, " +
            "2023-12-18, 2024-02-05, 2023-07-13, 2023-07-25]" +
            "You can buy on this dates.Negative Crossover Dates: " +
            "[2023-12-01, 2023-12-12, 2023-12-04, 2023-10-03, 2023-09-21, " +
            "2023-05-04, 2023-10-25, 2023-02-16, 2023-06-21, 2023-01-12, " +
            "2023-06-23, 2024-02-15, 2024-01-31, 2023-02-09, 2023-07-20]" +
            "You can sell on this dates.Operations on stock ALPHABET-------" +
            "-----------------------------------------------1. Daily stock " +
            "price change.2. Stock price change over a period of time. 3. X - " +
            "Day Moving Average. 4. Crossover over a period of time. 5. Moving " +
            "Crossover over a period of time. 6. Performance of the stock.7. Back " +
            "to main menu.---------------------------------------------------" +
            "---Choose an option: 1. Create a portfolio.2. View a portfolio.3. " +
            "Import your file.4. Stock Trend Analysis.5. List all portfolios.6." +
            " Exit.Choose an option: Exiting the application...", outputString, toString());
  }

  @Test
  public void testCrossoverUnavailableDate() {
    ByteArrayOutputStream bytes = generateInputString("4\nAlphabet\n4\n2023-01-01" +
            "\n2024-03-25\n7\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file.4." +
            " Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option: " +
            "Enter the name of the company: Operations on stock ALPHABET------------" +
            "------------------------------------------1. Daily stock price change.2. " +
            "Stock price change over a period of time. 3. X - Day Moving Average. 4. " +
            "Crossover over a period of time. 5. Moving Crossover over a period of time." +
            " 6. Performance of the stock.7. Back to main menu.-----------------------" +
            "-------------------------------Choose an option: Enter the starting date " +
            "(YYYY-MM-DD):Enter the ending date (YYYY-MM-DD):Share not found on this " +
            "2023-01-01Operations on stock ALPHABET----------------------------------" +
            "--------------------1. Daily stock price change.2. Stock price change " +
            "over a period of time. 3. X - Day Moving Average. 4. Crossover over a " +
            "period of time. 5. Moving Crossover over a period of time. 6. Performance " +
            "of the stock.7. Back to main menu.--------------------------------------" +
            "----------------Choose an option: 1. Create a portfolio.2. " +
            "View a portfolio.3. Import your file.4. Stock Trend Analysis.5. List all " +
            "portfolios.6. Exit.Choose an option: Exiting the application." +
            "..", outputString.toString());
  }

  @Test
  public void testMovingCrossover() {
    ByteArrayOutputStream bytes = generateInputString("4\nAlphabet\n5\n2023-01-03" +
            "\n30\n2024-03-25\n100\n7\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT " +
            "APPLICATION ------1. Create a portfolio.2. View a portfolio.3. " +
            "Import your file.4. Stock Trend Analysis.5. List all portfolios.6." +
            " Exit.Choose an option: Enter the name of the company: Operations on" +
            " stock ALPHABET----------------------------------------------------" +
            "--1. Daily stock price change.2. Stock price change over a period of " +
            "time. 3. X - Day Moving Average. 4. Crossover over a period of time. " +
            "5. Moving Crossover over a period of time. 6. Performance of the stock" +
            ".7. Back to main menu.------------------------------------------------" +
            "------Choose an option: Enter the starting date (YYYY-MM-DD):Enter the" +
            " 'X' day: Enter the ending date (YYYY-MM-DD):Enter the 'Y' day: " +
            "Positive Moving Crossover Dates: [2023-12-08, 2023-02-13]You can buy on " +
            "this dates.Negative Moving Crossover Dates: [2023-12-06]You can sell on" +
            " this dates.Operations on stock ALPHABET------------------------------" +
            "------------------------1. Daily stock price change.2. Stock price change" +
            " over a period of time. 3. X - Day Moving Average. 4. Crossover over a" +
            " period of time. 5. Moving Crossover over a period of time. 6. Performance" +
            " of the stock.7. Back to main menu.--------------------------------------" +
            "----------------Choose an option: 1. Create a portfolio.2. View a portfolio." +
            "3. Import your file.4. Stock Trend Analysis.5. List all portfolios.6. " +
            "Exit.Choose an option: Exiting the application...", outputString.toString());
  }

  @Test
  public void testMovingCrossoverNegativeXAndY() {
    ByteArrayOutputStream bytes = generateInputString("4\nAlphabet\n5\n2023-01-03\n" +
            "-30\n2024-03-25\n-100\n7\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file.4. " +
            "Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option: " +
            "Enter the name of the company: Operations on stock ALPHABET------------" +
            "------------------------------------------1. Daily stock price change.2." +
            " Stock price change over a period of time. 3. X - Day Moving Average. 4. " +
            "Crossover over a period of time. 5. Moving Crossover over a period of time." +
            " 6. Performance of the stock.7. Back to main menu.--------------------------" +
            "----------------------------Choose an option: Enter the starting date " +
            "(YYYY-MM-DD):Enter the 'X' day: Enter the ending date (YYYY-MM-DD):" +
            "Enter the 'Y' day: Please enter a valid day.Operations on stock ALPHABET" +
            "------------------------------------------------------1. Daily stock" +
            " price change.2. Stock price change over a period of time. 3. X - Day Moving" +
            " Average. 4. Crossover over a period of time. 5. Moving Crossover over a " +
            "period of time. 6. Performance of the stock.7. Back to main menu.----------" +
            "--------------------------------------------Choose an option: 1. Create a" +
            " portfolio.2. View a portfolio.3. Import your file.4. Stock Trend Analysis.5. " +
            "List all portfolios.6. Exit.Choose an option: Exiting the applicati" +
            "on...", outputString.toString());
  }

  @Test
  public void performanceOfFlexiblePortfolioDay() {
    ByteArrayOutputStream bytes = generateInputString("2\n2\nfp1\n7\n" +
            "2020-03-08\n2020-03-30\n9\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------" +
            "1. Create a portfolio.2. View a portfolio.3. Import your file.4. Stock" +
            " Trend Analysis.5. List all portfolios.6. Exit.Choose an option: 1. " +
            "Normal Portfolio.2. Flexible Portfolio.Choose an option: Enter your " +
            "portfolio name: Portfolio fp1 loaded successfully.1. View all stocks." +
            "2. Buy a particular stock.3. Sell a particular stock.4. Get the" +
            " total value of portfolio.5. Get the total composition of portfolio." +
            "6. Get the total investment of portfolio.7. View performance of " +
            "portfolio.8. Save the portfolio.9. Back to main menu.Choose an option: " +
            "Enter the start date (YYYY-MM-DD): Enter the end date (YYYY-MM-DD): " +
            "Performance of portfolio fp1 from 2020-03-08 to 2020-03-30" +
            "09 Mar 2020: ************************************" +
            "10 Mar 2020: **************************************" +
            "11 Mar 2020: ************************************" +
            "12 Mar 2020: *********************************" +
            "13 Mar 2020: ************************************" +
            "16 Mar 2020: ********************************" +
            "17 Mar 2020: *********************************" +
            "18 Mar 2020: *********************************" +
            "19 Mar 2020: *********************************" +
            "20 Mar 2020: ********************************" +
            "23 Mar 2020: ********************************" +
            "24 Mar 2020: **********************************" +
            "25 Mar 2020: *********************************" +
            "26 Mar 2020: ***********************************" +
            "27 Mar 2020: *********************************" +
            "Scale: * = 12541" +
            ". View all stocks.2. " +
            "Buy a particular stock.3. Sell a " +
            "particular stock.4. Get the total value of " +
            "portfolio.5. Get the total composition of portfolio" +
            ".6. Get the total investment of portfolio.7. View performance " +
            "of portfolio.8. Save the portfolio.9. Back to main menu.Choose an " +
            "option: 1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option: " +
            "Exiting the application...", outputString.toString());
  }

  @Test
  public void performanceOfFlexiblePortfolioMonth() {
    ByteArrayOutputStream bytes = generateInputString("2\n2\nfp1\n7\n" +
            "2020-03-08\n2021-12-31\n9\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file" +
            ".4. Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an " +
            "option: 1. Normal Portfolio.2. Flexible Portfolio.Choose an option: " +
            "Enter your portfolio name: Portfolio fp1 loaded successfully.1. View all " +
            "stocks.2. Buy a particular stock.3. Sell a particular stock.4. Get the" +
            " total value of portfolio.5. Get the total composition of portfolio.6." +
            " Get the total investment of portfolio.7. View performance of portfolio" +
            ".8. Save the portfolio.9. Back to main menu.Choose an option: Enter the" +
            " start date (YYYY-MM-DD): Enter the end date (YYYY-MM-DD): Performance o" +
            "f portfolio fp1 from 2020-03-08 to 2021-12-31" +
            "31 Mar 2020: ****************" +
            "23 Apr 2020: *****************" +
            "08 Jun 2020: *******************" +
            "01 Jul 2020: *******************" +
            "24 Jul 2020: ********************" +
            "08 Sep 2020: *********************" +
            "01 Oct 2020: ********************" +
            "16 Nov 2020: ************************" +
            "09 Dec 2020: ************************" +
            "16 Feb 2021: *****************************" +
            "11 Mar 2021: *****************************" +
            "26 Apr 2021: ********************************" +
            "19 May 2021: *******************************" +
            "11 Jun 2021: **********************************" +
            "27 Jul 2021: *************************************" +
            "19 Aug 2021: *************************************" +
            "04 Oct 2021: ************************************" +
            "27 Oct 2021: ****************************************" +
            "19 Nov 2021: *****************************************" +
            "Scale: * = 2761" +
            "1. View all stocks.2. Buy a particular stock.3. Sell a particular stock." +
            "4. Get the total value of portfolio.5. Get the total composition of " +
            "portfolio.6. Get the total investment of portfolio.7. View performance " +
            "of portfolio.8. Save the portfolio.9. Back to main menu.Choose an option:" +
            " 1. Create a portfolio.2. View a portfolio.3. Import your file.4. Stock " +
            "Trend Analysis.5. List all portfolios.6. Exit.Choose an option: " +
            "Exiting the application...", outputString.toString());
  }

  @Test
  public void performanceOfFlexiblePortfolioYear() {
    ByteArrayOutputStream bytes = generateInputString("2\n2\nfp1\n7\n" +
            "2020-03-08\n2024-03-08\n9\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file.4. " +
            "Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option: " +
            "1. Normal Portfolio.2. Flexible Portfolio.Choose an option: Enter your " +
            "portfolio name: Portfolio fp1 loaded successfully.1. View all stocks.2." +
            " Buy a particular stock.3. Sell a particular stock.4. Get the total " +
            "value of portfolio.5. Get the total composition of portfolio.6. Get the " +
            "total investment of portfolio.7. View performance of portfolio.8. Save " +
            "the portfolio.9. Back to main menu.Choose an option: Enter the start date" +
            " (YYYY-MM-DD): Enter the end date (YYYY-MM-DD): " +
            "Performance of portfolio fp1 from 2020-03-08 to 2024-03-08" +
            "Mar 2020: ***********" +
            "May 2020: **************" +
            "Jul 2020: ***************" +
            "Sep 2020: ***************" +
            "Nov 2020: ******************" +
            "Jan 2021: ******************" +
            "Mar 2021: ********************" +
            "May 2021: *************************" +
            "Jul 2021: ****************************" +
            "Sep 2021: *****************************" +
            "Nov 2021: ******************************" +
            "Jan 2022: **************************" +
            "Mar 2022: *****************************" +
            "May 2022: *********************" +
            "Jul 2022: *" +
            "Sep 2022: *" +
            "Nov 2022: *" +
            "Jan 2023: *" +
            "Mar 2023: *" +
            "May 2023: *" +
            "Jul 2023: *" +
            "Sep 2023: *" +
            "Nov 2023: *" +
            "Jan 2024: *" +
            "Scale: * = 3698" +
            "1. View all stocks." +
            "2. Buy a particular stock." +
            "3. Sell a particular stock." +
            "4. Get the total value of portfolio" +
            ".5. Get the total composition of portfolio" +
            ".6. Get the total investment of portfolio." +
            "7. View performance of portfolio.8. Save the portfolio." +
            "9. Back to main menu.Choose an option: 1. Create a portfolio." +
            "2. View a portfolio.3. Import your file.4. Stock Trend Analysis" +
            ".5. List all portfolios.6. Exit.Choose an option: " +
            "Exiting the application...", outputString.toString());
  }

  @Test
  public void performanceOfFlexiblePortfolioDateValidation() {
    ByteArrayOutputStream bytes = generateInputString("2\n2\nfp1\n7\n" +
            "2024-03-08\n2020-03-08\n9\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file.4. " +
            "Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option:" +
            " 1. Normal Portfolio.2. Flexible Portfolio.Choose an option: Enter your " +
            "portfolio name: Portfolio fp1 loaded successfully.1. View all stocks.2." +
            " Buy a particular stock.3. Sell a particular stock.4. Get the total value" +
            " of portfolio.5. Get the total composition of portfolio.6. Get the total " +
            "investment of portfolio.7. View performance of portfolio.8. Save the portfolio" +
            ".9. Back to main menu.Choose an option: Enter the start date (YYYY-MM-DD): " +
            "Enter the end date (YYYY-MM-DD): Provide valid period.1. View all stocks.2" +
            ". Buy a particular stock.3. Sell a particular stock.4. Get the total value" +
            " of portfolio.5. Get the total composition of portfolio.6. Get the total " +
            "investment of portfolio.7. View performance of portfolio.8. Save the portfolio." +
            "9. Back to main menu.Choose an option: 1. Create a portfolio.2. View a " +
            "portfolio.3. Import your file.4. Stock Trend Analysis.5. List all portfolios." +
            "6. Exit.Choose an option: Exiting the application...", outputString.toString());
  }

  @Test
  public void performanceOfInFlexiblePortfolioDay() {
    ByteArrayOutputStream bytes = generateInputString("2\n1\np45\n3\n" +
            "2020-03-08\n2020-03-30\n4\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION" +
            " ------1. Create a portfolio.2. View a portfolio.3. Import your file." +
            "4. Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option: " +
            "1. Normal Portfolio.2. Flexible Portfolio.Choose an option: Enter the " +
            "name of the portfolio: Portfolio p45 loaded successfully.1. Total Composition " +
            "of the portfolio.2. Total Value of the portfolio.3. Performance of the " +
            "portfolio.4. Back to menu.Choose an option: Enter the start date (YYYY-MM-DD): " +
            "Enter the end date (YYYY-MM-DD): " +
            "Performance of portfolio p45 from 2020-03-08 to 2020-03-30" +
            "09 Mar 2020: ************************************10 Mar 2020: " +
            "**************************************11 Mar 2020: " +
            "************************************12 Mar 2020: *********************************" +
            "13 Mar 2020: ************************************16 Mar 2020: " +
            "********************************17 Mar 2020: *********************************" +
            "18 Mar 2020: *********************************19 Mar 2020: " +
            "*********************************20 Mar 2020: ********************************" +
            "23 Mar 2020: ********************************24 Mar 2020: " +
            "**********************************25 Mar 2020: *********************************" +
            "26 Mar 2020: ***********************************27 Mar 2020: " +
            "*********************************Scale: * = 6601. " +
            "Total Composition of the portfolio.2. Total Value of the portfolio.3. " +
            "Performance of the portfolio.4. Back to menu.Choose an option: 1. Create " +
            "a portfolio.2. View a portfolio.3. Import your file.4. Stock " +
            "Trend Analysis.5. List all portfolios.6. Exit.Choose an option: Exiting " +
            "the application...", outputString.toString());
  }

  @Test
  public void performanceOfInFlexiblePortfolioMonth() {
    ByteArrayOutputStream bytes = generateInputString("2\n1\np45\n3\n" +
            "2020-03-08\n2021-12-31\n4\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION" +
            " ------1. Create a portfolio.2. View a portfolio.3. Import your file" +
            ".4. Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option:" +
            " 1. Normal Portfolio.2. Flexible Portfolio.Choose an option:" +
            " Enter the name of the portfolio: Portfolio p45 loaded successfully." +
            "1. Total Composition of the portfolio.2. Total Value of the portfolio." +
            "3. Performance of the portfolio.4. Back to menu.Choose an option:" +
            " Enter the start date (YYYY-MM-DD): Enter the end date (YYYY-MM-DD): " +
            "Performance of portfolio p45 from 2020-03-08 to 2021-12-3131 Mar 2020: " +
            "****************23 Apr 2020: *****************08 Jun 2020:" +
            " *******************01 Jul 2020: *******************24 Jul 2020:" +
            " ********************08 Sep 2020: *********************01 Oct 2020:" +
            " ********************16 Nov 2020: ************************09 Dec 2020:" +
            " ************************16 Feb 2021: *****************************" +
            "11 Mar 2021: *****************************26 Apr 2021:" +
            " ********************************19 May 2021: *******************************" +
            "11 Jun 2021: **********************************27 Jul 2021: " +
            "*************************************19 Aug 2021:" +
            " *************************************04 Oct 2021: " +
            "************************************27 Oct 2021: " +
            "****************************************19 Nov 2021: " +
            "*****************************************Scale: * = 14531." +
            " Total Composition of the portfolio.2. Total Value of the portfolio." +
            "3. Performance of the portfolio.4. Back to menu.Choose an option: " +
            "1. Create a portfolio.2. View a portfolio.3. Import your file.4." +
            " Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option:" +
            " Exiting the application...", outputString.toString());
  }

  @Test
  public void performanceOfStockDay() {
    ByteArrayOutputStream bytes = generateInputString("4\nAlphabet\n6\n2020-03-08" +
            "\n2020-03-30\n7\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file.4." +
            " Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option:" +
            " Enter the name of the company: Operations on stock ALPHABET-----------" +
            "-------------------------------------------1. Daily stock price change.2." +
            " Stock price change over a period of time. 3. X - Day Moving Average. 4. " +
            "Crossover over a period of time. 5. Moving Crossover over a period of time." +
            " 6. Performance of the stock.7. Back to main menu.-------------------------" +
            "-----------------------------Choose an option: Enter the start date (YYYY-MM-DD):" +
            " Enter the end date (YYYY-MM-DD):" +
            " Performance of stock Alphabet from 2020-03-08 to 2020-03-30" +
            "09 Mar 2020: ************************************" +
            "10 Mar 2020: **************************************" +
            "11 Mar 2020: ************************************" +
            "12 Mar 2020: *********************************" +
            "13 Mar 2020: ************************************" +
            "16 Mar 2020: ********************************17 Mar 2020:" +
            " *********************************18 Mar 2020: " +
            "*********************************19 Mar 2020:" +
            " *********************************20 Mar 2020: " +
            "********************************23 Mar 2020:" +
            " ********************************24 Mar 2020:" +
            " **********************************25 Mar 2020:" +
            " *********************************26 Mar 2020:" +
            " ***********************************27 Mar 2020:" +
            " *********************************Scale: * = 33" +
            "Operations on stock ALPHABET----------------------" +
            "--------------------------------1. Daily stock price change" +
            ".2. Stock price change over a period of time. 3. " +
            "X - Day Moving Average. 4. Crossover over a period of time. " +
            "5. Moving Crossover over a period of time." +
            " 6. Performance of the stock.7. Back to main menu." +
            "------------------------------------------------------" +
            "Choose an option: 1. Create a portfolio.2. View a portfolio." +
            "3. Import your file.4. Stock Trend Analysis.5. " +
            "List all portfolios.6. Exit.Choose an option: " +
            "Exiting the application...", outputString.toString());
  }

  @Test
  public void performanceOfStockMonth() {
    ByteArrayOutputStream bytes = generateInputString("4\nAlphabet\n6\n" +
            "2020-03-08\n2021-12-31\n7\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file" +
            ".4. Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option: " +
            "Enter the name of the company: Operations on stock ALPHABET-------" +
            "-----------------------------------------------1. Daily stock price change." +
            "2. Stock price change over a period of time. 3. X - Day Moving Average. " +
            "4. Crossover over a period of time. 5. Moving Crossover over a period of " +
            "time. 6. Performance of the stock.7. Back to main menu.------------------" +
            "------------------------------------Choose an option: Enter the start date " +
            "(YYYY-MM-DD): Enter the end date (YYYY-MM-DD): " +
            "Performance of stock Alphabet from 2020-03-08 to 2021-12-31" +
            "31 Mar 2020: ****************23 Apr 2020: " +
            "*****************08 Jun 2020: ********************" +
            "01 Jul 2020: *******************24 Jul 2020: " +
            "********************08 Sep 2020: *********************" +
            "01 Oct 2020: ********************16 Nov 2020: " +
            "************************09 Dec 2020: ************************" +
            "16 Feb 2021: *****************************11 Mar 2021:" +
            " *****************************26 Apr 2021: ********************************" +
            "19 May 2021: ********************************11 Jun 2021: " +
            "**********************************27 Jul 2021: " +
            "*************************************19 Aug 2021: " +
            "**************************************04 Oct 2021: " +
            "*************************************27 Oct 2021: " +
            "****************************************19 Nov 2021: " +
            "*****************************************Scale: * = 72" +
            "Operations on stock ALPHABET--------------------------------" +
            "----------------------1. Daily stock price change.2. Stock price" +
            " change over a period of time. 3. X - Day Moving Average. 4. Crossover" +
            " over a period of time. 5. Moving Crossover over a period of time." +
            " 6. Performance of the stock.7. Back to main menu.------------------" +
            "------------------------------------Choose an option: 1. Create a" +
            " portfolio.2. View a portfolio.3. Import your file.4. Stock Trend Analysis" +
            ".5. List all portfolios.6. Exit.Choose an option: Exiting the" +
            " application...", outputString.toString());
  }

  @Test
  public void performanceOfStockYear() {
    ByteArrayOutputStream bytes = generateInputString("4\nAlphabet\n6\n2020-03-08" +
            "\n2024-03-08\n7\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file.4. " +
            "Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option: " +
            "Enter the name of the company: Operations on stock ALPHABET------------" +
            "------------------------------------------1. Daily stock price change.2." +
            " Stock price change over a period of time. 3. X - Day Moving Average. 4." +
            " Crossover over a period of time. 5. Moving Crossover over a period of time." +
            " 6. Performance of the stock.7. Back to main menu.------------------------" +
            "------------------------------Choose an option: Enter the start date " +
            "(YYYY-MM-DD): Enter the end date (YYYY-MM-DD): Performance of stock Alphabet" +
            " from 2020-03-08 to 2024-03-08Mar 2020: ***********May 2020: " +
            "**************Jul 2020: ***************Sep 2020: ***************" +
            "Nov 2020: ******************Jan 2021: ******************Mar 2021: " +
            "********************May 2021: *************************Jul 2021: " +
            "****************************Sep 2021: *****************************" +
            "Nov 2021: ******************************Jan 2022: **************************" +
            "Mar 2022: *****************************May 2022: *********************" +
            "Jul 2022: *Sep 2022: *Nov 2022: *Jan 2023: *Mar 2023: *May 2023: *Jul 2023:" +
            " *Sep 2023: *Nov 2023: *Jan 2024: *Scale: * = 97" +
            "Operations on stock ALPHABET-------------------------------" +
            "-----------------------1. Daily stock price change.2. Stock price " +
            "change over a period of time. 3. X - Day Moving Average. 4. Crossover " +
            "over a period of time. 5. Moving Crossover over a period of time. 6." +
            " Performance of the stock.7. Back to main menu.-----------------------" +
            "-------------------------------Choose an option: 1. Create a portfolio.2." +
            " View a portfolio.3. Import your file.4. Stock Trend Analysis.5. List " +
            "all portfolios.6. Exit.Choose an option: Exiting the " +
            "application...", outputString.toString());
  }


  @Test
  public void performanceOfStockStartDateValidation() {
    ByteArrayOutputStream bytes = generateInputString("4\nAlphabet\n6\n2024-03-08\n" +
            "2020-03-08\n7\n6\n7\n6");
    formatBytes(bytes.toString());
    assertEquals("------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION " +
            "------1. Create a portfolio.2. View a portfolio.3. Import your file.4. " +
            "Stock Trend Analysis.5. List all portfolios.6. Exit.Choose an option:" +
            " Enter the name of the company: Operations on stock ALPHABET----------" +
            "--------------------------------------------1. Daily stock price change." +
            "2. Stock price change over a period of time. 3. X - Day Moving Average." +
            " 4. Crossover over a period of time. 5. Moving Crossover over a period of" +
            " time. 6. Performance of the stock.7. Back to main menu.----------------" +
            "--------------------------------------Choose an option: Enter the start date" +
            " (YYYY-MM-DD): Enter the end date (YYYY-MM-DD): Provide valid period." +
            "Operations on stock ALPHABET-----------------------------------------" +
            "-------------1. Daily stock price change.2. Stock price change over a " +
            "period of time. 3. X - Day Moving Average. 4. Crossover over a period " +
            "of time. 5. Moving Crossover over a period of time. 6. Performance of " +
            "the stock.7. Back to main menu.----------------------------------------" +
            "--------------Choose an option: 1. Create a portfolio.2. View a portfolio." +
            "3. Import your file.4. Stock Trend Analysis.5. List all portfolios.6. " +
            "Exit.Choose an option: Exiting the application...", outputString.toString());

  }

}