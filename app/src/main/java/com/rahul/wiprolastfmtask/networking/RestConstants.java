package com.rahul.wiprolastfmtask.networking;

public interface RestConstants {

    String LAST_FM_API_KEY = "d1754e143b57839aad90c3d0137ffdeb";
    String LAST_FM_SHARED_SECRET = "eebaca071e101da0d2ed142cb95334e8";

    String METHOD_ARTIST = "artist.search";
    String METHOD_ALBUM = "album.search";
    String METHOD_TRACK= "track.search";
    String FORMAT = "json";


    String BASE_URL = "https://ws.audioscrobbler.com/";

    String SEARCH = "/2.0/";

    String test = "http://ws.audioscrobbler.com/2.0/?method=album.search&album=believe&api_key=d1754e143b57839aad90c3d0137ffdeb&format=json";


}
