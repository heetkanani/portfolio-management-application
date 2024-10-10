package controller;


import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import enums.CommandLineEnum;
import model.ManagementModel;
import view.ManagementView;

/**
 * The management options where the user is able to see the operations that it can perform
 * while using the entire application.
 */
class ManagementOptions {

  /**
   * List all the available portfolios in the files (inflexible and flexible).
   *
   * @param view view of the application it interacts with.
   * @param file file I/O class as the input inorder to read and write the contents of the file.
   * @throws IOException I/O if an error occurs.
   */
  protected static void listAllPortfolios(
          ManagementView view, FileOperation file) throws IOException {
    try {

      view.setInputMessage("-------- FLEXIBLE PORTFOLIOS ------------");
      for (String portfolio : file.getFlexiblePortfolios()) {
        view.setInputMessage(portfolio);
      }
      view.setInputMessage("-------------------------------------------\n");
      view.setInputMessage("-------- INFLEXIBLE PORTFOLIOS ------------");
      for (String portfolio : file.getInflexiblePortfolios()) {
        view.setInputMessage(portfolio);
      }
      view.setInputMessage("-------------------------------------------\n");
    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }
  }


  /**
   * The user is able to view all the stocks that are supported by the system.
   *
   * @param view view of the application it interacts with.
   * @param file file I/O class as the input inorder to read and write the contents of the file.
   * @throws IOException I/O if an error occurs.
   */
  protected static void viewStocks(ManagementView view, FileOperation file) throws IOException {
    try {
      view.setInputMessage("Search ticker name from A-Z :  ");
      String companyTickerName = view.setInputString();
      view.setInputMessage("-----------------------------------------");
      view.setInputMessage("STOCKS\tCOMPANY NAME");
      for (Map.Entry<String, String> entry : file.getAllStocks(companyTickerName).entrySet()) {
        if (entry.getValue().isEmpty()) {
          view.setErrorMessage("No stock data found for: " + companyTickerName);
        }
        view.setInputMessage(entry.getKey() + "\t" + entry.getValue());
      }
      view.setInputMessage("-----------------------------------------");
    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }
  }

  /**
   * Loads the portfolio for the file based on the name of the portfolio provided by the user.
   *
   * @param view         view of the application it interacts with.
   * @param model        model of the application it interacts with.
   * @param file         file I/O class as the input inorder to read and write the
   *                     contents of the file.
   * @param inputMessage input message to be giving to the view to display it on the screen.
   * @return portfolio name after it loads the portfolio
   * @throws IOException    I/O if an error occurs.
   * @throws ParseException file is able to read and write correctly or not.
   */
  protected static String loadPortfolio(ManagementView view,
                                        ManagementModel model,
                                        FileOperation file,
                                        String inputMessage) throws IOException, ParseException {

    view.setInputMessage(inputMessage);
    String portfolioName = view.setInputString();
    List<String> shares = file.importPortfolio(portfolioName, PortfolioTypeEnum.NORMAL);
    List<Map<String, String>> sharesMap = file.extractData(shares);
    model.createPortfolio(portfolioName, sharesMap);
    view.setInputMessage(CommandLineEnum.SUCCESS.getStyle() +
            "\nPortfolio " + portfolioName + " loaded successfully.\n" +
            CommandLineEnum.RESET.getStyle());
    return portfolioName;
  }

  /**
   * Creates a new portfolio with the stocks added by the user in the portfolio.
   *
   * @param view          view of the application it interacts with.
   * @param portfolioName name of the portfolio provided by the user.
   * @param file          file I/O class as the input inorder to read and write
   *                      the contents of the file.
   * @return portfolio name after it loads the portfolio.
   * @throws IOException I/O if an error occurs.
   */
  protected static String createPortfolio(ManagementView view, String portfolioName,
                                          FileOperation file) throws IOException {
    view.setInputMessage("Enter the name of the portfolio: ");
    portfolioName = view.setInputString();
    if (file.doesInFlexiblePortfolioExist(portfolioName)) {
      throw new IllegalStateException(
              "Portfolio with " + portfolioName + " already exists."
      );
    }
    return portfolioName;
  }


