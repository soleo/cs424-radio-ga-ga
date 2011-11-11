import processing.net.*;

Client c;
String data;

void setup() {
  size(200, 200);
  background(50);
  fill(200);
  c = new Client(this, "musicbrainz.org", 80); // Connect to server on port 80
  c.write("GET /ws/2/artist/?query=artist:fred HTTP/1.1\n"); // Use the HTTP "GET" command to ask for a Web page
  c.write("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.106 Safari/535.2\n");
 // c.write("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n");
  c.write("Host: musicbrainz.org\n\n"); // Be polite and say who we are
}

void draw() {
  if (c.available() > 0) { // If there's incoming data from the client...
    data = c.readString(); // ...then grab it and print it
    println(data);
  }
}
