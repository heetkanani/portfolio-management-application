package view;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import controller.Features;

/**
 * The interface represents the methods that the Graphical user Interface would be using
 * in order to display the data on the graphical user interface.
 */
public interface ManagementGUIView {

  /**
   * Adds the different features that are used to perform different operations on the stock
   * and portfolios.
   *
   * @param features features used to perform operations on portfolios and stocks.
   */
  void addFeatures(Features features);

  /**
   * Generates the total composition table, i.e. the stocks data table
   * that is to be displayed on the GUI.
   *
   * @return the table model of the generated composition.
   */
  DefaultTableModel generateTotalCompositionTable();

  /**
   * Displays the desired shares data that is present in the portfolio on the GUI.
   *
   * @param share shares string that are present in the portfolio.
   * @param model Table model used to display the data.
   */
  void displayData(String share, DefaultTableModel model);

  /**
   * Sets the error message on the label if any error is to be displayed on the screen told by the
   * controller.
   *
   * @param message the error message to be displayed.
   * @param label   error label that displays the message.
   */
  void setErrorMessage(String message, JLabel label);

  /**
   * Generates the fixed amount buy table as the per the stocks added by the investor.
   *
   * @param columns the number of columns to be displayed.
   * @return table model to display the data.
   */
  DefaultTableModel generateBatchBuyTable(String[] columns);


}