  /**
   * Calculates the moving crossover of the stock over a period of time based on the period
   * provided by the user.
   *
   * @param view      view of the application it interacts with.
   * @param file      file I/O class as the input inorder to read and
   *                  write the contents of the file.
   * @param model     model of the application it interacts with.
   * @param stockName name of the stock on which stock analysis is to be performed.
   * @throws IOException              I/O if an error occurs.
   * @throws IllegalArgumentException whenever a wrong argument is passed by the user
   */
  protected static void stockMovingCrossover(ManagementView view,
                                             FileOperation file, ManagementModel model,
                                             String stockName)
          throws IOException, IllegalArgumentException {
    try {
      view.setInputMessage("Enter the starting date (YYYY-MM-DD):");
      String startDate = view.setInputString();
      view.setInputMessage("Enter the 'X' day: ");
      int xDay = view.setInputValue();
      view.setInputMessage("Enter the ending date (YYYY-MM-DD):");
      String endDate = view.setInputString();
      view.setInputMessage("Enter the 'Y' day: ");
      int yDay = view.setInputValue();
      if (xDay <= 0 || yDay < 0) {
        throw new IllegalArgumentException("Please enter a valid day.");
      }
      dateValidation(file, startDate, endDate);
      dataOfShareForDate(file.getShareByCompanyName(stockName), startDate);
      dataOfShareForDate(file.getShareByCompanyName(stockName), endDate);

      List<Map<String, String>> stockData = file.movingCrossovers(stockName,
              xDay, yDay, startDate, endDate);
      Map<String, List<String>> stockValue = model.movingCrossovers(stockData);
      getMovingCrossoverData(view, stockValue);

    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }
  }


  private static void getMovingCrossoverData(ManagementView view, Map<String,
          List<String>> stockValue) throws IOException {
    if (stockValue.get("POSITIVES").isEmpty()
            && stockValue.get("NEGATIVES").isEmpty()) {
      view.setErrorMessage("No moving crossover dates found for the period specified");
    } else {
      if (stockValue.get("POSITIVES").isEmpty()) {
        view.setErrorMessage("No moving crossover dates found for positive crossovers");
      } else {
        view.setInputMessage(CommandLineEnum.SUCCESS.getStyle() +
                "Positive Moving Crossover Dates: \n" + stockValue.get("POSITIVES").toString() +
                "\nYou can buy on this dates."
                + CommandLineEnum.RESET.getStyle());
      }
      if (stockValue.get("NEGATIVES").isEmpty()) {
        view.setErrorMessage("No moving crossover dates found for negative crossovers");
      } else {
        view.setErrorMessage("Negative Moving Crossover Dates: \n"
                + stockValue.get("NEGATIVES").toString() +
                "\nYou can sell on this dates.");
      }
    }
  }

  /**
   * Calculates the crossover of the stock over a period of time based on the period
   * provided by the user.
   *
   * @param view      view of the application it interacts with.
   * @param file      file I/O class as the input inorder to read
   *                  and write the contents of the file.
   * @param model     model of the application it interacts with.
   * @param stockName name of the stock on which stock analysis is to be performed.
   * @throws IOException              I/O if an error occurs.
   * @throws IllegalArgumentException whenever a wrong argument is passed by the user.
   */
  protected static void stockCrossover(ManagementView view, FileOperation file,
                                       ManagementModel model, String stockName) throws IOException,
          IllegalArgumentException {
    try {
      view.setInputMessage("Enter the starting date (YYYY-MM-DD):");
      String startDate = view.setInputString();
      view.setInputMessage("Enter the ending date (YYYY-MM-DD):");
      String endDate = view.setInputString();
      dateValidation(file, startDate, endDate);
      dataOfShareForDate(file.getShareByCompanyName(stockName), startDate);
      dataOfShareForDate(file.getShareByCompanyName(stockName), endDate);
      List<Map<String, String>> stockData = file.crossovers(stockName, startDate, endDate);
      Map<String, List<String>> stockValue = model.crossOvers(stockData);
      getCrossoverData(view, stockValue);

    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }
  }

  private static void dateValidation(FileOperation file,
                                     String startDate, String endDate)
          throws ParseException {
    if (file.parseDate(startDate).isAfter(file.parseDate(endDate))) {
      throw new IllegalArgumentException("Start date cannot be greater then End date");
    }
  }

  private static void getCrossoverData(ManagementView view, Map<String,
          List<String>> stockValue) throws IOException {
    if (stockValue.get("POSITIVES").isEmpty()
            && stockValue.get("NEGATIVES").isEmpty()) {
      view.setErrorMessage("No crossover dates found for the period specified");
    } else {
      if (stockValue.get("POSITIVES").isEmpty()) {
        view.setErrorMessage("No crossover dates found for positive crossovers");
      } else {
        view.setInputMessage(CommandLineEnum.SUCCESS.getStyle() +
                "Positive Crossover Dates: \n" + stockValue.get("POSITIVES").toString() +
                "\nYou can buy on this dates."
                + CommandLineEnum.RESET.getStyle());
      }
      if (stockValue.get("NEGATIVES").isEmpty()) {
        view.setErrorMessage("No crossover dates found for negative crossovers");
      } else {
        view.setErrorMessage("Negative Crossover Dates: \n"
                + stockValue.get("NEGATIVES").toString() +
                "\nYou can sell on this dates.");
      }
    }
  }

