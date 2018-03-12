package bpp.quoter;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by dasha on 09.03.18.
 */
@Service
@Profiling
public class SayHi implements Quoter{
    private String message;

    @Randomize(min = 2, max =10)
    private int repeat;

    @Override
    public void sayQuoter() {
        System.out.println(message);
    }

    public SayHi() {
        System.out.println("Phase 1: constructor");
        System.out.println("repeat = " + repeat);
    }

    @PostConstruct
    void init() {
        System.out.println("Phase 1.2: postProcessBeforeInitialization, repeat = " + repeat);
        System.out.print("Phase 2: init(), setMessage: ");
        message = "Hi! I'm robot Rob!";
        sayQuoter();
    }

    public void Now() {
        for (int i = 0; i < repeat; i++) {
            sayQuoter();
        }
    }
}
