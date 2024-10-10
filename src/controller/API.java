package controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * An API interface class that will be used to support different type of APIs as per the data
 * required by the user in a certain format.
 */
public interface API {
  /**
   * Fetches the stock data of different company listed on the stock market based on the ticker
   * symbol passed to it.
   *
   * @param ticker ticker symbol of the company whose stocks are listed.
   * @return API data in the form of List of map.
   * @throws IOException file I/O exception.
   * @throws ParseException file incorrectly parse exception.
   */
  List<Map<String, String>> getStockDetails(String ticker) throws IOException, ParseException;
}
