
package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import static controller.ManagementOptions.dataOfShareForDate;

/**
 * The class represents all the file operations and the operations that are performed on the
 * file like reading and writing.
 */
class FileOperation {

  API api;

  /**
   * Constructs the file operations inorder to perform the file read and write operations and
   * creating an object of the API inorder for it to use its methods.
   */
  public FileOperation() {
    api = new APIImpl();
  }

  protected Map<String, String> configReader() throws IOException {
    Properties configProperties = new Properties();
    FileInputStream fileInputStream = new FileInputStream(
            System.getProperty("user.dir")
                    + File.separator + "config.properties");
    configProperties.load(fileInputStream);
    Map<String, String> configMap = new HashMap<>();

    for (String key : configProperties.stringPropertyNames()) {
      String value = configProperties.getProperty(key);
      configMap.put(key, value);
    }
    return configMap;
  }

  /**
   * Extracts the data from the file and stores it in a map data structure.
   *
   * @param shares a list of shares array with different data values.
   * @return map of shares.
   */
  protected List<Map<String, String>> extractData(List<String> shares) {
    List<Map<String, String>> sharesMap = new ArrayList<>();
    for (String share : shares) {
      String[] row = share.split(",");
      Map<String, String> map = new HashMap<>();
      map.put("ticker", row[0]);
      map.put("timestamp", row[1]);
      map.put("open", row[2]);
      map.put("close", row[3]);
      map.put("volume", row[4]);
      map.put("quantity", row[5]);
      sharesMap.add(map);
    }
    return sharesMap;
  }


  private List<String> readerFile(BufferedReader reader) throws IllegalStateException,
          IOException {
    List<String> shares = new ArrayList<>();
    String line;
    while ((line = reader.readLine()) != null) {
      String[] row = line.split(",");
      fileFormatChecker(row);
      shares.add(line);
    }
    if (shares.isEmpty()) {
      throw new IllegalStateException("Your Portfolio is empty. Please enter some stocks");
    }
    return shares;
  }

  private List<String> readStrategyFile(BufferedReader reader) throws IllegalStateException,
          IOException {
    List<String> shares = new ArrayList<>();
    String line;
    while ((line = reader.readLine()) != null) {
      String[] row = line.split(",");
      shares.add(line);
    }
    if (shares.isEmpty()) {
      throw new IllegalStateException("Your Portfolio is empty. Please enter some stocks");
    }
    return shares;
  }

  /**
   * Exports the normal portfolio and saves it onto the file with the required data points.
   *
   * @param shares        list of shares.
   * @param portfolioName name of the portfolio.
   * @throws IOException if the file is not saved properly.
   */
  protected void exportInflexiblePortfolio(List<String> shares,
                                           String portfolioName) throws IOException {
    exportPortfolio(shares, "INFLEXIBLE_PORTFOLIOS_PATH", portfolioName);
  }

  /**
   * Exports the flexible portfolio and saves it onto the file with the required data points.
   *
   * @param shares        list of shares.
   * @param portfolioName name of the portfolio.
   * @throws IOException if the file is not saved properly.
   */
  protected void exportFlexiblePortfolio(List<String> shares,
                                         String portfolioName) throws IOException {
    exportPortfolio(shares, "FLEXIBLE_PORTFOLIOS_PATH", portfolioName);
  }

  protected void exportStrategy(List<String> strategy, String portfolioName)
          throws IOException {
    exportPortfolio(strategy, "STRATEGIES_PATH", portfolioName);
  }

  private void exportPortfolio(List<String> shares,
                               String portfolioPath, String portfolioName) throws IOException {
    if (shares.isEmpty() && !portfolioPath.equals("STRATEGIES_PATH")) {
      throw new IllegalStateException("Your portfolio is empty.");
    }
    File directory = new File(
            System.getProperty("user.dir") + File.separator +
                    configReader().get("PORTFOLIOS_PATH") +
                    File.separator + configReader().get(portfolioPath));
    directory.mkdir();
    try (FileWriter fileWriter =
                 new FileWriter(new File(directory, portfolioName + ".csv"))) {
      for (String share : shares) {
        fileWriter.write(share + "\n");
      }
    } catch (IOException e) {
      throw new IOException("An error occurred while saving the file.");
    }
  }