  /**
   * Calculates the X day moving average of the stock prices from the date provided by the user.
   *
   * @param view      view of the application it interacts with.
   * @param file      file I/O class as the input inorder to read and
   *                  write the contents of the file.
   * @param model     model of the application it interacts with.
   * @param stockName name of the stock on which stock analysis is to be performed.
   * @throws IOException I/O if an error occurs.
   */
  protected static void stockXDayMovingAverage(ManagementView view,
                                               FileOperation file, ManagementModel model,
                                               String stockName) throws IOException {
    try {
      view.setInputMessage("Enter the date (YYYY-MM-DD):");
      String givenDate = view.setInputString();
      view.setInputMessage("Enter the 'X' day: ");
      int xDay = view.setInputValue();
      if (xDay <= 0) {
        throw new IllegalArgumentException("Please enter a valid day.");
      }
      dataOfShareForDate(file.getShareByCompanyName(stockName), givenDate);
      List<Map<String, String>> stockData = file.xDayMovingAverage(stockName, xDay, givenDate);
      float stockValue = model.xDayMovingAverage(stockData);
      view.setInputMessage(CommandLineEnum.SUCCESS.getStyle() + "" + xDay +
              "-Day moving average for " + stockName + " is $" + stockValue +
              CommandLineEnum.RESET.getStyle());
    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }
  }

  /**
   * Calculates if over a given period of time did the stock gain or loss.
   *
   * @param view      view of the application it interacts with.
   * @param file      file I/O class as the input inorder to read and write the
   *                  contents of the file.
   * @param model     model of the application it interacts with.
   * @param stockName name of the stock on which stock analysis is to be performed.
   * @throws IOException I/O if an error occurs.
   */
  protected static void stockGainOrLossPeriod(ManagementView view,
                                              FileOperation file, ManagementModel model,
                                              String stockName) throws IOException {
    try {
      view.setInputMessage("Enter the starting date (YYYY-MM-DD):");
      String startDate = view.setInputString();
      view.setInputMessage("Enter the ending date (YYYY-MM-DD):");
      String endDate = view.setInputString();
      dateValidation(file, startDate, endDate);
      dataOfShareForDate(file.getShareByCompanyName(stockName), startDate);
      dataOfShareForDate(file.getShareByCompanyName(stockName), endDate);
      Map<String, String> stockData = file.stockAnalysisPeriod(stockName, startDate, endDate);
      float stockValue = model.stockAnalysisPeriod(stockData);
      if (stockValue > 0) {
        view.setInputMessage(CommandLineEnum.SUCCESS.getStyle() + "You may buy " + stockName +
                " as it has gained: +$" + stockValue + "\n" + CommandLineEnum.RESET.getStyle());
      } else if (stockValue < 0) {
        view.setErrorMessage("You may sell " + stockName +
                " as it has lost: $" + stockValue + "\n");
      }
    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }
  }

  /**
   * Calculates if the stock lose or gain for the particular date provided by the user.
   *
   * @param view      view of the application it interacts with.
   * @param file      file I/O class as the input inorder to read and write
   *                  the contents of the file.
   * @param model     model of the application it interacts with.
   * @param stockName name of the stock on which stock analysis is to be performed.
   * @throws IOException I/O if an error occurs.
   */
  protected static void stockGainOrLossDay(ManagementView view,
                                           FileOperation file, ManagementModel model,
                                           String stockName) throws IOException {
    try {
      view.setInputMessage("Enter the date (YYYY-MM-DD):");
      String givenDate = view.setInputString();
      dataOfShareForDate(file.getShareByCompanyName(stockName), givenDate);
      Map<String, String> stockData = file.stockAnalysisDay(stockName, givenDate);
      float stockValue = model.stockAnalysisDay(stockData);
      if (stockValue > 0) {
        view.setInputMessage(CommandLineEnum.SUCCESS.getStyle() + "You may buy " + stockName +
                " as it has gained: +$" + stockValue + "\n" + CommandLineEnum.RESET.getStyle());
      } else if (stockValue < 0) {
        view.setErrorMessage("You may sell " + stockName +
                " as it has lost: $" + stockValue + "\n");
      }
    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }

  }


  /**
   * Saves the portfolio onto the file with the necessary stocks.
   *
   * @param portfolioName    name of the portfolio that is to be saved onto the file.
   * @param sharesMap        share data read from the file
   * @param view             view of the application it interacts with.
   * @param file             file I/O class as the input inorder to read and
   *                         write the contents of the file.
   * @param model            model of the application it interacts with.
   * @param investStrategies the invested strategies added while saving the portfolio
   * @param type             type of the portfolio
   * @throws IOException    I/O if an error occurs.
   * @throws ParseException file is able to read and write correctly or not.
   */
  protected static void savePortfolio(
          String portfolioName, List<Map<String, String>> sharesMap,
          ManagementView view,
          FileOperation file, ManagementModel model,
          List<String> investStrategies, PortfolioTypeEnum type) throws IOException,
          ParseException {

    if (type == PortfolioTypeEnum.FLEXIBLE) {
      file.exportFlexiblePortfolio(model.getTransactionOfFlexiblePortfolio(), portfolioName);
      file.exportStrategy(investStrategies, portfolioName + "_" + "strategy");
    } else {
      model.createPortfolio(portfolioName, sharesMap);
      file.exportInflexiblePortfolio(model.totalComposition(), portfolioName);
    }
    view.setInputMessage(CommandLineEnum.SUCCESS.getStyle() +
            "\nPortfolio saved successfully.\n" + CommandLineEnum.RESET.getStyle());

  }

