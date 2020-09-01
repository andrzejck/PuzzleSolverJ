package mensa;

import java.util.HashMap;

public class Context {
    public Segment getSegment0() {
        return segment0;
    }

    public void setSegment0(Segment segment0) {
        this.segment0 = segment0;
    }

    private Segment segment0;

    private Context() {
        segment0 = new Segment(0, 0, 100, 0);
    }

    private static class ContextHelper {
        private static final Context INSTANCE = new Context();
    }

    public static Context getInstance() {
        return Context.ContextHelper.INSTANCE;
    }
}
