import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final int MINOR_AGE = 18;
    private static final int MAX_SOLDIER_AGE = 27;
    private static final int MAX_PENSION_AGE_MAN = 65;
    private static final int MAX_PENSION_AGE_WOMEN = 60;

    public static void main(String[] args) throws InterruptedException {

        Collection<Person> persons = getPersons();

        Collection<Person> minorPersons = findMinorPersons(persons);
        printCollection(minorPersons, "призывников");
        sleep(3);

        Collection<String> soldiers = findSoldiers(persons);
        for (String family : soldiers) {
            System.out.println(family);
        }
        sleep(3);

        Collection workablePeople = potentiallyWorkablePersons(persons);
        printCollection(workablePeople, "потенциально работающих");
    }

    private static void printCollection(Collection<Person> collection, String group) throws InterruptedException{
        System.out.println("Количество " + group + ": " + collection.size());
        sleep(2);
        for (Person person : collection) {
            System.out.println(person);
        }
    }

    private static void sleep(int seconds) throws InterruptedException {
        TimeUnit.SECONDS.sleep(seconds);
    }

    private static Collection<Person> getPersons() {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        return persons;
    }

    public static Collection<Person> findMinorPersons(Collection<Person> persons) {
        Stream<Person> stream = persons.stream();
        return stream.filter(person -> person.getAge() > MINOR_AGE)
                .collect(Collectors.toList());
    }

    public static Collection<String> findSoldiers(Collection<Person> persons) {
        Stream<Person> stream = persons.stream();
        List<String> soldiers = stream.filter(person -> person.getSex().equals(Sex.MAN))
                .filter(person -> person.getAge() > MINOR_AGE)
                .filter(person -> person.getAge() < MAX_SOLDIER_AGE)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        return soldiers;

    }

    public static Collection<Person> potentiallyWorkablePersons(Collection<Person> persons) {
        Stream<Person> stream = persons.stream();
        Comparator<Person> personComparator = Comparator.comparing(Person::getFamily);
        Collection<Person> workablePersons = stream.filter(person -> person.getEducation().equals(Education.HIGHER))
                .filter(person -> person.getAge() > MINOR_AGE)
                .filter(person -> {
                    if (person.getSex().equals((Sex.MAN)) && person.getAge() < MAX_PENSION_AGE_MAN) return true;
                    if (person.getSex().equals((Sex.WOMEN)) && person.getAge() < MAX_PENSION_AGE_WOMEN) return true;
                    return false;
                })
                .sorted(personComparator)
                .collect(Collectors.toList());
        return workablePersons;
    }
}
