package hw3;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;

public class HW3 extends JDialog {

    private JPanel contentPane;

    private JPanel topPanel;
    private JPanel businessPanel;
    private JPanel categoryPanel;
    private JScrollPane categoryPane;
    private JPanel subcategoryPanel;
    private JScrollPane subcategoryPane;
    private JPanel attributesPanel;
    private JScrollPane attributesPane;
    private JPanel searchCriteriaPanel;
    private JLabel businessSearchCriteriaLabel;
    private JComboBox businessSearchCriteriaComboBox;

    private JPanel reviewPanel;
    private JPanel reviewDateFromPanel;
    private JPanel reviewDateToPanel;
    private JDatePickerImpl reviewDatePickerFrom;
    private JDatePickerImpl reviewDatePickerTo;
    private JPanel reviewStarsPanel;
    private JLabel reviewStarsLabel;
    private JComboBox reviewStarsComboBox;
    private JLabel reviewStarsValueLabel;
    private JTextField reviewStarsValue;
    private JPanel reviewVotePanel;
    private JLabel voteLabel;
    private JLabel voteValueLabel;
    private JComboBox reviewVoteComboBox;
    private JComboBox reviewVoteOpComboBox;
    private JTextField reviewVoteValue;
    private JSeparator dateStarsSeparator;
    private JSeparator starsVoteSeparator;

    private JPanel middlePanel;
    private JPanel usersPanel;
    private JPanel usersSelectPanel;
    private JLabel memberSinceLabel;
    private JLabel reviewCountLabel;
    private JLabel numOfFriendsLabel;
    private JLabel avgStarsLabel;
    private JLabel numOfVotesLabel;
    private JPanel memberSinceDatePanel;
    private JDatePickerImpl memberSinceDatePicker;
    private JComboBox userReviewCountComboBox;
    private JComboBox userNOFComboBox;
    private JComboBox userAvgStarsComboBox;
    private JComboBox userNOVComboBox;
    private JPanel usersValuePanel;
    private JTextField userReviewCountValue;
    private JTextField userNOFValue;
    private JTextField userAvgStarsValue;
    private JTextField userNOVValue;
    private JPanel userSearchCriteriaPanel;
    private JLabel userANDORLabel;
    private JComboBox userSearchCriteriaComboBox;
    private JPanel fillerPanel;
    private JLabel rcValLabel;
    private JLabel nofValLabel;
    private JLabel asValLabel;
    private JLabel novValLabel;
    private JPanel queryPanel;
    private JTextArea queryTextArea;
    private JTextArea parametersTextArea;
    private JLabel parametersLabel;
    private JScrollPane queryScrollPane;
    private JScrollPane parametersScrollPane;

    private JPanel lastPanel;
    private JPanel resultsPanel;
    private JScrollPane resultsScrollPane;

    private JPanel footer;
    private JPanel buttons;
    private JButton buSearchButton;
    private JButton userSearchButton;
    private JButton buttonCancel;

    private String BU_SEARCH_CRITERIA = "OR";
    private String USER_SEARCH_CRITERIA = "OR";

    private List<String> checkedCategories = new ArrayList<String>();
    private List<String> checkedSubcategories = new ArrayList<String>();
    private List<String> checkedAttributes = new ArrayList<String>();

    private Date reviewDateFrom = null;
    private Date reviewDateTo = null;

    private Date memberSinceDate = null;

    public HW3(Connection connection, List<String> categories) {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);

        userSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onUserSearch(connection);
            }
        });

        buSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBusinessSearch(connection);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        businessSearchCriteriaComboBox.addItem("OR");
        businessSearchCriteriaComboBox.addItem("AND");
        businessSearchCriteriaComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                BU_SEARCH_CRITERIA = (String) cb.getSelectedItem();
            }
        });

        userSearchCriteriaComboBox.addItem("OR");
        userSearchCriteriaComboBox.addItem("AND");
        userSearchCriteriaComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                USER_SEARCH_CRITERIA = (String) cb.getSelectedItem();
            }
        });

        reviewStarsComboBox.addItem("Select operator");
        reviewStarsComboBox.addItem("=");
        reviewStarsComboBox.addItem("<");
        reviewStarsComboBox.addItem(">");
        reviewStarsComboBox.addItem("<=");
        reviewStarsComboBox.addItem(">=");
        reviewStarsComboBox.addItem("<>");

        reviewVoteComboBox.addItem("Select vote type");
        reviewVoteComboBox.addItem("useful");
        reviewVoteComboBox.addItem("funny");
        reviewVoteComboBox.addItem("cool");

        reviewVoteOpComboBox.addItem("Select operator");
        reviewVoteOpComboBox.addItem("=");
        reviewVoteOpComboBox.addItem("<");
        reviewVoteOpComboBox.addItem(">");
        reviewVoteOpComboBox.addItem("<=");
        reviewVoteOpComboBox.addItem(">=");
        reviewVoteOpComboBox.addItem("<>");

        userReviewCountComboBox.addItem("Select operator");
        userReviewCountComboBox.addItem("=");
        userReviewCountComboBox.addItem("<");
        userReviewCountComboBox.addItem(">");
        userReviewCountComboBox.addItem("<=");
        userReviewCountComboBox.addItem(">=");
        userReviewCountComboBox.addItem("<>");

        userNOFComboBox.addItem("Select operator");
        userNOFComboBox.addItem("=");
        userNOFComboBox.addItem("<");
        userNOFComboBox.addItem(">");
        userNOFComboBox.addItem("<=");
        userNOFComboBox.addItem(">=");
        userNOFComboBox.addItem("<>");

        userAvgStarsComboBox.addItem("Select operator");
        userAvgStarsComboBox.addItem("=");
        userAvgStarsComboBox.addItem("<");
        userAvgStarsComboBox.addItem(">");
        userAvgStarsComboBox.addItem("<=");
        userAvgStarsComboBox.addItem(">=");
        userAvgStarsComboBox.addItem("<>");

        userNOVComboBox.addItem("Select operator");
        userNOVComboBox.addItem("=");
        userNOVComboBox.addItem("<");
        userNOVComboBox.addItem(">");
        userNOVComboBox.addItem("<=");
        userNOVComboBox.addItem(">=");
        userNOVComboBox.addItem("<>");

        UtilDateModel fromModel = new UtilDateModel();
        UtilDateModel toModel = new UtilDateModel();
        UtilDateModel memberSinceModel = new UtilDateModel();

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanelFrom = new JDatePanelImpl(fromModel, p);
        reviewDatePickerFrom = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());

        JDatePanelImpl datePanelTo = new JDatePanelImpl(toModel, p);
        reviewDatePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());

        JDatePanelImpl datePanelMemberSince = new JDatePanelImpl(memberSinceModel, p);
        memberSinceDatePicker = new JDatePickerImpl(datePanelMemberSince, new DateLabelFormatter());

        reviewDateFromPanel.setLayout(new FlowLayout());
        reviewDateFromPanel.add(new JLabel("From"));
        reviewDateFromPanel.add(reviewDatePickerFrom);

        reviewDateToPanel.setLayout(new FlowLayout());
        reviewDateToPanel.add(new JLabel("To"));
        reviewDateToPanel.add(reviewDatePickerTo);

        memberSinceDatePanel.setLayout(new FlowLayout());
        memberSinceDatePanel.add(memberSinceDatePicker);

        reviewDatePickerFrom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDatePanelImpl picker = (JDatePanelImpl) e.getSource();
                Date from = (Date) picker.getModel().getValue();
                String oldFormat = "E MMM dd HH:mm:ss z yyyy";
                String newFormat = "dd-MMM-yy";
                SimpleDateFormat dateFormatter = new SimpleDateFormat(oldFormat);
                dateFormatter.applyPattern(newFormat);
                String fromStr = dateFormatter.format(from);
                try {
                    reviewDateFrom = dateFormatter.parse(fromStr);
                } catch (ParseException e1) {
                    System.out.println("Exception while parsing review date from: " + e1.getMessage());
                }
            }
        });

        reviewDatePickerTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDatePanelImpl picker = (JDatePanelImpl) e.getSource();
                Date to = (Date) picker.getModel().getValue();
                String oldFormat = "E MMM dd HH:mm:ss z yyyy";
                String newFormat = "dd-MMM-yy";
                SimpleDateFormat dateFormatter = new SimpleDateFormat(oldFormat);
                dateFormatter.applyPattern(newFormat);
                String toStr = dateFormatter.format(to);
                try {
                    reviewDateTo = dateFormatter.parse(toStr);
                } catch (ParseException e1) {
                    System.out.println("Exception while parsing review date to: " + e1.getMessage());
                }
            }
        });

        memberSinceDatePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDatePanelImpl picker = (JDatePanelImpl) e.getSource();
                Date date = (Date) picker.getModel().getValue();
                String oldFormat = "E MMM dd HH:mm:ss z yyyy";
                String newFormat = "dd-MMM-yy";
                SimpleDateFormat dateFormatter = new SimpleDateFormat(oldFormat);
                dateFormatter.applyPattern(newFormat);
                String toStr = dateFormatter.format(date);
                try {
                    memberSinceDate = dateFormatter.parse(toStr);
                } catch (ParseException e1) {
                    System.out.println("Exception while parsing member since date: " + e1.getMessage());
                }
            }
        });

        // Set preferred sizes
        categoryPane.setPreferredSize(new Dimension(250, 200));
        subcategoryPane.setPreferredSize(new Dimension(250, 200));
        attributesPane.setPreferredSize(new Dimension(250, 200));
        reviewPanel.setPreferredSize(new Dimension(250, 500));
        resultsPanel.setPreferredSize(new Dimension(750, 250));
        queryScrollPane.setPreferredSize(new Dimension(500, 250));
        parametersScrollPane.setPreferredSize(new Dimension(500, 250));

        // Add to UI
        // TODO AND Search for business
        JList categoryList = new JList();
        categoryList.setLayout(new BoxLayout(categoryList, BoxLayout.PAGE_AXIS));
        categoryList.setPreferredSize(new Dimension(200, categories.size() * 23));

        for (String category : categories) {
            JCheckBox checkBox = new JCheckBox(category);
            checkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    JCheckBox check = (JCheckBox) e.getSource();
                    String categoryName = check.getText();
                    if (e.getStateChange() == 1) {
                        checkedCategories.add(categoryName);
                        checkedSubcategories.clear();
                        checkedAttributes.clear();

                        // Clear in the UI
                        JList blankList = new JList();
                        attributesPane.add(blankList);
                        attributesPane.setViewportView(blankList);
                        attributesPane.repaint();

                    } else {
                        checkedCategories.remove(categoryName);

                        checkedSubcategories.clear();
                        checkedAttributes.clear();

                        // Clear in the UI
                        JList blankList = new JList();
                        attributesPane.add(blankList);
                        attributesPane.setViewportView(blankList);
                        attributesPane.repaint();
                    }

                    // Add to UI
                    JList subcategoryList = new JList();
                    subcategoryList.setLayout(new BoxLayout(subcategoryList, BoxLayout.PAGE_AXIS));

                    List<String> subcategories = new ArrayList<String>();
                    if (BU_SEARCH_CRITERIA.equals("OR")) {
                        subcategories = querySubcategoriesOR(connection, checkedCategories);
                    } else {
                        subcategories = querySubcategoriesAND(connection, checkedCategories);
                    }
                    subcategoryList.setPreferredSize(new Dimension(200, subcategories.size() * 23));

                    for (String subcategory : subcategories) {
                        JCheckBox checkBox = new JCheckBox(subcategory);
                        checkBox.addItemListener(new ItemListener() {
                            @Override
                            public void itemStateChanged(ItemEvent e) {
                                JCheckBox check = (JCheckBox) e.getSource();
                                String subcategoryName = check.getText();
                                if (e.getStateChange() == 1) {
                                    checkedSubcategories.add(subcategoryName);
                                } else {
                                    checkedSubcategories.remove(subcategoryName);
                                    checkedAttributes.clear();
                                }

                                // Add to UI
                                JList attributesList = new JList();
                                attributesList.setLayout(new BoxLayout(attributesList, BoxLayout.PAGE_AXIS));

                                List<String> attributes = new ArrayList<String>();
                                if (BU_SEARCH_CRITERIA.equals("OR")) {
                                    attributes = queryAttributesOR(connection, checkedCategories,
                                            checkedSubcategories);
                                } else {
                                    attributes = queryAttributesAND(connection, checkedCategories,
                                            checkedSubcategories);
                                }
                                attributesList.setPreferredSize(new Dimension(200, attributes.size() * 23));

                                for (String attr : attributes) {
                                    JCheckBox checkBox = new JCheckBox(attr);
                                    checkBox.addItemListener(new ItemListener() {
                                        @Override
                                        public void itemStateChanged(ItemEvent e) {
                                            JCheckBox check = (JCheckBox) e.getSource();
                                            String attribute = check.getText();
                                            if (e.getStateChange() == 1) {
                                                checkedAttributes.add(attribute);
                                            } else {
                                                checkedAttributes.remove(attribute);
                                            }

                                        }
                                    });
                                    attributesList.add(checkBox);
                                    attributesList.repaint();
                                }

                                attributesPane.setLayout(new ScrollPaneLayout());
                                attributesPane.add(attributesList);
                                attributesPane.setViewportView(attributesList);
                                attributesPane.repaint();

                            }
                        });
                        subcategoryList.add(checkBox);
                        subcategoryList.repaint();
                    }

                    subcategoryPane.setLayout(new ScrollPaneLayout());
                    subcategoryPane.add(subcategoryList);
                    subcategoryPane.setViewportView(subcategoryList);
                    subcategoryPane.repaint();

                }
            });
            categoryList.add(checkBox);
            categoryList.repaint();
        }

        categoryPane.setLayout(new ScrollPaneLayout());
        categoryPane.add(categoryList);
        categoryPane.setViewportView(categoryList);
        categoryPane.repaint();

    }

    private void onBusinessSearch(Connection conn) {

        if (checkedCategories.size() == 0) {
            JOptionPane.showMessageDialog(null, "Category selection is required!");
        } else {
            if (BU_SEARCH_CRITERIA.equals("OR")) {
                // category search only
                if (checkedSubcategories.isEmpty() && checkedAttributes.isEmpty()) {
                    categorySearchOR(conn);
                }
                // category and subcategory search
                else if (checkedAttributes.isEmpty()) {
                    categoryAndSubcategorySearchOR(conn);
                }
                // category and subcategory and attribute search
                else {
                    categoryAndSubcategoryAndAttributeSearchOR(conn);
                }
            } else {
                // category search only
                if (checkedSubcategories.isEmpty() && checkedAttributes.isEmpty()) {
                    categorySearchAND(conn);
                }
                // category and subcategory search
                else if (checkedAttributes.isEmpty()) {
                    categoryAndSubcategorySearchAND(conn);
                }
                // category and subcategory and attribute search
                else {
                    categoryAndSubcategoryAndAttributeSearchAND(conn);
                }
            }
        }

    }

    private void onUserSearch(Connection conn) {
        userSearch(conn);
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Exception while loading oracle jdbc driver: " + e.getMessage());
            System.out.println("Terminated program.");
            System.exit(-1);
        }

        Connection connection = null;
        try {

            connection = DriverManager
                    .getConnection(Constants.ORACLE_URL, Constants.USERNAME, Constants.PASSWORD);

            // Fetch category from DB
            List<String> categories = new ArrayList<String>();
            categories = queryAllCategories(connection);

            HW3 dialog = new HW3(connection, categories);
            dialog.pack();
            dialog.setVisible(true);

        } catch (SQLException e) {
            System.out.println("Exception while establishing connection: " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Exception while closing connection: " + e.getMessage());
                System.exit(-1);
            }
        }

        System.exit(0);

    }

    private static List<String> queryAllCategories(Connection conn) {

        List<String> categories = new ArrayList<String>();

        try {
            String sql = "SELECT C.CATEGORY FROM CATEGORY C";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                categories.add(rs.getString("CATEGORY"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Exception while querying for categories: " + e.getMessage());
        }

        return categories;

    }

    private static List<String> querySubcategoriesOR(Connection conn, List<String> checkedCategories) {

        List<String> subcategories = new ArrayList<String>();

        if (!checkedCategories.isEmpty()) {

            try {
                String categoryClause = "";
                for (int i = 0; i < checkedCategories.size(); i++) {
                    categoryClause += "A.CATEGORY = ? OR ";
                }
                categoryClause = categoryClause.substring(0, categoryClause.length() - 4); // remove the last ' OR '

                String sql = "SELECT DISTINCT B.SUBCATEGORY FROM BU_CATEGORY A, BU_SUBCATEGORY B WHERE " +
                        "((" + categoryClause + ") AND (A.BU_ID = B.BU_ID)) ORDER BY B.SUBCATEGORY";

                PreparedStatement ps = conn.prepareStatement(sql);
                for (int i = 1; i <= checkedCategories.size(); i++) {
                    ps.setString(i, checkedCategories.get(i - 1));
                }
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    subcategories.add(rs.getString("SUBCATEGORY"));
                }
                rs.close();

            } catch (SQLException e) {
                System.out.println("Exception while querying for subcategories: " + e.getMessage());
            }

        }

        return subcategories;

    }

    private static List<String> querySubcategoriesAND(Connection conn, List<String> checkedCategories) {

        List<String> subcategories = new ArrayList<String>();

        if (!checkedCategories.isEmpty()) {

            try {
                String categoryClause = "";
                for (int i = 0; i < checkedCategories.size(); i++) {
                    categoryClause += "CATEGORY = ? OR ";
                }
                categoryClause = categoryClause.substring(0, categoryClause.length() - 4); // remove the last ' OR '

                String sql = "SELECT DISTINCT SUBCATEGORY FROM BU_SUBCATEGORY WHERE BU_ID IN " +
                        "(SELECT BU_ID FROM BU_CATEGORY WHERE CATEGORY IN " +
                        "(SELECT CATEGORY FROM CATEGORY WHERE (" + categoryClause + ")) " +
                        "GROUP BY BU_ID HAVING COUNT(DISTINCT CATEGORY) = " + checkedCategories.size() + ")" +
                        "ORDER BY SUBCATEGORY";

                PreparedStatement ps = conn.prepareStatement(sql);
                for (int i = 1; i <= checkedCategories.size(); i++) {
                    ps.setString(i, checkedCategories.get(i - 1));
                }
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    subcategories.add(rs.getString("SUBCATEGORY"));
                }
                rs.close();

            } catch (SQLException e) {
                System.out.println("Exception while querying for subcategories: " + e.getMessage());
            }

        }

        return subcategories;

    }

    private static List<String> queryAttributesOR(Connection conn, List<String> checkedCategories,
                                                  List<String> checkedSubcategories) {

        List<String> attributes = new ArrayList<String>();

        if (!checkedSubcategories.isEmpty()) {

            try {
                String categoryClause = "";
                for (int i = 0; i < checkedCategories.size(); i++) {
                    categoryClause += "A.CATEGORY = ? OR ";
                }
                categoryClause = categoryClause.substring(0, categoryClause.length() - 4); // remove the last ' OR '

                String subcategoryClause = "";
                for (int i = 0; i < checkedSubcategories.size(); i++) {
                    subcategoryClause += "B.SUBCATEGORY = ? OR ";
                }
                subcategoryClause = subcategoryClause.substring(0, subcategoryClause.length() - 4); // remove the last ' OR '

                String sql = "SELECT DISTINCT C.ATTR_NAME, C.ATTR_VALUE FROM BU_CATEGORY A, BU_SUBCATEGORY B, " +
                        "BU_ATTRIBUTE C WHERE ((" + categoryClause + ") AND (" + subcategoryClause + ") AND " +
                        "(A.BU_ID = C.BU_ID) AND (B.BU_ID = C.BU_ID)) ORDER BY C.ATTR_NAME";

                PreparedStatement ps = conn.prepareStatement(sql);
                int id = 0;
                for (int i = 1; i <= checkedCategories.size(); i++) {
                    ps.setString((id += 1), checkedCategories.get(i - 1));
                }
                for (int i = 1; i <= checkedSubcategories.size(); i++) {
                    ps.setString((id += 1), checkedSubcategories.get(i - 1));
                }
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String attr = rs.getString("ATTR_NAME") + " = " + rs.getString("ATTR_VALUE");
                    attributes.add(attr);
                }
                rs.close();
            } catch (SQLException e) {
                System.out.println("Exception while querying for attributes: " + e.getMessage());
            }

        }

        return attributes;

    }

    private static List<String> queryAttributesAND(Connection conn, List<String> checkedCategories,
                                                  List<String> checkedSubcategories) {

        List<String> attributes = new ArrayList<String>();

        if (!checkedSubcategories.isEmpty()) {

            try {
                String categoryClause = "";
                for (int i = 0; i < checkedCategories.size(); i++) {
                    categoryClause += "?,";
                }
                categoryClause = categoryClause.substring(0, categoryClause.length() - 1);

                String subcategoryClause = "";
                for (int i = 0; i < checkedSubcategories.size(); i++) {
                    subcategoryClause += "?,";
                }
                subcategoryClause = subcategoryClause.substring(0, subcategoryClause.length() - 1);

                String sql =
                        "SELECT DISTINCT ATTR_NAME, ATTR_VALUE\n" +
                        "FROM BU_ATTRIBUTE\n" +
                        "WHERE BU_ID IN (\n" +
                        "SELECT DISTINCT Z.BU_ID FROM \n" +
                        "(SELECT BU_ID FROM BU_SUBCATEGORY WHERE (SUBCATEGORY IN (" + subcategoryClause + ")) " +
                                "GROUP BY BU_ID HAVING COUNT (DISTINCT SUBCATEGORY) = " + checkedSubcategories.size() + ") Z,\n" +
                        "(SELECT BU_ID FROM BU_CATEGORY WHERE (CATEGORY IN (" + categoryClause + ")) " +
                                "GROUP BY BU_ID HAVING COUNT(DISTINCT CATEGORY) = " + checkedCategories.size() + ") Y " +
                                "WHERE (Z.BU_ID = Y.BU_ID)\n" +
                        ") ORDER BY ATTR_NAME";

                System.out.println(sql);
                PreparedStatement ps = conn.prepareStatement(sql);
                int id = 0;
                for (int i = 1; i <= checkedSubcategories.size(); i++) {
                    ps.setString((id += 1), checkedSubcategories.get(i - 1));
                }
                for (int i = 1; i <= checkedCategories.size(); i++) {
                    ps.setString((id += 1), checkedCategories.get(i - 1));
                }
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String attr = rs.getString("ATTR_NAME") + " = " + rs.getString("ATTR_VALUE");
                    attributes.add(attr);
                }
                rs.close();
            } catch (SQLException e) {
                System.out.println("Exception while querying for attributes: " + e.getMessage());
            }

        }

        return attributes;

    }

    private void categorySearchOR(Connection conn) {

        String categoryClause = "";
        for (int i = 0; i < checkedCategories.size(); i++) {
            categoryClause += "CATEGORY = ? " + BU_SEARCH_CRITERIA + " ";
        }
        categoryClause = categoryClause.substring(0, categoryClause.length() - (BU_SEARCH_CRITERIA.length() + 2));

        String sql = "SELECT BU_ID FROM BU_CATEGORY WHERE (" + categoryClause + ")";

        businessSearch(conn, checkedCategories, sql);

    }

    private void categorySearchAND(Connection conn) {

        String categoryClause = "";
        for (int i = 0; i < checkedCategories.size(); i++) {
            categoryClause += "?,";
        }
        categoryClause = categoryClause.substring(0, categoryClause.length() - 1);

        String sql = "SELECT BU_ID FROM BU_CATEGORY WHERE (CATEGORY IN (" + categoryClause + ")) " +
                "GROUP BY BU_ID HAVING COUNT(DISTINCT CATEGORY) = " + checkedCategories.size();

        businessSearch(conn, checkedCategories, sql);

    }

    private void categoryAndSubcategorySearchOR(Connection conn) {

        String categoryClause = "";
        for (int i = 0; i < checkedCategories.size(); i++) {
            categoryClause += "A.CATEGORY = ? " + BU_SEARCH_CRITERIA + " ";
        }
        categoryClause = categoryClause.substring(0, categoryClause.length() - (BU_SEARCH_CRITERIA.length() + 2));

        String subcategoryClause = "";
        for (int i = 0; i < checkedSubcategories.size(); i++) {
            subcategoryClause += "B.SUBCATEGORY = ? " + BU_SEARCH_CRITERIA + " ";
        }
        subcategoryClause = subcategoryClause.substring(0, subcategoryClause.length() - (BU_SEARCH_CRITERIA.length() + 2));

        String sql = "SELECT A.BU_ID FROM BU_CATEGORY A, BU_SUBCATEGORY B WHERE " +
                "((" + categoryClause + ") AND (" + subcategoryClause + ") AND (A.BU_ID = B.BU_ID))";

        List<String> items = new ArrayList<String>();
        items.addAll(checkedCategories);
        items.addAll(checkedSubcategories);

        businessSearch(conn, items, sql);

    }

    private void categoryAndSubcategorySearchAND(Connection conn) {

        String categoryClause = "";
        for (int i = 0; i < checkedCategories.size(); i++) {
            categoryClause += "?,";
        }
        categoryClause = categoryClause.substring(0, categoryClause.length() - 1);

        String subcategoryClause = "";
        for (int i = 0; i < checkedSubcategories.size(); i++) {
            subcategoryClause += "?,";
        }
        subcategoryClause = subcategoryClause.substring(0, subcategoryClause.length() - 1);

        String sql = "SELECT DISTINCT Z.BU_ID FROM " +
                "(SELECT BU_ID FROM BU_SUBCATEGORY WHERE (SUBCATEGORY IN (" + subcategoryClause + ")) " +
                "GROUP BY BU_ID HAVING COUNT (DISTINCT SUBCATEGORY) = " + checkedSubcategories.size() + ") Z, " +
                "(SELECT BU_ID FROM BU_CATEGORY WHERE (CATEGORY IN (" + categoryClause + ")) " +
                "GROUP BY BU_ID HAVING COUNT(DISTINCT CATEGORY) = " + checkedCategories.size() + ") Y WHERE (Z.BU_ID = Y.BU_ID)";

        List<String> items = new ArrayList<String>();
        items.addAll(checkedSubcategories);
        items.addAll(checkedCategories);
        businessSearch(conn, items, sql);

    }

    private void categoryAndSubcategoryAndAttributeSearchOR(Connection conn) {

        String categoryClause = "";
        for (int i = 0; i < checkedCategories.size(); i++) {
            categoryClause += "A.CATEGORY = ? " + BU_SEARCH_CRITERIA + " ";
        }
        categoryClause = categoryClause.substring(0, categoryClause.length() - (BU_SEARCH_CRITERIA.length() + 2));

        String subcategoryClause = "";
        for (int i = 0; i < checkedSubcategories.size(); i++) {
            subcategoryClause += "B.SUBCATEGORY = ? " + BU_SEARCH_CRITERIA + " ";
        }
        subcategoryClause = subcategoryClause.substring(0, subcategoryClause.length() - (BU_SEARCH_CRITERIA.length() + 2));

        String attributeClause = "";
        List<String> attrItems = new ArrayList<String>();
        for (String checkedAttribute : checkedAttributes) {
            attributeClause += "(C.ATTR_NAME = ? AND C.ATTR_VALUE = ?) " + BU_SEARCH_CRITERIA + " ";
            String k = checkedAttribute.split(" = ")[0];
            String v = checkedAttribute.split(" = ")[1];
            attrItems.add(k);
            attrItems.add(v);
        }
        attributeClause = attributeClause.substring(0, attributeClause.length() - (BU_SEARCH_CRITERIA.length() + 2));

        String sql = "SELECT C.BU_ID FROM BU_CATEGORY A, BU_SUBCATEGORY B, BU_ATTRIBUTE C WHERE " +
                "((" + categoryClause + ") AND (" + subcategoryClause + ") AND (" + attributeClause + ") " +
                "AND (A.BU_ID = B.BU_ID) AND (B.BU_ID = C.BU_ID))";

        List<String> items = new ArrayList<String>();
        items.addAll(checkedCategories);
        items.addAll(checkedSubcategories);
        items.addAll(attrItems);

        businessSearch(conn, items, sql);

    }

    private void categoryAndSubcategoryAndAttributeSearchAND(Connection conn) {

        String categoryClause = "";
        for (int i = 0; i < checkedCategories.size(); i++) {
            categoryClause += "?,";
        }
        categoryClause = categoryClause.substring(0, categoryClause.length() - 1);

        String subcategoryClause = "";
        for (int i = 0; i < checkedSubcategories.size(); i++) {
            subcategoryClause += "?,";
        }
        subcategoryClause = subcategoryClause.substring(0, subcategoryClause.length() - 1);

        String attributeClause = "";
        List<String> attrItems = new ArrayList<String>();
        for (String checkedAttribute : checkedAttributes) {
            attributeClause += "(ATTR_NAME = ? AND ATTR_VALUE = ?) " + BU_SEARCH_CRITERIA + " ";
            String k = checkedAttribute.split(" = ")[0];
            String v = checkedAttribute.split(" = ")[1];
            attrItems.add(k);
            attrItems.add(v);
        }
        attributeClause = attributeClause.substring(0, attributeClause.length() - (BU_SEARCH_CRITERIA.length() + 2));

        String sql = "SELECT DISTINCT BU_ID FROM BU_ATTRIBUTE WHERE (" + attributeClause + ") AND BU_ID IN " +
                "(SELECT DISTINCT Z.BU_ID FROM (SELECT BU_ID FROM BU_SUBCATEGORY WHERE " +
                "(SUBCATEGORY IN (" + subcategoryClause + ")) " +
                "GROUP BY BU_ID HAVING COUNT (DISTINCT SUBCATEGORY) = " + checkedSubcategories.size() + ") Z, " +
                "(SELECT BU_ID FROM BU_CATEGORY WHERE (CATEGORY IN (" + categoryClause + ")) " +
                "GROUP BY BU_ID HAVING COUNT(DISTINCT CATEGORY) = " + checkedCategories.size() + ") Y " +
                "WHERE (Z.BU_ID = Y.BU_ID))";

        List<String> items = new ArrayList<String>();
        items.addAll(attrItems);
        items.addAll(checkedSubcategories);
        items.addAll(checkedCategories);

        businessSearch(conn, items, sql);

    }

    private void businessSearch(Connection conn, List<String> items, String inClause) {

        System.out.println("------------------------------------------------------------");
        System.out.println("Searching... ");
        try {
            // Get name, city, state, and stars of each bu_id
            String sql = "SELECT DISTINCT BU_ID, NAME, CITY, STATE, STARS FROM BUSINESS WHERE BU_ID IN (" + inClause + ") " +
                    "ORDER BY STARS DESC";

            System.out.println(sql);
            System.out.println(Arrays.asList(items).toString().replace("[", "").replace("]", ""));

            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 1; i <= items.size(); i++) {
                ps.setString(i, items.get(i - 1));
            }
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = buildTableModel(rs);
            rs.close();

            if (model != null) {
                JTable resultsTable = new JTable(model);
                resultsScrollPane.add(resultsTable);
                resultsScrollPane.setViewportView(resultsTable);
                resultsScrollPane.repaint();

                resultsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        if (resultsTable.getSelectedRow() > -1) {
                            String buId = resultsTable.getValueAt(resultsTable.getSelectedRow(), 0).toString();
                            reviewSearchByBU(conn, buId);
                        }
                    }
                });

            }

            queryTextArea.setText(sql);
            parametersTextArea.setText(Arrays.asList(items).toString().replace("[", "").replace("]", ""));

        } catch (SQLException e) {
            System.out.println("Exception while querying for businesses: " + e.getMessage());
        }
        System.out.print("Done.\n");
    }

    private void reviewSearchByBU(Connection conn, String buId) {

        String sql = "SELECT Y.USER_NAME, R.REVIEW_DATE, R.TEXT_CONTENT, R.STARS, R.USEFUL, R.FUNNY, R.COOL FROM YELP_USER Y, REVIEW R WHERE " +
                "(R.BU_ID = '" + buId + "') AND (R.USER_ID = Y.USER_ID)";

        reviewSearch(conn, sql, "business");

    }

    private void reviewSearchByUser(Connection conn, String userId) {

        String sql = "SELECT Y.USER_NAME, R.REVIEW_DATE, R.TEXT_CONTENT, R.STARS, R.USEFUL, R.FUNNY, R.COOL FROM YELP_USER Y, REVIEW R WHERE " +
                "(Y.USER_ID = '" + userId + "' AND R.USER_ID = '" + userId + "')";

        reviewSearch(conn, sql, "user");

    }

    private void reviewSearch(Connection conn, String sql, String searchType) {

        System.out.println("------------------------------------------------------------");
        System.out.print("Searching... ");
        String searchCriteria = "";
        if (searchType.equals("user")) {
            searchCriteria = USER_SEARCH_CRITERIA;
        } else {
            searchCriteria = BU_SEARCH_CRITERIA;
        }
        try {
            String filters = "";
            boolean filterByDate = false;
            if (reviewDateFrom != null) {
                filters += " " + searchCriteria + " (R.REVIEW_DATE >= ? AND R.REVIEW_DATE <= ?)";
                if (reviewDateTo == null) {
                    reviewDateTo = new Date();
                }
                filterByDate = true;
            }

            boolean filterByStars = false;
            String starsOp = (String) reviewStarsComboBox.getSelectedItem();
            String stars = reviewStarsValue.getText();
            if (!starsOp.equals("Select operator") && !stars.equals("")) {
                filters += " " + searchCriteria + " (R.STARS " + starsOp + " ?)";
                filterByStars = true;
            }

            boolean filterByVotes = false;
            String voteOp = (String) reviewVoteOpComboBox.getSelectedItem();
            String voteType = (String) reviewVoteComboBox.getSelectedItem();
            String voteValue = reviewVoteValue.getText();
            if (!voteType.equals("Select vote type") && !voteOp.equals("Select operator") && !voteValue.equals("")) {
                filters += " " + searchCriteria + " (R." + voteType.toUpperCase() + " " + voteOp + " ?)";
                filterByVotes = true;
            }

            if (!filters.equals("")) {
                filters = filters.substring(searchCriteria.length() + 2);
                sql += " AND (" + filters + ")";
            }

            System.out.println(sql);
            queryTextArea.setText(sql);
            parametersTextArea.setText("");

            // set values
            PreparedStatement ps = conn.prepareStatement(sql);
            int id = 0;
            if (filterByDate) {
                ps.setDate((id+=1), new java.sql.Date(reviewDateFrom.getTime()));
                ps.setDate((id+=1), new java.sql.Date(reviewDateTo.getTime()));
            }

            if (filterByStars) {
                ps.setInt((id+=1), Integer.parseInt(stars));
            }

            if (filterByVotes) {
                ps.setInt((id+=1), Integer.parseInt(voteValue));
            }

            ResultSet rs2 = ps.executeQuery();
            DefaultTableModel model = buildTableModel(rs2);
            rs2.close();

            if (model != null) {
                JTable resultsTable = new JTable(model);
                resultsScrollPane.add(resultsTable);
                resultsScrollPane.setViewportView(resultsTable);
                resultsScrollPane.repaint();
            }

        } catch(SQLException e) {
            System.out.println("Exception while querying for reviews: " + e.getMessage());
        }
        System.out.println("Done.");

    }

    private void userSearch(Connection conn) {

        System.out.print("Searching... ");
        try {

            String sql = "SELECT Y.USER_ID, Y.USER_NAME, Y.YELPING_SINCE, Y.REVIEW_COUNT, Y.AVG_STARS, Y.NUM_OF_FRIENDS, Y.NUM_OF_VOTES FROM YELP_USER Y";

            boolean filterByMemberSince = false;
            String memberSinceAttr = "";
            if (memberSinceDate != null) {
                memberSinceAttr = "(Y.YELPING_SINCE >= ?)";
                filterByMemberSince = true;
            }

            boolean filterByReviewCount = false;
            String rcOp = (String) userReviewCountComboBox.getSelectedItem();
            String reviewCount = userReviewCountValue.getText();
            String reviewCountAttr = "";
            if (!rcOp.equals("Select operator") && !reviewCount.equals("")) {
                reviewCountAttr = "(Y.REVIEW_COUNT " + rcOp + " ?)";
                filterByReviewCount = true;
            }

            boolean filterByNumberOfFriends = false;
            String nofOp = (String) userNOFComboBox.getSelectedItem();
            String nof = userNOFValue.getText();
            String nofAttr = "";
            if (!nofOp.equals("Select operator") && !nof.equals("")) {
                nofAttr = "(Y.NUM_OF_FRIENDS " + nofOp + " ?)";
                filterByNumberOfFriends = true;
            }

            boolean filterByAvgStars = false;
            String avgStarsOp = (String) userAvgStarsComboBox.getSelectedItem();
            String avgStars = userAvgStarsValue.getText();
            String avgStarsAttr = "";
            if (!avgStarsOp.equals("Select operator") && !avgStars.equals("")) {
                avgStarsAttr = "(Y.AVG_STARS " + avgStarsOp + " ?)";
                filterByAvgStars = true;
            }

            boolean filterByNumberOfVotes = false;
            String novOp = (String) userNOVComboBox.getSelectedItem();
            String nov = userNOVValue.getText();
            String novAttr = "";
            if (!novOp.equals("Select operator") && !nov.equals("")) {
                novAttr = "(Y.NUM_OF_VOTES " + novOp + " ?)";
                filterByNumberOfVotes = true;
            }

            if (filterByMemberSince || filterByReviewCount || filterByNumberOfFriends || filterByAvgStars || filterByNumberOfVotes) {
                String innerQuery = "";
                if (filterByMemberSince) {
                    innerQuery += memberSinceAttr + " " + USER_SEARCH_CRITERIA + " ";
                }
                if (filterByReviewCount) {
                    innerQuery += reviewCountAttr + " " + USER_SEARCH_CRITERIA + " ";
                }
                if (filterByNumberOfFriends) {
                    innerQuery += nofAttr + " " + USER_SEARCH_CRITERIA + " ";
                }
                if (filterByAvgStars) {
                    innerQuery += avgStarsAttr + " " + USER_SEARCH_CRITERIA + " ";
                }
                if (filterByNumberOfVotes) {
                    innerQuery += novAttr + " " + USER_SEARCH_CRITERIA + " ";
                }
                innerQuery = innerQuery.substring(0, innerQuery.length() - (USER_SEARCH_CRITERIA.length() + 2));
                sql += " WHERE (" + innerQuery + ")";
            }

            System.out.println(sql);
            queryTextArea.setText(sql);
            PreparedStatement ps = conn.prepareStatement(sql);

            int id = 0;
            if (filterByMemberSince || filterByReviewCount || filterByNumberOfFriends || filterByAvgStars || filterByNumberOfVotes) {
                String innerQuery = "";
                if (filterByMemberSince) {
                    ps.setDate((id+=1), new java.sql.Date(memberSinceDate.getTime()));
                }
                if (filterByReviewCount) {
                    ps.setInt((id+=1), Integer.parseInt(reviewCount));
                }
                if (filterByNumberOfFriends) {
                    ps.setInt((id+=1), Integer.parseInt(nof));
                }
                if (filterByAvgStars) {
                    ps.setFloat((id+=1), Float.parseFloat(avgStars));
                }
                if (filterByNumberOfVotes) {
                    ps.setInt((id+=1), Integer.parseInt(nov));
                }
            }

            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = buildTableModel(rs);
            rs.close();

            if (model != null) {
                JTable resultsTable = new JTable(model);
                resultsScrollPane.add(resultsTable);
                resultsScrollPane.setViewportView(resultsTable);
                resultsScrollPane.repaint();

                resultsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        if (resultsTable.getSelectedRow() > -1) {
                            String userName = resultsTable.getValueAt(resultsTable.getSelectedRow(), 0).toString();
                            reviewSearchByUser(conn, userName);
                        }
                    }
                });

            }

        } catch(SQLException e) {
            System.out.println("Exception while querying for users: " + e.getMessage());
        }
        System.out.println("Done.");

    }

    private static DefaultTableModel buildTableModel(ResultSet rs) {

        try {

            ResultSetMetaData metaData = rs.getMetaData();

            // names of columns
            Vector<String> columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }

            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    if (rs.getObject(columnIndex) instanceof Clob) {
                        String str = rs.getString(columnIndex);
                        vector.add(str);
                    } else if (rs.getObject(columnIndex) instanceof Date) {
                        String date = rs.getDate(columnIndex).toString();
                        date = date.substring(0, 10);
                        vector.add(date);
                    } else {
                        vector.add(rs.getObject(columnIndex));
                    }

                }
                data.add(vector);
            }

            return new DefaultTableModel(data, columnNames);

        } catch (SQLException e) {
            System.out.println("Exception while building data model for results table: " + e.getMessage());
        }

        return null;
    }

    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd-MMM-yy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }

}
