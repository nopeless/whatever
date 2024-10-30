import java.util.ArrayList;

public class Website {
    public ArrayList<Person> people = new ArrayList<>();
    public ArrayList<Message> messages = new ArrayList<>();

    public Website(){
    }

    public void addMessage(Message message){//a way to register a new Message as having been sent on the site.
        messages.add(message);
    }
    public void addPerson(Person person){
        people.add(person);
    }


    public Person findPerson(int UID){
        for (Person p : people){
            if(UID == p.getUID()){
                return p;
            }
        }
        return null;
    }

    public boolean isPersonInWebsite(int UID){
        //could make faster with hashmaps but eh this is fine, havent strictly learned hashmaps in class yet
        for (Person p : people){
            if(UID == p.getUID()){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Person> getPeople(){
        return people;
    }

    public String toStringOfAllMessagesForTwoPeople(Person firstPerson, Person secondPerson){
        String thing = "";
        ArrayList<Message> messagesForTwoPeople = new ArrayList<>();
        messagesForTwoPeople = getAllMessagesForTwoPeople(firstPerson, secondPerson);
        if(messagesForTwoPeople.isEmpty()) return String.format("There are no messages between %s %s and %s %s", firstPerson.getFirstName(), firstPerson.getLastName(), secondPerson.getFirstName(), secondPerson.getLastName());
        for (Message m : messagesForTwoPeople){
            thing += m.toString() + "\n";
        }
        return thing;
    }

    private ArrayList<Message> getAllMessagesForTwoPeople(Person firstPerson, Person secondPerson){
        ArrayList<Message> AllMessagesBetweenTwoPeople = new ArrayList<>();
        for (Message m : messages){
            if ((m.getSender() == firstPerson && m.getReceiver() == secondPerson) || (m.getSender() == secondPerson && m.getReceiver() == firstPerson)){
                AllMessagesBetweenTwoPeople.add(m);
            }
        }
        return AllMessagesBetweenTwoPeople;
    }
}
