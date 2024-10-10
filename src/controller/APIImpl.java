package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

/**
 * An implementation of the API interface class that uses it methods to implement the methods and
 * any other form of APIs can also be supported by creating a new method in this class.
 */
public class APIImpl implements API {

  @Override
  public List<Map<String, String>> getStockDetails(String ticker) throws IOException,
          ParseException {
    FileOperation file = new FileOperation();
    URL url;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + ticker + "&apikey=" + file.configReader().get("API_KEY") + "&datatype=csv");
      List<String[]> apiData = new ArrayList<>();
      BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
      String b;
      while ((b = reader.readLine()) != null) {
        String[] rowData = b.split(",");
        apiData.add(rowData);
      }
      file.generateFile(apiData, ticker);
      return file.generateTickerMap(apiData, ticker);
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alpha vantage API has either changed or "
              + "no longer works");
    } catch (IOException e) {
      throw new IOException("An error occurred while reading the file.");
    } catch (ParserConfigurationException e) {
      throw new ParseException("Error parsing the file.", 1);
    }
  }
}
