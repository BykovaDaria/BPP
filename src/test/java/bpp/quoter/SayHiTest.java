package bpp.quoter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by dasha on 11.03.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SayHiTest {
    @Autowired
    SayHi sayHi;

    @Test
    public void SayHi() {
        sayHi.Now();
    }

}