  /**
   * Get total composition of the portfolio i.e. view the data of the portfolio where it displays
   * the contents of the file.
   *
   * @param view  view of the application it interacts with.
   * @param model model of the application it interacts with.
   * @throws IOException I/O if an error occurs.
   */
  protected static void getTotalComposition(ManagementView view,
                                            ManagementModel model) throws IOException {
    view.setInputMessage("------------- PORTFOLIO COMPOSITION ---------------------");
    try {
      if (model.totalComposition().isEmpty()) {
        view.setErrorMessage("Please add stock to view the composition");
      }
      view.setInputMessage("\nTICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE");
      for (String share : model.totalComposition()) {
        view.setInputMessage(share.replace(",", "\t"));
      }
      view.setInputMessage("--------------------------------------------------------");
    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }
  }

  /**
   * Fetches the total value of the portfolio based on the date provided from the user.
   *
   * @param view  view of the application it interacts with.
   * @param file  file I/O class as the input inorder to read and write the contents of the file.
   * @param model model of the application it interacts with.
   * @throws IOException I/O if an error occurs.
   */
  protected static void getTotalValue(ManagementView view,
                                      FileOperation file, ManagementModel model)
          throws IOException {
    try {
      view.setInputMessage("Enter the date (YYYY-MM-DD): ");
      String date = view.setInputString();
      LocalDate formattedDate = file.parseDate(date);
      if (formattedDate.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Provide today's or before date");
      }
      List<Map<String, String>> values;
      values = file.getValue(formattedDate, model.totalComposition());
      view.setInputMessage("\nTotal value of portfolio on " +
              date + ": $" + model.totalValue(values) + "\n");
    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }
  }

  /**
   * User can add as many stocks as he wants and then the portfolio is created.
   *
   * @param view      view of the application it interacts with.
   * @param file      file I/O class as the input inorder to read and
   *                  write the contents of the file.
   * @param sharesMap map of shares data.
   * @throws IOException I/O if an error occurs.
   */
  protected static void addStock(ManagementView view,
                                 FileOperation file,
                                 List<Map<String, String>> sharesMap)
          throws IOException {
    String stockName;
    int stockQuantity;
    try {
      view.setInputMessage("Enter the name of the company: ");
      stockName = view.setInputString();
      view.setInputMessage("Enter the quantity of the stock: ");
      stockQuantity = view.setInputValue();
      if (stockQuantity <= 0) {
        throw new IllegalArgumentException("Please provide valid quantity.");
      }
      Map<String, String> data = file.getShareByCompanyName(stockName).get(1);
      data.put("quantity", String.valueOf(stockQuantity));
      sharesMap.add(data);
      view.setInputMessage(CommandLineEnum.SUCCESS.getStyle() + stockQuantity + " " +
              stockName + " stock/s " + " added successfully." +
              CommandLineEnum.RESET.getStyle());
    } catch (InputMismatchException e) {
      view.setInputMessage(CommandLineEnum.ERROR.getStyle() + "Please provide " +
              "valid quantity."
              + CommandLineEnum.RESET.getStyle());
      view.setNextLineMessage();
    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }
  }


  /**
   * Displays the performance of the individual stock that how it has
   * performed of the period of time.
   *
   * @param view        view of the application it interacts with.
   * @param file        file I/O class as the input inorder to read and
   *                    write the contents of the file.
   * @param model       model of the application it interacts with.
   * @param companyName name of the company to view performance of.
   * @throws IOException I/O if an error occurs.
   */
  protected static void getPerformanceOfStock(ManagementView view,
                                              FileOperation file, ManagementModel model,
                                              String companyName)
          throws IOException {
    String startDate;
    String endDate;
    try {
      view.setInputMessage("Enter the start date (YYYY-MM-DD): ");
      startDate = view.setInputString();
      LocalDate parsedStartDate = file.parseDate(startDate);
      view.setInputMessage("Enter the end date (YYYY-MM-DD): ");
      endDate = view.setInputString();
      LocalDate parsedEndDate = file.parseDate(endDate);
      if (parsedEndDate.isAfter(LocalDate.now()) || parsedStartDate.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Provide today's or before date");
      }
      if (parsedEndDate.isBefore(parsedStartDate)) {
        throw new IllegalArgumentException("Provide valid period.");
      }
      List<Map<String, String>> shareData = file.getShareByCompanyName(companyName);
      AbstractMap.SimpleEntry<Map<LocalDate, String>, Integer> performanceOfStock =
              model.getPerformanceOfStock(parsedStartDate, parsedEndDate, shareData);
      Map<LocalDate, String> values = performanceOfStock.getKey();
      float scale = performanceOfStock.getValue();
      view.displayGraph(values, companyName, startDate, endDate, (int) scale, "stock");
    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }
  }


