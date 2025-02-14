package game.entities;

import java.util.ArrayList;
import java.util.SortedSet;

import game.entities.characters.Character;

public class Account {
    private Information information;
    private ArrayList<Character> characters;
    private int gamesPlayed;

    // Constructor
    public Account(ArrayList<Character> characters, int gamesPlayed, Information information) {
        this.information = information;
        this.characters = characters;
        this.gamesPlayed = gamesPlayed;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public int increaseGamesPlayed() {
        gamesPlayed++;
        return gamesPlayed;
    }

    @Override
    public String toString() {
        return "Account: " + "Name = " + information.getName() + ", Email = " + information.getCredentials().getEmail() + ", Country = " + information.getCountry() + ", Games Played = " + gamesPlayed + ", Characters = " + characters;
    }

    // intern class
    public static class Information {
        private Credentials credentials;
        private SortedSet<String> favouriteGames;
        private String name;
        private String country;

        // Constructor
        public Information(Credentials credentials, SortedSet<String> favouriteGames, String name, String country) {
            this.credentials = credentials;
            this.favouriteGames = favouriteGames;
            this.name = name;
            this.country = country;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public SortedSet<String> getFavouriteGames() {
            return favouriteGames;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        @Override
        public String toString() {
            return "Information: " + "Name = " + name + ", Country = " + country + ", Favorite Games = " + favouriteGames + ", Credentials = " + credentials;
        }
    }
}
