package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import model.ManagementModel;
import view.ManagementGUIView;

import static controller.ManagementOptions.dataOfShareForDate;

/**
 * The controller of the application that implements the Features interface and its methods,
 * using the graphical user interface view and the model of the application.
 */
public class GUIController implements Features {

  private final ManagementModel model;
  private ManagementGUIView view;
  FileOperation file;

  /**
   * The constructor takes in the model and initializes the model and the file to perform save and
   * load operations on the file.
   *
   * @param model model of the application.
   */
  public GUIController(ManagementModel model) {
    this.model = model;
    this.file = new FileOperation();
  }

  @Override
  public void setView(ManagementGUIView view) {
    this.view = view;
    this.view.addFeatures(this);
  }

  @Override
  public void createPortfolio(String portfolioName, JLabel label) {
    try {
      model.createFlexiblePortfolio(portfolioName);
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage(), label);
    }
  }

  @Override
  public boolean checkPortfolio(String portfolioName) {
    try {
      return file.doesFlexiblePortfolioExist(portfolioName);
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public void loadPortfolio(String portfolioName, JLabel label) {
    try {
      List<String> shares = file.importPortfolio(portfolioName, PortfolioTypeEnum.FLEXIBLE);
      List<String> strategy = file.importStrategy(portfolioName);
      List<Map<String, String>> finalData = new ArrayList<>();
      List<String> strategyList = new ArrayList<>();
      float amount = -1;
      if (strategy != null) {
        amount = file.fetchStrategyData(strategy, amount, finalData, strategyList);
      }
      model.loadFlexiblePortfolio(portfolioName, shares, finalData, amount);
      for (String strategyRow : strategyList) {
        model.addStrategyToList(strategyRow);
      }
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage(), label);
    }
  }

  @Override
  public Map<String, String> viewAllStocks(String stockName, JLabel label) {
    try {
      return file.getAllStocks(stockName);
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage(), label);
    }
    return null;
  }

  @Override
  public boolean checkStock(String stockName) {
    try {
      file.getShareByCompanyName(stockName);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public boolean isValidDate(String year, String month, String day) {
    try {
      String dateString = year + "-" + month + "-" + day;
      if (day.length() == 1) {
        dateString = year + "-" + month + "-0" + day;
      }
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MMMM-dd").
              withResolverStyle(ResolverStyle.STRICT);
      LocalDate date = LocalDate.parse(dateString, formatter);
      return true;
    } catch (Exception e) {
      return false;
    }
  }


  @Override
  public boolean checkShareOnDate(String year, String month, String day, String stockName) {
    try {
      DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd",
              Locale.ENGLISH);
      String formattedDate = formatDates(year, month, day).format(formatter2);
      dataOfShareForDate(file.getShareByCompanyName(stockName),
              formattedDate);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public LocalDate formatDates(String year, String month, String day) {
    String dateString = year + "-" + month + "-" + day;
    if (day.length() == 1) {
      dateString = year + "-" + month + "-0" + day;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMMM-dd",
            Locale.ENGLISH);
    LocalDate date = LocalDate.parse(dateString, formatter);
    return date;
  }

  @Override
  public float dailyStockPriceChange(String stockName, LocalDate date, JLabel label) {
    try {
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",
              Locale.ENGLISH);
      String formattedDate = date.format(dateTimeFormatter);
      Map<String, String> stockData = file.stockAnalysisDay(stockName, formattedDate);
      return model.stockAnalysisDay(stockData);
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage(), label);
      return 0;
    }
  }

  @Override
  public float periodStockPriceChange(String stockName, LocalDate startDate, LocalDate endDate,
                                      JLabel label) {
    try {
      DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd",
              Locale.ENGLISH);
      String formattedStartDate = startDate.format(formatter2);
      String formattedEndDate = endDate.format(formatter2);
      Map<String, String> stockData = file.stockAnalysisPeriod(stockName, formattedStartDate,
              formattedEndDate);
      return model.stockAnalysisPeriod(stockData);
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage(), label);
    }
    return 0;
  }

  @Override
  public float xDayMovingAverage(String stockName, Integer xDay, LocalDate date) {
    try {
      DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd",
              Locale.ENGLISH);
      String givenDate = date.format(formatter2);
      List<Map<String, String>> stockData = file.xDayMovingAverage(stockName, xDay, givenDate);
      return model.xDayMovingAverage(stockData);
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public Map<String, List<String>> crossovers(String stockName, LocalDate startDate,
                                              LocalDate endDate) {
    try {
      DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd",
              Locale.ENGLISH);
      String formattedStartDate = startDate.format(formatter2);
      String formattedEndDate = endDate.format(formatter2);
      List<Map<String, String>> stockData = file.crossovers(stockName, formattedStartDate,
              formattedEndDate);
      return model.crossOvers(stockData);
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public Map<String, List<String>> movingCrossovers(String stockName, int xDay, int yDay,
                                                    LocalDate startDate, LocalDate endDate) {
    try {
      DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd",
              Locale.ENGLISH);
      String formattedStartDate = startDate.format(formatter2);
      String formattedEndDate = endDate.format(formatter2);
      List<Map<String, String>> stockData = file.movingCrossovers(stockName,
              xDay, yDay, formattedStartDate, formattedEndDate);
      return model.movingCrossovers(stockData);
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public float getValueOfPortfolio(LocalDate date) {
    try {
      if (date.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Provide today's or before date");
      }
      List<Map<String, String>> values = file.getValueOfFlexiblePortfolio(date,
              model.totalCompositionOfFlexiblePortfolio());
      return model.totalValueOfFlexiblePortfolio(values);
    } catch (Exception e) {
      return 0;
    }

  }

  @Override
  public float getTotalInvestmentOfPortfolio(LocalDate date) {
    try {
      if (date.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Provide today's or before date");
      }
      return model.getTotalInvestment(date);
    } catch (Exception e) {
      return 0;
    }

  }

  @Override
  public List<String> getFlexiblePortfolios() {
    try {
      return file.getFlexiblePortfolios();
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

  @Override
  public List<String> getAllStrategies() {
    try {
      return file.getStrategies();
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }


  @Override
  public boolean isStartDateBeforeEndDate(String startYear, String startMonth,
                                          String startDay, String endYear, String endMonth,
                                          String endDay) {

    LocalDate startDate = formatDates(startYear, startMonth, startDay);
    LocalDate endDate = formatDates(endYear, endMonth, endDay);
    return startDate.isBefore(endDate);
  }

  @Override
  public void buyStock(String companyName, String quantity, LocalDate buyDate, JLabel label) {
    try {
      Map<String, String> data =
              dataOfShareForDate(file.getShareByCompanyName(companyName), String.valueOf(buyDate));
      this.model.buy(Integer.parseInt(quantity), data);
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage(), label);
    }
  }

  @Override
  public boolean checkQuantity(float quantity) {
    return quantity > 0;
  }

  @Override
  public void sellStock(String companyName, String quantity, LocalDate date, JLabel label) {
    try {
      Map<String, String> data = file.getShareByCompanyName(companyName).get(1);
      model.sell(Integer.parseInt(quantity), data.get("ticker"), date);
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage(), label);
    }
  }

  @Override
  public boolean savePortfolio(String portfolioName, JLabel label) {
    try {
      file.exportFlexiblePortfolio(model.getTransactionOfFlexiblePortfolio(), portfolioName);
      file.exportStrategy(model.getStrategy(), portfolioName + "_" + "strategy");
      return true;
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage(), label);
      return false;
    }
  }

  @Override
  public void totalComposition(JLabel label) {
    try {
      List<String> shares = model.totalCompositionOfFlexiblePortfolio();
      DefaultTableModel tableModel = this.view.generateTotalCompositionTable();
      for (String share : shares) {
        this.view.displayData(share, tableModel);
      }
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage(), label);
    }
  }

  @Override
  public void addToList(float amount,
                        List<Map<String, String>> list,
                        JLabel percentageLabel, JLabel errorLabel, Map<String, String> data) {
    try {
      if (Float.parseFloat(percentageLabel.getText().trim()) <= 100) {
        list.add(data);
      }
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage(), errorLabel);
    }
  }

  @Override
  public void investFixedAmount(List<Map<String, String>> list, Float amount, JLabel label) {
    try {
      model.investFixedAmount(amount, list);
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage(), label);
    }
  }


  @Override
  public Map<String, String> addDataToTable(DefaultTableModel tableModel,
                                            String company, String timestamp,
                                            String percentage, JLabel percentageLabel,
                                            JLabel errorLabel) {
    Map<String, String> data = null;
    try {
      float totalPercentage = Float.parseFloat(percentageLabel.getText()) +
              Float.parseFloat(percentage);
      if (totalPercentage > 100) {
        this.view.setErrorMessage("Percentage can't be more than 100", errorLabel);
        return null;
      } else {
        data = dataOfShareForDate(file.getShareByCompanyName(company), timestamp);
        data.put("percentage", percentage);
        String[] row = new String[]{company, timestamp, data.get("close"), percentage};
        percentageLabel.setText(String.valueOf(
                totalPercentage));
        addRow(tableModel, row, company, tableModel.getRowCount(), percentage);
      }
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage(), errorLabel);
    }
    return data;
  }

  @Override
  public void addRow(DefaultTableModel model, String[] row, String name, int rowCount,
                     String percentage) {
    for (int i = 0; i < rowCount; i++) {
      if (model.getValueAt(i, 0).equals(name)) {
        float existingPercentage = Float.parseFloat((String) model.getValueAt(i,
                model.getColumnCount() - 1));
        float newPercentage = existingPercentage + Float.parseFloat(percentage);
        model.setValueAt(String.valueOf(newPercentage), i, model.getColumnCount() - 1);
        return;
      }
    }

    model.addRow(row);
  }

  @Override
  public Map<String, String> getDataForStrategy(LocalDate currentDate,
                                                String companyName,
                                                List<Map<String,
                                                        String>> shareData, JLabel label) {
    try {
      return dataOfShareForDate(shareData, currentDate.toString());
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage(), label);
      return null;
    }
  }

  @Override
  public List<Map<String, String>> getShareData(String companyName, JLabel label) {
    try {
      return file.getShareByCompanyName(companyName);
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage(), label);
    }
    return null;
  }

  @Override
  public void addShareToSharesList(List<String> sharesByPercentage, String shares) {
    sharesByPercentage.add(shares);
  }

  @Override
  public void createStrategy(List<String> sharesByPercentage, float amount, LocalDate startDate,
                             LocalDate endDate, LocalDate currentDate, int period, List<Map<String,
          String>> sharesList, JLabel label) {
    try {
      String sharesPercent = sharesByPercentage.toString()
              .replace(',', ';');

      String strategy = startDate.toString() + "," + endDate.toString() + "," +
              currentDate.minusDays(period)
              + "," + period + "," + amount + "," + sharesPercent;
      this.model.addStrategyToList(strategy);
      this.model.dollarCostStrategy(amount, sharesList);
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage(), label);
    }
  }
}