  /**
   * Displays the performance of the stocks that are available in the flexible portfolio
   * how it has performed of the period of time.
   *
   * @param view          view of the application it interacts with.
   * @param file          file I/O class as the input inorder to read and
   *                      write the contents of the file.
   * @param model         model of the application it interacts with.
   * @param portfolioName name of the portfolio to view the performance of.
   * @throws IOException I/O if an error occurs.
   */
  protected static void getFlexiblePerformanceOfPortfolio(ManagementView view,
                                                          FileOperation file,
                                                          ManagementModel model,
                                                          String portfolioName)
          throws IOException {
    String startDate;
    String endDate;
    try {
      view.setInputMessage("Enter the start date (YYYY-MM-DD): ");
      startDate = view.setInputString();
      LocalDate parsedStartDate = file.parseDate(startDate);
      view.setInputMessage("Enter the end date (YYYY-MM-DD): ");
      endDate = view.setInputString();
      LocalDate parsedEndDate = file.parseDate(endDate);
      if (parsedEndDate.isAfter(LocalDate.now()) || parsedStartDate.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Provide today's or before date");
      }
      if (parsedEndDate.isBefore(parsedStartDate)) {
        throw new IllegalArgumentException("Provide valid period.");
      }
      Map<LocalDate, List<Map<String, String>>> map = new HashMap<>();
      LocalDate currentDate = parsedStartDate;
      while (currentDate.isBefore(parsedEndDate)) {
        map.put(currentDate, file.getValue(currentDate,
                model.totalCompositionOfFlexiblePortfolio()
        ));
        currentDate = currentDate.plusDays(1);
      }
      Map<LocalDate, String> values = model
              .getPerformanceOfFlexiblePortfolio(parsedStartDate, parsedEndDate, map).getKey();
      Integer scale = model.getPerformanceOfFlexiblePortfolio(parsedStartDate,
              parsedEndDate, map).getValue();
      view.displayGraph(values, portfolioName, startDate, endDate, scale, "portfolio");
    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }
  }

  /**
   * Displays the performance of the stocks that are available in the inflexible portfolio
   * how it has performed of the period of time.
   *
   * @param view          view of the application it interacts with.
   * @param file          file I/O class as the input inorder to read and
   *                      write the contents of the file.
   * @param model         model of the application it interacts with.
   * @param portfolioName name of the portfolio to view the performance of.
   * @throws IOException I/O if an error occurs.
   */
  protected static void getInFlexiblePerformanceOfPortfolio(ManagementView view,
                                                            FileOperation file,
                                                            ManagementModel model,
                                                            String portfolioName)
          throws IOException {
    String startDate;
    String endDate;
    try {
      view.setInputMessage("Enter the start date (YYYY-MM-DD): ");
      startDate = view.setInputString();
      LocalDate parsedStartDate = file.parseDate(startDate);
      view.setInputMessage("Enter the end date (YYYY-MM-DD): ");
      endDate = view.setInputString();
      LocalDate parsedEndDate = file.parseDate(endDate);
      if (parsedEndDate.isAfter(LocalDate.now()) || parsedStartDate.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Provide today's or before date");
      }
      if (parsedEndDate.isBefore(parsedStartDate)) {
        throw new IllegalArgumentException("Provide valid period.");
      }
      Map<LocalDate, List<Map<String, String>>> map = new HashMap<>();
      LocalDate currentDate = parsedStartDate;
      while (currentDate.isBefore(parsedEndDate)) {
        map.put(currentDate, file.getValue(currentDate,
                model.totalComposition()
        ));
        currentDate = currentDate.plusDays(1);
      }
      Map<LocalDate, String> values =
              model.getPerformanceOfInFlexiblePortfolio(parsedStartDate,
                      parsedEndDate, map).getKey();
      Integer scale =
              model.getPerformanceOfInFlexiblePortfolio(parsedStartDate,
                      parsedEndDate, map).getValue();
      view.displayGraph(values, portfolioName, startDate, endDate, scale, "portfolio");
    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }
  }

  /**
   * Calculates the total amount until today has been invested in the portfolio stocks.
   *
   * @param view  view of the application it interacts with.
   * @param file  file I/O class as the input inorder to read and write the contents of the file.
   * @param model model of the application it interacts with.
   * @throws IOException I/O if an error occurs.
   */
  protected static void getTotalInvestment(ManagementView view,
                                           FileOperation file, ManagementModel model)
          throws IOException {
    String date;
    try {
      view.setInputMessage("Enter the date (YYYY-MM-DD): ");
      date = view.setInputString();
      LocalDate formattedDate = file.parseDate(date);
      if (formattedDate.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Provide today's or before date");
      }
      float investment = model.getTotalInvestment(formattedDate);
      view.setInputMessage("\nTotal Investment on " + date + " is $" + investment + "\n");
    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }
  }

