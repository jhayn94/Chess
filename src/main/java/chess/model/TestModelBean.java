package chess.model;

import org.springframework.stereotype.Component;

@Component
public class TestModelBean {

    private final int x;

    public TestModelBean() {
        this.x = 15;
    }

    public int getX() {
        return this.x;
    }
}
