package controller;

import java.io.IOException;

/**
 * The interface represents the controller of the application that acts as an intermediate
 * between view and the model of the application.
 */
public interface PortfolioController {
  /**
   * The method runs the application.
   *
   * @throws IOException If an I/O occurs.
   */
  void run() throws IOException;
}