  /**
   * Calculates the total value of the flexible portfolio stocks based on the given date.
   *
   * @param view          view of the application it interacts with.
   * @param file          file I/O class as the input inorder to read and write
   *                      the contents of the file.
   * @param model         model of the application it interacts with.
   * @param portfolioName name of the portfolio to view the performance of.
   * @throws IOException I/O if an error occurs.
   */
  protected static void getTotalValueOfFlexiblePortfolio(ManagementView view,
                                                         FileOperation file, ManagementModel model,
                                                         String portfolioName)
          throws IOException {
    try {
      view.setInputMessage("Enter the date (YYYY-MM-DD): ");
      String date = view.setInputString();
      LocalDate formattedDate = file.parseDate(date);
      if (formattedDate.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Provide today's or before date");
      }
      List<Map<String, String>> values;
      values = file.getValueOfFlexiblePortfolio(formattedDate,
              model.totalCompositionOfFlexiblePortfolio());
      view.setInputMessage("\nTotal value of portfolio on " +
              date + ": $" + model.totalValueOfFlexiblePortfolio(values) + "\n");
    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }
  }


  /**
   * Allows the user to buy as many stocks as he wants to on a particular date.
   *
   * @param view          view of the application it interacts with.
   * @param file          file I/O class as the input inorder to read and
   *                      write the contents of the file.
   * @param model         model of the application it interacts with.
   * @param portfolioName name of the portfolio to view the performance of.
   * @throws IOException I/O if an error occurs.
   */
  protected static void buyStock(ManagementView view,
                                 FileOperation file, ManagementModel model,
                                 String portfolioName) throws IOException {
    String stockName;
    int stockQuantity;
    String date;
    try {
      view.setInputMessage("Enter the name of the company: ");
      stockName = view.setInputString();
      view.setInputMessage("Enter the quantity of the stock: ");
      stockQuantity = view.setInputValue();
      view.setInputMessage("Enter the date when you want to add: ");
      date = view.setInputString();
      LocalDate formattedDate = file.parseDate(date);
      if (formattedDate.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Provide today's or before date");
      }
      if (stockQuantity <= 0) {
        throw new IllegalArgumentException("Please provide valid quantity.");
      }
      Map<String, String> data =
              dataOfShareForDate(file.getShareByCompanyName(stockName), date);
      model.buy(stockQuantity, data);
      view.setInputMessage(CommandLineEnum.SUCCESS.getStyle() + stockQuantity + " " +
              stockName + " stock/s " + " bought successfully." +
              CommandLineEnum.RESET.getStyle());
      view.setInputMessage("---------------------------------------------------------");
      view.setInputMessage("\nTICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE");
      for (String share : model.totalCompositionOfFlexiblePortfolio()) {
        view.setInputMessage(share.replace(",", "\t"));
      }
      view.setInputMessage("---------------------------------------------------------");
    } catch (InputMismatchException e) {
      view.setInputMessage(CommandLineEnum.ERROR.getStyle() + "Please provide " +
              "valid quantity."
              + CommandLineEnum.RESET.getStyle());
      view.setNextLineMessage();
    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }

  }


  /**
   * The total stocks that are in the portfolio can be views using the
   * composition of the portfolio.
   *
   * @param view  view of the application it interacts with.
   * @param model model of the application it interacts with.
   * @throws IOException I/O if an error occurs.
   */
  protected static void getTotalCompositionOfFlexiblePortfolio(ManagementView view,
                                                               ManagementModel model)
          throws IOException {
    view.setInputMessage("------------- PORTFOLIO COMPOSITION ---------------------");
    try {
      if (model.totalCompositionOfFlexiblePortfolio().isEmpty()) {
        view.setErrorMessage("Please add stock to view the composition");
      }
      view.setInputMessage("\nTICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE");
      for (String share : model.totalCompositionOfFlexiblePortfolio()) {
        view.setInputMessage(share.replace(",", "\t"));
      }
      view.setInputMessage("--------------------------------------------------------");
    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }
  }

