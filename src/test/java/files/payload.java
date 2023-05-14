package files;

public class payload {

    public static String addPlace() {
        return """
                {
                  "location": {
                    "lat": -38.383494,
                    "lng": 33.427362
                  },
                  "accuracy": 50,
                  "name": "Frontline house",
                  "phone_number": "(+91) 983 893 3937",
                  "address": "29, side layout, cohen 09",
                  "types": [
                    "shoe park",
                    "shop"
                  ],
                  "website": "http://google.com",
                  "language": "French-IN"
                }""";
    }

    public static String putPlace(String place_id, String address) {
        return """
                {
                "place_id":""" + "\"" + place_id + "\"" +
                """
                        ,"address":""" + "\"" + address + "\"" + """ 
                ,"key":"qaclick123"
                }""";
    }

    public static String coursePrice() {

        return """
                {
                  "dashboard": {
                    "purchaseAmount": 1162,
                    "website": "rahulshettyacademy.com"
                  },
                  "courses": [
                    {
                      "title": "Selenium Python",
                      "price": 50,
                      "copies": 6
                    },
                    {
                      "title": "Cypress",
                      "price": 40,
                      "copies": 4
                    },
                    {
                      "title": "RPA",
                      "price": 45,
                      "copies": 10
                    },
                     {
                      "title": "Appium",
                      "price": 36,
                      "copies": 7
                    }          \s
                  ]
                }
                """;
    }

    public static String addBook(String isbn, String aisle) {
        return "{\n" +
                "  \"name\": \"Learn Appium Automation with Java\",\n" +
                "  \"isbn\": \""+isbn+"\",\n" +
                "  \"aisle\": \""+aisle+"\",\n" +
                "  \"author\": \"John foe\"\n" +
                "}" ;
    }

    public static String deleteBook(String ID){
        return "{\n" +
                " \n" +
                "\"ID\" : \""+ID+"\"\n" +
                " \n" +
                "} \n";
    }
}
