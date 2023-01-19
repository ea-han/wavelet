import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> StringList = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("List of Strings:" + StringList);
        } else if (url.getPath().equals("/search")) {

            String[] parameters = url.getQuery().split("=");
            ArrayList<String> foundList = new ArrayList<>();
            if (parameters[0].equals("s")) {
                for (String searchString : StringList) {
                    if (searchString.contains(parameters[1])) {
                        foundList.add(searchString);
                    }
                }
                return String.format("Found Strings: " + foundList);
            }

        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    StringList.add(parameters[1]);
                    return String.format("String added!");
                }
            }
        }
        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
