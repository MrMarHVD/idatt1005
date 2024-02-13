package no.ntnu.idatt1005.plate.repo;

import no.ntnu.idatt1005.plate.data.MyEntity;
import no.ntnu.idatt1005.plate.repo.MyEntityRepo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyEntityRepoTest {

    @Test
    public void testThatWeCanReadMyEntityFromDatabase() {
        MyEntity e = new MyEntityRepo().getMyEntity("id");
        assertEquals(e.getName(), "name");
    }

}