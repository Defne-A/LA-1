package la1;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class MusicView {
    private LibraryModel model;
    private Scanner scanner;
    private boolean running = true;

    public MusicView() {
        model = new LibraryModel();
        scanner = new Scanner(System.in);
    }

    public void start() {
        displayWelcome();
        
        while (running) {
        	model.resetPrintString(); //resets the output
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    searchMusicStore();
                    break;
                case 2:
                    searchUserLibrary();
                    break;
                case 3:
                    addToLibrary();
                    break;
                case 4:
                    getLibraryItems();
                    break;
                case 5:
                    managePlaylist();
                    break;
                case 6:
                    markFavorite();
                    break;
                case 7:
                    rateSong();
                    break;
                case 8:
                    //play a song
                    playSong();
                	break;
                case 9:
                	running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private void displayWelcome() {
        System.out.println("=======================================");
        System.out.println("    Welcome to the Music Library!");
        System.out.println("=======================================");
    }
    
    private void displayMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Search for information from the music store");
        System.out.println("2. Search for information from your library");
        System.out.println("3. Add or remove something to your library");
        System.out.println("4. Get a list of items from your library");
        System.out.println("5. Create or manage playlists");
        System.out.println("6. Mark a song as favorite");
        System.out.println("7. Rate a song");
        System.out.println("8. Play a song");
        System.out.println("9. Exit");
    }
    
    private void searchMusicStore() {
        System.out.println("\nSearch Options:");
        System.out.println("1. Search for a song by title");
        System.out.println("2. Search for a song by artist");
        System.out.println("3. Search for an album by title");
        System.out.println("4. Search for an album by artist");
        System.out.println("5. Return to main menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        if (choice == 5) return;
        
        String searchTerm = getStringInput("Enter search term: ");
        
        switch (choice) {
            case 1:
                System.out.println(model.searchSongByTitle(searchTerm));
                break;
            case 2:
                System.out.println(model.searchSongByArtist(searchTerm));
                break;
            case 3:
                System.out.println(model.searchAlbumByTitle(searchTerm));
                break;
            case 4:
               System.out.println(model.searchAlbumByArtist(searchTerm));
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
    
    private void searchUserLibrary() {
        System.out.println("\nSearch Library Options:");
        System.out.println("1. Search for a song by title");
        System.out.println("2. Search for a song by artist");
        System.out.println("3. Search for an album by title");
        System.out.println("4. Search for an album by artist");
        System.out.println("5. Search for a playlist by name");
        System.out.println("6. Return to main menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        if (choice == 6) return;
        
        String searchTerm = getStringInput("Enter search term: ");
        
        switch (choice) {
            case 1:
                System.out.println(model.searchLibrarySongByTitle(searchTerm));
                break;
            case 2:
                System.out.println(model.searchLibrarySongByArtist(searchTerm));
                break;
            case 3:
                System.out.println(model.searchLibraryAlbumByTitle(searchTerm));
            case 4:
                System.out.println(model.searchLibraryAlbumByArtist(searchTerm));  
                break;
            case 5:
                System.out.println(model.searchPlaylistByName(searchTerm));
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
    private void addToLibrary() {
        System.out.println("\nAdd to Library:");
        System.out.println("1. Add a song to your library");
        System.out.println("2. Add an album to your library");
        System.out.println("3. Remove a song from user library");
        System.out.println("4. Remove an album from user library");
        System.out.println("5. Return to main menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        if (choice == 5) return;
        
        switch (choice) {
            case 1:
                String songTitle = getStringInput("Enter song title: ");
                String songArtist = getStringInput("Enter song artist: ");
                System.out.println(model.addSongToLibrary(songTitle, songArtist));
                break;
            case 2:
                String albumTitle = getStringInput("Enter album title: ");
                String albumArtist = getStringInput("Enter album artist: ");
                System.out.println(model.addAlbumToLibrary(albumTitle, albumArtist));
                break;
            case 3:
            	 songTitle = getStringInput("Enter song title: ");
                 songArtist = getStringInput("Enter song artist: ");
                 System.out.println(model.removeSongLibrary(songTitle, songArtist));
                 break;
            case 4:
            	 albumTitle = getStringInput("Enter album title: ");
                 albumArtist = getStringInput("Enter album artist: ");
                 System.out.println(model.removeAlbumLibrary(albumTitle, albumArtist));
                 break;
            default:
                 System.out.println("Invalid option. Please try again.");
        }
    }
    
    private void getLibraryItems() {
        System.out.println("\nGet Library Items:");
        System.out.println("1. Get a list of song titles");
        System.out.println("2. Get a list of artists");
        System.out.println("3. Get a list of albums");
        System.out.println("4. Get a list of playlists");
        System.out.println("5. Get a list of favorite songs");
        System.out.println("6. Return to main menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        if (choice == 6) return;
        
        switch (choice) {
            case 1:
                System.out.println(model.getLibrarySongs());
                break;
            case 2:
               System.out.println(model.getLibraryArtists());
                break;
            case 3:
              System.out.println(model.getLibraryAlbums());
                break;
            case 4:
                System.out.println(model.getLibraryPlaylists());
                break;
            case 5:
                System.out.println(model.getFavoriteSongs());
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
    
    private void managePlaylist() {
        System.out.println("\nPlaylist Management:");
        System.out.println("1. Create a new playlist");
        System.out.println("2. Add a song to a playlist");
        System.out.println("3. Remove a song from a playlist");
        System.out.println("4. Return to main menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        if (choice == 4) return;
        
        switch (choice) {
            case 1:
                String playlistName = getStringInput("Enter playlist name: ");
                System.out.println(model.createPlaylist(playlistName));
                break;
            case 2:
                String addPlaylistName = getStringInput("Enter playlist name: ");
                String songTitle = getStringInput("Enter song title: ");
                String songArtist = getStringInput("Enter song artist: ");
              System.out.println(model.addSongToPlaylist(addPlaylistName, songTitle, songArtist));
                break;
            case 3:
                String removePlaylistName = getStringInput("Enter playlist name: ");
                String removeSongTitle = getStringInput("Enter song title to remove: ");
                String removeSongArtist = getStringInput("Enter song artist: ");
                System.out.println(model.removeSongFromPlaylist(removePlaylistName, removeSongTitle, removeSongArtist));
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
    
    private void markFavorite() {
        String songTitle = getStringInput("Enter song title to mark as favorite: ");
        String songArtist = getStringInput("Enter song artist: ");
        model.markSongAsFavorite(songTitle, songArtist);
    }
    
    private void rateSong() {
        String songTitle = getStringInput("Enter song title to rate: ");
        String songArtist = getStringInput("Enter song artist: ");
        
        System.out.println("Ratings: 1 = ONE, 2 = TWO, 3 = THREE, 4 = FOUR, 5 = FIVE");
        int rating = getIntInput("Enter rating (1-5): ");
        
        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating. Please enter a number between 1 and 5.");
            return;
        }
        
        model.rateSong(songTitle, songArtist, rating);
    }
    private void playSong() {
    	String songTitle = getStringInput("Enter song title to play: ");
        String songArtist = getStringInput("Enter song artist: ");
        model.playSong(songTitle, songArtist);
    }
 
    
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        
        // Re-prompt if empty input
        while (input.isEmpty()) {
            System.out.println("Input cannot be empty. Please try again.");
            System.out.print(prompt);
            input = scanner.nextLine().trim();
        }
        
        return input;
    }
    
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Clear the invalid input
            System.out.print(prompt);
        }
        
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        return value;
    }

    public static void main(String[] args) {
        MusicView view = new MusicView();
        view.start();
    }
}