  /**
   * The method imports the portfolio of given name i.e. reads the data from the given file.
   *
   * @param portfolioName The name of portfolio.
   * @param portfolioType type of the portfolio.
   * @return List of shares.
   * @throws IOException If the portfolio of given name doesn't exist.
   */
  protected List<String> importPortfolio(String portfolioName,
                                         PortfolioTypeEnum portfolioType) throws IOException {
    File directory;
    if (portfolioType == PortfolioTypeEnum.NORMAL) {
      directory = new File(System.getProperty("user.dir") + File.separator +
              configReader().get("PORTFOLIOS_PATH") +
              File.separator + configReader().get("INFLEXIBLE_PORTFOLIOS_PATH"));
    } else {
      directory = new File(System.getProperty("user.dir") + File.separator
              + configReader().get("PORTFOLIOS_PATH") +
              File.separator + configReader().get("FLEXIBLE_PORTFOLIOS_PATH"));
    }
    File[] files = directory.listFiles();
    if (files != null && files.length != 0) {
      for (File file : files) {
        if (file.isFile()) {
          int lastIdx = file.toString().split("\\\\").length - 1;
          String fileName = file.toString().split("\\\\")[lastIdx];
          if (fileName.equalsIgnoreCase(portfolioName + ".csv")) {
            BufferedReader reader = new BufferedReader(new FileReader(new File(System
                    .getProperty("user.dir") + File.separator +
                    configReader().get("PORTFOLIOS_PATH") +
                    File.separator + configReader()
                    .get(portfolioType == PortfolioTypeEnum.NORMAL ?
                            "INFLEXIBLE_PORTFOLIOS_PATH" : "FLEXIBLE_PORTFOLIOS_PATH"),
                    portfolioName + ".csv")));
            return readerFile(reader);
          }
        }
      }
      throw new FileNotFoundException("Portfolio " + portfolioName + " doesn't exists.");
    } else {
      throw new FileNotFoundException("Portfolio " + portfolioName + " doesn't exists.");
    }
  }


  protected List<String> importStrategy(String portfolioName) throws IOException {
    if (!isStrategy(portfolioName).isEmpty()) {
      BufferedReader reader =
              new BufferedReader(new FileReader(new File(System
                      .getProperty("user.dir") + File.separator +
                      configReader().get("PORTFOLIOS_PATH") +
                      File.separator + configReader()
                      .get("STRATEGIES_PATH"), isStrategy(portfolioName))));
      return readStrategyFile(reader);
    }
    return null;
  }


  private String isStrategy(String portfolioName) throws IOException {
    File directory = new File(System.getProperty("user.dir") + File.separator
            + configReader().get("PORTFOLIOS_PATH") +
            File.separator + configReader().get("STRATEGIES_PATH"));
    File[] files = directory.listFiles();
    if (files != null && files.length != 0) {
      for (File file : files) {
        if (file.isFile()) {
          int lastIdx = file.toString().split("\\\\").length - 1;
          String fileName = file.toString().split("\\\\")[lastIdx].split("_")[0];
          if (fileName.equalsIgnoreCase(portfolioName)) {
            return file.toString().split("\\\\")[lastIdx];
          }
        }
      }
    }
    return "";
  }

  private void fileFormatChecker(String[] row) throws IOException {
    Matcher matcher;
    // 6 to 7 for new column
    if (row.length != 8) {
      throw new IOException("Data is missing in the file");
    }
    Pattern validTickerPattern = Pattern.compile("^[a-zA-z]+$");
    matcher = validTickerPattern.matcher(row[0]);
    if (!matcher.find()) {
      throw new IOException("Ticker name is not valid. It should be alphabetic.");
    }
    try {
      parseDate(row[1]);
    } catch (ParseException e) {
      throw new IOException("Provide a valid date");
    }
    try {
      if (Float.parseFloat(row[2]) < 0 || Float.parseFloat(row[3]) < 0) {
        throw new IOException("Provide valid price of stock.");
      }
    } catch (NumberFormatException e) {
      throw new IOException("Provide valid price of stock.");
    }
    try {
      //
      if (Long.parseLong(row[4]) < 0 || Float.parseFloat(row[5]) < 0 ||
              Float.parseFloat(row[6]) < 0) {
        throw new IOException("Provide valid quantity.");
      }
    } catch (NumberFormatException e) {
      throw new IOException("Provide valid quantity.");
    }
  }

