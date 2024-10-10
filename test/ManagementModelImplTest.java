import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.ManagementModel;
import model.ManagementModelImpl;

import static org.junit.Assert.assertEquals;

/**
 * The JUnit test class for ManagementModel implementation class. The class tests for the methods
 * available in the management model.
 */
public class ManagementModelImplTest {
  ManagementModel model;

  /**
   * The method initializes a new ManagementModelImpl everytime before a test runs.
   */
  @Before
  public void setUp() {
    model = new ManagementModelImpl();
  }

  @Test
  public void testSavePortfolio() throws ParseException {

    Map<String, String> stock = new HashMap<>();
    stock.put("ticker", "GOOG");
    stock.put("timestamp", "2024-03-25");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    stock.put("quantity", "23");
    model.createPortfolio("p1", Collections.singletonList(stock));
    assertEquals("[GOOG,2024-03-25,150.95,150.95,15114728,23,3471.8499]",
            model.totalComposition().toString());
  }

  @Test
  public void testTotalComposition() throws ParseException {
    Map<String, String> stock = new HashMap<>();
    stock.put("ticker", "GOOG");
    stock.put("timestamp", "2024-03-25");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    stock.put("quantity", "23");
    model.createPortfolio("p1", Collections.singletonList(stock));
    assertEquals("[GOOG,2024-03-25,150.95,150.95,15114728,23,3471.8499]",
            model.totalComposition().toString());
  }

