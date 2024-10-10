import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;

import controller.PortfolioController;
import controller.PortfolioControllerImpl;
import model.ManagementModel;
import view.ManagementView;
import view.ManagementViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test class for Portfolio Controller class.
 */
public class PortfolioControllerTest {

  private StringBuilder outputString;

  private void generateInputString(String sequence) {
    try {
      outputString = new StringBuilder();
      InputStream in = new ByteArrayInputStream(sequence.getBytes());
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      PrintStream out = new PrintStream(bytes);
      ManagementView portfolioView = new ManagementViewImpl(in, out);
      ManagementModel portfolioManagementModel = new ManagementMockModel(outputString);
      PortfolioController portfolioController =
              new PortfolioControllerImpl(portfolioManagementModel, portfolioView);
      portfolioController.run();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  @Test
  public void testAddStock() {
    generateInputString("1\n1\np5\n2\nAlphabet\n40\n4\n6\n");
    File f = new File("portfolios/inflexible/p5.csv");
    assertFalse(f.exists());
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p5\n" +
            "Reached add stock\n" +
            "40\n" +
            "Reached total composition", outputString.toString());
  }

  @Test
  public void testSavePortfolio() {
    generateInputString("1\n1\np5\n2\nAlphabet\n40\n3\n4\n6\n");
    File f1 = new File("portfolios/inflexible/p5.csv");
    assertFalse(f1.exists());
    assertEquals("\nReached create portfolio\n" +
            "p5\n" +
            "Reached total composition of portfolio\n", outputString.toString());
  }

  @Test
  public void testViewPortfolio() {
    generateInputString("2\n1\np6\n4\n6\n");
    File f = new File("portfolios/inflexible/p6.csv");
    assertTrue(f.exists());
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p6", outputString.toString());
  }

  @Test
  public void testImportFile() {
    generateInputString("3\n1\np6\n4\n6\n");
    File f = new File("portfolios/inflexible/p6.csv");
    assertTrue(f.exists());
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p6", outputString.toString());
  }

  @Test
  public void testListAllPortfolios() {
    generateInputString("5\n6\n");
    assertEquals("", outputString.toString());
  }

  @Test
  public void testViewAllStocks() {
    generateInputString("1\n1\np66\n1\nAlp\n2\nAlphabet\n40\n3\n6\n");
    File f = new File("portfolios/inflexible/p66.csv");
    assertFalse(f.exists());
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p66\n" +
            "Reached total composition of portfolio\n", outputString.toString());
  }

