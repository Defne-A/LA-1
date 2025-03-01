package la1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TestModel {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
    private String checkStr;
	private LibraryModel testModel;
	@BeforeEach
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    testModel = new LibraryModel();
	    checkStr = "";
	}

	@AfterEach //the reason why i used this instead of before is to reset it after each test!
	public void restoreStreams() {
	    System.setOut(originalOut);
	    testModel = new LibraryModel();
	    checkStr = "";
	}

    @Test
    void SearchNotFoundSongByTitle() {
    	testModel.searchSongByTitle("baa");
    	assertEquals("No songs found in music store.\n",outContent.toString());
    }
    @Test
    void SearchFoundSongByTitle() {
    	testModel.searchSongByTitle("Turning Tables");
    	assertEquals("\n"
    			+ "Found 1 song(s) in music store:\n"
    			+ "- Turning Tables by Adele\n",outContent.toString());
    }
    @Test
    void SearchNotFoundSongByArtist() {
    	testModel.searchSongByArtist("gog gg a");
    	assertEquals("No songs found in music store.\n",outContent.toString());
    }
    @Test
    void SearchFoundSongByArtist() {
    	testModel.searchSongByArtist("Adele");
    	checkStr = "Found 24 song(s) in music store:";
    	assertNotEquals(-1,outContent.toString().indexOf(checkStr)); //checking for a small part of the substring!
    }
    //tests for title complete
    @Test
    void SearchNotFoundAlbumByTitle() {
    	testModel.searchAlbumByTitle("baa");
    	assertEquals("No albums found in music store.\n",outContent.toString());
    }
    @Test
    void SearchFoundAlbumByTitle() {
    	testModel.searchAlbumByTitle("19");
    	checkStr = "Album: 19 by Adele\n";
    	assertNotEquals(-1,outContent.toString().indexOf(checkStr));
    }
    @Test
    void SearchNotFoundAlbumByArtist() {
    	testModel.searchAlbumByArtist("baa");
    	assertEquals("No albums found in music store.\n",outContent.toString());
    }
    @Test
    void SearchFoundAlbumByArtist() {
    	testModel.searchAlbumByArtist("Coldplay");
    	checkStr = "Album: A Rush of Blood to the Head by Coldplay\n";
    	assertNotEquals(-1,outContent.toString().indexOf(checkStr));
    }
    @Test
    void SearchLibrarySongsByTitleNULL() {
    	testModel.searchLibrarySongByTitle("baa");
    	assertEquals("No songs found in your library.\n",outContent.toString());
    }
    @Test
    void SearchLibrarySongsByTitle() {
    	testModel.addAlbumToLibrary("19","Adele");
    	testModel.addAlbumToLibrary("A Rush of Blood to the Head","Coldplay");
    	testModel.searchLibrarySongByTitle("Amsterdam");
    	checkStr = "Amsterdam";
    	assertNotEquals(-1,outContent.toString().indexOf(checkStr));
    	
    	}
    @Test
    void SearchLibrarySongsByArtist() {
        HelpAddAlbum();
    	testModel.searchLibrarySongByArtist("Adele");
    	checkStr = "- Set Fire to the Rain by Adele\n";
    	assertNotEquals(-1,outContent.toString().indexOf(checkStr));    	
    }
    @Test
    void SearchLibraryAlbumByTitle() {
        HelpAddAlbum();
    	testModel.searchLibraryAlbumByTitle("21");
    	checkStr = "Album: 21 by Adele\n";
    	assertNotEquals(-1,outContent.toString().indexOf(checkStr));
    }
    @Test
    void SearchLibraryAlbumByArtist() {
        HelpAddAlbum();
    	testModel.searchLibraryAlbumByArtist("Adele");
    	checkStr = "Found 2 album(s) in your library:\n";
    	assertNotEquals(-1,outContent.toString().indexOf(checkStr));
    	
    }
    @Test
    void CreatePlayListValid() {
    	testModel.createPlaylist("hi");
    	assertEquals("Created playlist: hi\n",outContent.toString());
    }
    @Test
    void CreatePlayListInvalid() {
    	testModel.createPlaylist("hi");
    	testModel.createPlaylist("hi");
    	checkStr = "A playlist with this name already exists.\n";
    	assertNotEquals(-1,outContent.toString().indexOf(checkStr));
    }
    @Test
    void AddSongPlaylistInvalid() {
    	testModel.createPlaylist("hi");
    	testModel.addSongToPlaylist("baa", "f", "f");
    	checkStr = "Playlist not found: baa\n";
    	assertNotEquals(-1,outContent.toString().indexOf(checkStr));
    }
    @Test
    void AddSongPlaylistInvalidLIBRARY() {
    	//in the case where the user hasn't added it to the library
    	testModel.createPlaylist("hi");
    	testModel.addSongToPlaylist("hi", "Tired", "Adele");
    	checkStr = "Song not found in your library. Add it to your library first.\n";
    	assertNotEquals(-1,outContent.toString().indexOf(checkStr));
    }
    @Test
    void AddSongPlaylistValidLIBRARY() {
    	HelpAddAlbum();
    	testModel.createPlaylist("hi");
    	testModel.addSongToPlaylist("hi", "Tired", "Adele");
    	checkStr = "Added 'Tired' to playlist 'hi'\n";
    	assertNotEquals(-1,outContent.toString().indexOf(checkStr));
    }
    @Test
    void RemoveSongPlaylistINVALID() {
    	testModel.createPlaylist("hi");
        testModel.removeSongFromPlaylist("f", "E","d");
        checkStr = "Playlist not found: ";
    	assertNotEquals(-1,outContent.toString().indexOf(checkStr));
    }
    @Test
    void RemoveSongPlaylistEMPTY() {
    	testModel.createPlaylist("hi");
        testModel.removeSongFromPlaylist("hi", "Tired","Adele");
        checkStr = "The playlist is empty.\n";
    	assertNotEquals(-1,outContent.toString().indexOf(checkStr));
    }
    @Test
    void RemoveSongPlaylistFOUND() {
    	HelpAddAlbum();
        testModel.removeSongFromPlaylist("hi", "Tired","Adele");
        checkStr = "Removed 'Tired' from playlist 'hi'\n";
    	assertNotEquals(-1,outContent.toString().indexOf(checkStr));
    }
    @Test
    void RemoveSongPlaylistNOTFOUND() {
      	HelpAddAlbum();
        testModel.removeSongFromPlaylist("hi", "baa","Adele");
        checkStr = "Song not found in the playlist.\n";
    	CheckSubstring();
    }
    @Test
    void SearchPlaylistNameINVALID() {
    	testModel.createPlaylist("hi");
    	testModel.searchPlaylistByName("baa");
    	checkStr = "No playlists found with that name in your library.";
    	CheckSubstring();
    }
    @Test
    void SearchPlaylistNameINVALIDSONG() {
    	testModel.createPlaylist("hi");
    	testModel.searchPlaylistByName("hi");
    	checkStr = "  (No songs in this playlist)\n";
    	CheckSubstring();
    }
    @Test
    void SearchPlaylistNameVALID() {
    	HelpAddAlbum();
    	testModel.searchPlaylistByName("hi");
    	checkStr = "Songs:\n"
    			+ "  - Tired by Adele\n"
    			+ "  - My Same by Adele";
    	CheckSubstring();
    }
    //favourite tests
    @Test
    void SetFavouriteINVALID() {
    	testModel.markSongAsFavorite("artist", "baa");
    	assertEquals("Song not found in your library.\n",outContent.toString());
    }
    @Test
    void SetFavouriteVALID() {
        HelpAddAlbum();
        testModel.markSongAsFavorite("Tired", "Adele");
        checkStr = "Marked 'Tired' by Adele as favorite.\n";
        CheckSubstring();
    }
    //rating test
    @Test 
    void rateSongINVALID() {
    	checkStr = "Song not found in your library.";
    	testModel.rateSong("baa", "rah", 0);
    	CheckSubstring();
    }
    @Test
    void rateSongVALID() {
    	checkStr = "Rated";
    	HelpAddAlbum();
    	testModel.rateSong("Tired", "Adele", 3);
    	CheckSubstring();
    }
    @Test
    void rateSongFIVE() {
    	checkStr = "5/5";
    	HelpAddAlbum();
    	testModel.rateSong("Tired", "Adele", 5);
    	CheckSubstring();
    }
    //library tests
    @Test
    void GetLibrarySongZERO() {
    	checkStr = "No songs in your library yet.";
    	testModel.getLibrarySongs();
    	CheckSubstring();
    }
    @Test
    void GetLibrarySongVALID() {
    	HelpAddAlbum();
    	checkStr = "Total songs: 24\n";
    	testModel.getLibrarySongs();
    	CheckSubstring();
    }
    @Test
    void GetLibraryArtistZERO() {
    	checkStr = "No artists in your library yet.";
    	testModel.getLibraryArtists();
    	CheckSubstring();
    }
    @Test
    void GetLibraryArtistVALID() {
    	HelpAddAlbum();
    	checkStr = "Total artists: 1\n";
    	testModel.getLibraryArtists();
    	CheckSubstring();
    }
    @Test
    void GetLibraryAlbumZERO() {
    	checkStr = "No albums in your library yet";
    	testModel.getLibraryAlbums();
    	CheckSubstring();
    }
    @Test
    void GetLibraryAlbumVALID() {
    	checkStr = "Albums in your library:\n"
    			+ "- 19 by Adele (2008) - Pop\n"
    			+ "- 21 by Adele (2011) - Pop";
    	HelpAddAlbum();
    	testModel.getLibraryAlbums();
    	CheckSubstring();
    }
    @Test
    void GetPlaylistZERO() {
       checkStr = "No playlists in your library yet.";
      testModel.getLibraryPlaylists();
      CheckSubstring();
    }
    @Test
    void GetPlaylistVALID() {
        HelpAddAlbum();
        testModel.getLibraryPlaylists();
        checkStr = "Playlists in your library:\n"
        		+ "- hi";
        CheckSubstring();
    }
    @Test
    void getFavouriteSongsZERO() {
    	checkStr = "No favorite songs yet.";
    	testModel.getFavoriteSongs();
    	CheckSubstring();
    }
    @Test
    void getFavouriteSongsVALID() {
    	HelpAddAlbum();
    	testModel.markSongAsFavorite("Tired", "Adele");
    	testModel.getFavoriteSongs();
    	checkStr = "Your favorite songs:\n"
    			+ "- Tired by Adele";
    	CheckSubstring();
    }
    private void HelpAddAlbum() {
    	//helper method to prevent duplicated code
    	testModel.addAlbumToLibrary("19","Adele");
    	testModel.addAlbumToLibrary("21","Adele");
    	testModel.createPlaylist("hi");
    	testModel.addSongToPlaylist("hi", "Tired", "Adele");
    	testModel.addSongToPlaylist("hi", "My Same", "Adele");
    }
    private void CheckSubstring() {
    	assertNotEquals(-1,outContent.toString().indexOf(checkStr));
    }
    }
