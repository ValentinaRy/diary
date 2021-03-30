package diary.entry;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.time.LocalDateTime;

@Immutable
public class DoubleEntry extends Entry {
    private final double value;

    public DoubleEntry(@Nonnull LocalDateTime timeStamp, double value) {
        super(timeStamp);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Nonnull
    @Override
    public EntryType getEntryType() {
        return EntryType.DOUBLE;
    }

    @Override
    @Nonnull
    public String printEntry() {
        return super.printEntry() + ", doubleValue=" + value;
    }
}
