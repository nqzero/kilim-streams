package stream2;

import java.util.function.Consumer;
import kilim.Mailbox;
import kilim.Pausable;

public class Example extends kilim.Task {
    static Mailbox<Integer> mb = new Mailbox<Integer>();

    public void execute() throws Pausable {
        double power = 
            StreamSupport.stream(new SpliterBox(),false)
                .mapToDouble(x->x)
                .map(x->Math.sqrt(x))
                .sum();
        System.out.println("power: " + power);
        System.exit(0);
    }

    public static class SpliterBox implements Spliterator<Integer> {
        public boolean tryAdvance(Consumer<? super Integer> action) throws Pausable {
            int msg = mb.get();
            if (msg < 0) return false;
            action.accept(msg);
            return true;
        }
        public Spliterator<Integer> trySplit() throws Pausable { return null; }
        public long estimateSize() { return -1; }
        public int characteristics() { return 0; }
    }
    
    
    public static void main(String [] args) throws Exception {
        new Example().start();
        for (int ii=0; ii < 10; ii++) {
            mb.putb(ii);
            Thread.sleep(200);
        }
        mb.putb(-1);
    }
}
