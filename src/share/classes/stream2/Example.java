package stream2;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import kilim.Mailbox;
import kilim.Pausable;
import static stream2.Arrays2.proxy;

public class Example extends kilim.Task {
    static Mailbox<String> mb = new Mailbox<String>();

    public void execute() throws Pausable {
        Stream<String> msgs = StreamSupport.stream(new SpliterBox(),false);
        msgs.map(s -> s+"___").forEach(System.out::println);
        System.exit(0);
    }

    public static class SpliterBox implements Spliterator<String> {
        public boolean tryAdvance(Consumer<? super String> action) throws Pausable {
            String msg = mb.get();
            if (msg.length()==0) return false;
            action.accept(msg);
            return true;
        }
        public Spliterator<String> trySplit() throws Pausable { return null; }
        public long estimateSize() { return -1; }
        public int characteristics() { return 0; }
    }
    
    
    public static void main(String [] args) throws Exception {
        new Example().start();
        for (int ii=0; ii < 10; ii++) {
            mb.putb("hello-" + ii);
            Thread.sleep(200);
        }
        mb.putb("");
    }
    
    
    
    
    public static void main2(String [] args) throws Pausable {
        Integer [] array = new Integer[10];
        for (int ii=0; ii < array.length; ii++) array[ii] = array.length-ii-1;
        List<Integer> list = java.util.Arrays.asList(array);

        list.parallelStream().skip(3).forEachOrdered(System.out::print);
        System.out.println();
        proxy(list).parallelStream().skip(3).forEachOrdered(System.out::print);
        System.out.println();
        list.stream().distinct().forEach(System.out::print);
        System.out.println();
        proxy(list.stream()).distinct().forEach(System.out::print);                
        System.out.println();
        proxy(list).stream().distinct().forEach(System.out::print);                
        System.out.println();
        proxy((java.lang.Iterable<Integer>)list).stream().distinct().forEach(System.out::print);
        System.out.println();

        final List<Integer> intsAsList = Arrays.asList(4,3,2,1,0);
        SpinedBuffer<Integer> spinedBuffer = new SpinedBuffer<>();
        intsAsList.forEach(spinedBuffer);
        
        
        
        double sum = IntStream.range(0,100000)
                .mapToDouble(x->x)
                .parallel()
                .map(x->Math.sqrt(x))
                .skip(100)
                .sum();
        System.out.println(sum);
        Spliterators.spliterator(new int[] {0,1,2},0);
        Stream.of(1,2,3,4).map(
                x -> "hello"+x
        ).forEach(System.out::println);
    }
}
