package hw3;

import java.sql.*;

public class CreateIndex {

    public static void createIndex(Connection connection) {

        try {
            System.out.println("Indexing...");

            Statement stmt = connection.createStatement();

            String sql1 = "CREATE INDEX bu_cat_idx ON bu_category (bu_id)";
            stmt.executeUpdate(sql1);
            System.out.println("Created bu_cat_idx on bu_category.");

            String sql2 = "CREATE INDEX bu_subcat_idx ON bu_subcategory (bu_id, subcategory)";
            stmt.executeUpdate(sql2);
            System.out.println("Created bu_subcat_idx on bu_subcategory.");

            String sql3 = "CREATE INDEX bu_attr_idx ON bu_attribute (bu_id, attr_name, attr_value)";
            stmt.executeUpdate(sql3);
            System.out.println("Created bu_attr_idx on bu_attribute.");

            String sql4 = "CREATE INDEX business_idx ON business (name, city, state, stars)";
            stmt.executeUpdate(sql4);
            System.out.println("Created business_idx on business.");

            String sql5 = "CREATE INDEX review_idx ON review " +
                    "(bu_id, user_id, stars, review_date)";
            stmt.executeUpdate(sql5);
            System.out.println("Created review_idx on review.");

            String sql6 = "CREATE INDEX yelp_user_idx ON yelp_user " +
                    "(user_id, user_name, yelping_since, review_count, avg_stars)";
            stmt.executeUpdate(sql6);
            System.out.println("Created yelp_user_idx on yelp_user.");

            stmt.close();

            System.out.println("Done indexing.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
