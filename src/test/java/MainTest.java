import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Collection;

class MainTest {
    private static Person Man20yoHigher;
    private static Person Women30yoHigher;
    private static Person Women25yoElementary;
    private static Person Man5yoFurther;

    @BeforeAll
    private static void before() {
        Man20yoHigher = new Person(
                "I am",
                "20 yo",
                20,
                Sex.MAN,
                Education.HIGHER
        );
        Women30yoHigher = new Person(
                "I am",
                "30 yo",
                30,
                Sex.WOMEN,
                Education.HIGHER
        );
        Women25yoElementary = new Person(
                "I am",
                "25 yo",
                25,
                Sex.WOMEN,
                Education.ELEMENTARY
        );
        Man5yoFurther = new Person(
                "I am",
                "5 yo",
                5,
                Sex.MAN,
                Education.FURTHER
        );
    }

    @Test
    public void findMinorPersonsTest() {
        Collection<Person> persons = new ArrayList<>();
        persons.add(Man20yoHigher);
        persons.add(Man5yoFurther);
        persons.add(Women30yoHigher);

        Collection<Person> target = Main.findMinorPersons(persons);

        assertThat(target, hasSize(2));
        assertThat(target, is(contains(Man20yoHigher, Women30yoHigher)));
    }

    @Test
    public void findSoldiersTest() {
        Collection<Person> persons = new ArrayList<>();
        persons.add(Man20yoHigher);
        persons.add(Man5yoFurther);
        persons.add(Women25yoElementary);

        Collection<String> target = Main.findSoldiers(persons);

        assertThat(target, hasSize(1));
        assertThat(target, contains("20 yo"));
    }

    @Test
    public void potentiallyWorkablePersonsTest() {
        Collection<Person> persons = new ArrayList<>();
        persons.add(Man20yoHigher);
        persons.add(Man5yoFurther);
        persons.add(Women30yoHigher);
        persons.add(Women25yoElementary);

        Collection<Person> target = Main.potentiallyWorkablePersons(persons);

        assertThat(target, hasSize(2));
        assertThat(target, contains(Man20yoHigher, Women30yoHigher));
    }
}