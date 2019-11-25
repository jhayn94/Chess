package chess.model;

import org.springframework.stereotype.Component;

@Component
public class TestBean {

    private final int x;

    public TestBean() {
        this.x = 15;
    }

    public int getX() {
        return x;
    }
}