  @Test
  public void testTotalValue() throws ParseException {

    Map<String, String> stock = new HashMap<>();
    stock.put("ticker", "GOOG");
    stock.put("timestamp", "2024-03-25");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    stock.put("quantity", "23");
    model.createPortfolio("p1", Collections.singletonList(stock));

    Map<String, String> totalValue = new HashMap<>();
    stock.put("timestamp", "2024-03-22");
    stock.put("fileTimeStamp", "2024-03-26");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    stock.put("quantity", "15");

    model.createPortfolio("p1", Collections.singletonList(stock));
    assertEquals("[GOOG,2024-03-25,150.95,150.95,15114728,23,3471.8499]",
            model.totalValue(Collections.singletonList(totalValue)));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testAddStockNegativeQuantity() throws IllegalArgumentException, ParseException {
    Map<String, String> stock = new HashMap<>();
    stock.put("ticker", "GOOG");
    stock.put("timestamp", "2024-03-25");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    stock.put("quantity", "-23");
    model.createPortfolio("p1", Collections.singletonList(stock));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddStockFractionQuantity() throws IllegalArgumentException, ParseException {
    Map<String, String> stock = new HashMap<>();
    stock.put("ticker", "GOOG");
    stock.put("timestamp", "2024-03-25");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    stock.put("quantity", "22.63");
    model.createPortfolio("p1", Collections.singletonList(stock));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddStockZeroQuantity() throws IllegalArgumentException, ParseException {
    Map<String, String> stock = new HashMap<>();
    stock.put("ticker", "GOOG");
    stock.put("timestamp", "2024-03-25");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    stock.put("quantity", "0");
    model.createPortfolio("p1", Collections.singletonList(stock));
  }

  @Test
  public void testSaveMultiplePortfolios() throws ParseException {
    Map<String, String> stock = new HashMap<>();
    stock.put("ticker", "GOOG");
    stock.put("timestamp", "2024-03-25");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    stock.put("quantity", "23");
    model.createPortfolio("p1", Collections.singletonList(stock));
    assertEquals("[GOOG,2024-03-25,150.95,150.95,15114728,23,3471.8499]",
            model.totalComposition().toString());

    Map<String, String> stock2 = new HashMap<>();
    stock2.put("ticker", "VZ");
    stock2.put("timestamp", "2024-03-21");
    stock2.put("open", "151.9500");
    stock2.put("close", "134.9500");
    stock2.put("volume", "1511728");
    stock2.put("quantity", "34");
    model.createPortfolio("p1", Collections.singletonList(stock2));
    assertEquals("[VZ,2024-03-21,151.95,134.95,1511728,34,4588.3]",
            model.totalComposition().toString());
  }

  //Flexible portfolio Tests
  @Test
  public void testBuyStock() throws ParseException {
    Map<String, String> stock = new HashMap<>();
    stock.put("ticker", "GOOG");
    stock.put("timestamp", "2024-03-25");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    model.createFlexiblePortfolio("p10");
    model.buy(23, stock);
    assertEquals("[GOOG,2024-03-25,150.95,150.95,15114728,23,3471.8499]",
            model.totalCompositionOfFlexiblePortfolio().toString());
  }

  @Test
  public void testSellStock() throws ParseException {
    Map<String, String> stock = new HashMap<>();
    stock.put("ticker", "GOOG");
    stock.put("timestamp", "2024-03-25");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    model.createFlexiblePortfolio("p10");
    model.buy(23, stock);
    model.sell(10, "GOOG", LocalDate.parse("2024-03-25"));
    assertEquals("[GOOG,2024-03-25,150.95,150.95,15114728,13,3471.8499]",
            model.totalCompositionOfFlexiblePortfolio().toString());
  }

  @Test
  public void testTotalCompositionFlexible() throws ParseException {
    Map<String, String> stock = new HashMap<>();
    stock.put("ticker", "GOOG");
    stock.put("timestamp", "2024-03-25");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    model.createFlexiblePortfolio("p10");
    model.buy(23, stock);
    model.sell(10, "GOOG", LocalDate.parse("2024-03-25"));
    assertEquals("[GOOG,2024-03-25,150.95,150.95,15114728,13,3471.8499]",
            model.totalCompositionOfFlexiblePortfolio().toString());
  }

  @Test
  public void testTotalValueFlexible() throws ParseException {
    Map<String, String> stock = new HashMap<>();
    stock.put("ticker", "GOOG");
    stock.put("timestamp", "2024-03-25");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    model.createFlexiblePortfolio("p10");
    model.buy(23, stock);
    model.sell(10, "GOOG", LocalDate.parse("2024-03-25"));
    assertEquals("[GOOG,2024-03-25,150.95,150.95,15114728,13,3471.8499]",
            model.totalValueOfFlexiblePortfolio(Collections.singletonList(stock)));
  }

  @Test
  public void testTotalInvestment() throws ParseException {
    Map<String, String> stock = new HashMap<>();
    stock.put("ticker", "GOOG");
    stock.put("timestamp", "2024-03-25");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    model.createFlexiblePortfolio("p10");
    model.buy(23, stock);
    model.sell(10, "GOOG", LocalDate.parse("2024-03-25"));
    assertEquals(3471.8499,
            model.getTotalInvestment(LocalDate.parse("2024-03-25")), 0.001);
  }

  @Test
  public void testBuyStockAfterLoad() throws ParseException {
    List<String> stock = new ArrayList<>();
    stock.add("VZ,2020-10-15,58.11,58.16,13645772,20,1163.2");
    stock.add("GOOG,2020-10-15,1547.15,1559.13,1540817,10,15591.3");

    Map<String, String> stock2 = new HashMap<>();
    stock2.put("ticker", "GOOG");
    stock2.put("timestamp", "2020-10-15");
    stock2.put("open", "150.9500");
    stock2.put("close", "150.9500");
    stock2.put("volume", "15114728");
    model.loadFlexiblePortfolio("p1", stock, null, -1);
    model.buy(23, stock2);
    assertEquals("[VZ,2020-10-15,58.11,58.16,13645772,20,1163.2, " +
                    "GOOG,2020-10-15,1547.15,1559.13,1540817,33,19063.15]",
            model.totalCompositionOfFlexiblePortfolio().toString());
  }

  @Test
  public void testSellStockAfterLoad() throws ParseException {
    List<String> stock = new ArrayList<>();
    stock.add("VZ,2020-10-15,58.11,58.16,13645772,20,1163.2");
    stock.add("GOOG,2020-10-15,1547.15,1559.13,1540817,10,15591.3");

    Map<String, String> stock2 = new HashMap<>();
    stock2.put("ticker", "GOOG");
    stock2.put("timestamp", "2020-10-15");
    stock2.put("open", "150.9500");
    stock2.put("close", "150.9500");
    stock2.put("volume", "15114728");
    model.loadFlexiblePortfolio("p1", stock, null, -1);
    model.sell(2, "GOOG", LocalDate.parse("2020-10-15"));
    assertEquals("[VZ,2020-10-15,58.11,58.16,13645772,20,1163.2, " +
                    "GOOG,2020-10-15,1547.15,1559.13,1540817,8,15591.3]",
            model.totalCompositionOfFlexiblePortfolio().toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSellMoreThanBuy() throws ParseException {
    List<String> stock = new ArrayList<>();
    stock.add("VZ,2020-10-15,58.11,58.16,13645772,20,1163.2");
    stock.add("GOOG,2020-10-15,1547.15,1559.13,1540817,10,15591.3");

    Map<String, String> stock2 = new HashMap<>();
    stock2.put("ticker", "GOOG");
    stock2.put("timestamp", "2020-10-15");
    stock2.put("open", "150.9500");
    stock2.put("close", "150.9500");
    stock2.put("volume", "15114728");
    model.loadFlexiblePortfolio("p1", stock, null, -1);
    model.sell(23, "GOOG", LocalDate.parse("2020-10-15"));
  }


  @Test
  public void testTotalCompositionFlexibleAfterLoad() throws ParseException {
    List<String> stock2 = new ArrayList<>();
    stock2.add("VZ,2020-10-15,58.11,58.16,13645772,20,1163.2");
    stock2.add("GOOG,2020-10-15,1547.15,1559.13,1540817,10,15591.3");

    Map<String, String> stock = new HashMap<>();
    stock.put("ticker", "GOOG");
    stock.put("timestamp", "2024-03-25");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    model.loadFlexiblePortfolio("p1", stock2, null, -1);
    model.buy(23, stock);
    model.sell(10, "GOOG", LocalDate.parse("2024-03-25"));
    assertEquals("[VZ,2020-10-15,58.11,58.16,13645772,20,1163.2, " +
                    "GOOG,2024-03-25,150.95,150.95,15114728,23,3471.8499]",
            model.totalCompositionOfFlexiblePortfolio().toString());
  }

  @Test
  public void testTotalValueFlexibleAfterLoad() throws ParseException {
    List<String> stock2 = new ArrayList<>();
    stock2.add("VZ,2020-10-15,58.11,58.16,13645772,20,1163.2");
    stock2.add("GOOG,2020-10-15,1547.15,1559.13,1540817,10,15591.3");

    Map<String, String> stock = new HashMap<>();
    stock.put("ticker", "GOOG");
    stock.put("timestamp", "2024-03-25");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    model.loadFlexiblePortfolio("p1", stock2, null, -1);
    model.buy(23, stock);
    model.sell(10, "GOOG", LocalDate.parse("2024-03-25"));
    assertEquals("[GOOG,2024-03-25,150.95,150.95,15114728,13,3471.8499]",
            model.totalValueOfFlexiblePortfolio(Collections.singletonList(stock)));
  }

  @Test
  public void testTotalInvestmentAfterLoad() throws ParseException {
    List<String> stock2 = new ArrayList<>();
    stock2.add("VZ,2020-10-15,58.11,58.16,13645772,20,1163.2");
    stock2.add("GOOG,2020-10-15,1547.15,1559.13,1540817,10,15591.3");

    Map<String, String> stock = new HashMap<>();
    stock.put("ticker", "GOOG");
    stock.put("timestamp", "2024-03-25");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    model.loadFlexiblePortfolio("p1", stock2, null, -1);
    model.buy(23, stock);
    model.sell(10, "GOOG", LocalDate.parse("2024-03-25"));
    assertEquals(19063.1504,
            model.getTotalInvestment(LocalDate.parse("2024-03-25")), 0.001);
  }

  @Test
  public void testTotalValueBeforePortfolioCreated() throws ParseException {
    List<String> stock2 = new ArrayList<>();
    stock2.add("VZ,2020-10-15,58.11,58.16,13645772,20,1163.2");
    stock2.add("GOOG,2020-10-15,1547.15,1559.13,1540817,10,15591.3");

    Map<String, String> stock = new HashMap<>();
    stock.put("ticker", "GOOG");
    stock.put("timestamp", "2019-03-25");
    stock.put("open", "150.9500");
    stock.put("close", "150.9500");
    stock.put("volume", "15114728");
    model.loadFlexiblePortfolio("p1", stock2, null, -1);
    model.buy(23, stock);
    model.sell(10, "GOOG", LocalDate.parse("2024-03-25"));
    assertEquals("[GOOG,2024-03-25,150.95,150.95,15114728,13,3471.8499]",
            model.totalValueOfFlexiblePortfolio(Collections.singletonList(stock)));
  }

  @Test
  public void testGainOnADay() {
    Map<String, String> stock = new HashMap<>();
    stock.put("close", "151.1500");
    stock.put("open", "150.9500");

    assertEquals(0.1999969, model.stockAnalysisDay(stock), 0.001);
  }

  @Test
  public void testGainOverPeriod() {
    Map<String, String> stock = new HashMap<>();
    stock.put("closePriceStartDate", "149.6800");
    stock.put("closePriceEndDate", "151.1500");

    assertEquals(1.4700, model.stockAnalysisPeriod(stock), 0.001);
  }

  @Test
  public void testLoseOnADay() {
    Map<String, String> stock = new HashMap<>();
    stock.put("open", "151.1500");
    stock.put("close", "150.9500");

    assertEquals(-0.1999969, model.stockAnalysisDay(stock), 0.001);
  }

  @Test
  public void testLoseOverPeriod() {
    Map<String, String> stock = new HashMap<>();
    stock.put("closePriceEndDate", "149.6800");
    stock.put("closePriceStartDate", "151.1500");

    assertEquals(-1.4700, model.stockAnalysisPeriod(stock), 0.001);
  }

  @Test
  public void testXDayMovingAverage() {
    List<Map<String, String>> dataList = new ArrayList<>();
    Map<String, String> data01 = new HashMap<>();
    data01.put("volume", "3142600");
    data01.put("ticker", "GOOG");
    data01.put("close", "554.9000");
    data01.put("open", "542.6000");
    data01.put("timestamp", "2024-03-19");
    dataList.add(data01);

    Map<String, String> data0 = new HashMap<>();
    data0.put("volume", "3142600");
    data0.put("ticker", "GOOG");
    data0.put("close", "554.9000");
    data0.put("open", "542.6000");
    data0.put("timestamp", "2024-03-20");
    dataList.add(data0);

    Map<String, String> data1 = new HashMap<>();
    data1.put("volume", "3142600");
    data1.put("ticker", "GOOG");
    data1.put("close", "554.9000");
    data1.put("open", "542.6000");
    data1.put("timestamp", "2024-03-21");
    dataList.add(data1);

    Map<String, String> data2 = new HashMap<>();
    data2.put("volume", "4389600");
    data2.put("ticker", "GOOG");
    data2.put("close", "538.1500");
    data2.put("open", "540.7400");
    data2.put("timestamp", "2024-03-25");
    dataList.add(data2);

    Map<String, String> additionalData = new HashMap<>();
    additionalData.put("date", "2024-03-25");
    additionalData.put("day", "2");
    dataList.add(additionalData);
    assertEquals(151.45, model.xDayMovingAverage(dataList), 0.001);

  }

  @Test
  public void testCrossover() {
    List<Map<String, String>> dataList = new ArrayList<>();
    Map<String, String> data01 = new HashMap<>();
    data01.put("volume", "3142600");
    data01.put("ticker", "GOOG");
    data01.put("close", "554.9000");
    data01.put("open", "542.6000");
    data01.put("timestamp", "2024-03-19");
    dataList.add(data01);

    Map<String, String> data0 = new HashMap<>();
    data0.put("volume", "3142600");
    data0.put("ticker", "GOOG");
    data0.put("close", "554.9000");
    data0.put("open", "542.6000");
    data0.put("timestamp", "2024-03-20");
    dataList.add(data0);

    Map<String, String> data1 = new HashMap<>();
    data1.put("volume", "3142600");
    data1.put("ticker", "GOOG");
    data1.put("close", "554.9000");
    data1.put("open", "542.6000");
    data1.put("timestamp", "2024-03-21");
    dataList.add(data1);

    Map<String, String> data2 = new HashMap<>();
    data2.put("volume", "4389600");
    data2.put("ticker", "GOOG");
    data2.put("close", "538.1500");
    data2.put("open", "540.7400");
    data2.put("timestamp", "2024-03-25");
    dataList.add(data2);

    Map<String, String> additionalData = new HashMap<>();
    additionalData.put("startDate", "2024-03-22");
    additionalData.put("endDate", "2024-03-25");
    additionalData.put("day", "30");
    dataList.add(additionalData);

    assertEquals("[2024-03-22]",
            model.crossOvers(dataList).get("POSITIVES").toString(), 0.001);
    assertEquals("[2024-03-21]",
            model.crossOvers(dataList).get("NEGATIVES").toString(), 0.001);
  }

  @Test
  public void testMovingCrossover() {
    List<Map<String, String>> dataList = new ArrayList<>();
    Map<String, String> data01 = new HashMap<>();
    data01.put("volume", "3142600");
    data01.put("ticker", "GOOG");
    data01.put("close", "554.9000");
    data01.put("open", "542.6000");
    data01.put("timestamp", "2024-03-19");
    dataList.add(data01);

    Map<String, String> data0 = new HashMap<>();
    data0.put("volume", "3142600");
    data0.put("ticker", "GOOG");
    data0.put("close", "554.9000");
    data0.put("open", "542.6000");
    data0.put("timestamp", "2024-03-20");
    dataList.add(data0);

    Map<String, String> data1 = new HashMap<>();
    data1.put("volume", "3142600");
    data1.put("ticker", "GOOG");
    data1.put("close", "554.9000");
    data1.put("open", "542.6000");
    data1.put("timestamp", "2024-03-21");
    dataList.add(data1);

    Map<String, String> data2 = new HashMap<>();
    data2.put("volume", "4389600");
    data2.put("ticker", "GOOG");
    data2.put("close", "538.1500");
    data2.put("open", "540.7400");
    data2.put("timestamp", "2024-03-25");
    dataList.add(data2);

    Map<String, String> additionalData = new HashMap<>();
    additionalData.put("startDate", "2024-03-22");
    additionalData.put("endDate", "2024-03-25");
    additionalData.put("xday", "30");
    additionalData.put("yday", "100");
    dataList.add(additionalData);
    assertEquals("[2024-03-22]",
            model.crossOvers(dataList).get("POSITIVES").toString(), 0.001);
    assertEquals("[2024-03-21]",
            model.crossOvers(dataList).get("NEGATIVES").toString(), 0.001);
  }

  @Test
  public void performanceOfFlexiblePortfolioDay() throws ParseException {
    Map<LocalDate, List<Map<String, String>>> dataMap = new HashMap<>();
    List<Map<String, String>> data20240327 = new ArrayList<>();
    Map<String, String> data1 = new HashMap<>();
    data1.put("quantity", "34");
    data1.put("fileTimeStamp", "2024-03-27");
    data1.put("close", "54.8300");
    data1.put("open", "55.3600");
    data1.put("timestamp", "2021-10-15");
    data20240327.add(data1);

    Map<String, String> data2 = new HashMap<>();
    data2.put("quantity", "39");
    data2.put("fileTimeStamp", "2024-03-26");
    data2.put("close", "1215.4100");
    data2.put("open", "1249.7000");
    data2.put("timestamp", "2024-03-25");
    data20240327.add(data2);

    dataMap.put(LocalDate.parse("2024-03-27"), data20240327);

    List<Map<String, String>> data20200310 = new ArrayList<>();
    Map<String, String> data3 = new HashMap<>();
    data3.put("quantity", "20");
    data3.put("fileTimeStamp", "2024-03-27");
    data3.put("close", "56.5200");
    data3.put("open", "56.0700");
    data3.put("timestamp", "2020-10-15");
    data20200310.add(data3);

    Map<String, String> data4 = new HashMap<>();
    data4.put("quantity", "10");
    data4.put("fileTimeStamp", "2024-03-26");
    data4.put("close", "1280.3900");
    data4.put("open", "1260.0000");
    data4.put("timestamp", "2020-10-15");
    data20200310.add(data4);

    dataMap.put(LocalDate.parse("2020-03-10"), data20200310);

    assertEquals("", model.getPerformanceOfFlexiblePortfolio(LocalDate
            .parse("2020-03-08"), LocalDate.parse("2020-03-30"), dataMap));
  }

  @Test
  public void performanceOfFlexiblePortfolioMonth() throws ParseException {
    Map<LocalDate, List<Map<String, String>>> dataMap = new HashMap<>();
    List<Map<String, String>> data20240327 = new ArrayList<>();
    Map<String, String> data1 = new HashMap<>();
    data1.put("quantity", "34");
    data1.put("fileTimeStamp", "2024-03-27");
    data1.put("close", "54.8300");
    data1.put("open", "55.3600");
    data1.put("timestamp", "2021-10-15");
    data20240327.add(data1);

    Map<String, String> data2 = new HashMap<>();
    data2.put("quantity", "39");
    data2.put("fileTimeStamp", "2024-03-26");
    data2.put("close", "1215.4100");
    data2.put("open", "1249.7000");
    data2.put("timestamp", "2024-03-25");
    data20240327.add(data2);

    dataMap.put(LocalDate.parse("2024-03-27"), data20240327);

    List<Map<String, String>> data20200310 = new ArrayList<>();
    Map<String, String> data3 = new HashMap<>();
    data3.put("quantity", "20");
    data3.put("fileTimeStamp", "2024-03-27");
    data3.put("close", "56.5200");
    data3.put("open", "56.0700");
    data3.put("timestamp", "2020-10-15");
    data20200310.add(data3);

    Map<String, String> data4 = new HashMap<>();
    data4.put("quantity", "10");
    data4.put("fileTimeStamp", "2024-03-26");
    data4.put("close", "1280.3900");
    data4.put("open", "1260.0000");
    data4.put("timestamp", "2020-10-15");
    data20200310.add(data4);

    dataMap.put(LocalDate.parse("2020-03-10"), data20200310);

    assertEquals("", model.getPerformanceOfFlexiblePortfolio(LocalDate
            .parse("2020-03-08"), LocalDate.parse("2020-12-30"), dataMap));
  }


  @Test
  public void performanceOfFlexiblePortfolioYear() throws ParseException {
    Map<LocalDate, List<Map<String, String>>> dataMap = new HashMap<>();
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> data1 = new HashMap<>();
    data1.put("quantity", "34");
    data1.put("fileTimeStamp", "2024-03-27");
    data1.put("close", "54.8300");
    data1.put("open", "55.3600");
    data1.put("timestamp", "2021-10-15");
    data.add(data1);

    Map<String, String> data2 = new HashMap<>();
    data2.put("quantity", "39");
    data2.put("fileTimeStamp", "2024-03-26");
    data2.put("close", "1215.4100");
    data2.put("open", "1249.7000");
    data2.put("timestamp", "2024-03-25");
    data.add(data2);

    dataMap.put(LocalDate.parse("2024-03-27"), data);

    List<Map<String, String>> stockData = new ArrayList<>();
    Map<String, String> data3 = new HashMap<>();
    data3.put("quantity", "20");
    data3.put("fileTimeStamp", "2024-03-27");
    data3.put("close", "56.5200");
    data3.put("open", "56.0700");
    data3.put("timestamp", "2020-10-15");
    stockData.add(data3);

    Map<String, String> data4 = new HashMap<>();
    data4.put("quantity", "10");
    data4.put("fileTimeStamp", "2024-03-26");
    data4.put("close", "1280.3900");
    data4.put("open", "1260.0000");
    data4.put("timestamp", "2020-10-15");
    stockData.add(data4);

    dataMap.put(LocalDate.parse("2020-03-10"), stockData);

    assertEquals("{Mar 2020,25357.27, " +
                    "2020-03-26=26 Mar 2020,26720.25, " +
                    "2020-03-27=27 Mar 2020,25546.328}",
            model.getPerformanceOfFlexiblePortfolio(LocalDate
                            .parse("2020-03-08"),
                    LocalDate.parse("2024-03-08"), dataMap).toString());
  }

  //Batch Buy and dollar Cost Averaging
  @Test
  public void testInvestFixedAmount() throws ParseException {
    model.createFlexiblePortfolio("test");
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
    model.investFixedAmount(2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,4.274722,600.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,2.8498147,400.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());

  }

  @Test
  public void testInvestFixedInValidAmount() throws ParseException {
    model.createFlexiblePortfolio("test");

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
    model.investFixedAmount(-2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,-7.124537,-1000.0, " +
            "VZ,2024-01-03,138.6,140.36,18974308,-4.274722,-600.0, " +
            "AAAU,2024-01-03,138.6,140.36,18974308,-2.8498147,-400.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());

  }

  @Test
  public void testInvestFixedFractionalPercentage() throws ParseException {
    model.createFlexiblePortfolio("test");

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
    model.investFixedAmount(2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,7.195782,1010.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,4.203477,590.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,2.8498147,400.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());

  }

  @Test
  public void testInvestFixedInvalidPercentage() throws ParseException {
    model.createFlexiblePortfolio("test");

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
    model.investFixedAmount(2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,-7.195782,-1010.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,4.203477,590.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,2.8498147,400.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());

  }

  @Test(expected = ParseException.class)
  public void testInvestFixedInvalidDate() throws ParseException {
    model.createFlexiblePortfolio("test");

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
    model.investFixedAmount(2000f, data);
  }

  @Test
  public void testInvestFixedAmountPercentageGreaterThan100() throws ParseException {
    model.createFlexiblePortfolio("test");

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
    model.investFixedAmount(2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,7.124537,1000.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,2.8498147,400.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());

  }

  //Batch buy on an existing portfolio
  // to be worked on....
  @Test
  public void testInvestFixedAmountLoad() throws ParseException {
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
    List<String> shares = new ArrayList<>();
    shares.add("GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0,BUY");

    model.loadFlexiblePortfolio("fp1", shares, null, -1);

    model.investFixedAmount(2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,14.249074,2000.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,4.274722,600.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,2.8498147,400.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());
  }

  @Test
  public void testInvestFixedInValidAmountLoad() throws ParseException {

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
    List<String> shares = new ArrayList<>();
    shares.add("GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0,BUY");

    model.loadFlexiblePortfolio("fp1", shares, null, -1);
    model.investFixedAmount(-2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,0.0,0.0, " +
            "VZ,2024-01-03,138.6,140.36,18974308,-4.274722,-600.0, " +
            "AAAU,2024-01-03,138.6,140.36,18974308,-2.8498147,-400.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());

  }

  @Test
  public void testInvestFixedFractionalPercentageLoad() throws ParseException {

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
    List<String> shares = new ArrayList<>();
    shares.add("GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0,BUY");
    model.loadFlexiblePortfolio("fp1", shares, null, -1);

    model.investFixedAmount(2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,14.320319,2010.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,4.203477,590.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,2.8498147,400.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());

  }

  @Test
  public void testInvestFixedInvalidPercentageLoad() throws ParseException {
    model.createFlexiblePortfolio("test");

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
    List<String> shares = new ArrayList<>();
    shares.add("GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0,BUY");
    model.loadFlexiblePortfolio("fp1", shares, null, -1);
    model.investFixedAmount(2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,-7.195782,-1010.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,4.203477,590.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,2.8498147,400.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());

  }

  @Test(expected = ParseException.class)
  public void testInvestFixedInvalidDateLoad() throws ParseException {
    model.createFlexiblePortfolio("test");

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
    List<String> shares = new ArrayList<>();
    shares.add("GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0,BUY");
    model.loadFlexiblePortfolio("fp1", shares, null, -1);
    model.investFixedAmount(2000f, data);
  }

  @Test
  public void testInvestFixedAmountPercentageGreaterThan100Load() throws ParseException {
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

    List<String> shares = new ArrayList<>();
    shares.add("GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0,BUY");
    model.loadFlexiblePortfolio("fp1", shares, null, -1);
    model.investFixedAmount(2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,14.249074,2000.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,7.124537,1000.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,2.8498147,400.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());

  }

  //Dollar cost averaging test cases
  @Test
  public void testDollarCostAveraging() throws ParseException {
    model.createFlexiblePortfolio("test");
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

    model.dollarCostStrategy(2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,7.124537,1000.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,2.8498147,400.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());

  }

  @Test
  public void testDollarCostAveragingInvalidAmount() throws ParseException {
    model.createFlexiblePortfolio("test");
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

    model.dollarCostStrategy(-2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,-7.124537,-1000.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,-7.124537,-1000.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,-2.8498147,-400.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());

  }

  @Test
  public void testDollarCostAveragingFractionalPercentage() throws ParseException {
    model.createFlexiblePortfolio("test");
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

    model.dollarCostStrategy(2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,7.195782,1010.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,4.203477,590.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,2.8498147,400.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());
  }

  @Test(expected = ParseException.class)
  public void testDollarCostAveragingInvalidDate() throws ParseException {
    model.createFlexiblePortfolio("test");
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

    model.dollarCostStrategy(2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,7.195782,1010.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,4.203477,590.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,2.8498147,400.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());
  }

  @Test
  public void testDollarCostAveragingPercentageMoreThan100() throws ParseException {
    model.createFlexiblePortfolio("test");
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
    model.dollarCostStrategy(2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,7.124537,1000.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,2.8498147,400.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());
  }

  //Load portfolio - dollar cost averaging
  @Test
  public void testDollarCostAveragingLoad() throws ParseException {
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

    List<String> shares = new ArrayList<>();
    shares.add("GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0,BUY");
    model.loadFlexiblePortfolio("fp1", shares, data, 2000f);
    model.dollarCostStrategy(2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,21.373611,3000.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,8.549444,1200.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,5.6996293,800.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());

  }

  @Test
  public void testDollarCostAveragingInvalidAmountLoad() throws ParseException {
    model.createFlexiblePortfolio("test");
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
    List<String> shares = new ArrayList<>();
    shares.add("GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0,BUY");
    model.loadFlexiblePortfolio("fp1", shares, data, -2000f);
    model.dollarCostStrategy(-2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,-7.124537,-1000.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,-8.549444,-1200.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,-5.6996293,-800.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());

  }

  @Test
  public void testDollarCostAveragingFractionalPercentageLoad() throws ParseException {
    model.createFlexiblePortfolio("test");
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

    List<String> shares = new ArrayList<>();
    shares.add("GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0,BUY");
    model.loadFlexiblePortfolio("fp1", shares, data, 2000f);
    model.dollarCostStrategy(2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,7.2670274,1020.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,8.406954,-1180.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,5.6996293,-800.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());
  }

  @Test(expected = ParseException.class)
  public void testDollarCostAveragingInvalidDateLoad() throws ParseException {
    model.createFlexiblePortfolio("test");
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
    List<String> shares = new ArrayList<>();
    shares.add("GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0,BUY");
    model.loadFlexiblePortfolio("fp1", shares, data, 2000f);
    model.dollarCostStrategy(2000f, data);

  }

  @Test
  public void testDollarCostAveragingPercentageMoreThan100Load() throws ParseException {
    model.createFlexiblePortfolio("test");
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
    List<String> shares = new ArrayList<>();
    shares.add("GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0,BUY");
    model.loadFlexiblePortfolio("fp1", shares, data, 2000f);
    model.dollarCostStrategy(2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,21.373611,3000.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,14.249074,2000.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,5.6996293,800.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());
  }


  @Test
  public void testDollarCostAveragingLoadInvalidTicker() throws ParseException {
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "AKAJAKA");
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

    List<String> shares = new ArrayList<>();
    shares.add("GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0,BUY");
    model.loadFlexiblePortfolio("fp1", shares, data, 2000f);
    model.dollarCostStrategy(2000f, data);
    assertEquals("[GOOG,2024-01-03,138.6,140.36,18974308,7.124537,1000.0, " +
                    "VZ,2024-01-03,138.6,140.36,18974308,8.549444,1200.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,5.6996293,800.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());
  }

  @Test
  public void testInvestFixedAmountLoadInvalidTicker() throws ParseException {
    List<Map<String, String>> data = new ArrayList<>();
    Map<String, String> share1 = new HashMap<>();
    share1.put("volume", "18974308");
    share1.put("ticker", "ajakakksjsk");
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
    List<String> shares = new ArrayList<>();
    shares.add("alakalaab,2024-01-03,138.6,140.36,18974308,7.124537,1000.0,BUY");
    model.loadFlexiblePortfolio("fp1", shares, null, -1);
    model.investFixedAmount(2000f, data);
    assertEquals("VZ,2024-01-03,138.6,140.36,18974308,4.274722,600.0, " +
                    "AAAU,2024-01-03,138.6,140.36,18974308,2.8498147,400.0]",
            model.totalCompositionOfFlexiblePortfolio().toString());
  }
}