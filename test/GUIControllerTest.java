import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

import controller.Features;
import controller.GUIController;
import view.ManagementGUIViewImpl;
import model.ManagementModel;
import view.ManagementGUIView;

import static org.junit.Assert.assertEquals;

/**
 * A Junit test class for the GUIController.
 */
public class GUIControllerTest {
  private StringBuilder outputString;
  ManagementModel model;
  Features controller;
  ManagementGUIView view;

  JLabel label;

  @Before
  public void setUp() {
    outputString = new StringBuilder();
    label = new JLabel();
    model = new ManagementMockModel(outputString);
    controller = new GUIController(model);
    view = new ManagementGUIViewImpl();
    controller.setView(view);

  }

  @Test
  public void testCreatePortfolio() {
    controller.createPortfolio("test", label);
    controller.buyStock("alphabet", "23",
            LocalDate.parse("2024-03-25"), label);
    controller.savePortfolio("test", label);
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "test\n" +
            "Reached buy\n" +
            "23\n" +
            "\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }

  @Test
  public void testCreateAnEmptyPortfolio() {
    controller.createPortfolio("test", label);
    controller.savePortfolio("test", label);
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "test\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testBuyAndSell() {
    controller.createPortfolio("test", label);
    controller.buyStock("alphabet", "23",
            LocalDate.parse("2024-03-25"), label);
    controller.sellStock("alphabet", "10",
            LocalDate.parse("2024-03-25"), label);
    controller.savePortfolio("test", label);
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "test\n" +
            "Reached buy\n" +
            "23\n" +
            "\n" +
            "Reached sell\n" +
            "GOOG\n" +
            "10\n" +
            "2024-03-25\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testMoreSellThenBuy() {
    controller.createPortfolio("test", label);
    controller.buyStock("alphabet", "23",
            LocalDate.parse("2024-03-25"), label);
    controller.sellStock("alphabet", "40",
            LocalDate.parse("2024-03-25"), label);
    controller.savePortfolio("test", label);
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "test\n" +
            "Reached buy\n" +
            "23\n" +
            "\n" +
            "Reached sell\n" +
            "GOOG\n" +
            "40\n" +
            "2024-03-25\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testBuyAndSellFractionalQuantity() {
    controller.createPortfolio("test", label);
    controller.buyStock("alphabet", "24.6",
            LocalDate.parse("2024-03-25"), label);
    controller.sellStock("alphabet", "11.7",
            LocalDate.parse("2024-03-25"), label);
    controller.savePortfolio("test", label);
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "test\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());
  }

  @Test(expected = DateTimeParseException.class)
  public void testBuyInvalidDate() {
    controller.createPortfolio("test", label);
    controller.buyStock("alphabet", "24",
            LocalDate.parse("2024-02-31"), label);
    controller.sellStock("alphabet", "11",
            LocalDate.parse("2024-03-25"), label);
    controller.savePortfolio("test", label);

  }

  @Test(expected = DateTimeParseException.class)
  public void testSellInvalidDate() {
    controller.createPortfolio("test", label);
    controller.buyStock("alphabet", "24",
            LocalDate.parse("2024-01-31"), label);
    controller.sellStock("alphabet", "11",
            LocalDate.parse("2024-02-31"), label);
    controller.savePortfolio("test", label);
  }

  @Test
  public void testLoadPortfolio() {
    controller.loadPortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1", outputString.toString());
  }

  @Test
  public void testBuyAndSellAfterLoad() {
    controller.loadPortfolio("fp1", label);
    controller.buyStock("alphabet", "23",
            LocalDate.parse("2024-03-25"), label);
    controller.sellStock("alphabet", "10",
            LocalDate.parse("2024-03-25"), label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n" +
            "Reached buy\n" +
            "23\n" +
            "\n" +
            "Reached sell\n" +
            "GOOG\n" +
            "10\n" +
            "2024-03-25\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testMoreSellThenBuyAfterLoad() {
    controller.loadPortfolio("fp1", label);
    controller.buyStock("alphabet", "23",
            LocalDate.parse("2024-03-25"), label);
    controller.sellStock("alphabet", "40",
            LocalDate.parse("2024-03-25"), label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n" +
            "Reached buy\n" +
            "23\n" +
            "\n" +
            "Reached sell\n" +
            "GOOG\n" +
            "40\n" +
            "2024-03-25\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testBuyAndSellFractionalQuantityAfterLoad() {
    controller.loadPortfolio("fp1", label);
    controller.buyStock("alphabet", "24.6",
            LocalDate.parse("2024-03-25"), label);
    controller.sellStock("alphabet", "11.7",
            LocalDate.parse("2024-03-25"), label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testTotalComposition() {
    controller.loadPortfolio("fp1", label);
    controller.totalComposition(label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n" +
            "Reached total composition of flexible portfolio\n", outputString.toString());
  }

  @Test
  public void testTotalValue() {
    controller.loadPortfolio("fp1", label);
    float value = controller.getValueOfPortfolio(LocalDate.parse("2023-03-25"));
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n" +
            "Reached total composition of flexible portfolio\n", outputString.toString());
    assertEquals(0.0, value, 0.001);
  }

  @Test(expected = DateTimeParseException.class)
  public void testTotalValueInValidDate() {
    controller.loadPortfolio("fp1", label);
    controller.getValueOfPortfolio(LocalDate.parse("2023-02-31"));
  }

  @Test
  public void testTotalInvestment() {
    controller.loadPortfolio("fp1", label);
    float value = controller.getTotalInvestmentOfPortfolio(LocalDate.parse("2023-03-25"));
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n" +
            "Reached total investment\n" +
            "2023-03-25", outputString.toString());
    assertEquals(0.0, value, 0.001);
  }

  @Test(expected = DateTimeParseException.class)
  public void testTotalInvestmentInValidDate() {
    controller.loadPortfolio("fp1", label);
    controller.getTotalInvestmentOfPortfolio(LocalDate.parse("2023-02-31"));
  }

  @Test
  public void testListAllPortfolios() {
    controller.getFlexiblePortfolios();
    assertEquals("", outputString.toString());
  }

  //Stock Analysis
  @Test
  public void testDailyStockPriceChange() {
    float value = controller.dailyStockPriceChange("alphabet",
            LocalDate.parse("2024-03-28"), label);
    assertEquals("\n" +
            "Reached stock analysis today\n", outputString.toString());
    assertEquals(0.0, value, 0.001);
  }

  @Test
  public void testDailyStockPriceChangeUnavailableDate() {
    float value = controller.dailyStockPriceChange("alphabet",
            LocalDate.parse("2024-01-01"), label);
    assertEquals("", outputString.toString());
    assertEquals(0.0, value, 0.001);
  }

  @Test(expected = DateTimeParseException.class)
  public void testDailyStockPriceChangeInValidDate() {
    controller.dailyStockPriceChange("alphabet",
            LocalDate.parse("2024-02-31"), label);
  }

  @Test
  public void testPeriodStockPriceChange() {
    float value = controller.periodStockPriceChange("alphabet",
            LocalDate.parse("2020-01-03"), LocalDate.parse("2024-03-28"), label);
    assertEquals("\n" +
            "Reached stock analysis period\n", outputString.toString());
    assertEquals(0.0, value, 0.001);
  }

  @Test
  public void testPeriodStockPriceChangeUnavailableDate() {
    float value = controller.periodStockPriceChange("alphabet",
            LocalDate.parse("2020-01-01"), LocalDate.parse("2024-01-01"), label);
    assertEquals("", outputString.toString());
    assertEquals(0.0, value, 0.001);
  }

  @Test(expected = DateTimeParseException.class)
  public void testPeriodStockPriceChangeInValidDate() {
    controller.periodStockPriceChange("alphabet",
            LocalDate.parse("2020-02-31"), LocalDate.parse("2024-02-31"), label);
  }

  @Test
  public void testXDayMovingAverage() {
    float value = controller.xDayMovingAverage("alphabet", 30,
            LocalDate.parse("2024-03-28"));
    assertEquals("\nReached x day moving average\n", outputString.toString());
    assertEquals(0.0, value, 0.001);
  }

  @Test
  public void testXDayMovingAverageUnavailableDate() {
    float value = controller.xDayMovingAverage("alphabet", 30,
            LocalDate.parse("2024-01-01"));
    assertEquals("\nReached x day moving average\n", outputString.toString());
    assertEquals(0.0, value, 0.001);
  }

  @Test(expected = DateTimeParseException.class)
  public void testXDayMovingAverageInValidDate() {
    controller.xDayMovingAverage("alphabet", 30,
            LocalDate.parse("2024-02-31"));
  }

  @Test
  public void testXDayMovingAverageInValidXValue() {
    float value = controller.xDayMovingAverage("alphabet", -30,
            LocalDate.parse("2024-03-31"));
    assertEquals("\nReached x day moving average\n", outputString.toString());
    assertEquals(0.0, value, 0.001);
  }

  @Test
  public void testCrossover() {
    controller.crossovers("alphabet", LocalDate.parse("2020-01-03"),
            LocalDate.parse("2024-03-28"));
    assertEquals("\nReached crossover\n", outputString.toString());
  }

  @Test
  public void testCrossoverUnavailableDate() {
    controller.crossovers("alphabet", LocalDate.parse("2020-01-01"),
            LocalDate.parse("2024-03-23"));
    assertEquals("\nReached crossover\n", outputString.toString());
  }

  @Test(expected = DateTimeParseException.class)
  public void testCrossoverInValidDate() {
    controller.crossovers("alphabet", LocalDate.parse("2020-02-31"),
            LocalDate.parse("2024-02-31"));
  }

  @Test
  public void testMovingCrossOver() {
    controller.movingCrossovers("alphabet", 30, 100,
            LocalDate.parse("2020-01-03"), LocalDate.parse("2024-03-28"));
    assertEquals("\n" +
            "Reached moving crossover\n", outputString.toString());
  }

  @Test
  public void testMovingCrossOverUnavailableDate() {
    controller.movingCrossovers("alphabet", 30, 100,
            LocalDate.parse("2020-01-01"), LocalDate.parse("2024-01-01"));
    assertEquals("\n" +
            "Reached moving crossover\n", outputString.toString());
  }

  @Test(expected = DateTimeParseException.class)
  public void testMovingCrossOveInValidDate() {
    controller.movingCrossovers("alphabet", 30, 100,
            LocalDate.parse("2020-02-31"), LocalDate.parse("2024-02-31"));
  }

  @Test
  public void testMovingCrossOverInvalidXY() {
    controller.movingCrossovers("alphabet", -30, -100,
            LocalDate.parse("2020-02-01"), LocalDate.parse("2024-03-31"));
    assertEquals("\n" +
            "Reached moving crossover\n", outputString.toString());
  }

  //Batch buy and dollar cost averaging
  @Test
  public void testInvestFixedAmount() {
    controller.createPortfolio("test1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "30");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    controller.investFixedAmount(data, 2000f, label);
    controller.savePortfolio("test1", label);
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "test1\n" +
            "Reached invest fixed amount\n" +
            "2000.0\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }

  @Test
  public void testInvestFixedInValidAmount() {
    controller.createPortfolio("test1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "30");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    controller.investFixedAmount(data, -2000f, label);
    controller.savePortfolio("test1", label);
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "test1\n", outputString.toString());

  }

  @Test
  public void testInvestFixedFractionalPercentage() {
    controller.createPortfolio("test1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50.5");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "29.5");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    controller.investFixedAmount(data, 2000f, label);
    controller.savePortfolio("test1", label);
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "test1\n" +
            "Reached invest fixed amount\n" +
            "2000.0\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }

  @Test
  public void testInvestFixedInvalidPercentage() {
    controller.createPortfolio("test1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "-50.5");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "29.5");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    controller.investFixedAmount(data, 2000f, label);
    controller.savePortfolio("test1", label);
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "test1\n", outputString.toString());

  }

  @Test
  public void testInvestFixedInvalidDate() {
    controller.createPortfolio("test1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50.5");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-02-31");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "29.5");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    controller.investFixedAmount(data, 2000f, label);
    controller.savePortfolio("test1", label);
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "test1\n", outputString.toString());

  }

  @Test
  public void testInvestFixedAmountPercentageGreaterThan100() {
    controller.createPortfolio("test1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "50");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    controller.investFixedAmount(data, 2000f, label);
    controller.savePortfolio("test1", label);
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "test1\n" +
            "Reached invest fixed amount\n" +
            "2000.0\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }

  @Test
  public void testInvestFixedAmountLoad() {
    controller.loadPortfolio("fp1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "30");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    controller.investFixedAmount(data, 2000f, label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n" +
            "Reached invest fixed amount\n" +
            "2000.0\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }

  @Test
  public void testInvestFixedAmountLoadInvalidTicker() {
    controller.loadPortfolio("fp1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "AAJALKDAL");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "30");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    controller.investFixedAmount(data, 2000f, label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n" +
            "Reached add Strategy to list\n" +
            "2024-01-03,2025-03-03,2024-04-03,30,2000.0,[alphabet:100]\n" +
            "Reached invest fixed amount\n" +
            "2000.0\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }

  @Test
  public void testInvestFixedInValidAmountLoad() {
    controller.loadPortfolio("fp1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "30");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    controller.investFixedAmount(data, -2000f, label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n", outputString.toString());

  }

  @Test
  public void testInvestFixedFractionalPercentageLoad() {
    controller.loadPortfolio("fp1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50.5");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "29.5");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    controller.investFixedAmount(data, 2000f, label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n" +
            "Reached invest fixed amount\n" +
            "2000.0\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }

  @Test
  public void testInvestFixedInvalidPercentageLoad() {
    controller.loadPortfolio("fp1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "-50.5");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "29.5");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    controller.investFixedAmount(data, 2000f, label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n", outputString.toString());

  }

  @Test
  public void testInvestFixedInvalidDateLoad() {
    controller.loadPortfolio("fp1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50.5");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-02-31");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "29.5");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    controller.investFixedAmount(data, 2000f, label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n", outputString.toString());

  }

  @Test
  public void testInvestFixedAmountPercentageGreaterThan100Load() {
    controller.loadPortfolio("fp1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "50");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    controller.investFixedAmount(data, 2000f, label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n" +
            "Reached invest fixed amount\n" +
            "2000.0\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }

  //Dollar cost averaging tests
  @Test
  public void testDollarAveraging() {
    controller.createPortfolio("test1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "30");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);

    List<String> sharePercentage = new ArrayList<>();
    sharePercentage.add("alphabet:50");
    sharePercentage.add("verizon:30");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, 2000, LocalDate.parse("2024-03-25"),
            LocalDate.parse("2034-01-03"), LocalDate.now(), 30, data, label);
    controller.savePortfolio("test1", label);
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "test1\n" +
            "Reached add Strategy to list\n" +
            "2024-01-03,2025-03-03,2024-04-03,30,2000.0,[alphabet:100]\n" +
            "Reached add Strategy to list\n" +
            "2024-03-25,2034-01-03,2024-03-11,30,2000.0,[alphabet:50; verizon:30; goldman:20]\n" +
            "Reached dollar cost Strategy\n" +
            "2000.0\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }

  @Test
  public void testDollarAveragingInValidAmount() {
    controller.createPortfolio("test1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "30");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    List<String> sharePercentage = new ArrayList<>();
    sharePercentage.add("alphabet:50");
    sharePercentage.add("verizon:30");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, -2000, LocalDate.parse("2024-03-25"),
            LocalDate.parse("2034-01-03"), LocalDate.now(), 30, data, label);
    controller.savePortfolio("test1", label);
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "test1\n" +
            "Reached add Strategy to list\n" +
            "2024-03-25,2034-01-03,2024-03-11,30,-2000.0,[alphabet:50; verizon:30; goldman:20]\n" +
            "Reached dollar cost Strategy\n" +
            "-2000.0\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }

  @Test
  public void testDollarAveragingFractionalPercentage() {
    controller.createPortfolio("test1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50.5");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "29.5");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    List<String> sharePercentage = new ArrayList<>();
    sharePercentage.add("alphabet:50.5");
    sharePercentage.add("verizon:29.5");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, -2000, LocalDate.parse("2024-03-25"),
            LocalDate.parse("2034-01-03"), LocalDate.now(), 30, data, label);
    controller.savePortfolio("test1", label);
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "test1\n" +
            "Reached add Strategy to list\n" +
            "2024-03-25,2034-01-03,2024-03-11,30,-2000.0,[alphabet:50.5; " +
            "verizon:29.5; goldman:20]\n" +
            "Reached dollar cost Strategy\n" +
            "-2000.0\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }

  @Test
  public void testDollarAveragingInvalidPercentage() {
    controller.createPortfolio("test1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "-50.5");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "29.5");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);

    List<String> sharePercentage = new ArrayList<>();
    sharePercentage.add("alphabet:-50.5");
    sharePercentage.add("verizon:29.5");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, -2000, LocalDate.parse("2024-03-25"),
            LocalDate.parse("2034-01-03"), LocalDate.now(), 30, data, label);
    controller.savePortfolio("test1", label);

    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "test1\n" +
            "Reached add Strategy to list\n" +
            "2024-03-25,2034-01-03,2024-03-11,30,-2000.0,[alphabet:-50.5; " +
            "verizon:29.5; goldman:20]\n" +
            "Reached dollar cost Strategy\n" +
            "-2000.0\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }

  @Test(expected = DateTimeParseException.class)
  public void testDollarAveragingInvalidDate() {
    controller.createPortfolio("test1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50.5");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-03-31");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "29.5");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    List<String> sharePercentage = new ArrayList<>();
    sharePercentage.add("alphabet:50.5");
    sharePercentage.add("verizon:29.5");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, 2000, LocalDate.parse("2024-02-31"),
            LocalDate.parse("2034-01-03"), LocalDate.now(), 30, data, label);
    controller.savePortfolio("test1", label);

  }

  @Test
  public void testDollarAveragingPercentageGreaterThan100() {
    controller.createPortfolio("test1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "50");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    List<String> sharePercentage = new ArrayList<>();
    sharePercentage.add("alphabet:50");
    sharePercentage.add("verizon:50");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, 2000, LocalDate.parse("2024-01-31"),
            LocalDate.parse("2034-01-03"), LocalDate.now(), 30, data, label);
    assertEquals("\n" +
            "Reached create flexible portfolio\n" +
            "test1\n" +
            "Reached add Strategy to list\n" +
            "2024-01-31,2034-01-03,2024-03-11,30,2000.0,[alphabet:50; verizon:50; goldman:20]\n" +
            "Reached dollar cost Strategy\n" +
            "2000.0", outputString.toString());

  }

  @Test
  public void testDollarAveragingEndDateNotSpecified() {
    controller.createPortfolio("test1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "30");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    List<String> sharePercentage = new ArrayList<>();

    sharePercentage.add("alphabet:50");
    sharePercentage.add("verizon:30");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, 2000, LocalDate.parse("2024-03-31"),
            LocalDate.now().plusYears(100), LocalDate.now(), 30, data, label);
    controller.savePortfolio("test1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "test1\n" +
            "Reached add Strategy to list\n" +
            "2024-01-03,2025-03-03,2024-04-03,30,2000.0,[alphabet:100]\n" +
            "Reached add Strategy to list\n" +
            "2024-03-31,2124-04-10,2024-03-11,30,2000.0,[alphabet:50; verizon:30; goldman:20]\n" +
            "Reached dollar cost Strategy\n" +
            "2000.0\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }

  @Test
  public void testDollarAveragingInvalidPeriod() {
    controller.createPortfolio("test1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "30");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    List<String> sharePercentage = new ArrayList<>();

    sharePercentage.add("alphabet:50");
    sharePercentage.add("verizon:30");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, 2000, LocalDate.parse("2024-03-31"),
            LocalDate.parse("2034-01-03"), LocalDate.now(), -30, data, label);
    controller.savePortfolio("test1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "test1\n", outputString.toString());

  }

  @Test
  public void testDollarAveragingLoad() {
    controller.loadPortfolio("fp1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "30");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    List<String> sharePercentage = new ArrayList<>();
    sharePercentage.add("alphabet:50");
    sharePercentage.add("verizon:30");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, 2000, LocalDate.parse("2024-01-31"),
            LocalDate.parse("2034-01-03"), LocalDate.now(), 30, data, label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n" +
            "Reached add Strategy to list\n" +
            "2024-01-03,2025-03-03,2024-04-03,30,2000.0,[alphabet:100]\n" +
            "Reached add Strategy to list\n" +
            "2024-01-31,2034-01-03,2024-03-11,30,2000.0,[alphabet:50; verizon:30; goldman:20]\n" +
            "Reached dollar cost Strategy\n" +
            "2000.0\n" +
            "Reached invest fixed amount\n" +
            "2000.0\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }

  @Test
  public void testDollarAveragingInValidAmountLoad() {
    controller.loadPortfolio("fp1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "30");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    List<String> sharePercentage = new ArrayList<>();
    sharePercentage.add("alphabet:50");
    sharePercentage.add("verizon:30");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, -2000, LocalDate.parse("2024-01-31"),
            LocalDate.parse("2034-01-03"), LocalDate.now(), 30, data, label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n", outputString.toString());

  }

  @Test
  public void testDollarAveragingFractionalPercentageLoad() {
    controller.loadPortfolio("fp1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50.5");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "29.5");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    List<String> sharePercentage = new ArrayList<>();
    sharePercentage.add("alphabet:50.5");
    sharePercentage.add("verizon:29.5");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, 2000, LocalDate.parse("2024-01-31"),
            LocalDate.parse("2034-01-03"), LocalDate.now(), 30, data, label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n" +
            "Reached add Strategy to list\n" +
            "2024-01-03,2025-03-03,2024-04-03,30,2000.0,[alphabet:100]\n" +
            "Reached add Strategy to list\n" +
            "2024-01-31,2034-01-03,2024-03-11,30,2000.0,[alphabet:50.5; " +
            "verizon:29.5; goldman:20]\n" +
            "Reached dollar cost Strategy\n" +
            "2000.0\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }

  @Test
  public void testDollarAveragingInvalidPercentageLoad() {
    controller.loadPortfolio("fp1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "-50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "30");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    List<String> sharePercentage = new ArrayList<>();

    sharePercentage.add("alphabet:-50");
    sharePercentage.add("verizon:30");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, 2000, LocalDate.parse("2024-01-31"),
            LocalDate.parse("2034-01-03"), LocalDate.now(), 30, data, label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n", outputString.toString());

  }

  @Test(expected = DateTimeParseException.class)
  public void testDollarAveragingInvalidDateLoad() {
    controller.loadPortfolio("fp1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-03-31");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "30");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    List<String> sharePercentage = new ArrayList<>();

    sharePercentage.add("alphabet:50");
    sharePercentage.add("verizon:30");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, 2000, LocalDate.parse("2024-02-31"),
            LocalDate.parse("2034-01-03"), LocalDate.now(), 30, data, label);
    controller.savePortfolio("fp1", label);

  }

  @Test
  public void testDollarAveragingPercentageGreaterThan100Load() {
    controller.loadPortfolio("fp1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "50");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    List<String> sharePercentage = new ArrayList<>();

    sharePercentage.add("alphabet:50");
    sharePercentage.add("verizon:50");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, 2000, LocalDate.parse("2024-03-31"),
            LocalDate.parse("2034-01-03"), LocalDate.now(), 30, data, label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n", outputString.toString());

  }

  @Test
  public void testDollarAveragingEndDateNotSpecifiedLoad() {
    controller.loadPortfolio("fp1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "30");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    List<String> sharePercentage = new ArrayList<>();

    sharePercentage.add("alphabet:50");
    sharePercentage.add("verizon:30");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, 2000, LocalDate.parse("2024-03-31"),
            LocalDate.now().plusYears(100), LocalDate.now(), 30, data, label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n" +
            "Reached add Strategy to list\n" +
            "2024-01-03,2025-03-03,2024-04-03,30,2000.0,[alphabet:100]\n" +
            "Reached add Strategy to list\n" +
            "2024-03-31,2124-04-10,2024-03-11,30,2000.0,[alphabet:50; verizon:30; goldman:20]\n" +
            "Reached dollar cost Strategy\n" +
            "2000.0\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }

  @Test
  public void testDollarAveragingInvalidPeriodLoad() {
    controller.loadPortfolio("fp1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "GOOG");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "30");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    List<String> sharePercentage = new ArrayList<>();

    sharePercentage.add("alphabet:50");
    sharePercentage.add("verizon:30");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, 2000, LocalDate.parse("2024-03-31"),
            LocalDate.parse("2034-01-03"), LocalDate.now(), -30, data, label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n", outputString.toString());

  }

  @Test
  public void testDollarAveragingLoadInvalidTicker() {
    controller.loadPortfolio("fp1", label);
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "AJAJAJA");
    share1.put("percentage", "50");
    share1.put("close", "140.36");
    share1.put("open", "138.6");
    share1.put("timestamp", "2024-01-03");

    Map<String, String> share2 = new HashMap<>();
    share2.put("volume", "18974308");
    share2.put("ticker", "VZ");
    share2.put("percentage", "30");
    share2.put("close", "140.36");
    share2.put("open", "138.6");
    share2.put("timestamp", "2024-01-03");

    Map<String, String> share3 = new HashMap<>();
    share3.put("volume", "18974308");
    share3.put("ticker", "AAAU");
    share3.put("percentage", "20");
    share3.put("close", "140.36");
    share3.put("open", "138.6");
    share3.put("timestamp", "2024-01-03");

    data.add(share1);
    data.add(share2);
    data.add(share3);
    List<String> sharePercentage = new ArrayList<>();
    sharePercentage.add("akakala:50");
    sharePercentage.add("verizon:30");
    sharePercentage.add("goldman:20");


    controller.createStrategy(sharePercentage, 2000, LocalDate.parse("2024-01-31"),
            LocalDate.parse("2034-01-03"), LocalDate.now(), 30, data, label);
    controller.savePortfolio("fp1", label);
    assertEquals("\n" +
            "Reached load flexible portfolio\n" +
            "fp1\n" +
            "Reached add Strategy to list\n" +
            "2024-01-03,2025-03-03,2024-04-03,30,2000.0,[alphabet:100]\n" +
            "Reached add Strategy to list\n" +
            "2024-01-31,2034-01-03,2024-03-12,30,2000.0,[akakala:50; verizon:30; goldman:20]\n" +
            "Reached dollar cost Strategy\n" +
            "2000.0\n" +
            "Reached get transaction of the flexible portfolio\n", outputString.toString());

  }


}