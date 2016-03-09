
copied Spliterator.?.java

for ii in *; do sed -i -n -e "/import .*java.util.Spliterator/ d; p" $ii; done

