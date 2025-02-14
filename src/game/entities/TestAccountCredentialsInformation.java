package game.entities;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class TestAccountCredentialsInformation {
    public static void main(String[] args) {
        ArrayList<Account> accounts = new ArrayList<>();

        // first account
        Credentials credentials1 = new Credentials("marcel@yahoo.com", "6K7GUxjsAc");
        SortedSet<String> favouriteGames1 = new TreeSet<>();
        favouriteGames1.add("LOL");
        favouriteGames1.add("Valorant");
        Account.Information info1 = new Account.Information(credentials1, favouriteGames1, "Marcel", "Romania");
        Account account1 = new Account(new ArrayList<>(), 15, info1);
        accounts.add(account1);

        // print the first account
        System.out.println("Email: " + info1.getCredentials().getEmail());
        System.out.println("Password: " + info1.getCredentials().getPassword());
        System.out.println("Name: " + info1.getName());
        System.out.println("Country: " + info1.getCountry());
        System.out.println("Favorite Games: " + info1.getFavouriteGames());

        // second account
        Credentials credentials2 = new Credentials("john@yahoo.com", "12345");
        SortedSet<String> favouriteGames2 = new TreeSet<>();
        favouriteGames2.add("League of Legends");
        favouriteGames2.add("Minecraft");
        Account.Information info2 = new Account.Information(credentials2, favouriteGames2, "Alex", "Romania");
        Account account2 = new Account(new ArrayList<>(), 10, info2);
        accounts.add(account2);

        // print all accounts
        for (Account account : accounts) {
            System.out.println("\nAccount Name: " + account.getInformation().getName());
            System.out.println("Email: " + account.getInformation().getCredentials().getEmail());
            System.out.println("Country: " + account.getInformation().getCountry());
            System.out.println("Favorite Games: " + account.getInformation().getFavouriteGames());
        }
    }
}
