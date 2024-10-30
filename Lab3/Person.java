public class Person {
    private String firstName;
    private String lastName;
    private static int ID = 0;
    private int UID;
    private Website website;

    public Person(String firstName, String lastName, Website website) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.UID = incrementID();
        this.website = website;
        addPersonToWebsite(website);
    }

    private void addPersonToWebsite(Website website){
        website.addPerson(this);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getUID() {
        return UID;
    }

    private static synchronized int incrementID() {
        return ++ID;
    }

    public static boolean sendMessageTo(int senderUID, int receiverUID, String messageContent, Website web) {
        Person sender = web.findPerson(senderUID); //might need to pass website through param, maybe not though
        Person receiver = web.findPerson(receiverUID);

        if(receiver == null){
            return false;
        }else if (receiver != null && sender != null){
            Message message = new Message(sender, receiver, messageContent);
            web.addMessage(message);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public String toString(){
        return String.format("Person[UID=%d, FirstName=%s, LastName=%s]", UID, firstName, lastName);
        }
}