  /**
   * Allows the user to sell as many stocks as he wants to on a particular date.
   *
   * @param view  view of the application it interacts with.
   * @param file  file I/O class as the input inorder to read and write the contents of the file.
   * @param model model of the application it interacts with.
   * @throws IOException I/O if an error occurs.
   */
  protected static void sellStock(ManagementView view,
                                  FileOperation file, ManagementModel model)
          throws IOException {
    String stockName;
    int stockQuantity;
    String date;
    try {
      view.setInputMessage("Enter the name of the stock: ");
      stockName = view.setInputString();
      view.setInputMessage("Enter the quantity of the stock: ");
      stockQuantity = view.setInputValue();
      view.setInputMessage("Enter the date when you want to sell: ");
      date = view.setInputString();
      LocalDate formattedDate = file.parseDate(date);
      if (formattedDate.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Provide today's or before date");
      }
      Map<String, String> data = file.getShareByCompanyName(stockName).get(1);
      model.sell(stockQuantity, data.get("ticker"), formattedDate);
      view.setInputMessage(CommandLineEnum.SUCCESS.getStyle() + stockQuantity + " " +
              stockName + " stock/s " + " sold successfully." +
              CommandLineEnum.RESET.getStyle());
      view.setInputMessage("---------------------------------------------------------");
      view.setInputMessage("\nTICKER\tDATE\tOPEN\tCLOSE\tVOLUME\tQUANTITY\tBUY VALUE");
      for (String share : model.totalCompositionOfFlexiblePortfolio()) {
        view.setInputMessage(share.replace(",", "\t"));
      }
      view.setInputMessage("---------------------------------------------------------");

    } catch (Exception e) {
      view.setErrorMessage(e.getMessage());
    }

  }

  /**
   * Creates a portfolio along with tha stock addition as many as user wants to.
   *
   * @param view          view of the application it interacts with.
   * @param file          file I/O class as the input inorder to read and
   *                      write the contents of the file.
   * @param model         model of the application it interacts with.
   * @param portfolioName name of the portfolio to view the performance of.
   * @return name of the portfolio which is created.
   * @throws IOException    I/O if an error occurs.
   * @throws ParseException file is able to read and write correctly or not.
   */
  protected static String createFlexiblePortfolio(ManagementView view,
                                                  FileOperation file, ManagementModel model,
                                                  String portfolioName) throws IOException,
          ParseException {
    view.setInputMessage("Enter the name of the portfolio: ");
    portfolioName = view.setInputString();
    if (file.doesFlexiblePortfolioExist(portfolioName)) {
      throw new IllegalStateException(
              "Portfolio with " + portfolioName + " already exists."
      );
    }
    model.createFlexiblePortfolio(portfolioName);
    return portfolioName;
  }


  /**
   * Loads tha portfolio based on the name provided and can fetch the value and total composition
   * after the loading os the portfolio.
   *
   * @param view         view of the application it interacts with.
   * @param file         file I/O class as the input inorder to read and
   *                     write the contents of the file.
   * @param model        model of the application it interacts with.
   * @param inputMessage the message that is to be sent to the view to display it.
   * @return name of the portfolio.
   * @throws IOException    I/O if an error occurs.
   * @throws ParseException file is able to read and write correctly or not.
   */
  protected static String loadFlexiblePortfolio(ManagementView view,
                                                FileOperation file, ManagementModel model,
                                                String inputMessage)
          throws IOException, ParseException {

    view.setInputMessage(inputMessage);
    String portfolioName = view.setInputString();
    List<String> shares = file.importPortfolio(portfolioName, PortfolioTypeEnum.FLEXIBLE);
    List<String> strategy = file.importStrategy(portfolioName);
    List<Map<String, String>> finalData = new ArrayList<>();
    List<String> strategyList = new ArrayList<>();
    float amount = -1;
    if (strategy != null) {
      for (String strategyRow : strategy) {
        String[] row = strategyRow.split(",");
        LocalDate lastUpdated = file.parseDate(row[2]);
        LocalDate endDate = file.parseDate(row[1]);
        amount = Float.parseFloat(row[3]);
        String[] sharesRow = row[5].split(";");
        sharesRow[0] = sharesRow[0].replace("[", "");
        sharesRow[sharesRow.length - 1] =
                sharesRow[sharesRow.length - 1].replace("]", "");
        Map<String, String> sharesPercentageData = new HashMap<>();
        for (String share : sharesRow) {
          String[] shareData = share.split(":");
          sharesPercentageData.put(shareData[0], shareData[1]);
        }
        int period = Integer.parseInt(row[3]);
        if (LocalDate.now().isBefore(endDate) && LocalDate.now().isAfter(lastUpdated)) {
          long daysDifference = ChronoUnit.DAYS.between(lastUpdated, LocalDate.now());
          if (daysDifference >= period) {
            LocalDate currentDate = lastUpdated;
            while (currentDate.isBefore(LocalDate.now())) {
              for (Map.Entry<String, String> share : sharesPercentageData.entrySet()) {
                try {
                  Map<String, String> map =
                          dataOfShareForDate(file.getShareByCompanyName(share.getKey()),
                                  currentDate.toString());
                  map.put("percentage", share.getValue());
                  sharesRow[2] = currentDate.toString();
                  finalData.add(map);
                } catch (Exception e) {
                  Map<String, String> map =
                          dataOfShareForDate(file.getShareByCompanyName(share.getKey()),
                                  currentDate.plusDays(1).toString());
                  map.put("percentage", share.getValue());
                  sharesRow[2] = currentDate.toString();
                  finalData.add(map);
                }
              }
              currentDate = currentDate.plusDays(period);
            }
          }
        }
        strategyList.add(strategyRow);
      }
    }
    model.loadFlexiblePortfolio(portfolioName, shares, finalData, amount);
    for (String strategyRow : strategyList) {
      model.addStrategyToList(strategyRow);
    }
    view.setInputMessage(CommandLineEnum.SUCCESS.getStyle() +
            "\nPortfolio " + portfolioName + " loaded successfully.\n" +
            CommandLineEnum.RESET.getStyle());
    return portfolioName;
  }


