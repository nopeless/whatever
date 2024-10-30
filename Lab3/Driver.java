import java.util.Scanner;

public class Driver {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String text = "";
        String message = "";
        int senderUID = 0;
        int receiverUID = 0;

        // hard coded person and website objects to test with
        Website website = new Website();
        new Person("luke", "monroe", website);
        new Person("bruce", "wayne", website);

        do {
            System.out.println("Type 'quit' to exit, anything else to keep going:");
            text = scanner.nextLine();

            if (text.equals("quit")) {
                break;
            }

            System.out.println("Enter the sender's UID, or -1 to view chat history, or -2 to create a new Person:");
            if (scanner.hasNextInt()) {
                senderUID = scanner.nextInt();
                if (senderUID == -1) {
                    // get chat history
                    System.out.println(getMessages(scanner, website));
                    scanner.nextLine();
                } else if (senderUID == -2) { // receives input and creates Person Obj
                    createPerson(scanner, website);
                } else { 
                    if (website.isPersonInWebsite(senderUID)) {
                        handleUserInputForReceiverUID(scanner, website, message, senderUID);
                    } else {
                        System.out.println("Person with that UID not found : ");
                        scanner.nextLine();
                    }
                }
            } else {
                System.out.println("Please enter a number.");
                scanner.nextLine();
            }
        } while (true);
        scanner.close();
    }

    private static String getMessages(Scanner scanner, Website website) {

        System.out.println("Enter the first person's UID:");
        if (scanner.hasNextInt()) {
            int firstPersonUID = scanner.nextInt();
            System.out.println("Enter the second person's UID:");
            if (scanner.hasNextInt()) {
                int secondPersonUID = scanner.nextInt();
                if (firstPersonUID == secondPersonUID) {
                    return "error: Cannot enter same UID.";
                } else if (website.isPersonInWebsite(firstPersonUID) && website.isPersonInWebsite(secondPersonUID)) {
                    Person personObj = website.findPerson(firstPersonUID);
                    Person personObj2 = website.findPerson(secondPersonUID);
                    System.out.printf("Showing chat history between [%s %s] and [%s %s] \n", personObj.getFirstName(),
                            personObj.getLastName(), personObj2.getFirstName(), personObj2.getLastName());
                    return website.toStringOfAllMessagesForTwoPeople(personObj, personObj2);
                } else if (!website.isPersonInWebsite(firstPersonUID)) {
                    return "First person not found in website.";
                } else if (!website.isPersonInWebsite(secondPersonUID)) {
                    return "Second person not found in website.";
                }
            }
        }
        return "error: Enter a integer.";

    }

    private static void createPerson(Scanner scanner, Website website) {
        System.out.println("What is the first name of the person: ");
        scanner.nextLine(); // consume the leftover newline
        String firstName = scanner.nextLine();

        System.out.println("What is the last name of the person: ");
        String lastName = scanner.nextLine();

        if (firstName != null && lastName != null) {
            new Person(firstName, lastName, website);
            System.out.println("Person created successfully.");
        } else {
            System.out.println("Error: First name or last name is null.");
        }
    }

    private static void sendMessage(Scanner scanner, Website website, String message, int senderUID, int receiverUID) {
        if (website.isPersonInWebsite(receiverUID) && (receiverUID == senderUID)) {
            System.out.println("Cannot send message to yourself.");
            scanner.nextLine();
        } else if (website.isPersonInWebsite(receiverUID)) {
            System.out.println("Enter the message text being sent:");
            scanner.nextLine(); // consume the leftover newline (clear buffer)
            message = scanner.nextLine();
            if (Person.sendMessageTo(senderUID, receiverUID, message, website)) {
                System.out.println("Message successfully sent.");
            } else {
                System.out.println("Error: There was a problem sending the message.");
            }
        } else {
            System.out.println("Person with that UID not found.");
            scanner.nextLine();

        }
    }

    private static int handleUserInputForSenderUID(Scanner scanner, Website website, String message) {
        System.out.println("Enter the sender's UID, or -1 to view chat history, or -2 to create a new Person:");
        if (scanner.hasNextInt()) {
            int senderUID = scanner.nextInt();
            if (senderUID == -1) {
                // get chat history
                System.out.println(getMessages(scanner, website));
                scanner.nextLine();
            } else if (senderUID == -2) { // receives input and creates Person Obj
                createPerson(scanner, website);
            } else { // if number is entered checks if that UID belongs to a person and if it does
                     // then lets the person enter a receiver UID
                if (website.isPersonInWebsite(senderUID)) {
                    handleUserInputForReceiverUID(scanner, website, message, senderUID);
                } else {
                    System.out.println("Person with that UID not found : ");
                    scanner.nextLine();
                }
            }
            return senderUID;
        } else {
            System.out.println("Please enter a number.");
            scanner.nextLine();
            return -1;
        }
    }

    private static void handleUserInputForReceiverUID(Scanner scanner, Website website, String message, int senderUID) {
        System.out.println("Enter the receiver's UID, or -1 to view chat history:");
        if (scanner.hasNextInt()) {
            int receiverUID = scanner.nextInt();
            if (receiverUID == -1) {
                // get chat history
                System.out.println(getMessages(scanner, website));
            } else {
                sendMessage(scanner, website, message, senderUID, receiverUID);
            }
        } else {
            System.out.println("Person with that UID not found : ");
            scanner.nextLine();
        }
    }
}