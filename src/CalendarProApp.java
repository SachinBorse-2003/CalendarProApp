import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Event {
    LocalDate date;
    String name;
    String time;
    String location;
    String notes;
    String category;

    public Event(LocalDate date, String name, String time, String location, String notes, String category) {
        this.date = date;
        this.name = name;
        this.time = time;
        this.location = location;
        this.notes = notes;
        this.category = category;
    }
}

public class CalendarProApp {
    private static List<Event> events = new ArrayList<>();
    public static int flag = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (flag == 0) {
            System.out.println("Calendar Pro - Your Ultimate Event Organizer\n");
            printCalendar(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
            flag = 1;
        }

        while (true) {
            System.out.println("1. Add an Event");
            System.out.println("2. View Events");
            System.out.println("3. View Calendar of any month");
            System.out.println("4. Reminder Settings");
            System.out.println("5. Search Events by Category");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addEvent(scanner);
                    break;
                case 2:
                    clearScreen();
                    viewEvents();
                    break;
                case 3:
                    getCalendarDetails(scanner);
                    break;
                case 4:
                    setReminders(scanner);
                    break;
                case 5:
                    searchEventsByCategory(scanner);
                    break;
                case 6:
                    System.out.println("Exiting CalendarProApp. Goodbye!");
                    System.exit(0);
                default:
                    clearScreen();
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void printCalendar(int year, int month) {
        System.out.println("Calendar for " + year + " - " + month);
        System.out.println("+---------------------------+");
        System.out.println("|Mon Tue Wed Thu Fri Sat Sun|");
        System.out.println("+---------------------------+");

        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        int dayOfWeek = startOfMonth.getDayOfWeek().getValue();
        int daysInMonth = startOfMonth.lengthOfMonth();

        for (int i = 1; i < dayOfWeek; i++) {
            System.out.print("   ");
        }

        for (int day1 = 1; day1 <= daysInMonth; day1++) {
            System.out.printf("%4d", day1);

            if ((dayOfWeek + day1 - 1) % 7 == 0 || day1 == daysInMonth) {
                System.out.println();
            }
        }
        System.out.println("+---------------------------+");
    }

    public static void getCalendarDetails(Scanner scanner){
        System.out.print("Enter year (yyyy): ");
        int year = scanner.nextInt();
        System.out.print("Enter month (MM): ");
        int month = scanner.nextInt();
        clearScreen();
        printCalendar(year, month);
    }

    public static void addEvent(Scanner scanner) {
        System.out.print("Enter date (yyyy-MM-dd): ");
        LocalDate eventDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);

        System.out.print("Event Name: ");
        String name = scanner.nextLine();
        System.out.print("Time: ");
        String time = scanner.nextLine();
        System.out.print("Location: ");
        String location = scanner.nextLine();
        System.out.print("Notes: ");
        String notes = scanner.nextLine();
        System.out.print("Category: ");
        String category = scanner.nextLine();

        if (hasSchedulingConflict(eventDate, time)) {
            System.out.println("Scheduling conflict! The selected time is not available.");
            return;
        }

        Event event = new Event(eventDate, name, time, location, notes, category);
        events.add(event);
        clearScreen();
        System.out.println("Event added successfully!");
    }

    public static boolean hasSchedulingConflict(LocalDate newEventDate, String newEventTime) {
        for (Event existingEvent : events) {
            if (existingEvent.date.equals(newEventDate) && existingEvent.time.equals(newEventTime)) {
                return true;
            }
        }
        return false;
    }

    public static void viewEvents() {
        if (events.isEmpty()) {
            System.out.println("No events found.");
        } else {
            System.out.println("Events:");
            for (Event event : events) {
                System.out.println("Category: " + event.category);
                System.out.println("Event: " + event.name);
                System.out.println("Date: " + event.date);
                System.out.println("Time: " + event.time);
                System.out.println("Location: " + event.location);
                System.out.println("Notes: " + event.notes);
                System.out.println();
            }
        }
    }

    public static void setReminders(Scanner scanner) {
        System.out.println("Reminder Settings:");
        System.out.println("1. Set Reminder");
        System.out.println("2. Back to Main Menu");
        System.out.print("Enter your choice: ");

        int reminderChoice = scanner.nextInt();
        scanner.nextLine();

        switch (reminderChoice) {
            case 1:
                setCustomReminder(scanner);
                break;
            case 2:
                return;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    public static void setCustomReminder(Scanner scanner) {
        System.out.print("Enter the event name for which you want to set a reminder: ");
        String eventName = scanner.nextLine();

        Event targetEvent = null;
        for (Event event : events) {
            if (event.name.equalsIgnoreCase(eventName)) {
                targetEvent = event;
                break;
            }
        }

        if (targetEvent == null) {
            System.out.println("Event not found.");
            return;
        }

        System.out.print("Enter the number of days before the event to set the reminder: ");
        int daysBefore = scanner.nextInt();

        LocalDate reminderDate = targetEvent.date.minusDays(daysBefore);

        System.out.println("Reminder set for " + targetEvent.name + " on " + reminderDate);
        long daysDifference = LocalDate.now().until(reminderDate).toTotalMonths();
        if (daysDifference == 0) {
            System.out.println("Reminder: Today!");
        } else if (daysDifference == 1) {
            System.out.println("Reminder: Tomorrow!");
        } else {
            System.out.println("Reminder: " + daysDifference + " days before.");
        }
    }

    public static void searchEventsByCategory(Scanner scanner) {
        System.out.print("Enter category to search for events: ");
        String searchCategory = scanner.nextLine();

        System.out.println("Events in category '" + searchCategory + "':");
        boolean found = false;

        for (Event event : events) {
            if (event.category.equalsIgnoreCase(searchCategory)) {
                System.out.println("Event: " + event.name);
                System.out.println("Date: " + event.date);
                System.out.println("Time: " + event.time);
                System.out.println("Location: " + event.location);
                System.out.println("Notes: " + event.notes);
                System.out.println();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No events found in the '" + searchCategory + "' category.");
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