  /**
   * The method converts the String date to LocalDate format according to the application.
   *
   * @param date The String date given in "yyyy-MM-dd" format.
   * @return formatted date in LocalDate type.
   * @throws ParseException if the date provided is not valid.
   */
  protected LocalDate parseDate(String date) throws ParseException {
    LocalDate formattedDate;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      dateFormat.setLenient(false);
      formattedDate = dateFormat.parse(date).toInstant()
              .atZone(ZoneId.systemDefault()).toLocalDate();
    } catch (ParseException e) {
      throw new ParseException("Provide a valid date", 1);
    }
    return formattedDate;
  }

  private BufferedReader readFile(String fileName, String parentName)
          throws FileNotFoundException {
    try {
      return new BufferedReader(
              new FileReader(new File(parentName, fileName)));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File not found");
    }
  }

  protected void generateFile(List<String[]> output, String ticker)
          throws ParserConfigurationException, IOException {
    if (output.isEmpty()) {
      throw new IllegalStateException("File is Empty");
    }
    File directory = new File(configReader().get("STOCK_DATA_PATH"));
    directory.mkdir();
    try (FileWriter fileWriter = new FileWriter(new File(directory,
            ticker + "_" + output.get(1)[0] + ".csv"))) {
      for (int i = 1; i < output.size(); i++) {
        StringBuilder rowBuilder = new StringBuilder();
        for (String s : output.get(i)) {
          rowBuilder.append(s);
          rowBuilder.append(",");
        }
        rowBuilder.deleteCharAt(rowBuilder.length() - 1);
        rowBuilder.append("\n");
        fileWriter.write(rowBuilder.toString());
      }
    } catch (IOException e) {
      throw new IOException("An error occurred while writing the contents onto the file.");
    }
  }

  /**
   * The method returns the key-value map of ticker and company name reading
   * the data from the file.
   *
   * @param startsWith The prefix or letter of the ticker needed to get the data.
   * @return key-value map of ticker and the company name.
   * @throws IOException If an I/O error occurs.
   */
  protected Map<String, String> getAllStocks(String startsWith) throws IOException {
    Map<String, String> tickerMap = new HashMap<>();
    try {
      BufferedReader reader = readFile("stockData.csv",
              System.getProperty("user.dir"));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] row = line.split(",");
        if (row[0].toLowerCase().startsWith(startsWith.toLowerCase())) {
          tickerMap.put(row[0], row[row.length - 1]);
        }
      }
    } catch (IOException e) {
      throw new IOException("File not found.");
    }
    return tickerMap;
  }

  protected List<Map<String, String>> generateTickerMap(List<String[]> data, String ticker) {
    List<Map<String, String>> result = new ArrayList<>();
    for (String[] row : data) {
      Map<String, String> map = new HashMap<>();
      map.put("ticker", ticker);
      map.put("timestamp", row[0]);
      map.put("open", row[1]);
      map.put("close", row[4]);
      map.put("volume", row[5]);
      result.add(map);
    }
    return result;
  }


  private List<Map<String, String>> getDataFromFile(String companyTicker, File file)
          throws FileNotFoundException {
    List<String[]> fileContent = new ArrayList<>();
    try {
      BufferedReader reader = new BufferedReader(
              new FileReader(file));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] output = line.split(",");
        fileContent.add(output);
      }
    } catch (IOException e) {
      throw new FileNotFoundException("File not found");
    }
    return generateTickerMap(fileContent, companyTicker);
  }

  private List<Map<String, String>> getTickerData(String companyTicker)
          throws ParseException, IOException {
    File directory = new File(configReader().get("STOCK_DATA_PATH"));
    File[] files = directory.listFiles();
    try {
      if (files == null || files.length == 0) {
        return api.getStockDetails(companyTicker);
      } else {
        for (File file : files) {
          if (file.isFile()) {
            String fileTicker = file.toString().split("_")[0];
            String fileDate = file.toString().split("_")[1].split("\\\\")[0];
            if (fileTicker.contains(companyTicker)) {
              // Added minusDays 1
              if (parseDate(fileDate).isBefore(LocalDate.now().minusDays(1))) {
                file.delete();
                return api.getStockDetails(companyTicker);
              }
              return getDataFromFile(companyTicker, file);
            }
          }
        }
      }
      return api.getStockDetails(companyTicker);
    } catch (IOException e) {
      throw new IOException("An error occurred while reading the file.");
    } catch (ParseException e) {
      throw new ParseException("Error parsing the file.", 1);
    }
  }

  /**
   * The method returns the share data for a given company name by reading the data from the file.
   *
   * @param companyName the name of the company.
   * @return The share data for a given company.
   * @throws ParseException If the file isn't parsed properly.
   * @throws IOException    If share data is unavailable for the company.
   */
  protected List<Map<String, String>> getShareByCompanyName(String companyName)
          throws ParseException, IOException {
    try {
      BufferedReader reader = readFile("stockData.csv", System.getProperty("user.dir"));
      String line = "";
      while ((line = reader.readLine()) != null) {
        String[] row = line.split(",");
        if (row[row.length - 1].toLowerCase().replaceAll("\\s+", "")
                .startsWith(companyName.toLowerCase().replaceAll("\\s+", "")) ||
                row[row.length - 1].equalsIgnoreCase(companyName)) {
          return getTickerData(row[0]);
        }
      }
      throw new IOException("Stock data for " + companyName + " is unavailable");
    } catch (IOException e) {
      throw new IOException("No stock data found for " + companyName);
    } catch (ParseException e) {
      throw new ParseException("Error parsing the file.", 1);
    }
  }

  /**
   * The method returns list of map of the stock data for a given date.
   *
   * @param date       The date for which data is needed.
   * @param sharesLine The list of string formatted share data.
   * @return List of map of stock data.
   * @throws IOException If an I/O error occurs.
   */
  protected List<Map<String, String>> getValue(LocalDate date,
                                               List<String> sharesLine) throws IOException {
    List<Map<String, String>> res = new ArrayList<>();
    try {
      File[] files = getFiles(sharesLine);
      for (String share : sharesLine) {
        String[] row = share.split(",");
        boolean flag = false;
        for (File file : files) {
          if (file.isFile()) {
            String fileName = file.toString().split("\\\\")[file.toString().
                    split("\\\\").length - 1];
            String fileTicker = fileName.split("_")[0];
            String fileDate = fileName.split("_")[1].split("\\.")[0];
            if (isTickerAndInDateRange(fileTicker, row, fileDate)) {
              if (date.isAfter(parseDate(fileDate))) {
                file.delete();
                api.getStockDetails(row[0]);
                return getValue(date, sharesLine);
              }
              flag = true;
              addDataToResult(date, fileName, row, fileDate, res);
            }
          }
        }
        if (!flag) {
          api.getStockDetails(row[0]);
          return getValue(date, sharesLine);
        }
      }
    } catch (ParseException | IOException e) {
      throw new IOException("Unable to fetch the total value from the file.");
    }
    return res;
  }

  private boolean isTickerAndInDateRange(String fileTicker, String[] row, String fileDate)
          throws ParseException {
    return fileTicker.contains(row[0])
            && (parseDate(row[1]).isBefore(
            parseDate(fileDate))
            || (parseDate(row[1])).isEqual(
            parseDate(fileDate)));
  }

  private File[] getFiles(List<String> sharesLine) throws IOException, ParseException {
    File directory = new File(System.getProperty("user.dir") + File.separator +
            configReader().get("STOCK_DATA_PATH"));
    File[] files = directory.listFiles();
    if (files == null ||
            Arrays.stream(files).
                    filter(File::isFile).toArray().length == 0) {
      for (String share : sharesLine) {
        api.getStockDetails(share.split(",")[0]);
      }
    }
    return files;
  }

  private void addDataToResult(LocalDate date, String fileName, String[] row,
                               String fileDate, List<Map<String, String>> res)
          throws IOException {
    BufferedReader reader = readFile(fileName,
            configReader().get("STOCK_DATA_PATH"));
    String line = "";
    while ((line = reader.readLine()) != null) {
      String[] lineData = line.split(",");
      if (lineData[0].equalsIgnoreCase(date.toString())) {
        Map<String, String> map = new HashMap<>();
        map.put("quantity", row[5]);
        map.put("open", lineData[1]);
        map.put("close", lineData[4]);
        map.put("timestamp", row[1]);
        map.put("fileTimeStamp", fileDate);
        res.add(map);
        break;
      }
    }
  }

  /**
   * The method returns list of flexible portfolio names saved in the directory.
   *
   * @return list of portfolios.
   * @throws IOException If an I/O error occurs.
   */
  protected List<String> getFlexiblePortfolios() throws IOException {
    // Changed path
    List<String> portfolios = new ArrayList<>();
    File directory =
            new File(System.getProperty("user.dir")
                    + File.separator + configReader().get("PORTFOLIOS_PATH") +
                    File.separator + configReader().get("FLEXIBLE_PORTFOLIOS_PATH"));
    return getPortfolios(directory, portfolios);
  }

  /**
   * The method returns list of strategies saved in the directory.
   *
   * @return list of portfolios.
   * @throws IOException If an I/O error occurs.
   */
  protected List<String> getStrategies() throws IOException {
    List<String> portfolios = new ArrayList<>();
    File directory =
            new File(System.getProperty("user.dir")
                    + File.separator + configReader().get("PORTFOLIOS_PATH") +
                    File.separator + configReader().get("STRATEGIES_PATH"));
    return getPortfolios(directory, portfolios);
  }


  /**
   * he method returns list of inflexible portfolio names saved in the directory.
   *
   * @return list of portfolios.
   * @throws IOException If an I/O error occurs.
   */
  protected List<String> getInflexiblePortfolios() throws IOException {
    List<String> portfolios = new ArrayList<>();
    File directory = new File(System.getProperty("user.dir")
            + File.separator + configReader().get("PORTFOLIOS_PATH") +
            File.separator + configReader().get("INFLEXIBLE_PORTFOLIOS_PATH"));
    return getPortfolios(directory, portfolios);
  }

  private List<String> getPortfolios(File directory, List<String> portfolios) {
    File[] files = directory.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isFile()) {
          portfolios.add(file.toString().split("\\\\")[
                  file.toString().split("\\\\").length - 1]);
        }
      }
    }
    return portfolios;
  }

  /**
   * The method checks whether the inflexible portfolio of same name exists or not.
   *
   * @param portfolioName The portfolio name.
   * @return true if exists, else false
   * @throws IOException If an I/O error occurs.
   */
  protected boolean doesInFlexiblePortfolioExist(String portfolioName) throws IOException {
    File directory =
            new File(System.getProperty("user.dir") + File.separator +
                    configReader().get("PORTFOLIOS_PATH") +
                    File.separator + configReader().get("INFLEXIBLE_PORTFOLIOS_PATH"));
    return doesPortfolioExists(directory, portfolioName);
  }


  /**
   * The method checks whether the inflexible portfolio of same name exists or not.
   *
   * @param portfolioName The portfolio name.
   * @return true if exists, else false
   * @throws IOException If an I/O error occurs.
   */
  protected boolean doesFlexiblePortfolioExist(String portfolioName) throws IOException {
    File directory = new File(System.getProperty("user.dir") +
            File.separator + configReader().get("PORTFOLIOS_PATH") +
            File.separator + configReader().get("FLEXIBLE_PORTFOLIOS_PATH"));
    return doesPortfolioExists(directory, portfolioName);
  }


  /**
   * Checks if the portfolio is already there in the directory or not.
   *
   * @param directory     the directory name where the file exists.
   * @param portfolioName the portfolio name.
   * @return boolean based on if the portfolio exists or not
   */
  protected boolean doesPortfolioExists(File directory, String portfolioName) {
    File[] files = directory.listFiles();
    if (files == null) {
      return false;
    } else {
      for (File file : files) {
        if (file.isFile()) {
          String fileName = getFileName(file.toString());
          if (fileName.equalsIgnoreCase(portfolioName)) {
            return true;
          }
        }
      }
    }
    return false;
  }


  /**
   * On a given day if a particular stock gained its value or lost its value comparing with the
   * previous and current date's data.
   *
   * @param companyName name of the company to see analysis for.
   * @param date        date on which analysis is to be displayed.
   * @return map of stock file data.
   * @throws IOException    if an I/O exception occurs.
   * @throws ParseException if file fails to load or save.
   */
  protected Map<String, String> stockAnalysisDay(String companyName, String date)
          throws IOException, ParseException {
    Map<String, String> res = new HashMap<>();
    String companyTicker = getShareByCompanyName(companyName).get(1).get("ticker");
    try {
      File[] files = getFiles(Collections.singletonList(companyTicker));
      boolean flag = false;
      for (File file : files) {
        if (file.isFile()) {
          String fileName = file.toString().split("\\\\")[file.toString().
                  split("\\\\").length - 1];
          String fileTicker = fileName.split("_")[0];
          String fileDate = fileName.split("_")[1].split("\\.")[0];
          if (isTickerCorrect(fileTicker, date, companyTicker, fileDate)) {
            if (parseDate(date).isAfter(parseDate(fileDate))) {
              file.delete();
              api.getStockDetails(companyTicker);
              return stockAnalysisDay(companyTicker, date);
            }
            flag = true;
            generateData(parseDate(date), fileName, res);
          }
        }
      }
      if (!flag) {
        api.getStockDetails(companyTicker);
        return stockAnalysisDay(companyName, date);
      }
    } catch (IOException e) {
      throw new IOException("Stock data for " + companyName + " is unavailable");
    } catch (ParseException e) {
      throw new ParseException("Stock data for " + companyName + " is unavailable", 1);
    }
    if (res.isEmpty()) {
      throw new IOException("Stock data for " + companyName +
              " is unavailable on the specified date");
    }
    return res;
  }

  private void generateData(LocalDate date, String fileName, Map<String, String> res)
          throws IOException {
    BufferedReader reader = readFile(fileName,
            configReader().get("STOCK_DATA_PATH"));
    String line;
    while ((line = reader.readLine()) != null) {
      String[] lineData = line.split(",");
      if (lineData[0].equalsIgnoreCase(date.toString())) {
        res.put("open", lineData[1]);
        res.put("close", lineData[4]);
        break;
      }
    }
  }

  private boolean isTickerCorrect(String fileTicker, String date,
                                  String companyName, String fileDate)
          throws ParseException {
    return fileTicker.contains(companyName)
            && (parseDate(date).isBefore(
            parseDate(fileDate))
            || (parseDate(date).isEqual(
            parseDate(fileDate))));
  }


  /**
   * On a given day if a particular stock gained its value or lost its value comparing
   * over a period of time.
   *
   * @param companyName name of the company to see analysis for.
   * @param startDate   start date of the period to view analysis of.
   * @param endDate     end date of the period to view analysis of.
   * @return map of stock file data.
   * @throws IOException    if an I/O exception occurs.
   * @throws ParseException if file fails to load or save.
   */
  protected Map<String, String> stockAnalysisPeriod(String companyName,
                                                    String startDate, String endDate)
          throws IOException, ParseException {
    Map<String, String> stockData = new HashMap<>();
    String closePriceStartDate = stockAnalysisDay(companyName, startDate).get("close");
    String closePriceEndDate = stockAnalysisDay(companyName, endDate).get("close");
    if (closePriceStartDate.isEmpty() || closePriceEndDate.isEmpty()) {
      throw new IllegalStateException("Data unavailable for the specified date");
    }
    stockData.put("closePriceStartDate", closePriceStartDate);
    stockData.put("closePriceEndDate", closePriceEndDate);
    return stockData;
  }


  /**
   * On a given number of days calculates the X day moving average of the particular stock.
   *
   * @param companyName name of the company to see analysis for.
   * @param day         number of days for which the moving average is to be calculated.
   * @param date        date from which the stock trend is to be viewed.
   * @return List of map of the stock data.
   * @throws ParseException if file fails to load or save.
   * @throws IOException    if an I/O exception occurs.
   */
  protected List<Map<String, String>> xDayMovingAverage(String companyName,
                                                        int day, String date)
          throws ParseException, IOException {
    Map<String, String> dateMap = new HashMap<>();
    dateMap.put("date", date);
    dateMap.put("day", String.valueOf(day));
    List<Map<String, String>> companyMap = getShareByCompanyName(companyName);
    companyMap.add(dateMap);
    return companyMap;
  }


  /**
   * Calculates the crossover of the stock trend from the start date and the end date specified.
   *
   * @param companyName name of the company to see analysis for.
   * @param startDate   start date of the period to view analysis of.
   * @param endDate     end date of the period to view analysis of.
   * @return List of map of the stock data.
   * @throws ParseException if file fails to load or save.
   * @throws IOException    if an I/O exception occurs.
   */
  protected List<Map<String, String>> crossovers(String companyName,
                                                 String startDate, String endDate)
          throws ParseException, IOException {
    Map<String, String> dateMap = new HashMap<>();
    dateMap.put("startDate", startDate);
    dateMap.put("endDate", endDate);
    List<Map<String, String>> companyMap = getShareByCompanyName(companyName);
    companyMap.add(dateMap);
    return companyMap;
  }


  /**
   * Calculates the moving crossover of the stock trend from the start date and the end date
   * with the X day and Y day moving average specified.
   *
   * @param companyName name of the company to see analysis for.
   * @param xday        number of days for which the moving average is to be calculated.
   * @param yday        number of days for which the moving average is to be calculated.
   * @param startDate   start date of the period to view analysis of.
   * @param endDate     end date of the period to view analysis of.
   * @return List of map of the stock data.
   * @throws ParseException if file fails to load or save.
   * @throws IOException    if an I/O exception occurs.
   */
  protected List<Map<String, String>> movingCrossovers(String companyName, int xday, int yday,
                                                       String startDate, String endDate)
          throws ParseException, IOException {
    Map<String, String> dateMap = new HashMap<>();
    dateMap.put("startDate", startDate);
    dateMap.put("endDate", endDate);
    dateMap.put("xday", String.valueOf(xday));
    dateMap.put("yday", String.valueOf(yday));
    List<Map<String, String>> companyMap = getShareByCompanyName(companyName);
    companyMap.add(dateMap);
    return companyMap;
  }

  private String getFileName(String fileName) {
    Path filePath = Paths.get(fileName);
    String nameOfFile = filePath.getFileName().toString();
    int dotIndex = nameOfFile.lastIndexOf('.');
    if (dotIndex == -1) {
      return nameOfFile;
    } else {
      return nameOfFile.substring(0, dotIndex);
    }
  }


  /**
   * Fetches the total value of the flexible portfolio based on the stocks available in the
   * portfolio.
   *
   * @param date       date on which the total value of the portfolio is to be fetched.
   * @param sharesLine List of shares for which the total value is to be calculated.
   * @return List of map of the stock data.
   * @throws IOException if an I/O exception occurs.
   */
  protected List<Map<String, String>> getValueOfFlexiblePortfolio(LocalDate date,
                                                                  List<String> sharesLine)
          throws IOException {
    List<Map<String, String>> res = new ArrayList<>();
    try {
      File[] files = getFiles(sharesLine);
      for (String share : sharesLine) {
        String[] row = share.split(",");
        if (parseDate(row[1]).isEqual(date) || parseDate(row[1]).isBefore(date)) {
          boolean flag = false;
          for (File file : files) {
            if (file.isFile()) {
              String fileName = file.toString().split("\\\\")[file.toString().
                      split("\\\\").length - 1];
              String fileTicker = fileName.split("_")[0];
              String fileDate = fileName.split("_")[1].split("\\.")[0];
              if (isTickerAndInDateRange(fileTicker, row, fileDate)) {
                if (date.isAfter(parseDate(fileDate))) {
                  file.delete();
                  api.getStockDetails(row[0]);
                  return getValue(date, sharesLine);
                }
                flag = true;
                addDataToResult(date, fileName, row, fileDate, res);
              }
            }
          }
          if (!flag) {
            api.getStockDetails(row[0]);
            return getValue(date, sharesLine);
          }
        }
      }
    } catch (ParseException | IOException e) {
      throw new IOException("Unable to fetch the total value from the file.");
    }
    return res;
  }

  protected float fetchStrategyData(List<String> strategy, float amount,
                                    List<Map<String, String>> finalData, List<String> strategyList)
          throws ParseException, IOException {
    for (String strategyRow : strategy) {
      String[] row = strategyRow.split(",");
      LocalDate lastUpdated = parseDate(row[2]);
      LocalDate endDate = parseDate(row[1]);
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
              fetchCompanyDataFromFile(finalData, share, currentDate, row);
            }
            currentDate = currentDate.plusDays(period);
          }
        }
      }
      strategyList.add(String.join(",", row));
    }
    return amount;
  }

  private void fetchCompanyDataFromFile(List<Map<String, String>> finalData,
                                        Map.Entry<String, String> share,
                                        LocalDate currentDate, String[] row)
          throws IOException, ParseException {
    try {
      Map<String, String> map = dataOfShareForDate(getShareByCompanyName(share.getKey()),
              currentDate.toString());
      map.put("percentage", share.getValue());
      row[2] = currentDate.toString();
      finalData.add(map);
    } catch (Exception e) {
      Map<String, String> map = dataOfShareForDate(getShareByCompanyName(share.getKey()),
              currentDate.plusDays(1).toString());
      map.put("percentage", share.getValue());
      row[2] = currentDate.toString();
      finalData.add(map);
    }
  }
}
 