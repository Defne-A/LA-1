package la1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LibraryModel {
    private MusicStore musicStore;
    private HashMap<String, ArrayList<Song>> userLibrarySongs;
    private HashMap<String, ArrayList<Album>> userLibraryAlbums;
    private ArrayList<Playlist> userPlaylists;
    
    public LibraryModel() {
        try {
            // Initialize the music store
            musicStore = new MusicStore();
            musicStore.readFile();
            
            // Initialize user's library=
            userLibrarySongs = new HashMap<>();
            userLibraryAlbums = new HashMap<>();
            userPlaylists = new ArrayList<>();
           
        } catch (IOException e) {
            e.printStackTrace(System.out); //ask i fthis is covered or not?
        }
    }
    
    // Music Store Search Methods
    
    public void searchSongByTitle(String title) {
        ArrayList<Song> results = musicStore.searchSongByTitle(title);
        displaySongResults(results, "music store");
    }
    
    public void searchSongByArtist(String artist) {
        ArrayList<Song> results = musicStore.searchSongByArtist(artist);
        displaySongResults(results, "music store");
    }
    
    public void searchAlbumByTitle(String title) {
        ArrayList<Album> results = musicStore.searchAlbumByTitle(title);
        displayAlbumResults(results, "music store");
    }
    
    public void searchAlbumByArtist(String artist) {
        ArrayList<Album> results = musicStore.searchAlbumByArtist(artist);
        displayAlbumResults(results, "music store");
    }
    
    // Library Search Methods
    
    public void searchLibrarySongByTitle(String title) {
        ArrayList<Song> results = new ArrayList<>();
        
        for (ArrayList<Song> artistSongs : userLibrarySongs.values()) {
            for (Song song : artistSongs) {
                if (song.getSongTitle().toLowerCase().contains(title.toLowerCase())) {
                    results.add(song);
                }
            }
        }
        
        displaySongResults(results, "your library");
    }
    
    public void searchLibrarySongByArtist(String artist) {
        ArrayList<Song> results = new ArrayList<>();
        
        if (userLibrarySongs.containsKey(artist.toLowerCase())) {
            results.addAll(userLibrarySongs.get(artist.toLowerCase()));
        }
        
        displaySongResults(results, "your library");
    }
    
    public void searchLibraryAlbumByTitle(String title) {
        ArrayList<Album> results = new ArrayList<>();
        
        for (ArrayList<Album> artistAlbums : userLibraryAlbums.values()) {
            for (Album album : artistAlbums) {
                if (album.getAlbumTitle().toLowerCase().contains(title.toLowerCase())) {
                    results.add(album);
                }
            }
        }
        
        displayAlbumResults(results, "your library");
    }
    
    public void searchLibraryAlbumByArtist(String artist) {
        ArrayList<Album> results = new ArrayList<>();
        
        if (userLibraryAlbums.containsKey(artist.toLowerCase())) {
            results.addAll(userLibraryAlbums.get(artist.toLowerCase()));
        }
        
        displayAlbumResults(results, "your library");
    }
    
    public void searchPlaylistByName(String name) {
        ArrayList<Playlist> results = new ArrayList<>();
        
        for (Playlist playlist : userPlaylists) {
            if (playlist.getTitle().toLowerCase().contains(name.toLowerCase())) {
                results.add(playlist);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("No playlists found with that name in your library.");
        } else {
            System.out.println("\nFound " + results.size() + " playlist(s) in your library:");
            for (Playlist playlist : results) {
                System.out.println("Playlist: " + playlist.getTitle());
                ArrayList<Song> songs = playlist.getPlaylist();
                if (songs != null && !songs.isEmpty()) {
                    System.out.println("Songs:");
                    for (Song song : songs) {
                        System.out.println("  - " + song.getSongTitle() + " by " + song.getSongArtist());
                    }
                } else {
                    System.out.println("  (No songs in this playlist)");
                }
                System.out.println();
            }
        }
    }
    
    // Add to Library Methods
    
    public void addSongToLibrary(String title, String artist) {
        Song song = musicStore.getSong(title, artist);
        
        if (song == null) {
            System.out.println("Song not found in the music store.");
            return;
        }
        
        String artistKey = artist.toLowerCase();
        
        if (!userLibrarySongs.containsKey(artistKey)) {
            userLibrarySongs.put(artistKey, new ArrayList<Song>());
        }
        
        // Check if song already exists
        boolean exists = false;
        for (Song librarySong : userLibrarySongs.get(artistKey)) {
            if (librarySong.getSongTitle().equals(title)) {
                exists = true;
                break;
            }
        }
        
        if (exists) {
            System.out.println("Song already exists in your library.");
        } else {
            userLibrarySongs.get(artistKey).add(song);
            System.out.println("Added '" + title + "' by " + artist + " to your library.");
        }
    }
    
    public void addAlbumToLibrary(String title, String artist) {
        Album album = musicStore.getAlbum(title, artist);
        
        if (album == null) {
            System.out.println("Album not found in the music store.");
            return;
        }
        
        String artistKey = artist.toLowerCase();
        
        if (!userLibraryAlbums.containsKey(artistKey)) {
            userLibraryAlbums.put(artistKey, new ArrayList<Album>());
        }
        
        // Check if album already exists
        boolean exists = false;
        for (Album libraryAlbum : userLibraryAlbums.get(artistKey)) {
            if (libraryAlbum.getAlbumTitle().equals(title)) {
                exists = true;
                break;
            }
        }
        
        if (exists) {
            System.out.println("Album already exists in your library.");
        } else {
            userLibraryAlbums.get(artistKey).add(album);
            System.out.println("Added album '" + title + "' by " + artist + " to your library.");
            
            // Also add all songs from the album to the user's library
            ArrayList<Song> albumSongs = musicStore.getSongsFromAlbum(title, artist);
            if (albumSongs != null) {
                for (Song song : albumSongs) {
                    addSongToLibrary(song.getSongTitle(), song.getSongArtist());
                }
            }
        }
    }
    
    // Get Library Items Methods
    
    public void getLibrarySongs() {
        int totalSongs = 0;
        
        System.out.println("\nSongs in your library:");
        
        for (String artist : userLibrarySongs.keySet()) {
            ArrayList<Song> songs = userLibrarySongs.get(artist);
            totalSongs += songs.size();
            
            for (Song song : songs) {
                System.out.println("- " + song.getSongTitle() + " by " + song.getSongArtist());
            }
        }
        
        if (totalSongs == 0) {
            System.out.println("No songs in your library yet.");
        } else {
            System.out.println("\nTotal songs: " + totalSongs);
        }
    }
    
    public void getLibraryArtists() {
        System.out.println("\nArtists in your library:");
        
        if (userLibrarySongs.isEmpty()) {
            System.out.println("No artists in your library yet.");
        } else {
            for (String artist : userLibrarySongs.keySet()) {
                System.out.println("- " + artist);
            }
            System.out.println("\nTotal artists: " + userLibrarySongs.size());
        }
    }
    
    public void getLibraryAlbums() {
        int totalAlbums = 0;
        
        System.out.println("\nAlbums in your library:");
        
        for (String artist : userLibraryAlbums.keySet()) {
            ArrayList<Album> albums = userLibraryAlbums.get(artist);
            totalAlbums += albums.size();
            
            for (Album album : albums) {
                System.out.println("- " + album.getAlbumTitle() + " by " + album.getArtist() + 
                                   " (" + album.getYear() + ") - " + album.getGenre());
            }
        }
        
        if (totalAlbums == 0) {
            System.out.println("No albums in your library yet.");
        } else {
            System.out.println("\nTotal albums: " + totalAlbums);
        }
    }
    
    public void getLibraryPlaylists() {
        System.out.println("\nPlaylists in your library:");
        
        if (userPlaylists.isEmpty()) {
            System.out.println("No playlists in your library yet.");
        } else {
            for (Playlist playlist : userPlaylists) {
                System.out.println("- " + playlist.getTitle());
            }
            System.out.println("\nTotal playlists: " + userPlaylists.size());
        }
    }
    
    public void getFavoriteSongs() {
        ArrayList<Song> favorites = new ArrayList<>();
        
        for (ArrayList<Song> artistSongs : userLibrarySongs.values()) {
            for (Song song : artistSongs) {
                if (song.isFavorite() != null && song.isFavorite()) {
                    favorites.add(song);
                }
            }
        }
        
        System.out.println("\nYour favorite songs:");
        
        if (favorites.isEmpty()) {
            System.out.println("No favorite songs yet.");
        } else {
            for (Song song : favorites) {
                System.out.println("- " + song.getSongTitle() + " by " + song.getSongArtist());
            }
            System.out.println("\nTotal favorites: " + favorites.size());
        }
    }
    
    // Playlist Management Methods
    
    public void createPlaylist(String name) {
        // Check if a playlist with the same name already exists
        for (Playlist playlist : userPlaylists) {
            if (playlist.getTitle().equalsIgnoreCase(name)) {
                System.out.println("A playlist with this name already exists.");
                return;
            }
        }
        
        userPlaylists.add(new Playlist(name));
        System.out.println("Created playlist: " + name);
    }
    
    public void addSongToPlaylist(String playlistName, String songTitle, String songArtist) {
        // Find the playlist
        Playlist targetPlaylist = null;
        for (Playlist playlist : userPlaylists) {
            if (playlist.getTitle().equalsIgnoreCase(playlistName)) {
                targetPlaylist = playlist;
                break;
            }
        }
        
        if (targetPlaylist == null) {
            System.out.println("Playlist not found: " + playlistName);
            return;
        }
        
        // Find the song in the user's library
        Song targetSong = null;
        String artistKey = songArtist.toLowerCase();
        
        if (userLibrarySongs.containsKey(artistKey)) {
            for (Song song : userLibrarySongs.get(artistKey)) {
                if (song.getSongTitle().equalsIgnoreCase(songTitle)) {
                    targetSong = song;
                    break;
                }
            }
        }
        
        if (targetSong == null) {
            System.out.println("Song not found in your library. Add it to your library first.");
            return;
        }
        
        // Add the song to the playlist
        targetPlaylist.addSong(targetSong);
        System.out.println("Added '" + songTitle + "' to playlist '" + playlistName + "'");
    }
    
    public void removeSongFromPlaylist(String playlistName, String songTitle, String songArtist) {
        // Find the playlist
        Playlist targetPlaylist = null;
        for (Playlist playlist : userPlaylists) {
            if (playlist.getTitle().equalsIgnoreCase(playlistName)) {
                targetPlaylist = playlist;
                break;
            }
        }
        
        if (targetPlaylist == null) {
            System.out.println("Playlist not found: " + playlistName);
            return;
        }
        
        // Get the playlist songs
        ArrayList<Song> playlistSongs = targetPlaylist.getPlaylist();
        
        if (playlistSongs == null || playlistSongs.isEmpty()) {
            System.out.println("The playlist is empty.");
            return;
        }
        
        // Find and remove the song
        boolean removed = false;
        ArrayList<Song> updatedSongs = new ArrayList<>();
        
        for (Song song : playlistSongs) {
            if (song.getSongTitle().equalsIgnoreCase(songTitle) && 
                song.getSongArtist().equalsIgnoreCase(songArtist)) {
                removed = true;
            } else {
                updatedSongs.add(song);
            }
        }
        
        if (removed) {
            // Create a new playlist with the updated songs
            Playlist updatedPlaylist = new Playlist(playlistName);
            for (Song song : updatedSongs) {
                updatedPlaylist.addSong(song);
            }
            
            // Replace the old playlist
            int index = userPlaylists.indexOf(targetPlaylist);
            userPlaylists.set(index, updatedPlaylist);
            
            System.out.println("Removed '" + songTitle + "' from playlist '" + playlistName + "'");
        } else {
            System.out.println("Song not found in the playlist.");
        }
    }
    
    // Song Rating and Favorite Methods
    
    public void markSongAsFavorite(String title, String artist) {
        Song song = findSongInLibrary(title, artist);
        
        if (song == null) {
            System.out.println("Song not found in your library.");
            return;
        }
        
        song.setFav();
        System.out.println("Marked '" + title + "' by " + artist + " as favorite.");
    }
    
    public void rateSong(String title, String artist, int rating) {
        Song song = findSongInLibrary(title, artist);
        
        if (song == null) {
            System.out.println("Song not found in your library.");
            return;
        }
        
        // Convert int to Rating enum
        Song.Rating ratingEnum = Song.Rating.values()[rating - 1];
        
        // Set the rating (this requires adding a setRating method to the Song class)
        setSongRating(song, ratingEnum);
        
        System.out.println("Rated '" + title + "' by " + artist + " as " + ratingEnum);
        
        // If rated 5, automatically set as favorite
        if (rating == 5) {
            song.setFav();
            System.out.println("Song rated 5/5, automatically marked as favorite.");
        }
    }
    
    // Helper Methods
    
    private void setSongRating(Song song, Song.Rating rating) {
        // This method would set the rating on the song object
        // Since the Song class doesn't have a setRating method, you'd need to add one
        // For now, this is a placeholder
        System.out.println("Setting rating " + rating + " (You need to implement setRating in Song class)");
    }
    
    private Song findSongInLibrary(String title, String artist) {
        String artistKey = artist.toLowerCase();
        
        if (userLibrarySongs.containsKey(artistKey)) {
            for (Song song : userLibrarySongs.get(artistKey)) {
                if (song.getSongTitle().equalsIgnoreCase(title)) {
                    return song;
                }
            }
        }
        
        return null;
    }
    
    private void displaySongResults(ArrayList<Song> results, String source) {
        if (results == null || results.isEmpty()) {
            System.out.println("No songs found in " + source + ".");
        } else {
            System.out.println("\nFound " + results.size() + " song(s) in " + source + ":");
            for (Song song : results) {
                System.out.println("- " + song.getSongTitle() + " by " + song.getSongArtist());
            }
        }
    }
    
    private void displayAlbumResults(ArrayList<Album> results, String source) {
        if (results == null || results.isEmpty()) {
            System.out.println("No albums found in " + source + ".");
        } else {
            System.out.println("\nFound " + results.size() + " album(s) in " + source + ":");
            for (Album album : results) {
                System.out.println("Album: " + album.getAlbumTitle() + " by " + album.getArtist());
                System.out.println("Year: " + album.getYear() + " | Genre: " + album.getGenre());
                System.out.println("Songs:");
                System.out.println(album.toString());
                System.out.println();
            }
        }
    }
}package la1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LibraryModel {
    private MusicStore musicStore;
    private HashMap<String, ArrayList<Song>> userLibrarySongs;
    private HashMap<String, ArrayList<Album>> userLibraryAlbums;
    private ArrayList<Playlist> userPlaylists;
    
    public LibraryModel() {
        try {
            // Initialize the music store
            musicStore = new MusicStore();
            musicStore.readFile();
            
            // Initialize user's library=
            userLibrarySongs = new HashMap<>();
            userLibraryAlbums = new HashMap<>();
            userPlaylists = new ArrayList<>();
           
        } catch (IOException e) {
            e.printStackTrace(System.out); //ask i fthis is covered or not?
        }
    }
    
    // Music Store Search Methods
    
    public void searchSongByTitle(String title) {
        ArrayList<Song> results = musicStore.searchSongByTitle(title);
        displaySongResults(results, "music store");
    }
    
    public void searchSongByArtist(String artist) {
        ArrayList<Song> results = musicStore.searchSongByArtist(artist);
        displaySongResults(results, "music store");
    }
    
    public void searchAlbumByTitle(String title) {
        ArrayList<Album> results = musicStore.searchAlbumByTitle(title);
        displayAlbumResults(results, "music store");
    }
    
    public void searchAlbumByArtist(String artist) {
        ArrayList<Album> results = musicStore.searchAlbumByArtist(artist);
        displayAlbumResults(results, "music store");
    }
    
    // Library Search Methods
    
    public void searchLibrarySongByTitle(String title) {
        ArrayList<Song> results = new ArrayList<>();
        
        for (ArrayList<Song> artistSongs : userLibrarySongs.values()) {
            for (Song song : artistSongs) {
                if (song.getSongTitle().toLowerCase().contains(title.toLowerCase())) {
                    results.add(song);
                }
            }
        }
        
        displaySongResults(results, "your library");
    }
    
    public void searchLibrarySongByArtist(String artist) {
        ArrayList<Song> results = new ArrayList<>();
        
        if (userLibrarySongs.containsKey(artist.toLowerCase())) {
            results.addAll(userLibrarySongs.get(artist.toLowerCase()));
        }
        
        displaySongResults(results, "your library");
    }
    
    public void searchLibraryAlbumByTitle(String title) {
        ArrayList<Album> results = new ArrayList<>();
        
        for (ArrayList<Album> artistAlbums : userLibraryAlbums.values()) {
            for (Album album : artistAlbums) {
                if (album.getAlbumTitle().toLowerCase().contains(title.toLowerCase())) {
                    results.add(album);
                }
            }
        }
        
        displayAlbumResults(results, "your library");
    }
    
    public void searchLibraryAlbumByArtist(String artist) {
        ArrayList<Album> results = new ArrayList<>();
        
        if (userLibraryAlbums.containsKey(artist.toLowerCase())) {
            results.addAll(userLibraryAlbums.get(artist.toLowerCase()));
        }
        
        displayAlbumResults(results, "your library");
    }
    
    public void searchPlaylistByName(String name) {
        ArrayList<Playlist> results = new ArrayList<>();
        
        for (Playlist playlist : userPlaylists) {
            if (playlist.getTitle().toLowerCase().contains(name.toLowerCase())) {
                results.add(playlist);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("No playlists found with that name in your library.");
        } else {
            System.out.println("\nFound " + results.size() + " playlist(s) in your library:");
            for (Playlist playlist : results) {
                System.out.println("Playlist: " + playlist.getTitle());
                ArrayList<Song> songs = playlist.getPlaylist();
                if (songs != null && !songs.isEmpty()) {
                    System.out.println("Songs:");
                    for (Song song : songs) {
                        System.out.println("  - " + song.getSongTitle() + " by " + song.getSongArtist());
                    }
                } else {
                    System.out.println("  (No songs in this playlist)");
                }
                System.out.println();
            }
        }
    }
    
    // Add to Library Methods
    
    public void addSongToLibrary(String title, String artist) {
        Song song = musicStore.getSong(title, artist);
        
        if (song == null) {
            System.out.println("Song not found in the music store.");
            return;
        }
        
        String artistKey = artist.toLowerCase();
        
        if (!userLibrarySongs.containsKey(artistKey)) {
            userLibrarySongs.put(artistKey, new ArrayList<Song>());
        }
        
        // Check if song already exists
        boolean exists = false;
        for (Song librarySong : userLibrarySongs.get(artistKey)) {
            if (librarySong.getSongTitle().equals(title)) {
                exists = true;
                break;
            }
        }
        
        if (exists) {
            System.out.println("Song already exists in your library.");
        } else {
            userLibrarySongs.get(artistKey).add(song);
            System.out.println("Added '" + title + "' by " + artist + " to your library.");
        }
    }
    
    public void addAlbumToLibrary(String title, String artist) {
        Album album = musicStore.getAlbum(title, artist);
        
        if (album == null) {
            System.out.println("Album not found in the music store.");
            return;
        }
        
        String artistKey = artist.toLowerCase();
        
        if (!userLibraryAlbums.containsKey(artistKey)) {
            userLibraryAlbums.put(artistKey, new ArrayList<Album>());
        }
        
        // Check if album already exists
        boolean exists = false;
        for (Album libraryAlbum : userLibraryAlbums.get(artistKey)) {
            if (libraryAlbum.getAlbumTitle().equals(title)) {
                exists = true;
                break;
            }
        }
        
        if (exists) {
            System.out.println("Album already exists in your library.");
        } else {
            userLibraryAlbums.get(artistKey).add(album);
            System.out.println("Added album '" + title + "' by " + artist + " to your library.");
            
            // Also add all songs from the album to the user's library
            ArrayList<Song> albumSongs = musicStore.getSongsFromAlbum(title, artist);
            if (albumSongs != null) {
                for (Song song : albumSongs) {
                    addSongToLibrary(song.getSongTitle(), song.getSongArtist());
                }
            }
        }
    }
    
    // Get Library Items Methods
    
    public void getLibrarySongs() {
        int totalSongs = 0;
        
        System.out.println("\nSongs in your library:");
        
        for (String artist : userLibrarySongs.keySet()) {
            ArrayList<Song> songs = userLibrarySongs.get(artist);
            totalSongs += songs.size();
            
            for (Song song : songs) {
                System.out.println("- " + song.getSongTitle() + " by " + song.getSongArtist());
            }
        }
        
        if (totalSongs == 0) {
            System.out.println("No songs in your library yet.");
        } else {
            System.out.println("\nTotal songs: " + totalSongs);
        }
    }
    
    public void getLibraryArtists() {
        System.out.println("\nArtists in your library:");
        
        if (userLibrarySongs.isEmpty()) {
            System.out.println("No artists in your library yet.");
        } else {
            for (String artist : userLibrarySongs.keySet()) {
                System.out.println("- " + artist);
            }
            System.out.println("\nTotal artists: " + userLibrarySongs.size());
        }
    }
    
    public void getLibraryAlbums() {
        int totalAlbums = 0;
        
        System.out.println("\nAlbums in your library:");
        
        for (String artist : userLibraryAlbums.keySet()) {
            ArrayList<Album> albums = userLibraryAlbums.get(artist);
            totalAlbums += albums.size();
            
            for (Album album : albums) {
                System.out.println("- " + album.getAlbumTitle() + " by " + album.getArtist() + 
                                   " (" + album.getYear() + ") - " + album.getGenre());
            }
        }
        
        if (totalAlbums == 0) {
            System.out.println("No albums in your library yet.");
        } else {
            System.out.println("\nTotal albums: " + totalAlbums);
        }
    }
    
    public void getLibraryPlaylists() {
        System.out.println("\nPlaylists in your library:");
        
        if (userPlaylists.isEmpty()) {
            System.out.println("No playlists in your library yet.");
        } else {
            for (Playlist playlist : userPlaylists) {
                System.out.println("- " + playlist.getTitle());
            }
            System.out.println("\nTotal playlists: " + userPlaylists.size());
        }
    }
    
    public void getFavoriteSongs() {
        ArrayList<Song> favorites = new ArrayList<>();
        
        for (ArrayList<Song> artistSongs : userLibrarySongs.values()) {
            for (Song song : artistSongs) {
                if (song.isFavorite() != null && song.isFavorite()) {
                    favorites.add(song);
                }
            }
        }
        
        System.out.println("\nYour favorite songs:");
        
        if (favorites.isEmpty()) {
            System.out.println("No favorite songs yet.");
        } else {
            for (Song song : favorites) {
                System.out.println("- " + song.getSongTitle() + " by " + song.getSongArtist());
            }
            System.out.println("\nTotal favorites: " + favorites.size());
        }
    }
    
    // Playlist Management Methods
    
    public void createPlaylist(String name) {
        // Check if a playlist with the same name already exists
        for (Playlist playlist : userPlaylists) {
            if (playlist.getTitle().equalsIgnoreCase(name)) {
                System.out.println("A playlist with this name already exists.");
                return;
            }
        }
        
        userPlaylists.add(new Playlist(name));
        System.out.println("Created playlist: " + name);
    }
    
    public void addSongToPlaylist(String playlistName, String songTitle, String songArtist) {
        // Find the playlist
        Playlist targetPlaylist = null;
        for (Playlist playlist : userPlaylists) {
            if (playlist.getTitle().equalsIgnoreCase(playlistName)) {
                targetPlaylist = playlist;
                break;
            }
        }
        
        if (targetPlaylist == null) {
            System.out.println("Playlist not found: " + playlistName);
            return;
        }
        
        // Find the song in the user's library
        Song targetSong = null;
        String artistKey = songArtist.toLowerCase();
        
        if (userLibrarySongs.containsKey(artistKey)) {
            for (Song song : userLibrarySongs.get(artistKey)) {
                if (song.getSongTitle().equalsIgnoreCase(songTitle)) {
                    targetSong = song;
                    break;
                }
            }
        }
        
        if (targetSong == null) {
            System.out.println("Song not found in your library. Add it to your library first.");
            return;
        }
        
        // Add the song to the playlist
        targetPlaylist.addSong(targetSong);
        System.out.println("Added '" + songTitle + "' to playlist '" + playlistName + "'");
    }
    
    public void removeSongFromPlaylist(String playlistName, String songTitle, String songArtist) {
        // Find the playlist
        Playlist targetPlaylist = null;
        for (Playlist playlist : userPlaylists) {
            if (playlist.getTitle().equalsIgnoreCase(playlistName)) {
                targetPlaylist = playlist;
                break;
            }
        }
        
        if (targetPlaylist == null) {
            System.out.println("Playlist not found: " + playlistName);
            return;
        }
        
        // Get the playlist songs
        ArrayList<Song> playlistSongs = targetPlaylist.getPlaylist();
        
        if (playlistSongs == null || playlistSongs.isEmpty()) {
            System.out.println("The playlist is empty.");
            return;
        }
        
        // Find and remove the song
        boolean removed = false;
        ArrayList<Song> updatedSongs = new ArrayList<>();
        
        for (Song song : playlistSongs) {
            if (song.getSongTitle().equalsIgnoreCase(songTitle) && 
                song.getSongArtist().equalsIgnoreCase(songArtist)) {
                removed = true;
            } else {
                updatedSongs.add(song);
            }
        }
        
        if (removed) {
            // Create a new playlist with the updated songs
            Playlist updatedPlaylist = new Playlist(playlistName);
            for (Song song : updatedSongs) {
                updatedPlaylist.addSong(song);
            }
            
            // Replace the old playlist
            int index = userPlaylists.indexOf(targetPlaylist);
            userPlaylists.set(index, updatedPlaylist);
            
            System.out.println("Removed '" + songTitle + "' from playlist '" + playlistName + "'");
        } else {
            System.out.println("Song not found in the playlist.");
        }
    }
    
    // Song Rating and Favorite Methods
    
    public void markSongAsFavorite(String title, String artist) {
        Song song = findSongInLibrary(title, artist);
        
        if (song == null) {
            System.out.println("Song not found in your library.");
            return;
        }
        
        song.setFav();
        System.out.println("Marked '" + title + "' by " + artist + " as favorite.");
    }
    
    public void rateSong(String title, String artist, int rating) {
        Song song = findSongInLibrary(title, artist);
        
        if (song == null) {
            System.out.println("Song not found in your library.");
            return;
        }
        
        // Convert int to Rating enum
        Song.Rating ratingEnum = Song.Rating.values()[rating - 1];
        
        // Set the rating (this requires adding a setRating method to the Song class)
        setSongRating(song, ratingEnum);
        
        System.out.println("Rated '" + title + "' by " + artist + " as " + ratingEnum);
        
        // If rated 5, automatically set as favorite
        if (rating == 5) {
            song.setFav();
            System.out.println("Song rated 5/5, automatically marked as favorite.");
        }
    }
    
    // Helper Methods
    
    private void setSongRating(Song song, Song.Rating rating) {
        // This method would set the rating on the song object
        // Since the Song class doesn't have a setRating method, you'd need to add one
        // For now, this is a placeholder
        System.out.println("Setting rating " + rating + " (You need to implement setRating in Song class)");
    }
    
    private Song findSongInLibrary(String title, String artist) {
        String artistKey = artist.toLowerCase();
        
        if (userLibrarySongs.containsKey(artistKey)) {
            for (Song song : userLibrarySongs.get(artistKey)) {
                if (song.getSongTitle().equalsIgnoreCase(title)) {
                    return song;
                }
            }
        }
        
        return null;
    }
    
    private void displaySongResults(ArrayList<Song> results, String source) {
        if (results == null || results.isEmpty()) {
            System.out.println("No songs found in " + source + ".");
        } else {
            System.out.println("\nFound " + results.size() + " song(s) in " + source + ":");
            for (Song song : results) {
                System.out.println("- " + song.getSongTitle() + " by " + song.getSongArtist());
            }
        }
    }
    
    private void displayAlbumResults(ArrayList<Album> results, String source) {
        if (results == null || results.isEmpty()) {
            System.out.println("No albums found in " + source + ".");
        } else {
            System.out.println("\nFound " + results.size() + " album(s) in " + source + ":");
            for (Album album : results) {
                System.out.println("Album: " + album.getAlbumTitle() + " by " + album.getArtist());
                System.out.println("Year: " + album.getYear() + " | Genre: " + album.getGenre());
                System.out.println("Songs:");
                System.out.println(album.toString());
                System.out.println();
            }
        }
    }
}
