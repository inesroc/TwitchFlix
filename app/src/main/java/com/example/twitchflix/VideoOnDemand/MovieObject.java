package com.example.twitchflix.VideoOnDemand;

class MovieObject {
    private String title;
    private String info;
    private String url;

    MovieObject (String text1, String text2, String text3){
        title = text1;
        info = text2;
        url = text3;
    }

    String getTitle() {
        return title;
    }

    String getInfo() {
        return info;
    }

    String getUrl(){
        return url;
    }
}
