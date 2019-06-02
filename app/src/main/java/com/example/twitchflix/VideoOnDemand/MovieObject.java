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

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public String getUrl(){
        return url;
    }
}
