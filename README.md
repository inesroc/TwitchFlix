# Report: Twitchflix

# Mobile Device Programing (2019)

## Rodrigo Marques - 201605427, Inês Rocha - 201606266
## 1 Introduction

This project was developed for the Mobile Devices Pro gramming class. The objective was to create a platform for both watching Video-on-Demand and livestreams from other users of the app. The frontend was developed as an android application. It is divided in two activities, watching videos and
live streaming. From the first users can choose from a catalog of videos on our database or watch another user’s livestream. From the later users can stream from their camera to other users using our server. The backend is a combination of a Jersey + Jetty REST service to serve movies from an Apache Cassandra Database and NGINX to handle livestreams. It is hosted in a Virtual Machine instance at the Google Cloud platform.

## 2 Architecture

## 2.1 Frontend

The frontend was developed for Android. It is compiled for API 28, with support as far back as API 21. From the main menu  users can choose
to stream videos to their device, opening a catalog of available videos or to start streaming directly from their camera.

2.1.1 Video-on-Demand

On choosing the video streaming option users are presented with a page with all the available categories. When selecting one, the app retrieves all videos of that category from the server and populates a RecyclerView with objects for each movie containing the name, info and url. This can be seen on the figure below
When one of these objects is pressed the PlayMovie activity starts and it uses the url of the object and a videoView to reproduce the movie.

![Alt text](https://drive.google.com/uc?export=view&id=1L-zNF0yP_xbCz5JVSjAC84DOsze49n67)

2.1.2 Broadcasting Livestreams
For broadcasting live videos we used ”Yet Another Stream Encoder for Android” [3] to encode and stream our live video. It uses the librtmp library to send the
data to the server via the rtmp url. Users can switch between the front-facing and the back camera while streaming and record their own stream on their device if they want. It also includes the option of choosing from a number of filters to be used while streaming, implemented through Magic Camera [8].
To stream, users type a name for their broadcast and press ’Publish’.

2.1.3 Watching Livestreams

From the Video Streaming activity users can also choose to watch a stream from another user. For this they prompted to input the name of the stream and
application will retrieve the broadcast from the server by building the rtmp url from input. After a short wait the video starts playing.
To be able to play RTMP videos, we used some of the code from one the examples given in class [4], in which we use the media player exoplayer2 [2] with the
rtmp extension to get the stream from the server.

![Alt text](https://drive.google.com/uc?export=view&id=1BRGrKcElE5Do5UeTonFv36xsslbbAdKH)

### 2.2 Backend

The backend is a combination of a Java server built from Jersey+Jetty, an Apache Cassandra database and NGINX. These all come together to server users with
url’s to the videos they want to play or to broadcast their livestreams. Everything is hosted in a Compute Engine VM in Google Cloud.

2.2.1 Cassandra DB

Movies are stored in a NoSQL database managed by Apache Cassandra. Instead of storing the whole file, we store only an url to it.
A keyspace and table were created with the following structure:

```
CREATE TABLE twitchflix.movie (
id int PRIMARY KEY,
hours int ,
minutes int ,
name text ,
seconds int ,
tags set<text>,
url text ,
year int
)
```

2.2.2 Jersey+Jetty

The server for movie distribution is built upon the example provided in class [5]. We also integrated the Datastax driver for Cassandra
[1] to query the database from Java. On start-up, the server retrieves all movies from the database and populates anArrayList<Movie>. When
asked for a specific movie the server also queries the database for a movie given its id. The server uses the same address structure as the
example provided: http://34.90.22.224:8080/resources returns aList<Movie>with all available movies, and http://34.90.22.224:8080/resources/{ID} returns the
stats of a specificMovie. Both return in JSON the following structure (or list of):

```
public class Movie {
int id ;
String name ;
int year ;
Set<String>tags ;
String url ;
Length length ;
}
public class Length {
int hours ;
int minutes ;
int seconds ;
}
```
#### 2.2.3 NGINX

NGINX is used as a reverse proxy for the livestreaming services. We used the nginx-rtmp-module [6]. With it, the configuration file is as simple as creating an
application, as shown (omitting the http part): 

```
rtmp {
  server {
    listen 1935;
    chunksize 4096;

    application l i v e {
      l i v e on ;
      record on ;
      recordpath /tmp/av ;
      hls on ;
      hlspath /data/ hls ;
      hlsfragment 1 s ;
      hlstype l i v e ;
      }
      ....
    }
}
```

Publishing and receiving streams are both handled by connecting to: rtmp://34.90.22.224:1935/live/name

### 2.3 Google Cloud

We set up a Virtual Machine from Datastax with Cassandra pre-installed, set up an external IP and added firewall rules as needed. After that, first we created
the database and filled with it movies. Then we installed our Java server on the machine. We ran it inside a tmux [7] session so it would stay up
even after closing the SSH connection. When all that was working we installed NGINX and configured it.

```
IP Address:34.90.22.
Open Ports:

- 8080: REST Server
- 1935: NGINX-RTMP
- 80: NGINX-HTTP
```

## 3 Results and Conclusion

There are still some bugs and unimplemented features in the final application. There is no catalog for ongoing streams and a bug where stream video playing stops when the camera rotates. We also did not implement user authentication, which could have been used for stream cataloging. As for the server, we realized while writing this report that we were querying the database for every movie request, when the movies were already loaded in memory on start up. This was because the handler for getting the list of movies was implemented after the handler for getting a single movie and the optimization didn’t occur to us then.

## References

[1] DataStax. Datastax java driver for apache cassandra. https://github.com/datastax/java-driver.

[2] Google. Exoplayer.https://github.com/google/ExoPlayer.

[3] Mao Leo. Yet another stream encoder for android. https://github.com/begeekmyfriend/yasea.

[4] Ant Media. Live video broadcaster. https://github.com/ant-media/LiveVideoBroadcaster.

[5] Martins Rolando. netty-jersey. https://github.com/rolandomar/MobileComputing1819/tree/master/netty-jersey.

[6] Arutyunyan Roman. nginx-rtmp-module. https://github.com/arut/nginx-rtmp-module.

[7] tmux. tmux.https://github.com/tmux/tmux.

[8] Haoyu Wu. Magic camera.https://github.com/wuhaoyu1990/MagicCamera.


