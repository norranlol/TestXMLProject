package parsers.jaxb.marshal;

import parsers.jaxb.model.*;

import java.util.ArrayList;

public class JAXBHelper {

    private static final String HTML_PATH = "src/parsers/jaxb/marshal/result/result.html";

    public static void modifyObjectStructure(Flights flights){
        if (!flights.getFlightList().isEmpty()){
            Flight flight = flights.getFlightList().get(flights.getFlightList().size() - 1);
            if (flight.getTicketList() == null)
                flight.setTicketList(new ArrayList<Ticket>());
            Client client1 = new Client(1, "Попов", "Иван", "Иванович", 1408, 553421, "89331435533", "popov@gmail.ru", "ул. Гороховая д.33 кв.54");
            Client client2 = new Client(2, "Бурундуков", "Василий", "Васильевич", 1409, 214345, "89215334266", "burunduk@gmail.ru", "ул. Василисина д.14 кв.35");
            AnonimousClient anonClient = new AnonimousClient("FRB", 443, "YES");
            Ticket ticket1 = new Ticket(1, 3, 4, 7900, client1, null);
            Ticket ticket2 = new Ticket(2, 5, 6, 10100, client2, null);
            Ticket ticket3 = new Ticket(3, 8, 9, 9000, null, anonClient);
            flight.getTicketList().add(ticket1);
            flight.getTicketList().add(ticket2);
            flight.getTicketList().add(ticket3);
        }
    }
}
