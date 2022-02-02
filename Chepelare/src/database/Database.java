package database;

public class Database {
    private static UserRepository userRepository;
    private static VenueRepository venueRepository;
    private static BookingRepository bookingRepository;
    private static RoomRepository roomRepository;

    public Database() {
        userRepository = new UserRepository();
        venueRepository = new VenueRepository();
        bookingRepository = new BookingRepository();
        roomRepository = new RoomRepository();
    }

    public static UserRepository getUserRepository() {
        return userRepository;
    }

    public static VenueRepository getVenueRepository() {
        return venueRepository;
    }

    public static BookingRepository getBookingRepository() {
        return bookingRepository;
    }

    public static RoomRepository getRoomRepository() {
        return roomRepository;
    }
}
