package com.rahul.wiprolastfmtask.models;

import java.util.List;

public class Results {
    public Result results;

    public class Result {
        public ArtistMatches artistmatches;
        public AlbumMatches albummatches;
        public TrackMatches trackmatches;
    }

    public class ArtistMatches {
        public List<Artist> artist;
    }

    public class AlbumMatches {
        public List<Album> album;
    }

    public class TrackMatches {
        public List<Track> track;
    }
}
