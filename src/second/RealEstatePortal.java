package second;

import java.util.NoSuchElementException;
import java.util.Scanner;

abstract class MarketReport {
    public abstract void generateReport(Scanner scanner);
}

class MarketTrendsReport extends MarketReport {
    @Override
    public void generateReport(Scanner scanner) {
        System.out.println("Generating Market Trends Report...");

        System.out.print("Enter the number of new property listings: ");
        int newPropertyListings = scanner.nextInt();

        System.out.print("Enter the number of properties sold: ");
        int propertiesSold = scanner.nextInt();

        System.out.print("Enter the average price of properties: ");
        double averagePrice = scanner.nextDouble();

        System.out.println("Market Trends Report Summary:");
        System.out.println("New Property Listings: " + newPropertyListings);
        System.out.println("Properties Sold: " + propertiesSold);
        System.out.println("Average Price: $" + averagePrice);
    }
}

class PropertyPriceAnalysisReport extends MarketReport {
    @Override
    public void generateReport(Scanner scanner) {
        System.out.println("Generating Property Price Analysis Report...");

        System.out.print("Enter the total number of properties in the market: ");
        int totalProperties = scanner.nextInt();

        System.out.print("Enter the average price per square foot: $");
        double averagePricePerSquareFoot = scanner.nextDouble();

        System.out.println("Property Price Analysis Report Summary:");
        System.out.println("Total Properties in the Market: " + totalProperties);
        System.out.println("Average Price per Square Foot: $" + averagePricePerSquareFoot);
    }
}

class NeighborhoodStatisticsReport extends MarketReport {
    @Override
    public void generateReport(Scanner scanner) {
        System.out.println("Generating Neighborhood Statistics Report...");

        System.out.print("Enter the number of neighborhoods analyzed: ");
        int neighborhoods = scanner.nextInt();

        System.out.print("Enter average rating (1-5): ");
        double averageRating = scanner.nextDouble();

        System.out.println("Neighborhood Statistics Report Summary:");
        System.out.println("Neighborhoods Analyzed: " + neighborhoods);
        System.out.println("Average Rating: " + averageRating);
    }
}

class User {
    private String username;
    private String password;

    public void createCredentials(Scanner scanner) {
        try {
            System.out.print("Enter a username: ");
            this.username = scanner.nextLine();

            if (isInvalidInput(username)) {
                System.out.println("Invalid username. Exiting...");
                System.exit(1);
            }

            System.out.print("Enter a password: ");
            this.password = scanner.nextLine();

            if (isInvalidInput(password)) {
                System.out.println("Invalid password. Exiting...");
                System.exit(1);
            }

        } catch (NoSuchElementException e) {
            System.out.println("Error reading input. Exiting...");
            System.exit(1);
        }
    }

    public boolean login(Scanner scanner) {
        try {
            System.out.print("Enter your username: ");
            String enteredUsername = scanner.nextLine();

            System.out.print("Enter your password: ");
            String enteredPassword = scanner.nextLine();

            return enteredUsername.equals(username) && enteredPassword.equals(password);

        } catch (NoSuchElementException e) {
            System.out.println("Error reading input. Exiting...");
            System.exit(1);
            return false;
        }
    }

    public String getUsername() {
        return username;
    }

    public void requestMarketReport(int reportType, Scanner scanner) {
        MarketReport marketReport;

        switch (reportType) {
            case 1:
                marketReport = new MarketTrendsReport();
                break;
            case 2:
                marketReport = new PropertyPriceAnalysisReport();
                break;
            case 3:
                marketReport = new NeighborhoodStatisticsReport();
                break;
            default:
                System.out.println("Invalid report type selected.");
                return;
        }

        marketReport.generateReport(scanner);
    }

    public void receiveReport() {
        System.out.println("User received the market report.");
    }

    private boolean isInvalidInput(String input) {
        return input == null || input.trim().isEmpty();
    }
}

public class RealEstatePortal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = new User();

        System.out.println("Welcome to the Real Estate Portal!");

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number (1-3).");
                scanner.nextLine(); // discard invalid input
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    user.createCredentials(scanner);
                    System.out.println("Signup successful. Please login to continue.");
                    break;

                case 2:
                    if (user.login(scanner)) {
                        System.out.println("Welcome, " + user.getUsername() + "!");

                        boolean generateMore = true;
                        while (generateMore) {
                            System.out.println("\nSelect the type of market report:");
                            System.out.println("1. Market Trends");
                            System.out.println("2. Property Price Analysis");
                            System.out.println("3. Neighborhood Statistics");
                            System.out.print("Enter choice: ");

                            if (!scanner.hasNextInt()) {
                                System.out.println("Invalid input. Please enter 1, 2, or 3.");
                                scanner.nextLine(); // discard invalid input
                                continue;
                            }

                            int reportType = scanner.nextInt();
                            scanner.nextLine(); // consume newline

                            user.requestMarketReport(reportType, scanner);
                            System.out.println("System is generating the report...");
                            user.receiveReport();

                            // ✅ FIXED: ask repeatedly until user gives valid input
                            while (true) {
                                System.out.print("Do you want to generate another report? (yes/no): ");
                                String again = scanner.nextLine().trim().toLowerCase();

                                if (again.equals("yes")) {
                                    break; // continue loop
                                } else if (again.equals("no")) {
                                    generateMore = false;
                                    break;
                                } else {
                                    System.out.println("Please enter 'yes' or 'no'.");
                                }
                            }
                        }

                    } else {
                        System.out.println("Login failed. Please try again.");
                    }
                    break;

                case 3:
                    System.out.println("Thank you for using the Real Estate Portal!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