  /**
   * Fetches the data of the stock based on the date specified.
   *
   * @param dataList fetch the data of the stock
   * @param date     date to be match if data is present on that day from the file.
   * @return data of the share available on the date specified.
   * @throws IOException I/O if an error occurs.
   */
  protected static Map<String, String> dataOfShareForDate(List<Map<String, String>>
                                                                  dataList, String date)
          throws IOException {
    for (Map<String, String> data : dataList) {
      if (data.get("timestamp").equals(date)) {
        return data;
      }
    }
    throw new IOException("Share not found on this " + date);
  }

  /**
   * Calculates the fixed amount that is invested in the portfolio over a given period of time
   * by the investor.
   *
   * @param file  file operation
   * @param view  view of the application.
   * @param model model of the application.
   * @throws ParseException if file not found than this exception occurs.
   * @throws IOException    I/O file exception.
   */
  protected static void calculateInvestFixedAmount(FileOperation file, ManagementView view,
                                                   ManagementModel model) throws IOException,
          ParseException {
    float percentage = 0;
    List<Map<String, String>> sharesList = new ArrayList<>();
    view.setInputMessage("Enter amount you want to invest in $");
    float amount = view.setInputValue();
    while (percentage < 100) {
      view.setInputMessage("Enter share name : ");
      String share = view.setInputString();
      view.setInputMessage("Enter percentage of share : ");
      float p = view.setInputValue();
      view.setInputMessage("Enter date : ");
      String date = view.setInputString();
      Map<String, String> data =
              dataOfShareForDate(file.getShareByCompanyName(share), date);
      data.put("percentage", String.valueOf(p));
      sharesList.add(data);
      percentage += p;
    }
    model.investFixedAmount(amount, sharesList);

  }

  /**
   * Calculates the dollar cost averaging of the particular portfolio based on the strategy created
   * by the user, where the shares value gets updated each time based on the period specified by
   * the user.
   *
   * @param file  file operation
   * @param view  view of the application.
   * @param model model of the application.
   * @throws ParseException if file not found than this exception occurs.
   * @throws IOException    I/O file exception.
   */
  protected static void calculatedDollarCostAveraging(FileOperation file, ManagementView view,
                                                      ManagementModel model)
          throws ParseException, IOException {
    view.setInputMessage("Enter start date: ");
    LocalDate startDate = file.parseDate(view.setInputString());
    view.setInputMessage("Enter end date or Enter (none) for no end date:  ");
    LocalDate endDate;
    if (view.setInputString().equalsIgnoreCase("none")) {
      endDate = LocalDate.now().plusYears(100);
    } else {
      endDate = file.parseDate(view.setInputString());
    }
    view.setInputMessage("Enter period in days: ");
    int period = view.setInputValue();
    float percentage = 0;
    List<String> sharesByPercentage = new ArrayList<>();
    List<Map<String, String>> sharesList = new ArrayList<>();
    view.setInputMessage("Enter amount you want to invest in $");
    float amount = view.setInputValue();
    LocalDate currentDate = startDate;
    while (percentage < 100) {
      view.setInputMessage("Enter share name : ");
      String share = view.setInputString();
      view.setInputMessage("Enter percentage of share : ");
      float p = view.setInputValue();
      if (p > 100) {
        view.setErrorMessage("Enter valid percentage");
        continue;
      }
      List<Map<String, String>> shareData = file.getShareByCompanyName(share);
      currentDate = startDate;
      while (currentDate.isBefore(LocalDate.now())) {
        try {
          Map<String, String> data =
                  dataOfShareForDate(shareData,
                          currentDate.toString());
          data.put("percentage", String.valueOf(p));
          sharesList.add(data);
          currentDate = currentDate.plusDays(period);
        } catch (Exception e) {
          currentDate = currentDate.plusDays(1);
        }
      }
      sharesByPercentage.add(share + ":" + p);
      percentage += p;
    }
    String sharesPercent = sharesByPercentage.toString().
            replace(',', ';');

    String strategy = startDate.toString() + "," + endDate.toString() + "," +
            currentDate.minusDays(period).toString()
            + "," + period + "," + amount + "," + sharesPercent;
    model.addStrategyToList(strategy);
    model.dollarCostStrategy(amount, sharesList);
  }
}