  @Test
  public void testViewPortfolioGetValue() {
    generateInputString("2\n1\np6\n2\n2024-03-11\n4\n6\n");
    File f = new File("portfolios/inflexible/p6.csv");
    assertTrue(f.exists());
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p6\n" +
            "Reached total composition of portfolio\n", outputString.toString());
  }

  @Test
  public void testViewPortfolioComposition() {
    generateInputString("2\n1\np6\n1\n4\n6\n");
    File f = new File("portfolios/inflexible/p6.csv");
    assertTrue(f.exists());
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p6\n" +
            "Reached total composition of portfolio\n", outputString.toString());
  }

  @Test
  public void testSaveMultiplePortfolio() {
    generateInputString("1\n1\np65\n2\nAlphabet\n40\n3\n1\n1\np68\n2\ngoldman\n40\n3\n6");
    File f1 = new File("portfolios/inflexible/p65.csv");
    assertFalse(f1.exists());
    File f2 = new File("portfolios/inflexible/p68.csv");
    assertFalse(f2.exists());
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p65\n" +
            "Reached total composition of portfolio\n" +
            "\n" +
            "Reached create portfolio\n" +
            "p68\n" +
            "Reached total composition of portfolio\n", outputString.toString());
  }

  @Test
  public void testCreateAndSaveMultiplePortfolioWithMultipleStocks() {
    generateInputString("1\n1\np65\n2\nAlphabet\n40\n2\nnasdaq" +
            "\n40\n3\n1\n1\np68\n2\ngoldman\n40\n2\nalphabet\n40\n3\n6");
    File f1 = new File("portfolios/inflexible/p65.csv");
    assertFalse(f1.exists());
    File f2 = new File("portfolios/inflexible/p68.csv");
    assertFalse(f2.exists());
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p65\n" +
            "Reached total composition of portfolio\n" +
            "\n" +
            "Reached create portfolio\n" +
            "p68\n" +
            "Reached total composition of portfolio\n", outputString.toString());

  }

  @Test
  public void testAddStockWithFractionStockQuantity() {
    generateInputString("1\n1\np66\n2\nAlphabet\n23.4\n4\n6\n");
    File f1 = new File("portfolios/inflexible/p66.csv");
    assertFalse(f1.exists());
    assertEquals("", outputString.toString());
  }

  @Test
  public void testInvalidGetValueDate() {
    generateInputString("2\n1\np6\n2\n2024-02-31\n4\n6\n");
    File f1 = new File("portfolios/inflexible/p6.csv");
    assertTrue(f1.exists());
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p6", outputString.toString());
  }

  @Test
  public void testInvalidGetValueDateString() {
    generateInputString("2\n1\np6\n2\najdk279\n4\n6\n");
    File f1 = new File("portfolios/inflexible/p6.csv");
    assertTrue(f1.exists());
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p6", outputString.toString());
  }

  @Test
  public void testInvalidCompanyName() {
    generateInputString("1\n1\np6\n2\nGoogle\n23\n4\n6\n");
    File f1 = new File("portfolios/inflexible/p6.csv");
    assertFalse(f1.exists());
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "Google", outputString.toString());
  }

  @Test
  public void testEmptyPortfolioSave() {
    generateInputString("1\n1\np56\n3\n4\n6\n");
    File f1 = new File("portfolios/inflexible/p56.csv");
    assertFalse(f1.exists());
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p56\n" +
            "Reached total composition of portfolio\n", outputString.toString());
  }

  @Test
  public void testGetValueOnAvailableDateBeforeUnavailable() {
    generateInputString("2\n1\np6\n2\n2024-03-09\n4\n6\n");
    File f1 = new File("portfolios/inflexible/p6.csv");
    assertTrue(f1.exists());
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p6\n" +
            "Reached total composition of portfolio\n", outputString.toString());
  }

  @Test
  public void testGetValueOnAvailableDateBeforeAvailable() {
    generateInputString("2\n1\np6\n2\n2024-03-08\n4\n6\n");
    File f1 = new File("portfolios/inflexible/p6.csv");
    assertTrue(f1.exists());
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p6\n" +
            "Reached total composition of portfolio\n", outputString.toString());
  }

  @Test
  public void testAddStockZeroQuantity() {
    generateInputString("1\n1\np1\n2\nAlphabet\n0\n4\n6\n");
    File f = new File("portfolios/inflexible/p1.csv");
    assertFalse(f.exists());
    assertEquals("", outputString.toString());
  }

  @Test
  public void testAddStockNegativeQuantity() {
    generateInputString("1\n1\np1\n2\nAlphabet\n-34\n4\n6\n");
    File f = new File("portfolios/inflexible/p1.csv");
    assertFalse(f.exists());
    assertEquals("", outputString.toString());
  }

  @Test
  public void testStockGreaterThanVolume() {
    generateInputString("1\n1\np5\n2\nGoldman\n100000\n4\n6\n");
    File f = new File("portfolios/p5.csv");
    assertFalse(f.exists());
    assertEquals("", outputString.toString());
  }

  @Test
  public void testAddMultipleStocks() {
    generateInputString("1\n1\ndummy\n" +
            "2\nGoldman\n10\n" +
            "2\nAlphabet\n10\n" +
            "2\nArmada\n10\n" +
            "2\nTesla\n10\n" +
            "2\nApple\n10\n" +
            "2\nACM\n10\n" +
            "2\nACNB\n10\n" +
            "2\nAscent\n10\n" +
            "2\nAclarion\n10\n" +
            "2\nAcorda\n10\n" +
            "2\nEnact\n10\n" +
            "2\nAdobe\n10\n" +
            "2\nMeta\n10\n" +
            "2\nEdoc\n10\n" +
            "2\nAffinity\n10\n" +
            "2\nAiemi\n10\n" +
            "2\nAcutus\n10\n" +
            "2\nAgile\n10\n" +
            "2\nAgilysys\n10\n" +
            "2\nAinos\n10\n" +
            "2\nCapitol\n10\n" +
            "2\nCeva\n10\n" +
            "2\nCarlyle\n10\n" +
            "2\nFeutune\n10\n" +
            "2\nFulgent\n10\n" +
            "2\nFlora\n10\n" +
            "2\nFluent\n10\n" +
            "2\nFlexShopper\n10\n3\n4" +
            "\n6\n");

    File f = new File("dummy.csv");
    assertFalse(f.exists());
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "dummy\n" +
            "Reached total composition of portfolio\n", outputString.toString());

  }

  //Flexible portfolio tests
  @Test
  public void testBuyStock() {
    generateInputString("1\n2\np4\n2\nAlphabet\n34\n2024-03-25\n7\n4\n6\n");
    File f = new File("portfolios/flexible/p4.csv");
    assertFalse(f.exists());
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "p4\n" +
            "Reached buy\n" +
            "34\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testSellStock() {
    generateInputString("1\n2\np4\n2\nAlphabet\n34\n2024-03-20\n" +
            "3\nAlphabet\n30\n2024-03-25\n7\n4\n6\n");
    File f = new File("portfolios/flexible/p4.csv");
    assertFalse(f.exists());
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "p4\n" +
            "Reached buy\n" +
            "34\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n" +
            "\n" +
            "Reached sell\n" +
            "GOOG\n" +
            "30\n" +
            "2024-03-25\n" +
            "Reached total composition of flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testBuySellFractionAmountShare() {
    generateInputString("1\n2\np4\n2\nAlphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n22.5\n2024-03-25\n7\n4\n6\n");
    File f = new File("portfolios/flexible/p4.csv");
    assertFalse(f.exists());
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "p4\n" +
            "Reached buy\n" +
            "34\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testTotalValue() {
    generateInputString("1\n2\np4\n2\nAlphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n22\n2024-03-25\n4\n2024-03-25\n7\n6\n");
    File f = new File("portfolios/flexible/p4.csv");
    assertFalse(f.exists());
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "p4\n" +
            "Reached buy\n" +
            "34\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n" +
            "\n" +
            "Reached sell\n" +
            "GOOG\n" +
            "22\n" +
            "2024-03-25\n" +
            "Reached total composition of portfolio\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testTotalComposition() {
    generateInputString("1\n2\np4\n2\nAlphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n22\n2024-03-25\n5\n7\n6\n");
    File f = new File("portfolios/flexible/p4.csv");
    assertFalse(f.exists());
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "p4\n" +
            "Reached buy\n" +
            "34\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n" +
            "\n" +
            "Reached sell\n" +
            "GOOG\n" +
            "22\n" +
            "2024-03-25\n" +
            "Reached total composition of flexible portfolio\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testTotalInvestment() {
    generateInputString("1\n2\np4\n2\nAlphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n22\n2024-03-25\n6\n2024-03-25\n7\n6\n");
    File f = new File("portfolios/flexible/p4.csv");
    assertFalse(f.exists());
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "p4\n" +
            "Reached buy\n" +
            "34\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n" +
            "\n" +
            "Reached sell\n" +
            "GOOG\n" +
            "22\n" +
            "2024-03-25\n" +
            "Reached total investment\n" +
            "2024-03-25\n" +
            "Reached total composition of flexible portfolio\n", outputString.toString());
  }

  // load tests
  @Test
  public void testBuyStockAfterLoad() {
    generateInputString("2\n2\np1\n2\nAlphabet\n34\n2024-03-25\n8\n6\n");
    File f = new File("portfolios/flexible/p1.csv");
    assertTrue(f.exists());
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "p1\n" +
            "Reached buy\n" +
            "34\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testSellStockAfterLoad() {
    generateInputString("2\n2\np1\n2\nAlphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n30\n2024-03-25\n8\n6\n");
    File f = new File("portfolios/flexible/p1.csv");
    assertTrue(f.exists());
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "p1\n" +
            "Reached buy\n" +
            "34\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n" +
            "\n" +
            "Reached sell\n" +
            "GOOG\n" +
            "30\n" +
            "2024-03-25\n" +
            "Reached total composition of flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testSellMoreThanBuy() {
    generateInputString("2\n2\np1\n2\nAlphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n54\n2024-03-25\n8\n6\n");
    File f = new File("portfolios/flexible/p1.csv");
    assertTrue(f.exists());
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "p1\n" +
            "Reached buy\n" +
            "34\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n" +
            "\n" +
            "Reached sell\n" +
            "GOOG\n" +
            "54\n" +
            "2024-03-25\n" +
            "Reached total composition of flexible portfolio\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testTotalValueAfterLoad() {
    generateInputString("2\n2\np1\n2\nAlphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n22\n2024-03-25\n4\n2024-03-25\n8\n6\n");
    File f = new File("portfolios/flexible/p1.csv");
    assertTrue(f.exists());
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "p1\n" +
            "Reached buy\n" +
            "34\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n" +
            "\n" +
            "Reached sell\n" +
            "GOOG\n" +
            "22\n" +
            "2024-03-25\n" +
            "Reached total composition of portfolio\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testTotalCompositionAfterLoad() {
    generateInputString("2\n2\np1\n2\nAlphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n22\n2024-03-25\n5\n8\n6\n");
    File f = new File("portfolios/flexible/p1.csv");
    assertTrue(f.exists());
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "p1\n" +
            "Reached buy\n" +
            "34\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n" +
            "\n" +
            "Reached sell\n" +
            "GOOG\n" +
            "22\n" +
            "2024-03-25\n" +
            "Reached total composition of flexible portfolio\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testTotalInvestmentAfterLoad() {
    generateInputString("2\n2\np1\n2\nAlphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n22\n2024-03-25\n6\n2024-03-25\n8\n6\n");
    File f = new File("portfolios/flexible/p1.csv");
    assertTrue(f.exists());
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "p1\n" +
            "Reached buy\n" +
            "34\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n" +
            "\n" +
            "Reached sell\n" +
            "GOOG\n" +
            "22\n" +
            "2024-03-25\n" +
            "Reached total investment\n" +
            "2024-03-25\n" +
            "Reached total composition of flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testTotalValueBeforePortfolioCreated() {
    generateInputString("2\n2\np1\n2\nAlphabet\n34\n2024-03-25\n" +
            "3\nAlphabet\n22\n2024-03-25\n4\n2019-03-25\n8\n6\n");
    File f = new File("portfolios/flexible/p1.csv");
    assertTrue(f.exists());
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "p1\n" +
            "Reached buy\n" +
            "34\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n" +
            "\n" +
            "Reached sell\n" +
            "GOOG\n" +
            "22\n" +
            "2024-03-25\n" +
            "Reached total composition of portfolio\n" +
            "\n" +
            "Reached total composition of flexible portfolio\n", outputString.toString());
  }

  //Stock trend analysis test case
  @Test
  public void testGainOnADay() {
    generateInputString("4\nAlphabet\n1\n2024-03-25");
    assertEquals("\n" +
            "Reached stock analysis today\n", outputString.toString());
  }

  @Test
  public void testLoseOnADay() {
    generateInputString("4\nAlphabet\n1\n2024-03-21");
    assertEquals("\n" +
            "Reached stock analysis today\n", outputString.toString());
  }

  @Test
  public void testGainOnADayOnUnavailableDate() {
    generateInputString("4\nAlphabet\n1\n2024-03-23");
    assertEquals("", outputString.toString());
  }

  @Test
  public void testLoseOnADayUnavailableDate() {
    generateInputString("4\nAlphabet\n1\n2024-03-23");
    assertEquals("", outputString.toString());
  }

  @Test
  public void testGainPeriod() {
    generateInputString("4\nAlphabet\n2\n2023-01-03\n2024-03-25");
    assertEquals("\n" +
            "Reached stock analysis period\n", outputString.toString());
  }

  @Test
  public void testLosePeriod() {
    generateInputString("4\nAlphabet\n2\n2022-01-05\n2024-03-25");
    assertEquals("\n" +
            "Reached stock analysis period\n", outputString.toString());
  }

  @Test
  public void testGainLossPeriodUnavailableDate() {
    generateInputString("4\nAlphabet\n2\n2023-01-01\n2024-03-25");
    assertEquals("", outputString.toString());
  }

  @Test
  public void testLoseGainPeriodStartDateGreaterEndDate() {
    generateInputString("4\nAlphabet\n2\n2024-03-25\n2024-03-18");
    assertEquals("", outputString.toString());
  }

  @Test
  public void testXDayMovingAverage() {
    generateInputString("4\nAlphabet\n3\n2024-03-25\n100");
    assertEquals("\n" +
            "Reached x day moving average\n", outputString.toString());
  }

  @Test
  public void testXDayMovingAverageUnavailableDate() {
    generateInputString("4\nAlphabet\n3\n2024-03-23\n100");
    assertEquals("", outputString.toString());
  }

  @Test
  public void testXDayMovingAverageXNegative() {
    generateInputString("4\nAlphabet\n3\n2024-03-25\n-100");
    assertEquals("", outputString.toString());
  }

  @Test
  public void testCrossover() {
    generateInputString("4\nAlphabet\n4\n2023-01-03\n2024-03-25");
    assertEquals("\n" +
            "Reached crossover\n", outputString.toString());
  }

  @Test
  public void testCrossoverStartDateGreaterThanEndDate() {
    generateInputString("4\nAlphabet\n4\n2024-03-25\n2023-03-01");
    assertEquals("", outputString.toString());
  }

  @Test
  public void testCrossoverUnavailableDate() {
    generateInputString("4\nAlphabet\n4\n2023-01-01\n2024-03-25");
    assertEquals("", outputString.toString());
  }

  @Test
  public void testMovingCrossover() {
    generateInputString("4\nAlphabet\n5\n2023-01-03\n30\n2024-03-25\n100");
    assertEquals("\n" +
            "Reached moving crossover\n", outputString.toString());
  }

  @Test
  public void testMovingCrossoverStartDateGreaterThanEndDate() {
    generateInputString("4\nAlphabet\n5\n2024-03-25\n30\n2023-03-01\n100");
    assertEquals("", outputString.toString());
  }

  @Test
  public void testMovingCrossoverUnavailableDate() {
    generateInputString("4\nAlphabet\n5\n2023-01-01\n30\n2024-03-25\n100");
    assertEquals("", outputString.toString());
  }

  @Test
  public void testMovingCrossoverNegativeXAndY() {
    generateInputString("4\nAlphabet\n5\n2023-01-01\n-30\n2024-03-25\n-100");
    assertEquals("", outputString.toString());
  }

  @Test
  public void performanceOfFlexiblePortfolioDay() {
    generateInputString("2\n2\np1\n7\n2020-03-08\n2020-03-30\n9\n6");
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "p1\n" +
            "Reached total composition of flexible portfolio\n" +
            "Reached performance of portfolio\n" +
            "2020-03-08\n2020-03-30", outputString.toString());

  }

  @Test
  public void performanceOfFlexiblePortfolioMonth() {
    generateInputString("2\n2\np1\n7\n2020-03-08\n2021-12-31\n9\n6");
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "p1\n" +
            "Reached total composition of flexible portfolio\n" +
            "Reached performance of portfolio\n" +
            "2020-03-08\n2021-12-31", outputString.toString());

  }

  @Test
  public void performanceOfFlexiblePortfolioYear() {
    generateInputString("2\n2\np1\n7\n2020-03-08\n2024-03-08\n9\n6");
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "p1\n" +
            "Reached total composition of flexible portfolio\n" +
            "Reached performance of portfolio\n" +
            "2020-03-08\n2024-03-08", outputString.toString());
  }

  @Test
  public void performanceOfFlexiblePortfolioDateValidation() {
    generateInputString("2\n2\np1\n7\n2024-03-08\n2020-03-08\n9\n6");
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "p1", outputString.toString());

  }

  @Test
  public void performanceOfInFlexiblePortfolioDay() {
    generateInputString("2\n1\np6\n3\n2020-03-08\n2020-03-30\n4\n6");
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p6\n" +
            "Reached total composition of flexible portfolio\n" +
            "Reached performance of portfolio\n" +
            "2020-03-08\n2020-03-30", outputString.toString());

  }

  @Test
  public void performanceOfInFlexiblePortfolioMonth() {
    generateInputString("2\n1\np6\n3\n2020-03-08\n2021-12-31\n4\n6");
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p6\n" +
            "Reached total composition of flexible portfolio\n" +
            "Reached performance of portfolio\n" +
            "2020-03-08\n2021-12-31", outputString.toString());

  }

  @Test
  public void performanceOfInFlexiblePortfolioYear() {
    generateInputString("2\n1\np6\n3\n2020-03-08\n2024-03-08\n4\n6");
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p6\n" +
            "Reached total composition of flexible portfolio\n" +
            "Reached performance of portfolio\n" +
            "2020-03-08\n2024-03-08", outputString.toString());
  }

  @Test
  public void performanceOfInFlexiblePortfolioDateValidation() {
    generateInputString("2\n1\np6\n3\n2024-03-08\n2020-03-08\n4\n6");
    assertEquals("\n" +
            "Reached create portfolio\n" +
            "p6", outputString.toString());
  }

  @Test
  public void performanceOfStockDay() {
    generateInputString("4\nAlphabet\n6\n2020-03-08\n2020-03-30\n7\n6");
    assertEquals("\n" +
            "Reached performance of stock\n" +
            "2020-03-08\n" +
            "2020-03-30", outputString.toString());

  }

  @Test
  public void performanceOfStockMonth() {
    generateInputString("4\nAlphabet\n6\n2020-03-08\n2021-12-31\n7\n6");
    assertEquals("\n" +
            "Reached performance of stock\n" +
            "2020-03-08\n" +
            "2021-12-31", outputString.toString());

  }

  @Test
  public void performanceOfStockYear() {
    generateInputString("4\nAlphabet\n6\n2020-03-08\n2024-03-08\n7\n6");
    assertEquals("\n" +
            "Reached performance of stock\n" +
            "2020-03-08\n" +
            "2024-03-08", outputString.toString());
  }

  @Test
  public void performanceOfStockStartDateValidation() {
    generateInputString("4\nAlphabet\n6\n2024-03-08\n2020-03-08\n7\n6");
    assertEquals("", outputString.toString());
  }
}